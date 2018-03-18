package picoevo.core.evolution;

import picoevo.core.representation.*;

public abstract class EvaluationOperator {

	// ### Data ###
	
	private String _name = "unnamed evaluation operator";
	
	// ### Constructors ###
	
	public EvaluationOperator () {}
	public EvaluationOperator (String __name) { _name=__name; }
	
	// ### Methods ###
	
	public String getName ()
	{
		return (_name);
	}

	abstract public void evaluate ( Object __object );
}
