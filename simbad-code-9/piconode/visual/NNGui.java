package piconode.visual;

import javax.swing.JFrame;

/**
 * @author lri
 *
 */
public class NNGui {

	/**
	 * @param args
	 * No params have to be set for this application
	 */
	static NNFrame myFrame;
	static boolean frameVisible=false;
	static boolean returnOK=false;
	
	public static void main(String[] args) {
		System.err.println("Launching NNEditor2");
		
		
		myFrame = new NNFrame();
		myFrame.setTitle("Neural Network Editor 2");
		frameVisible=true;
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	}

	
	
	
}
