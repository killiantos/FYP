package piconode.visual;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import piconode.XmlRepresentation;
import piconode.core.node.*;


public class NNPiconode {

	
	
	public static int showNetwork(XmlRepresentation network){
		return showNetwork(network,"",1);
			
	}
	
	public static int showNetwork(XmlRepresentation network,String filename,int step){
		System.err.println("Showing the network");
		NNGui.returnOK=false;
		

		//if no filename specified, graphical representation is shown
		if(filename.equals("")){
			
			if(!NNGui.frameVisible)
			{	
				NNGui.myFrame = new NNFrame();
				NNGui.myFrame.setTitle("Neural Network Editor 2");
				NNGui.frameVisible=true;
			}
			
			loadNetwork(network);
			NNGui.myFrame.setPicoMode();
			while (!NNGui.returnOK)
			{}
			NNGui.myFrame.unSetPicoMode();
			return 0;
		}
		else{
			File f=new File(filename);
			try {
				PrintWriter output = new PrintWriter( new FileOutputStream(f,true), true );
				output.println("Step "+step);
				output.println(network.toXml());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
		}
		return 0;
		
	}
	
	public static void loadNetwork(XmlRepresentation network){
	
		
		NNGui.myFrame.prepareToOpenDocument();
		
		NNSaxDocument.parseString(network.toXml(),NNGui.myFrame);
		

		

		NNGui.myFrame._canvas.listNNShape.rearrange();
		
	}
	
}
