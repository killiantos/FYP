import simbad.sim.*;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
public class MyEnv extends EnvironmentDescription {
    public MyEnv(){
    	light1IsOn = true;
        light2IsOn = false;
        //add(new Arch(new Vector3d(3,0,-3),this));
        add(new MyRobot(new Vector3d(5, 0, -7),"my robot"));
        Wall w1 = new Wall(new Vector3d(9, 0, 0), 19, 1, this);
        w1.rotate90(1);
        add(w1);
        Wall w2 = new Wall(new Vector3d(-9, 0, 0), 19, 2, this);
        w2.rotate90(1);
        add(w2);
        Wall w3 = new Wall(new Vector3d(0, 0, 9), 19, 1, this);
        add(w3);
        Wall w4 = new Wall(new Vector3d(0, 0, -9), 19, 2, this);
        add(w4);
      //Vector3d is the position, 3f is the size
        //Number order is y,z,x
        Wall w5 = new Wall(new Vector3d(2, 0, 2), 19, 1, this);
        add(w5);
        Wall w6 = new Wall(new Vector3d(-6, 0, -4), 6, 1, this);
        add(w6);
        Wall w7 = new Wall(new Vector3d(6, 0, -4), 6, 1, this);
        add(w7);
        Wall w8 = new Wall(new Vector3d(0, 0, -5), 9, 1, this);
        w8.rotate90(1);
        add(w8);
        //Box b1 = new Box(new Vector3d(-6, 0, -7), new Vector3f(1, 1, 1),this);
        //add(b1);
        //Box b2 = new Box(new Vector3d(-6, 0, -4), new Vector3f(6, 1, 1),this);
        //add(b2);
        //Box b3 = new Box(new Vector3d(2, 2, 2), new Vector3f(16, 0, 1),this);
        //add(b3);
        //Box b4 = new Box(new Vector3d(6, 0, -4), new Vector3f(6, 1, 1),this);
        //add(b4);
        
    }
}