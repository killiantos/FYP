/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 1 dec. 2005
 * 
 */

package picoevo.ext.evolution;

import picoevo.core.*;
import picoevo.core.evolution.*;
import picoevo.core.representation.*;
import picoevo.ext.*;
import picoevo.ext.evolution.*;
import picoevo.ext.representation.*;
import picoevo.toolbox.*;

public class ParameterSet_Evolution_mulambdaES extends ParameterSet {
	
	protected int _mu;
	protected int _lambda;
	protected boolean _mupluslambda;
	
	protected int _genomeSize;
	protected double _mutationRate;
	protected double _crossoverRate;
	
	public void displayInformation()
	{
		Display.info("\n##Parameter Setup##");
		Display.info("- Maximisation?             : " + this.getOptimisationFlag());
		Display.info("- Pop. Initialisation Op.   : " + this.getPopulationInitialisationOperator().getName());
		Display.info("- Ind. Initialisation Op.   : " + this.getIndividualInitialisationOperator().getName());
		//Display.info("- Ind. Evaluation Operator  : " + this.getEvaluationOperator_Individual().getName());
		//Display.info("- Ind. Evaluation Operator  : " + this.getEvaluationOperator_Individual().getName());
		Display.info("- max number of generations : " + this.getGenerations());
		Display.info("- initial population size   : " + this.getInitPopSize());
		Display.info("- genome size               : " + this.getGenomeSize());
		Display.info("- mutation rate             : " + this.getMutationRate());
		Display.info("- mutation rate             : " + this.getCrossoverRate());
		Display.info_nocr("- ES-selection method       : ");
		if ( isMuPlusLambda() == true )
			Display.info("(MU+LAMBDA)");
		else
			Display.info("(MU,LAMBDA)");
		Display.info("- MU                        : " + this.getMu());
		Display.info("- LAMBDA                    : " + this.getLambda());
	}

	public ParameterSet_Evolution_mulambdaES ( )
	{
		super();
	}

	public ParameterSet_Evolution_mulambdaES ( String __filename )
	{
		super(__filename);
	}

	public int getLambda() {
		return _lambda;
	}

	public void setLambda(int _lambda) {
		this._lambda = _lambda;
	}

	public int getMu() {
		return _mu;
	}

	public void setMu(int _mu) {
		this._mu = _mu;
	}

	public boolean isMuPlusLambda() {
		return _mupluslambda;
	}

	public void setMuPlusLambda(boolean _mupluslambda) {
		this._mupluslambda = _mupluslambda;
	}

	
	/**
	 * @return Returns the _genomeSize.
	 */
	public int getGenomeSize() {
		return _genomeSize;
	}
	/**
	 * @param size The _genomeSize to set.
	 */
	public void setGenomeSize(int size) {
		_genomeSize = size;
	}
	/**
	 * @return Returns the _mutationRate.
	 */
	public double getMutationRate() {
		return _mutationRate;
	}
	/**
	 * @param rate The _mutationRate to set.
	 */
	public void setMutationRate(double rate) {
		_mutationRate = rate;
	}
	/**
	 * @return Returns the _crossoverRate.
	 */
	public double getCrossoverRate() {
		return _crossoverRate;
	}
	/**
	 * @param rate The _crossoverRate to set.
	 */
	public void setCrossoverRate(double rate) {
		_crossoverRate = rate;
	}
}
