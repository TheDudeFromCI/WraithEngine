package wraith.library.AI;

import wraith.library.MiscUtil.Matrix;

public class LinearRegression{
	public static Matrix train(Matrix data, int iterations, double learningRate){
		Matrix theta = new Matrix(data.getCols(), 1);
		train(data, iterations, learningRate, theta);
		return theta;
	}
	public static void train(Matrix data, int iterations, double learningRate, Matrix theta){
		Matrix x = new Matrix(data.getRows(), data.getCols());
		Matrix y = new Matrix(data.getRows(), 1);
		Matrix tempTheta = new Matrix(theta.getRows(), theta.getCols());
		for(int a = 0; a<data.getRows(); a++){
			for(int b = 0; b<data.getCols(); b++){
				if(b==0)x.set(a, b, 1);
				else x.set(a, b, data.get(a, b-1));
			}
			y.set(a, 0, data.get(a, data.getCols()-1));
		}
		double d;
		for(int a = 0; a<iterations; a++){
			for(int j = 0; j<theta.getRows(); j++){
				d=0;
				for(int i = 0; i<x.getRows(); i++)d+=(theta.transpose().multiply(x.getRow(i)).get(0, 0)-y.get(i, 0))*x.get(i, j);
				tempTheta.set(j, 0, theta.get(j, 0)-learningRate*((1.0/x.getRows())*d));
			}
			for(int j = 0; j<theta.getRows(); j++)theta.set(j, 0, tempTheta.get(j, 0));
		}
	}
	public static int trainUntilOptima(Matrix data, double minLoss, double learningRate, Matrix theta){
		int iterations = 0;
		double loss = Double.MAX_VALUE;
		double last = Double.MAX_VALUE;
		while(loss>=minLoss){
			train(data, 1, learningRate, theta);
			iterations++;
			double run = LinearRegression.findCost(data, theta);
			loss=last-run;
			last=run;
		}
		return iterations;
	}
	public static double findCost(Matrix data, Matrix theta){
		Matrix x = new Matrix(data.getRows(), data.getCols());
		for(int a = 0; a<x.getRows(); a++){
			for(int b = 0; b<x.getCols(); b++){
				if(b==0)x.set(a, b, 1);
				else x.set(a, b, data.get(a, b-1));
			}
		}
		double d = 0;
		for(int a = 0; a<data.getRows(); a++)d+=Math.pow(theta.transpose().multiply(x.getRow(a)).get(0, 0)-data.get(a, data.getCols()-1), 2);
		return (1.0/(2.0*theta.getRows()))*d;
	}
}