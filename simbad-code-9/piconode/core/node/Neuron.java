/*
 * Created on 30 dec. 2004
 * bredeche(at)lri(point)fr
 *
 */
package piconode.core.node;

import java.util.ArrayList;

import piconode.XmlRepresentation;
import piconode.core.arc.*;
import piconode.core.operator.*;
import piconode.ext.*;
import piconode.toolbox.Display;


/**
 * The neuron class contains every thing needed to embed a functional neuron into a NeuralNetwork 
 * module. In particular it embeds a reference to an activation function used to compute the neuron
 * value and useful information about the neuron status (in/out/hidden). 
 */
public class Neuron extends NeuronalNode implements XmlRepresentation{

    /* ### Data ### */
    
	protected ActivationFunction _activationFunction = null;
	
	/* ### Constructors ### */
	
	 // a neuron *must* belong to a network
    public Neuron (NeuralNetwork __network)
    {
    	super(__network);
       	this.setActivationFunction(new ActivationFunction_logisticSigmoid()); // default activation function is logistic sigmoid
    }

    public Neuron (NeuralNetwork __network, ActivationFunction __activationFunctionObject) // a neuron *must* belongs to a network
    {
    	super(__network);
    	if ( this.isNetworkInput() == true )
        {
            System.out.println("Neuron::Neuron(-,-,-) - Input node should not have an activation function");
            System.exit(-1);
        }    		
        this.setActivationFunction(__activationFunctionObject); // default activation function is logistic sigmoid
    }
    
    public Neuron (NeuralNetwork __network, String __name) // a neuron *must* belongs to a network
    {
    	super(__network,__name);
       	this.setActivationFunction(new ActivationFunction_logisticSigmoid()); // default activation function is logistic sigmoid
    }
    
    public Neuron (NeuralNetwork __network, ActivationFunction __activationFunctionObject, String __name) // a neuron *must* belongs to a network
    {
    	super(__network,__name);
       	this.setActivationFunction(__activationFunctionObject); // default activation function is logistic sigmoid
    }
    
    
    /* ### Methods ### */
    

    /**
     * Compute output value of neuron according to input values and an activation function
     *
     */
    public void step ()
    {
        double value = 0;
        
        if ( isNetworkInput() == false )
        {
            // S_{0..n}(entryValue_i*arcWeight_i)
	        for ( int i = 0 ; i != getIncomingArcsListSize() ; i++ )
	        {
	            WeightedArc a = (WeightedArc)getIncomingArcAt( i );
	            value += a.getSourceNeuron().getOutputValue() * a.getWeightValue();
	        }
	        
	        // output=tranferFunction(output_of_S(...))
	        value = this.getActivationFunction().applyFunction(value);
	        
	        setValue(value);
        }
        else
        {
            System.err.println("Neuron::step() - not allowed on input node");
            System.exit(-1);
        }
    }
    
    protected ActivationFunction getActivationFunction()
    {
        if ( this._activationFunction == null ) // in *most* cases, this test is unnecessary
        {
            System.out.println("Neuron::getActivationFunction() - no activation function defined");
            System.exit(-1);
        }
        return ( this._activationFunction );
    }
 
	public void setActivationFunction( ActivationFunction __activationFunction )
    {
        this._activationFunction = __activationFunction;
    }

	 /**
     * @author Thomas Darde
     * The xml is defined by piconode.visual
     * @return an Xml representation of this object
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
