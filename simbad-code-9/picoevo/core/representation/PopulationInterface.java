/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 27 nov. 2005
 * 
 */

package picoevo.core.representation;

import java.util.ArrayList;

public interface PopulationInterface {
	
	public int getPopulationSize();
	public ArrayList getIndividualList ();
	public void registerIndividual ( Individual __individual );
	public Individual getIndividual ( int __index );
	
	/**
	 * Set any list to null in order to ease garbage collecting.
	 *
	 */
	public void reset();
	
}
