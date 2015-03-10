package wraith.library.AI.NeuralNetworks.FeedForward.Neurons;

import wraith.library.AI.NeuralNetworks.BackProbagationNeuron;
import wraith.library.AI.NeuralNetworks.ChildHolderNeuron;
import wraith.library.AI.NeuralNetworks.NeuralNetwork;
import wraith.library.AI.NeuralNetworks.Neuron;
import wraith.library.AI.NeuralNetworks.ParentHolderNeuron;

public class HiddenNeuron implements ParentHolderNeuron, ChildHolderNeuron, BackProbagationNeuron{
	private Neuron[] parents = new Neuron[0];
	private ParentHolderNeuron[] children = new ParentHolderNeuron[0];
	private double[] childWeights = new double[0];
	private double[] recived = new double[0];
	private NeuralNetwork network;
	private double backProbError;
	private double value;
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
		backProbError=0;
		value=0;
		for(int i = 0; i<recived.length; i++)value+=recived[i];
		value=network.getActiveFunction().processData(value);
		for(int i = 0; i<children.length; i++)children[i].reciveData(children[i].getIndexOfParent(this), value*childWeights[i]);
	}
	public int getIndexOfParent(Neuron n){
		for(int i = 0; i<parents.length; i++)if(parents[i]==n)return i;
		return -1;
	}
	public int getIndexOfChild(Neuron n){
		for(int i = 0; i<children.length; i++)if(children[i]==n)return i;
		return -1;
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
	public void forwardProb(double learningRate){
		ChildHolderNeuron h;
		for(Neuron p : parents){
			h=(ChildHolderNeuron)p;
			h.updateChildWeight(h.getIndexOfChild(this), learningRate*backProbError*network.getActiveFunction().derivitive(value));
		}
	}
	public void updateChildWeight(int childIndex, double childDelta){ childWeights[childIndex]+=childDelta*value; }
	public void reciveBackProb(double error){ backProbError+=error; }
	public HiddenNeuron(NeuralNetwork network){ this.network=network; }
	public void reciveData(int parentIndex, double value){ recived[parentIndex]=value; }
	public Neuron[] getParents(){ return parents; }
	public Neuron[] getChildren(){ return children; }
	public double[] getChildWeights(){ return childWeights; }
	public void setChildWeights(double[] weights){ childWeights=weights; }
}