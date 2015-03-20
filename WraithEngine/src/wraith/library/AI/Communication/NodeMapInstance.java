package wraith.library.AI.Communication;

public class NodeMapInstance{
	private double totalScore;
	private int scoresGiven;
	private final NodeMap nodeMap;
	private final FunctionInstance[] map;
	NodeMapInstance(NodeMap nodeMap, FunctionInstance[] map){
		this.nodeMap=nodeMap;
		this.map=map;
	}
	public void runMap(){
		//TODO
	}
	public void addScore(double score){
		totalScore+=score;
		scoresGiven++;
	}
}