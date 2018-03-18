package robot.generic;
import javax.vecmath.Vector3d;

import core.MazeRobotFactory;
import simbad.sim.Agent;
import simbad.sim.RobotFactory;

public class GenericRobotFactory implements MazeRobotFactory {

	public Agent create(String[] configuration) {
		String[] loc = configuration[2].split(",");
		GenericRobot robot = new GenericRobot(new Vector3d(Integer.parseInt(loc[0]), 0, Integer.parseInt(loc[1])),configuration[1]);
		robot.installSensor("sonar", RobotFactory.addSonarBeltSensor(robot, 8));
		robot.installSensor("bumper", RobotFactory.addBumperBeltSensor(robot, 8));
		robot.installBehaviour("FindWall", new FindWall());
		robot.installBehaviour("FollowWall", new FollowWall());
		robot.setBehaviour("FindWall");
		return robot;
	}
}
