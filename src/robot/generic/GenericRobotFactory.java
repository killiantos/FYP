package robot.generic;
import javax.vecmath.Vector3d;

import config.Robot;
import core.MazeRobotFactory;
import simbad.sim.Agent;
import simbad.sim.RobotFactory;

public class GenericRobotFactory implements MazeRobotFactory {

	public Agent create(Robot robot) {
		GenericRobot myRobot = new GenericRobot(new Vector3d(robot.location.x, 0, robot.location.y),robot.name);
		myRobot.installSensor("sonar", RobotFactory.addSonarBeltSensor(myRobot, 8));
		myRobot.installSensor("bumper", RobotFactory.addBumperBeltSensor(myRobot, 8));
		myRobot.installBehaviour("FindWall", new FindWall());
		myRobot.installBehaviour("FollowWall", new FollowWall());
		myRobot.setBehaviour("FindWall");
		return myRobot;
	}
}
