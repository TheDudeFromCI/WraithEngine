package wraith.library.AI;

import java.math.BigDecimal;
import java.math.BigInteger;

public class BasicEvolutionProgressLog{
	private int generations;
	private long maxScore = Long.MIN_VALUE;
	private long minScore = Long.MAX_VALUE;
	private BigInteger totalError;
	private final EvolutionaryLearningSystem evolutionaryLearningSystem;
	public BasicEvolutionProgressLog(EvolutionaryLearningSystem evolutionaryLearningSystem){
		this.evolutionaryLearningSystem=evolutionaryLearningSystem;
		totalError=new BigInteger("0");
		evolutionaryLearningSystem.addGenerationListener(new GenerationListener(){
			public void run(long score){ addScore(score); }
		});
	}
	private void addScore(long score){
		generations++;
		if(score>maxScore)maxScore=score;
		if(score<minScore)minScore=score;
		totalError=evolutionaryLearningSystem.getCurrentScore()==Long.MIN_VALUE?new BigInteger(String.valueOf(score)):totalError.add(new BigInteger(String.valueOf(score-evolutionaryLearningSystem.getCurrentScore())));
	}
	public long getHighestScore(){ return maxScore; }
	public long getLowestScore(){ return minScore; }
	public int generations(){ return generations; }
	public String getAdverageError(){ return new BigDecimal(totalError.toString()).divide(new BigDecimal(String.valueOf(generations)), 5, BigDecimal.ROUND_HALF_UP).toString(); }
}