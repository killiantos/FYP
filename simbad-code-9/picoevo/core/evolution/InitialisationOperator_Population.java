package picoevo.core.evolution;

import picoevo.core.representation.*;

public abstract class InitialisationOperator_Population extends InitialisationOperator {

	// ### Constructors ###
	
	public InitialisationOperator_Population () {super ("unnamed population initialisation operator");}
	public InitialisationOperator_Population (String __name) { super(__name); }
	
	// ### Methods ###
	
	abstract public void initialise ( Population __populationObject );

}
