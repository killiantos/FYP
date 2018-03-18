package piconode.ext;

import piconode.core.*;
import piconode.core.operator.ActivationFunctionWithDerivative;


/*
 * Created on 6 janv. 2005
 * bredeche(at)lri(point)fr
 * 
 */

public class ActivationFunction_piecewiseLinear extends ActivationFunctionWithDerivative { // [!n] method should be revised

    public double applyFunction(double __value) // another well-known non-linear activation function
    {
        System.out.println("Environment.applyPiecewiseLinearFunction(-) - [warning] - check source for exact definition.");
        
        if ( __value < -1 )
            return (-1);
        else
            if ( __value > 1 )
                return (1);
            else 
                return (__value);
    }

    public double applyDerivativeFunction ( double __value ) // f'(x) = ...
    {
    	System.out.println("PieceWiseLinearActivationFunction::applyDerivativeOfActivationFunction(-) - undefined");
    	System.exit(-1);
    	return (-1);
    }

}
