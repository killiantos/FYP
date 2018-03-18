/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 27 nov. 2005
 * 
 */

package picoevo.core.representation;

import java.util.ArrayList;

import picoevo.toolbox.*;

/**
 * 
 * This class is used to manipulate set of individuals. This is a powerhunger since multiple copy operations of individual lists are performed
 * to ensure registering single-referenced individuals. In practical, this is useful for educational purpose, but *not* for heavy evolution. In
 * this latter case, one should better use the no-argument constructor and registerClonedIndividual method to manually register individual. Of 
 * course, beware of individuals with multiple registrations.
 *  
 * @author bredeche
 *
 */

public class PopulationContainer extends PopulationObject implements PopulationInterface { 
	
	// ### constructors ###
	
	/** warning : this method *clone* target individuals */	
	public PopulationContainer ( ArrayList __individualList )
	{
		super("container");
		this.registerListOfIndividuals(__individualList);
	}

	/** warning : this method *clone* target individuals */	
	public PopulationContainer ( PopulationView __populationView )
	{
		this(__populationView.getIndividualList());
	}
	
	public PopulationContainer ( String __name, ArrayList __individualList)
	{
		this(__individualList);
		this._name = __name;
	}
	
	public PopulationContainer ( String __name, PopulationView __populationView )
	{
		this(__populationView);
		this._name = __name;
	}

	public PopulationContainer ( String __name )
	{
		super(__name);
	}
	
	public PopulationContainer ( )
	{
		this("container");
	}
	
	// ### Other Methods ###

	/** register **clones** of individuals in the list */
	public void registerListOfIndividuals ( ArrayList __individualList )
	{
		for ( int i = 0 ; i != __individualList.size() ; i++ )
			this.cloneAndRegisterIndividual((Individual)(((Individual)__individualList.get(i)))); 
	}

	/** register **clones** of individuals in the PopulationView's list */
	public void registerListOfIndividuals ( PopulationView __populationView )
	{
		this.registerListOfIndividuals(__populationView.getIndividualList());
	}

	/**
	 * Add all individual in target container to the calling container's list of individuals (concatenate).
	 * WARNING : target container is reseted! (i.e. individual list is set to null to ensure single referenced individuals)
	 * @param __targetPopulationContainer
	 */
	public void mergeWithContainer( PopulationContainer __targetPopulationContainer ) 
	{
		if ( this == __targetPopulationContainer )
		{
			Display.error(""+this.getClass().getName()+"::mergeWithContainer - cannot self-merge");
			return;
		}
		for ( int i = 0 ; i != __targetPopulationContainer.getPopulationSize() ; i++ )
			this.registerIndividual((Individual)__targetPopulationContainer.getIndividualList().get(i));
		__targetPopulationContainer.reset();
	}
	
	/** warning : this method *do not clone* the target individual but just *insert* it in a list (assume cloning has been done elsewhere). 
	 * It is important to note that only individuals that are **not** registered elsewhere should be added to a PopulationContainer. 
	 * As a consequence, this method should be used rarely and/or wisely. 
	 * In all other case, method registerCloneOfIndividual(.) can be used.
	 * 
	 * use with extreme care.
	 *  
	 * TODO auto-check if stand-alone clone
	 */	
	public void registerIndividual(Individual __individual) 
	{
		this._individualList.add(__individual);
	}

	/** This method clone the given individual and add the clone to the container list.
	 * Warning, Albeit method name is similar to that of Population, implementation differ (**clone** of individual is inserted in the list) */
	public void cloneAndRegisterIndividual(Individual __individual)
	{
		this._individualList.add(__individual.clone());
	}

	/*
	** This method clone the given individual and add the clone to the container list *
	public void registerCloneOfIndividual(Individual __individual)
	{
		this._individualList.add(__individual.clone());
	}
	*/

}
