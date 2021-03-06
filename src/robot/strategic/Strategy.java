package robot.strategic;

import config.RobotType;
import robot.generic.RobotEvent;

public interface Strategy {
	
	public void strategize(StrategicRobot agent, RobotEvent event);
	public void initialize(StrategicRobot agent, RobotType robot);
}
