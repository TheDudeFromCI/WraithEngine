package wraith.library.AI.NodeMapLearning;

import java.util.ArrayList;
import java.util.HashMap;

public class NodeMapPath{
	private ArrayList<NodeFunction> functions;
	private HashMap<NodeFunction,NodeData[]> results = new HashMap<>();
	private boolean done;
	public NodeMapPath(ArrayList<NodeFunction> functions){ this.functions=functions; }
	public void next(){
		for(NodeFunction function : functions){
			if(results.containsKey(key))
		}
	}
	public boolean isDone(){ return done; }
}