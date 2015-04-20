package wraith.library.WindowUtil.GUI;

import java.util.ArrayList;

public class GridLayout implements GuiLayout{
	private int tempX, tempY, tempWidth, tempHeight;
	private final int rows;
	private final int cols;
	private final boolean rowFirst;
	public GridLayout(int rows, int cols, boolean rowFirst){
		if(rows<0||cols<0)throw new IllegalArgumentException("Cannot have negative row or column count!");
		if(rows==0&&cols==0)throw new IllegalArgumentException("Cannot have both rows and columns of infinite size!");
		this.rows=rows;
		this.cols=cols;
		this.rowFirst=rowFirst;
	}
	public void setParentDimensions(int x, int y, int width, int height){
		tempX=x;
		tempY=y;
		tempWidth=width;
		tempHeight=height;
	}
	public void validateComponents(ArrayList<GuiComponent> components){
		int childWidth = cols==0?tempWidth/components.size():tempWidth/cols;
		int childHeight = rows==0?tempHeight/components.size():tempHeight/rows;
		for(int i = 0; i<components.size(); i++){
			if(cols==0)components.get(i).setSizeAndLocation(i%rows*childWidth+tempX, i/rows*childHeight+tempY, childWidth, childHeight);
			else if(rows==0)components.get(i).setSizeAndLocation(i%cols*childWidth+tempX, i/cols*childHeight+tempY, childWidth, childHeight);
			else if(rowFirst)components.get(i).setSizeAndLocation(i%cols*childWidth+tempX, i/cols*childHeight+tempY, childWidth, childHeight);
			else components.get(i).setSizeAndLocation(i%rows*childWidth+tempX, i/rows*childHeight+tempY, childWidth, childHeight);
		}
	}
}