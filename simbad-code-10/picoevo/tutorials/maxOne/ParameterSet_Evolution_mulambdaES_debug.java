/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 1 dec. 2005
 * 
 */

package picoevo.tutorials.maxOne;

import picoevo.core.*;
import picoevo.core.evolution.*;
import picoevo.core.representation.*;
import picoevo.ext.*;
import picoevo.ext.evolution.*;
import picoevo.ext.representation.*;
import picoevo.toolbox.*;

public class ParameterSet_Evolution_mulambdaES_debug extends ParameterSet {
	
	protected int _mu;
	protected int _lambda;
	protected boolean _mupluslambda;
	
	protected int _genomeSize;
	protected double _mutationRate;
	
	public void displayInformation()
	{
		Display.info("\n##Parameter Setup##");
		Display.info("- Maximisation?             : " + this.getOptimisationFlag());
		Display.info("- Pop. Initialisation Op.   : " + this.getPopulationInitialisationOperator().getName());
		Display.info("- Ind. Initialisation Op.   : " + this.getIndividualInitialisationOperator().getName());
		Display.info("- Ind. Evaluation Operator  : " + this.getEvaluationOperator_Individual().getName());
		Display.info("- max number of generations : " + this.getGenerations());
		Display.info("- initial population size   : " + this.getInitPopSize());
		Display.info("- genome size               : " + this.get_genomeSize());
		Display.info("- mutation rate             : " + this.get_mutationRate());
		Display.info_nocr("- ES-selection method       : ");
		if ( is_mupluslambda() == true )
			Display.info("(MU+LAMBDA)");
		else
			Display.info("(MU,LAMBDA)");
		Display.info("- MU                        : " + this.get_mu());
		Display.info("- LAMBDA                    : " + this.get_lambda());
	}

	public ParameterSet_Evolution_mulambdaES_debug ( int __initPopSize, int __generations )
	{
		this.setInitPopSize(__initPopSize);
		this.setGenerations(__generations);
	}

	public ParameterSet_Evolution_mulambdaES_debug ( int __initPopSize, int __generations , int __mu, int __lambda , boolean __mupluslambda, int __genomeSize, double __mutationRate )
	{
		this.setInitPopSize(__initPopSize);
		this.setGenerations(__generations);
		_mu = __mu;
		_lambda = __lambda;
		_mupluslambda = __mupluslambda;
		_genomeSize = __genomeSize;
		_mutationRate = __mutationRate;
	}

	public int get_lambda() {
		return _lambda;
	}

	public void set_lambda(int _lambda) {
		this._lambda = _lambda;
	}

	public int get_mu() {
		return _mu;
	}

	public void set_mu(int _mu) {
		this._mu = _mu;
	}

	public boolean is_mupluslambda() {
		return _mupluslambda;
	}

	public void set_mupluslambda(boolean _mupluslambda) {
		this._mupluslambda = _mupluslambda;
	}

	
	/**
	 * @return Returns the _genomeSize.
	 */
	public int get_genomeSize() {
		return _genomeSize;
	}
	/**
	 * @param size The _genomeSize to set.
	 */
	public void set_genomeSize(int size) {
		_genomeSize = size;
	}
	/**
	 * @return Returns the _mutationRate.
	 */
	public double get_mutationRate() {
		return _mutationRate;
	}
	/**
	 * @param rate The _mutationRate to set.
	 */
	public void set_mutationRate(double rate) {
		_mutationRate = rate;
	}
}
