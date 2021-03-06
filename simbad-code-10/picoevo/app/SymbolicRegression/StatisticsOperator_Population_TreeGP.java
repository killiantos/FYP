/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 15 janv. 2006
 * 
 */

package picoevo.app.SymbolicRegression;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import picoevo.core.evolution.*;
import picoevo.core.representation.*;
import picoevo.ext.evolution.*;
import picoevo.toolbox.*;

public class StatisticsOperator_Population_TreeGP extends StatisticsOperator_Population {
	
	public StatisticsOperator_Population_TreeGP() { super(); }
	public StatisticsOperator_Population_TreeGP(String __name) { super(__name); }

	public void displayStatistics ( Object __o )
	{
		double returnValues[] = computeStatistics( __o );
		
		Display.info ("" + (int)returnValues[1] + " individuals. Average fitness is " + returnValues[2]);
		Display.info ("Best individual is number " + (int)returnValues[3] + " with fitness " + returnValues[4] + "\nTree Depth is " + returnValues[5] + " ; there are " + returnValues[6] + " nodes.");
		Population population = (Population)__o;
		population.getIndividual((int)returnValues[3]).displayInformation();
		((EvaluationOperator_Individual_SymReg)population.getTemplate().getEvaluationOperator_Individual()).evaluateHits(population.getIndividual((int)returnValues[3]));
	}
	
	public void logStatistics ( Object __o, String __filename )
	{
	    try {
			BufferedWriter outputFile = new BufferedWriter(new FileWriter(__filename, true));
			double returnValues[] = computeStatistics( __o );
			outputFile.write(""+(int)returnValues[0]+","+(int)returnValues[1]+","+returnValues[2]+","+(int)returnValues[3]+","+returnValues[4]+","+returnValues[5]+"\n");
			outputFile.close();
	    } catch (IOException e) {
	    		Display.error(""+this.getClass().getName()+"::logStatistics(.) - cannot write logfile [" + __filename + "]");
	    }
	}

	private double[] computeStatistics(Object __o)
	{
		double returnValues[] = new double [7];
		
		try {
			Population population = (Population)__o;
			
			double bestFitness = 0, avgFitness = 0;
			int bestIndex = 0;
			for ( int i = 0 ; i != population.getPopulationSize() ; i++ )
			{
				if (i == 0 )
				{
					bestFitness =  population.getIndividual(0).getFitness();
					bestIndex = 0;
				}
				else
				{
					if ( population.getTemplate().getOptimisationFlag() == Dictionary.Maximisation )
					{
						if ( bestFitness < population.getIndividual(i).getFitness() )
						{
							bestFitness = population.getIndividual(i).getFitness();
							bestIndex = i;
						}
					}
					else
					{
						if ( bestFitness > population.getIndividual(i).getFitness() )
						{
							bestFitness = population.getIndividual(i).getFitness();
							bestIndex = i;
						}
					}
				}
				avgFitness += population.getIndividual(i).getFitness();
			}
			avgFitness /= population.getPopulationSize();
			
			// Display statistics
			
			returnValues[0] = population.getGenerationNumber();
			returnValues[1] = (double)population.getPopulationSize();
			returnValues[2] = avgFitness;
			returnValues[3] = (double)bestIndex;
			returnValues[4] = bestFitness;
			
			returnValues[5] = ((Element_Node_TreeGP)population.getIndividual(bestIndex).getElementAt(0)).getDistanceToDeepestTerminal();
			returnValues[6] = ((Element_Node_TreeGP)population.getIndividual(bestIndex).getElementAt(0)).getNumberOfNodes();
			
			return (returnValues);
			
		}
		catch ( java.lang.ClassCastException e )
		{
			Display.error(""+this.getClass().getName()+"::displayStatistics(.) - Class cast exception");
			return (null);
		}
	}
	
}
