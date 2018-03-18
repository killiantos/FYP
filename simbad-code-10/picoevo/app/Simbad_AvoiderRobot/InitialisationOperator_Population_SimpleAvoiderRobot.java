package picoevo.app.Simbad_AvoiderRobot;

import java.util.ArrayList;

import picoevo.core.*;
import picoevo.core.evolution.*;
import picoevo.core.representation.*;
import picoevo.ext.*;
import picoevo.ext.evolution.*;
import picoevo.ext.representation.*;
import picoevo.toolbox.*;
import simbad.gui.Simbatch;


public class InitialisationOperator_Population_SimpleAvoiderRobot extends InitialisationOperator_Population_SimplePopulation {

	// ### Constructors ###
	
	public InitialisationOperator_Population_SimpleAvoiderRobot () 
	{
		super();
	}
	
	public InitialisationOperator_Population_SimpleAvoiderRobot (String __name) 
	{ 
		super(__name);
	}
	
	// ### Methods ###

	public void initialise( Population __o ) 
	{
		Population_SimplePopulation population = null;

		// 1. initialize and register individuals
		try {
			population = (Population_SimplePopulation)__o;
			super.initialise(population);
		}
		catch ( java.lang.ClassCastException e )
		{
			Display.error(""+this.getClass().getName()+"::initialise(.) - Class cast exception");
		}
		
		// 2. create the simulation environment for evaluation purpose
		try {
			Display.info("Initialize evaluation environment.");
			((ParameterSet_Evolution_mulambdaES_SimpleAvoiderRobot)population.getTemplate()).setRobot(new Evaluator_SimpleAvoiderRobot());
			((ParameterSet_Evolution_mulambdaES_SimpleAvoiderRobot)population.getTemplate()).setSimulator(new Simbatch(((ParameterSet_Evolution_mulambdaES_SimpleAvoiderRobot)population.getTemplate()).getRobot(),true));
			((ParameterSet_Evolution_mulambdaES_SimpleAvoiderRobot)population.getTemplate()).getSimulator().reset();
		}
		catch ( java.lang.NullPointerException e )
		{
			Display.error(""+this.getClass().getName()+"::initialise(.) - null pointer exception when accessing Robot and/or Simulator");
		}
	}
}
