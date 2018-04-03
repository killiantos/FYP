package astra.behaviour;

import robot.generic.GenericRobot;
import robot.generic.RobotEvent;
import simbad.sim.RangeSensorBelt;

public class BackUp extends AbstractBehaviour {
	private int steps = 5;
	
	public BackUp() {
		config.put("speed", 0.4);
	}
	
	@Override
	public void execute(GenericRobot agent) {
		if (steps == 5) {
			agent.setTranslationalVelocity((double) config.get("speed"));
			agent.setRotationalVelocity(0);
			steps--;
		} else if (steps == 0) {
			agent.setTranslationalVelocity(0);
			agent.addEvent(new RobotEvent("Stopped", null));
			agent.setBehaviour(null);
			steps=5;
		} else {
			steps--;
		}
	}
}
