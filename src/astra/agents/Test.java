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
			"astra.agents.Test", new int[] {26,9,26,61},
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
				"astra.agents.Test", new int[] {26,60,42,5},
				new Statement[] {
					new ModuleCall("simbad",
						"astra.agents.Test", new int[] {27,8,27,26},
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
						"astra.agents.Test", new int[] {28,8,28,28},
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
					new Send("astra.agents.Test", new int[] {30,8,30,43},
						new Performative("subscribe"),
						Primitive.newPrimitive("main"),
						new Predicate("register", new Term[] {})
					),
					new Subgoal(
						"astra.agents.Test", new int[] {33,8,42,5},
						new Goal(
							new Predicate("orient", new Term[] {})
						)
					),
					new Subgoal(
						"astra.agents.Test", new int[] {38,8,42,5},
						new Goal(
							new Predicate("record_location", new Term[] {
								Primitive.newPrimitive("Start")
							})
						)
					),
					new ModuleCall("map",
						"astra.agents.Test", new int[] {39,8,39,21},
						new Predicate("display", new Term[] {}),
						new DefaultModuleCallAdaptor() {
							public boolean inline() {
								return true;
							}

							public boolean invoke(Intention intention, Predicate predicate) {
								return ((astra.agents.Map) intention.getModule("astra.agents.Test","map")).display(
								);
							}
						}
					),
					new ModuleCall("simbad",
						"astra.agents.Test", new int[] {41,8,41,41},
						new Predicate("setBehaviour", new Term[] {
							Primitive.newPrimitive("GoStraight")
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
			"astra.agents.Test", new int[] {44,9,44,53},
			new MessageEvent(
				new Performative("agree"),
				new Variable(Type.STRING, "sender",false),
				new Predicate("register", new Term[] {})
			),
			Predicate.TRUE,
			new Block(
				"astra.agents.Test", new int[] {44,52,46,5},
				new Statement[] {
					new BeliefUpdate('+',
						"astra.agents.Test", new int[] {45,8,46,5},
						new Predicate("registered", new Term[] {
							new Variable(Type.STRING, "sender")
						})
					)
				}
			)
		));
		addRule(new Rule(
			"astra.agents.Test", new int[] {49,9,49,48},
			new ModuleEvent("simbad",
				"$re",
				new Predicate("event", new Term[] {
					Primitive.newPrimitive("Stopped"),
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
				"astra.agents.Test", new int[] {49,47,61,5},
				new Statement[] {
					new ModuleCall("C",
						"astra.agents.Test", new int[] {50,8,50,28},
						new Predicate("println", new Term[] {
							Primitive.newPrimitive("Stopped")
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
					new Subgoal(
						"astra.agents.Test", new int[] {51,8,61,5},
						new Goal(
							new Predicate("record_location", new Term[] {
								Primitive.newPrimitive("Wall")
							})
						)
					),
					new Subgoal(
						"astra.agents.Test", new int[] {53,8,61,5},
						new Goal(
							new Predicate("selectDirection", new Term[] {
								new Variable(Type.STRING, "direction",false)
							})
						)
					),
					new ModuleCall("C",
						"astra.agents.Test", new int[] {54,8,54,42},
						new Predicate("println", new Term[] {
							Operator.newOperator('+',
								Primitive.newPrimitive("Choosing: "),
								new Variable(Type.STRING, "direction")
							)
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
					new Subgoal(
						"astra.agents.Test", new int[] {55,8,61,5},
						new Goal(
							new Predicate("turn", new Term[] {
								new Variable(Type.STRING, "direction")
							})
						)
					),
					new Declaration(
						new Variable(Type.STRING, "wall"),
						"astra.agents.Test", new int[] {57,8,61,5},
						Primitive.newPrimitive("left")
					),
					new If(
						"astra.agents.Test", new int[] {58,8,61,5},
						new Comparison("==",
							new Variable(Type.STRING, "direction"),
							Primitive.newPrimitive("Left")
						),
						new Assignment(
							new Variable(Type.STRING, "wall"),
							"astra.agents.Test", new int[] {58,33,61,5},
							Primitive.newPrimitive("right")
						)
					),
					new ModuleCall("simbad",
						"astra.agents.Test", new int[] {60,8,60,55},
						new Predicate("setBehaviour", new Term[] {
							Primitive.newPrimitive("FollowWall"),
							new ListTerm(new Term[] {
								new Funct("side", new Term[] {
									new Variable(Type.STRING, "wall")
								})
							})
						}),
						new DefaultModuleCallAdaptor() {
							public boolean inline() {
								return true;
							}

							public boolean invoke(Intention intention, Predicate predicate) {
								return ((astra.agents.Simbad) intention.getModule("astra.agents.Test","simbad")).setBehaviour(
									(java.lang.String) intention.evaluate(predicate.getTerm(0)),
									(astra.term.ListTerm) intention.evaluate(predicate.getTerm(1))
								);
							}
						}
					)
				}
			)
		));
		addRule(new Rule(
			"astra.agents.Test", new int[] {64,9,64,49},
			new ModuleEvent("simbad",
				"$re",
				new Predicate("event", new Term[] {
					Primitive.newPrimitive("LostWall"),
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
				"astra.agents.Test", new int[] {64,48,70,5},
				new Statement[] {
					new ModuleCall("C",
						"astra.agents.Test", new int[] {65,8,65,40},
						new Predicate("println", new Term[] {
							Operator.newOperator('+',
								Primitive.newPrimitive("LostWall: "),
								new Variable(Type.LIST, "params")
							)
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
					new Subgoal(
						"astra.agents.Test", new int[] {67,8,70,5},
						new Goal(
							new Predicate("record_location", new Term[] {
								Primitive.newPrimitive("LostWall")
							})
						)
					),
					new ModuleCall("simbad",
						"astra.agents.Test", new int[] {69,8,69,41},
						new Predicate("setBehaviour", new Term[] {
							Primitive.newPrimitive("GoStraight")
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
			"astra.agents.Test", new int[] {73,9,73,68},
			new GoalEvent('+',
				new Goal(
					new Predicate("selectDirection", new Term[] {
						new Variable(Type.STRING, "direction",false)
					})
				)
			),
			new Predicate("location", new Term[] {
				new Variable(Type.INTEGER, "locId",false)
			}),
			new Block(
				"astra.agents.Test", new int[] {73,67,89,5},
				new Statement[] {
					new If(
						"astra.agents.Test", new int[] {74,8,89,5},
						new NOT(
							new Predicate("decision", new Term[] {
								new Variable(Type.INTEGER, "locId"),
								new ModuleTerm("map", Type.STRING,
									new Predicate("getOrientation", new Term[] {}),
									new ModuleTermAdaptor() {
										public Object invoke(Intention intention, Predicate predicate) {
											return ((astra.agents.Map) intention.getModule("astra.agents.Test","map")).getOrientation(
											);
										}
										public Object invoke(BindingsEvaluateVisitor visitor, Predicate predicate) {
											return ((astra.agents.Map) visitor.agent().getModule("astra.agents.Test","map")).getOrientation(
											);
										}
									}
								),
								Primitive.newPrimitive("Right")
							})
						),
						new Block(
							"astra.agents.Test", new int[] {74,61,78,9},
							new Statement[] {
								new ModuleCall("C",
									"astra.agents.Test", new int[] {75,12,75,38},
									new Predicate("println", new Term[] {
										Primitive.newPrimitive("setting right")
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
								new Assignment(
									new Variable(Type.STRING, "direction"),
									"astra.agents.Test", new int[] {76,12,78,9},
									Primitive.newPrimitive("Right")
								),
								new BeliefUpdate('+',
									"astra.agents.Test", new int[] {77,12,78,9},
									new Predicate("decision", new Term[] {
										new Variable(Type.INTEGER, "locId"),
										new ModuleTerm("map", Type.STRING,
											new Predicate("getOrientation", new Term[] {}),
											new ModuleTermAdaptor() {
												public Object invoke(Intention intention, Predicate predicate) {
													return ((astra.agents.Map) intention.getModule("astra.agents.Test","map")).getOrientation(
													);
												}
												public Object invoke(BindingsEvaluateVisitor visitor, Predicate predicate) {
													return ((astra.agents.Map) visitor.agent().getModule("astra.agents.Test","map")).getOrientation(
													);
												}
											}
										),
										Primitive.newPrimitive("Right")
									})
								)
							}
						),
						new If(
							"astra.agents.Test", new int[] {78,15,89,5},
							new NOT(
								new Predicate("decision", new Term[] {
									new Variable(Type.INTEGER, "locId"),
									new ModuleTerm("map", Type.STRING,
										new Predicate("getOrientation", new Term[] {}),
										new ModuleTermAdaptor() {
											public Object invoke(Intention intention, Predicate predicate) {
												return ((astra.agents.Map) intention.getModule("astra.agents.Test","map")).getOrientation(
												);
											}
											public Object invoke(BindingsEvaluateVisitor visitor, Predicate predicate) {
												return ((astra.agents.Map) visitor.agent().getModule("astra.agents.Test","map")).getOrientation(
												);
											}
										}
									),
									Primitive.newPrimitive("Left")
								})
							),
							new Block(
								"astra.agents.Test", new int[] {78,67,82,9},
								new Statement[] {
									new ModuleCall("C",
										"astra.agents.Test", new int[] {79,12,79,37},
										new Predicate("println", new Term[] {
											Primitive.newPrimitive("setting left")
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
									new Assignment(
										new Variable(Type.STRING, "direction"),
										"astra.agents.Test", new int[] {80,12,82,9},
										Primitive.newPrimitive("Left")
									),
									new BeliefUpdate('+',
										"astra.agents.Test", new int[] {81,12,82,9},
										new Predicate("decision", new Term[] {
											new Variable(Type.INTEGER, "locId"),
											new ModuleTerm("map", Type.STRING,
												new Predicate("getOrientation", new Term[] {}),
												new ModuleTermAdaptor() {
													public Object invoke(Intention intention, Predicate predicate) {
														return ((astra.agents.Map) intention.getModule("astra.agents.Test","map")).getOrientation(
														);
													}
													public Object invoke(BindingsEvaluateVisitor visitor, Predicate predicate) {
														return ((astra.agents.Map) visitor.agent().getModule("astra.agents.Test","map")).getOrientation(
														);
													}
												}
											),
											Primitive.newPrimitive("Left")
										})
									)
								}
							),
							new If(
								"astra.agents.Test", new int[] {82,15,89,5},
								new Comparison("==",
									Operator.newOperator('%',
										new ModuleTerm("M", Type.INTEGER,
											new Predicate("randomInt", new Term[] {}),
											new ModuleTermAdaptor() {
												public Object invoke(Intention intention, Predicate predicate) {
													return ((astra.lang.Math) intention.getModule("astra.agents.Test","M")).randomInt(
													);
												}
												public Object invoke(BindingsEvaluateVisitor visitor, Predicate predicate) {
													return ((astra.lang.Math) visitor.agent().getModule("astra.agents.Test","M")).randomInt(
													);
												}
											}
										),
										Primitive.newPrimitive(2)
									),
									Primitive.newPrimitive(0)
								),
								new Block(
									"astra.agents.Test", new int[] {82,41,85,9},
									new Statement[] {
										new ModuleCall("C",
											"astra.agents.Test", new int[] {83,12,83,37},
											new Predicate("println", new Term[] {
												Primitive.newPrimitive("random right")
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
										new Assignment(
											new Variable(Type.STRING, "direction"),
											"astra.agents.Test", new int[] {84,12,85,9},
											Primitive.newPrimitive("Right")
										)
									}
								),
								new Block(
									"astra.agents.Test", new int[] {85,15,89,5},
									new Statement[] {
										new ModuleCall("C",
											"astra.agents.Test", new int[] {86,12,86,36},
											new Predicate("println", new Term[] {
												Primitive.newPrimitive("random left")
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
										new Assignment(
											new Variable(Type.STRING, "direction"),
											"astra.agents.Test", new int[] {87,12,88,9},
											Primitive.newPrimitive("Left")
										)
									}
								)
							)
						)
					)
				}
			)
		));
		addRule(new Rule(
			"astra.agents.Test", new int[] {91,9,91,21},
			new GoalEvent('+',
				new Goal(
					new Predicate("orient", new Term[] {})
				)
			),
			Predicate.TRUE,
			new Block(
				"astra.agents.Test", new int[] {91,20,96,5},
				new Statement[] {
					new ModuleCall("simbad",
						"astra.agents.Test", new int[] {92,8,92,37},
						new Predicate("setBehaviour", new Term[] {
							Primitive.newPrimitive("Orient")
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
					),
					new Wait(
						"astra.agents.Test", new int[] {93,8,96,5},
						new Predicate("oriented", new Term[] {
							new Variable(Type.STRING, "direction",false)
						})
					),
					new BeliefUpdate('-',
						"astra.agents.Test", new int[] {94,8,96,5},
						new Predicate("oriented", new Term[] {
							new Variable(Type.STRING, "direction")
						})
					),
					new ModuleCall("map",
						"astra.agents.Test", new int[] {95,8,95,37},
						new Predicate("setOrientation", new Term[] {
							new Variable(Type.STRING, "direction")
						}),
						new DefaultModuleCallAdaptor() {
							public boolean inline() {
								return true;
							}

							public boolean invoke(Intention intention, Predicate predicate) {
								return ((astra.agents.Map) intention.getModule("astra.agents.Test","map")).setOrientation(
									(java.lang.String) intention.evaluate(predicate.getTerm(0))
								);
							}
						}
					)
				}
			)
		));
		addRule(new Rule(
			"astra.agents.Test", new int[] {98,9,98,35},
			new GoalEvent('+',
				new Goal(
					new Predicate("turn", new Term[] {
						new Variable(Type.STRING, "direction",false)
					})
				)
			),
			Predicate.TRUE,
			new Block(
				"astra.agents.Test", new int[] {98,34,105,5},
				new Statement[] {
					new ModuleCall("C",
						"astra.agents.Test", new int[] {99,8,99,42},
						new Predicate("println", new Term[] {
							Operator.newOperator('+',
								Primitive.newPrimitive("Turning: "),
								new Variable(Type.STRING, "direction")
							)
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
						"astra.agents.Test", new int[] {100,8,100,45},
						new Predicate("setBehaviour", new Term[] {
							Operator.newOperator('+',
								Primitive.newPrimitive("Turn"),
								new Variable(Type.STRING, "direction")
							)
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
					),
					new Wait(
						"astra.agents.Test", new int[] {101,8,105,5},
						new Predicate("turned", new Term[] {
							new Variable(Type.STRING, "direction")
						})
					),
					new BeliefUpdate('-',
						"astra.agents.Test", new int[] {102,8,105,5},
						new Predicate("turned", new Term[] {
							new Variable(Type.STRING, "direction")
						})
					),
					new Subgoal(
						"astra.agents.Test", new int[] {104,8,105,5},
						new Goal(
							new Predicate("orient", new Term[] {})
						)
					)
				}
			)
		));
		addRule(new Rule(
			"astra.agents.Test", new int[] {107,9,107,41},
			new GoalEvent('+',
				new Goal(
					new Predicate("record_location", new Term[] {
						new Variable(Type.STRING, "type",false)
					})
				)
			),
			Predicate.TRUE,
			new Block(
				"astra.agents.Test", new int[] {107,40,135,5},
				new Statement[] {
					new ModuleCall("C",
						"astra.agents.Test", new int[] {108,8,108,91},
						new Predicate("println", new Term[] {
							Operator.newOperator('+',
								Primitive.newPrimitive("++++++++++++++++++++++++++++ "),
								Operator.newOperator('+',
									new Variable(Type.STRING, "type"),
									Primitive.newPrimitive(" ++++++++++++++++++++++++++++")
								)
							)
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
						"astra.agents.Test", new int[] {109,8,109,49},
						new Predicate("getCoordinates", new Term[] {
							new Variable(Type.DOUBLE, "x",false),
							new Variable(Type.DOUBLE, "y",false)
						}),
						new DefaultModuleCallAdaptor() {
							public boolean inline() {
								return true;
							}

							public boolean invoke(Intention intention, Predicate predicate) {
								return ((astra.agents.Simbad) intention.getModule("astra.agents.Test","simbad")).getCoordinates(
									(ActionParam<java.lang.Double>) intention.evaluate(predicate.getTerm(0)),
									(ActionParam<java.lang.Double>) intention.evaluate(predicate.getTerm(1))
								);
							}
						}
					),
					new Declaration(
						new Variable(Type.INTEGER, "locId"),
						"astra.agents.Test", new int[] {110,8,135,5},
						new ModuleTerm("map", Type.INTEGER,
							new Predicate("findLocation", new Term[] {
								new Variable(Type.DOUBLE, "x"),
								new Variable(Type.DOUBLE, "y"),
								Primitive.newPrimitive(0.3)
							}),
							new ModuleTermAdaptor() {
								public Object invoke(Intention intention, Predicate predicate) {
									return ((astra.agents.Map) intention.getModule("astra.agents.Test","map")).findLocation(
										(double) intention.evaluate(predicate.getTerm(0)),
										(double) intention.evaluate(predicate.getTerm(1)),
										(double) intention.evaluate(predicate.getTerm(2))
									);
								}
								public Object invoke(BindingsEvaluateVisitor visitor, Predicate predicate) {
									return ((astra.agents.Map) visitor.agent().getModule("astra.agents.Test","map")).findLocation(
										(double) visitor.evaluate(predicate.getTerm(0)),
										(double) visitor.evaluate(predicate.getTerm(1)),
										(double) visitor.evaluate(predicate.getTerm(2))
									);
								}
							}
						)
					),
					new If(
						"astra.agents.Test", new int[] {111,8,135,5},
						new Comparison(">",
							new Variable(Type.INTEGER, "locId"),
							Primitive.newPrimitive(-1)
						),
						new Block(
							"astra.agents.Test", new int[] {111,23,117,9},
							new Statement[] {
								new ModuleCall("C",
									"astra.agents.Test", new int[] {112,12,112,49},
									new Predicate("println", new Term[] {
										Operator.newOperator('+',
											Primitive.newPrimitive("FOUND LOCATION: "),
											new Variable(Type.INTEGER, "locId")
										)
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
								new SpecialBeliefUpdate(
									"astra.agents.Test", new int[] {113,12,117,9},
									new Predicate("location", new Term[] {
										new Variable(Type.INTEGER, "locId")
									})
								),
								new ForEach(
									"astra.agents.Test", new int[] {114,12,117,9},
									new Predicate("at", new Term[] {
										new Variable(Type.INTEGER, "locId"),
										new Variable(Type.STRING, "ori",false),
										new Variable(Type.STRING, "t",false)
									}),
									new Block(
										"astra.agents.Test", new int[] {114,54,116,13},
										new Statement[] {
											new ModuleCall("C",
												"astra.agents.Test", new int[] {115,16,115,50},
												new Predicate("println", new Term[] {
													Operator.newOperator('+',
														Primitive.newPrimitive("ori="),
														Operator.newOperator('+',
															new Variable(Type.STRING, "ori"),
															Operator.newOperator('+',
																Primitive.newPrimitive(" / type="),
																new Variable(Type.STRING, "t")
															)
														)
													)
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
											)
										}
									)
								)
							}
						),
						new Block(
							"astra.agents.Test", new int[] {117,15,135,5},
							new Statement[] {
								new Declaration(
									new Variable(Type.INTEGER, "loc"),
									"astra.agents.Test", new int[] {122,12,134,9}
								),
								new If(
									"astra.agents.Test", new int[] {123,12,134,9},
									new Predicate("location", new Term[] {
										new Variable(Type.INTEGER, "lid",false)
									}),
									new Block(
										"astra.agents.Test", new int[] {123,35,126,13},
										new Statement[] {
											new ModuleCall("map",
												"astra.agents.Test", new int[] {124,16,124,68},
												new Predicate("recordLocation", new Term[] {
													new ModuleTerm("S", Type.STRING,
														new Predicate("name", new Term[] {}),
														new ModuleTermAdaptor() {
															public Object invoke(Intention intention, Predicate predicate) {
																return ((astra.lang.System) intention.getModule("astra.agents.Test","S")).name(
																);
															}
															public Object invoke(BindingsEvaluateVisitor visitor, Predicate predicate) {
																return ((astra.lang.System) visitor.agent().getModule("astra.agents.Test","S")).name(
																);
															}
														}
													),
													new Variable(Type.INTEGER, "lid"),
													new Variable(Type.DOUBLE, "x"),
													new Variable(Type.DOUBLE, "y"),
													new Variable(Type.STRING, "type"),
													new Variable(Type.INTEGER, "l",false)
												}),
												new DefaultModuleCallAdaptor() {
													public boolean inline() {
														return true;
													}

													public boolean invoke(Intention intention, Predicate predicate) {
														return ((astra.agents.Map) intention.getModule("astra.agents.Test","map")).recordLocation(
															(java.lang.String) intention.evaluate(predicate.getTerm(0)),
															(java.lang.Integer) intention.evaluate(predicate.getTerm(1)),
															(java.lang.Double) intention.evaluate(predicate.getTerm(2)),
															(java.lang.Double) intention.evaluate(predicate.getTerm(3)),
															(java.lang.String) intention.evaluate(predicate.getTerm(4)),
															(ActionParam<java.lang.Integer>) intention.evaluate(predicate.getTerm(5))
														);
													}
												}
											),
											new Assignment(
												new Variable(Type.INTEGER, "loc"),
												"astra.agents.Test", new int[] {125,16,126,13},
												new Variable(Type.INTEGER, "l")
											)
										}
									),
									new Block(
										"astra.agents.Test", new int[] {126,19,134,9},
										new Statement[] {
											new ModuleCall("map",
												"astra.agents.Test", new int[] {127,16,127,66},
												new Predicate("recordLocation", new Term[] {
													new ModuleTerm("S", Type.STRING,
														new Predicate("name", new Term[] {}),
														new ModuleTermAdaptor() {
															public Object invoke(Intention intention, Predicate predicate) {
																return ((astra.lang.System) intention.getModule("astra.agents.Test","S")).name(
																);
															}
															public Object invoke(BindingsEvaluateVisitor visitor, Predicate predicate) {
																return ((astra.lang.System) visitor.agent().getModule("astra.agents.Test","S")).name(
																);
															}
														}
													),
													Primitive.newPrimitive(-1),
													new Variable(Type.DOUBLE, "x"),
													new Variable(Type.DOUBLE, "y"),
													new Variable(Type.STRING, "type"),
													new Variable(Type.INTEGER, "l",false)
												}),
												new DefaultModuleCallAdaptor() {
													public boolean inline() {
														return true;
													}

													public boolean invoke(Intention intention, Predicate predicate) {
														return ((astra.agents.Map) intention.getModule("astra.agents.Test","map")).recordLocation(
															(java.lang.String) intention.evaluate(predicate.getTerm(0)),
															(java.lang.Integer) intention.evaluate(predicate.getTerm(1)),
															(java.lang.Double) intention.evaluate(predicate.getTerm(2)),
															(java.lang.Double) intention.evaluate(predicate.getTerm(3)),
															(java.lang.String) intention.evaluate(predicate.getTerm(4)),
															(ActionParam<java.lang.Integer>) intention.evaluate(predicate.getTerm(5))
														);
													}
												}
											),
											new Assignment(
												new Variable(Type.INTEGER, "loc"),
												"astra.agents.Test", new int[] {128,16,129,13},
												new Variable(Type.INTEGER, "l")
											)
										}
									)
								),
								new ModuleCall("C",
									"astra.agents.Test", new int[] {131,12,131,45},
									new Predicate("println", new Term[] {
										Operator.newOperator('+',
											Primitive.newPrimitive("NEW LOCATION: "),
											new Variable(Type.INTEGER, "loc")
										)
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
								new SpecialBeliefUpdate(
									"astra.agents.Test", new int[] {132,12,134,9},
									new Predicate("location", new Term[] {
										new Variable(Type.INTEGER, "loc")
									})
								),
								new BeliefUpdate('+',
									"astra.agents.Test", new int[] {133,12,134,9},
									new Predicate("at", new Term[] {
										new Variable(Type.INTEGER, "loc"),
										new ModuleTerm("map", Type.STRING,
											new Predicate("getOrientation", new Term[] {}),
											new ModuleTermAdaptor() {
												public Object invoke(Intention intention, Predicate predicate) {
													return ((astra.agents.Map) intention.getModule("astra.agents.Test","map")).getOrientation(
													);
												}
												public Object invoke(BindingsEvaluateVisitor visitor, Predicate predicate) {
													return ((astra.agents.Map) visitor.agent().getModule("astra.agents.Test","map")).getOrientation(
													);
												}
											}
										),
										new Variable(Type.STRING, "type")
									})
								)
							}
						)
					)
				}
			)
		));
		addRule(new Rule(
			"astra.agents.Test", new int[] {138,9,138,126},
			new MessageEvent(
				new Performative("inform"),
				new Variable(Type.STRING, "sender",false),
				new Predicate("position", new Term[] {
					new Variable(Type.STRING, "robot",false),
					new Variable(Type.DOUBLE, "x",false),
					new Variable(Type.DOUBLE, "y",false),
					new Variable(Type.STRING, "type",false)
				})
			),
			new Predicate("location", new Term[] {
				new Variable(Type.STRING, "robot"),
				new Variable(Type.INTEGER, "loc",false)
			}),
			new Block(
				"astra.agents.Test", new int[] {138,125,141,5},
				new Statement[] {
					new ModuleCall("map",
						"astra.agents.Test", new int[] {139,8,139,61},
						new Predicate("recordLocation", new Term[] {
							new Variable(Type.STRING, "robot"),
							new Variable(Type.INTEGER, "loc"),
							new Variable(Type.DOUBLE, "x"),
							new Variable(Type.DOUBLE, "y"),
							new Variable(Type.STRING, "type"),
							new Variable(Type.INTEGER, "locId",false)
						}),
						new DefaultModuleCallAdaptor() {
							public boolean inline() {
								return true;
							}

							public boolean invoke(Intention intention, Predicate predicate) {
								return ((astra.agents.Map) intention.getModule("astra.agents.Test","map")).recordLocation(
									(java.lang.String) intention.evaluate(predicate.getTerm(0)),
									(java.lang.Integer) intention.evaluate(predicate.getTerm(1)),
									(java.lang.Double) intention.evaluate(predicate.getTerm(2)),
									(java.lang.Double) intention.evaluate(predicate.getTerm(3)),
									(java.lang.String) intention.evaluate(predicate.getTerm(4)),
									(ActionParam<java.lang.Integer>) intention.evaluate(predicate.getTerm(5))
								);
							}
						}
					),
					new SpecialBeliefUpdate(
						"astra.agents.Test", new int[] {140,8,141,5},
						new Predicate("location", new Term[] {
							new Variable(Type.STRING, "robot"),
							new Variable(Type.INTEGER, "locId")
						})
					)
				}
			)
		));
		addRule(new Rule(
			"astra.agents.Test", new int[] {143,9,143,99},
			new MessageEvent(
				new Performative("inform"),
				new Variable(Type.STRING, "sender",false),
				new Predicate("position", new Term[] {
					new Variable(Type.STRING, "robot",false),
					new Variable(Type.DOUBLE, "x",false),
					new Variable(Type.DOUBLE, "y",false),
					new Variable(Type.STRING, "type",false)
				})
			),
			Predicate.TRUE,
			new Block(
				"astra.agents.Test", new int[] {143,98,146,5},
				new Statement[] {
					new ModuleCall("map",
						"astra.agents.Test", new int[] {144,8,144,59},
						new Predicate("recordLocation", new Term[] {
							new Variable(Type.STRING, "robot"),
							Primitive.newPrimitive(-1),
							new Variable(Type.DOUBLE, "x"),
							new Variable(Type.DOUBLE, "y"),
							new Variable(Type.STRING, "type"),
							new Variable(Type.INTEGER, "locId",false)
						}),
						new DefaultModuleCallAdaptor() {
							public boolean inline() {
								return true;
							}

							public boolean invoke(Intention intention, Predicate predicate) {
								return ((astra.agents.Map) intention.getModule("astra.agents.Test","map")).recordLocation(
									(java.lang.String) intention.evaluate(predicate.getTerm(0)),
									(java.lang.Integer) intention.evaluate(predicate.getTerm(1)),
									(java.lang.Double) intention.evaluate(predicate.getTerm(2)),
									(java.lang.Double) intention.evaluate(predicate.getTerm(3)),
									(java.lang.String) intention.evaluate(predicate.getTerm(4)),
									(ActionParam<java.lang.Integer>) intention.evaluate(predicate.getTerm(5))
								);
							}
						}
					),
					new SpecialBeliefUpdate(
						"astra.agents.Test", new int[] {145,8,146,5},
						new Predicate("location", new Term[] {
							new Variable(Type.STRING, "robot"),
							new Variable(Type.INTEGER, "locId")
						})
					)
				}
			)
		));
		addRule(new Rule(
			"astra.agents.Test", new int[] {149,9,149,50},
			new ModuleEvent("simbad",
				"$re",
				new Predicate("event", new Term[] {
					Primitive.newPrimitive("Collision"),
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
				"astra.agents.Test", new int[] {149,49,152,5},
				new Statement[] {
					new ModuleCall("C",
						"astra.agents.Test", new int[] {150,8,150,37},
						new Predicate("println", new Term[] {
							Primitive.newPrimitive("We hit the wall!")
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
					)
				}
			)
		));
		addRule(new Rule(
			"astra.agents.Test", new int[] {154,9,154,52},
			new ModuleEvent("simbad",
				"$re",
				new Predicate("event", new Term[] {
					Primitive.newPrimitive("TurnedRight"),
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
				"astra.agents.Test", new int[] {154,51,157,5},
				new Statement[] {
					new ModuleCall("C",
						"astra.agents.Test", new int[] {155,8,155,33},
						new Predicate("println", new Term[] {
							Primitive.newPrimitive("Turned Right")
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
					new BeliefUpdate('+',
						"astra.agents.Test", new int[] {156,8,157,5},
						new Predicate("turned", new Term[] {
							Primitive.newPrimitive("Right")
						})
					)
				}
			)
		));
		addRule(new Rule(
			"astra.agents.Test", new int[] {159,9,159,51},
			new ModuleEvent("simbad",
				"$re",
				new Predicate("event", new Term[] {
					Primitive.newPrimitive("TurnedLeft"),
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
				"astra.agents.Test", new int[] {159,50,162,5},
				new Statement[] {
					new ModuleCall("C",
						"astra.agents.Test", new int[] {160,8,160,32},
						new Predicate("println", new Term[] {
							Primitive.newPrimitive("Turned Left")
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
					new BeliefUpdate('+',
						"astra.agents.Test", new int[] {161,8,162,5},
						new Predicate("turned", new Term[] {
							Primitive.newPrimitive("Left")
						})
					)
				}
			)
		));
		addRule(new Rule(
			"astra.agents.Test", new int[] {164,9,164,56},
			new ModuleEvent("simbad",
				"$re",
				new Predicate("event", new Term[] {
					Primitive.newPrimitive("Oriented"),
					new ListTerm(new Term[] {
						new Variable(Type.STRING, "direction",false)
					})
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
				"astra.agents.Test", new int[] {164,55,167,5},
				new Statement[] {
					new ModuleCall("C",
						"astra.agents.Test", new int[] {165,8,165,43},
						new Predicate("println", new Term[] {
							Operator.newOperator('+',
								Primitive.newPrimitive("Oriented: "),
								new Variable(Type.STRING, "direction")
							)
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
					new BeliefUpdate('+',
						"astra.agents.Test", new int[] {166,8,167,5},
						new Predicate("oriented", new Term[] {
							new Variable(Type.STRING, "direction")
						})
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
		fragment.addModule("map",astra.agents.Map.class,agent);
		fragment.addModule("M",astra.lang.Math.class,agent);
		fragment.addModule("C",astra.lang.Console.class,agent);
		fragment.addModule("S",astra.lang.System.class,agent);
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
