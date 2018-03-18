package piconode.ext;

import piconode.core.*;
import piconode.core.operator.ActivationFunctionWithDerivative;
import piconode.toolbox.Tools;


/*
 * Created on 6 janv. 2005
 * bredeche(at)lri(point)fr
 * 
 */

public class ActivationFunction_hyperbolicTangent extends ActivationFunctionWithDerivative {

    public double applyFunction( double __value ) // f(x) = tanh(x) [ f(x) -> [-1,1[ ]
    {
    	return( Tools.tanh(__value) );
    }

    /**
     * compute the value from derivative of the hyperbolic tangent function. Important note : input is tanh(x) !
     */
    public double applyDerivativeFunction( double __tanhValue ) // f'(x) = 1 - tanh(x)^2
    {
    	return ( 1 - __tanhValue * __tanhValue ); 
    }
}
