package robot.strategic;

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
			agent.setRotationalVelocity(0);
			agent.setTranslationalVelocity(0);
			agent.addEvent(new RobotEvent("LostWall",null));
		} else {
			double left = sonars.getFrontLeftQuadrantMeasurement();
			double right = sonars.getFrontRightQuadrantMeasurement();
			double front = sonars.getFrontQuadrantMeasurement();
			// if obstacle near
			// if(sonars.hasHit(MyEnv.b1))
			if ((front < 0.05) || (left < 0.05) || (right < 0.05)) {
				agent.setRotationalVelocity(-1); // always turns left
				agent.setTranslationalVelocity(0.6);
				/*
				 * if (left < right) setRotationalVelocity(-1); else setRotationalVelocity(-1);
				 * setTranslationalVelocity(0);
				 */
			} else if ((front > 0.1) || (left > 0.1) || (right > 0.1)) {
				agent.setRotationalVelocity(1);
				agent.setTranslationalVelocity(0.6);
			}
		}
	}
}
