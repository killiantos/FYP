/*
 * Created on 16 févr. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package picoevo.app.Simbad_AvoiderRobot;
import java.util.ArrayList;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import simbad.sim.*;
import simbad.gui.*;

import picoevo.toolbox.Display;

import piconode.core.arc.*;
import piconode.ext.*;
import piconode.core.node.*;

/**
 * Test of the batch mode - test Simbatch class.  
 */
public class Evaluator_SimpleAvoiderRobot extends EnvironmentDescription {

	public double	_fitness = 0;
	private double[] _genome;
	public boolean _isRunnable = true;
	MyRobot _myRobot = new MyRobot(new Vector3d(+4, 0, +3.5f), "picoevorobot example 1", this);
	
    public Evaluator_SimpleAvoiderRobot() 
    {
    	// build the environment
    	Wall w1 = new Wall(new Vector3d(9, 0, 0), 19, 1, this);
        w1.rotate90(1);
        add(w1);
        Wall w2 = new Wall(new Vector3d(-9, 0, 0), 19, 2, this);
        w2.rotate90(1);
        add(w2);
        Wall w3 = new Wall(new Vector3d(0, 0, 9), 19, 1, this);
        add(w3);
        Wall w4 = new Wall(new Vector3d(0, 0, -9), 19, 2, this);
        add(w4);
        

	//	add(new Box(new Vector3d(-5,0,0),new Vector3f(0.1f,1,10),this));
	//	add(new Box(new Vector3d(0,0,-5),new Vector3f(10,1,0.1f),this));
	//	add(new Box(new Vector3d(5,0,0),new Vector3f(0.1f,1,10),this));
        add(new Box(new Vector3d(5,0,0),new Vector3f(1f,1,10),this));
		add(new Box(new Vector3d(0,0,5),new Vector3f(10,1,1f),this));
		add(new Box(new Vector3d(0,0,0),new Vector3f(4,1,4),this));

		// create the robot and record the NN weights
        add(_myRobot);
    }
    
    public void setGenome (double[] __neuralNetWeightsList)
    {
        this._genome = __neuralNetWeightsList;
    }

    public class MyRobot extends Agent {

    	//RangeSensorBelt bumpers;
    	RangeSensorBelt sonars;

    	private Evaluator_SimpleAvoiderRobot _setup;  // useless... unless we later decide to externalize this internal class (!n)
    	private FeedForwardNeuralNetwork _robotNetworkController;
    	
    	ArrayList _inputControllerValuesList;
    	
        public MyRobot(Vector3d position, String name, Evaluator_SimpleAvoiderRobot __setup) {
        	super(position, name);
            
        	_setup = __setup;

            // Add sensors
            //bumpers = RobotFactory.addBumperBeltSensor(this, 12);
            sonars = RobotFactory.addSonarBeltSensor(this,12);
            
            _inputControllerValuesList = new ArrayList();
            /*_inputControllerValuesList.add(new Double(0));
            _inputControllerValuesList.add(new Double(0));
            _inputControllerValuesList.add(new Double(0));
            _inputControllerValuesList.add(new Double(0));*/
            
        }

      
        /** Initialize Agent's Behavior -- launched before the first performOneStep */
        public void initBehavior() {
        	// load the neural network weights
		    /* (NN1) Initializing and building a neural net */
			
        		// step 1 : create a network
			
			_robotNetworkController = new FeedForwardNeuralNetwork( new ActivationFunction_hyperbolicTangent() );
			
			// step 2 : create some neurons 
			//	- naming neuron (ex.: "in1", "in2", ...) is useful when debugging or when using network.displayInformation() method  

			Neuron in1 = new Neuron( _robotNetworkController, "in1");
			Neuron in2 = new Neuron( _robotNetworkController, "in2");
			Neuron in3 = new Neuron( _robotNetworkController, "in3");
			Neuron in4 = new Neuron( _robotNetworkController, "in4");
			Neuron hidden1 = new Neuron( _robotNetworkController, new ActivationFunction_hyperbolicTangent(), "hidden(1)");
			Neuron hidden2 = new Neuron( _robotNetworkController, new ActivationFunction_hyperbolicTangent(), "hidden(2)");
			Neuron hidden3 = new Neuron( _robotNetworkController, new ActivationFunction_hyperbolicTangent(), "hidden(3)");
			Neuron hidden4 = new Neuron( _robotNetworkController, new ActivationFunction_hyperbolicTangent(), "hidden(4)");
			Neuron out1 = new Neuron( _robotNetworkController, new ActivationFunction_hyperbolicTangent(), "output(1)" );
			Neuron out2 = new Neuron( _robotNetworkController, new ActivationFunction_hyperbolicTangent(), "output(2)" );
			
			// step 3 : declare input and output neurons
			
			_robotNetworkController.registerInputNeuron( in1 );
			_robotNetworkController.registerInputNeuron( in2 );
			_robotNetworkController.registerInputNeuron( in3 );
			_robotNetworkController.registerInputNeuron( in4 );
			_robotNetworkController.registerOutputNeuron( out1 ); // if several outputs, order is important if you intend to use a learning algorithm (i.e. matching target and predicted values is performed in list order)
			_robotNetworkController.registerOutputNeuron( out2 );
			// step 4 : create the topology
			
			_robotNetworkController.registerArc(new WeightedArc( in1 , hidden1 , 0 ));
			_robotNetworkController.registerArc(new WeightedArc( in1 , hidden2 , 0 ));
			_robotNetworkController.registerArc(new WeightedArc( in1 , hidden3 , 0 ));
			_robotNetworkController.registerArc(new WeightedArc( in1 , hidden4 , 0  ));
			_robotNetworkController.registerArc(new WeightedArc( in2 , hidden1 , 0 ));
			_robotNetworkController.registerArc(new WeightedArc( in2 , hidden2 , 0 ));
			_robotNetworkController.registerArc(new WeightedArc( in2 , hidden3 , 0 ));
			_robotNetworkController.registerArc(new WeightedArc( in2 , hidden4 , 0  ));
			_robotNetworkController.registerArc(new WeightedArc( in3 , hidden1 , 0 ));
			_robotNetworkController.registerArc(new WeightedArc( in3 , hidden2 , 0 ));
			_robotNetworkController.registerArc(new WeightedArc( in3 , hidden3 , 0 ));
			_robotNetworkController.registerArc(new WeightedArc( in3 , hidden4 , 0  ));
			_robotNetworkController.registerArc(new WeightedArc( in4 , hidden1 , 0 ));
			_robotNetworkController.registerArc(new WeightedArc( in4 , hidden2 , 0 ));
			_robotNetworkController.registerArc(new WeightedArc( in4 , hidden3 , 0 ));
			_robotNetworkController.registerArc(new WeightedArc( in4 , hidden4 , 0  ));
			_robotNetworkController.registerArc(new WeightedArc( hidden1, out1, 0  ));
			_robotNetworkController.registerArc(new WeightedArc( hidden2, out1, 0 ));
			_robotNetworkController.registerArc(new WeightedArc( hidden3, out1, 0 ));
			_robotNetworkController.registerArc(new WeightedArc( hidden4, out1, 0 ));
			_robotNetworkController.registerArc(new WeightedArc( hidden1, out2, 0  ));
			_robotNetworkController.registerArc(new WeightedArc( hidden2, out2, 0 ));
			_robotNetworkController.registerArc(new WeightedArc( hidden3, out2, 0 ));
			_robotNetworkController.registerArc(new WeightedArc( hidden4, out2, 0 ));
			_robotNetworkController.registerArc(new WeightedArc( in1, out1, 0 ));
			_robotNetworkController.registerArc(new WeightedArc( in1, out2, 0 ));
			
			// step 5 : initialize the network (perform some integrity checks and internal encoding)
			
			_robotNetworkController.initNetwork();

			// load the bias weights
			ArrayList biasvalues = new ArrayList();
			for (int i = 0 ; i != 6 ; i++ )
				biasvalues.add(new Double(0));
			_robotNetworkController.setBiasArcsWeightValues(biasvalues);

			// step 6 (optional -- parameters shown are default) : set parameters for learning
			/*			
			network.backprop_setEtaLearningRate(0.5); // example purpose (default value is already 1...)
			in1.setLearningNodeFlag(true); 		// example purpose (default value is already true...)  
			hidden1.setLearningNodeFlag(true); 	// -- this should be used when you want part of the network not to be affected by learning   
			hidden2.setLearningNodeFlag(true);	// -- arcs targeting a neuron which has its learningNodeFlag to arcs targeting a neuron which  
			hidden3.setLearningNodeFlag(true);	// -- has its learningNodeFlag set to "false" (i.e. neuron will not be modified during learning). 
			hidden4.setLearningNodeFlag(true);  // -- not that for example purpose, we created the output neuron "out2" which is *not* considered    
			out1.setLearningNodeFlag(true);		// -- during learning (learningNodeFlag set to false). This can be very useful in neural architecture 
												// -- where part of the outputs are not be considered during learning, e.g. nolfi/floreano/parisi kind
												// -- of NN (auto-teaching network, anticipation/control nets, ...) -- usually this type of NN are partly
												// -- optimized through Artificial Evolution.
			(...)
			ajouter le code relatif a in[2-4]
	        */		
        	
			//this._robotNetworkController.displayInformation();
        }


        /** move robot at start position and load the new genome  */
        public void resetEvaluation() {
        	
	    		this.moveToStartPosition();
	    		
	    		ArrayList myGenomeList = new ArrayList();
	    		for ( int i = 0 ; i != this._setup._genome.length ; i++ )
	    			myGenomeList.add( new Double (_setup._genome[i]) );
	    		
	    		//System.out.print(" taille : " + myGenomeList.size());
	    		//System.exit(-1);
	    		this._robotNetworkController.setAllArcsWeightValues(myGenomeList);
    		
	    		this._setup._fitness = 0;
	    		this._setup._isRunnable = true;
        }


        /** Perform one step of Agent's Behavior */
        /* -- old way
        public void performBehavior()
        {
			
        		// Computing the fitness
        		// step 1 of 2
        		// updating the fitness for the previous step and checking for collision
			if ( collisionDetected() == true )
			{
				_setup._fitness = - 1000;  
				//Display.info("collision!");
				this._setup._isRunnable = false; // if collision : stop simulation session
				return;
			}
			else 
				_setup._fitness = _setup._fitness + 1;
			
			
			//every 20 frames
			if (getCounter()%20==0){

				// * reads the sonar values
				
				double[] inputValue = new double[4];
			
			    // inputValue[0] = ( sonars.getMeasurement (0) + sonars.getMeasurement (1) + sonars.getMeasurement (11) + sonars.getMeasurement (10) ) / 4;
				// inputValue[1] = ( sonars.getMeasurement (2) + sonars.getMeasurement (3) ) / 2;
				// inputValue[2] = ( sonars.getMeasurement (8) + sonars.getMeasurement (9) ) / 2;
				// inputValue[3] = ( sonars.getMeasurement (5) + sonars.getMeasurement (6) ) / 2;
			
				inputValue[0] = sonars.getFrontQuadrantMeasurement();
				inputValue[1] = sonars.getFrontRightQuadrantMeasurement();
				inputValue[2] = sonars.getFrontLeftQuadrantMeasurement();
				inputValue[3] = 0;

				for ( int i = 0 ; i != 4 ; i++ )
				{
					if ( inputValue[i] == Double.POSITIVE_INFINITY)
					{
						inputValue [i] = 1.5;
					}
					//System.out.println(" quadrantSonar("+i+") = "+inputValue[i]);
				}
				
			    
				// * load the new input values into the controller
				_inputControllerValuesList.clear();
				for ( int i = 0 ; i != 4 ; i++ )
				{
					_inputControllerValuesList.add( new Double(inputValue[i]) );
				}			
				this._robotNetworkController.computeOutput(_inputControllerValuesList);
				//this._robotNetworkController.displayInformation();
				
				// * perform locomotion step
				double speed = _robotNetworkController.getOutputNeuronAt(0).getNeuronValue();
				double angle = _robotNetworkController.getOutputNeuronAt(1).getNeuronValue();
				setTranslationalVelocity( speed );
				setRotationalVelocity( angle );
				Point3d coord = new Point3d();
				this.getCoords(coord);
				//System.out.println("[time:"+ getCounter() + "] " + "( " + coord.x + " , " + coord.y + " ) ; " + "commande ( "+ _robotNetworkController.getOutputNeuronAt(0).getNeuronValue() + " , " + vitesse + " )");

	        		// Computing the fitness
	        		// step 2 of 2
	        		// penalize if not moving
				if ( speed == 0 )
					_setup._fitness = _setup._fitness - 1;
				else 
					_setup._fitness = _setup._fitness + speed ;

			}
        }*/
        
        public void performBehavior()
        {
			
        		// Computing the fitness
        		// step 1 of 2
        		// updating the fitness for the previous step and checking for collision
			if ( collisionDetected() == true )
			{
				_setup._fitness = 0;
				//Display.info("collision!");
				this._setup._isRunnable = false; // if collision : stop simulation session
				return;
			}
			//else 
			//	_setup._fitness = _setup._fitness + 1;
			
			
			//every 20 frames
			if (getCounter()%20==0){

				// * reads the sonar values
				
				double[] inputValue = new double[4];
			
			    // inputValue[0] = ( sonars.getMeasurement (0) + sonars.getMeasurement (1) + sonars.getMeasurement (11) + sonars.getMeasurement (10) ) / 4;
				// inputValue[1] = ( sonars.getMeasurement (2) + sonars.getMeasurement (3) ) / 2;
				// inputValue[2] = ( sonars.getMeasurement (8) + sonars.getMeasurement (9) ) / 2;
				// inputValue[3] = ( sonars.getMeasurement (5) + sonars.getMeasurement (6) ) / 2;
			
				inputValue[0] = sonars.getFrontQuadrantMeasurement();
				inputValue[1] = sonars.getFrontRightQuadrantMeasurement();
				inputValue[2] = sonars.getFrontLeftQuadrantMeasurement();
				inputValue[3] = 0;

				for ( int i = 0 ; i != 4 ; i++ )
				{
					if ( inputValue[i] == Double.POSITIVE_INFINITY)
					{
						inputValue [i] = 1.5;
					}
					//System.out.println(" quadrantSonar("+i+") = "+inputValue[i]);
				}
				
			    
				// * load the new input values into the controller
				_inputControllerValuesList.clear();
				for ( int i = 0 ; i != 4 ; i++ )
				{
					_inputControllerValuesList.add( new Double(inputValue[i]) );
				}			
				this._robotNetworkController.step(_inputControllerValuesList);
				//this._robotNetworkController.displayInformation();
				
				// * perform locomotion step
				double speed = _robotNetworkController.getOutputNeuronAt(0).getOutputValue();
				double angle = _robotNetworkController.getOutputNeuronAt(1).getOutputValue();
				setTranslationalVelocity( speed );
				setRotationalVelocity( angle );
				Point3d coord = new Point3d();
				this.getCoords(coord);
				//System.out.println("[time:"+ getCounter() + "] " + "( " + coord.x + " , " + coord.y + " ) ; " + "commande ( "+ _robotNetworkController.getOutputNeuronAt(0).getNeuronValue() + " , " + vitesse + " )");

        		// Computing the fitness
        		// step 2 of 2
        		// penalize if not moving
				double maxSensorValue = 0;
				for ( int i = 0 ; i != 4 ; i++ )
					if ( maxSensorValue < inputValue[i] )
						maxSensorValue = inputValue[i];
				_setup._fitness += speed + ( 1 - angle ) + ( 1 - maxSensorValue ) ; // adapted classic fitness from Nolfi&Floreano2000EvolutionaryRobotics, p73-74  
			}
        }
    }


    double computeRobotFitnessWithLightProcessing ( Simbatch sim, double genome[] )
    {
    		this._myRobot.resetEvaluation ();
    		
	    int i = 0;
	    while ( i < 10000 && _isRunnable == true ) 
	    {
	        sim.step();
	        i++;
	    }
	    
	    Display.info("fitness is " + _fitness + "");
	    
  	    return (_fitness);
	}

    static double computeRobotFitnessTest ( double genome[] )
    {
		
		Evaluator_SimpleAvoiderRobot ers = new Evaluator_SimpleAvoiderRobot();
		
		Simbatch sim = new Simbatch(ers,true);
	    sim.reset();
	    
	    int i = 0;
	    while ( i < 10000 && ers._isRunnable == true ) 
	    {
	    		try {Thread.sleep(10); } catch(Exception e){}  // [!n] hack : make it visible...
	    		sim.step();
	        i++;
	    }
	    
	    Display.info("fitness is " + ers._fitness + "");
	    sim.dispose();
	    
	    return (ers._fitness);
	}

    public static void main(String[] args) {

    		// Voici un exemple de genome qui ne marche pas juste au cas ou tu veuilles lancer uniquement un run.
    		// sinon, pour une evolution, regarde le fichier CommentLancerUneEvolution.txt
    	
		double genome[] = { -0.5687399872869805 , -0.9554699688080246 , 0.4772563411155608 , -0.7202728546127957 , 0.4119730929168963 , -0.8068253131237191 , -0.3643513176764104 , 0.8781821410043917 , -0.23512862677415036 , -0.7041303862298087 , -0.4113173429099277 , -0.7317029043160093 , 0.3012876529788864 , 0.22053091556991355 , -0.906677183737588 , -0.7316144958520516 , 0.14735554569250597 , 0.18971519217624322 , -0.35501010222316576 , -0.09035628814973728 , 0.12053614616318664 , -0.46431027781496215 , -0.979288920263091 , 0.9754937292326014 , 0.8142841918296084 , 0.245554205850127, -0.46431027781496215 , -0.979288920263091 , 0.9754937292326014 , 0.8142841918296084 , 0.245554205850127 };
		/*
		double genome [27];
		for ( int i = 0 ; i != genome.length ; i++ )
			genome[i] = Math.random() * 2 - 1;*/
		for ( int i = 0 ; i != 10 ; i++ )
			computeRobotFitnessTest(genome);
    }
}
