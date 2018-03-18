package picoevo.app.Simbad_AvoiderRobot;

import picoevo.core.*;
import picoevo.core.evolution.*;
import picoevo.core.representation.*;
import picoevo.ext.*;
import picoevo.ext.representation.*;
import picoevo.toolbox.*;


public class EvaluationOperator_SimpleAvoiderRobot extends EvaluationOperator_Individual {

	// ### Constructors ###
	
	public EvaluationOperator_SimpleAvoiderRobot () 
	{
		super();
	}
	
	public EvaluationOperator_SimpleAvoiderRobot (String __name) 
	{ 
		super(__name); 
	}
	
	// ### Methods ###

	public void evaluate(Object __o) 
	{
		super.evaluate(__o);
		
		try {
			Individual_SimpleIndividual individual = (Individual_SimpleIndividual)__o;
			
			double maxOne = 0;
			
			
			individual.setFitness( computeFitness(individual) );
		}
		catch ( java.lang.ClassCastException e )
		{
			Display.error(""+this.getClass().getName()+"::evaluate(.) - Class cast exception");
		}
	}

	private double computeFitness ( Individual_SimpleIndividual __individual )
	{
		((ParameterSet_Evolution_mulambdaES_SimpleAvoiderRobot)__individual.getTemplate()).getSimulator().reset(); // stability test?
		((ParameterSet_Evolution_mulambdaES_SimpleAvoiderRobot)__individual.getTemplate()).getRobot().setGenome((double[])((Element_StaticArray_Double)__individual.getElementAt(0)).getArray());
		((ParameterSet_Evolution_mulambdaES_SimpleAvoiderRobot)__individual.getTemplate()).getRobot()._myRobot.resetEvaluation ();

	    int i = 0;
	    while ( i < 10000 && ((ParameterSet_Evolution_mulambdaES_SimpleAvoiderRobot)__individual.getTemplate()).getRobot()._isRunnable == true ) 
	    {
	    		((ParameterSet_Evolution_mulambdaES_SimpleAvoiderRobot)__individual.getTemplate()).getSimulator().step();
	    		i++;
	    }
	    
		//Display.info("fitness is " + __individual.getTemplate().getRobot()._fitness + "");
		//Display.info(" value + " with genome : " );
		//for ( int j = 0 ; j < genome.length ; j++ )
		//	System.out.print( genome[j] + " ; " );
		//Display.info("");

	    return (((ParameterSet_Evolution_mulambdaES_SimpleAvoiderRobot)__individual.getTemplate()).getRobot()._fitness);
	}



	
}
