package wraith.library.AI.V7;

public class FunctionConnection{
	private int[] functions;
	private int[] childPositions;
	public FunctionConnection(int[] functions, int[] childPositions){
		this.functions=functions;
		this.childPositions=childPositions;
	}
	public boolean matches(FunctionConnection f){
		if(f.functions.length!=functions.length)return false;
		for(int i = 0; i<functions.length; i++)if(functions[i]!=f.functions[i])return false;
		if(f.childPositions.length!=childPositions.length)return false;
		for(int i = 0; i<childPositions.length; i++)if(childPositions[i]!=f.childPositions[i])return false;
		return true;
	}
	@Override public boolean equals(Object o){ return o instanceof FunctionConnection?matches((FunctionConnection)o):null; }
}