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

public class SelectionOperator_SimpleRank extends SelectionOperator {

	public SelectionOperator_SimpleRank() { super("MuLambda selection operator"); }
	public SelectionOperator_SimpleRank(String __name) { super(__name); }

	/** create a view with only the __nb best individuals - the view is ordered 
	 * note that this method is static, i.e. this object may not need to be instantiated 
	*/
	static public PopulationView performSelectionView ( PopulationObject __population, int __nb )
	{
		// TODO : testing purpose. should be rewritten.
		ArrayList list = new ArrayList();
		List list2 = (List)((ArrayList)__population.getIndividualList()).clone();
		
		Collections.sort(list2,Collections.reverseOrder());
		
		for ( int i = 0 ; i != __nb && i != list2.size() ; i++ )
		{
			list.add((Individual)list2.get(i));
			//Display.debug("("+i+") selected individual with fitness "+((Individual)list.get(i)).getFitness());
		}
		return (new PopulationView(list)); 
	}
	
	/** create a view with only all the individuals - the view is ordered 
	 * note that this method is static, i.e. this object may not need to be instantiated 
	*/
	static public PopulationView performSelectionView ( PopulationObject __population )
	{
		// TODO : testing purpose. should be rewritten.
		List list = (List)((ArrayList)__population.getIndividualList()).clone();
		
		Collections.sort(list,Collections.reverseOrder());
		
		return (new PopulationView(new ArrayList(list))); 
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
