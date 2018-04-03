package robot.strategic;

import java.util.HashMap;
import java.util.Map;

import config.RobotType;
import robot.generic.RobotEvent;

public class BasicStrategy implements Strategy{

	@Override
	public void strategize(StrategicRobot agent, RobotEvent event) {
		if (event.getType().equals("FoundWall")) {
			Map<String, Object> config = new HashMap<String, Object>();
			config.put("speed", 0.8);
			agent.setBehaviour("FollowWall", config);
			System.out.println("Found Wall");
		} else if (event.getType().equals("Collision")) {
			System.out.println("Collision...");
		} else if (event.getType().equals("LostWall")) {
			agent.setBehaviour("FindWall");
			System.out.println("Lost Wall");
		}
		/*
		else if(event.getType().equals("HitWall")) {
			agent.setBehaviour("HitWall");
			System.out.println("HitWall");
		} */
	}

	public void initialize(StrategicRobot agent, RobotType robot) {
		agent.setBehaviour("FindWall");
	}
	
}
