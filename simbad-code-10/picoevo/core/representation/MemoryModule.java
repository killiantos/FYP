/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 2 sept. 2005
 *
 */

package picoevo.core.representation;


/**  
 * Useful for internal data storage at different element levels.
 * A Core may (or may not) be passed on to the sub-elements (e.g. Element may/maynot access Individual object)
 * e.g.: a core for linear register-based GP should contains registers for example, memory and so on and be accessed by attribute (which are instruction).
 *
 * Using such an object is not usual 
 * 
 * An example of practical use : Imagine you have a tree that contain Quantifier that are set to 1 only
 * when a special value is encountered in a flow of data lines (case: tree is evaluated for each new line)
 * and this quantifier stays at this value till the end of evaluation. The current class makes it possible
 * to store the value for this quantifier. This is very handy when *one* quantifier can appear in
 * several nodes in the tree (e.g. "true if existed in time before an occurence of data X" used in two
 * subtrees) 
 * 
 */

public abstract class MemoryModule implements Cloneable 
{

	// ### Data ###
	// ### Constructor(s) ###
	// ### Accessing ###
	// ### Other methods ###
	
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
