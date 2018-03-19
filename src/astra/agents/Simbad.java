package astra.agents;

import javax.vecmath.Point3d;

import astra.core.Module;
import astra.event.Event;
import astra.reasoner.Unifier;
import astra.robot.ASTRARobot;
import astra.robot.ASTRARobotEvent;
import astra.robot.ASTRARobotEventUnifier;
import astra.term.Funct;
import astra.term.Primitive;
import astra.term.Term;

public class Simbad extends Module {
	static {
		Unifier.eventFactory.put(ASTRARobotEvent.class, new ASTRARobotEventUnifier());
	}

	private ASTRARobot robot;
	
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

	@TERM
	public Funct getCoordinates() {
		Point3d coords = new Point3d();
		robot.getCoords(coords);
		return new Funct("coords", new Term[] {
				Primitive.newPrimitive(coords.x),
				Primitive.newPrimitive(coords.y),
				Primitive.newPrimitive(coords.z)
		});
	}
	@EVENT(types = { "string", "list" }, signature = "$re", symbols = {})
	public Event event(Term type, Term params) {
		return new ASTRARobotEvent(type,params);
	}
}
