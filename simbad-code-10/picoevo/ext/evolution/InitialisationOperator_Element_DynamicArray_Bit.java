package picoevo.ext.evolution;

import java.util.ArrayList;

import picoevo.core.*;
import picoevo.core.evolution.*;
import picoevo.core.representation.*;
import picoevo.ext.*;
import picoevo.ext.representation.*;
import picoevo.toolbox.*;


public class InitialisationOperator_Element_DynamicArray_Bit extends InitialisationOperator_Element {

	// ### Constructors ###
	
	public InitialisationOperator_Element_DynamicArray_Bit () 
	{
		super();
	}
	
	public InitialisationOperator_Element_DynamicArray_Bit (String __name) 
	{ 
		super(__name); 
	}
	
	// ### Methods ###

	public void initialise( Element __o ) 
	{
		try {
			Element_DynamicArray_Bit element = (Element_DynamicArray_Bit)__o;

			if ( element.getInitGenotypeLength() == -1 )
			{
				Display.error(""+this.getClass().getName()+"::performInitialisation - size of list is incorrect");
				return;
			}
			ArrayList array = (ArrayList)element.getArray();
			for ( int i = 0 ; i != element.getInitGenotypeLength() ; i++ )
			{
				double value = Math.random();
				if ( value < 0.5 )
					array.add(new Boolean(false));
				else
					array.add(new Boolean(true));
			}
		}
		catch ( java.lang.ClassCastException e )
		{
			Display.error(""+this.getClass().getName()+"::initialise(.) - Class cast exception");
		}
	}


}
