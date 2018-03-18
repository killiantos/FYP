package picoevo.app.RecurrentNeuralNetOptimisation;

import java.util.ArrayList;

import picoevo.core.*;
import picoevo.core.evolution.*;
import picoevo.core.representation.*;
import picoevo.ext.*;
import picoevo.ext.representation.*;
import picoevo.toolbox.*;
import piconode.core.arc.WeightedArc;
import piconode.core.node.RecurrentNeuralNetwork;
import piconode.core.node.RecurrentNeuron;
import piconode.ext.*;


public class EvaluationOperator_RNNforTemporalXor extends EvaluationOperator_Individual {

	// ### Constructors ###
	
	public EvaluationOperator_RNNforTemporalXor () 
	{
		super();
	}
	
	public EvaluationOperator_RNNforTemporalXor (String __name) 
	{ 
		super(__name); 
	}
	
	// ### Methods ###

	public void evaluate(Object __o) 
	{
		super.evaluate(__o);
		
		//try {
			Individual_SimpleIndividual individual = (Individual_SimpleIndividual)__o;
			
			double[] l = (double[])((Element_StaticArray_Double)individual.getElementAt(0)).getArray();
			ArrayList weights = new ArrayList();
			for ( int i = 0 ; i != l.length ; i++ )
				weights.add(new Double(l[i]));
			individual.setFitness( this.computeFitness(weights , individual.getOwner().getGenerationNumber()));
		//}
		//catch ( java.lang.ClassCastException e )
		//{
		//	Display.error(""+this.getClass().getName()+"::evaluate(.) - Class cast exception");
		//}
	}
	
/*	private double computeFitness ( ArrayList __weights )
	{

	     (1) Initialisation 
		
        // step 1 : create a network
		RecurrentNeuralNetwork network = new RecurrentNeuralNetwork( new ActivationFunction_logisticSigmoid() );

		// step 2 : create some neurons
		RecurrentNeuron in1 = new RecurrentNeuron( network, new ActivationFunction_logisticSigmoid());
		in1.setName("in1");
		//RecurrentNeuron hidden1 = new RecurrentNeuron( network, new ActivationFunction_logisticSigmoid());
		//hidden1.setName("hidden1");
		RecurrentNeuron out1 = new RecurrentNeuron( network, new ActivationFunction_logisticSigmoid());
		out1.setName("out1");
		
		// step 3 : declare input and output neurons
		network.registerInputNeuron( in1 );
		network.registerOutputNeuron( out1 ); // must be registered - initNetwork check for unregistered output nodes.

		// step 4 : create the topology
		network.registerArc(new WeightedArc( in1 , out1 ));  
		network.registerArc(new WeightedArc( out1, out1 ));
	
		// step 5 : initialize the network (perform some integrity checks and internal encoding)
		network.initNetwork();

		// step 6 : loading weight values from genotype  
		network.setAllArcsWeightValues(__weights);
		
		 (2) Evaluation (feed-forward signal) 
	    
		// data
		//boolean test = true ^ false; // boolean XOR example
		//int nbevaluationcycle = 1; // number of evaluation cycle
		int success = 0;
		ArrayList inputValuesList = new ArrayList();
		ArrayList testValuesList = new ArrayList();
		ArrayList resultValuesList = new ArrayList();
		testValuesList.add(new Double(0));
		testValuesList.add(new Double(0));
		testValuesList.add(new Double(1));
		testValuesList.add(new Double(1));
		testValuesList.add(new Double(0));
		resultValuesList.add(new Double(0));
		resultValuesList.add(new Double(1));
		resultValuesList.add(new Double(0));
		resultValuesList.add(new Double(1));
		
		// first step : simply compute first output (nothing to be learned)
		inputValuesList.add(testValuesList.get(0));
		network.step( inputValuesList );
		
		// evaluate
		for ( int i = 0 ; i != 4 ; i++ )
		{
			inputValuesList.clear();
			inputValuesList.add(testValuesList.get(i+1));
			network.step( inputValuesList );
			//System.out.println("("+(i+2)+") Output value : " + out1.getOutputValue());
			if ( ( out1.getOutputValue() > 0.5 && ((Double)resultValuesList.get(i)).doubleValue() > 0.5 ) ||
					( out1.getOutputValue() < 0.5 && ((Double)resultValuesList.get(i)).doubleValue() < 0.5 ) )
				success++;
		}
		
		return ((double)success/4.0);
	}*/

	// DEBUG : test on random sequence
	private double computeFitness ( ArrayList __weights , int __gen)
	{

	    /* (1) Initialisation */
		
        // step 1 : create a network
		RecurrentNeuralNetwork network = new RecurrentNeuralNetwork( new ActivationFunction_logisticSigmoid() );

		// step 2 : create some neurons
		RecurrentNeuron in1 = new RecurrentNeuron( network, new ActivationFunction_logisticSigmoid());
		in1.setName("in1");
		RecurrentNeuron hidden1 = new RecurrentNeuron( network, new ActivationFunction_logisticSigmoid());
		hidden1.setName("hidden1");
		RecurrentNeuron hidden2 = new RecurrentNeuron( network, new ActivationFunction_logisticSigmoid());
		hidden1.setName("hidden2");
		RecurrentNeuron virtualIn2 = new RecurrentNeuron( network, new ActivationFunction_copy());
		hidden1.setName("virtualIn2");
		RecurrentNeuron out1 = new RecurrentNeuron( network, new ActivationFunction_threshold());
		out1.setName("out1");
		
		// step 3 : declare input and output neurons
		network.registerInputNeuron( in1 );
		network.registerOutputNeuron( out1 ); // must be registered - initNetwork check for unregistered output nodes.

		// step 4 : create the topology
		network.registerArc(new WeightedArc( in1 , hidden1 ));  
		network.registerArc(new WeightedArc( in1 , hidden2 ));  
		network.registerArc(new WeightedArc( in1 , virtualIn2 ));  
		network.registerArc(new WeightedArc( virtualIn2, hidden1 ));  
		network.registerArc(new WeightedArc( virtualIn2, hidden2 ));  
		network.registerArc(new WeightedArc( hidden1, out1 ));
		network.registerArc(new WeightedArc( hidden2, out1 ));
		
	
		// step 5 : initialize the network (perform some integrity checks and internal encoding)
		network.initNetwork();

		// step 6 : loading weight values from genotype
		/*
		ArrayList weights = new ArrayList();
		for ( int i = 0 ; i != __weights.size() ; i++ )
			weights.add(new Double(((Double)__weights.get(i)).doubleValue()*10));
		network.setStdArcsWeightValues(weights);*/
		network.setAllArcsWeightValues(__weights);
		//network.setStdArcsWeightValues(__weights); // if no bias
		
		/* (2) Evaluation (feed-forward signal) */
	    
		// data
		//boolean test = true ^ false; // boolean XOR example
		int nbevaluation = 100; // number of evaluation
		int success = 0;
		ArrayList inputValuesList = new ArrayList();
		
		// first step : simply compute first output (nothing to be learned)
		inputValuesList.add(new Double(0));
		network.step( inputValuesList ); // the first three steps are for initialisation
		network.step( inputValuesList );		
		network.step( inputValuesList );

		// ############
		// TASK : TEMPORAL XOR
		// the goal of temporal XOR is to predict xor value of 3-steps and 2-steps back input values
		// i.e. prediction_t = value_(t-3) xor value_(t-2) -- (because there is a two steps distance from input to output)
		// ############
		
		double notSoOldValue=0; // t-1 input value
		double oldValue=0; // t-2 input value
		double olderValue=0; // t-3 input value (okay, could have done an array...)

		// evaluate
		for ( int i = 0 ; i != nbevaluation ; i++ )
		{
			double newValue = (double)((int)( Math.random() + 0.5 ));
			inputValuesList.clear();
			inputValuesList.add(new Double(newValue));
			network.step( inputValuesList );

			olderValue = oldValue;
			oldValue = notSoOldValue;
			notSoOldValue = newValue;

			boolean result = false;
			if ( ( oldValue > 0.5 && olderValue < 0.5 ) || ( oldValue < 0.5 && olderValue > 0.5 ) ) // xor
				result = true;
			if ( ( out1.getOutputValue() > 0.5 && result == true ) || ( out1.getOutputValue() < 0.5 && result == false ) )
				success++;
		}
		
		return ((double)success/(double)nbevaluation);
	}

}
