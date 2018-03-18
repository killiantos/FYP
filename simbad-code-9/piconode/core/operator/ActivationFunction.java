package piconode.core.operator;
/*
 * Created on 6 janv. 2005
 * bredeche(at)lri(point)fr
 * 
 */

/**
 * This class defines the prototype for any activation function, usually used in the context
 * of artificial neural networks 
 */
public abstract class ActivationFunction extends Operator {

    /*
     * Methods
     */

	abstract public double applyFunction( double __value ); // f(x)
}

