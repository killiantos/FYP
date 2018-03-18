/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 30 aožt 2005
 *
 * la classe de plus haut niveau dans la hierarchie pour la representation des objets a Žvoluer
 *
 */

package picoevo.core.representation;

import java.util.ArrayList;

import picoevo.core.evolution.*;
import picoevo.core.representation.*;
import picoevo.toolbox.Misc;

public abstract class EvolutionObject {

	// data
	
	public ArrayList _variationOperatorList = new ArrayList ();
	public String _name = new String ("unnamed element");
	
	public MemoryModule _memoryModule = null; // for internal storage
	
	// constructor(s)
	
	EvolutionObject() {};

	// abstract methods
	
	public void displayInformation ()
	{
		Misc.notImplemented(this+"::displayInformation()");
	}

	public String getDisplayInformation ()
	{
		Misc.notImplemented(this+"::getDisplayInformation()");
		return null;
	}
	
	public VariationOperator getVariationOperator( int __index )
	{
		return ((VariationOperator)_variationOperatorList.get(__index));
	}
	
	public int getVariationOperatorListSize()
	{
		return ( this._variationOperatorList.size() );
	}
	
	/**
	 * return the list of Variation Operators (used when dealing with Population View(s))
	 * should be extra careful to free returned list once dealt with to avoid memory leak.
	 */
	public ArrayList getVariationOperatorList() 
	{
		return (this._variationOperatorList);
	}	

	
	/** performs level variations only (e.g. if called from a population, only population-level variations are performed such as crossover btw
	 *  individuals -- while mutation is not (should normaly be defined as a individual-level variation operator) )  */
	protected final void performLevelVariations ()
	{ 
		for ( int i = 0 ; i != this._variationOperatorList.size() ; i++ )
			((VariationOperator)this._variationOperatorList.get(i)).applyOperator(this);
	}

	/** This method is called recursively from the entry point towards the smallest element (if any). smaller elements are variated first. */
	abstract protected void performVariations ();
	
	public final void addOperator ( VariationOperator __attributeLevelOperator ) // should implement *check* and add operator. 
	{
		// TODO : check operator
		_variationOperatorList.add( __attributeLevelOperator );
	}
	
	public final void addOperatorList ( ArrayList __operatorList ) 
	{
		for ( int i = 0 ; i != __operatorList.size() ; i++ )
			_variationOperatorList.add( (VariationOperator)__operatorList.get(i) );
	}
	
	public void setName (String __name)
	{
		_name = __name;
	}
	
	public String getName ()
	{
		return (_name);
	}
		
}
