package wraith.library.AI.NeuralNetworks.FeedForward;

import wraith.library.AI.NeuralNetworks.ActiveFunction;
import wraith.library.AI.NeuralNetworks.NeuralNetwork;
import wraith.library.AI.NeuralNetworks.SigmoidFunction;
import wraith.library.AI.NeuralNetworks.FeedForward.Neurons.BiasNeuron;
import wraith.library.AI.NeuralNetworks.FeedForward.Neurons.HiddenNeuron;
import wraith.library.AI.NeuralNetworks.FeedForward.Neurons.InputNeuron;
import wraith.library.AI.NeuralNetworks.FeedForward.Neurons.OutputNeuron;

public class FeedForwardNeuralNetwork implements NeuralNetwork{
	private int inputs, hiddenLayers, hiddenLayerSize, outputs;
	private InputNeuron[] inputNeurons;
	private BiasNeuron[] biasNeurons;
	private HiddenNeuron[][] hiddenNeurons;
	private OutputNeuron[] outputNeurons;
	private ActiveFunction function = new SigmoidFunction();
	public FeedForwardNeuralNetwork(int inputs, int hiddenLayers, int hiddenLayerSize, int outputs){
		this.inputs=inputs;
		this.hiddenLayers=hiddenLayers;
		this.hiddenLayerSize=hiddenLayerSize;
		this.outputs=outputs;
		createNeurons();
	}
	public void run(double[] in, double[] out){
		for(int i = 0; i<inputs; i++){
			inputNeurons[i].setValue(in[i]);
			inputNeurons[i].run();
		}
		for(int i = 0; i<=hiddenLayers; i++)biasNeurons[i].run();
		for(int a = 0; a<hiddenLayers; a++)for(int b = 0; b<hiddenLayerSize; b++)hiddenNeurons[a][b].run();
		for(int i = 0; i<outputs; i++){
			outputNeurons[i].run();
			out[i]=outputNeurons[i].getValue();
		}
	}
	public void train(double[] in, double[] out){
		double[] r = new double[outputs];
		run(in, r);
		for(int i = 0; i<outputs; i++){
			outputNeurons[i].reciveBackProb(r[i]-out[i]);
			outputNeurons[i].backProb();
		}
		for(int a = hiddenLayers-1; a>=0; a--)for(int b = 0; b<hiddenLayerSize; b++)hiddenNeurons[a][b].backProb();
		for(int a = 0; a<hiddenLayers; a++)for(int b = 0; b<hiddenLayerSize; b++)hiddenNeurons[a][b].forwardProb(0.25);
		for(int i = 0; i<outputs; i++)outputNeurons[i].forwardProb(0.25);
	}
	private void createNeurons(){
		inputNeurons=new InputNeuron[inputs];
		biasNeurons=new BiasNeuron[hiddenLayers+1];
		hiddenNeurons=new HiddenNeuron[hiddenLayers][hiddenLayerSize];
		outputNeurons=new OutputNeuron[outputs];
		for(int i = 0; i<inputs; i++)inputNeurons[i]=new InputNeuron(this);
		for(int i = 0; i<biasNeurons.length; i++)biasNeurons[i]=new BiasNeuron();
		for(int a = 0; a<hiddenLayers; a++){
			for(int b = 0; b<hiddenLayerSize; b++){
				hiddenNeurons[a][b]=new HiddenNeuron(this);
				if(a==0){
					for(int c = 0; c<inputs; c++){
						hiddenNeurons[a][b].addParent(inputNeurons[c]);
						inputNeurons[c].addChild(hiddenNeurons[a][b]);
					}
				}else{
					for(int c = 0; c<hiddenLayerSize; c++){
						hiddenNeurons[a][b].addParent(hiddenNeurons[a-1][c]);
						hiddenNeurons[a-1][c].addChild(hiddenNeurons[a][b]);
					}
				}
				hiddenNeurons[a][b].addParent(biasNeurons[a]);
				biasNeurons[a].addChild(hiddenNeurons[a][b]);
			}
		}
		int outputParentLayerSize = hiddenLayers==0?inputs:hiddenLayerSize;
		for(int i = 0; i<outputs; i++){
			outputNeurons[i]=new OutputNeuron(this);
			for(int a = 0; a<outputParentLayerSize; a++){
				outputNeurons[i].addParent(hiddenLayerSize==0?inputNeurons[a]:hiddenNeurons[hiddenLayers-1][a]);
				if(hiddenLayerSize==0)inputNeurons[a].addChild(outputNeurons[i]);
				else hiddenNeurons[hiddenLayers-1][a].addChild(outputNeurons[i]);
			}
			outputNeurons[i].addParent(biasNeurons[hiddenLayers]);
			biasNeurons[hiddenLayers].addChild(outputNeurons[i]);
		}
	}
	public ActiveFunction getActiveFunction(){ return function; }
	public void setActiveFunction(ActiveFunction function){ this.function=function; }
	public int getInputs(){ return inputs; }
	public int getOutputs(){ return outputs; }
}