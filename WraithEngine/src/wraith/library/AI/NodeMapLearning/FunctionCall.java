package wraith.library.AI.NodeMapLearning;

public class FunctionCall{
	private FunctionCall[] parents;
	private int[][] requiredData;
	private NodeFunction function;
	private NodeData[] data;
	public FunctionCall(NodeFunction function, FunctionCall[] parents, int[][] requiredData){
		this.function=function;
		this.parents=parents;
		this.requiredData=requiredData;
	}
	public boolean canRun(){
		if(parents==null)return true;
		for(FunctionCall f : parents)if(!f.hasRan())return false;
		return true;
	}
	public void run(){
		data=function.run(compileData());
		if(data==null)data=new NodeData[0];
	}
	private NodeData[] compileData(){
		int inputSize = 0;
		for(int a = 0; a<requiredData.length; a++)inputSize+=requiredData[a].length;
		NodeData[] d = new NodeData[inputSize];
		int pos = 0;
		for(int a = 0; a<requiredData.length; a++){
			for(int b = 0; b<requiredData[a].length; b++){
				d[pos]=parents[a].data[b];
				pos++;
			}
		}
		return d;
	}
	public boolean hasRan(){ return data!=null; }
}