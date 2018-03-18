/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 16 fŽvr. 2006
 * 
 */

package piconode.core.node;

import java.util.ArrayList;

import piconode.XmlRepresentation;
import piconode.core.arc.*;
import piconode.core.operator.*;
import piconode.toolbox.Display;
import piconode.toolbox.Tools;

/**
 * The NeuralNetwork class provides the minimal basis to define a neural network module. 
 */
public abstract class NeuralNetwork extends Network implements XmlRepresentation{

    /*
     * Data
     */

    protected		ArrayList			_outputNeuronsList = new ArrayList();
    protected		ArrayList			_inputNeuronsList = new ArrayList();
    protected		Neuron				_biasNeuron;
    protected		boolean				_containsBiasNeuron = false;
    protected		ActivationFunction 	_activationFunctionForArcsFromBiasNeuron;
    
	protected		ArrayList			_biasArcList = new ArrayList();
    
	protected		boolean				_init = false; // used to check if network is initialised before using it.
	protected ArrayList _allNeuronsUnorderedList = new ArrayList();
    
    /*
     * Constructors
     */


    
    /**
     * create a new network with no bias neuron.
     */
    public NeuralNetwork() // by default, network contains a biased entry linked to every neurons (except entry node)
    {
        _containsBiasNeuron = false;
        
    }
    
    /**
     * Create a new network that contains a bias neuron. Activation function for arcs from bias neuron is given as parameter. 
     * @param __activationFunctionFromBiasNeuron
     */
    public NeuralNetwork( ActivationFunction __activationFunctionForArcsFromBiasNeuron )
    {
        _containsBiasNeuron = true; // useful for later initialization
        _activationFunctionForArcsFromBiasNeuron = __activationFunctionForArcsFromBiasNeuron;
        
    }

    /*
     * Methods
     */

/*    protected void registerNeuron(AtomicNode __neuron)
    {
        if ( _init == true )
        {
            System.err.println("Network::registerNeuron() - cannot handle neuron registration after initialization");
            System.exit(-1); 
        }
        
        _allNeuronsUnorderedList.add(__neuron);
    }*/

    /**
     * Add a bias neuron and connect it to all node in the network
     *
     */
    protected void addBiasNeuron()
    {
	    // create and record the bias neuron
	    _biasNeuron = new Neuron(this, "bias");
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

	protected void registerBiasArc( WeightedArc __arc )
	{
	    _biasArcList.add(__arc);
	}	

	protected WeightedArc getBiasArcAt( int __index )
	{
	    if ( this.containsBiasNeuron() != true )
	    {
	        System.out.println("Network::getElementInBiasArcList(-) - network contains no bias neuron");
	        System.exit(-1);		        
	    }
	    
	    if ( __index >= getBiasArcListSize() || __index < 0 )
	    {
	        System.out.println("Network::getElementInBiasArcList(-) - out of range");
	        System.exit(-1);
	    }
	    
	    return((WeightedArc)_biasArcList.get(__index));
	}	
	
	public boolean containsBiasNeuron()
    {
        return (_containsBiasNeuron);
    }
	
	protected int getInputNeuronListSize()
	{
	    return ( _inputNeuronsList.size() );
	}
	
	protected int getNodeListSize ()
	{
	    return ( _nodeList.size() );
	}
	
	protected ConnectedNode getNodeAt (int __index)
	{
	    return ( (Neuron)_nodeList.get(__index) );
	}


	/**
	 * return the totat number of arcs including both standard arcs *and* "bias" arcs for which the source neuron is the bias neuron 
	 * @return
	 */
	public int getNumberOfAllArcs ()
	{
		return ( getArcListSize() + getBiasArcListSize() );
	}

	/**
	 * return a list with weights values from *all* arcs (i.e. std+bias)
	 * @return
	 */
	public ArrayList getWeightsFromAllArcs ()
	{
		ArrayList list = new ArrayList () ;
		list.addAll(this.getWeightsFromStdArcs());
		list.addAll(this.getWeightsFromBiasArcs());
		return(list);
	}
	
	
	public void setAllArcsWeightValues ( ArrayList __list )
	{
		if ( __list.size() != getNumberOfAllArcs() )
		{
			System.out.println("Network::setAllArcsWeightValues(-) - number of values and weights do not match (waiting for "+getNumberOfAllArcs()+", "+__list.size()+" given.)");
			System.exit(-1);
		}

		int index = 0;
		
		// load the weights for the standard arcs
		
		ArrayList stdWeightList = new ArrayList ();

		for ( ; index != this.getArcListSize() ; index++ )
		stdWeightList.add( new Double ( ((Double)__list.get(index)).doubleValue() ) );
		
		this.setStdArcsWeightValues(stdWeightList);

		// load the weights for the bias arcs
		
		ArrayList biasWeightList = new ArrayList ();

		for ( ; index != this.getArcListSize() + this.getBiasArcListSize()  ; index++ )
		biasWeightList.add( new Double ( ((Double)__list.get(index)).doubleValue() ) );
		
		this.setBiasArcsWeightValues(biasWeightList);
	}

	
	/**
	 * return the number of Arcs *only* from bias neuron (may be useful when loading/saving weights (e.g. for evolution))
	 * @return
	 */
	public int getBiasArcListSize ()
	{
	    if ( this.containsBiasNeuron() != true )
	    {
	        System.out.println("Network::setBiasArcsWeightValues(-) - network contains no bias neuron");
	        System.exit(-1);		        
	    }
	    
	    return (_biasArcList.size());
	}		

	/**
	 * return a list that contains all output neurons values 
	 */
	public int getOutputNeuronListSize ()
	{
	    return ( _outputNeuronsList.size() );
	}
	
	/**
	 * return a specific output neuron 
	 * @param __index
	 * @return
	 */
	public NeuronalNode getOutputNeuronAt (int __index)
	{
	    return ( (NeuronalNode)_outputNeuronsList.get(__index) );
	}

	/**
     * specify an input neuron.
     */
	public void registerInputNeuron(NeuronalNode __neuron)
    {
        __neuron.setNetworkInputFlag(true);
        _inputNeuronsList.add(__neuron);
    }
    
    /**
     * specify an output neuron.
     * @param __neuron
     */
    public void registerOutputNeuron(NeuronalNode __neuron)
    {
        __neuron.setNetworkOutputFlag(true);
        _outputNeuronsList.add(__neuron);
    }

    /**
     * register a "standard" arc into the network. 
     * in the special case of a neural network - arcs from the bias neuron are automaticaly registered
     * Thus, the user should not explicitly register "bias" arc. 
     * @param __arc
     */
    public void registerArc( ConstrainedArc __arc )
	{
	    _arcList.add((WeightedArc)__arc);
	}

    
    /**
     * set values for arcs *excepting* arcs that are from the biased neuron - useful for evolution (update arcs values through evolutionary process)
     * @param __valuesList
     */
    
    public void setStdArcsWeightValues ( ArrayList __valuesList ) 
	{
	    if ( __valuesList.size() != getArcListSize() )
	    {
	        System.out.println("Network::setStdArcsWeightValues(-) - number of values and weights do not match ("+__valuesList.size()+" given, waiting for "+getArcListSize()+")");
	        System.exit(-1);		        
	    }
	    
	    for ( int i = 0 ; i != __valuesList.size() ; i++ )
	    {
	        ((WeightedArc)_arcList.get(i)).setWeightValue(((Double)__valuesList.get(i)).doubleValue());
	    }
	}

    /**
     * return only values for arcs *excepting* those from the biased neuron - useful for evolution (update arcs values through evolutionary process)
     * @return
     */
	public ArrayList getWeightsFromStdArcs () 
	{
	    ArrayList valuesList = new ArrayList();
	    
	    for ( int i = 0 ; i != this.getArcListSize() ; i++ )
	    {
	        valuesList.add( new Double(((WeightedArc)this.getArcAt(i)).getWeightValue()) );
	    }
	    
	    return (valuesList);
	}
    
	/**
	 * set values for arcs for which the source *is* the bias neuron (useful for evolution (update arcs values through evolutionary process))
	 * @param __valuesList
	 */
    public void setBiasArcsWeightValues ( ArrayList __valuesList )  
	{
	    if ( this.containsBiasNeuron() != true )
	    {
	        System.out.println("Network::setBiasArcsWeightValues(-) - network contains no bias neuron");
	        System.exit(-1);	
	    }
	        
	    if ( __valuesList.size() != getBiasArcListSize() )
	    {
	        System.out.println("Network::setBiasArcsWeightValues(-) - number of values and weights do not match");
	        System.exit(-1);		        
	    }
	    
	    for ( int i = 0 ; i != __valuesList.size() ; i++ )
	    {
	        ((WeightedArc)_biasArcList.get(i)).setWeightValue(((Double)__valuesList.get(i)).doubleValue());
	    }
	}

	/**
	 * get values for arcs for which the source is the bias neuron (useful for evolution (update arcs values through evolutionary process))
	 * @param __valuesList
	 */
	public ArrayList getWeightsFromBiasArcs () // useful for evolution (update arcs values through evolutionary process)
	{
	    if ( this.containsBiasNeuron() != true )
	    {
	        System.out.println("Network::setBiasArcsWeightValues(-) - network contains no bias neuron");
	        System.exit(-1);		        
	    }
	    
	    ArrayList valuesList = new ArrayList();
	    
	    for ( int i = 0 ; i != this.getBiasArcListSize() ; i++ )
	    {
	        valuesList.add( new Double(this.getBiasArcAt(i).getWeightValue()) );
	    }
	    
	    return (valuesList);
	}
    
			
	/**
	 * display information on the neural network (structure, weight, learnable nodes, input and output values)
	 *
	 */
	public void displayInformation()
	{
	    System.out.println("### Network Information : summary ###");
	    for ( int i = 0 ; i != this.getNodeListSize() ; i++ )
	        (this.getNodeAt(i)).displayInformation();
	    System.out.println("");
	}

    protected void registerNode(ConnectedNode __neuron)
    {
        _allNeuronsUnorderedList.add(__neuron);
    }

    
    /**
	 * Perform one step on the whole Network. 
	 */
	public void stepModule () // should be abstracted
	{
		if ( this.isUpdated() == true ) 
			return; // nothing to do.
		else
			this.setUpdated(true); // this node is updated
	
		// Execute le(s) module(s) avant pour satisfaire les dependances.
		// ( ... ) --> ne pas faire, puisque pris en charge lors de getSourceOutputRegisterValue
		
		// save current register value as old values 
		for ( int i = 0 ; i != this.getOldOutputRegisterSize() ; i++ )
			this.setOldOutputRegister(i, this.getOutputRegister(i));

	
		if ( this.getInputRegisterSize() != this.getIncomingArcsListSize() )
			Display.error("NeuralNetwork::stepModule() - input sizes do not match");
		
		// get input from source (!n - opt note : could be optimized)
		for ( int i = 0 ; i != this.getInputRegisterSize() ; i++ )
			this.setInputRegister(i, ((ModularArc)this.getIncomingArcAt(i)).getSourceOutputRegisterValue());
		
		// perform one running step (note that the step() method should also update output registers)  
		this.step();
		
		// copy output values into output register
		if ( this.getOutputRegisterSize() != this.getOutputNeuronListSize() )
			Display.error("NeuralNetwork::stepModule() - output sizes do not match");
		for ( int i = 0 ; i != this.getOutputRegisterSize() ; i++ )
			this.setOutputRegister(i,this.getOutputNeuronAt(i).getOutputValue());
		
		// call successors -- sub-optimal : N^2 calls in the worst case cmp to N for a blackboard approach (but worst case is *RARE*)
		for ( int i = 0 ; i != this.getOutgoingArcsListSize() ; i++ )
			((Module)((ModularArc)this.getOutgoingArcAt(i)).getTarget()).stepModule();
		
	}


    /*
	 * Thomas NNEditor
	 */
	public String toXml(){
		String retour="<file>\n<nodes>\n";

	   for ( int i = 0 ; i < _outputNeuronsList.size() ; i++ )
	        retour+=( (XmlRepresentation)  ( _outputNeuronsList.get(i))).toXml()+"\n";
	   for ( int i = 0 ; i < _inputNeuronsList.size() ; i++ )
	        retour+=( (XmlRepresentation)  ( _inputNeuronsList.get(i))).toXml()+"\n";

	   retour+="</nodes>\n<links>\n";
	   for ( int i = 0 ; i < _arcList.size() ; i++ )
	        retour+=((WeightedArc) _arcList.get(i)).toXml()+"\n";
	   retour+="</links>\n</file>\n";
	   return retour;
	}
    
}
