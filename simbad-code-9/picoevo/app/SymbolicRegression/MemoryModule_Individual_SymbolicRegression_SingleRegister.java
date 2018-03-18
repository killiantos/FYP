package picoevo.app.SymbolicRegression;

import picoevo.core.representation.MemoryModule;

public class MemoryModule_Individual_SymbolicRegression_SingleRegister extends
		MemoryModule {

	// ### Data ###
	
	private double _x;
	
	// ### Methods ###
	
	public void setX( double __x )
	{
		_x = __x;
	}
	
	public double getX()
	{
		return(_x);
	}

}
