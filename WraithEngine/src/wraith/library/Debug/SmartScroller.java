package wraith.library.Debug;

import java.awt.Component;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;

public class SmartScroller implements AdjustmentListener{
	public final static int HORIZONTAL = 0;
	public final static int VERTICAL = 1;
	public final static int START = 0;
	public final static int END = 1;
	private int viewportPosition;
	private JScrollBar scrollBar;
	private boolean adjustScrollBar = true;
	private int previousValue = -1;
	private int previousMaximum = -1;
	public SmartScroller(JScrollPane scrollPane, int scrollDirection, int viewportPosition){
		if(scrollDirection!=HORIZONTAL&&scrollDirection!=VERTICAL)throw new IllegalArgumentException("invalid scroll direction specified");
		if(viewportPosition!=START&&viewportPosition!=END)throw new IllegalArgumentException("invalid viewport position specified");
		this.viewportPosition=viewportPosition;
		if(scrollDirection==HORIZONTAL)scrollBar=scrollPane.getHorizontalScrollBar();
		else scrollBar=scrollPane.getVerticalScrollBar();
		scrollBar.addAdjustmentListener( this );
		Component view = scrollPane.getViewport().getView();
		if(view instanceof JTextComponent){
			JTextComponent textComponent = (JTextComponent)view;
			DefaultCaret caret = (DefaultCaret)textComponent.getCaret();
			caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		}
	}
	@Override public void adjustmentValueChanged(final AdjustmentEvent e) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run(){ checkScrollBar(e); }
		});
	}
	private void checkScrollBar(AdjustmentEvent e){
		JScrollBar scrollBar = (JScrollBar)e.getSource();
		BoundedRangeModel listModel = scrollBar.getModel();
		int value = listModel.getValue();
		int extent = listModel.getExtent();
		int maximum = listModel.getMaximum();
		boolean valueChanged = previousValue!=value;
		boolean maximumChanged = previousMaximum!=maximum;
		if(valueChanged&&!maximumChanged){
			if(viewportPosition==START)adjustScrollBar=value!=0;
			else adjustScrollBar=value+extent>=maximum;
		}
		if(adjustScrollBar&&viewportPosition==END){
			scrollBar.removeAdjustmentListener(this);
			value=maximum-extent;
			scrollBar.setValue(value);
			scrollBar.addAdjustmentListener(this);
		}
		if(adjustScrollBar&&viewportPosition==START){
			scrollBar.removeAdjustmentListener(this);
			value=value+maximum-previousMaximum;
			scrollBar.setValue(value);
			scrollBar.addAdjustmentListener(this);
		}
		previousValue=value;
		previousMaximum=maximum;
	}
	public SmartScroller(JScrollPane scrollPane){ this(scrollPane, VERTICAL, END); }
	public SmartScroller(JScrollPane scrollPane, int viewportPosition){ this(scrollPane, VERTICAL, viewportPosition); }
}