/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 7 janv. 2006
 * 
 */

package picoevo.app.SymbolicRegression;

import picoevo.core.*;
import picoevo.core.evolution.*;
import picoevo.core.representation.*;
import picoevo.ext.*;
import picoevo.ext.evolution.*;
import picoevo.ext.representation.*;
import picoevo.toolbox.*;

public class Main_SymbolicRegression {
    public static void launchEvolution()
    {
    	Display.info("#### Symbolic Regression ####"); // Tree GP ..

		// 1 - create an Evolution environment along with a single embedded Population
	
        ParameterSet_Evolution_mulambdaES_TreeGP parameterSet = new ParameterSet_Evolution_mulambdaES_TreeGP();
        
        //parameterSet.setSelectionOperator(new SelectionOperator_ClassicKozaGP("ClassicKozaGP"));
        parameterSet.setSelectionOperator(new SelectionOperator_MuLambda("MULAMBDA"));
        //parameterSet.setSelectionOperator(new SelectionOperator_ProgressiveVariation("ProgressiveVariation")); // test-purpose
        parameterSet.setEvaluationOperator_Individual(new EvaluationOperator_Individual_SymReg("symreg"));
        //parameterSet.setEvaluationOperator_Individual(new EvaluationOperator_Individual_SymReg_Sinus("symregWithSinus")); 
        parameterSet.setPopulationInitialisationOperator(new InitialisationOperator_Population_TreeGP("symreg"));
        parameterSet.setIndividualInitialisationOperator(new InitialisationOperator_Individual_TreeGP("symreg"));
        parameterSet.setElementInitialisationOperator(new InitialisationOperator_Element_Node_TreeGP("symreg"));
        parameterSet.setPopulationStatisticsOperator(new StatisticsOperator_Population_TreeGP());
        parameterSet.setOptimisationFlag(Dictionary.Minimisation);
        
        //parameterSet.setGenomeSize(96); // useless
        //parameterSet.setMutationRate(1.0/96.0); // ???

        parameterSet.setMu(50);
        parameterSet.setLambda(450);
        parameterSet.setMuPlusLambda(true);
        parameterSet.setGenerations(51);
        parameterSet.setInitPopSize(500);
        parameterSet.setMaxInitDepth(6);
        parameterSet.setMaxRunningDepth(17);
        parameterSet.setMaxDepth_Mutation(5);
        
        
        parameterSet.setMutationRate(0.00);
        parameterSet.setCrossoverRate(0.90);
        parameterSet.setEphemeralConstantMutationRate(0.01);


        // max: somme[i=0->n](2^i), soit : 2^(n+1)-1, (pour n=11 : 4095) // test (Xmxdefault, arbre max) : (1)pop=100;maxdepth=11 (2)pop=500;maxdepth=8 en "java -server"  -- note : koza92book p.104-105 : max permissible depth is 17 but usualy program do not hold more than 500 nodes (note : maxinit is 6)
        // sur sim86: 100/14
        
        parameterSet.setLogFilename("log/logfile_symreg_"+Misc.getCurrentTimeAsCompactString()+".data");
        
        parameterSet.check();
        parameterSet.displayInformation();
        
        Display.info("\n\n## initialization ##");
    	
		World myWorld = new World ("myEvolution", parameterSet);
		Population_SimplePopulation population = new Population_SimplePopulation ("my population",myWorld);
		myWorld.registerPopulation(population);
				
		population.performInitialisation();
		
		//population.displayInformation();
		
		Display.debug("-------------------------------------------------");
		
		//population.performEvaluation();
		//population.displayInformation();
		// Display.debug("STOP TEST");
		
		
		// System.exit(0); // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
		// 2 - evolution

		Display.info("\n\n## evolving ##");
		
		boolean isDisplay = true;

		for ( int i = 0 ; i != myWorld.getTemplate().getGenerations() ; i++ )
		{
			population.performEvaluation();
			if ( isDisplay == true)
				Display.info("\n# Generation no." + population.getGenerationNumber());
			population.getTemplate().getSelectionOperator().performSelection(population);
			if ( isDisplay == true)
				Display.memorycheck();
			population.displayStatistics();
			population.logStatistics();
		}
		
		//population.displayInformation();
		
		// --------------------------------------------------
		
		/*
		Display.info("\n\n## evolving ##");
	
		int displayFreq = 10;
		
		for ( int i = 0 ; i != myWorld.getTemplate().getGenerations() ; i++ )
		{
	        if (i % displayFreq == 0)
	        		myWorld.evolveOneStep(true);
	        else
	        		myWorld.evolveOneStep(false);
		}
		
		// maxOnePop.displayInformation();
		 */

		Display.info("\n\n\n## terminating ##\n");
	}
    
    public static void main(String[] args) {
        double startTime = System.currentTimeMillis();
		
        Version.displayCurrentReleaseInformation();
		
        Display.info("Running...");        
        launchEvolution(); 	
        Display.info("\nTerminated ("+ ((System.currentTimeMillis()-startTime)/1000) +"s elapsed).");
    }


}
