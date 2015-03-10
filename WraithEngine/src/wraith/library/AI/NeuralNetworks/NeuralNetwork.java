package wraith.library.AI.NeuralNetworks;

public interface NeuralNetwork{
	public void run(double[] in, double[] out);
	public void train(double[] in, double[] out);
	public ActiveFunction getActiveFunction();
	public void setActiveFunction(ActiveFunction function);
	public int getInputs();
	public int getOutputs();
}