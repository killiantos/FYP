/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 20 janv. 2006
 * 
 */

package picoevo.app.SymbolicRegression;

import java.util.ArrayList;

import picoevo.core.evolution.InitialisationOperator_Element;
import picoevo.core.representation.Individual;
import picoevo.ext.representation.Element_Node;
import picoevo.toolbox.Display;

/** this tree node is the *root* of a tree. It may appear exactly once in every 
 * trees and have a default arity on 1 while >1 is possible (e.g. main tree + AdF)
 */
public class Element_Node_TreeGP_Root extends Element_Node_TreeGP {

	private boolean _tempRoot = false;
	
	public Element_Node_TreeGP_Root(Individual __individualOwner, ArrayList __elementLevelOperatorList) {
		super(__individualOwner, __elementLevelOperatorList);
		setArity(1);
		setDisplayValue("(root)");
	}
	
	/**
	 * create a *temporary* Root. Use **only** when manipulating trees - a tree starting with a temporary root should only be used locally (i.e. no inclusion in the evolution process) 
	 *
	 */
	public Element_Node_TreeGP_Root(Individual __individualOwner)
	{
		super (__individualOwner,null);
		_tempRoot = true;
		setArity(1);
		setDisplayValue("(Temporary Root)");
	}
	
	public double evaluateDouble ()
	{
		if ( this._tempRoot == true )
			Display.error(""+this.getClass().getName()+"::evaluateDouble() - cannot evaluate tree starting with temporary root");
			
		ArrayList list = this.getSuccessors();
		if ( list.size() != 1 )
			Display.error(""+this.getClass().getName()+"::evaluateDouble() - incorrect number of successors ("+list.size()+")");

		return ( ((Element_Node_TreeGP)list.get(0)).evaluateDouble() );
	}
	
	public boolean evaluateBoolean ()
	{
		if ( this._tempRoot == true )
			Display.error(""+this.getClass().getName()+"::evaluateBoolean() - cannot evaluate tree starting with temporary root");
			
		ArrayList list = this.getSuccessors();
		if ( list.size() != 1 )
			Display.error(""+this.getClass().getName()+"::evaluateBoolean() - incorrect number of successors ("+list.size()+")");

		return ( ((Element_Node_TreeGP)list.get(0)).evaluateBoolean() );
	}
	
	public ArrayList getNonTerminalElements ( ArrayList __list )
	{
		for ( int i  = 0 ; i != this.getSuccessors().size() ; i++ )
			((Element_Node_TreeGP)this.getSuccessors().get(i)).getNonTerminalElements(__list);
		return ( __list); // continue
	}

	public ArrayList getTerminalElements ( ArrayList __list )
	{
		for ( int i  = 0 ; i != this.getSuccessors().size() ; i++ )
			((Element_Node_TreeGP)this.getSuccessors().get(i)).getTerminalElements(__list);
		return ( __list ); // continue
	}
	
	public ArrayList getAllElements ( ArrayList __list )
	{
		for ( int i  = 0 ; i != this.getSuccessors().size() ; i++ )
			((Element_Node_TreeGP)this.getSuccessors().get(i)).getAllElements(__list);
		return ( __list ); // continue
	}

	/** 
	 * root node depth is 0.
	 */
	public int getCurrentDepth()
	{
		return 0;
	}

	/**
	 * 
	 * @return the number of nodes in the sub tree starting this node (included) 
	 * - root node does not count.
	 */
	public int getNumberOfNodes()
	{
		int numberOfNodes = 0;
		for ( int i = 0 ; i != this.getSuccessors().size() ; i++ )
		{
			numberOfNodes += ((Element_Node_TreeGP)this.getSuccessors().get(i)).getNumberOfNodes();
		}
		return (numberOfNodes);  // [developper's note : see also corresponding methods in Element_Node_TreeGP_Terminal and Element_Node_TreeGP]
	}

}

