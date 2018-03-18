package picoevo.ext.evolution;

import java.util.ArrayList;

import picoevo.core.*;
import picoevo.core.evolution.*;
import picoevo.core.representation.*;
import picoevo.ext.*;
import picoevo.ext.representation.*;
import picoevo.ext.evolution.*;
import picoevo.toolbox.*;

/** perform the simplest possible initialisation for a population, i.e. just launch embedded individuals initialisation operators */
public class InitialisationOperator_Population_SimplePopulation extends InitialisationOperator_Population {

	// ### Constructors ###
	
	public InitialisationOperator_Population_SimplePopulation () 
	{
		super();
	}
	
	public InitialisationOperator_Population_SimplePopulation (String __name) 
	{ 
		super(__name); 
	}
	
	// ### Methods ###

	public void initialise( Population __o ) 
	{
		try {
			Population_SimplePopulation population = (Population_SimplePopulation)__o;
			for ( int j = 0 ; j != population.getTemplate().getInitPopSize() ; j++ )
			{
				Individual_SimpleIndividual ind = (new Individual_SimpleIndividual ("ind#"+(population.getPopulationSize()+1), population ));
				ind.performInitialisation();
				population.registerIndividual(ind);
			}
		}
		catch ( java.lang.ClassCastException e )
		{
			Display.error(""+this.getClass().getName()+"::initialise(.) - Class cast exception");
		}
	}

}
