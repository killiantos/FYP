/*
 * Created on 15 may 2006
 * Author : bredeche(at)lri(point)fr
 */

package piconode.core.node;

import java.util.ArrayList;

import piconode.XmlRepresentation;
import piconode.core.arc.WeightedArc;
import piconode.toolbox.Display;

/** !n : [major optimisation issue] this class may be suppressed and re-implemented as arrays in the SelfOrganizingMap class
 */
public class SOMoutputNeuron extends NeuronalNode implements XmlRepresentation {
	
	private int[] _coordinates;  

	// in this class context, neuronValue is the "distance" between last inputs and this output neuron
	
	public SOMoutputNeuron(NeuralNetwork __network) {
		super(__network);
	}

	public SOMoutputNeuron(NeuralNetwork __network, String __name) {
		super(__network, __name);
	}

	public void setCoordinates ( int [] __coordinates )
	{
		_coordinates = new int [__coordinates.length]; 
		for ( int i = 0 ; i != __coordinates.length ; i++ )
			_coordinates[i] = __coordinates[i]; 
	}
	
	// compute euclidian distance between this output neuron and input neuron values
	public void step ()
	{
		double target[] = new double[this.getIncomingArcsListSize()];
		for ( int i = 0 ; i != this.getIncomingArcsListSize() ; i++ )
			target[i] = ((WeightedArc)this.getIncomingArcAt(i)).getSourceNeuron().getOutputValue();
		this._neuronValue = this.computeDistanceToTarget(target); 
	}

	// compute euclidian distance between this output neuron and another output neuron on the 2D map
	public double computeDistanceOnMap ( SOMoutputNeuron __targetNeuron )
	{
		double distance = 0;
		for ( int i = 0 ; i != this._coordinates.length ; i++ )
			distance += Math.pow( this._coordinates[i] - __targetNeuron._coordinates[i], 2 ) ; 
		return Math.sqrt(distance);
	}

	// compute euclidian distance between current neuron and target values
	public double computeDistanceToTarget( double[] __target )
	{
		if ( __target.length != this.getIncomingArcsListSize() )
			Display.critical("SOMoutputNeuron::computeDistanceToTarget(.) - dimensions do not match");
		
		// compute distance btw output neuron and target
		double distance = 0;
		for ( int i = 0 ; i != __target.length ; i++ )
		{
			distance += Math.pow( ((WeightedArc)this.getIncomingArcAt(i)).getWeightValue() - __target[i] , 2 );
		}

		return Math.sqrt(distance); // Note: the sqrt is useless when looking for BMU... possible optimisation.
	}
	
	public void adjustWeights ( double[] __targetValues, double _learningRate, double _influence)
	{
		if ( __targetValues.length != this.getIncomingArcsListSize() )
			Display.critical("SOMoutputNeuron::adjustWeights(.) - dimensions do not match");

		// update weights
		for ( int i = 0 ; i != this.getIncomingArcsListSize() ; i++ )
		{
			double value = ((WeightedArc)this.getIncomingArcAt(i)).getWeightValue();
			value = value + _influence * _learningRate * ( __targetValues[i] - value);
			((WeightedArc)this.getIncomingArcAt(i)).setWeightValue(value);
		}
	}

	public double[] getWeights ()
	{
		double array[] = new double[this.getIncomingArcsListSize()]; 
		for ( int i = 0 ; i != this.getIncomingArcsListSize() ; i++ )
			array[i] = ((WeightedArc)this.getIncomingArcAt(i)).getWeightValue();
		return array;
	}
	
	  /**
     * @author Thomas Darde
     * The xml is defined by piconode.visual
     * @return an Xml representation of this object
     */
    public String toXml(){
    		String xmlString;
    		xmlString="<node>";
    		xmlString+="<name at=\"" + this.getName() +"\"/>"
    			+ "<id at=\"" + this.hashCode() +"\"/>";
    		if (isNetworkInput())
    			xmlString+="<type at=\"input\"/>";
    		else if (isNetworkOutput())
    			xmlString+="<type at=\"output\"/>";
    		else xmlString+="<type at=\"hidden\"/>";
    		xmlString+= "</node>";
    		return xmlString;
    }
}
