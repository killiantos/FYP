package picoevo.core.evolution;

import picoevo.core.representation.*;

public abstract class InitialisationOperator_Element extends InitialisationOperator {

	// ### Constructors ###
	
	public InitialisationOperator_Element () {super("unnamed element initialisation operator");}
	public InitialisationOperator_Element (String __name) { super(__name); }
	
	// ### Methods ###

	abstract public void initialise ( Element __elementObject );

}
