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

public class SelectionOperator_FitnessRankProportionateReproduction extends SelectionOperator {

	public SelectionOperator_FitnessRankProportionateReproduction() { super("MuLambda selection operator"); }
	public SelectionOperator_FitnessRankProportionateReproduction(String __name) { super(__name); }

	/** create a view with only the __nb best individuals - the view is ordered.
	 *  inviduals are selected according to fitness RANK (the higher the rank, the more likely the selection)
	 * note that this method is static, i.e. this object may not need to be instantiated 
	 * note 2: fitness *rank* proportionate reproduction may be more fitted than simple fitness proportionate reproduction in most case
	 * note 3: moreover, fitness rank proportionate reproduction is much faster to compute.    
	*/
	static public PopulationView performSelectionView ( PopulationObject __population, int __nb )
	{
		PopulationView newPopView = new PopulationView();
		
		PopulationView popViewOrdered = SelectionOperator_SimpleRank.performSelectionView(__population); // results depends on optimisation flag (best fitnesses first anyway)
		
		int i = 0, j = 0;
		int popsize = __population.getPopulationSize();
		
		for ( i = 1 ; i != popsize+1 ; i++ )
			j+=i;
		
		for ( int k = 0 ; k != __nb ; k++ )
		{
			int selected = (int)(Math.random()*(double)j);
			//Display.debug("selected = " + selected + " and j = " + j );

			int current = 0;
			for ( i = 0 ; i != popsize ; i++ )
			{
				current += ( popsize - i );
				if ( current >= selected)
				{
					//Display.debug("select " + i + " with " + popViewOrdered.getIndividual(i).getFitness() );
					break;
				}
			}
			newPopView.registerIndividual(popViewOrdered.getIndividual(i));
		}
		
		//System.exit(-3);

		return (newPopView); 
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
