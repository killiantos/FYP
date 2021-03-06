package picoevo.ext.evolution;

import java.util.ArrayList;

import picoevo.core.*;
import picoevo.core.evolution.*;
import picoevo.core.representation.*;
import picoevo.ext.*;
import picoevo.ext.representation.*;
import picoevo.toolbox.*;


public class InitialisationOperator_Individual_StaticArray_Double extends InitialisationOperator_Individual {

	// ### Constructors ###
	
	public InitialisationOperator_Individual_StaticArray_Double () 
	{
		super();
	}
	
	public InitialisationOperator_Individual_StaticArray_Double (String __name) 
	{ 
		super(__name); 
	}
	
	// ### Methods ###

	public void initialise( Individual __o ) 
	{
		try {
			Individual_SimpleIndividual individual = (Individual_SimpleIndividual)__o;
			
			ArrayList OperatorList = new ArrayList();
			VariationOperator_Element_DoubleArray_UniformMutation op = new VariationOperator_Element_DoubleArray_UniformMutation("mutation1", ((ParameterSet_Evolution_mulambdaES)individual.getTemplate()).getMutationRate());
			OperatorList.add(op);
			individual.addElement(new Element_StaticArray_Double("double array", individual, OperatorList, ((ParameterSet_Evolution_mulambdaES)individual.getTemplate()).getGenomeSize()));
			((Element_StaticArray_Double)individual.getElementAt(0)).performInitialisation();
		}
		catch ( java.lang.ClassCastException e )
		{
			Display.error(""+this.getClass().getName()+"::initialise(.) - Class cast exception");
		}

	}

}
