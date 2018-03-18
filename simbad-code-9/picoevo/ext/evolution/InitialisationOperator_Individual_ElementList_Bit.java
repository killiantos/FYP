package picoevo.ext.evolution;

import java.util.ArrayList;

import picoevo.core.*;
import picoevo.core.evolution.*;
import picoevo.core.representation.*;
import picoevo.ext.*;
import picoevo.ext.representation.*;
import picoevo.toolbox.*;


public class InitialisationOperator_Individual_ElementList_Bit extends InitialisationOperator_Individual {

	// ### Constructors ###
	
	public InitialisationOperator_Individual_ElementList_Bit () 
	{
		super();
	}
	
	public InitialisationOperator_Individual_ElementList_Bit (String __name) 
	{ 
		super(__name); 
	}
	
	// ### Methods ###

	public void initialise( Individual __o ) 
	{
		try {
			Individual_SimpleIndividual individual = (Individual_SimpleIndividual)__o;
			
			// create embedded elements
			for (int k = 0 ; k != ((ParameterSet_Evolution_mulambdaES)individual.getTemplate()).getGenomeSize() ; k++ ) 
			{
				ArrayList OperatorList = new ArrayList();
				VariationOperator_Element_Bit_UniformMutation op = new VariationOperator_Element_Bit_UniformMutation ("mutation1", ((ParameterSet_Evolution_mulambdaES)individual.getTemplate()).getMutationRate());
				OperatorList.add(op);
				//VariationOperator_Element_DoubleZeroOneUniformMutation op2 = new VariationOperator_Element_DoubleZeroOneUniformMutation ("mutation2", 0.1); // add a *second* mutation operator (example purpose)
				//OperatorList.add(op2);
				individual.addElement(new Element_Bit("attribute #"+k, individual, OperatorList));
			}
			for ( int i = 0 ; i != individual.getElementListSize() ; i++ )
			{
				((Element_Bit)individual.getElementAt(i)).performInitialisation();
			}
			//this.recordAttributeListFromArrayList ( _individualList ); // must do. TODO -- should be done outside this method. in super()?
		}
		catch ( java.lang.ClassCastException e )
		{
			Display.error(""+this.getClass().getName()+"::initialise(.) - Class cast exception");
		}
		
	}

}
