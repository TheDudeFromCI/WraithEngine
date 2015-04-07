package wraith.library.AI;

import org.ejml.simple.SimpleMatrix;

public class LinearRegression{
	public static SimpleMatrix train(SimpleMatrix data, int iterations, double learningRate){
		SimpleMatrix theta = new SimpleMatrix(data.numRows(), 1);
		train(data, iterations, learningRate, theta);
		return theta;
	}
	public static void train(SimpleMatrix data, int iterations, double learningRate, SimpleMatrix theta){
		SimpleMatrix x = new SimpleMatrix(data.numRows(), data.numCols());
		SimpleMatrix y = new SimpleMatrix(data.numRows(), 1);
		SimpleMatrix tempTheta = new SimpleMatrix(theta.numRows(), theta.numCols());
		for(int a = 0; a<data.numRows(); a++){
			for(int b = 0; b<data.numCols(); b++){
				if(b==0)x.set(a, b, 1);
				else x.set(a, b, data.get(a, b-1));
			}
			y.set(a, 0, data.get(a, data.numCols()-1));
		}
		double d;
		SimpleMatrix thetaTrans;
		for(int a = 0; a<iterations; a++){
			thetaTrans=theta.transpose();
			for(int j = 0; j<theta.numRows(); j++){
				d=0;
				for(int i = 0; i<x.numRows(); i++)d+=(thetaTrans.mult(getRow(x, i)).get(0, 0)-y.get(i, 0))*x.get(i, j);
				tempTheta.set(j, 0, theta.get(j, 0)-learningRate*((1.0/x.numRows())*d));
			}
			theta.set(tempTheta);
		}
	}
	public static int trainUntilOptima(SimpleMatrix data, double minLoss, double learningRate, SimpleMatrix theta){
		int iterations = 0;
		double loss = Double.MAX_VALUE;
		double last = Double.MAX_VALUE;
		while(loss>minLoss){
			train(data, 10, learningRate, theta);
			iterations++;
			double run = LinearRegression.findCost(data, theta);
			loss=last-run;
			last=run;
		}
		return iterations;
	}
	public static void normalEquation(SimpleMatrix data, SimpleMatrix theta){
		SimpleMatrix x = new SimpleMatrix(data.numRows(), data.numCols());
		SimpleMatrix y = new SimpleMatrix(data.numRows(), 1);
		for(int a = 0; a<data.numRows(); a++){
			for(int b = 0; b<data.numCols(); b++){
				if(b==0)x.set(a, b, 1);
				else x.set(a, b, data.get(a, b-1));
			}
			y.set(a, 0, data.get(a, data.numCols()-1));
		}
		SimpleMatrix xTrans = x.transpose();
		SimpleMatrix m = xTrans.mult(x);
		try{ theta.set(m.invert().mult(xTrans).mult(y));
		}catch(Exception exception){}
	}
	public static double findCost(SimpleMatrix data, SimpleMatrix theta){
		SimpleMatrix x = new SimpleMatrix(data.numRows(), data.numCols());
		for(int a = 0; a<x.numRows(); a++){
			for(int b = 0; b<x.numCols(); b++){
				if(b==0)x.set(a, b, 1);
				else x.set(a, b, data.get(a, b-1));
			}
		}
		double d = 0;
		SimpleMatrix thetaTrans = theta.transpose();
		for(int a = 0; a<data.numRows(); a++)d+=Math.pow(thetaTrans.mult(getRow(x, a)).get(0, 0)-data.get(a, data.numCols()-1), 2);
		return (1.0/(2.0*theta.numRows()))*d;
	}
	private static SimpleMatrix getRow(SimpleMatrix x, int row){
		SimpleMatrix r = new SimpleMatrix(x.numCols(), 1);
		for(int i = 0; i<r.numRows(); i++)r.set(i, 0, x.get(row, i));
		return r;
	}
	public static double parse(SimpleMatrix theta, SimpleMatrix x){ return theta.transpose().mult(x).get(0, 0); }
}