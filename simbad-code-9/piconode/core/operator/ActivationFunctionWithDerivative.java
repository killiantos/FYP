package piconode.core.operator;
/*
 * Created on 6 janv. 2005
 * bredeche(at)lri(point)fr
 * 
 */

/**
 * This class defines the prototype for any activation function, usually used in the context
 * of artificial neural networks. This class embeds also a prototype for defining the derivative
 * of the function needed by learning algorithm such as back-propagation.  
 */
public abstract class ActivationFunctionWithDerivative extends ActivationFunction {

    /*
     * Methods
     */

	abstract public double applyFunction( double __value ); // f(x)
	abstract public double applyDerivativeFunction( double __value ); // compute derivative of f => f'(x)

}
