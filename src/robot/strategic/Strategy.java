package robot.strategic;

import robot.generic.RobotEvent;

public interface Strategy {
	
	public void Strategize(StrategicRobot agent, RobotEvent event);
	public void initialize(StrategicRobot agent);
}
