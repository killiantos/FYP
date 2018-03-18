/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 20 aožt 2005
 *
 * un element d'un genome -- synonyme d'attribut, de noeud, etc.
 *
 */
package picoevo.core.representation;

import java.util.ArrayList;

import picoevo.core.evolution.*;
import picoevo.toolbox.Display;


public abstract class Element extends EvolutionObject implements InitialisationInterface, Cloneable {

	protected Individual _individualOwner;
	
	public Element ( String __name,  Individual __individualOwner, VariationOperator __elementLevelOperator )
	{
		_name = __name;
		_individualOwner = __individualOwner;
		_variationOperatorList.add(__elementLevelOperator);
	}

	public Element ( String __name,  Individual __individualOwner, ArrayList __elementLevelOperatorList )
	{
		_name = __name;
		_individualOwner = __individualOwner;
		_variationOperatorList = __elementLevelOperatorList;
	}

	public Element ( Individual __individualOwner, VariationOperator __elementLevelOperator )
	{
		_name = new String ("unnamed element");
		_individualOwner = __individualOwner;
		_variationOperatorList.add(__elementLevelOperator);
	}
	
	public Element ( Individual __individualOwner, ArrayList __elementLevelOperatorList )
	{
		_name = new String ("unnamed element");
		_individualOwner = __individualOwner;
		_variationOperatorList = __elementLevelOperatorList;
	}
	
	public void displayInformation() 
	{
	    System.out.print("( \""+_name+"\",");
	    for ( int i = 0 ; i != _variationOperatorList.size() ; i++ )
	    		((VariationOperator)_variationOperatorList.get(i)).displayInformation();
	    System.out.print(" )");
	}

	public void performVariations ()
	{
		this.performLevelVariations();
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
	
	final public ParameterSet getTemplate() 
	{
		return ((ParameterSet)this.getOwner().getOwner().getOwner()._parameterSet);
	}
	
	final public Individual getOwner ()
	{
		return this._individualOwner;
	}

	/** Initialise this element -- this method may be overridden with an empty method if initialisation is performed elsewhere (see "Element_*array_*" classes) 
	 * If a nullPointerException is thrown, that means EITHER :
	 *  (1) operator was not set ... OR
	 *  (2) this method should not be called (initialised elsewhere)
	 * */
	public void performInitialisation ()
	{
		try {
			((InitialisationOperator_Element)this.getTemplate().getElementInitialisationOperator()).initialise(this);
		}
		catch ( NullPointerException e )
		{//"[\""+this.getName()+"\"]"+
			Display.error(""+this.getClass().getName()+"::performInitialisation(.) - no operator available"); 
		}
	}
	
	/**
	 * Trace this elements - no return value.
	 * By default, this method performs nothing -- however, descendant classes may overwrite this method to perform user-specified code.
	 * e.g.: specific operator/terminal code for post-evaluation monitoring purpose.
	 * Note: careful when overwriting this method when considering cycle issues 
	 *
	 */
	public void trace ()
	{
		// nothing to do -- method may be overwritten
	}

}
