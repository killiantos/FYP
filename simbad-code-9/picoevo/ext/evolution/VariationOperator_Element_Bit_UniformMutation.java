/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 2 déc. 2005
 * 
 */

package picoevo.ext.evolution;

import picoevo.core.*;
import picoevo.core.evolution.*;
import picoevo.core.representation.*;
import picoevo.ext.*;
import picoevo.ext.representation.*;
import picoevo.toolbox.*;


/**
 * @author nicolas
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class VariationOperator_Element_Bit_UniformMutation extends VariationOperator {
	public VariationOperator_Element_Bit_UniformMutation ( String __name )
	{
		super (__name);
	}
	
	public VariationOperator_Element_Bit_UniformMutation ( String __name, double __operatorRate )
	{
		super(__name,__operatorRate);
	}
	
	public void applyOperator ( EvolutionObject __element )
	{
		/*if ( __element.getClass() != Element_Bit.class )
		{
			Display.error("VariationOperator_(...).applyOperator - mismatch");
		}*/
		if ( Math.random() < this._operatorRate )
		{
			if ( Math.random() < 0.5 )
				((Element_Bit)__element).setValue(false);
			else
				((Element_Bit)__element).setValue(true);
			//Display.info("!!!!Mutate!!!");
		}
	}

}
