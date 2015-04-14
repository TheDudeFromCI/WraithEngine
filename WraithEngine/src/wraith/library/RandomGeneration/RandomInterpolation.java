package wraith.library.RandomGeneration;

import java.util.Random;

public class RandomInterpolation implements InterpolationFunction{
	private final Random random;
	private final float randomness;
	private final InterpolationFunction baseFunction;
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
		frac+=random.nextFloat()*randomness-randomness/2;
		if(frac>1)frac=1;
		if(frac<0)frac=0;
		return baseFunction.interpolate(a, b, frac);
	}
}