package picoevo.ext.evolution;

import java.util.ArrayList;

import picoevo.core.*;
import picoevo.core.evolution.*;
import picoevo.core.representation.*;
import picoevo.ext.*;
import picoevo.ext.representation.*;
import picoevo.toolbox.*;


public class InitialisationOperator_Element_Double extends InitialisationOperator_Element {

	// ### Data ###
	
	protected double _minValue = 0;
	protected double _maxValue = 1;
	
	// ### Constructors ###
	
	public InitialisationOperator_Element_Double () 
	{
		super();
	}
	
	public InitialisationOperator_Element_Double (String __name) 
	{ 
		super(__name); 
	}
	
	public InitialisationOperator_Element_Double ( double __minValue, double __maxValue) 
	{ 
		if ( __minValue >= __maxValue )
			Display.critical(""+this.getClass().getName()+"::constructor - min and max values are incorrect.");
		_minValue = __minValue;
		_maxValue = __maxValue;
	}

	public InitialisationOperator_Element_Double (String __name, double __minValue, double __maxValue) 
	{ 
		super(__name);
		if ( __minValue >= __maxValue )
			Display.critical(""+this.getClass().getName()+"::constructor - min and max values are incorrect.");
		_minValue = __minValue;
		_maxValue = __maxValue;
	}
	
	// ### Methods ###

	public void initialise( Element __o ) 
	{
		try {
			Element_Double element = (Element_Double)__o;
			double interval = _maxValue - _minValue;
			element.setValue( Math.random() * interval + _minValue);		
		}		
		catch ( java.lang.ClassCastException e )
		{
			Display.error(""+this.getClass().getName()+"::initialise(.) - Class cast exception");
		}
	}


}
