// 20060704n

package picoevo.app.SymbolicRegression;

import java.util.ArrayList;
import picoevo.core.representation.Individual;
import picoevo.ext.representation.Element_Node;
import picoevo.toolbox.Misc;


/**
 * This top-level class is the starting point for writing a Factory class for Element_Node.
 * Such a factory object contains only a static method that creates an element_node object.
 * future implementation: object recycling could be implemented here.   
 */

abstract public class Element_Node_Factory {


	abstract public Element_Node build(Individual __owner, ArrayList __operators);
	/*{
		return (null);
	}*/
		
}
