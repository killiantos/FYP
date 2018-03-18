package core;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import simbad.sim.EnvironmentDescription;

public class MyEnv extends EnvironmentDescription {

	public MyEnv(String config) throws InstantiationException, IllegalAccessException, ClassNotFoundException, FileNotFoundException, IOException{
		Properties props = new Properties();
		props.load(new FileInputStream(config));
		
		light1IsOn = true;
		light2IsOn = false;

		((MazeGenerator) Class.forName(props.getProperty("generator")).newInstance()).generate(this);

		for (int r=0; r<Integer.parseInt(props.getProperty("robots")); r++) {
			String[] conf = props.getProperty("robot"+r).split("\\|");
			add(((MazeRobotFactory) Class.forName(conf[0]).newInstance()).create(conf));
		}

//		int i = 0,j = 0;
		

/*
        //creating border walls
        Wall w1 = new Wall(new Vector3d(9, 0, 0), 19, 1, this);
        w1.rotate90(1);
        add(w1);
        visited[i] = true;
        Wall w2 = new Wall(new Vector3d(-9, 0, 0), 19, 2, this);
        w2.rotate90(1);
        add(w2);

        Wall w3 = new Wall(new Vector3d(0, 0, 9), 19, 1, this);
        add(w3);
        Wall w4 = new Wall(new Vector3d(0, 0, -9), 19, 2, this);
        add(w4);
 */


//w5.visited = true;
/*
      //Vector3d is the position, 3f is the size
        //Number order is y,z,x
        Wall w5 = new Wall(new Vector3d(2, 0, 2), 19, 2, this);
        add(w5);
        Wall w6 = new Wall(new Vector3d(-6, 0, -4), 6, 1, this);
        add(w6);
        Wall w7 = new Wall(new Vector3d(6, 0, -4), 6, 1, this);
        add(w7);
        Wall w8 = new Wall(new Vector3d(0, 0, -5), 9, 1, this);
        w8.rotate90(1);
        add(w8);
 */
//Box b1 = new Box(new Vector3d(-6, 0, -7), new Vector3f(1, 1, 1),this);
//add(b1);
//Box b2 = new Box(new Vector3d(-6, 0, -4), new Vector3f(6, 1, 1),this);
//add(b2);
//Box b3 = new Box(new Vector3d(2, 2, 2), new Vector3f(16, 0, 1),this);
//add(b3);
//Box b4 = new Box(new Vector3d(6, 0, -4), new Vector3f(6, 1, 1),this);
//add(b4);

}
/*
    public Maze(int n) {
		this.n = n;
        StdDraw.setXscale(0, n+2);
        StdDraw.setYscale(0, n+2);
        init();
        generate(n, n);
    }

	private void generate(int x, int y) {
        visited[x][y] = true;

        // while there is an unvisited neighbor
        while (!visited[x][y+1] || !visited[x+1][y] || !visited[x][y-1] || !visited[x-1][y]) {

            // pick random neighbor (could use Knuth's trick instead)
            while (true) {
                double r = StdRandom.uniform(4);
                if (r == 0 && !visited[x][y+1]) {
                    north[x][y] = false;
                    south[x][y+1] = false;
                    generate(x, y + 1);
                    break;
                }
                else if (r == 1 && !visited[x+1][y]) {
                    east[x][y] = false;
                    west[x+1][y] = false;
                    generate(x+1, y);
                    break;
                }
                else if (r == 2 && !visited[x][y-1]) {
                    south[x][y] = false;
                    north[x][y-1] = false;
                    generate(x, y-1);
                    break;
                }
                else if (r == 3 && !visited[x-1][y]) {
                    west[x][y] = false;
                    east[x-1][y] = false;
                    generate(x-1, y);
                    break;
                }
            }
        }
    }
 */
}