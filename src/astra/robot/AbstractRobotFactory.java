package astra.robot;
import config.Robot;
import core.MazeRobotFactory;
import core.MyEnv;
import robot.generic.GenericRobot;
import robot.generic.RobotBehaviour;
import simbad.sim.Agent;
import simbad.sim.RobotFactory;

public abstract class AbstractRobotFactory implements MazeRobotFactory {

	protected void initialize(GenericRobot myRobot, Robot robot) {
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
	}
}