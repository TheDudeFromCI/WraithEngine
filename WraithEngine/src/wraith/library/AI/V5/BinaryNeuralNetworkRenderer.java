package wraith.library.AI.V5;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class BinaryNeuralNetworkRenderer extends JPanel{
	private double[] inputData;
	private double[][] weights;
	private double[][] data;
	private boolean[] outputData;
	private double highestWeight, lowestWeight;
	private final BinaryEvolutionaryNeuralNetwork neuralNetwork;
	private static final int NEURON_SIZE = 20;
	public BinaryNeuralNetworkRenderer(BinaryEvolutionaryNeuralNetwork neuralNetwork){
		this.neuralNetwork=neuralNetwork;
		updateRunningData(new double[neuralNetwork.getInputs()]);
	}
	@Override public void paint(Graphics g1){
		Graphics2D g = (Graphics2D)g1;
		g.setColor(Color.gray);
		g.fillRect(0, 0, getWidth(), getHeight());
		renderWeights(g);
		renderInputs(g);
		for(int i = 0; i<neuralNetwork.getHiddenLayers(); i++)renderHiddenLayer(g, i);
		renderOutputs(g);
		g.setColor(Color.black);
		g.drawString("Weights: ["+((int)(lowestWeight*10000)/10000.0)+", "+((int)(highestWeight*10000)/10000.0)+"]", 3, getHeight()-15);
		g.drawString("Learning {R: "+neuralNetwork.getLearningSystem().getRandomnessFactor()+", O: "+neuralNetwork.getLearningSystem().getOutfittingRate()+"}", 3, getHeight()-3);
		g.dispose();
	}
	private void renderWeights(Graphics2D g){
		for(int a = 0; a<weights.length; a++){
			if(a==0){
				int weightIndex = 0;
				for(int b = 0; b<=neuralNetwork.getInputs(); b++){
					for(int c = 0; c<neuralNetwork.getHiddenLayerSize(); c++){
						renderWeight(g, getInputNeuronX(b), getInputNeuronY(), getHiddenNeuronX(c), getHiddenNeuronY(0), weights[a][weightIndex]);
						weightIndex++;
					}
				}
			}else if(a==weights.length-1){
				int weightIndex = 0;
				for(int b = 0; b<=neuralNetwork.getHiddenLayerSize(); b++){
					for(int c = 0; c<neuralNetwork.getOutputs(); c++){
						renderWeight(g, getHiddenNeuronX(b), getHiddenNeuronY(a-1), getOutputNeuronX(c), getOutputNeuronY(), weights[a][weightIndex]);
						weightIndex++;
					}
				}
			}else{
				int weightIndex = 0;
				for(int b = 0; b<=neuralNetwork.getHiddenLayerSize(); b++){
					for(int c = 0; c<neuralNetwork.getHiddenLayerSize(); c++){
						renderWeight(g, getHiddenNeuronX(b), getHiddenNeuronY(a-1), getHiddenNeuronX(c), getHiddenNeuronY(a), weights[a][weightIndex]);
						weightIndex++;
					}
				}
			}
		}
	}
	private void calculateWeightCaps(){
		highestWeight=0;
		lowestWeight=0;
		for(int a = 0; a<weights.length; a++){
			for(int b = 0; b<weights[a].length; b++){
				if(weights[a][b]>highestWeight)highestWeight=weights[a][b];
				if(weights[a][b]<lowestWeight)lowestWeight=weights[a][b];
			}
		}
	}
	public void updateRunningData(double[] inputData){
		this.inputData=inputData;
		outputData=neuralNetwork.run(inputData);
		weights=neuralNetwork.getWeights();
		data=neuralNetwork.getData();
		calculateWeightCaps();
	}
	private void renderWeight(Graphics2D g, int x, int y, int x2, int y2, double w){
		if(w>0){
			float p = 1-(float)(w/highestWeight);
			float[] c = new float[3];
			float[] hsb = Color.RGBtoHSB(0, 0, 0, null);
			float[] hsb2 = Color.RGBtoHSB(0, 255, 255, null);
			c[0]=hsb[0]*p+hsb2[0]*(1-p);
			c[1]=hsb[1]*p+hsb2[1]*(1-p);
			c[2]=hsb[2]*p+hsb2[2]*(1-p);
			g.setColor(Color.getHSBColor(c[0], c[1], c[2]));
		}else{
			float p = 1-(float)(w/lowestWeight);
			float[] c = new float[3];
			float[] hsb = Color.RGBtoHSB(0, 0, 0, null);
			float[] hsb2 = Color.RGBtoHSB(255, 255, 0, null);
			c[0]=hsb[0]*p+hsb2[0]*(1-p);
			c[1]=hsb[1]*p+hsb2[1]*(1-p);
			c[2]=hsb[2]*p+hsb2[2]*(1-p);
			g.setColor(Color.getHSBColor(c[0], c[1], c[2]));
		}
		g.drawLine(x, y, x2, y2);
	}
	private void renderInputs(Graphics2D g){ for(int i = 0; i<=neuralNetwork.getInputs(); i++)renderNeuron(g, getInputNeuronX(i), getInputNeuronY(), i==neuralNetwork.getInputs()?Color.cyan:calculateNeuronColor((float)inputData[i])); }
	private void renderHiddenLayer(Graphics2D g, int layer){ for(int i = 0; i<=neuralNetwork.getHiddenLayerSize(); i++)renderNeuron(g, getHiddenNeuronX(i), getHiddenNeuronY(layer), i==neuralNetwork.getHiddenLayerSize()?Color.cyan:calculateNeuronColor((float) data[layer][i])); }
	private int getInputNeuronX(int index){ return (int)(getWidth()/(neuralNetwork.getInputs()+2.0)*(index+1)); }
	private int getInputNeuronY(){ return (int)(getHeight()/(neuralNetwork.getHiddenLayers()+3.0)); }
	private int getHiddenNeuronX(int index){ return (int)(getWidth()/(neuralNetwork.getHiddenLayerSize()+2.0)*(index+1)); }
	private int getHiddenNeuronY(int layer){ return (int)(getHeight()/(neuralNetwork.getHiddenLayers()+3.0)*(layer+2)); }
	private int getOutputNeuronX(int index){ return (int)(getWidth()/(neuralNetwork.getOutputs()+1.0)*(index+1)); }
	private int getOutputNeuronY(){ return (int)(getHeight()/(neuralNetwork.getHiddenLayers()+3.0)*(neuralNetwork.getHiddenLayers()+2)); }
	private void renderOutputs(Graphics2D g){ for(int i = 0; i<neuralNetwork.getOutputs(); i++)renderNeuron(g, getOutputNeuronX(i), getOutputNeuronY(), outputData[i]?Color.yellow:Color.lightGray); }
	private static void renderNeuron(Graphics2D g, int x, int y, Color color){
		g.setColor(color);
		g.fillOval(x-NEURON_SIZE/2, y-NEURON_SIZE/2, NEURON_SIZE, NEURON_SIZE);
	}
	private static Color calculateNeuronColor(float p){
		p=1-p;
		float[] c = new float[3];
		float[] hsb = Color.RGBtoHSB(255, 0, 0, null);
		float[] hsb2 = Color.RGBtoHSB(0, 255, 0, null);
		c[0]=hsb[0]*p+hsb2[0]*(1-p);
		c[1]=hsb[1]*p+hsb2[1]*(1-p);
		c[2]=hsb[2]*p+hsb2[2]*(1-p);
		return Color.getHSBColor(c[0], c[1], c[2]);
	}
}