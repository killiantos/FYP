/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 25 nov. 2005
 * 
 */

package picoevo.core.evolution;

import picoevo.core.representation.*;
import picoevo.toolbox.*;

/** This operator is usually called through a performSelection method to
 * select some objects according to a given criterion. This operator is 
 * usually applied on a World or Population to select enclosed Individuals */
public abstract class SelectionOperator extends Operator {

	public SelectionOperator() { super("unnamed selection operator"); }
	public SelectionOperator(String __name) { super(__name); }

	/**
	 * perform a selection and renew the Population.
	 * Should be implemented in inherited classes.
	 */
	public void performSelection (PopulationObject __population)
	{
		Misc.notImplemented(""+this.getClass().getName()+"::performSelection(.)");
		System.exit(-1);
	}
	
	/**
	 * return a PopulationView of selected individuals from the population wrt selection criteria.
	 * Should be implemented in inherited classes.
	 */
	/*static public PopulationView performSelectionView (PopulationObject __population)
	{
		//Misc.notImplemented(""+this.getClass().getName()+"::performSelectionView(.)");
		System.exit(-1);
		return (null);
	}*/
	
}
