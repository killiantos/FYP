package picoevo.ext.evolution;

import java.util.ArrayList;

import picoevo.core.*;
import picoevo.core.evolution.*;
import picoevo.core.representation.*;
import picoevo.ext.*;
import picoevo.ext.representation.*;
import picoevo.toolbox.*;


public class InitialisationOperator_Element_StaticArray_Bit extends InitialisationOperator_Element {

	// ### Constructors ###
	
	public InitialisationOperator_Element_StaticArray_Bit () 
	{
		super();
	}
	
	public InitialisationOperator_Element_StaticArray_Bit (String __name) 
	{ 
		super(__name); 
	}
	
	// ### Methods ###

	public void initialise( Element __o ) 
	{
		try {
			Element_StaticArray_Bit element = (Element_StaticArray_Bit)__o;

			if ( element.getInitGenotypeLength() == -1 )
			{
				Display.error(""+this.getClass().getName()+"::performInitialisation - size of list is incorrect");
				return;
			}
			
			boolean[] array = new boolean[element.getInitGenotypeLength()];
			
			for ( int i = 0 ; i != element.getInitGenotypeLength() ; i++ )
			{
				double value = Math.random();
				if ( value < 0.5 )
					array[i]=false;
				else
					array[i]=true;
			}
			
			element.setArray(array);
		}
		catch ( java.lang.ClassCastException e )
		{
			Display.error(""+this.getClass().getName()+"::initialise(.) - Class cast exception");
		}
	}


}
