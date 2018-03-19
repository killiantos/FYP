package astra.agents;
/**
 * GENERATED CODE - DO NOT CHANGE
 */

import astra.core.*;
import astra.execution.*;
import astra.event.*;
import astra.messaging.*;
import astra.formula.*;
import astra.lang.*;
import astra.statement.*;
import astra.term.*;
import astra.type.*;
import astra.tr.*;
import astra.reasoner.util.*;


public class Test extends ASTRAClass {
	public Test() {
		setParents(new Class[] {astra.lang.Agent.class});
		addRule(new Rule(
			"astra.agents.Test", new int[] {7,9,7,61},
			new GoalEvent('+',
				new Goal(
					new Predicate("start", new Term[] {
						new Variable(new ObjectType(astra.robot.ASTRARobot.class), "robot",false),
						new Variable(Type.LIST, "params",false)
					})
				)
			),
			Predicate.TRUE,
			new Block(
				"astra.agents.Test", new int[] {7,60,11,5},
				new Statement[] {
					new ModuleCall("simbad",
						"astra.agents.Test", new int[] {8,8,8,26},
						new Predicate("link", new Term[] {
							new Variable(new ObjectType(astra.robot.ASTRARobot.class), "robot")
						}),
						new DefaultModuleCallAdaptor() {
							public boolean inline() {
								return true;
							}

							public boolean invoke(Intention intention, Predicate predicate) {
								return ((astra.agents.Simbad) intention.getModule("astra.agents.Test","simbad")).link(
									(astra.robot.ASTRARobot) intention.evaluate(predicate.getTerm(0))
								);
							}
						}
					),
					new ModuleCall("C",
						"astra.agents.Test", new int[] {9,8,9,28},
						new Predicate("println", new Term[] {
							Primitive.newPrimitive("Started")
						}),
						new DefaultModuleCallAdaptor() {
							public boolean inline() {
								return true;
							}

							public boolean invoke(Intention intention, Predicate predicate) {
								return ((astra.lang.Console) intention.getModule("astra.agents.Test","C")).println(
									(java.lang.String) intention.evaluate(predicate.getTerm(0))
								);
							}
						}
					),
					new ModuleCall("simbad",
						"astra.agents.Test", new int[] {10,8,10,39},
						new Predicate("setBehaviour", new Term[] {
							Primitive.newPrimitive("FindWall")
						}),
						new DefaultModuleCallAdaptor() {
							public boolean inline() {
								return true;
							}

							public boolean invoke(Intention intention, Predicate predicate) {
								return ((astra.agents.Simbad) intention.getModule("astra.agents.Test","simbad")).setBehaviour(
									(java.lang.String) intention.evaluate(predicate.getTerm(0))
								);
							}
						}
					)
				}
			)
		));
		addRule(new Rule(
			"astra.agents.Test", new int[] {13,9,13,50},
			new ModuleEvent("simbad",
				"$re",
				new Predicate("event", new Term[] {
					Primitive.newPrimitive("FoundWall"),
					new Variable(Type.LIST, "params",false)
				}),
				new ModuleEventAdaptor() {
					public Event generate(astra.core.Agent agent, Predicate predicate) {
						return ((astra.agents.Simbad) agent.getModule("astra.agents.Test","simbad")).event(
							predicate.getTerm(0),
							predicate.getTerm(1)
						);
					}
				}
			),
			Predicate.TRUE,
			new Block(
				"astra.agents.Test", new int[] {13,49,18,5},
				new Statement[] {
					new ModuleCall("C",
						"astra.agents.Test", new int[] {14,8,14,31},
						new Predicate("println", new Term[] {
							Primitive.newPrimitive("Found Wall")
						}),
						new DefaultModuleCallAdaptor() {
							public boolean inline() {
								return true;
							}

							public boolean invoke(Intention intention, Predicate predicate) {
								return ((astra.lang.Console) intention.getModule("astra.agents.Test","C")).println(
									(java.lang.String) intention.evaluate(predicate.getTerm(0))
								);
							}
						}
					),
					new Declaration(
						new Variable(Type.FUNCTION, "f"),
						"astra.agents.Test", new int[] {15,8,18,5},
						new ModuleTerm("simbad", Type.FUNCTION,
							new Predicate("getCoordinates", new Term[] {}),
							new ModuleTermAdaptor() {
								public Object invoke(Intention intention, Predicate predicate) {
									return ((astra.agents.Simbad) intention.getModule("astra.agents.Test","simbad")).getCoordinates(
									);
								}
								public Object invoke(BindingsEvaluateVisitor visitor, Predicate predicate) {
									return ((astra.agents.Simbad) visitor.agent().getModule("astra.agents.Test","simbad")).getCoordinates(
									);
								}
							}
						)
					),
					new ModuleCall("C",
						"astra.agents.Test", new int[] {16,8,16,20},
						new Predicate("println", new Term[] {
							new Variable(Type.FUNCTION, "f")
						}),
						new DefaultModuleCallAdaptor() {
							public boolean inline() {
								return true;
							}

							public boolean invoke(Intention intention, Predicate predicate) {
								return ((astra.lang.Console) intention.getModule("astra.agents.Test","C")).println(
									(astra.term.Funct) intention.evaluate(predicate.getTerm(0))
								);
							}
						}
					),
					new ModuleCall("simbad",
						"astra.agents.Test", new int[] {17,8,17,41},
						new Predicate("setBehaviour", new Term[] {
							Primitive.newPrimitive("FollowWall")
						}),
						new DefaultModuleCallAdaptor() {
							public boolean inline() {
								return true;
							}

							public boolean invoke(Intention intention, Predicate predicate) {
								return ((astra.agents.Simbad) intention.getModule("astra.agents.Test","simbad")).setBehaviour(
									(java.lang.String) intention.evaluate(predicate.getTerm(0))
								);
							}
						}
					)
				}
			)
		));
	}

	public void initialize(astra.core.Agent agent) {
	}

	public Fragment createFragment(astra.core.Agent agent) throws ASTRAClassNotFoundException {
		Fragment fragment = new Fragment(this);
		fragment.addModule("simbad",astra.agents.Simbad.class,agent);
		fragment.addModule("C",astra.lang.Console.class,agent);
		return fragment;
	}

	public static void main(String[] args) {
		Scheduler.setStrategy(new AdaptiveSchedulerStrategy());
		ListTerm argList = new ListTerm();
		for (String arg: args) {
			argList.add(Primitive.newPrimitive(arg));
		}

		String name = java.lang.System.getProperty("astra.name", "main");
		try {
			astra.core.Agent agent = new Test().newInstance(name);
			agent.initialize(new Goal(new Predicate("main", new Term[] { argList })));
			Scheduler.schedule(agent);
		} catch (AgentCreationException e) {
			e.printStackTrace();
		} catch (ASTRAClassNotFoundException e) {
			e.printStackTrace();
		};
	}
}
