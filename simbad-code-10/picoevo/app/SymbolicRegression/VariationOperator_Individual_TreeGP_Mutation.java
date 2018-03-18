/*
 * Created on 20 aožt 2005
 *
 */

package picoevo.app.SymbolicRegression;

import java.util.ArrayList;

import picoevo.core.*;
import picoevo.core.evolution.*;
import picoevo.core.representation.*;
import picoevo.ext.*;
import picoevo.ext.representation.*;
import picoevo.toolbox.*;

public class VariationOperator_Individual_TreeGP_Mutation extends VariationOperator {
	
	public VariationOperator_Individual_TreeGP_Mutation ( String __name )
	{
		super (__name);
	}
	
	public VariationOperator_Individual_TreeGP_Mutation ( String __name, double __operatorRate )
	{
		super(__name,__operatorRate);
	}

	/**
	 * Standard Koza mutation for symbolic regression (cf. GP book vol.1)
	 */
	public void applyOperator ( EvolutionObject __individual )
	{
		Individual_TreeGP individual = (Individual_TreeGP)__individual;
		
		if ( Math.random() < ((ParameterSet_Evolution_mulambdaES_TreeGP)individual.getTemplate()).getMutationRate() )
		{
			int maxDepthForMutation = ((ParameterSet_Evolution_mulambdaES_TreeGP)individual.getTemplate()).getMaxDepth_Mutation();
			Element_Node_TreeGP targetElement;
			
			if ( Math.random() < 0.8 && individual.getNonTerminalElements().size() > 0 ) // Koza says : 0.8 non-terminal vs. 0.2 terminal
			{
				ArrayList nonTerminalElements = individual.getNonTerminalElements();
				int randomIndex = (int)(((double)nonTerminalElements.size()*Math.random()));
				targetElement = ((Element_Node_TreeGP)nonTerminalElements.get(randomIndex));
			}
			else
			{
				ArrayList terminalElements = individual.getTerminalElements();
				int randomIndex  = (int)(((double)terminalElements.size()*Math.random()));
				targetElement = ((Element_Node_TreeGP)terminalElements.get(randomIndex));
			}
			
			int maxDepth;
			if ( ( maxDepthForMutation + targetElement.getCurrentDepth() ) > ((ParameterSet_Evolution_mulambdaES_TreeGP)individual.getTemplate()).getMaxRunningDepth() ) 
				maxDepth = ((ParameterSet_Evolution_mulambdaES_TreeGP)individual.getTemplate()).getMaxRunningDepth() - targetElement.getCurrentDepth();
			else
				maxDepth = maxDepthForMutation;
			
			Element_Node_TreeGP_Root newSubTreeRoot = new Element_Node_TreeGP_Root (individual);
			newSubTreeRoot.performInitialisation( maxDepth, Dictionary.growMethod, false ); // Grow tree generation - single terminal is possible
			targetElement.substituteWith((Element_Node_TreeGP)(newSubTreeRoot.getSuccessors().get(0)));
		}
	}
}
