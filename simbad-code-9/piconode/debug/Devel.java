/*
 * Created on 23 févr. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package piconode.debug;

import java.util.ArrayList;

import piconode.core.arc.ArcForBackPropLearning;
import piconode.core.node.FeedForwardNeuralNetworkForBackPropLearning;
import piconode.core.node.NeuronForBackPropLearning;
import piconode.ext.ActivationFunction_hyperbolicTangent;
import piconode.ext.ActivationFunction_logisticSigmoid;
import piconode.toolbox.Tools;


/**
 * @author nicolas
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Devel {


	/*
	 * Examples : how to use the simplenet library
	 */
	
	/**
	 * This example shows how to initialize and use a simple neural network with only feed-forwarding the signal through the net architecture.  
	 */
	
    public static void launchExampleOne_InitialisingAndSignalFeedforwarding ()
    {
        
        System.out.println("This example shows how to initialize and use a simple neural network with only feed-forwarding the signal through the net architecture.");
        
	    /* (1) Initializing and building a neural net */
		
        // step 1 : create a network
		
		FeedForwardNeuralNetworkForBackPropLearning network = new FeedForwardNeuralNetworkForBackPropLearning( new ActivationFunction_logisticSigmoid() );

		// step 2 : create some neurons
		
		NeuronForBackPropLearning in1 = new NeuronForBackPropLearning( network, new ActivationFunction_logisticSigmoid());
		NeuronForBackPropLearning in2 = new NeuronForBackPropLearning( network, new ActivationFunction_logisticSigmoid());
		NeuronForBackPropLearning hidden1 = new NeuronForBackPropLearning( network, new ActivationFunction_logisticSigmoid());
		NeuronForBackPropLearning out1 = new NeuronForBackPropLearning( network, new ActivationFunction_logisticSigmoid());
		
		// step 3 : declare input and output neurons
		
		network.registerInputNeuron( in1 );
		network.registerInputNeuron( in2 );
		network.registerOutputNeuron( out1 ); // if several outputs, order is important if you intend to use a learning algorithm (i.e. matching target and predicted values is performed in list order)

		// step 4 : create the topology
		
		network.registerArc(new ArcForBackPropLearning( in1 , hidden1 , Tools.getArcWeightRandomInitValue() ));
		network.registerArc(new ArcForBackPropLearning( in2 , hidden1 , Tools.getArcWeightRandomInitValue() ));
		network.registerArc(new ArcForBackPropLearning( hidden1, out1, Tools.getArcWeightRandomInitValue() ));
	
				
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

    
    /**
     * This example shows how to use simple for learning the XOR operator with back-propagation and a simple one hidden layer neural network.
     */
    
    public static void launchExampleTwo_IntialisationAndLearningTheXorFunction ()
    {

        System.out.println("This example shows how to use simple for learning the XOR operator with back-propagation and a simple one hidden layer neural network.");
        
	    /* (1) Initializing and building a neural net */
		
        // step 1 : create a network
		
		FeedForwardNeuralNetworkForBackPropLearning network = new FeedForwardNeuralNetworkForBackPropLearning( new ActivationFunction_logisticSigmoid() );

		// step 2 : create some neurons 
		// 	- the activation function parameter is the "k" constant value in f(x) = 1 / ( 1 + e^(-k*x)) [ sigmoid function - f(x) is [0,1] ]
		//	- naming neuron (ex.: "in1", "in2", ...) is useful when debugging or when using network.displayInformation() method  
		
		NeuronForBackPropLearning in1 = new NeuronForBackPropLearning( network, new ActivationFunction_logisticSigmoid(1), "in1");
		NeuronForBackPropLearning in2 = new NeuronForBackPropLearning( network, new ActivationFunction_logisticSigmoid(1), "in2" );
		NeuronForBackPropLearning hidden1 = new NeuronForBackPropLearning( network, new ActivationFunction_logisticSigmoid(1), "hidden" );
		//Neuron hidden2 = new Neuron( network, new LogisticSigmoidActivationFunction(1)  );
		NeuronForBackPropLearning out1 = new NeuronForBackPropLearning( network, new ActivationFunction_logisticSigmoid(1), "output" );
		NeuronForBackPropLearning out2 = new NeuronForBackPropLearning( network, new ActivationFunction_logisticSigmoid(1), "outputParasite" );
		
		// step 3 : declare input and output neurons
		
		network.registerInputNeuron( in1 );
		network.registerInputNeuron( in2 );
		network.registerOutputNeuron( out1 ); // if several outputs, order is important if you intend to use a learning algorithm (i.e. matching target and predicted values is performed in list order)
		network.registerOutputNeuron( out2 ); // parasite node, for example purpose.

		// step 4 : create the topology
		
		network.registerArc(new ArcForBackPropLearning( in1 , hidden1 , Tools.getArcWeightRandomInitValue() ));
		network.registerArc(new ArcForBackPropLearning( in2 , hidden1 , Tools.getArcWeightRandomInitValue() ));
		network.registerArc(new ArcForBackPropLearning( hidden1, out1, Tools.getArcWeightRandomInitValue() ));
		network.registerArc(new ArcForBackPropLearning( in1, out1, Tools.getArcWeightRandomInitValue() )); //ver2
		network.registerArc(new ArcForBackPropLearning( in2, out1, Tools.getArcWeightRandomInitValue() )); //ver2
		//network.registerArc(new Arc( in1 , hidden2 , Environment.getDefaultArcWeightValue() )); //ver3
		//network.registerArc(new Arc( in2 , hidden2 , Environment.getDefaultArcWeightValue() )); //ver3
		//network.registerArc(new Arc( hidden2, out1, Environment.getDefaultArcWeightValue() )); //ver3
		network.registerArc(new ArcForBackPropLearning( in2, out2, Tools.getArcWeightRandomInitValue() )); //version with parasite node
				
		// step 5 : initialize the network (perform some integrity checks and internal encoding)
		
		network.initNetwork();
		
		// step 6 (optional -- parameters shown are default) : set parameters for learning
		
		network.backprop_setEtaLearningRate(1); // example purpose (default value is already 1...)
		in1.setLearningNodeFlag(true); 		// example purpose (default value is already true...)  
		in2.setLearningNodeFlag(true); 		// -- this should be used when you want part of the network not to be affected by learning arcs targeting a neuron which has its  
		hidden1.setLearningNodeFlag(true); 	// -- learningNodeFlag to arcs targeting a neuron which has its learningNodeFlag to "false" will not be modified during learning. 
		//hidden2.setLearningNodeFlag(true);	// -- WARNING! it's up to the user to feed the learning algorithm with correct "correct" values - values fed to non learning output 
		out1.setLearningNodeFlag(true);		// -- neuron will be ignored (i.e. you can write anything). e.g. if in1.learningNodeFlag is true (i.e. output neuron at TabOutputNeuron[0]), 
											// -- TabCorrectValues.get[0] will be ignored but must be specified (e.g. TabCorrectValues[] = { *(=0,1,-99,...), 1 } for two outputs where only the second one is considered for learning.)
											// -- Example of use : nolfi/floreano/parisi type of NN (auto-teaching network, anticipation/control nets, ...) -- should be used along with Artificial Evolution 
		out2.setLearningNodeFlag(false);
		
		/* (2) using the network (feed-forward signal + back-propagation learning) */

		// step 1 : preparing the input values and
		
		ArrayList learningSetForXorTest = new ArrayList();
		learningSetForXorTest.add(new Double(0));
		learningSetForXorTest.add(new Double(0));
		learningSetForXorTest.add(new Double(1));
		learningSetForXorTest.add(new Double(0));
		learningSetForXorTest.add(new Double(0));
		learningSetForXorTest.add(new Double(1));
		learningSetForXorTest.add(new Double(1));
		learningSetForXorTest.add(new Double(1));

		ArrayList labelSetForXorTest = new ArrayList();
		labelSetForXorTest.add(new Double(0));
		labelSetForXorTest.add(new Double(1));
		labelSetForXorTest.add(new Double(1));
		labelSetForXorTest.add(new Double(0));

		// step 2 : computing the output values and learning
		
		network.displayInformation();
		
		for (int i = 0 ; i != 10000 ; i++ ) // i.e. 2500 learning cycles (size of the learning sample is 4)
		{
		    ArrayList inputXorValuesList = new ArrayList();
		    inputXorValuesList.add((Double)learningSetForXorTest.get((i*2)%8));
		    inputXorValuesList.add((Double)learningSetForXorTest.get((i*2+1)%8));
			network.step( inputXorValuesList );
			ArrayList correctOutputValue = new ArrayList();
			correctOutputValue.add(new Double(((Double)(labelSetForXorTest.get(i%4))).doubleValue()));
			if ( ( i % 1000) <= 3 ) 
			{
				if ( ( i % 1000) == 0 ) 
				{
					//System.out.println("\n\n\n\n\n\n\n\n\n");
					System.out.println("** iteration " + i/4 + " ** : ");
				}
				System.out.println("( "+(Double)learningSetForXorTest.get((i*2)%8)+" xor "+(Double)learningSetForXorTest.get((i*2+1)%8)+" ) -> "+out1.getOutputValue()+" [should be " + ((Double)(labelSetForXorTest.get(i%4))).doubleValue()+" -- squared error is " + network.estimateSquaredError(correctOutputValue)+"]");	
			}
			network.performBackPropagationLearning(correctOutputValue);
		}
		
		System.out.println("Stopped learning.");
		
		network.displayInformation();
    }

    /**
     * This example shows how to use simple for learning the sinus function with back-propagation and a simple one hidden layer neural network.
     */
    
    public static void launchExampleThree_IntialisationAndLearningTheSinusFunction ()
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

    
    /**
     * This example shows how to use simple for learning the sinus function with back-propagation and a simple one hidden layer neural network.
     */
    
    public static void test ()
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
		
		network.registerArc(new ArcForBackPropLearning( in1 , hidden1 , 1 ));
		network.registerArc(new ArcForBackPropLearning( in1 , hidden2 , 2 ));
		network.registerArc(new ArcForBackPropLearning( in1 , hidden3 , 3 ));
		network.registerArc(new ArcForBackPropLearning( in1 , hidden4 , 4  ));
		network.registerArc(new ArcForBackPropLearning( hidden1, out1, 5  ));
		network.registerArc(new ArcForBackPropLearning( hidden2, out1, 6  ));
		network.registerArc(new ArcForBackPropLearning( hidden3, out1, 7  ));
		network.registerArc(new ArcForBackPropLearning( hidden4, out1, 8 ));
		network.registerArc(new ArcForBackPropLearning( in1, out1, 9 ));
		
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

		ArrayList values = new ArrayList();
		for ( int i = 0 ; i != 9+5 ; i++ )
		{
			 values.add(new Double (i));
		}
		network.setAllArcsWeightValues(values);
		ArrayList returnValues = network.getWeightsFromAllArcs();
		System.out.println("in");
		for ( int i = 0 ; i != 9+5 ; i++ )
		{
			System.out.print("["+((Double)values.get(i)).doubleValue()+"]");
		}
		System.out.println("\nOut");
		for ( int i = 0 ; i != 9+5 ; i++ )
		{
			System.out.print("["+((Double)returnValues.get(i)).doubleValue()+"]");
		}
		
		System.out.println();
    }
    
    
    
    /*
     * Main
     */
	
    public static void main(String[] args) {

        double startTime = System.currentTimeMillis();
        System.out.println("Running...");        
        launchExampleThree_IntialisationAndLearningTheSinusFunction(); 	
        System.out.println("\nTerminated ("+ ((System.currentTimeMillis()-startTime)/1000) +"s elapsed).");
	}
}
