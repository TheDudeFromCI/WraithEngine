package wraith.library.MiscUtil;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TypeListener extends KeyAdapter{
	private StringBuilder text = new StringBuilder();
	private char[] regex;
	private int carretPosition;
	private boolean insert;
	private Runnable onEnter;
	private int characterCap;
	public TypeListener(String regex, Runnable run){
		this.regex=regex.toCharArray();
		onEnter=run;
	}
	@Override public void keyTyped(KeyEvent e){
		if(e.getKeyCode()==KeyEvent.VK_BACK_SPACE){
			if(carretPosition>0){
				carretPosition--;
				text.deleteCharAt(carretPosition);
			}
		}else if(e.getKeyCode()==KeyEvent.VK_LEFT)carretPosition=Math.max(carretPosition-1, 0);
		else if(e.getKeyCode()==KeyEvent.VK_RIGHT)carretPosition=Math.min(carretPosition+1, text.length());
		else if(e.getKeyCode()==KeyEvent.VK_DELETE){
			if(carretPosition<text.length())text.deleteCharAt(carretPosition);
		}else if(e.getKeyCode()==KeyEvent.VK_INSERT)insert=!insert;
		else{
			char c = e.getKeyChar();
			if(c=='\n'&&onEnter!=null){
				onEnter.run();
				return;
			}
			if(characterCap>0&&text.length()>=characterCap)return;
			if(containsCharacter(c)){
				if(insert){
					if(carretPosition==text.length())text.insert(carretPosition, c);
					else text.setCharAt(carretPosition, c);
				}else text.insert(carretPosition, c);
				carretPosition++;
			}
		}
	}
	private boolean containsCharacter(char a){
		for(char b : regex)if(a==b)return true;
		return false;
	}
	@Override public String toString(){ return text.toString(); }
	public void setCharacterCap(int cap){ characterCap=cap; }
}