/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 16 fŽvr. 2006
 * 
 */

package piconode.core.node;

import java.util.ArrayList;

import piconode.XmlRepresentation;
import piconode.core.arc.*;
import piconode.core.operator.*;

/**
 * The Network class provides the minimal basis to define a network module. 
 */
public abstract class Network extends Module implements XmlRepresentation{

    /*
     * Data
     */

    protected ArrayList _arcList = new ArrayList(); // std as in "standard". Means that it contains no arcs from bias neuron
	protected ArrayList _nodeList = new ArrayList();
    
    
    /*
     * Constructors
     */


    /*
     * Methods
     */

	protected ConstrainedArc getArcAt( int __index )
	{
	    if ( __index >= getArcListSize() || __index < 0 )
	    {
	        System.out.println("Network::getArcAt(-) - out of range");
	        System.exit(-1);
	    }
	    return((ConstrainedArc)_arcList.get(__index));
	}
	
	protected int getNodeListSize ()
	{
	    return ( _nodeList.size() );
	}
	
	protected ConnectedNode getNodeAt (int __index)
	{
	    return ( (ConnectedNode)_nodeList.get(__index) );
	}

	/**
	 * return the number of Arcs (if NeuralNetwork : excepting arcs from bias neuron (may be useful when loading/saving weights (e.g. for evolution)))
	 * @return
	 */
	public int getArcListSize ()
	{
	    return (_arcList.size());
	}

	/**
     * register an arc into the network (if NeuralNetwork : arcs from the bias neuron are automaticaly registered)
     * @param __arc
     */
    public void registerArc( ConstrainedArc __arc )
	{
	    _arcList.add(__arc);
	}

    
    /**
	 * display information on the network
	 *
	 */
	public void displayInformation()
	{
	    System.out.println("### Network Information : summary ###");
	    for ( int i = 0 ; i != this.getNodeListSize() ; i++ )
	        (this.getNodeAt(i)).displayInformation();
	    System.out.println("");
	}

    protected void registerNode(ConnectedNode __neuron)
    {
        _nodeList.add(__neuron);
    }
    
  

    
    /**
     * Thomas Darde TER NNEDITOR
     * Provides an xml representation of this element
     * Xml DTD is defined in visual piconode
     */
    
	public String toXml(){
		String retour="<file>\n<nodes>\n";
	if(_nodeList.size()==0)
		System.out.println("node list is empty");
	   for ( int i = 0 ; i < _nodeList.size() ; i++ )
	        retour+=((Neuron) _nodeList.get(i)).toXml()+"\n";
	   retour+="</nodes>\n<links>\n";
	   for ( int i = 0 ; i < _arcList.size() ; i++ )
	        retour+=((WeightedArc) _arcList.get(i)).toXml()+"\n";
	   retour+="</links>\n</file>\n";
	   return retour;
	}




}
