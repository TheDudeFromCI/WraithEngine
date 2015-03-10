package wraith.library.AI.NeuralNetworks;

public interface ActiveFunction{
	public double processData(double in);
	public double derivitive(double in);
}