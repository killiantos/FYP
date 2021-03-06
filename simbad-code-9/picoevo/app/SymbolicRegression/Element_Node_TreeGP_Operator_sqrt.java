/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 20 janv. 2006
 * 
 */

package picoevo.app.SymbolicRegression;

import java.util.ArrayList;

import picoevo.core.representation.*;
import picoevo.ext.representation.*;
import picoevo.toolbox.*;

public class Element_Node_TreeGP_Operator_sqrt extends Element_Node_TreeGP_Operator {

	public Element_Node_TreeGP_Operator_sqrt(Individual __individualOwner, ArrayList __elementLevelOperatorList) {
		super(__individualOwner, __elementLevelOperatorList);
		setArity(1);
		setDisplayValue("sqrt");
	}

	public double evaluateDouble ()
	{
		ArrayList list = this.getSuccessors();
		if ( list.size() != 1 )
			Display.error(""+getClass().getName()+"::evaluate() - incorrect number of successors ("+list.size()+")");
		return ( Math.sqrt(Math.abs(((Element_Node_TreeGP)list.get(0)).evaluateDouble())));
	}
	
}

