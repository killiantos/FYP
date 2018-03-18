/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 2 déc. 2005
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

public class Element_Bit extends Element {

	// ### Data ###

	protected boolean _value;

	// ### Constructors ###
	
	public Element_Bit ( String __name,  Individual __individualSpace, VariationOperator __elementLevelOperator )
	{
		super (__name, __individualSpace, __elementLevelOperator);
	}

	public Element_Bit ( String __name, Individual __individualSpace, ArrayList __elementLevelOperatorList )
	{
		super (__name, __individualSpace, __elementLevelOperatorList);
	}
		
	
	/*public void performInitialisation()
	{
		double value = Math.random();
		if ( value < 0.5 )
			_value = false;
		else
			_value = true;
	}*/
	
	public void setValue( boolean __value )
	{
		_value = __value;
	}

	public boolean getValue()
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
