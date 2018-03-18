/*
 * Created on 15 may 2006
 * Author : bredeche(at)lri(point)fr
 */

package piconode.core.node;

import java.util.ArrayList;

import piconode.core.arc.*;
import piconode.core.operator.*;
import piconode.toolbox.*;


/** 
 * This class implement a simple 2D "Kohonen" Self Organizing Map. see tutorials. 
 */
public class SelfOrganizingMap extends NeuralNetwork {

	private double _mapRadius;
	private double _lambdaTimeConstant = 30; // not used // ?? loosely hand-tune. better use alternate method (see in step() method)
	
	private double _startLearningRate = 1;
	public double _learningRate = 0.9;
	
	private int _steps = 0;
	private int _totalSteps = 100000;

	private int _BMUindex = -1;
	private int _dx = 0;
	private int _dy = 0;
	
    /**
     * create a new network (no bias neuron for SOM).
     */
	public SelfOrganizingMap ( ) 
	{
		_containsBiasNeuron = false;
		_learningRate = _startLearningRate;
	}
	
    /**
     * Perform initialization of the network. Should be called only once after all neurons and 
     * arcs are registered and input and ouput neurons are identified.
     */
    public void initNetwork ()
    {
    		Misc.notImplemented(this,"initNetwork() - not implemented -- you must call the parameterized initNetwork(.,.,.,.,.) method");
    		System.exit(-1);
    }

    /**
     * Perform the full creation and initialization of the network using the parameters and 
     * set the radius-related parameters (size of BMU neighbourhood to be updated and decreasing 
     * factor for the radius over time).
     * note : there are __dx*__dy output neurons  
     */
    public void initNetwork ( int __inputNb, int __dx, int __dy, double __startLearningRate, int __totalSteps )
    {
        // initialisation
            
        this._startLearningRate = __startLearningRate;
    		if ( __dx > __dy )
    			this.setMapRadius(__dx/2);
    		else
    			this.setMapRadius(__dy/2);
    		this._totalSteps = __totalSteps;
    		this._dx = __dx;
    		this._dy = __dy;

    		for ( int i = 0 ; i != __inputNb ; i++ )
    			this.registerInputNeuron( new NeuronalNode(this) );
    		
    		for ( int i = 0 ; i != _dx*_dy ; i++ )
    		{
    			SOMoutputNeuron neuron = new SOMoutputNeuron(this);
    			int [] coord = new int[2];
    			coord[0] = i%_dx;
    			coord[1] = i/_dx;
    			neuron.setCoordinates(coord);
    			this.registerOutputNeuron( neuron );
    			for ( int j = 0 ; j != __inputNb ; j++ )
    				this.registerArc( new WeightedArc ( (NeuronalNode)this._inputNeuronsList.get(j) , neuron , Math.random() ) );
    		}
    		

            setOldOutputRegisterSize(this._outputNeuronsList.size());
            setOutputRegisterSize(this._outputNeuronsList.size());
            setInputRegisterSize(this._inputNeuronsList.size());
    		
    		// finished.
    		
    		this._init = true;
    }



    /**
     * compute output values from input values (i.e. f(in)=out).
     * @param __inputValuesList
     */
    public void step( double[] __inputValues )
    {

        	// * Begin Self-Organize

        	// STEP 1 : locate Best Matching Unit ("BMU") 
        	
    		this.findBMU(__inputValues);
        	//Display.debug("BMU is "+_BMUindex);
        	
        	// STEP 2 : compute and update the neighbourhood radius
        	
    		double neighbourgRadius = this._mapRadius * ( ( (double)this._totalSteps - (double)this._steps ) / (double)this._totalSteps )  ; // ver.0 (niko) / straight-forward and efficient / (default/advised) 
        //double neighbourgRadius = 1.0 + ( this._mapRadius - 1.0 ) * ( ( (double)this._totalSteps / (double)this._steps ) / (double)this._totalSteps )  ; // ver.1 (src: louis) / very efficient 
        //double neighbourgRadius = this._mapRadius * Math.exp( - (double)_steps / _lambdaTimeConstant ); // ver.2 (src: aigeek) / theshold hard to set - variability in the results - WARNING : dont forget to set lambda if you use this method
        //double neighbourgRadius = (double)this._mapRadius * ( 0.5 - 0.5  * ( (double)this._steps / (double)this._totalSteps ) ) ; // ver.3 : simple decay... not that efficient (only in the last steps)

        double widthSquare = neighbourgRadius * neighbourgRadius;
        	
        	// STEP 3 : update BMU *and* neighbouring nodes included in the neighbourhood radius
        
        if ( _learningRate > 0.1 ) // learning step 1 : learning rate is high, BMU and neighbours are updated.
        {
	        	for ( int i = 0 ; i != _outputNeuronsList.size() ; i++ )
	        	{
	        		// !n-devnotes : possible optimisation since sqrt(square(x)) = x (see also in SOMoutputNeuron.computeDistanceOnMap
	        		double distanceToSquare = ((SOMoutputNeuron)_outputNeuronsList.get(i)).computeDistanceOnMap((SOMoutputNeuron)_outputNeuronsList.get(_BMUindex));
	        		distanceToSquare *= distanceToSquare;
	        		if ( distanceToSquare < widthSquare ) // is node inside updating radius?
	        		{ 
	        			double influence = Math.exp(-(distanceToSquare) / (2*widthSquare));
	        			((SOMoutputNeuron)_outputNeuronsList.get(i)).adjustWeights(__inputValues,_learningRate,influence);
	        		}
	        	}
        }
        else // learning step 2 : learning rate is below 0.1, only BMU is updated.
        {
        		((SOMoutputNeuron)_outputNeuronsList.get(_BMUindex)).adjustWeights(__inputValues,_learningRate,1);
        	}
        	
        	
        	// STEP 4 : decrease learning rate and increase nb of steps performed

        _learningRate = _startLearningRate * ( 1 - (double)_steps / (double)_totalSteps ); // ver.1 (default/advised) - simple but sure that 0 is reached
        //_learningRate = _startLearningRate * Math.exp(-(double)_steps/(double)_totalSteps); // ver.2 - clever but do not reach zero (i.e. learning step 2 is never performed)
        	_steps++;
        	
        	//step 5 recopy actual output values as old output values
        	
        	for (int i=0;i<getOutputRegisterSize();i++)
        		setOldOutputRegister(i,getOutputRegister(i));
			
    }
    
   /**
    * perform one iteration step using input data from incoming nodes if any -- arity must be correct
    * @param __valuesList
    */
    public void step() // TODO !!
    {
    	/*
    		double[] array = new double [this.getIncomingArcsListSize()];
    		for ( int i = 0 ; i != this.getIncomingArcsListSize() ; i++ )
    			array[i] = (this.getIncomingArcAt().getSource()).....	
    	*/
    		Misc.notImplemented(this,"step");
    		System.exit(-1);
    }

    /**
     * compute output values from input values (i.e. f(in)=out).
     * @param __inputValuesList
     */
    public void step( ArrayList __inputValues ) // TODO
    {
		Misc.notImplemented(this,"step");
		System.exit(-1);
    }

    /**
     * find the best matching unit ("BMU") wrt. input values
     * @param __inputValues
     */
	public int findBMU( double[] __inputValues )
	{   	
		// * setting up
    	
        	if ( _init == false )
        	{
        	    System.out.println("SelfOrganizingMap::step(-) - network not initialized!");
        	    System.exit(-1);
        	}
     
        	if ( __inputValues.length != _inputNeuronsList.size() )
        	{
        	    System.out.println("SelfOrganizingMap::step(-) - input values mismatch");
        	    System.exit(-1);
        	}
        	
        	// copy input values into input nodes
        	for ( int i = 0 ; i != __inputValues.length ; i++ )
        	{
        		if ( __inputValues[i] < 0 || __inputValues[i] > 1 )
        		{
        			Display.critical("input values *must* be normalized btw 0 and 1");
        		}
        	    ((NeuronalNode)_inputNeuronsList.get(i)).setInputValue(__inputValues[i]);
        	}

        	// * locate Best Matching Unit ("BMU")
        	
        	double BMUvalue = 1.1; // all awaited values are <=1.  
        	for ( int i = 0 ; i != _outputNeuronsList.size() ; i++ )
        	{
        	    ((NeuronalNode)_outputNeuronsList.get(i)).step(); // compute similarity
        	    if ( ((SOMoutputNeuron)_outputNeuronsList.get(i)).getOutputValue() < BMUvalue ) // check BMU
        	    {
        	    		BMUvalue = ((SOMoutputNeuron)_outputNeuronsList.get(i)).getOutputValue();
        	    		_BMUindex = i;
        	    }
        	}
        	
        	return _BMUindex;
    }
    
    /**
     * A Self Organizing Map contains only input and output nodes.
     */
    public void registerNode()
    {
    		Display.error("Self Organizing Map network can only register input or output node");
    }

	public double getMapRadius() {
		return _mapRadius;
	}

	public void setMapRadius(double __mapRadius) {
		_mapRadius = __mapRadius;
	}
	
	/**
	 * return the last Best Matching Unit ("BMU") found
	 * Note : this method does not iterate the SOM! -- see findBMU(intputValues) method for iteration
	 * This method may be useful for debug or demo purpose (e.g. perform a learning step and then check which was the BMU)
	 * @return
	 */
	public int getBMU() { return this._BMUindex; }
	
	public int getTotalSteps() { return this._totalSteps; }

	// not used
	public void setLambdaTimeConstant( double __lambda )
	{
		this._lambdaTimeConstant = __lambda;
	}
	
	// not used
	public double getLambdaTimeConstant ()
	{
		return this._lambdaTimeConstant;
	}
	
	public double[] getOutputNeuronValues ( int __x, int __y )
	{
		return ((SOMoutputNeuron)this.getOutputNeuronAt(__x+__y*this._dx)).getWeights();
	}
	
	public SOMoutputNeuron getOutputNeuronAt ( int __x, int __y )
	{
		return (SOMoutputNeuron)this.getOutputNeuronAt(__x+__y*this._dx);
	}
	
	/**
	 * return the number of inputs -- symply calls with the getInputNeuronListSize method ( methods are synonyms )
	 * @return
	 */
	public int getInputSize()
	{
		return this.getInputNeuronListSize();
	}
	
	/**
	 * Quick test -- for a more "illustrative" test, please refer to Tutorial_5
	 * @param args
	 */
	public static void main(String[] args) {

        double startTime = System.currentTimeMillis();
        System.out.println("Running...");        

        SelfOrganizingMap map = new SelfOrganizingMap();
        map.initNetwork(3,5,5,1,10001);
        
        int log[] = new int[5*5];
        
        for ( int i = 0 ; i != map.getTotalSteps() ; i++ )
        {	
        		if ( i%1000 == 0 )
        			Display.info("iterations : " + i);
	        	double[] array = new double[3];
	        	for ( int j = 0 ; j != 3 ; j++ )
	        		array[j] = Math.random();
	        	map.step(array);
	        	if ( i > map.getTotalSteps()/2 ) log[map.getBMU()]++;
        }

        Display.info("Summary:");
        for ( int i = 0 ; i != 5*5 ; i++ )
        {
        		Display.info("node n." + i + " : " + log[i]);
        }
        
        
        
        System.out.println("\nTerminated ("+ ((System.currentTimeMillis()-startTime)/1000) +"s elapsed).");
	}

	
}

