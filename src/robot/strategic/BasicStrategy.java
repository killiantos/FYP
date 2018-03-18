package robot.strategic;

import config.Robot;
import robot.generic.RobotEvent;

public class BasicStrategy implements Strategy{

	@Override
	public void strategize(StrategicRobot agent, RobotEvent event) {
		if (event.getType().equals("FoundWall")) {
			agent.setBehaviour("FollowWall");
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

	public void initialize(StrategicRobot agent, Robot robot) {
		agent.setBehaviour("FindWall");
	}
	
}
