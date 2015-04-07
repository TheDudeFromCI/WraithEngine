package Test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GatherIntellegnce{
	private JPanel panel;
	private JFrame frame;
	private boolean slow = true;
	private int steps;
	private final ArrayList<Animal> animals = new ArrayList<>();
	private final ArrayList<Food> foods = new ArrayList<>();
	public GatherIntellegnce(){
		frame=new JFrame();
		frame.setTitle("Gathering Intellegence");
		frame.setSize(100, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.add(panel=new JPanel(){
			@Override public void paint(Graphics g1){
				Graphics2D g = (Graphics2D)g1;
				g.setColor(Color.lightGray);
				g.fillRect(0, 0, getWidth(), getHeight());
				g.setColor(Color.black);
				for(int i = 1; i<20; i++)g.drawLine(i*32, 0, i*32, getHeight());
				for(int i = 1; i<15; i++)g.drawLine(0, i*32, getWidth(), i*32);
				Animal a;
				for(int i = 0; i<animals.size(); i++){
					a=animals.get(i);
					if(a==null)continue;
					g.setColor(Color.red);
					g.fillOval(a.x*32, a.y*32, 32, 32);
					g.setColor(Color.black);
					g.drawOval(a.x*32, a.y*32, 32, 32);
					g.setColor(Color.blue);
					if(a.outputs[0])g.drawLine(a.x*32+16, a.y*32+16, a.x*32, a.y*32+16);
					if(a.outputs[1])g.drawLine(a.x*32+16, a.y*32+16, a.x*32+32, a.y*32+16);
					if(a.outputs[2])g.drawLine(a.x*32+16, a.y*32+16, a.x*32+16, a.y*32);
					if(a.outputs[3])g.drawLine(a.x*32+16, a.y*32+16, a.x*32+16, a.y*32+32);
				}
				Food f;
				for(int i = 0; i<foods.size(); i++){
					f=foods.get(i);
					if(f==null)continue;
					g.setColor(Color.white);
					g.fillOval(f.x*32, f.y*32, 32, 32);
					g.setColor(Color.black);
					g.drawOval(f.x*32, f.y*32, 32, 32);
				}
				g.dispose();
			}
		});
		panel.setPreferredSize(new Dimension(640, 480));
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.addKeyListener(new KeyAdapter(){
			@Override public void keyPressed(KeyEvent e){ if(e.getKeyCode()==KeyEvent.VK_Q)slow=!slow; }
		});
		for(int i = 0; i<5; i++)animals.add(new Animal());
		frame.setVisible(true);
		new Thread(new Runnable(){
			public void run(){
				while(true){
					step();
					panel.repaint();
					if(slow){
						try{ Thread.sleep(50);
						}catch(Exception exception){}
					}
				}
			}
		}).start();
	}
	private void step(){
		steps++;
		while(foods.size()<10)foods.add(new Food());
		for(Animal a : animals)a.step();
		for(int i = 0; i<animals.size();){
			if(animals.get(i).health==0)animals.remove(i);
			else i++;
		}
		boolean allDead = animals.size()==0;
		while(animals.size()<5){
			if(allDead)animals.add(new Animal());
			else animals.add(new Animal(animals.get((int)(Math.random()*animals.size())), animals.get((int)(Math.random()*animals.size()))));
		}
	}
	public static void main(String[] args){ new GatherIntellegnce(); }
	class Animal{
		int x, y, health;
		NeuralNetwork brain;
		double[] inputs = new double[12];
		boolean[] outputs = new boolean[4];
		public Animal(){
			do{ randomizeLocation();
			}while(locationTaken(true));
			health=100;
			brain=new NeuralNetwork(inputs.length, 10, 1, outputs.length);
		}
		public Animal(Animal a, Animal b){
			do{ randomizeLocation();
			}while(locationTaken(true));
			health=100;
			brain=NeuralNetwork.merge(a.brain, b.brain);
		}
		void randomizeLocation(){
			x=(int)(Math.random()*20);
			y=(int)(Math.random()*15);
		}
		boolean locationTaken(boolean food){
			for(Animal a : animals)if(a!=this&&a.x==x&&a.y==y)return true;
			if(food)for(Food f  : foods)if(f.x==x&&f.y==y)return true;
			return false;
		}
		void step(){
			updateSensors();
			brain.run(inputs, outputs);
			motorSkills();
			health--;
		}
		void updateSensors(){
			inputs[0]=testVision(x-1, y)?1:0;
			inputs[1]=testVision(x+1, y)?1:0;
			inputs[2]=testVision(x, y-1)?1:0;
			inputs[3]=testVision(x, y+1)?1:0;
			inputs[4]=testVisionFood(x-1, y)?1:0;
			inputs[5]=testVisionFood(x+1, y)?1:0;
			inputs[6]=testVisionFood(x, y-1)?1:0;
			inputs[7]=testVisionFood(x, y+1)?1:0;
			inputs[8]=steps/100.0;
			inputs[9]=health/100.0;
			inputs[10]=x/20.0;
			inputs[11]=y/15.0;
		}
		boolean testVision(int x, int y){
			if(x<0)return true;
			if(y<0)return true;
			if(x>=20)return true;
			if(y>=15)return true;
			for(Animal a : animals)if(a!=this&&a.x==x&&a.y==y)return true;
			return false;
		}
		boolean testVisionFood(int x, int y){
			for(Food f : foods)if(f.x==x&&f.y==y)return true;
			return false;
		}
		void motorSkills(){
			if(outputs[0]){
				if(x>0){
					x--;
					if(locationTaken(false))x++;
				}
			}
			if(outputs[1]){
				if(x<19){
					x++;
					if(locationTaken(false))x--;
				}
			}
			if(outputs[2]){
				if(y>0){
					y--;
					if(locationTaken(false))y++;
				}
			}
			if(outputs[3]){
				if(y<14){
					y++;
					if(locationTaken(false))y--;
				}
			}
			tryEatFood();
		}
		void tryEatFood(){
			for(int i = 0; i<foods.size();){
				if(foods.get(i).x==x&&foods.get(i).y==y){
					foods.remove(i);
					health+=100;
				}else i++;
			}
		}
	}
	class Food{
		int x, y;
		Food(){
			do{ randomizeLocation();
			}while(locationTaken());
		}
		void randomizeLocation(){
			x=(int)(Math.random()*20);
			y=(int)(Math.random()*15);
		}
		boolean locationTaken(){
			for(Animal a : animals)if(a.x==x&&a.y==y)return true;
			for(Food f : foods)if(f!=this&&f.x==x&&f.y==y)return true;
			return false;
		}
	}
	static class NeuralNetwork{
		private double[][] weights;
		private double[][] hiddenValues;
		private double[] outputValues;
		private final int inputs;
		private final int hidden;
		private final int hiddenLayers;
		private final int outputs;
		NeuralNetwork(int inputs, int hidden, int hiddenLayers, int outputs){
			this.inputs=inputs;
			this.hidden=hidden;
			this.hiddenLayers=hiddenLayers;
			this.outputs=outputs;
			weights=new double[1+hiddenLayers][];
			weights[0]=new double[inputs*hidden];
			for(int i = 1; i<hiddenLayers; i++)weights[i]=new double[hidden*hidden];
			weights[hiddenLayers]=new double[outputs*hidden];
			hiddenValues=new double[hiddenLayers][hidden];
			outputValues=new double[outputs];
			randomize(weights);
		}
		NeuralNetwork(int inputs, int hidden, int hiddenLayers, int outputs, double[][] weights){
			this.inputs=inputs;
			this.hidden=hidden;
			this.hiddenLayers=hiddenLayers;
			this.outputs=outputs;
			this.weights=weights;
			hiddenValues=new double[hiddenLayers][hidden];
			outputValues=new double[outputs];
			mutate(weights);
		}
		void run(double[] in, boolean[] out){
			int c;
			for(int d = 0; d<hiddenValues.length; d++){
				boolean ends = d==0||d==hiddenLayers-1;
				if(d==0){
					c=0;
					for(int a = 0; a<hidden; a++){
						hiddenValues[d][a]=0;
						for(int b = 0; b<inputs; b++){
							hiddenValues[d][a]+=in[b]*weights[d][c];
							c++;
						}
						hiddenValues[d][a]=sigmoid(hiddenValues[d][a]);
					}
				}
				if(d==hiddenLayers-1){
					c=0;
					for(int a = 0; a<outputs; a++){
						outputValues[a]=0;
						for(int b = 0; b<hidden; b++){
							outputValues[a]+=hiddenValues[d][b]*weights[d+1][c];
							c++;
						}
						out[a]=round(outputValues[a]=sigmoid(outputValues[a]));
					}
				}
				if(!ends){
					c=0;
					for(int a = 0; a<hidden; a++){
						hiddenValues[d][a]=0;
						for(int b = 0; b<hidden; b++){
							hiddenValues[d][a]+=hiddenValues[d-1][b]*weights[d+1][c];
							c++;
						}
						hiddenValues[d][a]=sigmoid(hiddenValues[d][a]);
					}
				}
			}
		}
		static NeuralNetwork merge(NeuralNetwork a, NeuralNetwork b){
			double[][] newWeights = new double[a.weights.length][];
			for(int c = 0; c<newWeights.length; c++){
				newWeights[c]=new double[a.weights[c].length];
				for(int d = 0; d<newWeights[c].length; d++)newWeights[c][d]=(a.weights[c][d]+b.weights[c][d])*0.5;
			}
			mutate(newWeights);
			return new NeuralNetwork(a.inputs, a.hidden, a.hiddenLayers, a.outputs, newWeights);
		}
		static void mutate(double[][] weights){
			int block = (int)(Math.random()*2);
			weights[block][(int)(Math.random()*weights[block].length)]+=(Math.random()-0.5)*2;
		}
		static double sigmoid(double x){ return (1/(1+Math.pow(Math.E, -x))); }
		static boolean round(double x){ return x>=0.5?true:false; }
		static void randomize(double[][] weights){ for(int a = 0; a<weights.length; a++)for(int b = 0; b<weights[a].length; b++)weights[a][b]=(Math.random()-0.5)*20; }
	}
}