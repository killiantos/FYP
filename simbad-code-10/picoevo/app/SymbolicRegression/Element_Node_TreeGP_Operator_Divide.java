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

public class Element_Node_TreeGP_Operator_Divide extends Element_Node_TreeGP_Operator {

	public Element_Node_TreeGP_Operator_Divide(Individual __individualOwner, ArrayList __elementLevelOperatorList) {
		super(__individualOwner, __elementLevelOperatorList);
		setArity(2);
		setDisplayValue("/");
	}

	/** perform a protected division (i.e. if ( b == 0 ) then return (a/b) else return (1) )*/
	public double evaluateDouble ()
	{
		ArrayList list = this.getSuccessors();
		
		if ( list.size() != 2 )
			Display.error(""+getClass().getName()+"::evaluate() - incorrect number of successors ("+list.size()+")");
		
		double result = ((Element_Node_TreeGP)list.get(1)).evaluateDouble(); 
		
		if ( result == 0 ) // protected division
			return (1);
		else
			return (((Element_Node_TreeGP)list.get(0)).evaluateDouble() / result);
	}
}

