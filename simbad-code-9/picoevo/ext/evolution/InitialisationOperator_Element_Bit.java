package picoevo.ext.evolution;

import java.util.ArrayList;

import picoevo.core.*;
import picoevo.core.evolution.*;
import picoevo.core.representation.*;
import picoevo.ext.*;
import picoevo.ext.representation.*;
import picoevo.toolbox.*;


public class InitialisationOperator_Element_Bit extends InitialisationOperator_Element {

	// ### Constructors ###
	
	public InitialisationOperator_Element_Bit () 
	{
		super();
	}
	
	public InitialisationOperator_Element_Bit (String __name) 
	{ 
		super(__name); 
	}
	
	// ### Methods ###

	public void initialise( Element __o ) 
	{
		try {
			Element_Bit element = (Element_Bit)__o;

			double value = Math.random();
			if ( value < 0.5 )
				element.setValue(false);
			else
				element.setValue(true);		
		}		
		catch ( java.lang.ClassCastException e )
		{
			Display.error(""+this.getClass().getName()+"::initialise(.) - Class cast exception");
		}
	}


}
