package wraith.library.AI.NeuralNetworks;

import java.util.ArrayList;

public class NeuralNetworkTrainingMatrix{
	private ArrayList<double[][]> data = new ArrayList<>();
	private double error = 0.1;
	public void train(NeuralNetwork network){
		double[][] errorRates = new double[data.size()][];
		double[] in = new double[network.getInputs()];
		double[] out = new double[network.getOutputs()];
		while(true){
			System.out.println("1");
			for(int i = 0; i<errorRates.length; i++)errorRates[i]=test(network, data.get(i), in, out);
			if(passes(errorRates, error))return;
			int worstError = getWorstError(errorRates);
			soloTrainer:while(true){
				System.out.println("  2");
				network.train(data.get(worstError)[0], data.get(worstError)[1]);
				double[] o = test(network, data.get(worstError), in, out);
				System.out.print("   ");
				print(o);
				for(int i = 0; i<o.length; i++)if(o[i]>error)continue soloTrainer;
				break;
			}
		}
	}
	private static void print(double[] data){
		System.out.print("[");
		for(int i = 0; i<data.length; i++)System.out.print(i==0?data[i]:", "+data[i]);
		System.out.println("]");
	}
	public void addData(double[] in, double[] out){ data.add(new double[][]{in, out}); }
	public void clearData(){ data.clear(); }
	public ArrayList<double[][]> getData(){ return data; }
	public double getError(){ return error; }
	public void setError(double error){ this.error=error; }
	private static double[] test(NeuralNetwork network, double[][] d, double[] in, double[] out){
		double[] a = new double[out.length];
		for(int i = 0; i<in.length; i++)in[i]=d[0][i];
		network.run(in, out);
		for(int i = 0; i<a.length; i++)a[i]=Math.abs(out[i]-d[1][i]);
		return a;
	}
	private static boolean passes(double[][] d, double error){
		for(int a = 0; a<d.length; a++)for(int b = 0; b<d[a].length; b++)if(d[a][b]>error)return false;
		return true;
	}
	private static int getWorstError(double[][] d){
		int j = 0;
		double e = 0;
		for(int a = 0; a<d.length; a++){
			for(int b = 0; b<d[a].length; b++){
				if(d[a][b]>e){
					e=d[a][b];
					j=a;
				}
			}
		}
		return j;
	}
}