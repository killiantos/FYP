/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 15 févr. 2006
 * 
 */

package piconode.core.node;

import java.util.ArrayList;

import piconode.XmlRepresentation;
import piconode.core.arc.*;
import piconode.core.operator.*;
import piconode.ext.ActivationFunction_logisticSigmoid;

/**
 * The abstract ConnectedNode class contains the basis for defining a node that is part of 
 * a Network module. It contains only the minimal requirements to define a connected node (i.e.
 * incoming and outgoing arc lists).
 * Thanks to this class, note that a network can embed other networks (for e.g.)
 */
public abstract class ConnectedNode extends Node implements XmlRepresentation{

	/* ### data ### */
    
    protected	ArrayList	_incomingArcsList = new ArrayList(); // from source nodes... ([!n] - usually not used)
    protected	ArrayList	_outgoingArcsList = new ArrayList(); // ... to target nodes

    
    /* ### Constructors ### */
     
    public ConnectedNode ()
    {
    	super();
    }

    /* ### methods ### */

    protected ArrayList getIncomingArcsList()
    {
        return (_incomingArcsList);
    }

    protected ArrayList getOutgoingArcsList()
    {
        return (_outgoingArcsList);
    }
    
    protected int getIncomingArcsListSize()
    {
        return (_incomingArcsList.size());
    }

    protected int getOutgoingArcsListSize()
    {
        return (_outgoingArcsList.size());
    }

    protected Arc getIncomingArcAt(int __index)
    {
        return ((Arc)_incomingArcsList.get(__index));
    }

    protected Arc getOutgoingArcAt(int __index)
    {
        return ((Arc)_outgoingArcsList.get(__index));
    }
    
    public void addIncomingArc(Arc __arc)
    {
        _incomingArcsList.add(__arc);
    }
    
    public void addOutgoingArc(Arc __arc)
    {
        _outgoingArcsList.add(__arc);
    }

    public String toXml(){
		String xmlString;
		xmlString="<node>";
		xmlString+="<name at=\"" + this.getName() +"\"/>"
			+ "<id at=\"" + this.hashCode() +"\"/>";
		xmlString+= "</node>";
	
		
		return xmlString;
    }
    
}
