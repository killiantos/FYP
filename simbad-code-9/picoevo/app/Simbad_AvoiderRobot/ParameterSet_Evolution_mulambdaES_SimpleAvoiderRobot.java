/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 1 dec. 2005
 * 
 */

package picoevo.app.Simbad_AvoiderRobot;

import picoevo.core.*;
import picoevo.core.evolution.*;
import picoevo.core.representation.*;
import picoevo.ext.*;
import picoevo.ext.evolution.*;
import picoevo.ext.representation.*;
import picoevo.toolbox.*;
import simbad.gui.Simbatch;

public class ParameterSet_Evolution_mulambdaES_SimpleAvoiderRobot extends ParameterSet_Evolution_mulambdaES {
	
	Simbatch _simulator;
	Evaluator_SimpleAvoiderRobot _robot;
	
	public ParameterSet_Evolution_mulambdaES_SimpleAvoiderRobot ( )
	{
		super ();
	}

	public ParameterSet_Evolution_mulambdaES_SimpleAvoiderRobot ( String __filename )
	{
		super (__filename);
	}

	public Evaluator_SimpleAvoiderRobot getRobot() {
		return _robot;
	}

	public void setRobot(Evaluator_SimpleAvoiderRobot _robot) {
		this._robot = _robot;
	}

	public Simbatch getSimulator() {
		return _simulator;
	}

	public void setSimulator(Simbatch _simulator) {
		this._simulator = _simulator;
	}
}
