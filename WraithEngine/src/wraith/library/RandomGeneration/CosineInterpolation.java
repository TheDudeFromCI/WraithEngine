package wraith.library.RandomGeneration;

public class CosineInterpolation implements InterpolationFunction{
	public float interpolate(float a, float b, float frac){
		frac=(float)((1-Math.cos(frac*Math.PI))/2);
		return (a*(1-frac)+b*frac);
	}
}