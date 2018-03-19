package astra.robot;
import java.lang.reflect.InvocationTargetException;

import javax.vecmath.Vector3d;

import astra.core.ASTRAClass;
import astra.core.ASTRAClassNotFoundException;
import astra.core.AgentCreationException;
import astra.core.Scheduler;
import astra.execution.AdaptiveSchedulerStrategy;
import astra.formula.Goal;
import astra.formula.Predicate;
import astra.term.ListTerm;
import astra.term.Primitive;
import astra.term.Term;
import config.Robot;
import simbad.sim.Agent;

public class ASTRARobotFactory extends AbstractRobotFactory {

	public Agent create(Robot robot) {
		ASTRARobot myRobot = new ASTRARobot(new Vector3d(robot.location.x, 0, robot.location.y), robot.name);
		initialize(myRobot, robot);
		
		if (!Scheduler.hasStrategy()) {
			Scheduler.setStrategy(new AdaptiveSchedulerStrategy());
		}
		
		// Load any additional arguments from the config line
		ListTerm argList = new ListTerm();
		for (int i=1; i < robot.config.size(); i++) {
			argList.add(Primitive.newPrimitive(robot.config.get(i)));
		}

		// Create the ASTRA agent...
		try {
			astra.core.Agent agent = ((ASTRAClass) Class.forName(robot.config.get(0)).getConstructor(new Class[0]).newInstance()).newInstance(robot.name);
			// custom initial goal: !setup(<robot>, <list-args>)
			agent.initialize(new Goal(new Predicate("start", new Term[] { Primitive.newPrimitive(myRobot), argList })));
			Scheduler.schedule(agent);
		} catch (AgentCreationException e) {
			e.printStackTrace();
		} catch (ASTRAClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		};
		
		return myRobot;
	}
}
