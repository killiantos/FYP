/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 20 aožt 2005
 * 
 * individu dans une population. Synonyme de candidats, points dans l'espace, genotype 
 *
 */

package picoevo.core.representation;

import java.util.ArrayList;
import picoevo.toolbox.Dictionary;

import picoevo.core.evolution.*;
import picoevo.toolbox.Display;

public abstract class Individual extends EvolutionObject implements InitialisationInterface, EvaluationInterface, Cloneable, Comparable {

	// ### Data ###
	
	public Population _populationOwner;
	protected ArrayList _elementList = new ArrayList();
	
	protected boolean _isEvaluated = false;
	protected double _fitness = -1;

	// ### Constructor(s) ###

	public Individual ( String __name , Population __populationOwner )
	{
		_name = __name;
		setPopulationSpace (__populationOwner);
	}
	
	public Individual ( Population __populationOwner )
	{
		setPopulationSpace (__populationOwner);
		_name = new String ("unnamed individual");
	}
	
	// ### Methods ###
	
	/** initialise this individual */
	public void performInitialisation()
	{
		//try {
			((InitialisationOperator_Individual)this.getTemplate().getIndividualInitialisationOperator()).initialise(this);
		//}
		//catch ( NullPointerException e )
		//{
		//	Display.error(""+this.getClass().getName()+"::performInitialisation(.) - no operator available"); 
		//}

	}
	
	/**
	 * 
	 * @return return the fitness value
	 */
	public double getFitness ()
	{
		//if ( isEvaluated() )
		//	Display.critical(""+this.getClass().getName()+"::computeFitness - no evaluation.");
		return ( _fitness );		
	}	
	
	
	/*public void setFitness ( Object __o )
	{
		_fitness = ((Double)__o).doubleValue();
	}*/

	final public void setFitness ( double __d ) // TODO in future implementation, fitness should not be limited to "double"
	{
		setEvaluationFlag(true);
		this._fitness = __d;
	}
	
	/**
	 * update the fitness value by adding the new value
	 *
	 */
	public void updateFitness( double __value )
	{
		this._fitness += __value;
	}

	
	protected void setEvaluationFlag ( boolean __value )
	{
		_isEvaluated = __value;
	}
	
	public Element getElementAt( int __index )
	{
		return ( (Element)this._elementList.get(__index) );
	}
	
	public int getElementListSize ()
	{
		return ( this._elementList.size() );
	}
	
	public boolean isEvaluated ()
	{
		return (_isEvaluated);
	}
	
	public void setPopulationSpace ( Population __populationSpace )
	{
		_populationOwner = __populationSpace;
		//_populationSpace.addIndividual(this);
	}
	
	public void performVariations()
	{
		for ( int i = 0 ; i != this._elementList.size() ; i++ )
			((Element)this._elementList.get(i)).performVariations();
		for ( int i = 0 ; i != this._variationOperatorList.size() ; i++ )
			((VariationOperator)this._variationOperatorList.get(i)).applyOperator(this);
	}
	
	public void performEvaluation() 
	{
		if ( isEvaluated() )
			Display.critical(""+this.getClass().getName()+"::performEvaluation - already evaluated.");
	
		// calling (if any) individual-level evaluation operator
		if ( this.getTemplate().existsEvaluationOperator_Individual() == true )
		{
			((EvaluationOperator_Individual)this.getTemplate().getEvaluationOperator_Individual()).evaluate(this);
		}
		else 
			Display.warning(this.getName()+" : trying to evaluate an individual while no individual-level evaluation operator available.");
		/**/
	}	

	//public abstract void setFitness( Object o );

	public Object clone ()
	{
		Individual individualClone = null;
		
		try 
		{
			individualClone = ( Individual )super.clone();
		}
		catch (CloneNotSupportedException e) 
		{
            // This should never happen
            throw new InternalError(e.toString());
		}
		
		// 1. clone MemoryModule
		if ( this._memoryModule != null )
			individualClone._memoryModule = (MemoryModule)this._memoryModule.clone(); 

		//if ( this._parameterSet != null )
		//	individualClone._parameterSet = (ParameterSet)this._parameterSet.clone(); 
		
		// 2. clone operators
		individualClone._variationOperatorList = new ArrayList();
		for ( int i = 0 ; i != this._variationOperatorList.size() ; i++ )
			individualClone._variationOperatorList.add(((VariationOperator)this._variationOperatorList.get(i)).clone());
		
		// 3. clone elements
		individualClone._elementList = new ArrayList();
		for ( int i = 0 ; i != this._elementList.size() ; i++ )
			individualClone._elementList.add(((Element)this._elementList.get(i)).clone());
		
		return (individualClone);
	}

	
	// ### Other methods ###
	
	// used for unary operator
	public abstract void addElement ( Element __element ); 
	
	
	/**
	 * 
	 * @param 
    */
	public int compareTo(Object __o) 
	{ 
		double fitness1 = this.getFitness(); 
		double fitness2 = ((Individual) __o).getFitness();
		
		if ( this.getTemplate().getOptimisationFlag() == Dictionary.Maximisation ) // Maximising!
		{
			if ( fitness1 > fitness2 )  
				return 1; 
			else 
				if ( fitness1 == fitness2 ) 
					return 0; 
				else 
					return -1;
		}
		else // Minimising!
		{
			if ( fitness1 > fitness2 )  
				return -1; 
			else 
				if ( fitness1 == fitness2 ) 
					return 0; 
				else 
					return 1;
		}			
	}
	
	final public ParameterSet getTemplate() 
	{
		return ((ParameterSet)this.getOwner().getOwner().getTemplate());
	}

	final public Population getOwner ()
	{
		return this._populationOwner;
	}

	/**
	 * Trace elements directly (and possibly indirectly) related to this individual - no return value.
	 * By default, this method performs nothing -- however, descendant classes may overwrite this method to perform user-specified code.
	 * e.g.: specific operator/terminal code for post-evaluation monitoring purpose. 
	 *
	 */
	public void trace ()
	{
		for ( int i = 0 ; i != this.getElementListSize() ; i++ )
			this.getElementAt(i).trace();
	}


}
