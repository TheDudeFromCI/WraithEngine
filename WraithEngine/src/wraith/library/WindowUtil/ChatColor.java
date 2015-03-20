package wraith.library.WindowUtil;

import java.awt.Color;

public enum ChatColor{
	WHITE('0', new Color(255, 255, 255)),
	GREEN('1', new Color(0, 255, 0)),
	RED('2', new Color(255, 0, 0)),
	YELLOW('3', new Color(255, 255, 0)),
	BLUE('4', new Color(0, 0, 255)),
	AQUA('5', new Color(0, 255, 255)),
	ORANGE('6', new Color(255, 128, 0)),
	LIGHT_GRAY('7', new Color(200, 200, 200)),
	GRAY('8', new Color(128, 128, 128)),
	DARK_GRAY('9', new Color(70, 70, 70)),
	PURPLE('A', new Color(255, 0, 255)),
	BLACK('B', new Color(0, 0, 0));
	private final char symbol;
	private final Color color;
	private ChatColor(char symbol, Color color){
		this.symbol=symbol;
		this.color=color;
	}
	public Color getColor(){ return color; }
	@Override public String toString(){ return "¥"+symbol; }
	public static ChatColor getBySymbol(char symbol){
		for(ChatColor c : values())if(c.symbol==symbol)return c;
		return null;
	}
}