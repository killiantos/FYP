/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 7 janv. 2006
 * 
 */

package picoevo.ext.representation;

import java.util.ArrayList;

import picoevo.core.*;
import picoevo.core.evolution.*;
import picoevo.core.representation.*;
import picoevo.ext.*;
import picoevo.ext.evolution.*;
import picoevo.ext.representation.*;
import picoevo.toolbox.*;


public abstract class Element_DynamicArray extends Element_Array {

	// ### Data ###

	protected ArrayList _array = new ArrayList();
	
	// ### Constructors ###
	
	public Element_DynamicArray(Individual __individualOwner, ArrayList __elementLevelOperatorList) {
		super(__individualOwner, __elementLevelOperatorList);
	}
	
	public Element_DynamicArray ( String __name, Individual __individualSpace, ArrayList __elementLevelOperatorList )
	{
		super (__name, __individualSpace, __elementLevelOperatorList);
	}
	
	public Element_DynamicArray(Individual __individualOwner, ArrayList __elementLevelOperatorList, int __size) {
		super(__individualOwner, __elementLevelOperatorList);
		this.setInitGenotypeLength(__size);
	}
	
	public Element_DynamicArray ( String __name, Individual __individualSpace, ArrayList __elementLevelOperatorList, int __size)
	{
		super (__name, __individualSpace, __elementLevelOperatorList);
		this.setInitGenotypeLength(__size);
	}
	
	/** return genotype - return parameter is an ArrayList */
	final public Object getArray ()
	{
		return ( this._array );
	}

	/** set up genotype - parameter should be an ArrayList */
	final public void setArray ( Object __array )
	{
		try {
			this._array = (ArrayList)__array;
		} catch (ClassCastException e) {
			Display.critical(""+this.getClass().getName()+"::setArray - Object is not an ArrayList");
		}
	}
	
	public void displayInformation()
	{
	    Display.info_nocr("( \""+_name+"\",{");
	    for ( int i = 0 ; i != this._array.size() ; i++ )
	    		Display.info_nocr(" "+((Boolean)this._array.get(i)).booleanValue());
	    Display.info_nocr("},");
	    for ( int i = 0 ; i != _variationOperatorList.size() ; i++ )
	    		((VariationOperator)_variationOperatorList.get(i)).displayInformation();
	    Display.info_nocr(" )");
	}
}
