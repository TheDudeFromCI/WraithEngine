package wraith.library.AI;

import java.util.HashMap;

public class BinaryNeuralNetworkTrainingMatrix{
	private final HashMap<double[],boolean[]> standardOutputs = new HashMap<>();
	public void addData(double[] in, boolean[] out){ standardOutputs.put(in, out); }
	public HashMap<double[],boolean[]> getData(){ return standardOutputs; }
}