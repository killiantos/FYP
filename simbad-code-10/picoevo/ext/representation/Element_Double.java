/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 9 sept. 2005
 * 
 * dérivé d'Element : en autres pour les ES
 * 
 */

package picoevo.ext.representation;

import java.util.ArrayList;

import picoevo.core.*;
import picoevo.core.evolution.*;
import picoevo.core.representation.*;
import picoevo.ext.*;
import picoevo.ext.evolution.*;
import picoevo.ext.representation.*;
import picoevo.toolbox.*;


public class Element_Double extends Element {
	
	private double _value;

	public Element_Double ( String __name,  Individual __individualOwner, VariationOperator __elementLevelOperator )
	{
		super (__name, __individualOwner, __elementLevelOperator);
	}

	public Element_Double ( String __name, Individual __individualOwner, ArrayList __elementLevelOperatorList )
	{
		super (__name, __individualOwner, __elementLevelOperatorList);
	}
		
	/*
	public void performInitialisation()
	{
		_value = Math.random();
	}
	*/
	
	public void setValue( double __value )
	{
		_value = __value;
	}

	public double getValue()
	{
		return (_value);
	}
	
	public void displayInformation()
	{
		Display.info_nocr("( \""+_name+"\",{"+_value+"},");
	    for ( int i = 0 ; i != _variationOperatorList.size() ; i++ )
	    		((VariationOperator)_variationOperatorList.get(i)).displayInformation();
	    Display.info_nocr(" )");
	}
	

}
