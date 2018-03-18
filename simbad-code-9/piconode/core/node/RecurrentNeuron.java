/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 24 févr. 2006
 * 
 */

package piconode.core.node;

import piconode.XmlRepresentation;
import piconode.core.arc.WeightedArc;
import piconode.core.operator.*;
import piconode.ext.*;
import piconode.toolbox.Display;


public class RecurrentNeuron extends Neuron implements XmlRepresentation {

    /* ### Data ### */
    
    protected	double	_newNeuronValue = 0;
    protected 	boolean	_isNewValueBuffered = false;
	
    /* ### Constructors ### */
	
	public RecurrentNeuron(NeuralNetwork __network) {
		super(__network);
	}

    public RecurrentNeuron (NeuralNetwork __network, ActivationFunction __activationFunctionObject) // a neuron *must* belongs to a network
    {
		super( __network, __activationFunctionObject );
    }
    
    public RecurrentNeuron (NeuralNetwork __network, String __name) // a neuron *must* belongs to a network
    {
		super( __network, __name );
    }
    
    public RecurrentNeuron (NeuralNetwork __network, ActivationFunction __activationFunctionObject, String __name) // a neuron *must* belongs to a network
    {
		super( __network,  __activationFunctionObject, __name);
    }

    /**
     * update the neuron value with the new (memorised) value. called by ReccurentNetwork.updateNetwork() method
     */
    protected void updateValue()
    {
    		if ( this._isNewValueBuffered == false )
    			Display.error(this.getClass().getName()+"::updateValue() - already performed.");
    		if ( this._isInputNode == true )
    			Display.error(this.getClass().getName()+"::updateValue() - input value cannot be updated.");
    		this._neuronValue = this._newNeuronValue;
    		this._isNewValueBuffered = false;
    }
    
    protected boolean checkNetwork (NeuralNetwork __network) // [!n] sous optimal. Parcours au pire NbEntrees*(NbTotal-NbEntree)
    {
        // self-check 1 : belongs to same network?
        if ( _network != __network )
            return (false);
        
        //Display.info("test("+this.getName()+"): " + getOutgoingArcsListSize() + "," +this.isNetworkOutput());
        
        // self-check 2 : if no successor, is output neuron?
        if ( this.getOutgoingArcsListSize() == 0 && this.isNetworkOutput() == false )
        {
        		Display.error(this.getClass().getName()+"::checkNetwork(.) - a neuron with no successor must be registered as output node.");
        		return(false);
        }
        	
		// "check as checked", i.e. ensure this neuron is only visited once.
		this._isNewValueBuffered = true;
		
        // check children (if any)
        for ( int i = 0 ; i != _outgoingArcsList.size() ; i++ ) 
        {
        		RecurrentNeuron neuron = (RecurrentNeuron)((WeightedArc)_outgoingArcsList.get(i)).getTargetNeuron();
        		if ( neuron.isNetworkInput() == true ) // target cannot be another input node
        		{
        			Display.error(this.getClass().getName()+"::checkNetwork(.) - input node cannot be a target.");
        			return (false);
        		}
        		if ( neuron._isNewValueBuffered == false ) // already visited?
        			if ( neuron.checkNetwork(__network) == false) // no? visit.
        				return (false);
        }
	        
        // default
        return (true);
    }

    protected void setValue(double __neuronValue)
    {
    		if ( this._isNewValueBuffered == true )
			Display.error(this.getClass().getName()+"::step() - neuron is not up to date (i.e. a previous new value is still buffered).");
		//Display.debug(""+this.getClass().getName()+"::setValue() - check");
		this._newNeuronValue = __neuronValue;
		this._isNewValueBuffered = true;
    }
    
    /**
     * This method is used to set the value of an **input** neuron.
     * @param __inputValue
     */
    public void setInputValue(double __inputValue)
    {
        if ( this.isNetworkInput() != true )
        {
            System.out.println("Neuron::setInputValue(-) - not an input node");
            System.exit(-1);
        }
        else
        {
            this._neuronValue = __inputValue;
            //this._newNeuronValue = __inputValue;
        }
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
