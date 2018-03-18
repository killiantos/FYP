/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 20 aožt 2005
 *
 */

package picoevo.core.evolution;

import picoevo.core.representation.*;
import picoevo.toolbox.*;

/** This operator is usually called through a performVariation method to
 * apply some variations (e.g. mutation for Element) on the object passed as 
 * parameter or on enclosed objects (e.g. crossover for Population's 
 * Individuals). */
public abstract class VariationOperator extends Operator implements Cloneable {

	protected double _operatorRate = 0.0; // occurence rate

	public VariationOperator ( String __name )
	{
		super (__name);
	}
	
	public VariationOperator ( String __name, double __operatorRate )
	{
		super (__name);
		_operatorRate = __operatorRate;
	}

	public void setRate ( double __operatorRate )
	{
		_operatorRate = __operatorRate;
	}

	public void displayInformation() // do not display topological information
	{
		Display.info_nocr("["+_name+"]");
	}
	
	public abstract void applyOperator( EvolutionObject __evolutionObject );
	
	public Object clone ()
	{
		try 
		{
			return super.clone();
		}
		catch (CloneNotSupportedException e) 
		{
            throw new InternalError(e.toString());
		}
	}
}
