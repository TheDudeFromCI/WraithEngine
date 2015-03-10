package wraith.library.AI.NeuralNetworks.FeedForward.Neurons;

import wraith.library.AI.NeuralNetworks.ChildHolderNeuron;
import wraith.library.AI.NeuralNetworks.NeuralNetwork;
import wraith.library.AI.NeuralNetworks.Neuron;
import wraith.library.AI.NeuralNetworks.ParentHolderNeuron;

public class InputNeuron implements ChildHolderNeuron{
	private ParentHolderNeuron[] children = new ParentHolderNeuron[0];
	private double[] childWeights = new double[0];
	private double value;
	private NeuralNetwork network;
	public void addChild(Neuron n){
		ParentHolderNeuron[] a = new ParentHolderNeuron[children.length+1];
		double[] b = new double[a.length];
		for(int i = 0; i<=children.length; i++){
			if(i==children.length){
				a[i]=(ParentHolderNeuron)n;
				b[i]=Math.random()-0.5;
			}else{
				a[i]=children[i];
				b[i]=childWeights[i];
			}
		}
		children=a;
		childWeights=b;
	}
	public void run(){
		value=network.getActiveFunction().processData(value);
		for(int i = 0; i<children.length; i++)children[i].reciveData(children[i].getIndexOfParent(this), value*childWeights[i]);
	}
	public int getIndexOfChild(Neuron n){
		for(int i = 0; i<children.length; i++)if(children[i]==n)return i;
		return -1;
	}
	public void updateChildWeight(int childIndex, double childDelta){ childWeights[childIndex]+=childDelta*value; }
	public InputNeuron(NeuralNetwork network){ this.network=network; }
	public void setValue(double value){ this.value=value; }
	public Neuron[] getChildren(){ return children; }
	public double[] getChildWeights(){ return childWeights; }
	public void setChildWeights(double[] weights){ childWeights=weights; }
}