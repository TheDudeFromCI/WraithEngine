package wraith.library.AI.V7;

public interface Function{
	public void run(Data[] inputs, Data[] outputs, FunctionMetaData meta);
	public DataType[] getInputs();
	public DataType[] getOutputs();
}