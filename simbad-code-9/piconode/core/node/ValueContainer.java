package piconode.core.node;

import piconode.XmlRepresentation;
import piconode.core.arc.ModularArc;
import piconode.toolbox.Display;

/**
 * a ValueContainer object contains a simple double value and may be linked from and to other 
 * objects. The motiation for this object is to provide a generic framework for linking nodes
 * in a network. see step() methods in corresponding objects.
 */
public class ValueContainer extends ModularNode implements XmlRepresentation {

	private double _value; // input and output registers are identical (i.e. "_value")
	
	public ValueContainer ()
	{
		super();
		this.setName("valueContainer");
	}
	
	public void displayInformation() {
		Display.info("ValueContainer Object : \""+this.getName()+"\" - value is : " + this.getValue());

	}

	/**
	 * not applicable for this object. (future implementation : decay possible?)
	 */
	public void step() {
		Display.error(this.getName()+" : cannot perfom step on a ValueContainer object");
		System.exit(-1);
	}
	
	public void setValue ( double __value )
	{
		_value = __value;
	}
	
	public double getValue ()
	{
		return _value;
	}
	
	public double getOutputRegister( int index ) {
		if ( index != 0 )
			Display.critical("ValueContainer::getOutputRegister(.) - index must be 0");
		return _value;
	}

	public void setInputRegister( int index, double value ) {
		if ( index != 0 )
			Display.critical("ValueContainer::setInputRegister(.) - index must be 0");
		_value = value;
	}
	
	/**
	 * perform one step on this module, i.e. in this special case, calls successors *only*
	 * Warning : ValueContainer does not bother with backward dependencies and thus should
	 * be explicitly called only if it is an entry point. 
	 */
	public void stepModule ()
	{
		if ( this.isUpdated() == true ) 
			return; // nothing to do.
		else
			this.setUpdated(true); // this node is updated
		
		// calls successors -- sub-optimal : N^2 calls in the worst case cmp to N for a blackboard approach (but worst case is *RARE* -- indeed best case should be always if call is made in a straight forward fashion, i.e. starting entry point of the input signal)
		for ( int i = 0 ; i != this.getOutgoingArcsListSize() ; i++ )
			((Module)((ModularArc)this.getOutgoingArcAt(i)).getTarget()).stepModule();
		
	}
	
	public void resetUpdate()
	{
		this.setUpdated(false);
		// call successors
		for ( int i = 0 ; i != this.getOutgoingArcsListSize() ; i++ )
			((ValueContainer)((ModularArc)this.getOutgoingArcAt(i)).getTarget()).resetUpdate(); // changer le "Module"
		
		// dont forget to reset embedded modules if any
		// ...
	}

}
