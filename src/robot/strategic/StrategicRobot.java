package robot.strategic;

import javax.vecmath.Vector3d;

import config.Robot;
import robot.generic.GenericRobot;

public class StrategicRobot extends GenericRobot {

	private Strategy strategy;

	public StrategicRobot(Vector3d arg0, String arg1) {
		super(arg0, arg1);
	}
	
	public void performBehavior() {
		super.performBehavior();
		
		if(!events.isEmpty()) {
			strategy.strategize(this, events.poll());
		}
	}

	public void setStrategy(Strategy strategy, Robot robot) {
		this.strategy = strategy;
		strategy.initialize(this, robot);
	}

}
