/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 24 mai 2006
 * 
 */

package piconode.tutorials;

import piconode.core.node.SelfOrganizingMap;
import piconode.toolbox.Display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JFrame;

public class Tutorial_5_SelfOrganizingMap extends JFrame implements Runnable {

	Thread thread;
	SelfOrganizingMap map;

	// window parameters
	boolean _traceGrid = false; // trace grid showing similarity btw neighbours (the more black, the more similar)
	int _windowDimension = 300;

	// default SOM parameters -- tweak these in the constructor below if you want to play...
	int _dx = 15; // width
	int _dy = 15; // height
	int _iterations = 1001;
	int _displayIt = 100;

	// monitoring parameters
	int log[];
	double _startTime;
	
	public Tutorial_5_SelfOrganizingMap ()
	{
		_startTime = System.currentTimeMillis();

		this.setTraceGrid(false);
		this.setWindowDimension(300);
		
		this._dx = 10; //15 //30 //50
		this._dy = 10; //15 //30 //50
		
		this._iterations = 100; //1001; //100001    // arbitrary setup - (note that the SOM may run much much longer than needed if nb of iterations is big - demo purpose :-) )
		this._displayIt = 2; //100; //1000
		
         map = new SelfOrganizingMap();
         map.initNetwork(3,_dx,_dy,1,_iterations);
         //map.setLambdaTimeConstant(40); // used only if method 2 for computing neighborhood 
        
         log = new int[_dx*_dy];
         
         thread = new Thread(this);
         thread.start();
	}

	/* start learning and display  */
	public void run()
	{
        for ( int i = 0 ; i != map.getTotalSteps() ; i++ )
        {	
	        	double[] array = new double[3];
	        	for ( int j = 0 ; j != 3 ; j++ )
	        		array[j] = Math.random();
	        	map.step(array);
	        	
	        	if ( i > map.getTotalSteps()/2 ) 
	        		log[map.getBMU()]++;
	    		
        		if ( i%_displayIt == 0 )
        		{
        			Display.info("iterations : " + i + " / " + this._iterations);
        			Display.info("learning rate is "+map._learningRate);
		        	repaint();
		    		try
		    		{
		    			Thread.sleep(500);
		    		} 
		    		catch (InterruptedException e) { }
        		}
        }
        this.close();
	}

	public void close()
	{
        Display.info("Summary of node BMU score during the last half iterations: ");
        for ( int i = 0 ; i != this._dx*this._dy ; i++ )
        {
			if ( i % this._dx == 0 )
				Display.info("");
			Display.info_nocr("" + log[i] + " ; " );
        }
        System.out.println("\nTerminated ("+ ((System.currentTimeMillis()-_startTime)/1000) +"s elapsed).");
	}
	
	public void paint(Graphics g)
	{
		// draws each node of the Som map in a 2D space coloring wrt. values 
		Dimension dims = getSize();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, dims.width, dims.height);
		int w = _dx;
		int h = _dy;
		g.setColor(Color.BLACK);
		
		// draw 
		for (int x = 0; x < w ; x++)
			for (int y = 0; y < h; y++)
			{
				double [] values = map.getOutputNeuronValues(x,y);
				
				g.setColor(new Color((int)(values[0]*255.0),(int)(values[1]*255.0),(int)(values[2]*255.0)));
				g.fillRect(x*dims.width/w, y*dims.height/h, (x+1)*dims.width/w, (y+1)*dims.height/h);
				
				double distance;
				double maxDistance = Math.sqrt( (double)map.getInputSize() ); 
				double colorNormalize = 255.0 / maxDistance;

				if ( _traceGrid == true )
				{
					if ( x - 1 >= 0 )
					{
						distance = map.getOutputNeuronAt(x-1,y).computeDistanceToTarget(values);
						int colorDistance = (int)(distance*colorNormalize);
						g.setColor( new Color (colorDistance,colorDistance,colorDistance) );
						g.drawLine(x*dims.width/w, y*dims.height/h, x*dims.width/w, (y+1)*dims.height/h);
					}
					if ( y - 1 >= 0 )
					{
						distance = map.getOutputNeuronAt(x,y-1).computeDistanceToTarget(values);
						int colorDistance = (int)(distance*colorNormalize);
						//System.out.print("" + colorDistance + " ; ");
						g.setColor( new Color (colorDistance,colorDistance,colorDistance) );
						g.drawLine(x*dims.width/w, y*dims.height/h, (x+1)*dims.width/w, y*dims.height/h);
					}
				}
			}
		//System.out.println("");
	}

	/**
	 * @return Returns the _traceGrid.
	 */
	public boolean isTraceGrid() {
		return _traceGrid;
	}
	/**
	 * @param grid The _traceGrid to set.
	 */
	public void setTraceGrid(boolean grid) {
		_traceGrid = grid;
	}
	/**
	 * @param dimension The _windowDimension to set.
	 */
	public void setWindowDimension(int dimension) {
		_windowDimension = dimension;
	}

	public static void main(String[] args) {

		Display.info("### Self-Organizing-Map Tutorial/Demo (classic \"Kohonen\" Map)");
		Display.info("Random Red/Green/Blue values are input into a 2D Self Organising Map");
		Display.info("Through time, the map will get organised so as to cluster similar valued");
		Display.info("3D RGB vectors in neighbouring 2D regions (This is a classic example).");
        
		Display.info("\nRunning...");

        Tutorial_5_SelfOrganizingMap frame = new Tutorial_5_SelfOrganizingMap(); // see constructors to play with parameters

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(frame._windowDimension , frame._windowDimension+20 );
		frame.setVisible(true);
        
        
	}

}
