package robot.strategic;
import javax.vecmath.Vector3d;

import config.Robot;
import core.MazeRobotFactory;
import simbad.sim.Agent;
import simbad.sim.RobotFactory;

public class StrategicRobotFactory implements MazeRobotFactory {

	public Agent create(Robot robot) {
		StrategicRobot myRobot = new StrategicRobot(new Vector3d(robot.location.x, 0, robot.location.y), robot.name);
		myRobot.installSensor("sonar", RobotFactory.addSonarBeltSensor(myRobot, 8));
		myRobot.installSensor("bumper", RobotFactory.addBumperBeltSensor(myRobot, 8));
		//myRobot.installSensor("compass", RobotFactory.addLightSensor(myRobot));
		myRobot.installBehaviour("FindWall", new FindWall());
		myRobot.installBehaviour("FollowWall", new FollowWall());
		//robot.installBehaviour("HitWall", new HitWall());
		try {
			myRobot.setStrategy((Strategy) Class.forName(robot.config.get(0)).newInstance(), robot);
		} catch (InstantiationException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return myRobot;
	}
}
