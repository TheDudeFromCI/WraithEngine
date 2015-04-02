package wraith.library.AI.V5;

import java.util.ArrayList;

public class BinaryNeuralNetworkTrainingMatrix{
	private final ArrayList<Data> data = new ArrayList<>();
	public void addData(double[] in, boolean[] out, double certainty){ data.add(new Data(in, out, certainty)); }
	public ArrayList<Data> getData(){ return data; }
	public static class Data{
		private double certainty;
		private final double[] in;
		private final boolean[] out;
		private Data(double[] in, boolean[] out, double certainty){
			this.in=in;
			this.out=out;
			this.certainty=certainty;
		}
		public double[] getIn(){ return in; }
		public boolean[] getOut(){ return out; }
		public double getCertainty(){ return certainty; }
		public void setCertainty(double certainty){ this.certainty=certainty; }
	}
}