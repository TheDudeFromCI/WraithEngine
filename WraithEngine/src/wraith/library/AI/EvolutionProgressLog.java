package wraith.library.AI;

import java.util.ArrayList;

public class EvolutionProgressLog{
	private int deletions = 0;
	private long lowest = Long.MAX_VALUE;
	private long highest = Long.MIN_VALUE;
	private final ArrayList<Long> scores;
	private final ArrayList<Long> maxScores;
	private final int maxSize;
	private final EvolutionaryLearningSystem evolutionaryLearningSystem;
	private final Object multithreadLock = 0;
	public EvolutionProgressLog(EvolutionaryLearningSystem evolutionaryLearningSystem, int maxSize){
		scores=new ArrayList<>(maxSize!=-1?maxSize:10000);
		maxScores=new ArrayList<>(maxSize!=-1?maxSize:10000);
		this.maxSize=maxSize;
		this.evolutionaryLearningSystem=evolutionaryLearningSystem;
		evolutionaryLearningSystem.addGenerationListener(new GenerationListener(){
			public void run(long score){ addScore(score); }
		});
	}
	public double getScorePercent(int index){
		long h = getHighestScore();
		long l = getLowestScore();
		h-=l;
		synchronized(multithreadLock){ return h==0?1:(scores.get(index)-l)/(double)h; }
	}
	public double getMaxScorePercent(int index){
		long h = getHighestScore();
		long l = getLowestScore();
		h-=l;
		synchronized(multithreadLock){ return h==0?1:(maxScores.get(index)-l)/(double)h; }
	}
	private void clearRoom(){
		synchronized(multithreadLock){
			for(int i = 0; i<scores.size(); i++){
				scores.remove(i);
				maxScores.remove(i);
				deletions++;
			}
		}
	}
	private void addScore(long score){
		synchronized(multithreadLock){
			if(score>highest)highest=score;
			if(score<lowest)lowest=score;
			scores.add(score);
			maxScores.add(Math.max(evolutionaryLearningSystem.getCurrentScore(), score));
			if(maxSize!=-1&&scores.size()>maxSize)clearRoom();
		}
	}
	public int generations(){ return scores.size(); }
	public int getDeletions(){ return deletions; }
	public int getTotalGenerations(){ return generations()+deletions; }
	public long getHighestScore(){ return highest; }
	public long getLowestScore(){ return lowest; }
	public long getScore(int index){ return scores.get(index); }
	public long getMaxScore(int index){ return maxScores.get(index); }
}