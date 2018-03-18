package piconode.visual;

import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.Vector;


public class NNList {

	
	protected Vector nodeVector;
	protected Vector arcVector;
	
	
	public NNList(){
		arcVector= new Vector();
		nodeVector=new Vector();
	}
	
	
	Enumeration getArcs() {
		return arcVector.elements();
	}
	
	Enumeration getNodes() {
		return nodeVector.elements();
	}
	
	void addNode(NNNode node_arg){
		nodeVector.addElement(node_arg);
	}
	
	void addArc(NNArc arc_arg){
		arcVector.addElement(arc_arg);
	}
	
	boolean isEmpty()
	{
		return getNodes().hasMoreElements();
	}
	
	boolean removeNode(NNNode shape_arg){
		return nodeVector.remove(shape_arg);	
	}
	
	void removeArc(NNArc shape_arg){
		arcVector.remove(shape_arg);	
	}

	void removeAllNode(){
		nodeVector.removeAllElements();
	}
	
	void removeAllArc(){
		arcVector.removeAllElements();
	}
	
	NNNode firstNode(){
		try{
		return (NNNode)nodeVector.firstElement();
		}catch(NoSuchElementException e)
		{
		return null;
		}
	}

	NNNode nodeAlone() {
		if(nodeVector.size()==1)
			return firstNode();
		return null;
	}

	boolean noNode(){
		if(nodeVector.size()==0)
			return true;
		else return false;
	
	}
	
	void removeNodeAt(int place)
	{
		nodeVector.removeElementAt(place);
	}
	
	void removeArcAt(int place)
	{
		arcVector.removeElementAt(place);
	}
	
	NNArc firstArc(){
		return (NNArc)arcVector.firstElement();
	}

	NNArc arcAlone() {
		if(arcVector.size()==1)
			return firstArc();
		return null;
	}
	
	boolean noArc(){
		if(arcVector.size()==0)
			return true;
		else return false;
	
	}

	void removeAll(){
		NNNode.resetNbNode();
		arcVector.removeAllElements();
		nodeVector.removeAllElements();
	}
	
	public NNNode getNodeById(String value) {
		
		int id=Integer.parseInt(value);
		NNNode nextNode;
		Enumeration enumeration=nodeVector.elements();
		while (enumeration.hasMoreElements()){
			nextNode=(NNNode)enumeration.nextElement();
			
			if(nextNode.getMyId()==id)
			{
				return nextNode;
			}
		}
		return null;
	}


	public void rearrange() {
		// TODO Auto-generated method stub
		int input=0, output=0,hidden=0;
		NNNode nextNode;
		Enumeration enumeration=nodeVector.elements();
		while (enumeration.hasMoreElements()){
			nextNode=(NNNode)enumeration.nextElement();
			if(nextNode.getType().equals(NNNode.types[NNNode._INPUT]))
				nextNode.replace(input++);
			else if(nextNode.getType().equals(NNNode.types[NNNode._OUTPUT]))
				nextNode.replace(output++);
			else	 if(nextNode.getType().equals(NNNode.types[NNNode._HIDDEN]))
				nextNode.replace(hidden++);
			
		}
		
	}
	
}
