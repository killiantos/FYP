package piconode.core.arc;

import piconode.toolbox.*;
import piconode.core.node.*;
import piconode.core.operator.*;

/*
 * Created on 29 may 2006
 * bredeche(at)lri(point)fr
 * 
 * Note: The ModularArc class is a general purpose class for modelling Arcs
 * between Modular Nodes (i.e. nodes with embedded registers) for complex
 * heterogeneous topologies.
 *
 */

public class ModularArc extends Arc {

    /* ### Data ### */
    
    protected		int _inputRegIndex;
    
    /*
     * Constructors
     */

    public ModularArc ()
    {
    	super();
    }

    
    public ModularArc (Node __source, Node __target)
    {
    	super (__source,__target);
    	this._inputRegIndex = 0;

        //_source.addOutgoingArc(this);
        //_target.addIncomingArc(this);
    }

    public ModularArc (Node __source, int __inIndex, Node __target )
    {
    	super (__source,__target);
    	this._inputRegIndex = __inIndex;

        //_source.addOutgoingArc(this);
        //_target.addIncomingArc(this);
    }

    /*
     * Methods
     */
    
    public Node getSource()
    {
        return (_source);
    }

    public Node getTarget()
    {
        return (_target);
    }


	public int getInputRegIndex() {
		return _inputRegIndex;
	}


	public void setInputRegIndex(int regIndex) {
		_inputRegIndex = regIndex;
	}


	public double getSourceOutputRegisterValue (){
		
		if ( !((Module)this._source).isUpdated())
			((Module)this._source).stepModule();
		return  ((Module)this._source).getOldOutputRegister(this._inputRegIndex);
	}
}
