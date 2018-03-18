/*
 * Created on 30 dec. 2004
 * Author : bredeche(at)lri(point)fr
 */

package piconode.core.node;

import java.util.ArrayList;

import piconode.XmlRepresentation;
import piconode.core.arc.*;
import piconode.core.operator.*;
import piconode.toolbox.*;


/** 
 * This class implements the basis for a recurrent neural network (no special ability).
 */
public class RecurrentNeuralNetwork extends NeuralNetwork implements XmlRepresentation {

    /**
     * create a new network with no bias neuron.
     */
	public RecurrentNeuralNetwork() // by default, network contains a biased entry linked to every neurons (except entry node)
    {
		super();
    }
    
    /**
     * Create a new network that contains a bias neuron. Activation function for arcs from bias neuron is given as parameter. 
     * @param __activationFunctionFromBiasNeuron
     */
    public RecurrentNeuralNetwork( ActivationFunction __activationFunctionForArcsFromBiasNeuron )
    {
    		super (__activationFunctionForArcsFromBiasNeuron);
    }
    
    
    /**
     * Perform initialization of the network. Should be called only once after all neurons and arcs are registered and input and ouput neurons are identified.
     *
     */
//    
    public void initNetwork () // TODO optimise...? [!n]-20060217
    {
        /* 
         * order neurons from _allNeuronsUnorderedList with the less arc-dependent neuron first (i.e. input, hidden, output).
         * (results will be in _allNeuronsList).
         * 
         */

        //ArrayList temporaryList = new ArrayList (); // this list will contains neurons for *one* given layer

        /* test network integrity (i.e. test if all non-input neurons are directly or indirectly connected to 1+ input neuron(s) */ 
        //Display.error("stop="+_inputNeuronsList.size());

        for ( int i = 0 ; i != this._inputNeuronsList.size() ; i++ )
            if ( ((Neuron)this._inputNeuronsList.get(i)).checkNetwork(this) == false )
                Display.error(this.getClass().getName()+"::initNetwork() - network contains either unreferenced or badly referenced neuron(s)");
            
        // after the previous step, if every thing goes well, all non-input neurons should have _isNewValueBuffered set to true.
        // if not, means that there is/are stand-alone neuron(s) -- will be tested after removing input nodes from unordered list (see later.)
        // NOTE : the purpose of _isNewValueBuffered has nothing to do with this initialisation but is merely used because
        //        a boolean is needed -- value is restored (i.e. set to false) right after this (see last else statement in the following code) 
                
        	for ( int i = 0 ; i != this._allNeuronsUnorderedList.size() ; i++ )  
        		if ( ((RecurrentNeuron)this._allNeuronsUnorderedList.get(i)).isNetworkInput() == false ) // ignore input nodes
        			if ( ((RecurrentNeuron)this._allNeuronsUnorderedList.get(i))._isNewValueBuffered == false ) // check if already visited from input nodes (previous step)
        				Display.error(this.getClass().getName()+"::initNetwork() - non-input stand-alone neurons not allowed.");
        			else
        				((RecurrentNeuron)this._allNeuronsUnorderedList.get(i))._isNewValueBuffered = false; // re-init value 
        				
        /* if required, add a bias neuron to the input and unordered neuron lists */
        
        if ( this.containsBiasNeuron() == true )
        {
            this.addBiasNeuron();
        }		

        /* move input to ordered list (and remove them from _allNeuronsUnorderedList) */

        if ( _inputNeuronsList.size() == 0 || _outputNeuronsList.size() == 0 )
        {
		    	System.err.println("Network::initNetwork() - no input and/or output defined");
		    	System.exit(-1);                    
        }
        
        for ( int i = _inputNeuronsList.size()-1 ; i != -1 ; i-- ) // reverse order (at the beginning we know that sizeOf(list)>0)
        {
            this._nodeList.add(_inputNeuronsList.get(i));
            this._allNeuronsUnorderedList.remove(_inputNeuronsList.get(i));
        }

        /* remove output neurons from the unordered list (will be added to ordered list later) */
        
        for ( int i = 0 ; i != _outputNeuronsList.size() ; i++ ) 
        {
            this._allNeuronsUnorderedList.remove(_outputNeuronsList.get(i));
        }

        
        /* move and order all other neurons, order does not matter since signal forwarding is on a one-to-one basis
         * i.e. while in feed-forward nets, signal is computer from the outputs to the inputs in one complete steps (global update), 
         * recurrent nets only deal with local updates */
        
        /*if ( _allNeuronsUnorderedList.size() == 0 )
        {
		    System.err.println("Network::initNetwork() - empty list (i.e. no neuron)");  
		    System.exit(-1);            
        }*/
        
        for ( int i = 0 ; i != this._allNeuronsUnorderedList.size() ; i++ )
        		this._nodeList.add(this._allNeuronsUnorderedList.get(i));

        this._allNeuronsUnorderedList.clear();

        for ( int i = 0 ; i != this._outputNeuronsList.size() ; i++ )
        		this._nodeList.add(this._outputNeuronsList.get(i));

        setOldOutputRegisterSize(this._outputNeuronsList.size());
        setOutputRegisterSize(this._outputNeuronsList.size());
        setInputRegisterSize(this._inputNeuronsList.size());

        _init = true;
    }
    
    /**
     * Add a bias neuron and connect it to all node in the network
     * Bias neuron is also included in the Input neuron list.
     *
     */
    protected void addBiasNeuron()
    {
	    // create and record the bias neuron
	    _biasNeuron = new RecurrentNeuron(this, "bias");
	    _biasNeuron.setNetworkInputFlag(true);
	    _biasNeuron.setInputValue(1);
	    this.registerInputNeuron(_biasNeuron);
	    
	    // add arcs from the bias neuron to all neurons in the network (except input nodes)
	    for ( int i = 0 ; i != _allNeuronsUnorderedList.size() ; i++ )
	    {
	        if ( ((Neuron)_allNeuronsUnorderedList.get(i)).isNetworkInput() == false )
	            registerBiasArc(new WeightedArc(_biasNeuron, (Neuron)_allNeuronsUnorderedList.get(i), Tools.getArcWeightRandomInitValue()));
	    }
    }
    
	protected ConnectedNode getNodeAt (int __index)
	{
	    return ( (RecurrentNeuron)_nodeList.get(__index) );
	}

    /**
     * compute output values from input values (i.e. f(in)=out).
     * @param __inputValuesList
     */
    public void step( ArrayList __inputValuesList )
    {
        	if ( _init == false )
        	{
        	    System.out.println("ReccurentNetwork::computeOutput(-) - network not initialized!");
        	    System.exit(-1);
        	}
     
        	if ( ( this.containsBiasNeuron() == false && __inputValuesList.size() != _inputNeuronsList.size() ) || ( this.containsBiasNeuron() == true && __inputValuesList.size() != _inputNeuronsList.size()-1 ) )
        	{
        	    System.out.println("Network::computeOutput(-) - input values mismatch");
        	    System.exit(-1);
        	}
        	
        	// copy new input values into input nodes (note : if bias neuron exists, it is located at the end of the input nodes list, so it is ignored)
        	for ( int i = 0 ; i != __inputValuesList.size() ; i++ )
        	{
        	    ((RecurrentNeuron)_inputNeuronsList.get(i)).setInputValue(((Double)__inputValuesList.get(i)).doubleValue());
        	}
        	
        	// feed-forward signal to compute output *new* neurons values.
        	// current neuron values are unaltered and new values are "buffered".
        	// do not compute neuron values for input nodes.
        	for ( int i = _inputNeuronsList.size() ; i != _nodeList.size() ; i++ )
        	{
        	    ((RecurrentNeuron)_nodeList.get(i)).step();
        	}
        	
        	// for every neuron, copy the new "buffered" value to current value
        	this.updateNetwork();
        	
        	// recopy actual output values as old output values
        	
        	for (int i=0;i<getOutputRegisterSize();i++)
        		setOldOutputRegister(i,getOutputRegister(i));

    }
    
    

    /**
     * update all nodes in the network -- must be performed at the end of the step method
     */
    
    protected void updateNetwork ()
    {
    		for ( int i = this.getInputNeuronListSize() ; i != this.getNodeListSize() ; i++ )
    			((RecurrentNeuron)this.getNodeAt(i)).updateValue();
    }
    
   /**
    * perform one iteration step using input data from incoming nodes if any -- arity must be correct
    * @param __valuesList
    */
    public void step()
    {
    		Misc.notImplemented(this,"step()");
    		System.exit(-1);
    }
    
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

