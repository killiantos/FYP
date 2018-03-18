/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 15 janv. 2006
 * 
 */

package picoevo.core.evolution;

/** This operator is usually called through a performInitialisation method to
 * initialise the object passed as parameter. This operator is usually called
 * only once for each object */
public class InitialisationOperator extends Operator {

	public InitialisationOperator () {super ("unnamed initialisation operator");}
	public InitialisationOperator(String __name) { super(__name); }

}
