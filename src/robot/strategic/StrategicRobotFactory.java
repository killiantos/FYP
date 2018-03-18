package robot.strategic;
import javax.vecmath.Vector3d;

import core.MazeRobotFactory;
import simbad.sim.Agent;
import simbad.sim.RobotFactory;

public class StrategicRobotFactory implements MazeRobotFactory {

	public Agent create(String[] configuration) {
		String[] loc = configuration[2].split(",");
		StrategicRobot robot = new StrategicRobot(new Vector3d(Integer.parseInt(loc[0]), 0, Integer.parseInt(loc[1])),configuration[1]);
		robot.installSensor("sonar", RobotFactory.addSonarBeltSensor(robot, 8));
		robot.installSensor("bumper", RobotFactory.addBumperBeltSensor(robot, 8));
		//robot.installSensor("compass", RobotFactory.addLightSensor(robot));
		robot.installBehaviour("FindWall", new FindWall());
		robot.installBehaviour("FollowWall", new FollowWall());
		//robot.installBehaviour("HitWall", new HitWall());
		robot.setStrategy(new BasicStrategy());
		return robot;
	}
}
