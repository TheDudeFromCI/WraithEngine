package wraith.library.AI.NeuralNetworks;

public interface ParentHolderNeuron extends Neuron{
	public Neuron[] getParents();
	public void addParent(Neuron n);
	public void reciveData(int parentIndex, double value);
	public int getIndexOfParent(Neuron n);
}