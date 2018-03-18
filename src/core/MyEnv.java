package core;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import config.Robot;
import config.SystemConfig;
import simbad.sim.EnvironmentDescription;

public class MyEnv extends EnvironmentDescription {
	private static Gson gson = new Gson();
	
	public MyEnv(String filename) throws InstantiationException, IllegalAccessException, ClassNotFoundException, JsonSyntaxException, JsonIOException, FileNotFoundException {
		SystemConfig config = gson.fromJson(new FileReader(filename), SystemConfig.class);
		
		((MazeGenerator) Class.forName(config.maze.generator).newInstance()).generate(this);

		for (Robot robot : config.robots) {
			add(((MazeRobotFactory) Class.forName(robot.factory).newInstance()).create(robot));
		}
	}
}