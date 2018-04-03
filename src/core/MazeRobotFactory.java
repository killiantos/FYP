package core;
import config.Instance;
import config.Robot;
import config.RobotType;
import simbad.sim.Agent;

public interface MazeRobotFactory {
	public Agent create(Robot robot);
	public Agent create(RobotType type, Instance instance);
}
