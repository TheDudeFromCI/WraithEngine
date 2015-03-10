package wraith.library.AI.NeuralNetworks;

public interface ChildHolderNeuron extends Neuron{
	public Neuron[] getChildren();
	public void addChild(Neuron n);
	public double[] getChildWeights();
	public void setChildWeights(double[] weights);
	public int getIndexOfChild(Neuron n);
	public void updateChildWeight(int childIndex, double childDelta);
}