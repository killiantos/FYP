package picoevo.app.SymbolicRegression;

import java.util.ArrayList;

import picoevo.core.*;
import picoevo.core.evolution.*;
import picoevo.core.representation.*;
import picoevo.ext.*;
import picoevo.ext.evolution.*;
import picoevo.ext.representation.*;
import picoevo.toolbox.*;


public class InitialisationOperator_Individual_TreeGP extends InitialisationOperator_Individual {

	// ### Constructors ###
	
	public InitialisationOperator_Individual_TreeGP () 
	{
		super();
	}
	
	public InitialisationOperator_Individual_TreeGP (String __name)
	{ 
		super(__name); 
	}
	
	// ### Methods ###

	public void initialise( Individual __o ) 
	{
		//try {
			Individual_TreeGP individual = (Individual_TreeGP)__o;
			
			// add a memory module
			individual.setMemoryModule(new MemoryModule_Individual_SymbolicRegression_SingleRegister());
			
			// no operators for individual
			individual.addOperator(new VariationOperator_Individual_TreeGP_Mutation("mutation",((ParameterSet_Evolution_mulambdaES)individual.getTemplate()).getMutationRate()));
			
			// Add the first element with its operator(s)
			ArrayList operatorList = new ArrayList();
			Element_Node_TreeGP_Root newElement = new Element_Node_TreeGP_Root( individual, operatorList );
			//newElement.addOperator(new VariationOperator_Element_TreeGP_ReInitialize("Reinitialiser op."));
			individual.addElement( newElement );
			
			// initialise the first element (cascade init from this element)
			if ( ((ParameterSet_Evolution_mulambdaES_TreeGP)individual.getTemplate()).getMaxInitDepth() <= 1 )
				((Element_Node_TreeGP_Root)(individual.getElementAt(0))).performInitialisation();
			else
			{
				int depth = ((ParameterSet_Evolution_mulambdaES_TreeGP)individual.getTemplate()).getMaxInitDepth();
				depth = 2 + (int)( (double)(depth-1) * Math.random() ); // ramped half&half, step 1 : set max init depth (btw 2 and N) -- cf. koza
				if ( Math.random() < 0.5 ) // ramped half&half, step 2 : full or grow tree generation ? (p=0.5)
					((Element_Node_TreeGP_Root)(individual.getElementAt(0))).performInitialisation(depth, Dictionary.fullMethod, true );
				else 
					((Element_Node_TreeGP_Root)(individual.getElementAt(0))).performInitialisation(depth, Dictionary.growMethod, true );
			}
		//}
		//catch ( java.lang.ClassCastException e )
		//{
		//	Display.error(""+this.getClass().getName()+"::initialise(.) - Class cast exception");
		//}
	}
}
