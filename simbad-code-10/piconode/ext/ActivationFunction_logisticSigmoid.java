package piconode.ext;

import piconode.core.*;
import piconode.core.operator.ActivationFunctionWithDerivative;


/*
 * Created on 6 janv. 2005
 * bredeche(at)lri(point)fr
 * 
 */

public class ActivationFunction_logisticSigmoid extends ActivationFunctionWithDerivative {

    /*
     * Data
     */
    

    protected	static	double _sigmoidDefaultKValue = 1; // default parameter value 
    private	double	_sigmoidKValue; // more or less flat sigmoid (->0) ou "threshlold" (parameter "k")
    
    /*
     * Constructor(s)
     */

    public ActivationFunction_logisticSigmoid () 
    {
        _sigmoidKValue = getDefaultSigmoidKValue(); 
    }

    public ActivationFunction_logisticSigmoid ( double __value ) 
    {
        _sigmoidKValue = __value; 
    }

    /*
     * Method(s)
     */
    
	static protected double getDefaultSigmoidKValue ()
	{
	    return ( _sigmoidDefaultKValue );
	}

    public double applyFunction( double __value ) 	// f(x) = 1 / ( 1 + e^(-k*x)) [ sigmoid function - f(x) is [0,1] ]
	{
   	    return( 1 / ( 1 + Math.exp( - _sigmoidKValue * __value )) );
	}
    
    public double applyDerivativeFunction( double __value ) // f'(x) = x * ( 1 - x )
    {
    	return( __value * ( 1 - __value ) );
    }

}