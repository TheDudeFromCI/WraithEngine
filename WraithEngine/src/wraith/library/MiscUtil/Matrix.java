package wraith.library.MiscUtil;

public class Matrix{
	private final int rows, cols;
	private final double[][] values;
	public Matrix(int rows, int cols, double[][] values){
		if(values.length!=rows)throw new IllegalArgumentException("Value size does not mach rows and columns!");
		for(int a = 0; a<rows; a++)if(values[a].length!=cols)throw new IllegalArgumentException("Value size does not mach rows and columns!");
		this.values=values;
		this.rows=rows;
		this.cols=cols;
	}
	public Matrix copy(){
		double[][] v = new double[rows][cols];
		for(int a = 0; a<rows; a++)for(int b = 0; b<cols; b++)v[a][b]=values[a][b];
		return new Matrix(rows, cols, v);
	}
	public Matrix transpose(){
		double[][] v = new double[cols][rows];
		for(int a = 0; a<cols; a++)for(int b = 0; b<rows; b++)v[a][b]=values[b][a];
		return new Matrix(cols, rows, v);
	}
	public Matrix multiply(Matrix m){
		if(rows!=m.cols)throw new IllegalArgumentException("Uncomparable matrix sizes!");
		Matrix o = new Matrix(rows, m.cols, new double[rows][m.cols]);
		for(int a = 0; a<rows; a++)for(int b = 0; b<m.cols; b++)for(int c = 0; c<cols; c++)for(int d = 0; d<m.rows; d++)o.values[a][b]+=values[a][c]*m.values[b][d];
		return o;
	}
	public Matrix multiply(double s){
		Matrix m = copy();
		for(int a = 0; a<rows; a++)for(int b = 0; b<cols; b++)m.values[a][b]*=s;
		return m;
	}
	public Matrix divide(double s){
		Matrix m = copy();
		for(int a = 0; a<rows; a++)for(int b = 0; b<cols; b++)m.values[a][b]/=s;
		return m;
	}
	public Matrix add(Matrix m){
		if(rows!=m.rows||cols!=m.cols)throw new IllegalArgumentException("Matrices not the same size!");
		Matrix o = copy();
		for(int a = 0; a<rows; a++)for(int b = 0; b<cols; b++)o.values[a][b]+=m.values[a][b];
		return o;
	}
	public Matrix subtract(Matrix m){
		if(rows!=m.rows||cols!=m.cols)throw new IllegalArgumentException("Matrices not the same size!");
		Matrix o = copy();
		for(int a = 0; a<rows; a++)for(int b = 0; b<cols; b++)o.values[a][b]-=m.values[a][b];
		return o;
	}
	public double dot(Matrix m){
		if(!isVector())throw new IllegalStateException("This is not a vector!");
		if(!m.isVector())throw new IllegalArgumentException("Matrix is not a vector!");
		if(rows!=m.rows)throw new IllegalArgumentException("Vectors not same length!");
		double d = 0;
		for(int a = 0; a<rows; a++)d+=values[a][0]*m.values[a][0];
		return d;
	}
	public void set(int r, int c, double v){ values[r][c]=v; }
	public double get(int r, int c){ return values[r][c]; }
	public boolean isVector(){ return cols==1; }
}