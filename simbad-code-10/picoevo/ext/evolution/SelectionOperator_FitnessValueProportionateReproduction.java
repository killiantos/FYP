/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 25 nov. 2005
 * 
 */

package picoevo.ext.evolution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import picoevo.core.*;
import picoevo.core.evolution.*;
import picoevo.core.representation.*;
import picoevo.ext.*;
import picoevo.ext.representation.*;
import picoevo.toolbox.*;

public class SelectionOperator_FitnessValueProportionateReproduction extends SelectionOperator {

	public SelectionOperator_FitnessValueProportionateReproduction() { super("MuLambda selection operator"); }
	public SelectionOperator_FitnessValueProportionateReproduction(String __name) { super(__name); }

	/** create a view with only the __nb best individuals - the view is ordered.
	 *  inviduals are selected according to fitness proportion (the higher the fitness, the more likely the selection)
	 * note that this method is static, i.e. this object may not need to be instantiated 
	 * note 2: fitness *rank* proportionate reproduction may be prefered in some case (see related selection operator)    
	*/
	static public PopulationView performSelectionView ( PopulationObject __population, int __nb )
	{
		// TODO : testing purpose. should be rewritten.
		//ArrayList list = new ArrayList();

		// what is the sum of all fitnesses?
		double fitnesses = 0;

		for ( int i = 0 ; i != __population.getPopulationSize() ; i++ )
		{
			fitnesses += __population.getIndividual(i).getFitness();
		}

		PopulationView popView = new PopulationView();
		    
		
		for ( int j = 0 ; j != __nb ; j++ ) // worst case : __nb loop -- easy optim: order list before, bigger density in first indexes.
		{
			int targetIndividual = -1;
			//double targetFitness = Math.random();
			double targetFitness = Math.random() * fitnesses;
			double currentFitness = 0;
			//Display.debug("targetFitness = "+targetFitness+ " / " + fitnesses);
		
			for ( int i = 0 ; i != __population.getPopulationSize() ; i++ )
			{
				if ( ((Population_SimplePopulation)__population).getTemplate().getOptimisationFlag() == Dictionary.Maximisation )
					currentFitness += __population.getIndividual(i).getFitness();// / fitnesses; // case: max
				else
					currentFitness += ( fitnesses - __population.getIndividual(i).getFitness() );// / fitnesses; // case: min
				
				if ( currentFitness >= targetFitness )
				{
					targetIndividual = i;
					//Display.debug("i="+targetIndividual+" ("+__population.getIndividual(i).getFitness()+")");
					break;
				}
			}
			if ( targetIndividual == -1 ) Display.critical("SelectionOperator_FitnessProportionalReproduction.performSelectionView(.,.) - bug!");
			popView.registerIndividual(__population.getIndividual(targetIndividual));
		}
			
		//System.out.println("return Popsize = " + popView.getPopulationSize());
		
		//System.exit(-2);
		
		return (popView); 
	}
	
	/** create a view with all the individuals - the view is ordered 
	 * This method returns a view of a population by sampling individuals from original population depending on their fitnesses. (i.e. oversample best individuals)  
	 * note that this method is static, i.e. this object may not need to be instantiated 
	*/
	static public PopulationView performSelectionView ( PopulationObject __population )
	{
		return ( performSelectionView ( __population, __population.getPopulationSize() ) );
	}

	/**
	 * Perform a selection of individuals *excluding* those referred by _popViewExclude
	 */
	/*
	static public PopulationView performSelectionView ( Population __population, PopulationView __popViewExclude )
	{
		ArrayList list = (ArrayList)(__population.getIndividualList()).clone();
		for ( int i = 0 ; i != __popViewExclude.getPopulationSize() ; i++ )
		{
			if ( list.remove( __popViewExclude.getIndividual(i) ) != true )
				Display.debug(""+this.getClass().getName()+"::performSelection(Population,PopulationView) - removing objects is not optimal");
		}
		return (new PopulationView(list));
	}
	*/
}
