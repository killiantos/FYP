package core;
import config.Robot;
import simbad.sim.Agent;

public interface MazeRobotFactory {
	public Agent create(Robot robot);
}
