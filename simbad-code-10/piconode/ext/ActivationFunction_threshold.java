package piconode.ext;

import piconode.core.*;
import piconode.core.operator.ActivationFunctionWithDerivative;


/*
 * Created on 6 janv. 2005
 * bredeche(at)lri(point)fr
 * 
 */

public class ActivationFunction_threshold extends ActivationFunctionWithDerivative {

    /* 
     * Data 
     */
    
    protected	static	double _thresholdDefaultValue = 0.5; // default parameter value
    protected	static	double	_thresholdValue; 

    /*
     * Constructor(s)
     */
    
    public ActivationFunction_threshold () 
    {
        _thresholdValue = getDefaultThresholdValue(); 
    }


    public ActivationFunction_threshold ( double __value) 
    {
        _thresholdValue = __value; 
    }

    /*
     * Methods
     */

	static protected double getDefaultThresholdValue ()
	{
	    return ( _thresholdDefaultValue );
	}
	
    public double applyFunction(double __value) // f(x) = 1 if x > threshold, f(x) = O otherwise
    {
	    if ( __value >= _thresholdValue )
	        return (1);
	    else
	        return (0);
    }

    public double applyDerivativeFunction ( double __value ) // f'(x) = ...
    {
    	System.out.println("ThresholdActivationFunction::applyDerivativeOfActivationFunction(-) - undefined");
    	System.exit(-1);
    	return (-1);
    }


}
