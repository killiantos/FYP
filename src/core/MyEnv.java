package core;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import config.Behaviour;
import config.Instance;
import config.Robot;
import config.RobotType;
import config.SystemConfig;
import simbad.sim.EnvironmentDescription;

public class MyEnv extends EnvironmentDescription {
	public static Map<String, String> behaviours = new HashMap<String, String>();
	
	private static Gson gson = new Gson();
	
	public MyEnv(String filename) throws InstantiationException, IllegalAccessException, ClassNotFoundException, JsonSyntaxException, JsonIOException, FileNotFoundException, InvocationTargetException, NoSuchMethodException, SecurityException {
		SystemConfig config = gson.fromJson(new FileReader(filename), SystemConfig.class);
	
		// Load System Behaviours
		for (Behaviour behaviour : config.behaviours) {
			behaviours.put(behaviour.name, behaviour.code);
		}
		
		// Create Maze
		((MazeGenerator) Class.forName(config.maze.generator).getConstructor().newInstance()).generate(this);

		if (config.types != null) {
			Map<String, RobotType> types = new TreeMap<String, RobotType>();
			for(RobotType type : config.types) {
				types.put(type.name, type);
			}
			
			if (config.instances.type.equals("enumeration")) {
				for (Instance instance : config.instances.list) {
					RobotType type = types.get(instance.type);
					if (type == null) throw new RuntimeException("No such robot type: " + instance.type); 
					add(((MazeRobotFactory) Class.forName(type.factory).getConstructor().newInstance()).create(type, instance));
				}
			}
		} else {
			// old robot creation mechanism 
			// Create Robots
			for (Robot robot : config.robots) {
				add(((MazeRobotFactory) Class.forName(robot.factory).getConstructor().newInstance()).create(robot));
			}
		}
	}
}