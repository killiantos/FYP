/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 9 sept. 2005
 * 
 */

package picoevo.ext.representation;

import picoevo.core.*;
import picoevo.core.evolution.*;
import picoevo.core.representation.*;
import picoevo.ext.*;
import picoevo.ext.evolution.*;
import picoevo.ext.representation.*;
import picoevo.toolbox.*;

/* this class extends Population (which is asbtract) - in the current setup, nothing must be done to use it */
public class Population_SimplePopulation extends Population {
	
	// ### Data ###
	
	EvolveOperator_Population_Generational _evolveOperator; 
	
	// ### Constructor(s) ###
	
	public Population_SimplePopulation ( String __populationName , World __evolutionSpace )
	{
		super ( __populationName, __evolutionSpace );
		this._evolveOperator = new EvolveOperator_Population_Generational("generational");
	}

	public void evolveOneStep( boolean __display ) 
	{
		this._evolveOperator.evolve(this,__display);
	}

	// ### Other methods ###
	
}
