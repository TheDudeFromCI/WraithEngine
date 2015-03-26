package wraith.library.AI.V7;

import java.math.BigDecimal;
import java.util.ArrayList;

public class FunctionConnectionBelief{
	private ArrayList<BigScore> scores = new ArrayList<>(1);
	public void add(Score[] scores){
		for(Score s : scores){
			for(BigScore f : this.scores){
				if(f.id==s.id){
					f.total=f.total.add(new BigDecimal(s.value));
					f.passes++;
				}
			}
		}
	}
	public FunctionConnectionBelief(Score[] scores){ add(scores); }
	class BigScore{
		int id;
		BigDecimal total;
		long passes;
	}
}