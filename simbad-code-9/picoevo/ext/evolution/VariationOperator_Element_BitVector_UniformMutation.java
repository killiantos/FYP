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



public class VariationOperator_Element_BitVector_UniformMutation extends VariationOperator {
	
	public VariationOperator_Element_BitVector_UniformMutation ( String __name )
	{
		super (__name);
	}
	
	public VariationOperator_Element_BitVector_UniformMutation ( String __name, double __operatorRate )
	{
		super(__name,__operatorRate);
	}
	
	public void applyOperator ( EvolutionObject __element )
	{
		ArrayList list = new ArrayList();
		
		/*if ( __element.getClass() != Element_Bit.class )
		{
			Display.error("""+this.getClass().getName()+"::applyOperator - mismatch");
		}*/
		
		for ( int i = 0 ; i != ((Element_DynamicArray_Bit)__element).getInitGenotypeLength() ; i++ )
		{
			if ( Math.random() < this._operatorRate )
			{
				if ( Math.random() < 0.5 )
					 list.add(new Boolean(false));
				else
					list.add(new Boolean(true));
				//Display.info("!!!!Mutate!!!");
			}
			else
				list.add((Boolean)(((ArrayList)((Element_DynamicArray_Bit)__element).getArray()).get(i)));
		}
		((Element_DynamicArray_Bit)__element).setArray(list);
	}

}
