package wraith.library.AI.NeuralNetworks.FeedForward.Neurons;

import wraith.library.AI.NeuralNetworks.BackProbagationNeuron;
import wraith.library.AI.NeuralNetworks.ChildHolderNeuron;
import wraith.library.AI.NeuralNetworks.NeuralNetwork;
import wraith.library.AI.NeuralNetworks.Neuron;
import wraith.library.AI.NeuralNetworks.ParentHolderNeuron;

public class OutputNeuron implements ParentHolderNeuron, BackProbagationNeuron{
	private Neuron[] parents = new Neuron[0];
	private double[] recived = new double[0];
	private double value;
	private NeuralNetwork network;
	private double backProbError;
	public void addParent(Neuron n){
		Neuron[] a = new Neuron[parents.length+1];
		for(int i = 0; i<=parents.length; i++){
			if(i==parents.length)a[i]=n;
			else a[i]=parents[i];
		}
		parents=a;
		recived=new double[a.length];
	}
	public void run(){
		value=0;
		for(int i = 0; i<recived.length; i++)value+=recived[i];
		value=network.getActiveFunction().processData(value);
	}
	public void backProb(){
		HiddenNeuron h;
		for(Neuron p : parents){
			if(p instanceof HiddenNeuron){
				h=(HiddenNeuron)p;
				h.reciveBackProb(backProbError*h.getChildWeights()[h.getIndexOfChild(this)]);
			}
		}
	}
	public int getIndexOfParent(Neuron n){
		for(int i = 0; i<parents.length; i++)if(parents[i]==n)return i;
		return -1;
	}
	public void forwardProb(double learningRate){
		ChildHolderNeuron h;
		for(Neuron p : parents){
			h=(ChildHolderNeuron)p;
			h.updateChildWeight(h.getIndexOfChild(this), learningRate*backProbError*network.getActiveFunction().derivitive(value));
		}
	}
	public OutputNeuron(NeuralNetwork network){ this.network=network; }
	public void reciveData(int parentIndex, double value){ recived[parentIndex]=value; }
	public Neuron[] getParents(){ return parents; }
	public double getValue(){ return value; }
	public void reciveBackProb(double error){ backProbError=error; }
}