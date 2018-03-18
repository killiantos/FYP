/*
 * Created on 20 aožt 2005
 *
 */

// Example purpose

package picoevo.ext.evolution;

import picoevo.core.*;
import picoevo.core.evolution.*;
import picoevo.core.representation.*;
import picoevo.ext.*;
import picoevo.ext.representation.*;
import picoevo.toolbox.*;


public class VariationOperator_Population_CrossOver extends VariationOperator {

	VariationOperator_Population_CrossOver ( String __name )
	{
		super(__name);
	}
	
	VariationOperator_Population_CrossOver ( String __name, double __operatorRate )
	{
		super(__name, __operatorRate);
	}
	
	public void applyOperator ( EvolutionObject __element )
	{
		Misc.notImplemented(this,"applyOperator");
		// TODO
	}
}
