/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 20 janv. 2006
 * 
 */

package picoevo.app.SymbolicRegression;

import java.util.ArrayList;

import picoevo.core.representation.Individual;
import picoevo.ext.representation.Element_Node;

public class Element_Node_TreeGP_Variable extends Element_Node_TreeGP_Terminal {

	public Element_Node_TreeGP_Variable(Individual __individualOwner, ArrayList __elementLevelOperatorList) {
		super(__individualOwner, __elementLevelOperatorList);
		setArity(0);
		setDisplayValue("x");
	}
	
	public double evaluateDouble ()
	{
		return ( ((MemoryModule_Individual_SymbolicRegression_SingleRegister)((Individual_TreeGP)this.getOwner()).getMemoryModule()).getX() );
	}

}

