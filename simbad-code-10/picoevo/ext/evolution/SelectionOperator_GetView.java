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

public class SelectionOperator_GetView extends SelectionOperator {

	public SelectionOperator_GetView() { super("MuLambda selection operator"); }
	public SelectionOperator_GetView(String __name) { super(__name); }

	/** create a view with only the __nb best individuals - the view is ordered 
	 * note that this method is static, i.e. this object may not need to be instantiated 
	*/
	static public PopulationView performSelectionView ( Population __population, int __nb )
	{
		// TODO : testing purpose. should be rewritten.
		List list2 = (List)((ArrayList)__population.getIndividualList()).clone();
		return (new PopulationView(new ArrayList(list2))); 
	}
	
	/** create a view with only all the individuals - the view is ordered 
	 * note that this method is static, i.e. this object may not need to be instantiated 
	*/
	static public PopulationView performSelectionView ( Population __population )
	{
		// TODO : testing purpose. should be rewritten.
		List list = (List)((ArrayList)__population.getIndividualList()).clone();
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
