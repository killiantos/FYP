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

public class Element_Node_TreeGP_Operator_Minus extends Element_Node_TreeGP_Operator {

	public Element_Node_TreeGP_Operator_Minus(Individual __individualOwner, ArrayList __elementLevelOperatorList) {
		super(__individualOwner, __elementLevelOperatorList);
		setArity(2);
		setDisplayValue("-");
	}

	public double evaluateDouble ()
	{
		ArrayList list = this.getSuccessors();
		if ( list.size() != 2 )
			Display.error(""+getClass().getName()+"::evaluate() - incorrect number of successors ("+list.size()+")");
		return ( ((Element_Node_TreeGP)list.get(0)).evaluateDouble() - ((Element_Node_TreeGP)list.get(1)).evaluateDouble() );
	}
}

