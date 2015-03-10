package wraith.library.AI.NeuralNetworks;

public interface BackProbagationNeuron{
	public void backProb();
	public void reciveBackProb(double error);
	public void forwardProb(double learningRate);
}