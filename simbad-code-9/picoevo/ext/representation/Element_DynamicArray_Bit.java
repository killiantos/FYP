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

/**
 * @author nicolas
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Element_DynamicArray_Bit extends Element_DynamicArray {

	// ### Data ###

	// ### Constructors ###
	
	public Element_DynamicArray_Bit(Individual __individualOwner, ArrayList __elementLevelOperatorList) {
		super(__individualOwner, __elementLevelOperatorList);
	}
	
	public Element_DynamicArray_Bit ( String __name, Individual __individualSpace, ArrayList __elementLevelOperatorList )
	{
		super (__name, __individualSpace, __elementLevelOperatorList);
	}
	
	public Element_DynamicArray_Bit(Individual __individualOwner, ArrayList __elementLevelOperatorList, int __size) {
		super(__individualOwner, __elementLevelOperatorList);
		this.setInitGenotypeLength(__size);
	}
	
	public Element_DynamicArray_Bit ( String __name, Individual __individualSpace, ArrayList __elementLevelOperatorList, int __size)
	{
		super (__name, __individualSpace, __elementLevelOperatorList);
		this.setInitGenotypeLength(__size);
	}
	
	/*public void performInitialisation() 
	{
		InitialisationOperator_Element_DynamicArray_Bit op = new InitialisationOperator_Element_DynamicArray_Bit();
		op.initialise(this);
	}
		/*if ( this._initGenotypeLength == -1 )
		{
			Display.error(""+this.getClass().getName()+"::performInitialisation - size of list is incorrect");
			return;
		}
		for ( int i = 0 ; i != _initGenotypeLength ; i++ )
		{
			double value = Math.random();
			if ( value < 0.5 )
				this._array.add(new Boolean(false));
			else
				this._array.add(new Boolean(true));
		}
	}*/

}
