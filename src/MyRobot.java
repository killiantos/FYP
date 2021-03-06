import  simbad.sim.*;
import javax.vecmath.Vector3d;

public class MyRobot extends Agent {
	RangeSensorBelt bumpers;
    public MyRobot (Vector3d position, String name) {     
        super(position,name);
        bumpers = RobotFactory.addBumperBeltSensor(this,8);
    }
    public void initBehavior() {}
    
    public void performBehavior() {
    	
        if (collisionDetected()) {
            // stop the robot
            setTranslationalVelocity(0.0);
            setRotationalVelocity(0);
        } else {
            // progress at 0.5 m/s
            setTranslationalVelocity(0.5);
            // frequently change orientation 
            if ((getCounter() % 100)==0) 
               setRotationalVelocity(Math.PI/2 * (0.5 - Math.random()));
        }
      //every 20 frames
        if (getCounter()%20==0){
            // print each bumper state 
            for (int i=0;i< bumpers.getNumSensors();i++) {
                double angle = bumpers.getSensorAngle(i);
                boolean hit = bumpers.hasHit(i);
                System.out.println("Bumpers at angle "+ angle 
                + " has hit something:"+hit); 
            }
        }
    }
}