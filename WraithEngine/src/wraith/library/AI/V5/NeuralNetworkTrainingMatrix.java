package wraith.library.AI.V5;

import java.util.HashMap;

public class NeuralNetworkTrainingMatrix{
	private final HashMap<double[],double[]> standardOutputs = new HashMap<>();
	public void addData(double[] in, double[] out){ standardOutputs.put(in, out); }
	public HashMap<double[],double[]> getData(){ return standardOutputs; }
}