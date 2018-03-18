package piconode.visual;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Enumeration;

import javax.swing.JPanel;


class NNCanvas extends JPanel implements MouseMotionListener, MouseListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int SELECTIONMODE=1;
	public static final int NODEMODE=2;
	public static final int ARCMODE=3;
	
	//For mini modes, default must be 0
	public static final int NODE0ARCMODE=0;
	public static final int NODE1ARCMODE=1;
	
	public static final int RECTSELECTION=1;
	public static final int SELECTIONMADE=2;
	
	private static final int ARCMOVE = 3;
	
	int _GeneralMode,_MiniMode;
	
	int xSelectionStart,ySelectionStart;
	NNFrame frame;
	
	NNList listNNShape;
	NNSelection selectionNNShape;
	NNNode tmpNode;
	NNArc tmpArc;
	private Rectangle tmpRect;
	
	NNCanvas(NNFrame frame_arg){
		super();
		frame=frame_arg;
		_GeneralMode=NODEMODE;
		_MiniMode=NODE0ARCMODE;
		addMouseMotionListener(this);
		addMouseListener(this);
		listNNShape=new NNList();
		tmpNode=null;
		selectionNNShape=new NNSelection();
		tmpRect=null;
		
	}
	
	public void paint(Graphics g) 
	{ 
		g.clearRect(0,0,	getWidth(),getHeight());
		Graphics2D g2;
		g2 = (Graphics2D) g; 
		
		g2.setColor(new Color(230,230,230));
		g2.fillRect(0,0,	getWidth(),getHeight());
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		
		//Drawing Grid
		if (frame.isGrid())
		{
			g2.setColor(Color.white);
			int tmpScale=NNFrame.MIDGRIDWIDTH*2;
			
			while (tmpScale<getWidth()){
				
				g2.drawLine(tmpScale,0,tmpScale,getHeight());
				tmpScale+=NNFrame.MIDGRIDWIDTH*2;
				
			}
			
			
			tmpScale=NNFrame.MIDGRIDWIDTH*2;
			
			while (tmpScale<getHeight()){
				
				g2.drawLine(0,tmpScale,getWidth(),tmpScale);
				tmpScale+=NNFrame.MIDGRIDWIDTH*2;
				
			}
			
		}
		g2.setColor(Color.black);
		NNNode drawNode;
		
		Enumeration enumeration;
		
		enumeration = listNNShape.getArcs();
		while(enumeration.hasMoreElements()) 
		{ 
			((NNArc)(enumeration.nextElement())).paint(g2);
		}
		
		enumeration = listNNShape.getNodes();
		while(enumeration.hasMoreElements()) 
		{
			drawNode=((NNNode)(enumeration.nextElement()));	
			drawNode.paint(g2);	
		}
		
		if (tmpRect!=null)
			g2.draw(tmpRect);
		
	}
	
	void activateGrid(){
		Enumeration enumeration;
		
		enumeration = listNNShape.getNodes();
		while(enumeration.hasMoreElements()) 
		{
			((NNNode)(enumeration.nextElement())).replaceForGrid();	
		}
	}
	
	public void mouseDragged(MouseEvent e) {
		Enumeration enumeration;
		
		switch (_GeneralMode){
		case NODEMODE:
			break;
		case SELECTIONMODE:
			switch(_MiniMode){
			case RECTSELECTION:
				
				tmpRect=new Rectangle(Math.min(xSelectionStart,e.getX()),Math.min(ySelectionStart,e.getY()),
						Math.abs(e.getX()-xSelectionStart),Math.abs(e.getY()-ySelectionStart));
				NNNode drawNode;
				
				//Updating Selection
				
				selectionNNShape.resetSelection();
				enumeration = listNNShape.getNodes();
				while(enumeration.hasMoreElements()) 
				{
					drawNode=((NNNode)(enumeration.nextElement()));
					
					if (drawNode.isInRect(tmpRect) )
						selectionNNShape.addNode(drawNode);
				}
				
				enumeration = listNNShape.getArcs();
				 while(enumeration.hasMoreElements()) 
				 {
				 tmpArc=((NNArc)(enumeration.nextElement()));
				 
				 if ( tmpArc.getNodeFrom().isSelected() != tmpArc.getNodeFrom().isInverseTempSelection()
						 && tmpArc.getNodeTo().isSelected() != tmpArc.getNodeTo().isInverseTempSelection() 
						 )
				 selectionNNShape.addArc(tmpArc);
				 }
				 tmpArc=null;
			
				
				repaint();
				break;
			case SELECTIONMADE:
				
				int dx,dy;
				dx=e.getX()-xSelectionStart;
				dy=e.getY()-ySelectionStart;
				enumeration = selectionNNShape.getNodes();
				while(enumeration.hasMoreElements())
				{
					((NNNode) enumeration.nextElement() ).translate(dx,dy);
				}
				repaint();
				xSelectionStart=e.getX();
				ySelectionStart=e.getY();
				
				break;
			case ARCMOVE:
				tmpArc.mooveTo(e.getX(),e.getY());
				repaint();
				break;
			default:
				System.err.println("Switch Minimod SMode error");
			}
			
			
			break;
		case ARCMODE:
			break;
		default:System.err.println("Drag : Switch Error");
		}
	}
	
	
	
	
	
	public void mousePressed(MouseEvent e) {
		switch (_GeneralMode){
		case NODEMODE:
			System.err.println("Press : NodeMode");
			tmpNode=new NNNode(e.getX(),e.getY(),frame.isGrid());
			listNNShape.addNode(tmpNode);
			selectionNNShape.emptySelection();
			selectionNNShape.addNode(tmpNode);
			frame.formNode.update();
			frame.formArc.update();
			
			break;
		case SELECTIONMODE:
			System.err.println("Press : SelectionMode");
			
			
			tmpNode=onNode(e);
			if(tmpNode!=null)
			{//Clic sur un node
				if(tmpNode.isSelected())
				{
					if(e.isShiftDown()){
						selectionNNShape.removeNode(tmpNode);
						repaint();
					}
				}
				else{
					if(!e.isShiftDown())
						selectionNNShape.emptySelection();
					
					
					selectionNNShape.addNode(tmpNode);	
				}
				xSelectionStart=e.getX();
				ySelectionStart=e.getY();
				_MiniMode=SELECTIONMADE;
				
				tmpNode=null;
			}
			else {
				
				tmpArc=onArc(e);
				if(tmpArc!=null)
				{//Clic sur un arc
					if(tmpArc.isSelected()  && !selectionNNShape.noNode() )
					{
						//cas ou il y a deja une selection
						xSelectionStart=e.getX();
						ySelectionStart=e.getY();
						_MiniMode=SELECTIONMADE;
						
					}
					else{
						
						selectionNNShape.emptySelection();
						selectionNNShape.addArc(tmpArc);
						xSelectionStart=e.getX();
						ySelectionStart=e.getY();
						_MiniMode=ARCMOVE;
						
					}
					//tmpArc=null;
				}
				
				else{
					//Clic dans le vide
					if(!e.isShiftDown())
						selectionNNShape.emptySelection();
					
					_MiniMode=RECTSELECTION;
					xSelectionStart=e.getX();
					ySelectionStart=e.getY();
				}
				
			}
			break;
		case ARCMODE:
			System.err.println("Press : ArcMode");
			switch(_MiniMode){
			case NODE0ARCMODE:
				tmpNode=onNode(e);
				if(tmpNode!=null)
				{
					_MiniMode=NODE1ARCMODE;
					selectionNNShape.emptySelection();
					selectionNNShape.addNode(tmpNode);
					System.err.println("\tStart Node Selected");
				}
				break;
			case NODE1ARCMODE:
				tmpNode=onNode(e);
				if(tmpNode!=null)
				{
					//Creating a new Arc
					try {
						tmpArc=new NNArc(selectionNNShape.firstNode(),tmpNode);
					} catch (Throwable e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					listNNShape.addArc(tmpArc);
					
					
					_MiniMode=NODE0ARCMODE;
					selectionNNShape.addNode(tmpNode);
					selectionNNShape.addArc(tmpArc);
					selectionNNShape.confirmSelection();
					frame.formNode.update();
					frame.formArc.update();
					System.err.println("\tEnd Node Selected");
					
				}
				break;
				
			default:System.err.println("Press : ArcMiniMode : Switch Error");
			}
			break;
		default:System.err.println("Press : Switch Error");
		
		}
		repaint();		
	}
	
	public void mouseReleased(MouseEvent e) {
		
		
		
		
		
		switch (_GeneralMode){
		case NODEMODE:
			break;
		case SELECTIONMODE:
			selectionNNShape.confirmSelection();
			
			if(frame.isGrid())
			{
				NNNode selectNode;
				
				Enumeration enumeration;
				enumeration = selectionNNShape.getNodes();
				
				while(enumeration.hasMoreElements()) 
				{ 
					selectNode=((NNNode)(enumeration.nextElement()));
					selectNode.replaceForGrid();
				}
				
			}
			
			
			if(_MiniMode==ARCMOVE)
			{
				tmpArc=null;
				
			}
			tmpNode=null;
			tmpRect=null;
			_MiniMode=SELECTIONMADE;
			frame.formNode.update();
			frame.formArc.update();
			
			repaint();
			break;
		case ARCMODE:
			
			break;
		default:System.err.println("Drag : Switch Error");
		}
	}
	
	
	
	private NNNode onNode(MouseEvent e)
	{
		NNNode selectNode;
		Enumeration enumeration;
		enumeration = listNNShape.getNodes();
		
		while(enumeration.hasMoreElements()) 
		{ 
			selectNode=((NNNode)(enumeration.nextElement()));
			if(selectNode.contains(e.getX(),e.getY()))
				return selectNode;
		}
		
		
		return null;
	}
	
	private NNArc onArc(MouseEvent e)
	{
		NNArc selectArc;
		Enumeration enumeration;
		enumeration = listNNShape.getArcs();
		
		while(enumeration.hasMoreElements()) 
		{ 
			selectArc=((NNArc)(enumeration.nextElement()));
			if(selectArc.contains(e.getX(),e.getY()))
				return selectArc;
		}
		
		
		return null;
	}
	
	
	
	/*
	 * Not in use
	 * */
	
	
	public void mouseClicked(MouseEvent e) {
		switch (_GeneralMode){
		case NODEMODE:
			//		System.err.println("Click : NodeMode");
			break;
		case SELECTIONMODE:
			if (e.getClickCount() == 2)  {
					System.err.println("Click : SelectionMode Double Click");
			
					tmpNode=onNode(e);
					if(tmpNode!=null && tmpNode.hasUnderNode())
					{
						NNFrame myFrame = new NNFrame();
						myFrame.setTitle("Neural Network Editor 2: Subteo");
						
						myFrame.openSubNetwork(tmpNode.getUnderNode());
						
					}
			}
					break;
		case ARCMODE:
			//		System.err.println("Click : ArcMode");
			break;
		default:System.err.println("Click : Switch Error");
		
		}
	}
	
	
	public void mouseEntered(MouseEvent e) {
		
	}
	
	public void mouseExited(MouseEvent e) {
		
	}
	
	public void mouseMoved(MouseEvent e) {
		
	}
	
}