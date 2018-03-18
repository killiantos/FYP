package picoevo.core.evolution;

import picoevo.core.representation.*;
import picoevo.toolbox.*;

public abstract class EvaluationOperator_Individual extends EvaluationOperator {

	// ### Data ###
	
	private String _name = "unnamed individual evaluation operator";
	
	// ### Constructors ###
	
	public EvaluationOperator_Individual () { super(); }
	public EvaluationOperator_Individual (String __name) { super(__name); }
	
	// ### Methods ###

	public void evaluate ( Object __individualObject )
	{
		if ( ! ( __individualObject instanceof Individual ) )
		{
			Display.critical(this.getName()+" : not an individual!");
		}
	}
}
