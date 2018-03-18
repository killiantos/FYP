package picoevo.app.SymbolicRegression;

import java.util.ArrayList;

import picoevo.core.evolution.VariationOperator;
import picoevo.core.representation.*;
import picoevo.ext.representation.*;
import picoevo.toolbox.Display;

public class Individual_TreeGP extends Individual_SimpleIndividual {

	// ### Data ###

	/** MemoryModule may be useful to store registers (e.g. for GP) */
	private MemoryModule _memoryModule;
	
	// ### Constructors ###
	
	public Individual_TreeGP(String __name, Population __populationOwner) {
		super(__name, __populationOwner);
	}

	public Individual_TreeGP(Population __populationOwner) {
		super(__populationOwner);
	}

	public void setMemoryModule ( MemoryModule __memoryModule )
	{
		_memoryModule = __memoryModule;
	}
	
	public MemoryModule getMemoryModule ()
	{
		return (_memoryModule);
	}

	public void performInitialisation()
	{
		Display.info_nocr(".");
		super.performInitialisation();	
	}
	
	/**
	 * 
	 * @return all non terminal elements embedded in this individual
	 */
	public ArrayList getNonTerminalElements () // first element should be Root
	{
		if ( this._elementList != null && this._elementList.size() != 0 )
			return ((Element_Node_TreeGP)this._elementList.get(0)).getNonTerminalElements();
		else
		{
			Display.error(""+this.getClass().getName()+"::getNonTerminalElements - error with element list");
			return null;
		}
	}
	
	/**
	 * 
	 * @return all terminal elements embedded in this individual
	 */
	public ArrayList getTerminalElements ()
	{
		if ( this._elementList != null && this._elementList.size() != 0 ) // first element should be Root
			return ((Element_Node_TreeGP)this._elementList.get(0)).getTerminalElements();
		else
		{
			Display.error(""+this.getClass().getName()+"::getTerminalElements - error with element list");
			return null;
		}
	}
	
}
