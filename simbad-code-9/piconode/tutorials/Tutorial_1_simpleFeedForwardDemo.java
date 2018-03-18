/*
 * Created on 23 févr. 2005
 * nicolas.bredeche(at)lri.fr
 */

package piconode.tutorials;

import java.util.ArrayList;

import piconode.core.arc.*;
import piconode.core.node.*;
import piconode.ext.*;
import piconode.toolbox.Tools;


public class Tutorial_1_simpleFeedForwardDemo {


	/**
	 * This example shows how to initialize and use a simple neural network with only feed-forwarding the signal through the net architecture.  
	 */
	
    public static void launchExample ()
    {
        
        System.out.println("This example shows how to initialize and use a simple neural network with only feed-forwarding the signal through the net architecture.");
        
	    /* (1) Initializing and building a neural net */
		
        // step 1 : create a network
		
		FeedForwardNeuralNetwork network = new FeedForwardNeuralNetwork( new ActivationFunction_logisticSigmoid() );

		// step 2 : create some neurons
		
		Neuron in1 = new Neuron( network, new ActivationFunction_logisticSigmoid());
		Neuron in2 = new Neuron( network, new ActivationFunction_logisticSigmoid());
		Neuron hidden1 = new Neuron( network, new ActivationFunction_logisticSigmoid());
		Neuron out1 = new Neuron( network, new ActivationFunction_logisticSigmoid());
		
		// step 3 : declare input and output neurons
		
		network.registerInputNeuron( in1 );
		network.registerInputNeuron( in2 );
		network.registerOutputNeuron( out1 ); // if several outputs, order is important if you intend to use a learning algorithm (i.e. matching target and predicted values is performed in list order)

		// step 4 : create the topology
		
		network.registerArc(new WeightedArc( in1 , hidden1 , Tools.getArcWeightRandomInitValue() ));
		network.registerArc(new WeightedArc( in2 , hidden1 , Tools.getArcWeightRandomInitValue() ));
		network.registerArc(new WeightedArc( hidden1, out1, Tools.getArcWeightRandomInitValue() ));
	
				
		// step 5 : initialize the network (perform some integrity checks and internal encoding)
		
		network.initNetwork();
		
	    /* (2) using the network (feed-forward signal) */
	    
		// step 0  : randomly initialize the weights in [0,1[ -- in fact, this have been already done
		
		/*begin(optional)*
		Environment.initializeRandomArcWeights(network);
		/*end(optional)*/
		
		// step 1 : loading the input values
		
		ArrayList inputValuesList = new ArrayList();
		inputValuesList.add(new Double (0.5));
		inputValuesList.add(new Double (0.5));

		// step 2 : computing the output values
		
		network.step( inputValuesList );
	    
		System.out.println("Output value : " + out1.getOutputValue());
    }

    
    /*
     * Main
     */
	
    public static void main(String[] args) {

        double startTime = System.currentTimeMillis();
        System.out.println("Running...");        
        launchExample(); 	
        System.out.println("\nTerminated ("+ ((System.currentTimeMillis()-startTime)/1000) +"s elapsed).");
	}
}





