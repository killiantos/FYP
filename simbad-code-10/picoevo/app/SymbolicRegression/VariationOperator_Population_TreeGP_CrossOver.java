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

public class VariationOperator_Population_TreeGP_CrossOver extends VariationOperator {
	
	public VariationOperator_Population_TreeGP_CrossOver ( String __name )
	{
		super (__name);
	}
	
	public VariationOperator_Population_TreeGP_CrossOver ( String __name, double __operatorRate )
	{
		super(__name,__operatorRate);
	}

	/** 
	 * random apply operator to every individual in the population
	 * 
	 */
	public void applyOperator ( EvolutionObject __population )
	{		
		/*
		 * algo:
		 * pour chaque individu, y a t'il crossover?
		 * oui: 
		 * 	selectionne le partenaire (hors lui meme?)
		 *  selectionne un point dans les terminaux+nonTerminaux pour chacun.
		 *  swap les deux
		 */
		
		PopulationContainer population = (PopulationContainer)__population;

		if ( population.getPopulationSize() <= 1 )
			return;
		
		for ( int i = 0 ; i != population.getPopulationSize() ; i++ )
		{
			ParameterSet_Evolution_mulambdaES_TreeGP template = (ParameterSet_Evolution_mulambdaES_TreeGP)(population.getIndividual(0)).getTemplate();
			if ( Math.random() < template.getCrossoverRate() ) // crossover?
			{
				// * choose partner
				int partnerIndex = (int)(Math.random()*(population.getPopulationSize()-1));
				if ( partnerIndex >= i )
					partnerIndex++;

				// * get target individuals
				
				Individual_TreeGP ind1 = (Individual_TreeGP)population.getIndividual(i);
				Individual_TreeGP ind2 = (Individual_TreeGP)population.getIndividual(partnerIndex);

				// * perform crossOver btw two individuals
				performCrossOver(ind1,ind2, template);
			}
		}
	}
	
	public void performCrossOver (Individual ind1, Individual ind2, ParameterSet_Evolution_mulambdaES_TreeGP template)
	{
		// * select crossover point for individual 1
		
		ArrayList ind1_ElementList; // = (((Element_Node_TreeGP)ind1.getElementAt(0)).getAllElements());
		
		if ( Math.random() < template.getDoubleValue("crossover_nodeVsLeafProportion") && (((Element_Node_TreeGP)ind1.getElementAt(0)).getNonTerminalElements()).size() != 0 )
			ind1_ElementList = (((Element_Node_TreeGP)ind1.getElementAt(0)).getNonTerminalElements());
		else
			ind1_ElementList = (((Element_Node_TreeGP)ind1.getElementAt(0)).getTerminalElements());
		int ind1_TargetIndex = (int)((double)ind1_ElementList.size() * Math.random());

		// * select crossover point for individual 2
		
		ArrayList ind2_ElementList; // = (((Element_Node_TreeGP)ind2.getElementAt(0)).getAllElements());

		if ( Math.random() < template.getDoubleValue("crossover_nodeVsLeafProportion") && (((Element_Node_TreeGP)ind2.getElementAt(0)).getNonTerminalElements()).size() != 0 )
			ind2_ElementList = (((Element_Node_TreeGP)ind2.getElementAt(0)).getNonTerminalElements());
		else
			ind2_ElementList = (((Element_Node_TreeGP)ind2.getElementAt(0)).getTerminalElements());
		int ind2_TargetIndex = (int)((double)ind2_ElementList.size() * Math.random());
		
		// * check if crossover is possible (wrt. max depth allowed)
		int ind1_maxDepthAllowed = template.getMaxRunningDepth() - ((Element_Node_TreeGP)ind1_ElementList.get(ind1_TargetIndex)).getCurrentDepth();
		int ind1_subTreeDepth = ((Element_Node_TreeGP)ind1_ElementList.get(ind1_TargetIndex)).getDistanceToDeepestTerminal();
		int ind2_maxDepthAllowed = template.getMaxRunningDepth() - ((Element_Node_TreeGP)ind2_ElementList.get(ind2_TargetIndex)).getCurrentDepth();
		int ind2_subTreeDepth = ((Element_Node_TreeGP)ind2_ElementList.get(ind2_TargetIndex)).getDistanceToDeepestTerminal();
		
		if ( ind1_maxDepthAllowed < ind2_subTreeDepth || ind2_maxDepthAllowed < ind1_subTreeDepth )
		{
			//Display.debug("Cross-over not possible...");
			return; // crossover is not possible (because it would generate at least one too large individual : abort (no retry).
		}

		// * swap sub-trees
		
		Element_Node_TreeGP ind1_targetElement = ((Element_Node_TreeGP)ind1_ElementList.get(ind1_TargetIndex));
		Element_Node_TreeGP ind2_targetElement = ((Element_Node_TreeGP)ind2_ElementList.get(ind2_TargetIndex));
		
		//Display.debug("(1) : " + ind1_targetElement.getDisplayValue());
		//Display.debug("(2) : " + ind2_targetElement.getDisplayValue());
						
		ind1_targetElement.swapSubTrees(ind2_targetElement);		
	}
}
