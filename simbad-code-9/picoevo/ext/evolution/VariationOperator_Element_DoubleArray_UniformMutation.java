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


public class VariationOperator_Element_DoubleArray_UniformMutation extends VariationOperator {
	public VariationOperator_Element_DoubleArray_UniformMutation ( String __name )
	{
		super (__name);
	}
	
	public VariationOperator_Element_DoubleArray_UniformMutation ( String __name, double __operatorRate )
	{
		super(__name,__operatorRate);
	}
	
	public void applyOperator ( EvolutionObject __element )
	{
		double[] doublearray = (double[])((Element_StaticArray_Double)__element).getArray();
		//boolean[] newbitarray = new boolean[bitarray.length];
		
		for ( int i = 0 ; i != doublearray.length ; i++ )
		{
			if ( Math.random() < this._operatorRate )
			{
				doublearray[i] = (Math.random()*2)-1;
			}
		}
		((Element_StaticArray_Double)__element).setArray(doublearray);
	}

}
