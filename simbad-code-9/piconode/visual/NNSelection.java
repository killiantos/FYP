package piconode.visual;

import java.util.Enumeration;
import java.util.Vector;


class NNSelection extends NNList {
	
	NNSelection(){
		super();
	}
	
	void describeSelection()
	{
		Enumeration selection;
		selection=super.getNodes();
		while(selection.hasMoreElements()){
			System.out.println( ((NNNode) selection.nextElement() ).getName());
		}
	
	}
	
	void addNode(NNNode node_arg){
		node_arg.setInverseTempSelection(true);
		
		if (!node_arg.isSelected()) {
			super.addNode(node_arg);
		}
	}
	
	void addArc(NNArc arc_arg){
		
		
		if (!arc_arg.isSelected() && !arc_arg.isInverseTempSelection() ) 
			super.addArc(arc_arg);
		
		arc_arg.setInverseTempSelection(true);
	}
	
	
	void resetWeigthSelection(NNList list){
		int i=0;
		int vectorSize=arcVector.size();
		
		NNArc nextArc;
		while (i<vectorSize)
		{
			nextArc=(NNArc) arcVector.get(i);
			nextArc.set_decalage(0);
			i++;
		}


	}

	void deleteSelection (NNList list){
		NNNode nextNode;
	
		int i=0;
		int vectorSize=nodeVector.size();
		
		while (i<vectorSize)
		{
			nextNode=(NNNode) nodeVector.get(i);
			nextNode.delete(list);
			list.removeNode(nextNode);
			nodeVector.remove(i);
			vectorSize--;
		}
		
		vectorSize=arcVector.size();
		
		NNArc nextArc;
		while (i<vectorSize)
		{
			nextArc=(NNArc) arcVector.get(i);
		//	nextArc.delete(list);
			list.removeArc(nextArc);
			arcVector.remove(i);
			vectorSize--;
		}


	}
	
	void duplicateSelection(NNList list){
		
		NNNode nextNode,newNode;
		NNArc nextArc,newArc;
		NNCorrespondance idC;
		
		idC=new NNCorrespondance();
		int i=0;
		int vectorSize=nodeVector.size();
		
		while (i<vectorSize){
			nextNode=(NNNode) nodeVector.get(i);
			newNode=new NNNode(nextNode);
			
			list.addNode( newNode );
			this.addNode( newNode );
			idC.addCorresondance(nextNode.getMyId(),newNode.getMyId());			
			i++;
		}
		i=0;
		while (i<vectorSize){
			((NNNode)nodeVector.get(0)).setSelected(false);
			((NNNode)nodeVector.get(0)).setInverseTempSelection(false);
			nodeVector.remove(0);
			i++;
		}
		
//ARCS		
		i=0;
		vectorSize=arcVector.size();
		
		while (i<vectorSize){
			nextArc=(NNArc) arcVector.get(i);
			newArc=new NNArc(nextArc,this.getNodeById(""+idC.getCorrespondance(nextArc.getNodeFrom().getMyId()))
					,this.getNodeById(""+idC.getCorrespondance(nextArc.getNodeTo().getMyId())));
			list.addArc( newArc );
			this.addArc( newArc );
			i++;
			
		}
		
		i=0;
		while (i<vectorSize){
			((NNArc)arcVector.get(0)).setSelected(false);
			((NNArc)arcVector.get(0)).setInverseTempSelection(false);
			arcVector.remove(0);
			i++;
		}
		this.confirmSelection();
		
	}
	
	
	
	
	void resetSelection(){
		NNNode nextNode;
		NNArc nextArc;
	
		int i=0;
		int vectorSize=nodeVector.size();
		
		while (i<vectorSize)
		{
			nextNode=(NNNode) nodeVector.get(i);
			if(nextNode.isSelected()){
				nextNode.setInverseTempSelection(false);
				i++;
			}
			else {
				nextNode.setInverseTempSelection(false);
				nodeVector.removeElementAt(i);
				vectorSize--;
			}
			
		}
		
		
		i=0;
		vectorSize=arcVector.size();
		
		while (i<vectorSize)
		{
			nextArc=(NNArc) arcVector.get(i);
			if(nextArc.getNodeFrom().isSelected() && nextArc.getNodeTo().isSelected()){
				nextArc.setInverseTempSelection(false);
				i++;
			}
			else {
				nextArc.setInverseTempSelection(false);
				arcVector.removeElementAt(i);
				vectorSize--;
			}
			
		}
	}
	
	void confirmSelection(){
		//describeSelection();
		NNNode nextNode;
		NNArc nextArc;
		
		
		int i=0;
		int vectorSize=nodeVector.size();
		
		while (i<vectorSize)
		{
			nextNode=(NNNode) nodeVector.get(i);
			
			if(nextNode.isSelected()){
				if(nextNode.isInverseTempSelection()){
					nextNode.setInverseTempSelection(false);
					nextNode.setSelected(false);
					nodeVector.removeElementAt(i);
					vectorSize--;
					
				}
				else i++;	
			}
			else {
				if(nextNode.isInverseTempSelection())
					{
					nextNode.setInverseTempSelection(false);
					nextNode.setSelected(true);
					i++;
					}
				else {
					nodeVector.removeElementAt(i);
					vectorSize--;		
				}
			}
		}
			
			
			
		i=0;
		vectorSize=arcVector.size();
		
		while (i<vectorSize)
		{
			nextArc=(NNArc) arcVector.get(i);
			
			if(nextArc.isSelected()){
				if(nextArc.isInverseTempSelection()){
					nextArc.setInverseTempSelection(false);
					nextArc.setSelected(false);
					arcVector.removeElementAt(i);
					vectorSize--;
				}
				else{
					i++;
				}
			}
			else {
				if(nextArc.isInverseTempSelection()){
					nextArc.setInverseTempSelection(false);
					nextArc.setSelected(true);
					i++;
				}
				else{
					arcVector.removeElementAt(i);
					vectorSize--;
				}
			}
			
		}
		
		
	
		
	}
	
	void emptySelection(){
		NNNode nextNode;
		NNArc nextArc;
		Enumeration selection;
		selection=super.getNodes();
		
		while(selection.hasMoreElements()){
			nextNode=(NNNode) selection.nextElement();
			
			nextNode.setInverseTempSelection(false);
			nextNode.setSelected(false);
			
			
		}
		
		selection=super.getArcs();
		
		while(selection.hasMoreElements()){
			nextArc=(NNArc) selection.nextElement();
			
			nextArc.setInverseTempSelection(false);
			nextArc.setSelected(false);
			
			
		}
		
		super.removeAllNode();
		super.removeAllArc();
	}
	
}

class NNCorrespondance{
	Vector idCorrespondance;
	Vector idCorrespondance2;
	NNCorrespondance(){
		idCorrespondance=new Vector();
		idCorrespondance2=new Vector();
	}

	void addCorresondance(int a,int b){
		idCorrespondance.add(new Integer(a));
		idCorrespondance2.add(new Integer(b));
	}
	
	int getCorrespondance(int a){
		
		Enumeration enumeration = idCorrespondance.elements();
		int index=0;
		while (enumeration.hasMoreElements())
		{
			if( ((Integer) enumeration.nextElement()).intValue()==a)
				return ((Integer) (idCorrespondance2.elementAt(index)) ).intValue();
			else index++;
		}
		
		System.err.println("non trouve getCorrespondance");
		return -1;
	
	}
	
}
