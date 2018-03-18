package picoevo.app.SymbolicRegression;

import java.util.ArrayList;
import picoevo.core.representation.Individual;
import picoevo.ext.representation.Element_Node;

public class Element_Node_TreeGP_Operator_Plus_Factory extends Element_Node_Factory {

	public Element_Node build(Individual __owner, ArrayList __operators) {
		return (Element_Node)(new Element_Node_TreeGP_Operator_Plus(__owner, __operators));
	}

}
