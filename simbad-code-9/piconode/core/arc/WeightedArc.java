package piconode.core.arc;

import piconode.XmlRepresentation;
import piconode.toolbox.*;
import piconode.core.node.*;
import piconode.core.operator.*;


public class WeightedArc extends ConstrainedArc implements XmlRepresentation{

    /* ### Data ### */
   
    protected	double	_weightValue;
    
    /*
     * Constructors
     */

    public WeightedArc (NeuronalNode __sourceNeuron, NeuronalNode __targetNeuron)
    {
    	super (__sourceNeuron, __targetNeuron);
        _weightValue = Tools.getArcWeightRandomInitValue();
    }

    public WeightedArc (NeuronalNode __sourceNeuron, NeuronalNode __targetNeuron, double __weight)
    {
    	super (__sourceNeuron,__targetNeuron);
        _weightValue = __weight;
    }

    /*
     * Methods
     */
    
    public double getWeightValue()
    {
        return(_weightValue);
    }
    
    public void setWeightValue(double __weight)
    {
        _weightValue=__weight;
    }
    
    /**
     * Thomas Darde TER NNEditor
     * @return an Xml String representation of this objetc
     */
    public String toXml(){
    		String retour="<link";
    		retour+=" idFrom=\""+ _source.hashCode()+ "\"";
    		retour+=" idTo=\""+ _target.hashCode()+ "\">";
    		retour+="<weight at=\""+ getWeightValue()+ "\"/>";
    		retour+="</link>";
    		return retour;
    }
    
    
    
    
}
