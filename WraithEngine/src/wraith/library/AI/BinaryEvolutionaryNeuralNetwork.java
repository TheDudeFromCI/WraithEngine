package wraith.library.AI;

import java.util.HashMap;

public class BinaryEvolutionaryNeuralNetwork{
	private double[][] weights;
	private final EvolutionaryLearningSystem learningSystem;
	private final BasicEvolutionProgressLog progressLog;
	private final int inputs, hiddenLayers, hiddenLayerSize, outputs;
	public BinaryEvolutionaryNeuralNetwork(int inputs, int hiddenLayers, int hiddenLayerSize, int outputs, double randomnessFactor){
		this.inputs=inputs;
		this.hiddenLayers=hiddenLayers;
		this.hiddenLayerSize=hiddenLayerSize;
		this.outputs=outputs;
		weights=new double[1+hiddenLayers][];
		int weightLinkCount = 0;
		for(int i = 0; i<=hiddenLayers; i++){
			if(i==0)weights[i]=new double[(inputs+1)*hiddenLayerSize];
			else if(i==hiddenLayers)weights[i]=new double[(hiddenLayerSize+1)*outputs];
			else weights[i]=new double[(hiddenLayerSize+1)*hiddenLayerSize];
			weightLinkCount+=weights[i].length;
		}
		learningSystem=new EvolutionaryLearningSystem(weightLinkCount, randomnessFactor, true);
		progressLog=new BasicEvolutionProgressLog(learningSystem);
		assignWeights();
	}
	private void assignWeights(){
		double[] values = learningSystem.next();
		int index = 0;
		for(int i = 0; i<=hiddenLayers; i++){
			for(int a = 0; a<weights[i].length; a++){
				weights[i][a]=values[index];
				index++;
			}
		}
	}
	public void score(long score){
		learningSystem.score(score);
		assignWeights();
	}
	public boolean[] run(double[] in){
		double[] lastData = new double[inputs+1];
		for(int a = 0; a<inputs; a++)lastData[a]=in[a];
		lastData[inputs]=1;
		for(int a = 0; a<=hiddenLayers; a++)lastData=compileLayer(lastData, a==hiddenLayers?outputs:hiddenLayerSize, a);
		boolean[] outputData = new boolean[lastData.length];
		for(int i = 0; i<outputData.length; i++)outputData[i]=Math.round(lastData[i])==1;
		return outputData;
	}
	private double[] compileLayer(double[] lastData, int layerSize, int layer){
		double[] out = new double[layerSize+(layer==hiddenLayers?0:1)];
		int weightIndex = 0;
		for(int a = 0; a<layerSize; a++){
			for(int b = 0; b<lastData.length; b++){
				out[a]+=lastData[b]*weights[layer][weightIndex];
				weightIndex++;
			}
			out[a]=activeFunction(out[a]);
		}
		if(layer!=hiddenLayers)out[out.length-1]=1;
		return out;
	}
	public void train(BinaryNeuralNetworkTrainingMatrix matrix, int passes){
		HashMap<double[],boolean[]> data = matrix.getData();
		for(int i = 0; i<passes; i++)score(calculateScore(data));
	}
	private long calculateScore(HashMap<double[],boolean[]> data){
		long l = 0;
		for(double[] a : data.keySet())l-=calculateError(a, data.get(a));
		return l;
	}
	private int calculateError(double[] in, boolean[] out){
		boolean[] d = run(in);
		int i = 0;
		for(int a = 0; a<d.length; a++)if(d[a]!=out[a])i++;
		return i;
	}
	public int getInputs(){ return inputs; }
	public int getHiddenLayers(){ return hiddenLayers; }
	public int getHiddenLayerSize(){ return hiddenLayerSize; }
	public int getOutputs(){ return outputs; }
	public BasicEvolutionProgressLog getProgressLog(){ return progressLog; }
	private static double activeFunction(double d){ return 1/(1+Math.exp(-d)); }
}