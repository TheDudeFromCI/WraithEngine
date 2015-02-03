package wraith.library.AI.NodeMapLearning;

public interface NodeFunction{
	public NodeDataType[] getInputs();
	public NodeDataType[] getOutputs();
	public NodeData[] run(NodeData[] data);
}