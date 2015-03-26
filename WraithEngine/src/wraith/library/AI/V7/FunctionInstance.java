package wraith.library.AI.V7;

import java.util.ArrayList;

public class FunctionInstance{
	private int hasRun;
	private final Data[] inputs;
	private final Data[] outputs;
	private final Function function;
	private final FunctionInstance[] children;
	private final FunctionMetaData meta = new FunctionMetaData();
	public FunctionInstance(Function function){
		this.function=function;
		children=new FunctionInstance[function.getOutputs().length];
		inputs=new Data[function.getInputs().length];
		outputs=new Data[function.getOutputs().length];
	}
	public void run(){
		function.run(inputs, outputs, meta);
		for(int i = 0; i<children.length; i++){
			if(children[i]==null)continue;
			children[i].inputs[i]=outputs[i];
			children[i].hasRun++;
			if(children[i].hasRun==children[i].inputs.length)children[i].run();
		}
	}
	public void resetAllRuns(){
		hasRun=0;
		for(int i = 0; i<children.length; i++)if(children[i]!=null)children[i].resetAllRuns();
	}
	public int getMaximumDepth(int current){
		int maxDepth = 0;
		for(int i = 0; i<children.length; i++){
			if(children[i]==null)continue;
			maxDepth=Math.max(maxDepth, children[i].getMaximumDepth(current+1));
		}
		return maxDepth==0?current+1:maxDepth;
	}
	public void addToList(ArrayList<FunctionInstance> functionInstances){
		functionInstances.add(this);
		for(int i = 0; i<children.length; i++){
			if(children[i]==null)continue;
			children[i].addToList(functionInstances);
		}
	}
	public Function getFunction(){ return function; }
	public FunctionInstance[] getChildren(){ return children; }
}