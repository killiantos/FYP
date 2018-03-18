/*
 * Created on 30 dec. 2004
 * bredeche(at)lri(point)fr
 *
 */
package piconode.core.node;
import java.util.ArrayList;

import piconode.XmlRepresentation;
import piconode.core.arc.ConstrainedArc;
import piconode.core.arc.ArcForBackPropLearning;
import piconode.core.operator.ActivationFunctionWithDerivative;
import piconode.ext.ActivationFunction_logisticSigmoid;

/**
 * The NeuronForBackPropLearning class extends a Neuron with methods that makes it possible 
 * to embed such neurons into NeuralNetworks with Back-propagation learning ability (e.g.
 * FeedForwardNeuralNetworkForBackPropLearning).
 */

public class NeuronForBackPropLearning extends Neuron implements XmlRepresentation{

    /*
     * Data
     */

    /* core data */
	private boolean 		_learningNodeFlag = true;
	private 	double		_deltaErrorSignal = 0; // used by the back-propagation learning algorithm
    
    /*
     * Constructors
     */

    public NeuronForBackPropLearning (FeedForwardNeuralNetworkForBackPropLearning __network) // a neuron *must* belongs to a network
    {
		super( __network );
    }
 
    public NeuronForBackPropLearning (FeedForwardNeuralNetworkForBackPropLearning __network, ActivationFunctionWithDerivative __activationFunctionObject) // a neuron *must* belongs to a network
    {
    		super( __network, __activationFunctionObject );
    }
    
    public NeuronForBackPropLearning (FeedForwardNeuralNetworkForBackPropLearning __network, String __name) // a neuron *must* belongs to a network
    {
		super( __network, __name );
    }
    
    public NeuronForBackPropLearning (FeedForwardNeuralNetworkForBackPropLearning __network, ActivationFunctionWithDerivative __activationFunctionObject, String __name) // a neuron *must* belongs to a network
    {
		super( __network, __activationFunctionObject, __name );
    }

    /*
     * Methods
     */
    
    public boolean isLearningNode()
    { 
        return ( _learningNodeFlag );
    }
    
    private void setDeltaErrorSignal( double __value )
    {
        _deltaErrorSignal = __value;
    }
    
    public double getDeltaErrorSignal( )
    {
        return ( _deltaErrorSignal );
    }

    /**
     * [backpropagation step 1]
     * delta_k = derivativeOfActivationFunction(x) * ( targetValue_k - outputValue_k )
     * @param __targetValue
     */
    protected void backprop_computeErrorTermOutputNeuron ( double __targetValue ) 
    {
        if ( this.isNetworkOutput() == false )
        {
            System.out.println("Neuron::backprop_computeErrorTerm(-) - cannot be applied to non-output neuron");
            System.exit(-1);
        }
        
        if ( this.isLearningNode() == true ) // consider this neuron for learning?
        	this.setDeltaErrorSignal( ((ActivationFunctionWithDerivative)this.getActivationFunction()).applyDerivativeFunction(this.getOutputValue()) * ( __targetValue - this.getOutputValue() ) ); 
        	
    }

    /**
     * [backpropagation step 2]
     * delta_k = derivativeOfActivationFunction(x) * ( Sum_allAxons ( weight_k * DeltaErrorSignal_targetedNeuronByk ) )
     */
    protected void backprop_computeErrorTermHiddenNeuron ( ) 
    {
        if ( this.isNetworkOutput() == true )
        {
            System.out.println("Neuron::backprop_computeErrorTerm() - cannot be applied to output neuron");
            System.exit(-1);
        }
        
        if ( this.isLearningNode() == false ) // consider this neuron for learning?
            return;
        
        double childrenSumValue = 0;
        
        for ( int i = 0 ; i != this.getOutgoingArcsListSize() ; i++ )
        {
            if ( ((ArcForBackPropLearning)this.getOutgoingArcAt(i)).getTargetNeuron().isLearningNode() == true ) // consider this child?
            {
                childrenSumValue += ((ArcForBackPropLearning)this.getOutgoingArcAt(i)).getWeightValue() * ((NeuronForBackPropLearning)((ArcForBackPropLearning)this.getOutgoingArcAt(i)).getTargetNeuron()).getDeltaErrorSignal();
            }
        }
        
        this.setDeltaErrorSignal( ((ActivationFunctionWithDerivative)this.getActivationFunction()).applyDerivativeFunction(this.getOutputValue()) * childrenSumValue ); 
    }


    /**
     * set the neuron status toward learning ("true" by default -- i.e. will be considered during back-propagation).
     * For instance, this is particularly useful if you intend to implement nolfi/parisi types of neural networks where only part of the network is modified during learning. 
     * Thus, if flag is *false*, every arcs targeting this neuron will *not* considered during learning.
     * @param __flag
     */
    
    public void setLearningNodeFlag( boolean __flag )
    {
        if ( _learningNodeFlag != __flag ) // modify?
        {
            _learningNodeFlag = __flag;
        }
        /*else
        {
            System.out.println("Neuron::setLearningNodeFlag(-) - [warning] - no change ("+this.getNeuronName()+")");
        }*/
    }

    /**
     * Thomas Darde
     * TER Visual Neuron Editor
     */
    public String toXml(){
 		String xmlString;
 		xmlString="<node>";
 		xmlString+="<name at=\"" + this.getName() +"\"/>"
 			+ "<id at=\"" + this.hashCode() +"\"/>";
 		if (isNetworkInput())
 			xmlString+="<type at=\"input\"/>";
 		else if (isNetworkOutput())
 			xmlString+="<type at=\"output\"/>";
 		else xmlString+="<type at=\"hidden\"/>";
 		xmlString+= "</node>";
 		return xmlString;
 }
    
}
