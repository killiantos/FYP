package picoevo.app.SymbolicRegression;

import picoevo.core.*;
import picoevo.core.evolution.*;
import picoevo.core.representation.*;
import picoevo.ext.*;
import picoevo.ext.representation.*;
import picoevo.toolbox.*;

public class EvaluationOperator_Individual_SymReg extends EvaluationOperator_Individual {

	// ### Constructors ###
	
	public EvaluationOperator_Individual_SymReg () 
	{
		super();
	}
	
	public EvaluationOperator_Individual_SymReg (String __name) 
	{ 
		super(__name); 
	}
	
	// ### Methods ###

	public void evaluate(Object __o) 
	{
		super.evaluate(__o); 
		
		try {
			Individual_TreeGP individual = (Individual_TreeGP)__o;

			double nbtry = 20;
			double error = 0;
			
			for ( double i = 0 ; i < nbtry ; i++ )
			{
				/* evaluation method #1 : always different points -- note: much MUCH faster to converge than deterministic */
				double testValue = Math.random()*2-1;
				/* evaluation method #2 : deterministic -- see note above. */
				//double testValue = -1.0 + i/nbtry * 2.0;
				
				//Display.info_nocr(".");
				((MemoryModule_Individual_SymbolicRegression_SingleRegister)((Individual_TreeGP)individual).getMemoryModule()).setX(testValue);
				double y = ((Element_Node_TreeGP)individual.getElementAt(0)).evaluateDouble();

				error += Math.abs( y - computeTargetFunction(testValue) );
			}
			//Display.info_nocr(":\n");
				
			individual.setFitness( error );
		}
		catch ( java.lang.ClassCastException e )
		{
			Display.error(""+this.getClass().getName()+"::evaluate(.) - Class cast exception");
		}	
	}
	

	/**
	 * Evaluate the number of "hits", i.e. for 20 random values, is error(f(x))<0.01 ? (Koza's definition of "hit")
	 * @param __o
	 */
	public void evaluateHits(Individual __o) 
	{	
		try {
			Individual_TreeGP individual = (Individual_TreeGP)__o;

			double nbtry = 20;
			double error = 0;
			int hits = 0;
			
			//Display.info_nocr(":");
			for ( double i = 0 ; i < nbtry ; i++ )
			{
				double testValue = -1.0 + i/nbtry * 2.0;
				((MemoryModule_Individual_SymbolicRegression_SingleRegister)((Individual_TreeGP)individual).getMemoryModule()).setX(testValue);
				double y = ((Element_Node_TreeGP)individual.getElementAt(0)).evaluateDouble();
				// fitness : koza style, GP vol.1 p.164
				double localError = Math.abs( y - computeTargetFunction(testValue) );
				if ( localError < 0.01 )
				{
					Display.info_nocr("[hit]");
					hits++;
				}
				else
				{
					Display.info_nocr("[missed]");
				}
				error += localError;
			}
			Display.info_nocr("\n("+hits+"/"+(int)nbtry+")\n");
		}
		catch ( java.lang.ClassCastException e )
		{
			Display.error(""+this.getClass().getName()+"::evaluate(.) - Class cast exception");
		}	
	}

	double computeTargetFunction( double testValue )
	{
		// fitness : koza style, GP vol.1 p.164
		//return (Math.toRadians((testValue+1)*90));
		//return (Math.tan(testValue)); // tanh = / sin x cos x
		//return (Math.sin(testValue)); // SINUS 
		return (testValue+testValue*testValue); // x+x^2
		//return (testValue+testValue*testValue+testValue*testValue*testValue); // x+x^2+x^3		
		//->20060403==>return (testValue+testValue*testValue+testValue*testValue*testValue+testValue*testValue*testValue*testValue);  // x+x^2+x^3+x^4 20060321->20060403 
	//return (0.12321321*testValue+0.5677*testValue*testValue+0.782*testValue*testValue*testValue+0.93*testValue*testValue*testValue*testValue);  // x+x^2+x^3+x^4 20060403->...
		//return (testValue+testValue*testValue+testValue*testValue*testValue+testValue*testValue*testValue*testValue+testValue*testValue*testValue*testValue*testValue);  // x+x^2+x^3+x^4+x^5
		//return (testValue+testValue*testValue+testValue*testValue*testValue+testValue*testValue*testValue*testValue+testValue*testValue*testValue*testValue*testValue+testValue*testValue*testValue*testValue*testValue*testValue);  // x+x^2+x^3+x^4+x^5+x^6
	}
}
