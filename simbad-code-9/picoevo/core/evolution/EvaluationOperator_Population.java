package picoevo.core.evolution;

import picoevo.core.representation.*;
import picoevo.toolbox.*;

public abstract class EvaluationOperator_Population  extends EvaluationOperator {

	// ### Data ###
	
	private String _name = "unnamed population evaluation operator";
	
	// ### Constructors ###
	
	public EvaluationOperator_Population () { super(); }
	public EvaluationOperator_Population (String __name) { super(__name); }
	
	// ### Methods ###
	
	public void evaluate ( Object __populationObject )
	{
		if ( ! ( __populationObject instanceof Population ) )
		{
			Display.critical(this.getName()+" : not a population!");
		}
	}
}
