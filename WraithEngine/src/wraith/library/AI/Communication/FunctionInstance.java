package wraith.library.AI.Communication;

import java.util.ArrayList;

class FunctionInstance{
	private FunctionMetaData metaData = new FunctionMetaData();
	private final Function function;
	private final ArrayList<FunctionInstance> children = new ArrayList<>();
	public FunctionInstance clone(){
		FunctionInstance f = new FunctionInstance(function);
		f.metaData=metaData;
		for(FunctionInstance i : children)f.addChild(i.clone());
		return f;
	}
	public FunctionInstance(Function function){ this.function=function; }
	public void addChild(FunctionInstance functionInstance){ children.add(functionInstance); }
	public void removeChild(FunctionInstance functionInstance){ children.remove(functionInstance); }
	public ArrayList<FunctionInstance> getChildren(){ return children; }
	public Function getFunction(){ return function; }
	public void runFunction(Data[] inputs){ function.runFunction(inputs, metaData); }
}