/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 7 janv. 2006
 * 
 */

package picoevo.app.RecurrentNeuralNetOptimisation;

import picoevo.core.*;
import picoevo.core.evolution.*;
import picoevo.core.representation.*;
import picoevo.ext.*;
import picoevo.ext.evolution.*;
import picoevo.ext.representation.*;
import picoevo.toolbox.*;

public class NeuralNetForTemporalXorOptimisation {
    public static void launchExampleOne_MaxOne_GAstyle()
    {
    	Display.info("#### RNN Tutorial (weight optimisation for the temporal XOR problem) ####");

		// 1 - create an Evolution environment along with a single embedded Population
	
		//ParameterSet_Evolution_mulambdaES parameterSet = new ParameterSet_Evolution_mulambdaES(200, 40,5,195,true,96,1.0/96.0); // optimal reached after 30-40 generations
        ParameterSet_Evolution_mulambdaES parameterSet = new ParameterSet_Evolution_mulambdaES();

        // This is an example experiment :
        // Parameters are far from optimal 
        // so as to make optimisation in more 
        // than one generation :-) indeed, search
        // space *and* problem are so simple that
        // random init may produce optimal individual.
        parameterSet.setGenomeSize(11);
        parameterSet.setMuPlusLambda(true);
        parameterSet.setMu(20);
        parameterSet.setLambda(180);
        parameterSet.setInitPopSize(200);
        parameterSet.setMutationRate(0.3);
        parameterSet.setGenerations(1000);
        parameterSet.setOptimisationFlag(Dictionary.Maximisation);
        
        String myProblem = new String ("RNNforXor"); // NO SPACE ALLOWED!
        
        parameterSet.setSelectionOperator(new SelectionOperator_MuLambda("MuLambda"));
        parameterSet.setEvaluationOperator_Individual(new EvaluationOperator_RNNforTemporalXor(myProblem));
        parameterSet.setPopulationInitialisationOperator(new InitialisationOperator_Population_SimplePopulation(myProblem));
        parameterSet.setIndividualInitialisationOperator(new InitialisationOperator_Individual_StaticArray_Double(myProblem));
        parameterSet.setElementInitialisationOperator(new InitialisationOperator_Element_StaticArray_Double());
        parameterSet.setPopulationStatisticsOperator(new StatisticsOperator_Population());
        
        parameterSet.setLogFilename("log/logfile_"+myProblem+"_"+Misc.getCurrentTimeAsCompactString()+".data");
        
        parameterSet.check();
        parameterSet.displayInformation();
        
        Display.info("\n\n## initialization ##");
    	
		World myWorld = new World ("myEvolution", parameterSet);
		Population_SimplePopulation maxOnePop = new Population_SimplePopulation ("population",myWorld);
		myWorld.registerPopulation(maxOnePop);
		
		maxOnePop.performInitialisation();
		
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
		
		//maxOnePop.displayInformation();

		Display.info("\n\n\n## terminating ##\n");
	}
    
    public static void main(String[] args) {
        double startTime = System.currentTimeMillis();
		
        Version.displayCurrentReleaseInformation();
		
        Display.info("###PicoEvo Tutorial Example###");
        Display.info("Running...");        
        launchExampleOne_MaxOne_GAstyle(); 	
        Display.info("\nTerminated ("+ ((System.currentTimeMillis()-startTime)/1000) +"s elapsed).");
    }

} // jean-claude barbet

