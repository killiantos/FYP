/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 1 dec. 2005
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

public class ParameterSet_Evolution_mulambdaES_TreeGP extends ParameterSet_Evolution_mulambdaES {
	
	protected int _maxInitDepth = -1;
	protected int _maxRunningDepth = -1;
	protected int _maxDepthAtMutation = -1;
	protected double _ephemeralConstantMutationRate = 0.0;
	
	public void displayInformation()
	{
		Display.info("\n##Parameter Setup##");
		Display.info("- Maximisation?             : " + this.getOptimisationFlag());
		Display.info("- Pop. Initialisation Op.   : " + this.getPopulationInitialisationOperator().getName());
		Display.info("- Ind. Initialisation Op.   : " + this.getIndividualInitialisationOperator().getName());
		//Display.info("- Ind. Evaluation Operator  : " + this.getEvaluationOperator_Individual().getName());
		//Display.info("- Pop. Evaluation Operator  : " + this.getEvaluationOperator_Population().getName());
		Display.info("- max number of generations : " + this.getGenerations());
		Display.info("- initial population size   : " + this.getInitPopSize());
		Display.info("- max init depth            : " + this.getMaxInitDepth());
		Display.info_nocr("- ES-selection method       : ");
		if ( isMuPlusLambda() == true )
			Display.info("(MU+LAMBDA)");
		else
			Display.info("(MU,LAMBDA)");
		Display.info("- MU                        : " + this.getMu());
		Display.info("- LAMBDA                    : " + this.getLambda());
	}

	public ParameterSet_Evolution_mulambdaES_TreeGP ()
	{
	}

	public ParameterSet_Evolution_mulambdaES_TreeGP ( String __filename )
	{
		super(__filename);
	}

	/**
	 * @return maximum tree depth at initialisation
	 */
	public int getMaxInitDepth ()
	{
		if ( this._maxInitDepth == -1 )
			Display.error(""+this.getClass().getName()+"::getMaxInitDepth() - value not initialised");
		return (this._maxInitDepth);
	}
	
	/**
	 * set the maximum tree depth at initialisation
	 * @param __value
	 */
	public void setMaxInitDepth ( int __value )
	{
		this._maxInitDepth = __value;
	}

	/**
	 * @return maximum tree depth during running
	 */
	public int getMaxRunningDepth ()
	{
		if ( this._maxRunningDepth == -1 )
			Display.error(""+this.getClass().getName()+"::getRunningInitDepth() - value not initialised");
		return (this._maxRunningDepth);
	}
	
	/**
	 * set the maximum tree depth during running
	 * @param __value
	 */
	public void setMaxRunningDepth ( int __value )
	{
		this._maxRunningDepth = __value;
	}
	
	/**
	 * @return maximum tree depth allowed for generated sub-tree when performing mutation (warning : this does not take into account maxRunningDepth !)
	 */
	public int getMaxDepth_Mutation ()
	{
		if ( this._maxDepthAtMutation == -1 )
			Display.error(""+this.getClass().getName()+"::getVariationInitDepth() - value not initialised");
		return (this._maxDepthAtMutation);
	}
	
	/**
	 * set the maximum tree depth allowed for generated sub-tree when performing mutation (warning : this does not take into account maxRunningDepth !)
	 * @param __value
	 */
	public void setMaxDepth_Mutation ( int __value )
	{
		this._maxDepthAtMutation = __value;
	}

	public double getEphemeralConstantMutationRate()
	{
		return this._ephemeralConstantMutationRate;
	}
	
	public void setEphemeralConstantMutationRate( double __value )
	{
		_ephemeralConstantMutationRate = __value;
	}

}
