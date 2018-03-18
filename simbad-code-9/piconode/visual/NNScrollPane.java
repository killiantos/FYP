package piconode.visual;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class NNScrollPane extends JScrollPane implements AdjustmentListener
	{
	    
	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		NNCanvas _canvas;
	    NNScrollPane(NNCanvas pl)
	    {	
		super();
		_canvas=pl;
		
		

		setPreferredSize(new Dimension(600, 500));	
		setVisible(true);
		setViewportView(pl);
		JScrollBar h_bar=getHorizontalScrollBar();
		JScrollBar v_bar=getVerticalScrollBar();
		h_bar.addAdjustmentListener( this );
		v_bar.addAdjustmentListener( this );
		h_bar.setBlockIncrement( NNFrame.MIDGRIDWIDTH*2 ); 
		v_bar.setBlockIncrement( NNFrame.MIDGRIDWIDTH*2 ); 
		h_bar.setUnitIncrement( NNFrame.MIDGRIDWIDTH*2 ); 
		v_bar.setUnitIncrement(NNFrame.MIDGRIDWIDTH*2 ); 
		h_bar.setValueIsAdjusting( true ); 
		v_bar.setValueIsAdjusting( true ); 
	    }
		public void adjustmentValueChanged(AdjustmentEvent e) {
			
			
		}

}
