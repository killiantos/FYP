/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 9 sept. 2005
 * 
 * dérivé d'élement : utile pour la PG avec graphe ou arbre
 * 
 */

package picoevo.ext.representation;

import java.util.ArrayList;

import picoevo.core.*;
import picoevo.core.evolution.*;
import picoevo.core.representation.*;
import picoevo.ext.*;
import picoevo.ext.evolution.*;
import picoevo.ext.representation.*;
import picoevo.toolbox.*;

abstract public class Element_Node extends Element {
	
	protected String _displayValue = "?";

	private ArrayList _previousElements = new ArrayList();
	private ArrayList _nextElements = new ArrayList();
	
	private int _arity = -1; // default is -1
	
	public Element_Node ( String __name,  Individual __individualOwner, VariationOperator __elementLevelOperator )
	{
		super (__name, __individualOwner, __elementLevelOperator);
	}
  
	public Element_Node ( String __name,  Individual __individualOwner, ArrayList __elementLevelOperatorList )
	{
		super (__name, __individualOwner, __elementLevelOperatorList);
	}

	public Element_Node ( Individual __individualOwner, VariationOperator __elementLevelOperator )
	{
		super ("unnamed element", __individualOwner, __elementLevelOperator);
	}
	
	public Element_Node ( Individual __individualOwner, ArrayList __elementLevelOperatorList )
	{
		super ("unnamed element", __individualOwner, __elementLevelOperatorList);
	}

	/** add a target element as successor to this element (warning, current element is not added as predecessor to the target element) */ 
	public void addSuccessor ( Element_Node __element ) 
	{
		_nextElements.add( __element );
	}
	
	/** add a target element as predecessor to this element (warning, current element is not added as successor to the target element) */
	public void addPredecessor ( Element_Node __element ) 
	{
		_previousElements.add( __element );
	}
	
	public ArrayList getSuccessors ( ) 
	{
		return ( this._nextElements );
	}
	
	public ArrayList getPredecessors ( ) 
	{
		return ( this._previousElements );
	}

	/**
	 * Returns the arity of this node (e.g. 0 if terminal, 2 if PLUS op, etc.).
	 * @return arity
	 */
	public int getArity() {
		return _arity;
	}
	/**
	 * set the arity of this node (e.g. 0 if terminal, 2 if PLUS op, etc.).
	 * @param _arity 
	 */
	public void setArity(int _arity) {
		this._arity = _arity;
	}

	public String getDisplayValue ()
	{
		return (_displayValue);
	}
	
	public void setDisplayValue ( String __displayValue )
	{
		_displayValue = __displayValue;
	}
	

	/**
	 * Reset all connections to successors and predecessors. I.e. this element is now root and terminal. 
	 * Note that connections (if any) from successors/predecessors remain unmodified.
	 *
	 */
	public void resetAllConnections()
	{
		this._nextElements = new ArrayList();
		this._previousElements = new ArrayList();
	}

	/**
	 * Reset all connections to predecessor. I.e. this element is now root. 
	 * Note that connections (if any) from predecessors remain unmodified.
	 */
	public void resetPredecessorConnections()
	{
		this._previousElements = new ArrayList();
	}

	/**
	 * Reset all connections to successor. I.e. this element is now terminal. 
	 * Note that connections (if any) from successors remain unmodified.
	 */
	public void resetSuccessorConnections()
	{
		this._nextElements = new ArrayList();
	}
}



