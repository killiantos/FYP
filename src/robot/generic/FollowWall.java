package robot.generic;

import simbad.sim.RangeSensorBelt;

public class FollowWall implements RobotBehaviour {

	@Override
	public void execute(GenericRobot agent) {
		// reads the three front quadrants
		RangeSensorBelt sonars = (RangeSensorBelt) agent.getSensor("sonar");
		if (sonars == null)
			throw new RuntimeException("No sonar installed");
		
		if(sonars.oneHasHit()){
			double left = sonars.getFrontLeftQuadrantMeasurement();
			double right = sonars.getFrontRightQuadrantMeasurement();
			double front = sonars.getFrontQuadrantMeasurement();
			// if obstacle near
			// if(sonars.hasHit(MyEnv.b1))
			if ((front < 0.3) || (left < 0.3) || (right < 0.3)) {
				agent.setRotationalVelocity(-1); // always turns left
				agent.setTranslationalVelocity(0.6);
				System.out.println("left");
				/*
				 * if (left < right) setRotationalVelocity(-1); else setRotationalVelocity(-1);
				 * setTranslationalVelocity(0);
				 */
			} else if ((front > 0.4) || (left > 0.4) || (right > 0.4)) {
				agent.setRotationalVelocity(1);
				agent.setTranslationalVelocity(0.6);
				System.out.println("right");
			}
		}
	}
}
