/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 15 janv. 2006
 * 
 */

package picoevo.core.evolution;

/** This operator is usually called through a displayStatistics method to
 * display stats for the object passed as parameter. Defining this operator
 * is usually optional */

public abstract class StatisticsOperator extends Operator {

	public StatisticsOperator(String __name) {	 super(__name); }
	
	abstract public void displayStatistics ( Object __o );
	abstract public void logStatistics ( Object __o, String __filename );
	
}
