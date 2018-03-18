/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 15 janv. 2006
 * 
 */

package picoevo.ext.evolution;

import picoevo.core.evolution.*;
import picoevo.ext.representation.*;
import picoevo.toolbox.*;

public class EvolveOperator_Population_Generational extends EvolveOperator {

	public EvolveOperator_Population_Generational(String __name) 
	{
		super(__name);
	}

	public EvolveOperator_Population_Generational() 
	{
		super("unnamed Evolve Operator");
	}

	public void evolve(Object __o) 
	{
		evolve(__o,false);
	}
	
	public void evolve(Object __o, boolean __displayFreq ) 
	{
		//try {
			Population_SimplePopulation population = (Population_SimplePopulation)__o;
			
			if ( __displayFreq == true)
				Display.info("\n# Generation no." + population.getGenerationNumber());
			 
			population.performEvaluation();
			
			// statistics
			if ( __displayFreq == true)
				population.displayStatistics();
			population.logStatistics(); // must be specified to update logfile
			
			population.getTemplate().getSelectionOperator().performSelection(population);
			//population.getTemplate().incrementCurrentGeneration();
			
			// [DEBUG] Memory check 
			if ( __displayFreq == true)
				Display.memorycheck();
		//}
		//catch ( java.lang.ClassCastException e )
		//{
		//	Display.error(""+this.getClass().getName()+"::evolve(.) - Class cast exception");
		//}
	}
	

}
