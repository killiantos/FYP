/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 20 janv. 2006
 * 
 */

package picoevo.app.SymbolicRegression;

import java.util.ArrayList;

import picoevo.core.representation.Individual;
import picoevo.ext.representation.Element_Node;
import picoevo.toolbox.Display;

abstract public class Element_Node_TreeGP_Operator extends Element_Node_TreeGP {

	public Element_Node_TreeGP_Operator(Individual __individualOwner, ArrayList __elementLevelOperatorList) {
		super(__individualOwner, __elementLevelOperatorList);
	}

	public ArrayList getNonTerminalElements ( ArrayList __list )
	{
		for ( int i  = 0 ; i != this.getSuccessors().size() ; i++ )
			((Element_Node_TreeGP)this.getSuccessors().get(i)).getNonTerminalElements(__list);
		__list.add(this);
		return ( __list); // add self and continue
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
		__list.add(this);
		return ( __list); // add self and continue
	}
	

}
