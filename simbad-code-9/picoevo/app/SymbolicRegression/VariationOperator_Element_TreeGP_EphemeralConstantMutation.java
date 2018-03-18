/*
 * Created on 20 aožt 2005
 *
 */

package picoevo.app.SymbolicRegression;

import picoevo.core.*;
import picoevo.core.evolution.*;
import picoevo.core.representation.*;
import picoevo.ext.*;
import picoevo.ext.representation.*;
import picoevo.toolbox.*;

public class VariationOperator_Element_TreeGP_EphemeralConstantMutation extends VariationOperator {
	
	public VariationOperator_Element_TreeGP_EphemeralConstantMutation ( String __name )
	{
		super (__name);
	}
	
	public VariationOperator_Element_TreeGP_EphemeralConstantMutation ( String __name, double __operatorRate )
	{
		super(__name,__operatorRate);
	}
	
	public void applyOperator ( EvolutionObject __element )
	{
		Element_Node_TreeGP_EphemeralConstant element = (Element_Node_TreeGP_EphemeralConstant)__element;
		if ( Math.random() < ((ParameterSet_Evolution_mulambdaES_TreeGP)element.getTemplate()).getEphemeralConstantMutationRate() )
		{	
			element.setValue(Math.random());
			//Display.debug("muteEphemeral");
		}
	}
}
