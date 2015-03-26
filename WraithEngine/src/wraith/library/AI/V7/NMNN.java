package wraith.library.AI.V7;

/**
 * Node Map Neural Network
 * @author TheDudeFromCI
 */
public class NMNN{
	private final Function[] functions;
	private final TrainingMatrix matrix = new TrainingMatrix();
	public void score(Score[] scores){
		//TODO
	}
	public NMNN(Function[] functions){ this.functions=functions; }
	public Function[] getFunctions(){ return functions; }
}