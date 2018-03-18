package robot.basic;
import  simbad.sim.*;
import javax.vecmath.Vector3d;

//Alfred
public class MyRobot3 extends Agent {
	RangeSensorBelt bumpers, sonars;
	double x = 0;
	double y = 0;
	double startX = 0;
	double startY = 0;
	
	public MyRobot3 (Vector3d position, String name) {     
		super(position,name);
		bumpers = RobotFactory.addBumperBeltSensor(this,8);
//		sonars = RobotFactory.addSonarBeltSensor(this, 8);	
		startX = position.x;
		startY = position.z;
	}
	
	public void initBehavior() {}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}

	public void performBehavior() {

		if (collisionDetected()) {
			// stop the robot
			setTranslationalVelocity(0.0);
			setRotationalVelocity(-1 ); // always turns left
		} 

		//follows left wall
		else if (sonars.oneHasHit()) {
			// reads the three front quadrants
			double left = sonars.getFrontLeftQuadrantMeasurement();
			double right = sonars.getFrontRightQuadrantMeasurement();
			double front = sonars.getFrontQuadrantMeasurement();
			// if obstacle near
			//if(sonars.hasHit(MyEnv.b1))
			if ((front < 0.05) || (left < 0.05) || (right < 0.05)) {
				setRotationalVelocity(-1); // always turns left
				setTranslationalVelocity(0.6);
				/*if (left < right)
					setRotationalVelocity(-1);
				else
					setRotationalVelocity(-1);
				setTranslationalVelocity(0);
				*/
			} else if ((front > 0.1) || (left > 0.1) || (right > 0.1)) {
				setRotationalVelocity(1);
				setTranslationalVelocity(0.6);
			}
		} 

		
		else {
			// progress at 0.5 m/s
			setTranslationalVelocity(0.5);
			// frequently change orientation 
			/*if ((getCounter() % 100)==0) 
				setRotationalVelocity(Math.PI/2 * (0.5 - Math.random())); 
				*/
		} 
		//every 20 frames
		if (getCounter()%20==0){
			// print each bumper state 
			for (int i=0;i< bumpers.getNumSensors();i++) {
				double angle = bumpers.getSensorAngle(i);
				boolean hit = bumpers.hasHit(i);
				if(hit == true) {
					
				}
				
				System.out.println("Bumpers at angle "+ angle 
						+ " has hit something:"+hit); 
			}
		}
		
	}
}