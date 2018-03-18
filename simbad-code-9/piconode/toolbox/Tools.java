/*
 * Created on 30 dec. 2004
 * bredeche(at)lri(point)fr
 * 
 */
package piconode.toolbox;

import java.util.ArrayList;

import piconode.core.node.FeedForwardNeuralNetworkForBackPropLearning;

public class Tools {
   
    /* 
     * Data 
     */

    /* misc. parameters */    
    private		static 	boolean 	DEBUG_MODE = false; // true : display debug messages, false : quiet mode (i.e. release mode)

    /*
     * Methods
     */

    /**
     * Used to randomly initialise the weight of arc. Return a value between [ -1 , +1 ].
     */
    static public double getArcWeightRandomInitValue()
    {
    	//return (0.1);
        return (Math.random()*2-1);
    }

    /**
     * Used to randomly initialise the weight of the arc. Return a value within [ __offset , __offset + __range ] (offset value can be negative).
     * @param __offset
     * @param __range
     * @return
     */
    static public double getArcWeightRandomInitValue( double __offset, double __range )
    {
    	//return (1.6);
    	//return (0.5081);
        return ( Math.random() * __range + __offset ); // return a value within [ __offset , __offset + __range ]
    }

    /**
     * initialize ALL arcs weights includind those related to the bias neuron (if any). 
     * Initialisation value within [ -1 , +1 ]
     * @param __network
     */
	static public void initializeRandomArcWeights( FeedForwardNeuralNetworkForBackPropLearning __network ) // initialize ALL weights, including those related to the bias neuron (if any)
	{
	    initializeRandomArcWeightsFromRegisteredNeurons(__network);
	    
		if ( __network.containsBiasNeuron() == true )
		    Tools.initializeRandomArcWeightsFromBiasNeuron(__network);
	}

	/**
	 * initialize all arcs weights, except for arcs connected to the bias neuron (if any). 
	 * Initialisation value within [ -1 , +1 ]
	 * @param __network
	 */
	static private void initializeRandomArcWeightsFromRegisteredNeurons( FeedForwardNeuralNetworkForBackPropLearning __network ) // warning, do not initialize weights where arc.source is the bias neuron
	{
		ArrayList arcInitWeightsList = new ArrayList();
		
		for ( int i = 0 ; i != __network.getArcListSize() ; i++ ) 
		    arcInitWeightsList.add(new Double(Tools.getArcWeightRandomInitValue()));
	    __network.setStdArcsWeightValues(arcInitWeightsList);
	}
	
	/**
	 * initialize only arcs weights that are connected to the bias neuron. 
	 * Initialisation value within [ -1 , +1 ]
	 * @param __network
	 */
	static private void initializeRandomArcWeightsFromBiasNeuron( FeedForwardNeuralNetworkForBackPropLearning __network )
	{
	    if ( __network.containsBiasNeuron() != true )
	    {
	        System.out.println("Network::setBiasArcsWeightValues(-) - network contains no bias neuron");
	        System.exit(-1);		        
	    }
	    
	    ArrayList biasArcInitWeightsList = new ArrayList();

	    for ( int i = 0 ; i != __network.getBiasArcListSize() ; i++ ) 
	        biasArcInitWeightsList.add(new Double(Tools.getArcWeightRandomInitValue()));

	    __network.setBiasArcsWeightValues(biasArcInitWeightsList);
	}

    /**
     * initialize ALL arcs weights includind those related to the bias neuron (if any). 
     * Initialisation value within [ __offset , __offset + __range ] (offset value can be negative).
     * @param __network
     */
	static public void initializeRandomArcWeights( FeedForwardNeuralNetworkForBackPropLearning __network,  double __offset, double __range ) // initialize ALL weights, including those related to the bias neuron (if any)
	{
	    initializeRandomArcWeightsFromRegisteredNeurons(__network);
	    
		if ( __network.containsBiasNeuron() == true )
		    Tools.initializeRandomArcWeightsFromBiasNeuron(__network);
	}

	/**
	 * initialize all arcs weights, except for arcs connected to the bias neuron (if any). 
	 * Initialisation value within [ __offset , __offset + __range ] (offset value can be negative).
	 * @param __network
	 */	
	static private void initializeRandomArcWeightsFromRegisteredNeurons( FeedForwardNeuralNetworkForBackPropLearning __network,  double __offset, double __range ) // warning, do not initialize weights where arc.source is the bias neuron
	{
		ArrayList arcInitWeightsList = new ArrayList();
		
		for ( int i = 0 ; i != __network.getArcListSize() ; i++ ) 
		    arcInitWeightsList.add(new Double(Tools.getArcWeightRandomInitValue()));
	    __network.setStdArcsWeightValues(arcInitWeightsList);
	}

	/**
	 * initialize only arcs weights that are connected to the bias neuron. 
	 * Initialisation value within [ __offset , __offset + __range ] (offset value can be negative).
	 * @param __network
	 */
	static private void initializeRandomArcWeightsFromBiasNeuron( FeedForwardNeuralNetworkForBackPropLearning __network,  double __offset, double __range )
	{
	    if ( __network.containsBiasNeuron() != true )
	    {
	        System.out.println("Network::setBiasArcsWeightValues(-) - network contains no bias neuron");
	        System.exit(-1);		        
	    }
	    
	    ArrayList biasArcInitWeightsList = new ArrayList();

	    for ( int i = 0 ; i != __network.getBiasArcListSize() ; i++ ) 
	        biasArcInitWeightsList.add(new Double(Tools.getArcWeightRandomInitValue()));

	    __network.setBiasArcsWeightValues(biasArcInitWeightsList);
	}	
	
	/**
	 * apply the hyperbolic tangent function. Useful for the eponyme activation function.
	 * @param __value
	 * @return
	 */
	
    static public double tanh ( double __value )
    {
    	return( 2 / ( 1 + Math.exp( - 2 * __value )) - 1 ); // faster to compute than (e^x-e^-x)/(e^x+e^-x) or (e^{2*x}-1)/(e^{2*x}+1)
    }


	
}
