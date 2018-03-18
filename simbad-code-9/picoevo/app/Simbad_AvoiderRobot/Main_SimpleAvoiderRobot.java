/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 7 janv. 2006
 * 
 */

package picoevo.app.Simbad_AvoiderRobot;

import picoevo.core.*;
import picoevo.core.evolution.*;
import picoevo.core.representation.*;
import picoevo.ext.*;
import picoevo.ext.evolution.*;
import picoevo.ext.representation.*;
import picoevo.toolbox.*;

public class Main_SimpleAvoiderRobot {
    public static void launchEvolution()
    {
    	Display.info("#### Simple Avoider Robot - Optimising weights ####");
    	
		// 1 - create an Evolution environment along with a single embedded Population
	
        ParameterSet_Evolution_mulambdaES_SimpleAvoiderRobot parameterSet = new ParameterSet_Evolution_mulambdaES_SimpleAvoiderRobot();
        
        parameterSet.setGenomeSize(32);
        parameterSet.setMu(2);
        parameterSet.setLambda(18);
        parameterSet.setMutationRate(0.1);
        parameterSet.setMuPlusLambda(true);
        parameterSet.setGenerations(250);
        parameterSet.setInitPopSize(20);
        
        parameterSet.setSelectionOperator(new SelectionOperator_MuLambda("MuLambda"));
        parameterSet.setEvaluationOperator_Individual(new EvaluationOperator_SimpleAvoiderRobot("avoiderRobot"));
        parameterSet.setPopulationInitialisationOperator(new InitialisationOperator_Population_SimpleAvoiderRobot("avoiderRobot"));
        parameterSet.setIndividualInitialisationOperator(new InitialisationOperator_Individual_StaticArray_Double("avoiderRobot"));
        parameterSet.setElementInitialisationOperator(new InitialisationOperator_Element_StaticArray_Double(-1,1));
        parameterSet.setPopulationStatisticsOperator(new StatisticsOperator_Population());
        
        
        parameterSet.setLogFilename("log/logfile_simpleAvoiderRobot_"+Misc.getCurrentTimeAsCompactString()+".data");
        parameterSet.check();
        parameterSet.displayInformation();
        
        Display.info("\n\n## initialization ##");
    	
		World myWorld = new World ("myEvolution", parameterSet);
		Population_SimplePopulation population = new Population_SimplePopulation ("max-one population",myWorld);
		myWorld.registerPopulation(population);
		
		population.performInitialisation();
		
		//maxOnePop.displayInformation();
	
	
		// 2 - evolution
	
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

		Display.info("\n\n\n## terminating ##\n");
	}
    
    public static void main(String[] args) {
        double startTime = System.currentTimeMillis();
		
        Version.displayCurrentReleaseInformation();
		
        Display.info("###PicoEvo Tutorial Example###");
        Display.info("Running...");        
        launchEvolution(); 	
        Display.info("\nTerminated ("+ ((System.currentTimeMillis()-startTime)/1000) +"s elapsed).");
    }


}
