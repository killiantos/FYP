package robot.basic;
import javax.vecmath.Vector3d;

import core.MazeRobotFactory;
import simbad.sim.Agent;

public class MyRobotFactory implements MazeRobotFactory {

	public Agent create(String[] configuration) {
		String[] loc = configuration[2].split(",");
		return new MyRobot(new Vector3d(Integer.parseInt(loc[0]), 0, Integer.parseInt(loc[1])),configuration[1]);
	}
}
