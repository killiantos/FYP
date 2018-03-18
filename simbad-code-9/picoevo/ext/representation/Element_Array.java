/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 7 janv. 2006
 * 
 */

package picoevo.ext.representation;

import java.util.ArrayList;

import picoevo.core.representation.*;

public abstract class Element_Array extends Element {

	// ### Data ###

	protected int _initGenotypeLength = -1; 
	
	// ### Constructors ###
	
	public Element_Array(Individual __individualOwner, ArrayList __elementLevelOperatorList) {
		super(__individualOwner, __elementLevelOperatorList);
	}
	
	public Element_Array ( String __name, Individual __individualSpace, ArrayList __elementLevelOperatorList )
	{
		super (__name, __individualSpace, __elementLevelOperatorList);
	}
	
	public Element_Array(Individual __individualOwner, ArrayList __elementLevelOperatorList, int __size) {
		super(__individualOwner, __elementLevelOperatorList);
		this.setInitGenotypeLength(__size);
	}
	
	public Element_Array ( String __name, Individual __individualSpace, ArrayList __elementLevelOperatorList, int __size)
	{
		super (__name, __individualSpace, __elementLevelOperatorList);
		this.setInitGenotypeLength(__size);
	}
	
	public abstract Object getArray ();
	public abstract void setArray ( Object __array );
	
	/** set genotype initial size (must be set prior to performInitialisation call) */
	final public void setInitGenotypeLength ( int __size )
	{
		this._initGenotypeLength = __size;
	}

	/** get the supposed genotype initial size */
	final public int getInitGenotypeLength ()
	{
		return ( this._initGenotypeLength );
	}

}
