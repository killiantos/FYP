/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 15 janv. 2006
 * 
 */

package picoevo.core.evolution;


public abstract class Operator {

	public String _name = "unnamed operator";
	
	public Operator ( String __name )
	{
		_name = __name;
	}
	
	final public String getName ()
	{
		return (_name);
	}
}
