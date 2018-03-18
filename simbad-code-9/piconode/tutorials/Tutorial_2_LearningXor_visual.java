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
import piconode.visual.NNPiconode;


public class Tutorial_2_LearningXor_visual {

    
    /**
     * This example shows how to use simple for learning the XOR operator with back-propagation and a simple one hidden layer neural network.
     */
    
    public static void launchExample ()
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
					int retour = NNPiconode.showNetwork(network);
				}
				System.out.println("( "+(Double)learningSetForXorTest.get((i*2)%8)+" xor "+(Double)learningSetForXorTest.get((i*2+1)%8)+" ) -> "+out1.getOutputValue()+" [should be " + ((Double)(labelSetForXorTest.get(i%4))).doubleValue()+" -- squared error is " + network.estimateSquaredError(correctOutputValue)+"]");	
			}
			network.performBackPropagationLearning(correctOutputValue);
		}
		
		System.out.println("Stopped learning.");
		
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
