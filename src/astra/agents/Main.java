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


public class Main extends ASTRAClass {
	public Main() {
		setParents(new Class[] {astra.lang.Agent.class});
		addRule(new Rule(
			"astra.agents.Main", new int[] {13,9,13,28},
			new GoalEvent('+',
				new Goal(
					new Predicate("main", new Term[] {
						new Variable(Type.LIST, "args",false)
					})
				)
			),
			Predicate.TRUE,
			new Block(
				"astra.agents.Main", new int[] {13,27,15,5},
				new Statement[] {
					new ModuleCall("simbad",
						"astra.agents.Main", new int[] {14,8,14,35},
						new Predicate("launch", new Term[] {
							Primitive.newPrimitive("astra.json")
						}),
						new DefaultModuleCallAdaptor() {
							public boolean inline() {
								return true;
							}

							public boolean invoke(Intention intention, Predicate predicate) {
								return ((astra.agents.Simbad) intention.getModule("astra.agents.Main","simbad")).launch(
									(java.lang.String) intention.evaluate(predicate.getTerm(0))
								);
							}
						}
					)
				}
			)
		));
		addRule(new Rule(
			"astra.agents.Main", new int[] {17,9,17,57},
			new MessageEvent(
				new Performative("subscribe"),
				new Variable(Type.STRING, "sender",false),
				new Predicate("register", new Term[] {})
			),
			Predicate.TRUE,
			new Block(
				"astra.agents.Main", new int[] {17,56,20,5},
				new Statement[] {
					new BeliefUpdate('+',
						"astra.agents.Main", new int[] {18,8,20,5},
						new Predicate("registered", new Term[] {
							new Variable(Type.STRING, "sender")
						})
					),
					new Send("astra.agents.Main", new int[] {19,8,19,39},
						new Performative("agree"),
						new Variable(Type.STRING, "sender"),
						new Predicate("register", new Term[] {})
					)
				}
			)
		));
		addRule(new Rule(
			"astra.agents.Main", new int[] {22,9,22,85},
			new MessageEvent(
				new Performative("inform"),
				new Variable(Type.STRING, "sender",false),
				new Predicate("position", new Term[] {
					new Variable(Type.DOUBLE, "x",false),
					new Variable(Type.DOUBLE, "y",false),
					new Variable(Type.STRING, "type",false)
				})
			),
			Predicate.TRUE,
			new Block(
				"astra.agents.Main", new int[] {22,84,28,5},
				new Statement[] {
					new ForEach(
						"astra.agents.Main", new int[] {23,8,28,5},
						new Predicate("registered", new Term[] {
							new Variable(Type.STRING, "name",false)
						}),
						new Block(
							"astra.agents.Main", new int[] {23,42,27,9},
							new Statement[] {
								new If(
									"astra.agents.Main", new int[] {24,12,27,9},
									new Comparison("~=",
										new Variable(Type.STRING, "name"),
										new Variable(Type.STRING, "sender")
									),
									new Block(
										"astra.agents.Main", new int[] {24,32,26,13},
										new Statement[] {
											new Send("astra.agents.Main", new int[] {25,16,25,64},
												new Performative("inform"),
												new Variable(Type.STRING, "name"),
												new Predicate("position", new Term[] {
													new Variable(Type.STRING, "sender"),
													new Variable(Type.DOUBLE, "x"),
													new Variable(Type.DOUBLE, "y"),
													new Variable(Type.STRING, "type")
												})
											)
										}
									)
								)
							}
						)
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
			astra.core.Agent agent = new Main().newInstance(name);
			agent.initialize(new Goal(new Predicate("main", new Term[] { argList })));
			Scheduler.schedule(agent);
		} catch (AgentCreationException e) {
			e.printStackTrace();
		} catch (ASTRAClassNotFoundException e) {
			e.printStackTrace();
		};
	}
}
