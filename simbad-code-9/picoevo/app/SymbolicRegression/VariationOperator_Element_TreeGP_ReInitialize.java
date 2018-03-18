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

public class VariationOperator_Element_TreeGP_ReInitialize extends VariationOperator {
	
	VariationOperator_Element_TreeGP_ReInitialize ( String __name )
	{
		super (__name);
	}
	
	VariationOperator_Element_TreeGP_ReInitialize ( String __name, double __operatorRate )
	{
		super(__name,__operatorRate);
	}
	
	public void applyOperator ( EvolutionObject __element )
	{
		Element_Node_TreeGP_Root element = (Element_Node_TreeGP_Root)__element;
		element.resetSuccessorConnections(); // successors should be garbage collected.
		if ( ((ParameterSet_Evolution_mulambdaES_TreeGP)element.getTemplate()).getMaxInitDepth() <= 1 )
			element.performInitialisation();
		else
			element.performInitialisation(((ParameterSet_Evolution_mulambdaES_TreeGP)element.getTemplate()).getMaxInitDepth(), Dictionary.growMethod, true);
	}
}
