/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 2 sept. 2005
 *
 * parameter set repository
 *  
 */

package picoevo.core.evolution;

import java.util.ArrayList;

import picoevo.core.evolution.*;
import picoevo.ext.evolution.StatisticsOperator_Population;
import picoevo.toolbox.*;

public abstract class ParameterSet implements Cloneable {

	// ### Data ###
	
	private ArrayList _keys = new ArrayList();
	private ArrayList _values = new ArrayList();
	
	private int _generations = -1; // number of generations to evolve
	private int _initPopSize = -1;
	private boolean _optimisationFlag = true; // true: maximise! ; false: minimise!
	
	private SelectionOperator _selectionOperator = null;
	private EvaluationOperator_Individual _evaluationOperator_Individual = null;
	private EvaluationOperator_Population _evaluationOperator_Population = null;
	private InitialisationOperator_Population _initialisationOperator_Population = null;
	private InitialisationOperator_Individual _initialisationOperator_Individual = null;
	private InitialisationOperator_Element _initialisationOperator_Element = null;
	
	private StatisticsOperator _statisticsOperator_Population = null;
	
	private String _logFilename = null;
	
	// ### Constructors ###
	
	public ParameterSet ()
	{
	}
	
	public ParameterSet ( String __filename )
	{
		Misc.notImplemented(this,"constructor(filename)");
	}
	
	// ### Parameter List Methods ###
	
	public void put ( String __key, Object __value )
	{
		_keys.add(__key);
		_values.add(__value);
	}
	
	public void put ( String __key, boolean __value )
	{
		_keys.add(__key);
		_values.add(new Boolean(__value));
	}

	public void put ( String __key, double __value )
	{
		_keys.add(__key);
		_values.add(new Double(__value));
	}

	public Object get ( String __key )
	{
		int i;
		for ( i = 0 ; i != _keys.size() ; i++ )
			if ( (((String)_keys.get(i)).compareTo(__key)) == 0 )
				break;

		if ( i == _keys.size() )
			Display.critical("ParameterSet: key ["+__key+"] does not exist.");
		
		return ( _values.get(i)); 
	}	
	
	public double getDoubleValue ( String __key )
	{
		return ((Double)this.get(__key)).doubleValue();
	}
	
	public boolean getBooleanValue ( String __key )
	{
		return ((Boolean)this.get(__key)).booleanValue();
	}
	
	public void displayParameters ()
	{
		Display.info("Registered parameters.");
		for ( int i = 0 ; i != _keys.size() ; i++ )
			Display.info("["+((String)_keys.get(i))+"] => ["+_values.get(i)+"]");
	}	
	
	// ### Methods ###
	
	public SelectionOperator getSelectionOperator ()
	{
		if ( this._selectionOperator == null )
			Display.error(""+this.getClass().getName()+"::getSelectionOperator(.) - selection operator not initialised.");
		return (_selectionOperator);
	}
	
	public void setSelectionOperator ( SelectionOperator __selectionOperator )
	{
		_selectionOperator = __selectionOperator ;
	}
	
	public boolean existsEvaluationOperator_Individual ()
	{
		if ( this._evaluationOperator_Individual == null )
			return (false);
		else
			return (true);
	}

	public EvaluationOperator_Individual getEvaluationOperator_Individual ()
	{
		if ( this._evaluationOperator_Individual == null )
			Display.error(""+this.getClass().getName()+"::getEvaluationOperator_Individual(.) - individual-level evaluation operator not initialised.");
		return (_evaluationOperator_Individual);
	}
	
	public void setEvaluationOperator_Individual ( EvaluationOperator_Individual __evaluationOperator )
	{
		_evaluationOperator_Individual = __evaluationOperator ;
	}
	
	public boolean existsEvaluationOperator_Population ()
	{
		if ( this._evaluationOperator_Population == null )
			return (false);
		else
			return (true);
	}

	public EvaluationOperator_Population getEvaluationOperator_Population ()
	{
		if ( this._evaluationOperator_Population == null )
			Display.error(""+this.getClass().getName()+"::getEvaluationOperator_Population(.) - population-level evaluation operator not initialised.");
		return (_evaluationOperator_Population);
	}
	
	public void setEvaluationOperator_Population ( EvaluationOperator_Population __evaluationOperator )
	{
		_evaluationOperator_Population = __evaluationOperator ;
	}
	
	public InitialisationOperator_Population getPopulationInitialisationOperator ()
	{
		if ( this._initialisationOperator_Population == null )
			Display.error(""+this.getClass().getName()+"::getPopulationInitialisationOperator(.) - initialisation operator not initialised.");
		return (_initialisationOperator_Population);
	}
	
	public void setPopulationInitialisationOperator ( InitialisationOperator_Population __initialisationOperator_Population )
	{
		_initialisationOperator_Population = __initialisationOperator_Population ;
	}
	
	public InitialisationOperator_Individual getIndividualInitialisationOperator ()
	{
		if ( this._initialisationOperator_Individual == null )
			Display.error(""+this.getClass().getName()+"::getIndividualInitialisationOperator(.) - initialisation operator not initialised.");
		return (_initialisationOperator_Individual);
	}
	
	public void setIndividualInitialisationOperator ( InitialisationOperator_Individual __initialisationOperator_Individual )
	{
		_initialisationOperator_Individual = __initialisationOperator_Individual ;
	}

	public InitialisationOperator_Element getElementInitialisationOperator() {
		if ( this._initialisationOperator_Element == null )
			Display.error(""+this.getClass().getName()+"::getElementInitialisationOperator(.) - initialisation operator not initialised.");
		return _initialisationOperator_Element;
	}

	public void setElementInitialisationOperator( InitialisationOperator_Element __operator) {
		_initialisationOperator_Element = __operator;
	}
	
	public StatisticsOperator getPopulationStatisticsOperator ()
	{
		if ( this._statisticsOperator_Population == null )
			Display.error(""+this.getClass().getName()+"::getPopulationStatisticsOperator(.) - operator not set.");
		return (_statisticsOperator_Population);
	}
	
	public void setPopulationStatisticsOperator ( StatisticsOperator __statisticsOperator_Population )
	{
		_statisticsOperator_Population = __statisticsOperator_Population ;
	}

	abstract public void displayInformation();
	
	/** get the number of generations to evolve */
	public int getGenerations ()
	{
		return (_generations);
	}
	
	/** set the number of generations to evolve */
	public void setGenerations ( int __generations )
	{
		_generations = __generations;
	}
	
	public int getInitPopSize ()
	{
		return (_initPopSize);
	}
	
	public void setInitPopSize ( int __initPopSize )
	{
		_initPopSize = __initPopSize;
	}
	
	public String getLogFilename() 
	{
		return _logFilename;
	}
	
	public void setLogFilename(String __logFilename) 
	{
		_logFilename = __logFilename;
	}

	public Object clone ()
	{
		try 
		{
			return super.clone();
		}
		catch (CloneNotSupportedException e) 
		{
            throw new InternalError(e.toString());
		}
	}

	/** get optimisation mode. "true" means maximisation, "false" means minimisation */
	public boolean getOptimisationFlag ()
	{
		return (this._optimisationFlag);
	}
	
	/** set optimisation mode. "true" means maximisation, "false" means minimisation */
	public void setOptimisationFlag( boolean __optimisationFlag )
	{
		this._optimisationFlag = __optimisationFlag;
	}
	
	/** check if all parameters are correctly initialised (debug purpose) - should be rewritten in inherited classes */
	public void check ()
	{
		int nb = 0;
		Display.info("\n# Checking Parameters integrity #");
		if ( _generations == -1 )
		{
			Display.warning(" number of generations not set.");
			nb++;
		}
		if ( _initPopSize == -1 )
		{
			Display.warning(" initial population size not set.");
			nb++;
		}
		if ( _selectionOperator == null )
		{
			Display.warning(" Selection Operator not set.");
			nb++;
		}
		if ( _initialisationOperator_Population == null )
		{
			Display.warning(" Population Initialisation Operator not set");
			nb++;
		}
		if ( _initialisationOperator_Individual == null )
		{
			Display.warning(" Individual Initialisation Operator not set");
			nb++;
		}
		if ( _evaluationOperator_Individual == null )
		{
			Display.warning(" Individual Evaluation Operator not set.");
			nb++;
		}
		if ( _statisticsOperator_Population == null )
		{
			Display.warning(" Population Statistics Operator not set.");
			nb++;
		}
		if ( nb == 0 )
			Display.info("ok.");
		else
			Display.info(""+nb+" warning(s).");
	}


	
	/*
	// Lambda/mu parameters
	int LAMBDAPLUSMU = 0; 
	int LAMBDACOMAMU = 1; 
	
	// selection method
	int TOURNAMENTSELECTION = 0;
	int RANKSELECTION = 1;
	int RANDOMSELECTION = 2;

	// general-purpose parameters
	public int _currentGeneration = 0;
	public int _maxGenerations = 0;
	public int _populationSize = 0;
	*/
	
	
	/*
	// Lambda/mu parameters
	static int LAMBDAPLUSMU = 0; 
	static int LAMBDACOMAMU = 1; 
	
	// selection method
	static int TOURNAMENTSELECTION = 0;
	static int RANKSELECTION = 1;
	static int RANDOMSELECTION = 2;
	*/

}
