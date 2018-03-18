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
 * This class implement a simple feed-forward neural network (no special properties) 
 */
public class FeedForwardNeuralNetwork extends NeuralNetwork implements XmlRepresentation{


    /**
     * create a new network with no bias neuron.
     */
	public FeedForwardNeuralNetwork ( ) 
	{
		_containsBiasNeuron = false;
	}

    /**
     * Create a new network that contains a bias neuron. Activation function for arcs from bias neuron is given as parameter. 
     * @param __activationFunctionFromBiasNeuron
     */
	public FeedForwardNeuralNetwork ( ActivationFunction __activationFunctionForArcsFromBiasNeuron ) 
	{
		_containsBiasNeuron = true; // useful for later initialization
		_activationFunctionForArcsFromBiasNeuron = (ActivationFunction)__activationFunctionForArcsFromBiasNeuron;
	}
	
    /**
     * Perform initialization of the network. Should be called only once after all neurons and 
     * arcs are registered and input and ouput neurons are identified.
     */
    public void initNetwork () // TODO optimise...? [!n]-20060217
    {
        /* 
         * order neurons from _allNeuronsUnorderedList with the less arc-dependent neuron first (i.e. input, hidden, output).
         * (results will be in _allNeuronsList).
         * 
         */

        ArrayList temporaryList = new ArrayList (); // this list will contains neurons for *one* given layer

        /* test network integrity (i.e. test if all non-input neurons are directly or indirectly connected to 1+ input neuron(s) */ 
          
        for ( int i = 0 ; i != _inputNeuronsList.size() ; i++ )
            if (((Neuron)_inputNeuronsList.get(i)).checkNetwork(this) == false)
            {
                System.out.println("Network::initNetwork() - network contains either unreferenced or badly referenced neuron(s)");
                System.exit(-1);
            }
        
        /* if required, add a bias neuron */
        
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
            _nodeList.add(_inputNeuronsList.get(i));
            _allNeuronsUnorderedList.remove(_inputNeuronsList.get(i));
        }
                
        /* move and order all other neurons, layers close to inputs are moved first */
        
        if ( this._allNeuronsUnorderedList.size() == 0 )
        {
		    System.err.println("Network::initNetwork() - empty list (i.e. no neuron)");
		    System.exit(-1);            
        }
        else
            if ( this._outputNeuronsList.size() == 0 )
            {
                System.err.println("Network::initNetwork() - no output defined");
                System.exit(-1);
            }
        
        while ( _allNeuronsUnorderedList.size() > 0 ) // at the beginning, we know that it is > 0 -- loop until _allNeuronsUnorderedList is empty
        {
            int i = 0;
            boolean isCycle = true;
            
            while ( i != _allNeuronsUnorderedList.size() && _allNeuronsUnorderedList.size() > 0 )  // identify neurons that belong to current layer
            {
                Neuron currentNeuron = (Neuron)_allNeuronsUnorderedList.get(i);
                boolean isToCopy = true;
                
                // test if all parents of neurons are already in _allNeuronsList [yes: copy=true, no: copy=false]
                for ( int j = 0 ; j!= currentNeuron.getIncomingArcsListSize() ; j++ )
                {
                    // is father k already in _allNeuronsList ? 

                    boolean isParentRegistered = false;
                    
                	   for ( int k = 0 ; k != _nodeList.size() ; k++ )
                	   {
                	       if ( ((WeightedArc)currentNeuron.getIncomingArcAt(j)).getSourceNeuron().equals(_nodeList.get(k)) == true )
                	       {
                	           isParentRegistered = true;
                	           break;
                	       }
                	   }
                	   
                	   if ( isParentRegistered == false )
                	   {
                	       isToCopy = false;
                	       break; // this neuron cannot be handled now (i.e. will be handled later)
                	   }
                }
                
                if ( isToCopy == true ) // do we have to handle this neuron (i.e. move it to temporaryList)
                {
                    temporaryList.add(_allNeuronsUnorderedList.get(i));
                    _allNeuronsUnorderedList.remove(i); // on incremente pas i
                    isCycle = false;
                }
                else // no? --> ignore this neuron for now
                    i++;
            }
            
            // copy new layer to _allNeuronsList
            for ( int j = 0 ; j != temporaryList.size() ; j++ )
                _nodeList.add(temporaryList.get(j));
            temporaryList.clear();
            
            if ( isCycle == true ) // no change for this pass (i.e. no new dependencies resolved) --> cycle detected! [error]
            {
                System.out.println("Network::initNetwork() - cannot handle network cycle");
                System.exit(-1);
            }
        }
        
        _allNeuronsUnorderedList.clear();

        setOldOutputRegisterSize(this._outputNeuronsList.size());
        setOutputRegisterSize(this._outputNeuronsList.size());
        setInputRegisterSize(this._inputNeuronsList.size());

        
        
        _init=true;
    }
    


    /**
     * compute output values from input values (i.e. f(in)=out).
     * @param __inputValuesList
     */
    public void step( ArrayList __inputValuesList )
    {
        	if ( _init == false )
        	{
        	    System.out.println("Network::computeOutput(-) - network not initialized!");
        	    System.exit(-1);
        	}
     
        	if ( ( this.containsBiasNeuron() == false && __inputValuesList.size() != _inputNeuronsList.size() ) || ( this.containsBiasNeuron() == true && __inputValuesList.size() != _inputNeuronsList.size()-1 ) )
        	{
        	    System.out.println("Network::computeOutput(-) - input values mismatch");
        	    System.exit(-1);
        	}
        	
        	// copy input values into input nodes (note : if bias neuron exists, it is located at the end of the input nodes list, so it is ignored)
        	for ( int i = 0 ; i != __inputValuesList.size() ; i++ )
        	{
        	    ((Neuron)_inputNeuronsList.get(i)).setInputValue(((Double)__inputValuesList.get(i)).doubleValue());
        	}

        	// signal feedforward-propagation and compute output neurons values.
        	for ( int i = _inputNeuronsList.size() ; i != _nodeList.size() ; i++ )
        	{
        	    ((Neuron)_nodeList.get(i)).step();
        	}
        	
     	//step 5 recopy actual output values as old output values
        	
        	for (int i=0;i<getOutputRegisterSize();i++)
        		setOldOutputRegister(i,getOutputRegister(i));
	
    }
    
   /**
    * perform one iteration step using input data from incoming nodes if any -- arity must be correct
    * @param __valuesList
    */
    public void step()
    {
    		Misc.notImplemented(this,"step");
    		System.exit(-1);
    }
    
    
    /**
     * Thomas Darde TER NNEDITOR
     * Provides an xml representation of this element
     * Xml DTD is defined in visual piconode
     */
	public String toXml(){
		String retour="<file>\n<nodes>\n";
	if(_nodeList.size()==0)
		System.out.println("node list is empty");
	   for ( int i = 0 ; i < _nodeList.size() ; i++ )
	        retour+=((Neuron) _nodeList.get(i)).toXml()+"\n";
	   retour+="</nodes>\n<links>\n";
	   for ( int i = 0 ; i < _arcList.size() ; i++ )
	        retour+=((WeightedArc) _arcList.get(i)).toXml()+"\n";
	   retour+="</links>\n</file>\n";
	   return retour;
	}
    
    
}

