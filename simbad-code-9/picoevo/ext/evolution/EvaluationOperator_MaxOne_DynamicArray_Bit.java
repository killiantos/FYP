package picoevo.ext.evolution;

import java.util.ArrayList;

import picoevo.core.*;
import picoevo.core.evolution.*;
import picoevo.core.representation.*;
import picoevo.ext.*;
import picoevo.ext.representation.*;
import picoevo.toolbox.*;

public class EvaluationOperator_MaxOne_DynamicArray_Bit extends EvaluationOperator_Individual {

	// ### Constructors ###
	
	public EvaluationOperator_MaxOne_DynamicArray_Bit () 
	{
		super();
	}
	
	public EvaluationOperator_MaxOne_DynamicArray_Bit (String __name) 
	{ 
		super(__name); 
	}
	
	// ### Methods ###

	public void evaluate(Object __o) 
	{
		super.evaluate(__o);
		
		try {
			Individual_SimpleIndividual individual = (Individual_SimpleIndividual)__o;
	
			double maxOne = 0;
			
			for ( int i = 0 ; i != ((Element_DynamicArray_Bit)individual.getElementAt(0)).getInitGenotypeLength() ; i++ )
				if ( (((Boolean)((ArrayList)((Element_DynamicArray_Bit)individual.getElementAt(0)).getArray()).get(i)).booleanValue()) == true )
					maxOne++;
				
			individual.setFitness( maxOne / ((Element_DynamicArray_Bit)individual.getElementAt(0)).getInitGenotypeLength() );
		}
		catch ( java.lang.ClassCastException e )
		{
			Display.error(""+this.getClass().getName()+"::evaluate(.) - Class cast exception");
		}
	}

}
