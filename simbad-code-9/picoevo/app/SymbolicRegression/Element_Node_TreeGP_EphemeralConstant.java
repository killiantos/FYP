/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 20 janv. 2006
 * 
 */

package picoevo.app.SymbolicRegression;

import java.util.ArrayList;

import picoevo.core.representation.Individual;
import picoevo.ext.representation.Element_Node;
import picoevo.toolbox.Display;

public class Element_Node_TreeGP_EphemeralConstant extends Element_Node_TreeGP_Terminal {

	private double _value = 0;
	
	public Element_Node_TreeGP_EphemeralConstant(Individual __individualOwner, ArrayList __elementLevelOperatorList) {
		super(__individualOwner, __elementLevelOperatorList);
		setArity(0);
	}

	public void setValue(double __value) 
	{
		_value = __value;
	}

	public String getDisplayValue()
	{
		return (new Double(_value).toString());
	}
	
	public void setDisplayValue ( String __displayValue )
	{
		Display.warning(""+this.getClass().getName()+"::setDisplayValue(.) - method does nothing");
	}
	
	public double evaluateDouble ()
	{
		return ( _value );
	}

}

