package wraith.library.AI.V5;

public class BasicEvolutionProgressLog{
	private int generations;
	private double maxScore = Long.MIN_VALUE;
	private double minScore = Long.MAX_VALUE;
	public BasicEvolutionProgressLog(EvolutionaryLearningSystem evolutionaryLearningSystem){
		evolutionaryLearningSystem.addGenerationListener(new GenerationListener(){
			public void run(double score, boolean current, double increase){ addScore(score); }
		});
	}
	private void addScore(double score){
		generations++;
		if(score>maxScore)maxScore=score;
		if(score<minScore)minScore=score;
	}
	public double getHighestScore(){ return maxScore; }
	public double getLowestScore(){ return minScore; }
	public int generations(){ return generations; }
}