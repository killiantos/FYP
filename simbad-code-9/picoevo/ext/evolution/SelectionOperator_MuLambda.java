/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 14 janv. 2006
 * 
 */

package picoevo.ext.evolution;

import picoevo.core.*;
import picoevo.core.evolution.*;
import picoevo.core.representation.*;
import picoevo.ext.*;
import picoevo.ext.representation.*;
import picoevo.toolbox.*;


public class SelectionOperator_MuLambda extends SelectionOperator {

	public SelectionOperator_MuLambda() { super("MuLambda selection operator"); }
	public SelectionOperator_MuLambda(String __name) { super(__name); }
	
	public void performSelection ( PopulationObject __population )
	{
		Population population = (Population)__population;
		
		// un population container permet de manipuler une liste qui contient des *clones* des individus originaux (on peut theoriquement faire n'importe quoi donc...)
		PopulationContainer newpop = new PopulationContainer (); // newpop va contenir ma liste d'individus pour la prochaine generation
		PopulationContainer newpopbuffer; // newpopbuffer va contenir des bouts de la pop finale
		
		// TODO : revoir les methodes de PopulationContainer pour cloner systematiquement les individus passŽ en parametres. Sauf nom de methode explicite!!!

		// un population viewer permet de manipuler une liste qui *pointe* sur les individus originaux (attention a pas faire n'importe quoi)
		PopulationView populationView = SelectionOperator_SimpleRank.performSelectionView(population,((ParameterSet_Evolution_mulambdaES)population.getTemplate()).getMu()); // on passe par un view car parfois on a besoin de faire un traitement avant de reintegrer les individus
		// on peut aussi les ajouter a la main... : populationView.addIndividual(monIndividu);

		int nb = ( ((ParameterSet_Evolution_mulambdaES)population.getTemplate()).getLambda() + ((ParameterSet_Evolution_mulambdaES)population.getTemplate()).getMu() ) / ((ParameterSet_Evolution_mulambdaES)population.getTemplate()).getMu();
		for ( int j = 0 ; j != nb ; j++ ) // emulate (muPLUSlambda)-ES ("PLUS" should be made clearer later) TODO
		{
			newpopbuffer = new PopulationContainer (populationView);
			if ( j != 0 || !((ParameterSet_Evolution_mulambdaES)population.getTemplate()).isMuPlusLambda() ) // premiere passe pour les mu individus que l'on conserve. Ensuite on gerera les lambdas.
			{
				newpopbuffer.addOperatorList(__population.getVariationOperatorList());
				newpopbuffer.performVariations();
			}
			newpop.mergeWithContainer(newpopbuffer); // after this, newpopbuffer does not contain any individuals.
		}

		// replace old population with the new frankensteined one 
		population.renewPopulation(newpop); // after this, newpop does not contain any individuals and maxOnePop contains only new individuals.
		// TODO : en implémentant mergeWithContainer en tant que mergeWithPopulation dans Population,
	}
}
