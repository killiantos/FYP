/*
 * Created on 20 aožt 2005
 *
 */

package picoevo.ext.evolution;

import picoevo.core.*;
import picoevo.core.evolution.*;
import picoevo.core.representation.*;
import picoevo.ext.*;
import picoevo.ext.representation.*;
import picoevo.toolbox.*;



public class VariationOperator_Element_Double_UniformMutation_ZeroOneInterval extends VariationOperator {
	
	VariationOperator_Element_Double_UniformMutation_ZeroOneInterval ( String __name )
	{
		super (__name);
	}
	
	VariationOperator_Element_Double_UniformMutation_ZeroOneInterval ( String __name, double __operatorRate )
	{
		super(__name,__operatorRate);
	}
	
	public void applyOperator ( EvolutionObject __element )
	{
		if ( __element.getClass() != Element_Double.class )
		{
			Display.error(""+this.getClass().getName()+"::applyOperator() - mismatch");
		}
		if ( Math.random() < this._operatorRate )
		{
			((Element_Double)__element).setValue(Math.random());
			//Display.info("!!!!Mutate!!!");
		}
	}

}
