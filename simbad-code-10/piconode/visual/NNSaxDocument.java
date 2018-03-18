

package piconode.visual;

import java.awt.Color;
import java.io.*;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParserFactory;

import javax.xml.parsers.SAXParser;


public class NNSaxDocument extends DefaultHandler{
	
	/**
	 * @param args
	 */
	
	static NNFrame frame;
	static final int NOMODE=0;
	static final int NODEMODE=1;
	static final int ARCMODE=2;
	static int mode;
	static NNNode tmpNode;
	static NNArc tmpArc;
	
	static void open(String filename,NNFrame frame_arg) {
		mode=NOMODE;
		frame=frame_arg;
		DefaultHandler handler = new NNSaxDocument();
		// Use the default (non-validating) parser
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			
			// Parse the input
			SAXParser saxParser = factory.newSAXParser();
			saxParser.parse( new File(filename), handler);
			
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
		
	}
	
	static void subOpen(String subnet,NNFrame frame_arg) {
		mode=NOMODE;
		frame=frame_arg;
	//	DefaultHandler handler = new NNSaxDocument();
		// Use the default (non-validating) parser
	//	SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			
			// Parse the input
	//		SAXParser saxParser = factory.newSAXParser();
			parseString( subnet, frame_arg);
			
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
		
	}


	static void parseString(String stringToParse,NNFrame frame_arg) {
		System.err.println(stringToParse);
		mode=NOMODE;
		frame=frame_arg;
		DefaultHandler handler = new NNSaxDocument();
		// Use the default (non-validating) parser
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			
			// Parse the input
			SAXParser saxParser = factory.newSAXParser();
			saxParser.parse(new InputSource(new StringReader(stringToParse)),handler);
			//saxParser.parse( new StringReader(stringToParse) , handler);
			
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
		
	}
	
	
	
	public void startDocument()
	throws SAXException
	{
	}
	
	public void endDocument()
	throws SAXException
	{
		
		frame._canvas.repaint();
		System.err.println("Document is ready !");
		
		
	}
	
	public void startElement(String namespaceURI,
			String lName, // local name
			String qName, // qualified name
			Attributes attrs)
	{
		String eName = qName; // element name
		
		if (mode==NOMODE){
			if (eName.equals("node")){
				mode=NODEMODE;
				tmpNode=new NNNode();			
			}
			else if (eName.equals("link"))
			{
				mode=ARCMODE;
				
			
					tmpArc=new NNArc(frame._canvas.listNNShape.getNodeById(attrs.getValue(0)),
							frame._canvas.listNNShape.getNodeById(attrs.getValue(1)));
			}
			
			else if (eName.equals("nneditordescription"))
			{
				if (attrs.getValue(0).equals("true"))
					frame.setGrid( true);
				else frame.setGrid( false);
			}
		}
		else if (mode==NODEMODE){
			if (eName.equals("name")){
				tmpNode.setName(attrs.getValue(0));
			}
			else if (eName.equals("id")){
				tmpNode.setMyId(Integer.parseInt(attrs.getValue(0)));
			}
			else if (eName.equals("type")){
				tmpNode.setType(attrs.getValue(0));
			}
			else if (eName.equals("function")){
				tmpNode.setFunction(attrs.getValue(0));
			}
		
			else if (eName.equals("x")){
				tmpNode.setPositionX(Integer.parseInt(attrs.getValue(0)));
			}
			else if (eName.equals("y")){
				tmpNode.setPositionY(Integer.parseInt(attrs.getValue(0)));
			}
			else if (eName.equals("color")){
				tmpNode.setColor(new Color(
						Integer.parseInt(attrs.getValue(0)),
						Integer.parseInt(attrs.getValue(1)),
						Integer.parseInt(attrs.getValue(2))));
			}

		}
		else if (mode==ARCMODE){
			if (eName.equals("name")){
				tmpArc.setName(attrs.getValue(0));
			}
			else if (eName.equals("weight")){
				tmpArc.setWeight(Double.parseDouble(attrs.getValue(0)));
			}
			else if (eName.equals("color")){
				tmpArc.setColor(new Color(
						Integer.parseInt(attrs.getValue(0)),
						Integer.parseInt(attrs.getValue(1)),
						Integer.parseInt(attrs.getValue(2))));
			}

		}
		
		
	}
	
	public void endElement(String namespaceURI,
			String sName, // simple name
			String qName  // qualified name
	)
	throws SAXException
	{
		if (qName.equals("node")){
			tmpNode.generateId();
			frame._canvas.listNNShape.addNode(tmpNode);
			tmpNode=null;
			mode=NOMODE;
		}
		else if (qName.equals("link")){
		
			frame._canvas.listNNShape.addArc(tmpArc);
			tmpArc=null;
			mode=NOMODE;
		}
		
	}
	
	
	
	
	void emit (String st){
		System.err.println(st);
	}
	
}






