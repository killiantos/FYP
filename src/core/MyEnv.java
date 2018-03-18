package core;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import config.Behaviour;
import config.Robot;
import config.SystemConfig;
import simbad.sim.EnvironmentDescription;

public class MyEnv extends EnvironmentDescription {
	public static Map<String, String> behaviours = new HashMap<String, String>();
	
	private static Gson gson = new Gson();
	
	public MyEnv(String filename) throws InstantiationException, IllegalAccessException, ClassNotFoundException, JsonSyntaxException, JsonIOException, FileNotFoundException {
		SystemConfig config = gson.fromJson(new FileReader(filename), SystemConfig.class);
	
		// Load System Behaviours
		for (Behaviour behaviour : config.behaviours) {
			behaviours.put(behaviour.name, behaviour.code);
		}
		
		// Create Maze
		((MazeGenerator) Class.forName(config.maze.generator).newInstance()).generate(this);

		// Create Robots
		for (Robot robot : config.robots) {
			add(((MazeRobotFactory) Class.forName(robot.factory).newInstance()).create(robot));
		}
	}
}