package robot.generic;

import java.util.Map;

public interface RobotBehaviour {
	public void configure(Map<String, Object> config);

	public void execute(GenericRobot agent);
}
