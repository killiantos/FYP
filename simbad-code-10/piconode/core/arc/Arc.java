package piconode.core.arc;

import piconode.XmlRepresentation;
import piconode.toolbox.*;
import piconode.core.node.*;
import piconode.core.operator.*;

/*
 * Created on 30 dec. 2004
 * bredeche(at)lri(point)fr
 * 
 * Note: The Arc class is a general purpose class for modelling Arcs
 * between nodes. Arc are 1-to-1 link.
 *
 */
public class Arc implements XmlRepresentation{

    /* ### Data ### */
    
    protected		Node _source;
    protected		Node _target;
    
    /*
     * Constructors
     */

    public Arc ()
    {
    }

    
    public Arc (Node __source, Node __target)
    {
        _source = __source;
        _target = __target;

        //_source.addOutgoingArc(this);
        //_target.addIncomingArc(this);
    }

    /*
     * Methods
     */
    
    public Node getSource()
    {
        return (_source);
    }

    public Node getTarget()
    {
        return (_target);
    }

    /**
     * Thomas Darde TER NNEditor
     * @return an Xml String representation of this objetc
     */
    public String toXml(){
    		String retour="<link";
    		retour+=" idFrom=\""+ _source.hashCode()+ "\"";
    		retour+=" idTo=\""+ _target.hashCode()+ "\">";
    		retour+="</link>";
    		return retour;
    }
    
    



}
