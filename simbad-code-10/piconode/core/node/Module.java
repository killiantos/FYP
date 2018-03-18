/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 16 fŽvr. 2006
 * 
 */

package piconode.core.node;

import java.util.ArrayList;

import piconode.core.arc.ModularArc;
import piconode.toolbox.Display;

/**
 * The abstract Module class provides the minimal setting to define modular node, i.e. node
 * that embeds a set of sub-nodes such as a neural network or a hand-written function. The
 * arity may be N->M. A module may be used as a stand-alone (e.g. a neural net) or embedded 
 * in another module (e.g. "supervisor controler" made of cascading neural nets).
 */

public abstract class Module extends ModularNode {

	private double [] _inputRegister;
	private double [] _outputRegister;
	private double [] _oldOutputRegister;
	
	/** 
	 * perform one running step with the input values contained in the array.
	 * @param __inputValuesList
	 */
	abstract public void step ( ArrayList __inputValuesList );
	abstract public void stepModule ();
	
	public int getInputRegisterSize()
	{
		return _inputRegister.length;
	}
	
	public int getOutputRegisterSize()
	{
		return _outputRegister.length;
	}

	public int getOldOutputRegisterSize()
	{
		return _oldOutputRegister.length;
	}
	
	
	public void setInputRegisterSize(int size)
	{
		_inputRegister=new double[size];
	}
	
	public void setOutputRegisterSize(int size)
	{
		_outputRegister=new double[size];
	}

	public void setOldOutputRegisterSize(int size)
	{
		_oldOutputRegister=new double[size];
	}
	
	
	
	
	
	public double getInputRegister( int index ) {
		return _inputRegister[index];
	}
	public void setInputRegister( int index, double value) {
		_inputRegister [index] = value;
	}
	public double getOutputRegister( int index ) {
		return _outputRegister[index];
	}
	public void setOutputRegister( int index, double value ) {
		_outputRegister [index] = value;
	}
	
	/**/
	public double getOldOutputRegister( int index ) {
		return _oldOutputRegister[index];
	}
	public void setOldOutputRegister( int index, double value) {
		_oldOutputRegister[index] = value;
	}
	
	
	
	/** the following methods are used for connecting modules */
	/* TODO : bp200603011802n [!n]
	abstract public int getInputArity ();
	abstract public int getOutputArity();
    abstract public void setInputValue(int __index, double __value);
    abstract public double getOutputValue(int __index);
    abstract public ValueContainer getInputObjectAt(int __index);
	abstract public ValueContainer getOutputObjectAt(int __index);
	*/
}
