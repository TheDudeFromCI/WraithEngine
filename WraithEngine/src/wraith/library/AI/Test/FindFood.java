package wraith.library.AI.Test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import wraith.library.AI.BinaryEvolutionaryNeuralNetwork;
import wraith.library.AI.BinaryNeuralNetworkRenderer;
import wraith.library.AI.ScrollingEvolutionProgressLog;

public class FindFood{
	private JFrame frame, frame2, frame3;
	private JPanel panel;
	private BinaryNeuralNetworkRenderer nnRenderer;
	private boolean slow = true;
	private boolean paused = false;
	private long score = 0;
	private final ArrayList<Ball> balls;
	private final BinaryEvolutionaryNeuralNetwork neuralNetwork;
	private final ScrollingEvolutionProgressLog log;
	private final ArrayList<Food> foods = new ArrayList<>();
	private final Object LOCK = 0;
	private static int steps;
	private static final int FOOD_SIZE = 30;
	private static final int BALL_SIZE = 20;
	private static final int STEP_SIZE = 10;
	private static final int BALL_HEAD_SIZE = 20;
	private static final double TURN_STEP_SIZE = Math.toRadians(1);
	private static final int GENERATION_LENGTH = 1500;
	public FindFood(){
		neuralNetwork=new BinaryEvolutionaryNeuralNetwork(1, 1, 1, 3, 10, false, true, 0.8, 20, 100);
		log=new ScrollingEvolutionProgressLog(neuralNetwork.getLearningSystem(), 800);
		balls=new ArrayList<>();
		balls.add(new Ball());
		createWindows();
		new Thread(new Runnable(){
			public void run(){
				while(true){
					steps++;
					synchronized(LOCK){
						while(foods.size()<3)foods.add(new Food((int)(Math.random()*580+20), (int)(Math.random()*420+20)));
						for(Ball ball : balls)ball.update();
					}
					if(steps%GENERATION_LENGTH==0){
						neuralNetwork.score(score);
						score=0;
					}
					frame.repaint();
					frame2.repaint();
					frame3.repaint();
					if(slow){
						try{ Thread.sleep(25);
						}catch(Exception exception){}
					}
					while(paused){
						try{ Thread.sleep(1);
						}catch(Exception exception){}
					}
				}
			}
		}).start();
	}
	private void createWindows(){
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int windowOffsetX = (dimension.width-1280)/2;
		int windowOffsetY = (dimension.height-720)/4;
		frame=new JFrame();
		frame.setTitle("Find Food");
		frame.setSize(640, 480);
		frame.setResizable(false);
		frame.setLocation(windowOffsetX, windowOffsetY);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(new KeyListener(){
			public void keyTyped(KeyEvent e){}
			public void keyReleased(KeyEvent e){}
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_Q)slow=!slow;
				if(e.getKeyCode()==KeyEvent.VK_SPACE)paused=!paused;
			}
		});
		frame.add(panel=new JPanel(){
			@Override public void paint(Graphics g1){
				Graphics2D g = (Graphics2D)g1;
				g.setColor(Color.lightGray);
				g.fillRect(0, 0, getWidth(), getHeight());
				synchronized(LOCK){
					for(Food f : foods){
						g.setColor(Color.white);
						g.fillOval(f.x-FOOD_SIZE/2, f.y-FOOD_SIZE/2, FOOD_SIZE, FOOD_SIZE);
						g.setColor(Color.darkGray);
						g.drawOval(f.x-FOOD_SIZE/2, f.y-FOOD_SIZE/2, FOOD_SIZE, FOOD_SIZE);
					}
				}
				for(Ball ball : balls){
					g.setColor(ball.foodInSight?Color.magenta:Color.green);
					g.drawLine(ball.x, ball.y, ball.x+(int)Math.round(Math.cos(ball.r)*1500), ball.y+(int)Math.round(Math.sin(ball.r)*1500));
					g.setColor(Color.red);
					g.fillOval(ball.x-BALL_SIZE/2, ball.y-BALL_SIZE/2, BALL_SIZE, BALL_SIZE);
					g.setColor(Color.blue);
					g.drawLine(ball.x+(int)(Math.cos(ball.r+Math.PI/2)*BALL_SIZE/2), ball.y+(int)(Math.sin(ball.r+Math.PI/2)*BALL_SIZE/2), ball.x+(int)(Math.cos(ball.r)*BALL_HEAD_SIZE), ball.y+(int)(Math.sin(ball.r)*BALL_HEAD_SIZE));
					g.drawLine(ball.x+(int)(Math.cos(ball.r-Math.PI/2)*BALL_SIZE/2), ball.y+(int)(Math.sin(ball.r-Math.PI/2)*BALL_SIZE/2), ball.x+(int)(Math.cos(ball.r)*BALL_HEAD_SIZE), ball.y+(int)(Math.sin(ball.r)*BALL_HEAD_SIZE));
					g.drawLine(ball.x+(int)(Math.cos(ball.r-Math.PI/2)*BALL_SIZE/2), ball.y+(int)(Math.sin(ball.r-Math.PI/2)*BALL_SIZE/2), ball.x+(int)(Math.cos(ball.r+Math.PI/2)*BALL_SIZE/2), ball.y+(int)(Math.sin(ball.r+Math.PI/2)*BALL_SIZE/2));
				}
				g.setColor(Color.black);
				g.drawString("Steps: "+steps+" ("+(GENERATION_LENGTH-steps%GENERATION_LENGTH)+")", 3, 13);
				g.drawString("Score: "+score, 3, 25);
				g.dispose();
			}
		});
		frame.setVisible(true);
		frame2=new JFrame();
		frame2.setTitle("Learning Progress");
		frame2.setSize(1280, 240);
		frame2.setLocation(windowOffsetX, windowOffsetY+480);
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.add(new JPanel(){
			@Override public void paint(Graphics g1){
				Graphics2D g = (Graphics2D)g1;
				int scrolledData = (int)(getWidth()/(double)log.size()*(log.generations()%log.size()));
				GradientPaint gradientPaint1 = new GradientPaint(scrolledData, 0, Color.darkGray, getWidth()+scrolledData, 0, Color.gray);
				g.setPaint(gradientPaint1);
				g.fillRect(scrolledData, 0, getWidth(), getHeight());
				GradientPaint gradientPaint = new GradientPaint(scrolledData-getWidth(), 0, Color.darkGray, getWidth(), 0, Color.gray);
				g.setPaint(gradientPaint);
				g.fillRect(scrolledData-getWidth(), 0, getWidth(), getHeight());
				int generations = log.size();
				double horizontalSpacing = getWidth()/(double)generations;
				double height = getHeight()*0.8;
				double edgeBuffer = getHeight()*0.1;
				g.setColor(Color.red);
				for(int i = 0; i<generations-1; i++)if(log.generations()%log.size()!=i+1)g.drawLine((int)(horizontalSpacing*i), (int)((1-log.getScorePercent(i))*height+edgeBuffer), (int)(horizontalSpacing*(i+1)), (int)((1-log.getScorePercent(i+1))*height+edgeBuffer));
				g.setColor(Color.white);
				for(int i = 0; i<generations-1; i++)if(log.generations()%log.size()!=i+1)g.drawLine((int)(horizontalSpacing*i), (int)((1-log.getMaxScorePercent(i))*height+edgeBuffer), (int)(horizontalSpacing*(i+1)), (int)((1-log.getMaxScorePercent(i+1))*height+edgeBuffer));
				g.setColor(Color.orange);
				for(int i = 0; i<generations-1; i++)if(log.generations()%log.size()!=i+1)g.drawLine((int)(horizontalSpacing*i), (int)((1-log.getGenerationScorePercent(i))*height+edgeBuffer), (int)(horizontalSpacing*(i+1)), (int)((1-log.getGenerationScorePercent(i+1))*height+edgeBuffer));
				g.setColor(Color.blue);
				for(int i = 0; i<generations-1; i++)if(log.generations()%log.size()!=i+1)g.drawLine((int)(horizontalSpacing*i), (int)((1-log.getAverageScorePercent(i))*height+edgeBuffer), (int)(horizontalSpacing*(i+1)), (int)((1-log.getAverageScorePercent(i+1))*height+edgeBuffer));
				g.setColor(Color.green);
				g.drawString("Generations: "+log.generations()+" (Sib: "+neuralNetwork.getLearningSystem().getSibilingNumber()+", Real: "+(log.generations()/neuralNetwork.getLearningSystem().getSibilings())+")", 3, 13);
				g.drawString("Range: ["+log.getLowestScore()+", "+log.getHighestScore()+"]", 3, 25);
				g.drawString("Average: "+log.findAverage(), 3, 37);
				g.drawString("Learns: "+log.getLearns(), 3, 49);
				g.dispose();
			}
		});
		frame2.setVisible(true);
		frame3=new JFrame();
		frame3.setTitle("Neural Network Activity");
		frame3.setSize(640, 480);
		frame3.setLocation(windowOffsetX+640, windowOffsetY);
		frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame3.add(nnRenderer=new BinaryNeuralNetworkRenderer(neuralNetwork));
		frame3.setVisible(true);
		frame.requestFocus();
	}
	public static void main(String[] args){ new FindFood(); }
	private class Ball{
		private int x, y;
		private double r;
		private boolean[] tasks = new boolean[]{false, false, false};
		private boolean foodInSight;
		private Ball(){
			x=(int)(Math.random()*600)+20;
			y=(int)(Math.random()*400)+40;
			r=Math.random()*Math.PI*2;
		}
		private void update(){
			runSensor();
			if(tasks[0]){
				r+=TURN_STEP_SIZE;
				if(r>=Math.toRadians(360))r-=Math.toRadians(360);
			}
			if(tasks[1]){
				r-=TURN_STEP_SIZE;
				if(r<0)r+=Math.toRadians(360);
			}
			if(tasks[2]){
				int nx = (int)Math.round(x+Math.cos(r)*STEP_SIZE);
				int ny = (int)Math.round(y+Math.sin(r)*STEP_SIZE);
				if(nx>=0&&ny>=0&&nx<panel.getWidth()&&ny<panel.getHeight()){
					boolean spaceEmpty = true;
					for(Ball b : balls){
						if(b==this)continue;
						if(Math.pow(nx-b.x, 2)+Math.pow(ny-b.y, 2)<=BALL_SIZE*BALL_SIZE){
							spaceEmpty=false;
							break;
						}
					}
					if(spaceEmpty){
						x=nx;
						y=ny;
						if(x<0)x+=panel.getWidth();
						if(y<0)y+=panel.getHeight();
						if(x>=panel.getWidth())x-=panel.getWidth();
						if(y>=panel.getHeight())y-=panel.getHeight();
						Food f;
						for(int i = 0; i<foods.size(); i++){
							f=foods.get(i);
							if(distance(f.x, f.y)<(FOOD_SIZE/2+BALL_SIZE/2)*(FOOD_SIZE/2+BALL_SIZE/2)){
								foods.remove(f);
								score+=1;
								break;
							}
						}
					}
				}
			}
		}
		private void runSensor(){
			foodInSight=false;
			for(Food f : foods){
				if(canSee(f.x, f.y)){
					foodInSight=true;
					break;
				}
			}
			double[] data = new double[]{foodInSight?1:0};
			tasks=neuralNetwork.run(data);
			nnRenderer.updateRunningData(data);
		}
		private boolean canSee(int x, int y){
			double dx = Math.cos(r)*1500;
			double dy = Math.sin(r)*1500;
			double fx = this.x-x;
			double fy = this.y-y;
			double a = dot(dx, dy, dx, dy);
			double b = 2*dot(fx, fy, dx, dy);
			double c = dot(fx, fy, fx, fy)-(FOOD_SIZE/2)*(FOOD_SIZE/2);
			double discriminant = b*b-4*a*c;
			if(discriminant<0)return false;
			discriminant=Math.sqrt(discriminant);
			double t1 = (-b-discriminant)/(2*a);
			double t2 = (-b+discriminant)/(2*a);
			if(t1>=0&&t1<=1)return true;
			if(t2>=0&&t2<=1)return true;
			return false ;
		}
		private double dot(double x1, double y1, double x2, double y2){ return x1*x2+y1*y2; }
		private double distance(int x, int y){ return Math.pow(x-this.x, 2)+Math.pow(y-this.y, 2); }
	}
	private class Food{
		private int x, y;
		public Food(int x, int y){
			this.x=x;
			this.y=y;
		}
	}
}