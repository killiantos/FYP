package astra.robot;
import java.lang.reflect.InvocationTargetException;

import config.RobotType;
import core.MazeRobotFactory;
import core.MyEnv;
import robot.generic.GenericRobot;
import robot.generic.RobotBehaviour;
import simbad.sim.RobotFactory;

public abstract class AbstractRobotFactory implements MazeRobotFactory {
	protected void initialize(GenericRobot myRobot, RobotType type) {
		for (String sensor : type.sensors) {
			if (sensor.equals("sonar")) {
				myRobot.installSensor("sonar", RobotFactory.addSonarBeltSensor(myRobot, 8));
			} else if (sensor.equals("bumper")) {
				myRobot.installSensor("bumper", RobotFactory.addBumperBeltSensor(myRobot, 8));
			} else if (sensor.equals("compass")) {
				myRobot.installSensor("compass", RobotFactory.addLightSensor(myRobot));
			}
		}
		
		for (String behaviour : type.behaviours) {
			try {
				myRobot.installBehaviour(behaviour, (RobotBehaviour) Class.forName(MyEnv.behaviours.get(behaviour)).getConstructor().newInstance());
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}
	}
	
}