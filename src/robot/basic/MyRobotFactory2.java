package robot.basic;
import javax.vecmath.Vector3d;

import config.Instance;
import config.Robot;
import config.RobotType;
import core.MazeRobotFactory;
import simbad.sim.Agent;

public class MyRobotFactory2 implements MazeRobotFactory {

	@Override
	public Agent create(Robot robot) {
		return new MyRobot2(new Vector3d(robot.location.x, 0, robot.location.y), robot.name);
	}

	@Override
	public Agent create(RobotType type, Instance instance) {
		return new MyRobot2(new Vector3d(instance.location.x, 0, instance.location.y), instance.name);
	}
}
