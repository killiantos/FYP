/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 20 aožt 2005
 *
 * TODO : future evolution - evolutionContainer should be able to contain other evoContainer OR pop.
 */

package picoevo.core.representation;

import java.util.ArrayList;

import picoevo.core.evolution.*;
import picoevo.toolbox.*;

public class World extends EvolutionObject implements EvolvableInterface, InitialisationInterface, EvaluationInterface {

	// ### Data ###
	
	public ParameterSet _parameterSet = null;
	protected ArrayList _populationList = new ArrayList();
	
	// ### Constructor(s) ###
	
	public World ( String __evolutionName, ParameterSet __parameterSet ) 
	{
		_name = __evolutionName;
		_parameterSet = __parameterSet;
	}
	
	public World ( String __evolutionName, String __parameterSetFilename ) 
	{
		_name = __evolutionName;
		Misc.notImplemented("parameter file loading");
	}

	// ### Accessing ###
	
	final public ParameterSet getTemplate() 
	{
		return (_parameterSet);
	}

	public void setTemplate(ParameterSet __parameterSet) 
	{
		_parameterSet = __parameterSet;
	}
	
	public ArrayList getPopulationList() 
	{
		return _populationList;
	}

	public void setPopulationList(ArrayList __list) 
	{
		_populationList = __list;
	}
	
	// ### Other methods ###
	
	/** Initialise world *and* all embedded populations */
	public void performInitialisation () 
	{ 
		for ( int i = 0 ; i != this._populationList.size() ; i++ )
			((Population)this._populationList.get(i)).performInitialisation();
	}
	
	public void displayInformation () 
	{
		Display.info("########################################");
		Display.info("########################################");
		Display.info("###    World Information : summary   ###");
		Display.info("World name : " + this._name );
		Display.info("World populations : " + this._populationList.size() );
		Display.info_nocr("Population-level Operator(s) : ");
	    for ( int i = 0 ; i != _variationOperatorList.size() ; i++ )
			this.getVariationOperator(i).displayInformation();
	    Display.info("\n");
		for ( int i = 0 ; i != this._populationList.size() ; i++ )
			((Population)this._populationList.get(i)).displayInformation();
	    Display.info("\n");
	    Display.info("\n");
		Display.info("########################################");
		Display.info("########################################");
	}

	public void performEvaluation () 
	{
		for ( int i = 0 ; i != this._populationList.size() ; i++ )
			((Population)this._populationList.get(i)).performEvaluation();
	}
	
	public void performVariations()
	{
		for (int i = 0 ; i != this._populationList.size() ; i++ )
			((Population)this._populationList.get(i)).performVariations();
		this.performLevelVariations();
	}
	
	public void registerPopulation ( Population __population )
	{
		this._populationList.add(__population);
	}
	
	public void evolve ( int __steps ) 
	{
		if ( __steps < 0 )
		{
			Display.error(""+this.getClass().getName()+"::evolve(.) - Negative value for generations");
			return;
		}
		for ( int i = 0 ; i != __steps ; i++ )
			evolveOneStep(false);
	}
	
	public void evolve () 
	{ 
		if ( this.getTemplate().getGenerations() < 0 )
		{
			Display.error(""+this.getClass().getName()+"::evolve() - Negative value for generations");
			return;
		}
		for ( int i = 0 ; i != this.getTemplate().getGenerations() ; i++ )
			evolveOneStep(false);
	}

	public void evolveOneStep ( boolean __display )
	{
		for ( int i = 0 ; i != this.getPopulationList().size() ; i++ )
			((Population)this.getPopulationList().get(i)).evolveOneStep(__display);
	}
}
