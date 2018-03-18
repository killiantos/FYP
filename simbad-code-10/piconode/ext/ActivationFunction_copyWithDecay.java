package piconode.ext;

import piconode.core.operator.ActivationFunction;


/*
 * Created on 6 janv. 2005
 * bredeche(at)lri(point)fr
 * 
 */

public class ActivationFunction_copyWithDecay extends ActivationFunction {
 
	private double _decay = 0.9;
	
	public ActivationFunction_copyWithDecay ()
	{
		
	}
	
	public ActivationFunction_copyWithDecay ( double __decay )
	{
		_decay = __decay;
	}
	
    public double applyFunction(double __value) // f(x) = x
    {
    	    return (__value*_decay);
	}
    
}
