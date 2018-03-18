package robot.basic;
import javax.vecmath.Vector3d;

import config.Robot;
import core.MazeRobotFactory;
import simbad.sim.Agent;

public class MyRobotFactory2 implements MazeRobotFactory {

	@Override
	public Agent create(Robot robot) {
		return new MyRobot2(new Vector3d(robot.location.x, 0, robot.location.y), robot.name);
	}
}
