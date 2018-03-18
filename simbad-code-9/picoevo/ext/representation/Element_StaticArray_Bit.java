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

public class Element_StaticArray_Bit extends Element_Array {

	// ### Data ###

	protected boolean[] _array;
	
	// ### Constructors ###
	
	public Element_StaticArray_Bit(Individual __individualOwner, ArrayList __elementLevelOperatorList) {
		super(__individualOwner, __elementLevelOperatorList);
	}
	
	public Element_StaticArray_Bit ( String __name, Individual __individualSpace, ArrayList __elementLevelOperatorList )
	{
		super (__name, __individualSpace, __elementLevelOperatorList);
	}
	
	public Element_StaticArray_Bit(Individual __individualOwner, ArrayList __elementLevelOperatorList, int __size) {
		super(__individualOwner, __elementLevelOperatorList);
		this.setInitGenotypeLength(__size);
	}
	
	public Element_StaticArray_Bit ( String __name, Individual __individualSpace, ArrayList __elementLevelOperatorList, int __size)
	{
		super (__name, __individualSpace, __elementLevelOperatorList);
		this.setInitGenotypeLength(__size);
	}
		
	/** Initialise the genotype with random values. setInitGenotypeLength must be set before calling this method. */
	/*
	 public void performInitialisation() 
	 {
		if ( this._initGenotypeLength == -1 )
		{
			Display.error(""+this.getClass().getName()+"::performInitialisation - size of list is incorrect");
			return;
		}
		this._array = new boolean[this._initGenotypeLength];
		for ( int i = 0 ; i != _initGenotypeLength ; i++ )
		{
			double value = Math.random();
			if ( value < 0.5 )
				this._array[i]=false;
			else
				this._array[i]=true;
		}
	}
	*/
	
	/**
	 * return a *copy* of bitList (i.e. *not* a pointer to the original list) - return value is a boolean[]
	 * @param __bitList
	 */
	public Object getArray () // !n - optimization note : byRef would speed up evolution while being less secured.
	{
		if ( this.getInitGenotypeLength() == -1 )
			Display.error(""+this.getClass().getName()+"::getArray  - array not initialized");
		//boolean [] bitarray = new boolean [this._array.length];
		//System.arraycopy(this._bitArray,0,bitarray,0,_bitArray.length);
		//return ( bitarray );
		return ( (boolean[])this._array.clone() );
	}

	/**
	 * copy array values into the internal array (genotype) - parameter should be a boolean[]
	 * @param __array
	 */
	public void setArray ( Object __array ) // !n - optimization note : byRef would speed up evolution while being less secured.
	{
		try {
			if ( this.getInitGenotypeLength() == -1  )
				Display.error(""+this.getClass().getName()+"::setArray - array not initialized");
			if ( this._array != null )
				if ( this._array.length != ((boolean[])__array).length )
					Display.error(""+this.getClass().getName()+"::setArray - array length do not match");		
			//System.arraycopy(__array,0,this._bitArray,0,__array.length);
			//this._bitArray = __array; // copie!
			this._array = (boolean[])((boolean[])__array).clone();
		} catch (ClassCastException e) {
			Display.critical(""+this.getClass().getName()+"::setArray - Object does not match internal structure");
		}
	}
	
	public void displayInformation()
	{
	    Display.info_nocr("( \""+_name+"\",{");
	    for ( int i = 0 ; i != this._initGenotypeLength ; i++ )
	    		Display.info_nocr(" "+this._array[i]);
	    Display.info_nocr("},");
	    for ( int i = 0 ; i != _variationOperatorList.size() ; i++ )
	    		((VariationOperator)_variationOperatorList.get(i)).displayInformation();
	    Display.info_nocr(" )");
	}
}
