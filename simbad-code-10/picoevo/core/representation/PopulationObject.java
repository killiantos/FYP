/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 20 aožt 2005
 *
 */

package picoevo.core.representation;

import java.util.ArrayList;

import picoevo.toolbox.*;

public abstract class PopulationObject extends EvolutionObject implements PopulationInterface {

	// ### Data ###
	
	protected ArrayList _individualList = new ArrayList ();
	
	// ### Constructor(s) ###
	

	public PopulationObject ( String __populationName )
	{
		_name = __populationName;
	}

	// ### Accessing ###

	/** 
	 * return actual population size
	 */
	public int getPopulationSize ()
	{
		if ( this._individualList == null )
			Display.error(this.getClass().getName()+" : trying to get size of population while population not initialised.");
		return ( this._individualList.size() );
	}
	
	// ### Other methods ###

	public void displayInformation()
	{
		Display.info("### Population Information : summary ###");
		Display.info("Population name : " + this._name );
		Display.info("Population size : " + this.getPopulationSize() );
		Display.info_nocr("Population-level Operator(s) : ");
	    for ( int i = 0 ; i != _variationOperatorList.size() ; i++ )
			this.getVariationOperator(i).displayInformation();
	    Display.info("\n");
	    for ( int i = 0 ; i != _individualList.size() ; i++ )
	    		this.getIndividual(i).displayInformation();
	}
	
	abstract public void registerIndividual ( Individual __ind );
	
	public Individual getIndividual ( int __index )
	{
		return ((Individual)_individualList.get(__index));
	}
	
	public ArrayList getIndividualList() 
	{
		return this._individualList;
	}
	
	public final void performVariations()
	{
		for ( int i = 0 ; i != this._individualList.size() ; i++ )
			this.getIndividual(i).performVariations();
		this.performLevelVariations();
	}
	
	public void reset ()
	{
		this._individualList = null;
	}



}
