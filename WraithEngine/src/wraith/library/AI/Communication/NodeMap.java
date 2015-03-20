package wraith.library.AI.Communication;

import java.util.ArrayList;

public class NodeMap{
	private final int generationSize;
	private final Function[] functions;
	private final ArrayList<FunctionInstance> map = new ArrayList<>();
	public NodeMap(Function[] functions, int generationSize){
		this.functions=functions;
		this.generationSize=generationSize;
	}
	public NodeMapInstance nextInstance(){
		FunctionInstance[] f = new FunctionInstance[map.size()];
		for(int i = 0; i<f.length; i++)f[i]=map.get(i).clone();
		return new NodeMapInstance(this, f);
	}
	public int getGenerationSize(){ return generationSize; }
	public Function[] getFunctions(){ return functions; }
}