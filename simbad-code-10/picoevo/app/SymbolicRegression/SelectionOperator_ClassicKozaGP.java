/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 14 janv. 2006
 * 
 */

package picoevo.app.SymbolicRegression;

import picoevo.core.*;
import picoevo.core.evolution.*;
import picoevo.core.representation.*;
import picoevo.ext.*;
import picoevo.ext.evolution.ParameterSet_Evolution_mulambdaES;
import picoevo.ext.evolution.SelectionOperator_SimpleRank;
import picoevo.ext.representation.*;
import picoevo.toolbox.*;


public class SelectionOperator_ClassicKozaGP extends SelectionOperator {

	public SelectionOperator_ClassicKozaGP() { super("Classic Koza GP selection operator"); }
	public SelectionOperator_ClassicKozaGP(String __name) { super(__name); }
	
	public void performSelection ( PopulationObject __population )
	{
		PopulationContainer newpop = new PopulationContainer (); // newpop va contenir ma liste d'individus pour la prochaine generation
		PopulationContainer newpopbuffer; // newpopbuffer va contenir des bouts de la pop finale
		
		//System.out.println("passed.");System.exit(-4);
		
		int popsize = __population.getPopulationSize();

		PopulationView populationView = SelectionOperator_SimpleRank.performSelectionView(__population,popsize/10); 

		for ( int j = 0 ; j != 10 ; j++ )
		{
			newpopbuffer = new PopulationContainer (populationView);
			if ( j != 0 ) // elitism. copy the ten best unchanged
			{
				newpopbuffer.addOperatorList(__population.getVariationOperatorList());
				newpopbuffer.performVariations(); // an individual may be subject to more than one variation... ERROR?
			}
			newpop.mergeWithContainer(newpopbuffer); // newpopbuffer is automaticaly emptied
		}

		((Population)__population).renewPopulation(newpop); // newpop is emptied
	}
}
