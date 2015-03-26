package wraith.library.AI.V7;

import java.math.BigInteger;
import java.util.ArrayList;

public class FunctionConnectionBelief{
	private ArrayList<BigScore> scores = new ArrayList<>(1);
	public void add(Score[] scores){
		for(Score s : scores){
			for(BigScore f : this.scores){
				if(f.id==s.id){
					f.total=f.total.add(new BigInteger(String.valueOf(s.value)));
					f.passes++;
				}
			}
		}
	}
	public long getAverageScore(int scoreId){
		for(BigScore bs : scores)if(bs.id==scoreId)return bs.total.divide(new BigInteger(String.valueOf(bs.passes))).longValue();
		return 0;
	}
	public FunctionConnectionBelief(Score[] scores){ add(scores); }
	class BigScore{
		int id;
		BigInteger total;
		long passes;
	}
}