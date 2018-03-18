/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 7 janv. 2006
 * 
 */

package picoevo.ext.evolution;

import java.util.ArrayList;

import picoevo.core.*;
import picoevo.core.evolution.*;
import picoevo.core.representation.*;
import picoevo.ext.*;
import picoevo.ext.representation.*;
import picoevo.toolbox.*;



public class VariationOperator_Element_BitArray_UniformMutation extends VariationOperator {
	public VariationOperator_Element_BitArray_UniformMutation ( String __name )
	{
		super (__name);
	}
	
	public VariationOperator_Element_BitArray_UniformMutation ( String __name, double __operatorRate )
	{
		super(__name,__operatorRate);
	}
	
	public void applyOperator ( EvolutionObject __element )
	{
		/*if ( __element.getClass() != Element_Bit.class )
		{
			Display.error(""+this.getClass().getName()+"::applyOperator - mismatch");
		}*/

		boolean[] bitarray = (boolean[])((Element_StaticArray_Bit)__element).getArray();
		//boolean[] newbitarray = new boolean[bitarray.length];
		
		for ( int i = 0 ; i != bitarray.length ; i++ )
		{
			if ( Math.random() < this._operatorRate )
			{
				if ( Math.random() < 0.5 )
					/*new*/bitarray[i] = false;
				else
					/*new*/bitarray[i] = true;
			}
			//else // do not change value 
				//newbitarray[i] = bitarray[i];
		}
		((Element_StaticArray_Bit)__element).setArray(/*new*/bitarray);
	}

}
