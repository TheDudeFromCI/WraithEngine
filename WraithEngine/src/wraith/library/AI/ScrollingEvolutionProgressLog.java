package wraith.library.AI;

public class ScrollingEvolutionProgressLog{
	private int generations;
	private int learns;
	private double lowest = Long.MAX_VALUE;
	private double highest = Long.MIN_VALUE;
	private final double[] scores;
	private final double[] maxScores;
	private final double[] averages;
	private final double[] generationScores;
	private final EvolutionaryLearningSystem learningSystem;
	public ScrollingEvolutionProgressLog(EvolutionaryLearningSystem evolutionaryLearningSystem, int size){
		learningSystem=evolutionaryLearningSystem;
		scores=new double[size];
		maxScores=new double[size];
		averages=new double[size];
		generationScores=new double[size];
		evolutionaryLearningSystem.addGenerationListener(new GenerationListener(){
			public void run(double score, boolean current, double increase){ addScore(score, current); }
		});
	}
	public double getScorePercent(int index){
		double h = highest;
		double l = lowest;
		h-=l;
		return h==0?1:(scores[index]-l)/h;
	}
	public double getMaxScorePercent(int index){
		double h = highest;
		double l = lowest;
		h-=l;
		return h==0?1:(maxScores[index]-l)/h;
	}
	public double getAverageScorePercent(int index){
		double h = highest;
		double l = lowest;
		h-=l;
		return h==0?1:(averages[index]-l)/h;
	}
	public double getGenerationScorePercent(int index){
		double h = highest;
		double l = lowest;
		h-=l;
		return h==0?1:(generationScores[index]-l)/h;
	}
	private void addScore(double score, boolean gain){
		if(gain)learns++;
		if(score>highest)highest=score;
		if(score<lowest)lowest=score;
		maxScores[generations%scores.length]=Math.max(learningSystem.getCurrentScore(), lowest);
		scores[generations%scores.length]=score;
		averages[generations%scores.length]=findAverage();
		generationScores[generations%scores.length]=learningSystem.getSibilingNumber()==0?score:learningSystem.getCurrentGenerationScore();
		generations++;
	}
	public double findAverage(){
		long l = 0;
		for(int i = 0; i<scores.length; i++)l+=scores[i];
		return l/(double)scores.length;
	}
	public int generations(){ return generations; }
	public double getHighestScore(){ return highest; }
	public double getLowestScore(){ return lowest; }
	public double getScore(int index){ return scores[index]; }
	public int size(){ return scores.length; }
	public int getLearns(){ return learns; }
}