package picoevo.ext.evolution;

import picoevo.core.*;
import picoevo.core.evolution.*;
import picoevo.core.representation.*;
import picoevo.ext.*;
import picoevo.ext.representation.*;
import picoevo.toolbox.*;


public class EvaluationOperator_MaxOne_StaticArray_Bit extends EvaluationOperator_Individual {

	// ### Constructors ###
	
	public EvaluationOperator_MaxOne_StaticArray_Bit () 
	{
		super();
	}
	
	public EvaluationOperator_MaxOne_StaticArray_Bit (String __name) 
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
			
			boolean[] bitarray = (boolean[])((Element_StaticArray_Bit)(individual.getElementAt(0))).getArray();
			for ( int i = 0 ; i != ((Element_StaticArray_Bit)(individual.getElementAt(0))).getInitGenotypeLength() ; i++ )
				if (  bitarray[i] == true )
					maxOne++;
			
			individual.setFitness( maxOne / ((Element_StaticArray_Bit)(individual.getElementAt(0))).getInitGenotypeLength() );
		}
		catch ( java.lang.ClassCastException e )
		{
			Display.error(""+this.getClass().getName()+"::evaluate(.) - Class cast exception");
		}
	}

}
