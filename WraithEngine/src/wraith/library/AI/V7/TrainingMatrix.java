package wraith.library.AI.V7;

public class TrainingMatrix{
	private FunctionConnection[] connections = new FunctionConnection[0];
	private FunctionConnectionBelief[] beliefs = new FunctionConnectionBelief[0];
	private final Object LOCK = 0;
	public void addConnection(FunctionConnection f, Score[] score){
		synchronized(LOCK){
			for(int i = 0; i<connections.length; i++){
				if(connections[i].matches(f)){
					beliefs[i].add(score);
					return;
				}
			}
			FunctionConnection[] set1 = new FunctionConnection[connections.length+1];
			FunctionConnectionBelief[] set2 = new FunctionConnectionBelief[connections.length+1];
			for(int i = 0; i<connections.length; i++){
				set1[i]=connections[i];
				set2[i]=beliefs[i];
			}
			set1[connections.length]=f;
			set2[connections.length]=new FunctionConnectionBelief(score);
			connections=set1;
			beliefs=set2;
		}
	}
}