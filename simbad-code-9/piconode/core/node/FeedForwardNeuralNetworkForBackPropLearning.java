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
 * This class implement a feed-forward neural network with learning capabilitie through 
 * back-propagation. Such a network should be used exclusively with neurons that handles
 * this very ability (i.e. NeuronForBackPropLearning objects). 
 */
public class FeedForwardNeuralNetworkForBackPropLearning extends FeedForwardNeuralNetwork implements XmlRepresentation{

    /* ### Data ### */
	
    protected	ActivationFunctionWithDerivative 	_activationFunctionForArcsFromBiasNeuron; // redefined inherited
    protected	NeuronForBackPropLearning	_biasNeuron; // redefined inherited
    protected 	double			_etaLearningRate = 1; // used the retropagation algorithm (eta~=0 -> convergence with stability ; eta>>0 -> speed)
    
    
    /* ### Methods ### */

    /**
     * create a new network with no bias neuron.
     */
    FeedForwardNeuralNetworkForBackPropLearning() // by default, network contains a biased entry linked to every neurons (except entry node)
    {
        super();
    }
    
    /**
     * Create a new network that contains a bias neuron. Activation function for arcs from bias neuron is given as parameter. 
     * @param __activationFunctionFromBiasNeuron
     */
    public FeedForwardNeuralNetworkForBackPropLearning( ActivationFunction __activationFunctionForArcsFromBiasNeuron )
    {
		_containsBiasNeuron = true; // useful for later initialization
		_activationFunctionForArcsFromBiasNeuron = (ActivationFunctionWithDerivative)__activationFunctionForArcsFromBiasNeuron;
    }
    
    /*
     * Methods
     */

	protected void launchBackPropagationLearningAlgorithm ( ArrayList __targetValuesList ) // [!n] - method may/will be optimized
	{
	    
	    if ( __targetValuesList.size() != this.getOutputNeuronListSize() ) // [!n] - useless : test without and delete
	    {
	        System.out.println("Network::performBackPropagationLearning(-) - target and predicted lists sizes do not match");
	        System.exit(-1);
	    }

	    // step 1 : compute the error term for output neurons
	    for ( int i = 0 ; i != this.getOutputNeuronListSize() ; i++ )
	    {
	        ((NeuronForBackPropLearning)this.getOutputNeuronAt(i)).backprop_computeErrorTermOutputNeuron(((Double)(__targetValuesList.get(i))).doubleValue());
	    }
	    
	    // step 2 : compute the error term for *hidden* neurons only (reverse order so we can handle dependencies)
	    for ( int i = ( this.getNodeListSize() - this.getOutputNeuronListSize() - 1 ) ; i != this.getInputNeuronListSize() -1 ; i-- ) 
	    {
	    	((NeuronForBackPropLearning)this.getNodeAt(i)).backprop_computeErrorTermHiddenNeuron();
	    }	    
	    
	    // step 3 : compute all the weights delta
	    for ( int i = 0 ; i != this.getArcListSize() ; i++ )
	        ((ArcForBackPropLearning)this.getArcAt(i)).backprop_computeWeightAdjustment();
	    for ( int i = 0 ; i != this.getBiasArcListSize() ; i++ )
	        ((ArcForBackPropLearning)this.getBiasArcAt(i)).backprop_computeWeightAdjustment();
	    	    
	    // step 4 : adjust the weights with corrected values
	    for ( int i = 0 ; i != this.getArcListSize() ; i++ )
	        ((ArcForBackPropLearning)this.getArcAt(i)).backprop_updateWeight();
	    for ( int i = 0 ; i != this.getBiasArcListSize() ; i++ )
	        ((ArcForBackPropLearning)this.getBiasArcAt(i)).backprop_updateWeight();
	}

	/**
	 * This method is used to build a list suitable for internal use. If all output neurons flags are set to "learnable", input list will be returned
	 * @param __targetValuesRawList
	 * @return
	 */
	private ArrayList makeCleanTargetValuesList ( ArrayList __targetValuesRawList )
	{
	    int learningOutputListSize = 0;
	    int index = 0;
	    ArrayList targetValuesList = new ArrayList();
	    
	    if ( __targetValuesRawList.size() > this.getOutputNeuronListSize() )
	    {
	        System.out.println("Network::performBackPropagationLearning(-) - too many target values in list");
	        System.exit(-1);
	    }
	    
	    for ( int i = 0 ; i != this.getOutputNeuronListSize() ; i++ )
	        if ( this.getOutputNeuronAt(i).isLearningNode() == true )
	        {
	            targetValuesList.add((Double)__targetValuesRawList.get(index));
	            learningOutputListSize++;
	            index++;
	        }
	        else
	        {
	            targetValuesList.add(new Double (this.getOutputNeuronAt(i).getOutputValue())); // should not be corrected (useful when computing network error)
	        }
	    
	    if ( __targetValuesRawList.size() != learningOutputListSize )
	    {
	        System.out.println("Network::performBackPropagationLearning(-) - too few target values in list");
	        System.exit(-1);
	    }
	    
	    return (targetValuesList);
	}

	/*
	 * Public methods - these are the only methods the user should call to build, use and learn a neural network. 
	 */


    
	/**
	 * Compute the squared error of the networks. Ignore non-learnable output node if any.
	 * @param __targetValuesRawList
	 * @return
	 */
	public double estimateSquaredError (ArrayList __targetValuesRawList) 
	{
	    // SquaredError = 1/2 Sum_{k is in Output}((targetValue_k-predictedValue_k)^2)
	    
	    double squaredError = 0;

	    ArrayList targetValuesList = new ArrayList();
	    targetValuesList = this.makeCleanTargetValuesList(__targetValuesRawList);

	    
	    if ( targetValuesList.size() != getOutputNeuronListSize() )
	    {
	        System.out.println("Network::estimateSquaredError(-) - target and predicted lists sizes do not match");
	        System.exit(-1);
	    }
	    
	    for ( int i = 0 ; i != this.getOutputNeuronListSize() ; i++ )
	    {
	        double value = ((Double)(targetValuesList.get(i))).doubleValue() - ((NeuronForBackPropLearning)this.getOutputNeuronAt(i)).getOutputValue();
	        squaredError += value*value; 
	    }
	       
	    return ( squaredError / 2 );
	}
		
	/**
	 * Perform back-propagation learning algorithm using the target output values (correct values only for learnable outputs).
	 * @param __targetValuesRawList
	 */
	public void performBackPropagationLearning ( ArrayList __targetValuesRawList )
	{
	    ArrayList targetValuesList = new ArrayList();
	    targetValuesList = this.makeCleanTargetValuesList(__targetValuesRawList);
	    this.launchBackPropagationLearningAlgorithm(targetValuesList);
	}
		

    public double backprop_getEtaLearningRate()
    {
        return (this._etaLearningRate);
    }
    
    public void backprop_setEtaLearningRate( double __etaLearningRate )
    {
        _etaLearningRate = __etaLearningRate;
    }
    
    

    /**
     * specify an output neuron.
     * @param __neuron
     */
    public void registerOutputNeuron(NeuronForBackPropLearning __neuron)
    {
        __neuron.setNetworkOutputFlag(true);
        __neuron.setLearningNodeFlag(true);
        _outputNeuronsList.add(__neuron);
    }

	/**
	 * specify an output neuron and if the neuron should be considered during learning.
	 * @param __neuron
	 * @param __learnableFlag
	 */
    public void registerOutputNeuron(NeuronForBackPropLearning __neuron, boolean __learnableFlag)
    {
        __neuron.setNetworkOutputFlag(true);
        __neuron.setLearningNodeFlag(__learnableFlag);
        _outputNeuronsList.add(__neuron);
    }

    
    protected void addBiasNeuron()
    {
	    // create and record the bias neuron
	    _biasNeuron = new NeuronForBackPropLearning(this, "bias");
	    _biasNeuron.setNetworkInputFlag(true);
	    _biasNeuron.setInputValue(1);
	    this.registerInputNeuron(_biasNeuron);
	    
	    // add arcs from the bias neuron to all neurons in the network (except input nodes)
	    for ( int i = 0 ; i != _allNeuronsUnorderedList.size() ; i++ )
	    {
	        if ( ((Neuron)_allNeuronsUnorderedList.get(i)).isNetworkInput() == false )
	            registerBiasArc((WeightedArc)new ArcForBackPropLearning(_biasNeuron, (NeuronForBackPropLearning)_allNeuronsUnorderedList.get(i), Tools.getArcWeightRandomInitValue()));
	    }
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

