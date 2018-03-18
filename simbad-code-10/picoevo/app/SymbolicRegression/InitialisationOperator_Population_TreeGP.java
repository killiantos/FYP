package picoevo.app.SymbolicRegression;

import java.util.ArrayList;

import picoevo.core.*;
import picoevo.core.evolution.*;
import picoevo.core.representation.*;
import picoevo.ext.*;
import picoevo.ext.evolution.ParameterSet_Evolution_mulambdaES;
import picoevo.ext.evolution.VariationOperator_Element_Bit_UniformMutation;
import picoevo.ext.representation.*;
import picoevo.toolbox.*;


public class InitialisationOperator_Population_TreeGP extends InitialisationOperator_Population {

	// ### Constructors ###
	
	public InitialisationOperator_Population_TreeGP () 
	{
		super();
	}
	
	public InitialisationOperator_Population_TreeGP (String __name) 
	{ 
		super(__name); 
	}
	
	// ### Methods ###

	public void initialise( Population __o ) 
	{
		//try {
			Population_SimplePopulation population = (Population_SimplePopulation)__o;
		
			// add variation operators
			population.addOperator(new VariationOperator_Population_TreeGP_CrossOver("crossover",((ParameterSet_Evolution_mulambdaES)population.getTemplate()).getCrossoverRate()));
			
			// init individuals
			
			for ( int j = 0 ; j != population.getTemplate().getInitPopSize() ; j++ )
			{
				Individual_TreeGP ind = (new Individual_TreeGP ("ind#"+(population.getPopulationSize()+1), population ));
				ind.performInitialisation();
				population.registerIndividual(ind);
			}
		//}
		//catch ( java.lang.ClassCastException e )
		//{
		//	Display.error(""+this.getClass().getName()+"::initialise(.) - Class cast exception");
		//}
	}

}
