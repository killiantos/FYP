/*
 * Created on 23 févr. 2005
 * nicolas.bredeche(at)lri.fr
 */
package piconode.tutorials;

import java.util.ArrayList;

import piconode.core.arc.ArcForBackPropLearning;
import piconode.core.node.FeedForwardNeuralNetworkForBackPropLearning;
import piconode.core.node.NeuronForBackPropLearning;
import piconode.ext.ActivationFunction_hyperbolicTangent;
import piconode.ext.ActivationFunction_logisticSigmoid;
import piconode.toolbox.Tools;

public class Tutorial_3_LearningSinus {

    /**
     * This example shows how to use simple for learning the sinus function with back-propagation and a simple one hidden layer neural network.
     */
    
    public static void launchExample ()
    {

       System.out.println("This example shows how to use simplenet for learning the sine function with back-propagation and a simple one hidden layer neural network.\n\n");
        
	    /* (1) Initializing and building a neural net */
		
       // step 1 : create a network
		
		FeedForwardNeuralNetworkForBackPropLearning network = new FeedForwardNeuralNetworkForBackPropLearning( new ActivationFunction_hyperbolicTangent() );
		
		// step 2 : create some neurons 
		//	- naming neuron (ex.: "in1", "in2", ...) is useful when debugging or when using network.displayInformation() method  
		
		NeuronForBackPropLearning in1 = new NeuronForBackPropLearning( network, "in1");
		NeuronForBackPropLearning hidden1 = new NeuronForBackPropLearning( network, new ActivationFunction_hyperbolicTangent(), "hidden(1)");
		NeuronForBackPropLearning hidden2 = new NeuronForBackPropLearning( network, new ActivationFunction_hyperbolicTangent(), "hidden(2)");
		NeuronForBackPropLearning hidden3 = new NeuronForBackPropLearning( network, new ActivationFunction_hyperbolicTangent(), "hidden(3)");
		NeuronForBackPropLearning hidden4 = new NeuronForBackPropLearning( network, new ActivationFunction_hyperbolicTangent(), "hidden(4)");
		NeuronForBackPropLearning out1 = new NeuronForBackPropLearning( network, new ActivationFunction_hyperbolicTangent(), "output" );
		
		// step 3 : declare input and output neurons
		
		network.registerInputNeuron( in1 );
		network.registerOutputNeuron( out1 ); // if several outputs, order is important if you intend to use a learning algorithm (i.e. matching target and predicted values is performed in list order)

		// step 4 : create the topology
		
		network.registerArc(new ArcForBackPropLearning( in1 , hidden1 , Tools.getArcWeightRandomInitValue(-1,2) ));
		network.registerArc(new ArcForBackPropLearning( in1 , hidden2 , Tools.getArcWeightRandomInitValue(-1,2) ));
		network.registerArc(new ArcForBackPropLearning( in1 , hidden3 , Tools.getArcWeightRandomInitValue(-1,2) ));
		network.registerArc(new ArcForBackPropLearning( in1 , hidden4 , Tools.getArcWeightRandomInitValue(-1,2)  ));
		network.registerArc(new ArcForBackPropLearning( hidden1, out1, Tools.getArcWeightRandomInitValue(-1,2)  ));
		network.registerArc(new ArcForBackPropLearning( hidden2, out1, Tools.getArcWeightRandomInitValue(-1,2)  ));
		network.registerArc(new ArcForBackPropLearning( hidden3, out1, Tools.getArcWeightRandomInitValue(-1,2)  ));
		network.registerArc(new ArcForBackPropLearning( hidden4, out1, Tools.getArcWeightRandomInitValue(-1,2) ));
		network.registerArc(new ArcForBackPropLearning( in1, out1, Tools.getArcWeightRandomInitValue(-1,2) ));
		
		// step 5 : initialize the network (perform some integrity checks and internal encoding)
		
		network.initNetwork();

		// step 6 (optional -- parameters shown are default) : set parameters for learning
		
		network.backprop_setEtaLearningRate(0.2); // example purpose (default value is already 1...)
		in1.setLearningNodeFlag(true); 		// example purpose (default value is already true...)  
		hidden1.setLearningNodeFlag(true); 	// -- this should be used when you want part of the network not to be affected by learning   
		hidden2.setLearningNodeFlag(true);	// -- arcs targeting a neuron which has its learningNodeFlag to arcs targeting a neuron which  
		hidden3.setLearningNodeFlag(true);	// -- has its learningNodeFlag set to "false" (i.e. neuron will not be modified during learning). 
		hidden4.setLearningNodeFlag(true);  // -- not that for example purpose, we created the output neuron "out2" which is *not* considered    
		out1.setLearningNodeFlag(true);		// -- during learning (learningNodeFlag set to false). This can be very useful in neural architecture 
											// -- where part of the outputs are not be considered during learning, e.g. nolfi/floreano/parisi kind
											// -- of NN (auto-teaching network, anticipation/control nets, ...) -- usually this type of NN are partly
											// -- optimized through Artificial Evolution.
		
		
		/* (2) using the network (feed-forward signal + back-propagation learning) */

		// step 1 : preparing the input values and
		
		network.displayInformation();

		ArrayList inputSinusValuesList = new ArrayList();
		ArrayList correctOutputSinusValuesList = new ArrayList();
				
	    System.out.println("\n###create learning examples###");	
	    int k = 0;
	    double index_init = -1, index_stop = 1, index_step = 0.125; // must be power 2 (double encoding issue) (e.g. 0.5, 0.25, 0.125, ..., 0.0625, ...)
	    for ( double d = index_init ; d < index_stop ; d+=index_step) 
		{
			System.out.print("\ntraining example " + k + " : sin(" + d + ") = " + Math.sin(d));
			inputSinusValuesList.add(new Double(d));
			correctOutputSinusValuesList.add(new Double(Math.sin(d))); // btw : target result is in radians
			k++;
		}

		// step 2 : computing the output values and learning

	    double previousEstimatedError = 0; // used to detect if learning is stuck in local/global maxima
		
		System.out.println("\n\n###start learning###");	
		System.out.println("# learning cycle # estimated squared error # error on test set #");

		for ( int i = 0 ; i != 500 ; i++ ) // learn for 500 cycles (i.e. 500 * nb of examples) 
		{
			double estimatedError = 0;
			double errorOnTestSet = 0;
			
 			System.out.print(i+" \t");

 			// perform a learning cycle (all examples are considered)
			for ( int j = 0 ; j != inputSinusValuesList.size() ; j++ )
			{
				ArrayList inputSinusValue = new ArrayList();
				inputSinusValue.add( (Double)(inputSinusValuesList.get(j)) );
				network.step( inputSinusValue );
				
				ArrayList correctOutputSinusValue = new ArrayList();
				correctOutputSinusValue.add(correctOutputSinusValuesList.get(j));
				
				estimatedError += network.estimateSquaredError(correctOutputSinusValue);
				double errortemp = network.estimateSquaredError(correctOutputSinusValue);
				network.performBackPropagationLearning(correctOutputSinusValue);
			}
			
			// display estimated squared error for this cycle
		    System.out.print(estimatedError/inputSinusValuesList.size()+" \t");

		    if ( previousEstimatedError == estimatedError/inputSinusValuesList.size() ) // should not happen here
			{
				System.out.println("\nTerminated : Network is stuck in either local or global maxima.");
				network.displayInformation(); 
				System.exit(0);
			}
		    previousEstimatedError = estimatedError/inputSinusValuesList.size();
	    
		    // compute and display error on test set (pessimist)
		    // This is a *non orthodox* test protocol! (better use leave-on-out or N-fold cross validation) -- btw, the following test might result in pessimistic figures... 
			for ( int j = 0 ; j != inputSinusValuesList.size() ; j++ )
			{
				ArrayList inputSinusValue = new ArrayList();
				double randomInput = (Math.random()-1)*2;
				inputSinusValue.add( new Double(randomInput) );
				network.step( inputSinusValue );
				
				ArrayList correctOutputSinusValue = new ArrayList();
				correctOutputSinusValue.add( new Double ( Math.sin(randomInput) ) );
				
				errorOnTestSet += network.estimateSquaredError(correctOutputSinusValue);
			}
			
			// display estimated squared error for this cycle
		    System.out.print(errorOnTestSet/inputSinusValuesList.size());		    

		    System.out.print("\n");
		}
		
		System.out.println("Stopped learning.\n");

		// step 3 (optional) : test resulting network
		
		System.out.println("Final classifier results (test on learning set - debug purpose)");
		for ( int i = 0 ; i != inputSinusValuesList.size() ; i++ )
		{	
			double value = ((Double)(inputSinusValuesList.get(i))).doubleValue();
			ArrayList inputSinusValue = new ArrayList();
			inputSinusValue.add( new Double(value) );
			network.step( inputSinusValue );

			System.out.println("test on example " + value + " : sin(" + value + ") = " + network.getOutputNeuronAt(0).getOutputValue() + " (correct value is " + Math.sin(value) + ")");
		}
		
		System.out.println();
		network.displayInformation(); 
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
