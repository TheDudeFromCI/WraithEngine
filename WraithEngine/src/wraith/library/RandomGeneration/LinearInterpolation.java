package wraith.library.RandomGeneration;

public class LinearInterpolation implements InterpolationFunction{
	public float interpolate(float a, float b, float frac){ return (a-b)*frac+b; }
}