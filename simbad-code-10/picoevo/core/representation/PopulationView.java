/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 25 nov. 2005
 * 
 * create a "view" on a subpart of the whole population. In this scope, it assumed that all recorded invidual are
 * also recorded elsewhere -- As a results, the *list* of individuals may be modified but not the very individuals.
 * The resulting object (i.e. list of individuals) should be used by Selection Operators to store or/and order selected 
 * individuals. 
 * 
 */

package picoevo.core.representation;

import java.util.ArrayList;

/**
 * 
 * @author nicolas
 *
 * This class should be mainly used as an extended wrapper for a list of individual. E.g. You may implement sort methods.
 */

public class PopulationView implements PopulationInterface, Cloneable {
	
	public ArrayList _individualList;
	
	/** stats with an empty list of individuals */
	public PopulationView ()
	{
		_individualList = new ArrayList();
	}
	
	/** starts with a list of individuals that is a clone (i.e. can be altered) of the targetted population */
	public PopulationView ( Population __population )
	{
		this._individualList = (ArrayList)__population.getIndividualList().clone();
		// TODO check if clone works.
	}
	
	/** starts with a list of individuals that is a clone (i.e. can be altered) of the list given as parameter */
	public PopulationView ( ArrayList __individualList )
	{
		this._individualList = (ArrayList)__individualList.clone();
		// TODO check if clone works.
	}
	
	public ArrayList getIndividualList ()
	{
		return (_individualList);
	}

	public int getPopulationSize() 
	{
		return this._individualList.size();
	}

	/**
	 * register individual, i.e. add a pointer to this individual (no cloning)
	 */
	public void registerIndividual(Individual __individual) // same as Population::addIndividual
	{
		this._individualList.add(__individual);
	}
	
	public Individual getIndividual ( int __index )
	{
		return ((Individual)_individualList.get(__index));
	}
	
	public void reset ()
	{
		this._individualList = null;
	}
	
	public Object clone ()
	{
		try 
		{
			return super.clone();
		}
		catch (CloneNotSupportedException e) 
		{
            throw new InternalError(e.toString());
		}
	}
	
	//TODO abstract getBest,getFirst,getLast,get(index)
}
