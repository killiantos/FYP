package astra.behaviour;

import robot.generic.GenericRobot;
import robot.generic.RobotBehaviour;
import robot.generic.RobotEvent;
import simbad.sim.RangeSensorBelt;

public class FollowWall implements RobotBehaviour {

	@Override
	public void execute(GenericRobot agent) {
		// reads the three front quadrants
		RangeSensorBelt sonars = (RangeSensorBelt) agent.getSensor("sonar");
		if (sonars == null)
			throw new RuntimeException("No sonar installed");
		
		if (!sonars.oneHasHit()) {
//			agent.setRotationalVelocity(0);
//			agent.setTranslationalVelocity(0);
			System.out.println("Lost Wall...");
//			agent.addEvent(new RobotEvent("LostWall",null));
		} else {
			double left = sonars.getFrontLeftQuadrantMeasurement();
			double right = sonars.getFrontRightQuadrantMeasurement();
			double front = sonars.getFrontQuadrantMeasurement();

			// if obstacle near
			// if(sonars.hasHit(MyEnv.b1))
			if ((front < 1.0) || (left < 1.0) || (right < 1.0)) {
				agent.setRotationalVelocity(-1); // always turns left
				agent.setTranslationalVelocity(0.2);
			} else if ((front > 1.2) || (left > 1.2) || (right > 1.2)) {
				agent.setRotationalVelocity(1);
				agent.setTranslationalVelocity(0.2);
			} else {
				agent.setRotationalVelocity(0);
			}
		}
	}
}
