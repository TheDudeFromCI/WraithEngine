package wraith.library.AI.V5;

import java.util.ArrayList;
import wraith.library.AI.V5.BinaryNeuralNetworkTrainingMatrix.Data;

public class BinaryEvolutionaryNeuralNetwork{
	private double[] lastInput;
	private double[][] weights;
	private double[][] data;
	private int weightLinkCount;
	private final int trainingPasses;
	private final BinaryNeuralNetworkTrainingMatrix matrix;
	private final EvolutionaryLearningSystem learningSystem;
	private final BasicEvolutionProgressLog progressLog;
	private final int inputs, hiddenLayers, hiddenLayerSize, outputs;
	public BinaryEvolutionaryNeuralNetwork(int inputs, int hiddenLayers, int hiddenLayerSize, int outputs, double randomnessFactor, boolean singleRandom, boolean repetativeGuesses, double outfittingRate, int sibilingCount, int trainingPasses){
		this.inputs=inputs;
		this.hiddenLayers=hiddenLayers;
		this.hiddenLayerSize=hiddenLayerSize;
		this.outputs=outputs;
		this.trainingPasses=trainingPasses;
		weights=new double[1+hiddenLayers][];
		data=new double[hiddenLayers][hiddenLayerSize];
		for(int i = 0; i<=hiddenLayers; i++){
			if(i==0)weights[i]=new double[(inputs+1)*hiddenLayerSize];
			else if(i==hiddenLayers)weights[i]=new double[(hiddenLayerSize+1)*outputs];
			else weights[i]=new double[(hiddenLayerSize+1)*hiddenLayerSize];
			weightLinkCount+=weights[i].length;
		}
		learningSystem=new EvolutionaryLearningSystem(weightLinkCount, randomnessFactor, singleRandom, repetativeGuesses, outfittingRate, sibilingCount, true);
		progressLog=new BasicEvolutionProgressLog(learningSystem);
		matrix=new BinaryNeuralNetworkTrainingMatrix();
		learningSystem.addGenerationListener(new GenerationListener(){
			private boolean knowsFirstScore;
			private double firstScore;
			private final ArrayList<Double> increases = new ArrayList<>();
			public void run(double score, boolean current, double increase){
				if(current){
					if(!knowsFirstScore)firstScore=score;
					else{
						increases.add(increase);
						matrix.addData(lastInput, BinaryEvolutionaryNeuralNetwork.this.run(lastInput), 1);
						updateCertainties(score);
					}
				}
			}
			private void updateCertainties(double score){
				double range = score-firstScore;
				ArrayList<Data> data = matrix.getData();
				for(int i = 0; i<data.size(); i++)data.get(i).setCertainty(increases.get(i)/range);
			}
		});
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
	public void score(double score){
		learningSystem.score(score);
		assignWeights();
		train(matrix, trainingPasses);
	}
	public boolean[] run(double[] in){
		lastInput=new double[in.length];
		for(int i = 0; i<in.length; i++)lastInput[i]=in[i];
		double[] lastData = new double[inputs+1];
		for(int a = 0; a<inputs; a++)lastData[a]=in[a];
		lastData[inputs]=1;
		for(int a = 0; a<=hiddenLayers; a++){
			lastData=compileLayer(lastData, a==hiddenLayers?outputs:hiddenLayerSize, a);
			if(a<hiddenLayers)for(int b = 0; b<hiddenLayerSize; b++)data[a][b]=lastData[b];
		}
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
		ArrayList<Data> data = matrix.getData();
		EvolutionaryLearningSystem l = new EvolutionaryLearningSystem(weightLinkCount, 10, false, true, 0.8, 1, false);
		double[][] preWeights = new double[weights.length][];
		for(int a = 0; a<weights.length; a++){
			preWeights[a]=new double[weights[a].length];
			for(int b = 0; b<preWeights[a].length; b++)preWeights[a][b]=weights[a][b];
		}
		for(int i = 0; i<passes; i++){
			double[] out = l.next();
			int index = 0;
			for(int j = 0; j<=hiddenLayers; j++){
				for(int a = 0; a<weights[j].length; a++){
					weights[j][a]=out[index]+preWeights[j][a];
					index++;
				}
			}
			l.score(calculateScore(data));
		}
	}
	private int calculateScore(ArrayList<Data> data){
		int l = 0;
		for(Data d : data)l-=calculateError(d.getIn(), d.getOut())*d.getCertainty();
		return l;
	}
	private double calculateError(double[] in, boolean[] out){
		boolean[] d = run(in);
		int i = 0;
		for(int a = 0; a<d.length; a++)if(d[a]!=out[a])i++;
		return i/(double)d.length;
	}
	public int getInputs(){ return inputs; }
	public int getHiddenLayers(){ return hiddenLayers; }
	public int getHiddenLayerSize(){ return hiddenLayerSize; }
	public int getOutputs(){ return outputs; }
	public BasicEvolutionProgressLog getProgressLog(){ return progressLog; }
	public EvolutionaryLearningSystem getLearningSystem(){ return learningSystem; }
	public double[][] getWeights(){ return weights; }
	public double[][] getData(){ return data; }
	private static double activeFunction(double d){ return -0.5<d&&d<0.5?0:(d>0?1:-1); }
//	private static double activeFunction(double d){ return (1/(1+Math.pow(Math.E, -d))); }
}