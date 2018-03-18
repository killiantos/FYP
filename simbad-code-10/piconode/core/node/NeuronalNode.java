/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 18 mai 2006
 * 
 */

package piconode.core.node;

import piconode.XmlRepresentation;
import piconode.core.arc.Arc;
import piconode.core.arc.ConstrainedArc;
import piconode.core.arc.WeightedArc;
import piconode.core.operator.ActivationFunction;
import piconode.ext.ActivationFunction_logisticSigmoid;
import piconode.toolbox.Misc;


// TODO : this is an output kohonen neuron
public class NeuronalNode extends ConnectedNode implements XmlRepresentation{

    /* ### Data ### */
    
    protected	NeuralNetwork	_network = null; // network where neuron is registered
    
    protected	double	_neuronValue = 0;
	protected boolean _isInputNode = false;
	protected boolean _isOutputNode = false;

	
    /* ### Constructors ### */
	
	 // a neuron *must* belong to a network
    public NeuronalNode (NeuralNetwork __network)
    {
    	super();
        _network = __network;
       	__network.registerNode(this);
    }

    public NeuronalNode (NeuralNetwork __network, String __name) // a neuron *must* belongs to a network
    {
    	super();
        this.setName(__name);
        _network = __network;
       	__network.registerNode(this);
    }
    
    /* ### Methods ### */
    
    public boolean isLearningNode()
    { 
        return ( false );
    }
    
    protected void setValue(double __neuronValue)
    {
        _neuronValue = __neuronValue;
    }

    protected void setNetworkInputFlag( boolean __flag )
    {
        if ( this.isNetworkOutput() == true && __flag == true)
        {
            System.out.println("NeuronalNode::setNetworkInputFlag(-) - neuron cannot be both input *and* output");
            System.exit(-1);
        } 
        else
            _isInputNode = __flag;
    }
    
    protected void setNetworkOutputFlag( boolean __flag )
    {
        if ( this.isNetworkInput() == true && __flag == true )
        {
            System.out.println("NeuronalNode::setNetworkOutputFlag(-) - neuron cannot be both input *and* output");
            System.exit(-1);
        }
        else
            _isOutputNode = __flag;
    }

    /**
     * Compute output value of neuron according to input values and an activation function
     *
     */
    //abstract public void step ();
    
    protected boolean checkNetwork (NeuralNetwork __network) // [!n] sous optimal. Parcours au pire NbEntrees*(NbTotal-NbEntree)
    {
        // self-check
        if ( _network != __network )
            return (false);

        // check children (if any) -- DO NOT HANDLE CYCLE
        for ( int i = 0 ; i != _outgoingArcsList.size() ; i++ ) 
            if (((WeightedArc)_outgoingArcsList.get(i)).getTargetNeuron().checkNetwork(__network) == false)
                return (false);
	        
        // default
        return (true);
    }
    
    
    public void displayInformation ()
    {
        System.out.print("# " + this.getName() + " ");
        
        if ( this.isNetworkInput() == true )
            System.out.print("[input]");
        else
            if ( this.isNetworkOutput() == true )
                System.out.print("[output]");
            
        if ( this.isLearningNode() == true && this.isNetworkInput() == false )
            System.out.print("[learn]");
            
        if ( this.isNetworkOutput() == true )
        {
	        System.out.println(" value( " + this.getOutputValue() + " ).");            
        }
        else
        {
	        System.out.print(" value( " + this.getOutputValue() + " ) -> ");
	        for ( int i = 0 ; i != this.getOutgoingArcsListSize() ; i++ )
	            System.out.print (" arc( " + (((WeightedArc)this.getOutgoingArcAt(i))).getWeightValue() + " , " +((WeightedArc)this.getOutgoingArcAt(i)).getTargetNeuron().getName() + " ) ");
	        System.out.println("");
        }
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
            this.setValue(__inputValue);
    }

    /**
     * This method is used to get the value of the neuron (usually, only output neurons values are of interest to the user). 
     * @return
     */
    
    public double getOutputValue ()
    {
        return (_neuronValue);
    }

	public boolean isNetworkInput()
	{
	    return (_isInputNode);
	}
	
	public boolean isNetworkOutput()
	{
	    return (_isOutputNode);
	}
	
	protected int getIncomingArcsListSize()
	{
	    if ( isNetworkInput() == true )
	    {
	        System.out.println("Neuron::getIncomingArcsListSize(-) - not allowed on input node");
	        System.exit(-1);
	    }
	    return (super.getIncomingArcsListSize());
	}

	protected Arc getIncomingArcAt(int __index)
	{
	    if ( isNetworkInput() == true )
	    {
	        System.out.println("Neuron::getIncomingArcAt(-) - not allowed on input node");
	        System.exit(-1);
	    }
	    return (super.getIncomingArcAt(__index));
	}

	public void addIncomingArc(ConstrainedArc __arc)
	{
	    if ( isNetworkInput() == true )
	    {
	        System.out.println("Neuron::addIncomingArc(-) - not allowed on input node");
	        System.exit(-1);
	    }
	    super.addIncomingArc(__arc);
	}

    public NeuralNetwork getNetwork()
    {
        return ( _network );
    }

	public void step() {
		Misc.notImplemented(this,"::step()");
	}
	
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
