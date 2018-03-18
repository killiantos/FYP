package robot.strategic;

import javax.vecmath.Vector3d;

import robot.generic.GenericRobot;

public class StrategicRobot extends GenericRobot {

	public StrategicRobot(Vector3d arg0, String arg1) {
		super(arg0, arg1);
	}
	
	private Strategy strategy = new BasicStrategy();
	
	public void performBehavior() {
		super.performBehavior();
		
		if(!events.isEmpty()) {
			strategy.Strategize(this, events.poll());
		}
	}

	public void setStrategy(BasicStrategy strategy) {
		this.strategy = strategy;
		strategy.initialize(this);
	}

}
