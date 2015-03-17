package wraith.library.AI;

import java.util.ArrayList;

public class EvolutionProgressLog{
	private int deletions = 0;
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
	public long getHighestScore(){
		long h = Long.MIN_VALUE;
		synchronized(multithreadLock){ for(Long l : scores)if(l>h)h=l; }
		return h;
	}
	public long getLowestScore(){
		long h = Long.MAX_VALUE;
		synchronized(multithreadLock){ for(Long l : scores)if(l<h)h=l; }
		return h;
	}
	public double getScorePercent(int index){
		long h = getHighestScore();
		long l = getLowestScore();
		h-=l;
		synchronized(multithreadLock){ return h==0?1:(scores.get(index)-l)/h; }
	}
	public double getMaxScorePercent(int index){
		long h = getHighestScore();
		long l = getLowestScore();
		h-=l;
		synchronized(multithreadLock){ return h==0?1:(maxScores.get(index)-l)/h; }
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
			scores.add(score);
			maxScores.add(Math.max(evolutionaryLearningSystem.getCurrentScore(), score));
			if(maxSize!=-1&&scores.size()>maxSize)clearRoom();
		}
	}
	public int generations(){ return scores.size(); }
	public int getDeletions(){ return deletions; }
	public int getTotalGenerations(){ return generations()+deletions; }
}