/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 2 sept. 2005
 *
 * Useful for internal data storage at different element levels.
 * A substrata may (or may not) be passed on to the sub-elements (e.g. Attribute may/maynot acces IndividualEnvironment object)
 * e.g.: an IndividualSubstrata for linear register-based GP should contains registers, memory and so on and be accessed by attribute (which are instruction).
 *
 * TODO (for future extensions)
 * 
 * [20060113] : Currently unused (example purpose)
 *  
 */

package picoevo.ext.representation;

import picoevo.core.*;
import picoevo.core.evolution.*;
import picoevo.core.representation.*;
import picoevo.ext.*;
import picoevo.ext.evolution.*;
import picoevo.ext.representation.*;
import picoevo.toolbox.*;

public class MemoryModule_Individual_DoubleRegisters extends MemoryModule {

	double[] _registersList; 
	
	MemoryModule_Individual_DoubleRegisters ( int __nbRegisters )
	{
		_registersList = new double[__nbRegisters];
	}
	
	double getRegisterValue ( int __index )
	{
		if ( __index > _registersList.length -1 && __index < 0)
			Display.error(""+this.getClass().getName()+"::getRegisterValue(.) - out of range");
		return ( _registersList[__index] );
	}
	
	void setRegisterValue ( int __index, double __value )
	{
		if ( __index > _registersList.length -1 && __index < 0)
			Display.error(""+this.getClass().getName()+"::setRegisterValue(.) - out of range");
		_registersList[__index] = __value ;
	}
}
