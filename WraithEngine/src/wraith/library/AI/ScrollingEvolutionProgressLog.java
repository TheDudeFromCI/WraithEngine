package wraith.library.AI;

public class ScrollingEvolutionProgressLog{
	private int generations;
	private long lowest = Long.MAX_VALUE;
	private long highest = Long.MIN_VALUE;
	private double averageLow = Long.MAX_VALUE;
	private double averageHigh = Long.MIN_VALUE;
	private final long[] scores;
	private final long[] maxScores;
	private final double[] averages;
	public ScrollingEvolutionProgressLog(EvolutionaryLearningSystem evolutionaryLearningSystem, int size){
		scores=new long[size];
		maxScores=new long[size];
		averages=new double[size];
		evolutionaryLearningSystem.addGenerationListener(new GenerationListener(){
			public void run(long score){ addScore(score); }
		});
	}
	public double getScorePercent(int index){
		long h = getHighestScore();
		long l = getLowestScore();
		h-=l;
		return h==0?1:(scores[index]-l)/(double)h;
	}
	public double getMaxScorePercent(int index){
		long h = getHighestScore();
		long l = getLowestScore();
		h-=l;
		return h==0?1:(maxScores[index]-l)/(double)h;
	}
	public double getAverageScorePercent(int index){
		double h = averageHigh;
		double l = averageLow;
		h-=l;
		return h==0?1:(averages[index]-l)/h;
	}
	private void addScore(long score){
		if(score>highest){
			highest=score;
			maxScores[generations%scores.length]=score;
		}else maxScores[generations%scores.length]=maxScores[(generations-1)%scores.length];
		if(score<lowest)lowest=score;
		scores[generations%scores.length]=score;
		averages[generations%scores.length]=findAverage();
		if(averages[generations%scores.length]>averageHigh)averageHigh=averages[generations%scores.length];
		if(averages[generations%scores.length]<averageLow)averageLow=averages[generations%scores.length];
		generations++;
	}
	public double findAverage(){
		long l = 0;
		for(int i = 0; i<scores.length; i++)l+=scores[i];
		return l/(double)scores.length;
	}
	public int generations(){ return generations; }
	public long getHighestScore(){ return highest; }
	public long getLowestScore(){ return lowest; }
	public long getScore(int index){ return scores[index]; }
	public int size(){ return scores.length; }
}