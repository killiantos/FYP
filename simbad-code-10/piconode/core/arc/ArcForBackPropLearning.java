package piconode.core.arc;

import piconode.XmlRepresentation;
import piconode.toolbox.*;
import piconode.core.node.*;
import piconode.core.operator.*;



public class ArcForBackPropLearning extends WeightedArc implements XmlRepresentation{

    /* ### Data ### */
    
    private		double	_bigDeltaWeightAdjustmentValue = 0; // learned parameter
   
    /*
     * Constructors
     */

    public ArcForBackPropLearning (NeuronForBackPropLearning __sourceNeuron, NeuronForBackPropLearning __targetNeuron)
    {
    	super (__sourceNeuron, __targetNeuron);
    }

    public ArcForBackPropLearning (NeuronForBackPropLearning __sourceNeuron, NeuronForBackPropLearning __targetNeuron, double __weight)
    {
		super (__sourceNeuron, __targetNeuron, __weight);
    }

    /*
     * Methods
     */
    
    protected double getBigDeltaWeightAdjustmentValue ()
    {
        return ( _bigDeltaWeightAdjustmentValue );
    }
    
    protected void setBigDeltaWeightAdjustmentValue( double __value )
    {
        _bigDeltaWeightAdjustmentValue = __value;
    }

    /**
     * [backpropagation step 3]
     * this._bigDeltaWeightAdjustmentValue = _defaultEtaLearningRate * _targetNeuron._deltaErrorSignal * _sourceNeuron._neuronValue
     */
    public void backprop_computeWeightAdjustment ()  
    {
        if ( this.getTargetNeuron().isLearningNode() == true ) // consider this arc for learning?
            this.setBigDeltaWeightAdjustmentValue( ((FeedForwardNeuralNetworkForBackPropLearning)this.getTargetNeuron().getNetwork()).backprop_getEtaLearningRate() * ((NeuronForBackPropLearning)this.getTargetNeuron()).getDeltaErrorSignal() * this.getSourceNeuron().getOutputValue() ); // note that to get etaLearningRate, you could use either sourceNeuron path or targetNeuron path...
    }
    
    /**
     * [backpropagation step 4]
     * this._weightValue = this._weightValue + this._bigDeltaWeightAdjustmentValue
     */
    public void backprop_updateWeight () 
    {
        if ( this.getTargetNeuron().isLearningNode() == true) // consider this arc for learning?
            this.setWeightValue( this.getWeightValue() + this.getBigDeltaWeightAdjustmentValue() );
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
