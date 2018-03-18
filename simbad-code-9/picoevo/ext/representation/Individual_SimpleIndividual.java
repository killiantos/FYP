/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 7 janv. 2006
 * 
 */

package picoevo.ext.representation;

import java.util.ArrayList;

import picoevo.core.*;
import picoevo.core.evolution.*;
import picoevo.core.representation.*;
import picoevo.ext.*;
import picoevo.ext.evolution.*;
import picoevo.ext.representation.*;
import picoevo.toolbox.*;


public class Individual_SimpleIndividual extends Individual {

	// ### Constructors ###
	
	public Individual_SimpleIndividual ( String __name , Population __populationOwner )
	{
		super (__name,__populationOwner);
	}
	
	public Individual_SimpleIndividual ( Population __populationOwner )
	{
		super (__populationOwner);
	}

	// ### Inherited methods ###

	public void addElement(Element __element) 
	{
		_elementList.add(__element);
	}

	public void displayInformation()
	{
		Display.info("## Individual Information : summary ##");
		Display.info("Individual name : " + _name);
		Display.info("fitness : ");
	    //if ( isEvaluated() )
	    		Display.info(""+_fitness);
	    //else
    		//	Display.info("n/a");
	    
	    Display.info_nocr("Individual-level Operator(s) : ");
	    for ( int i = 0 ; i != _variationOperatorList.size() ; i++ )
			((VariationOperator)_variationOperatorList.get(i)).displayInformation();
	    Display.info("");

	    //System.out.println("# Elements Information : summary #");
	    for ( int i = 0 ; i != _elementList.size() ; i++ )
	    		((Element)_elementList.get(i)).displayInformation();
	    Display.info("\n");
	}

	// ### Other Methods ###
	
	
}
