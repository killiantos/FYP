/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 16 févr. 2006
 * 
 */

package piconode.core.node;

import java.util.ArrayList;

import piconode.XmlRepresentation;

/** 
 * General Node class from which is derived every elements from atomic node 
 * (e.g. neurons, bayesian node) to modules (e.g. neural nets, bayesian nets, 
 * Dynamic Systems implementations, etc.)
 */
public abstract class Node implements XmlRepresentation{

	/* ### data ### */
	
    protected	String	_name = new String("(node)");

    /* ### Constructor(s) ### */
    
    public Node ()
    {
    }
    
    /* ### methods ### */
    
    final public void setName (String __name)
    {
        this._name = __name;
    }

    final public String getName ()
    {
        return ( this._name );
    }
    
	/**
	* display information 
	*/
	abstract public void displayInformation();
	
	/**
	 * compute the output of a node/module
	 */
	abstract public void step ();
	
	/**
	 * Thomas Darde TER NNEditor
	 * Allows an object to give an XML representation from himself
	 */
	 /**
     * Thomas Darde TER NNEDITOR
     * Provides an xml representation of this element
     * Xml DTD is defined in visual piconode
     */
    public String toXml(){
		String xmlString;
		xmlString="<node>";
		xmlString+="<name at=\"" + this.getName() +"\"/>"
			+ "<id at=\"" + this.hashCode() +"\"/>";
		xmlString+= "</node>";
		return xmlString;
    }
}
