package wraith.library.AI.V7;

import java.util.ArrayList;

public class NodeMap{
	private final ArrayList<FunctionInstance> net = new ArrayList<>(); 
	private final TrainingMatrix matrix;
	private final Function[] functions;
	public NodeMap(Function[] functions, TrainingMatrix matrix){
		this.functions=functions;
		this.matrix=matrix;
		ArrayList<Integer> inputs = findInputFunctions();
		net.add(new FunctionInstance(functions[inputs.get((int)(Math.random()*inputs.size()))]));
	}
	private ArrayList<Integer> findInputFunctions(){
		ArrayList<Integer> inputs = new ArrayList<>();
		for(int i = 0; i<functions.length; i++)if(functions[i].getInputs().length==0)inputs.add(i);
		return inputs;
	}
	public void score(Score[] scores){
		ArrayList<FunctionConnection> connections = new ArrayList<>();
		ArrayList<FunctionInstance> allFunctions = getAllFunctionInstances();
		int maxDepth = getMaximumDepth();
		for(int i = 0; i<maxDepth; i++)for(FunctionInstance f : allFunctions)findConnections(connections, f, i);
		for(FunctionConnection c : connections)matrix.addConnection(c, scores);
	}
	public void evolve(){
		ArrayList<EvolutionStep> choices = new ArrayList<>();
		//TODO Find all possible steps the ai can take to evolve, and pick the one with the highest chance of success.
		for(Function f : functions)addAllPlacements(choices, f);
		for(FunctionInstance f : getAllFunctionInstances()){
			for(int i = 0; i<f.getChildren().length; i++)if(f.getChildren()[i]!=null)continue;
			choices.add(new EvolutionStep(f));
		}
	}
	private void addAllPlacements(ArrayList<EvolutionStep> choices, Function f){
		//TODO Find all placements where this function can be added, and save them to this list.
	}
	private int getMaximumDepth(){
		int maxDepth = 0;
		for(FunctionInstance functionInstance : net)maxDepth=Math.max(maxDepth, functionInstance.getMaximumDepth(0));
		return maxDepth;
	}
	private ArrayList<FunctionInstance> getAllFunctionInstances(){
		ArrayList<FunctionInstance> list = new ArrayList<>();
		for(FunctionInstance f : net)f.addToList(list);
		return list;
	}
	private void findConnections(ArrayList<FunctionConnection> connections, FunctionInstance f, int length){
		if(f.getMaximumDepth(0)<length)return;
		if(length==1){
			connections.add(new FunctionConnection(new int[getIndexOf(f.getFunction())], new int[0]));
			return;
		}
		addConnection(connections, f, 0, length-1, new int[length], new int[length-1]);
	}
	private void addConnection(ArrayList<FunctionConnection> connections, FunctionInstance f, int depth, int length, int[] functions, int[] childPositions){
		if(f==null)return;
		functions[depth]=getIndexOf(f.getFunction());
		if(depth<length){
			for(int i = 0; i<f.getChildren().length; i++){
				childPositions[depth]=i;
				addConnection(connections, f.getChildren()[i], depth+1, length, functions, childPositions);
			}
			return;
		}
		int[] a = new int[functions.length];
		int[] b = new int[childPositions.length];
		for(int i = 0; i<a.length; i++)a[i]=functions[i];
		for(int i = 0; i<b.length; i++)b[i]=childPositions[i];
		connections.add(new FunctionConnection(a, b));
	}
	private int getIndexOf(Function f){
		for(int i = 0; i<functions.length; i++)if(functions[i]==f)return i;
		return -1;
	}
	public void run(){
		for(FunctionInstance functionInstance : net)functionInstance.run();
		for(FunctionInstance functionInstance : net)functionInstance.resetAllRuns();
	}
}