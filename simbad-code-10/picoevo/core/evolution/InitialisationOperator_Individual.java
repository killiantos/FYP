package picoevo.core.evolution;

import picoevo.core.representation.*;

public abstract class InitialisationOperator_Individual extends InitialisationOperator {

	// ### Constructors ###
	
	public InitialisationOperator_Individual () {super("unnamed individual initialisation operator");}
	public InitialisationOperator_Individual (String __name) { super(__name); }
	
	// ### Methods ###

	abstract public void initialise ( Individual __individualObject );

}
