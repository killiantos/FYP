package astra.agents;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.vecmath.Point3d;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import astra.core.ActionParam;
import astra.core.Module;
import astra.event.Event;
import astra.reasoner.Unifier;
import astra.robot.ASTRARobot;
import astra.robot.ASTRARobotEvent;
import astra.robot.ASTRARobotEventUnifier;
import astra.term.Funct;
import astra.term.ListTerm;
import astra.term.Primitive;
import astra.term.Term;
import core.MyEnv;

public class Simbad extends Module {
	static {
		Unifier.eventFactory.put(ASTRARobotEvent.class, new ASTRARobotEventUnifier());
	}

	private ASTRARobot robot;
	private static simbad.gui.Simbad frame;
	
	@ACTION
	public boolean launch(String filename) {
		if (frame == null)
			try {
				frame = new simbad.gui.Simbad(new MyEnv(filename) ,false);
			} catch (JsonSyntaxException | JsonIOException | InstantiationException | IllegalAccessException
					| ClassNotFoundException | FileNotFoundException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
				return false;
			}
		return true;
	}
	
	@ACTION
	public boolean link(ASTRARobot robot) {
		this.robot = robot;
		robot.setAgent(agent);
		return true;
	}
	
	@ACTION
	public boolean setBehaviour(String behaviour) {
		if (robot == null) throw new RuntimeException("Agent is not linked to a robot");
		robot.setBehaviour(behaviour);
		return true;
	}

	@ACTION
	public boolean setVelocity(double translation, double rotation) {
		if (robot == null) throw new RuntimeException("Agent is not linked to a robot");
		robot.setTranslationalVelocity(translation);
		robot.setRotationalVelocity(rotation);
		return true;
	}

	@ACTION
	public boolean setBehaviour(String behaviour, ListTerm config) {
		if (robot == null) throw new RuntimeException("Agent is not linked to a robot");
		
		Map<String, Object> map = new HashMap<String, Object>();
		for (Term term : config) {
			Funct funct = (Funct) term;
			map.put(funct.functor(), ((Primitive) funct.termAt(0)).value()); 
		}
		
		robot.setBehaviour(behaviour, map);
		return true;
	}

	@ACTION
	public boolean getCoordinates(ActionParam<Double> x, ActionParam<Double> y) {
		Point3d coords = new Point3d();
		robot.getCoords(coords);
		x.set(coords.x);
		y.set(coords.z);
		return true;
	}
	
	@EVENT(types = { "string", "list" }, signature = "$re", symbols = {})
	public Event event(Term type, Term params) {
		return new ASTRARobotEvent(type,params);
	}
}
