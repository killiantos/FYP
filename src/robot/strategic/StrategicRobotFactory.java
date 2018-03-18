package robot.strategic;
import javax.vecmath.Vector3d;

import config.Robot;
import core.MazeRobotFactory;
import core.MyEnv;
import robot.generic.RobotBehaviour;
import simbad.sim.Agent;
import simbad.sim.RobotFactory;

public class StrategicRobotFactory implements MazeRobotFactory {

	public Agent create(Robot robot) {
		StrategicRobot myRobot = new StrategicRobot(new Vector3d(robot.location.x, 0, robot.location.y), robot.name);
		for (String sensor : robot.sensors) {
			if (sensor.equals("sonar")) {
				myRobot.installSensor("sonar", RobotFactory.addSonarBeltSensor(myRobot, 8));
			} else if (sensor.equals("bumper")) {
				myRobot.installSensor("bumper", RobotFactory.addBumperBeltSensor(myRobot, 8));
			} else if (sensor.equals("compass")) {
				myRobot.installSensor("compass", RobotFactory.addLightSensor(myRobot));
			}
		}
		
		for (String behaviour : robot.behaviours) {
			try {
				myRobot.installBehaviour(behaviour, (RobotBehaviour) Class.forName(MyEnv.behaviours.get(behaviour)).newInstance());
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

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
