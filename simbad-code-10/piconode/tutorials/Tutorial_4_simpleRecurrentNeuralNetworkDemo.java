/*
 * Created on 23 févr. 2005
 * nicolas.bredeche(at)lri.fr
 */

package piconode.tutorials;

import java.util.ArrayList;

import piconode.core.arc.*;
import piconode.core.node.*;
import piconode.ext.*;
import piconode.toolbox.*;

public class Tutorial_4_simpleRecurrentNeuralNetworkDemo {


	/**
	 * This example shows how to initialize and use a simple recurrent neural network   
	 */
	
    public static void launchExample ()
    {
        System.out.println("This example shows how to initialize and use a simple recurrent neural network with only feed-forwarding a signal through the net architecture and observing decay through time when no new inputs.");
        
	    /* (1) Initializing and building a neural net */
		
        // step 1 : create a network
		
		RecurrentNeuralNetwork network = new RecurrentNeuralNetwork( new ActivationFunction_logisticSigmoid() );

		// step 2 : create some neurons
		
		RecurrentNeuron in1 = new RecurrentNeuron( network, new ActivationFunction_logisticSigmoid());
		RecurrentNeuron in2 = new RecurrentNeuron( network, new ActivationFunction_logisticSigmoid());
		RecurrentNeuron hidden1 = new RecurrentNeuron( network, new ActivationFunction_logisticSigmoid());
		RecurrentNeuron hidden2 = new RecurrentNeuron( network, new ActivationFunction_logisticSigmoid());
		RecurrentNeuron hidden3 = new RecurrentNeuron( network, new ActivationFunction_logisticSigmoid());
		//RecurrentNeuron hidden4 = new RecurrentNeuron( network, new ActivationFunction_logisticSigmoid());
		RecurrentNeuron out1 = new RecurrentNeuron( network, new ActivationFunction_logisticSigmoid());
		
		in1.setName("in1");
		in2.setName("in2");
		hidden1.setName("hidden1");
		hidden2.setName("hidden2");
		hidden3.setName("hidden3");
		out1.setName("out1");
		//hidden4.setName("hidden4");
		
		// step 3 : declare input and output neurons
		
		network.registerInputNeuron( in1 );
		network.registerInputNeuron( in2 );
		network.registerOutputNeuron( out1 ); // must be registered - initNetwork check for unregistered output nodes.

		// step 4 : create the topology
		
		network.registerArc(new WeightedArc( in1 , hidden1 , 1 )); // may also use Tools.getArcWeightRandomInitValue() at init 
		network.registerArc(new WeightedArc( in2 , hidden1 , 1 )); 
		network.registerArc(new WeightedArc( hidden1, out1, 1 ));
		network.registerArc(new WeightedArc( hidden1, hidden2, 1 ));
		network.registerArc(new WeightedArc( hidden2, out1, 1 ));
		network.registerArc(new WeightedArc( hidden2, hidden1, 1 ));
		network.registerArc(new WeightedArc( hidden3, hidden1, 1 ));
		network.registerArc(new WeightedArc( hidden2, hidden3, 1 ));
		//network.registerArc(new WeightedArc( hidden2, hidden4, 1 ));
		network.registerArc(new WeightedArc( out1, hidden1, 0.7 )); // should decay through time
	
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
		
		// step 1
		network.step( inputValuesList );
		System.out.println("(1) Output value : " + out1.getOutputValue());
		inputValuesList.clear();
		inputValuesList.add(new Double (0.0));
		inputValuesList.add(new Double (0.0));

		for ( int i = 0 ; i != 5 ; i++ )
		{
			network.step( inputValuesList );
			System.out.println("("+(i+2)+") Output value : " + out1.getOutputValue());
			network.displayInformation();
		}
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





