package wraith.library.AI.NodeMapLearning;

import java.util.ArrayList;

public class NodeMap{
	private boolean isCreated = false;
	private ArrayList<NodeFunction> functions = new ArrayList<>();
	private Object lock = false;
	public void addNodeFunction(NodeFunction nodeFunction){
		synchronized(lock){
			if(isCreated)throw new IllegalStateException("Node Map already created!");
			functions.add(nodeFunction);
		}
	}
	public void createMap(){
		synchronized(lock){
			if(isCreated)throw new IllegalStateException("Node Map already created!");
			isCreated=true;
			create();
		}
	}
	public void runStep(){
		synchronized(lock){ prepareNextStep(); }
		//TODO Run step.
	}
	private void prepareNextStep(){
		//TODO Prepare next step.
	}
	private void create(){
		//TODO Create the map. 
	}
}