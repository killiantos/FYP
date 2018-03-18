package piconode.ext;

import piconode.core.operator.ActivationFunction;


/*
 * Created on 6 janv. 2005
 * bredeche(at)lri(point)fr
 * 
 */

public class ActivationFunction_copy extends ActivationFunction {
 
    public double applyFunction(double __value) // f(x) = x
    {
    	    return (__value);
	}
    
    public double applyDerivativeFunction(double __value) // f'(x) = x' = 1
    {
    	    return (1);
	}
    
}
