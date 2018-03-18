/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 7 janv. 2006
 * 
 */

package picoevo.debug;

import picoevo.core.*;
import picoevo.core.evolution.*;
import picoevo.core.representation.*;
import picoevo.ext.*;
import picoevo.ext.evolution.*;
import picoevo.ext.representation.*;
import picoevo.toolbox.*;

public class MaxOne_StaticArray {
    public static void launchExampleOne_MaxOne_GAstyle()
    {
    	Display.info("#### MaxOne Tutorial (\"fastest\" GA-like version) ####");


		// 1 - create an Evolution environment along with a single embedded Population
	
		//ParameterSet_Evolution_mulambdaES parameterSet = new ParameterSet_Evolution_mulambdaES(200, 40,5,195,true,96,1.0/96.0); // optimal reached after 30-40 generations
        ParameterSet_Evolution_mulambdaES parameterSet = new ParameterSet_Evolution_mulambdaES();

        parameterSet.setGenomeSize(512);
        parameterSet.setMu(5);
        parameterSet.setLambda(195);
        parameterSet.setMutationRate(1.0/512.0);
        parameterSet.setMuPlusLambda(true);
        parameterSet.setGenerations(250);
        parameterSet.setInitPopSize(200);
        
        parameterSet.setSelectionOperator(new SelectionOperator_MuLambda("MuLambda"));
        parameterSet.setEvaluationOperator_Individual(new EvaluationOperator_MaxOne_StaticArray_Bit("MaxOne"));
        parameterSet.setPopulationInitialisationOperator(new InitialisationOperator_Population_SimplePopulation("MaxOne"));
        parameterSet.setIndividualInitialisationOperator(new InitialisationOperator_Individual_StaticArray_Bit("MaxOne"));
        parameterSet.setElementInitialisationOperator(new InitialisationOperator_Element_StaticArray_Bit());
        parameterSet.setPopulationStatisticsOperator(new StatisticsOperator_Population());
        
        parameterSet.check();
        parameterSet.displayInformation();
        
        Display.info("\n\n## initialization ##");
    	
		World myWorld = new World ("myEvolution", parameterSet);
		Population_SimplePopulation maxOnePop = new Population_SimplePopulation ("max-one population",myWorld);
		
		maxOnePop.performInitialisation();
		
		//maxOnePop.displayInformation();
	
	
		// 2 - evolution
	
		Display.info("\n\n## evolving ##");
	
		int displayFreq = 10;
		
		for ( int i = 0 ; i != myWorld.getTemplate().getGenerations() ; i++ )
		{
			
	        if (i % displayFreq == 0)
	        		Display.info("\n# Generation no." + i);
			 
			// TODO ? pourrait etre resume par maxOnePop.evolveOneStep() ?
			// TODO ? pour cas ecologique, permettre un getIndividualVector()... pour faire l'evaluation a la main
			
	        //Display.debug("maxOnePop.size = " + maxOnePop.getIndividualList().size());
	        
			maxOnePop.performEvaluation();
			/* dans ce cas, synonyme de : 
			 * 1. myEvolution.performEvaluation() // mode je veux pas m'embeter
			 * 2. for ( int k = 0 ; k != maxOnePop.getPopulationSize() ; k++ ) maxOnePop.getIndividual(i).performEvaluation(); // mode je veux faire de l'ecosysteme
			 * 3. for (...) maxOnePop.getIndividual(i).setFitness(...); // si la methode existe.
			 */
			
	        if (i % displayFreq == 0)
	        		maxOnePop.displayStatistics();
			
	        //SelectionOperator_MuLambda selectionOperator = new SelectionOperator_MuLambda ();
	        parameterSet.getSelectionOperator().performSelection(maxOnePop);

	        
	        /* ****************** performSelection as of 200601140928
			// un population container permet de manipuler une liste qui contient des *clones* des individus originaux (on peut theoriquement faire n'importe quoi donc...)
			PopulationContainer newpop = new PopulationContainer (); // newpop va contenir ma liste d'individus pour la prochaine generation
			PopulationContainer newpopbuffer; // newpopbuffer va contenir des bouts de la pop finale
			
			// TODO : revoir les methodes de PopulationContainer pour cloner systematiquement les individus passŽ en parametres. Sauf nom de methode explicite!!!
	
			// un population viewer permet de manipuler une liste qui *pointe* sur les individus originaux (attention a pas faire n'importe quoi)
			PopulationView populationView = SelectionOperator_RankBased.performSelection(maxOnePop,((ParameterSet_Evolution_mulambdaES)myEvolution._parameterSet).get_mu()); // on passe par un view car parfois on a besoin de faire un traitement avant de reintegrer les individus
			// on peut aussi les ajouter a la main... : populationView.addIndividual(monIndividu);
	
			int nb = ( ((ParameterSet_Evolution_mulambdaES)myEvolution._parameterSet).get_lambda() + ((ParameterSet_Evolution_mulambdaES)myEvolution._parameterSet).get_mu() ) / ((ParameterSet_Evolution_mulambdaES)myEvolution._parameterSet).get_mu();
			for ( int j = 0 ; j != nb ; j++ ) // emulate (muPLUSlambda)-ES ("PLUS" should be made clearer later) TODO
			{
				newpopbuffer = new PopulationContainer (populationView);
				if ( j != 0 || !((ParameterSet_Evolution_mulambdaES)myEvolution._parameterSet).is_mupluslambda() ) // premiere passe pour les mu individus que l'on conserve. Ensuite on gerera les lambdas.
				{
					newpopbuffer.addOperatorList(maxOnePop.getVariationOperatorList());
					newpopbuffer.performVariations();
				}
				newpop.mergeWithContainer(newpopbuffer); // after this, newpopbuffer does not contain any individuals.
			}
	
			// replace old population with the new frankensteined one 
			maxOnePop.renewPopulation(newpop); // after this, newpop does not contain any individuals and maxOnePop contains only new individuals.
			// TODO : en implémentant mergeWithContainer en tant que mergeWithPopulation dans Population,
			
			//maxOnePop.performVariations();  // sauf que moins bien dans le cas ecologique -- prevoir le cas ou c'est l'utilisateur qui met la fitness.
	
			//maxOnePop.performSelection(); 			
			// [1]initialisation-[2]evaluation-variation-selection(sur ancien et nouveau)-((recommence ˆ [2]))
			
			************************ */
			
			// [DEBUG] Memory check 
	        if (i % displayFreq == 0)
	        		Display.memorycheck();
		}
		
		Display.info("\n\n\n## terminating ##\n");
	
		//maxOnePop.displayInformation();
	
		/*
		 * plusieurs solutions s'offrent a nous
		 * - on construit a la main des chromosomes : liberte totale mais laborieux
		 * - on ajoute au chromosome template une modele generateur de chromosome - sinon c'est pas vraiment un template...
		 * - on developpe un graphe sur lequel on pose les genes, on contraint l'enregistrement en ligne de chaque gene en parrallele
		 */
	
		// evaluate : compute the fitness localy for each individual
		//maxOnePop.evolveOneStep( 10, 100, EvolutionParameterSet.LAMBDAPLUSMU, EvolutionParameterSet.TOURNAMENTSELECTION ); // or myEvolution.(idem)
	}
    
    public static void main(String[] args) {
        double startTime = System.currentTimeMillis();
		
        Version.displayCurrentReleaseInformation();
		
        Display.info("###PicoEvo Tutorial Example###");
        Display.info("Running...");        
        launchExampleOne_MaxOne_GAstyle(); 	
        Display.info("\nTerminated ("+ ((System.currentTimeMillis()-startTime)/1000) +"s elapsed).");
    }


}
