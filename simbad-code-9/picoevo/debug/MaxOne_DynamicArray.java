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

public class MaxOne_DynamicArray {
    public static void launchExampleOne_MaxOne_GAstyle()
    {
		Display.info("#### MaxOne Tutorial (\"fast\" GA-like version) ####");


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
        parameterSet.setEvaluationOperator_Individual(new EvaluationOperator_MaxOne_DynamicArray_Bit("MaxOne"));
        parameterSet.setPopulationInitialisationOperator(new InitialisationOperator_Population_SimplePopulation("MaxOne"));
        parameterSet.setIndividualInitialisationOperator(new InitialisationOperator_Individual_DynamicArray_Bit("MaxOne"));
        parameterSet.setElementInitialisationOperator(new InitialisationOperator_Element_DynamicArray_Bit());
        parameterSet.setPopulationStatisticsOperator(new StatisticsOperator_Population());
        
        parameterSet.check();
        parameterSet.displayInformation();
        
        Display.info("\n\n## initialization ##");
    	
		World myWorld = new World ("myEvolution", parameterSet);
		Population_SimplePopulation maxOnePop = new Population_SimplePopulation ("max-one population",myWorld);
		
		maxOnePop.performInitialisation();
	
		// 2 - evolution
	
		Display.info("\n\n## evolving ##");
	
		int displayFreq = 10;
		
		for ( int i = 0 ; i != myWorld.getTemplate().getGenerations() ; i++ )
		{
	        if (i % displayFreq == 0)
	        		Display.info("\n# Generation no." + i);
	        
			maxOnePop.performEvaluation();
			
	        if (i % displayFreq == 0)
	        		maxOnePop.displayStatistics();
			
	        parameterSet.getSelectionOperator().performSelection(maxOnePop);
	        
			// [DEBUG] Memory check 
	        if (i % displayFreq == 0)
	        		Display.memorycheck();
		}
		
		Display.info("\n\n\n## terminating ##\n");
	}
    
    public static void main(String[] args) 
    {    	
		double startTime = System.currentTimeMillis();
		
		Version.displayCurrentReleaseInformation();
		
		Display.info("###PicoEvo Tutorial Example###");
		Display.info("Running...");        
		launchExampleOne_MaxOne_GAstyle(); 	
		Display.info("\nTerminated ("+ ((System.currentTimeMillis()-startTime)/1000) +"s elapsed).");
    }


}
