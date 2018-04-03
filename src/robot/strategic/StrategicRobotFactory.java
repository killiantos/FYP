package robot.strategic;
import java.lang.reflect.InvocationTargetException;

import javax.vecmath.Vector3d;

import astra.robot.AbstractRobotFactory;
import config.Instance;
import config.Robot;
import config.RobotType;
import simbad.sim.Agent;

public class StrategicRobotFactory extends AbstractRobotFactory {

	public Agent create(Robot robot) {
		StrategicRobot myRobot = new StrategicRobot(new Vector3d(robot.location.x, 0, robot.location.y), robot.name);
		
		initialize(myRobot, robot);

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

	@Override
	public Agent create(RobotType type, Instance instance) {
		StrategicRobot myRobot = new StrategicRobot(new Vector3d(instance.location.x, 0, instance.location.y), instance.name);
		
		initialize(myRobot, type);

		try {
			myRobot.setStrategy((Strategy) Class.forName(type.config.get(0)).getConstructor().newInstance(), type);
		} catch (InstantiationException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return null;
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			return null;
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			return null;
		} catch (SecurityException e) {
			e.printStackTrace();
			return null;
		}
		return myRobot;
	}
}
