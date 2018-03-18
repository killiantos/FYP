package robot.generic;

import simbad.sim.RangeSensorBelt;

public class FindWall implements RobotBehaviour {

	@Override
	public void execute(GenericRobot agent) {
		
			RangeSensorBelt sonars = (RangeSensorBelt) agent.getSensor("sonar");
			if(sonars == null) throw new RuntimeException("No sonar installed");
			if (sonars.oneHasHit()) {
				System.out.println("Found");
				agent.setBehaviour("FollowWall");
			}

			else {
				// progress at 0.5 m/s
				agent.setTranslationalVelocity(0.5);
				// frequently change orientation
				/*
				 * if ((getCounter() % 100)==0) setRotationalVelocity(Math.PI/2 * (0.5 -
				 * Math.random()));
				 */

			}

		
	}
}
