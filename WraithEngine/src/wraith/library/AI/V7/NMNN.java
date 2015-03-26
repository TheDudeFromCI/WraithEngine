package wraith.library.AI.V7;

/**
 * Node Map Neural Network
 * @author TheDudeFromCI
 */
public class NMNN{
	private final Function[] functions;
	private final TrainingMatrix matrix = new TrainingMatrix();
	private final NodeMap mainNet;
	public NMNN(Function[] functions){
		this.functions=functions;
		mainNet=new NodeMap(functions, matrix);
	}
	public void runRandomizedNetwork(int evolutionIterations){
		NodeMap nodeMap = new NodeMap(functions, matrix);
		for(int i = 0; i<evolutionIterations; i++)nodeMap.evolve();
		nodeMap.run();
	}
	public void score(Score[] scores){ mainNet.score(scores); }
	public void evolve(){ mainNet.evolve(); }
	public void run(){ mainNet.run(); }
	public Function[] getFunctions(){ return functions; }
}