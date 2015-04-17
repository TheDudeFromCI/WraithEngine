package wraith.library.RandomGeneration;

import java.util.Random;

public class RandomInterpolation implements InterpolationFunction{
	private int direction = BOTH_DIRECTIONS;
	private final Random random;
	private final float randomness;
	private final InterpolationFunction baseFunction;
	public static final int TOWARDS_HIGHER = 0;
	public static final int TOWARDS_LOWER = 1;
	public static final int BOTH_DIRECTIONS = 2;
	public RandomInterpolation(InterpolationFunction baseFunction, float randomness){
		this.randomness=randomness;
		this.baseFunction=baseFunction;
		random=new Random();
	}
	public RandomInterpolation(InterpolationFunction baseFunction, float randomness, long seed){
		this.randomness=randomness;
		this.baseFunction=baseFunction;
		random=new Random(seed);
	}
	public float interpolate(float a, float b, float frac){
		float r = random.nextFloat()*randomness-randomness/2;
		if(direction!=BOTH_DIRECTIONS){
			if(a>b){
				if(r>0)r*=-1;
			}else if(r<0)r*=-1;
		}
		frac+=r;
		if(frac>1)frac=1;
		if(frac<0)frac=0;
		return baseFunction.interpolate(a, b, frac);
	}
	public void setDirection(int direction){ this.direction=direction;}
}