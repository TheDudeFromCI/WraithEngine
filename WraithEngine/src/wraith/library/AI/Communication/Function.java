package wraith.library.AI.Communication;

public abstract class Function{
	public abstract DataType[] getInputs();
	public abstract DataType[] getOutputs();
	public abstract Data[] runFunction(Data[] inputs, FunctionMetaData meta);
}