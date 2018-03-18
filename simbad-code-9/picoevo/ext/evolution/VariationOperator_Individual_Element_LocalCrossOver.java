/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 2 sept. 2005
 * 
 */

package picoevo.ext.evolution;

import picoevo.core.*;
import picoevo.core.evolution.*;
import picoevo.core.representation.*;
import picoevo.ext.*;
import picoevo.ext.representation.*;
import picoevo.toolbox.*;


public class VariationOperator_Individual_Element_LocalCrossOver extends VariationOperator {
			
	VariationOperator_Individual_Element_LocalCrossOver ( String __name )
	{
		super(__name);
	}
	
	VariationOperator_Individual_Element_LocalCrossOver ( String __name, double __operatorRate )
	{
		super(__name, __operatorRate);
	}
	
	public void applyOperator ( EvolutionObject __element )
	{
		Misc.notImplemented(this,"applyOperator");
		// TODO
	}
}
