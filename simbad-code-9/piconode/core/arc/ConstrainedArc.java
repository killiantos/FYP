package piconode.core.arc;

import piconode.XmlRepresentation;
import piconode.toolbox.*;
import piconode.core.node.*;
import piconode.core.operator.*;

/*
 * Created on 30 dec. 2004
 * bredeche(at)lri(point)fr
 * 
 * Note: ConstrainedArc class is extended from the standard Arc class
 * with constraints about input/output nodes as source/target (i.e. input
 * node cannot be a target, output node cannot be a source).
 * Arity is 1-1
 *
 */
public class ConstrainedArc extends Arc implements XmlRepresentation{

    /* ### Data ### */
    
    /*
     * Constructors
     */

    public ConstrainedArc (NeuronalNode __sourceNeuron, NeuronalNode __targetNeuron)
    {
    		super ();

    		_source = __sourceNeuron;
        _target= __targetNeuron;
        
        if ( ((NeuronalNode)_target).isNetworkInput() == true )
        {
            System.err.println("Arc::Arc(1) - input node cannot be a target");
            System.exit(-1);
        }
        
        ((NeuronalNode)_source).addOutgoingArc(this);
        ((NeuronalNode)_target).addIncomingArc(this);
    }

    /*
     * Methods
     */
    
    public NeuronalNode getSourceNeuron()
    {
        return ((NeuronalNode)this.getSource());
    }

    public NeuronalNode getTargetNeuron()
    {
        return ((NeuronalNode)this.getTarget());
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
