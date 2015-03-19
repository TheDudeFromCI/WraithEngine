package wraith.library.AI;

import java.util.ArrayList;

public class EvolutionaryLearningSystem{
	private boolean listenToLastJump;
	private int unevenIndex;
	private double[] jumps, currentData, tempData;
	private double currentScore = Long.MIN_VALUE;
	private boolean awaitingScore;
	private double randomnessFactor, outfittingRate;
	private int sibilingNumber;
	private double totalGenerationScore;
	private final int sibilings;
	private final boolean singleRandom;
	private final boolean repetativeGuesses;
	private final int dimensions;
	private final Object multithreadLock = 0;
	private final ArrayList<GenerationListener> generationListeners = new ArrayList<>();
	public EvolutionaryLearningSystem(int dimensions, double randomnessFactor, boolean singleRandom, boolean repetativeGuesses, double outfittingRate, int sibilings, boolean randomInital){
		this.dimensions=dimensions;
		this.randomnessFactor=randomnessFactor;
		this.singleRandom=singleRandom;
		this.repetativeGuesses=repetativeGuesses;
		this.outfittingRate=outfittingRate;
		this.sibilings=sibilings;
		if(dimensions>0){
			currentData=new double[dimensions];
			tempData=new double[dimensions];
			jumps=new double[dimensions];
		}else{
			currentData=new double[1];
			tempData=new double[1];
			jumps=new double[1];
		}
		if(randomInital)randomizeInitalData();
	}
	public double[] next(){
		if(awaitingScore)throw new IllegalStateException("Still awaiting score!");
		synchronized(multithreadLock){
			awaitingScore=true;
			if(sibilingNumber==0)randomizeCurrentData();
			return tempData;
		}
	}
	public void score(double score){
		if(!awaitingScore)throw new IllegalStateException("Not awaiting score!");
		synchronized(multithreadLock){
			awaitingScore=false;
			sibilingNumber++;
			totalGenerationScore+=score;
			if(sibilingNumber==sibilings){
				score=totalGenerationScore/sibilings;
				sibilingNumber=0;
				totalGenerationScore=0;
				if(score>currentScore){
					callGenerationListeners(score, true, score-currentScore);
					randomnessFactor*=outfittingRate;
					currentData=new double[tempData.length];
					for(int i = 0; i<currentData.length; i++)currentData[i]=tempData[i];
					currentScore=score;
					if(repetativeGuesses)listenToLastJump=true;
				}else{
					callGenerationListeners(score, false, score-currentScore);
					listenToLastJump=false;
				}
			}else callGenerationListeners(score, false, getCurrentGenerationScore()-currentScore);
		}
	}
	private void randomizeCurrentData(){
		if(dimensions==0){
			if(!listenToLastJump&&Math.random()>0.9){
				if(currentData.length>1&&Math.random()>0.5){
					unevenIndex=(int)(Math.random()*currentData.length);
					tempData=new double[currentData.length-1];
					int tempDataIndex = 0;
					jumps=new double[tempData.length];
					int singleRandomIndex = (int)(Math.random()*jumps.length);
					for(int i = 0; i<currentData.length; i++){
						if(i==unevenIndex)continue;
						if(singleRandom)tempData[tempDataIndex]=currentData[i]+(jumps[tempDataIndex]=tempDataIndex==singleRandomIndex?(Math.random()-0.5)*randomnessFactor:0);
						else tempData[tempDataIndex]=currentData[i]+(jumps[tempDataIndex]=(Math.random()-0.5)*randomnessFactor);
						tempDataIndex++;
					}
				}else{
					unevenIndex=(int)(Math.random()*(currentData.length+1));
					tempData=new double[currentData.length+1];
					int currentDataIndex = 0;
					jumps=new double[tempData.length];
					int singleRandomIndex = (int)(Math.random()*jumps.length);
					for(int i = 0; i<tempData.length; i++){
						if(i==unevenIndex){
							tempData[i]=(Math.random()-0.5)*randomnessFactor;
							continue;
						}
						if(singleRandom)tempData[i]=currentData[currentDataIndex]+(jumps[i]=i==singleRandomIndex?(Math.random()-0.5)*randomnessFactor:0);
						else tempData[i]=currentData[currentDataIndex]+(jumps[i]=(Math.random()-0.5)*randomnessFactor);
						currentDataIndex++;
					}
				}
				return;
			}
		}
		if(!listenToLastJump)jumps=new double[tempData.length];
		if(singleRandom){
			if(!listenToLastJump)jumps[(int)(Math.random()*jumps.length)]=(Math.random()-0.5)*randomnessFactor;
			for(int i = 0; i<dimensions; i++)tempData[i]=currentData[i]+jumps[i];
		}else{
			for(int i = 0; i<dimensions; i++){
				if(!listenToLastJump)jumps[i]=(Math.random()-0.5)*randomnessFactor;
				tempData[i]=currentData[i]+jumps[i];
			}
		}
	}
	public double[] getJumps(){
		if(!awaitingScore)throw new IllegalStateException("Not awaiting score!");
		synchronized(multithreadLock){ return jumps; }
	}
	private void randomizeInitalData(){ for(int i = 0; i<currentData.length; i++)currentData[i]=tempData[i]=(Math.random()-0.5)*randomnessFactor; }
	private void callGenerationListeners(double score, boolean current, double increase){ for(GenerationListener r : generationListeners)r.run(score, current, increase); }
	public void addGenerationListener(GenerationListener r){ generationListeners.add(r); }
	public void removeGenerationListener(GenerationListener r){ generationListeners.remove(r); }
	public void resetScore(){ currentScore=Long.MIN_VALUE; }
	public double getCurrentScore(){ return currentScore; }
	public double[] getBest(){ return currentData; }
	public double getRandomnessFactor(){ return randomnessFactor; }
	public void setRandomnessFactor(double randomnessFactor){ this.randomnessFactor=randomnessFactor; }
	public double getOutfittingRate(){ return outfittingRate; }
	public void setOutfittingRate(double outfittingRate){ this.outfittingRate=outfittingRate; }
	public int getSibilings(){ return sibilings; }
	public int getSibilingNumber(){ return sibilingNumber; }
	public double getCurrentGenerationScore(){ return totalGenerationScore/sibilingNumber; }
}