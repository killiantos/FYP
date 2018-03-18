package core;
import javax.vecmath.Vector3d;

import simbad.sim.Agent;

public interface MazeRobotFactory {
	public Agent create(String[] configuration);
}
