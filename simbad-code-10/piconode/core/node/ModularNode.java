package piconode.core.node;

public abstract class ModularNode extends ConnectedNode {

	private boolean _updated = false; // a reporter dans valueContainer
	
	public boolean isUpdated() {
		return _updated;
	}
	public void setUpdated(boolean _updated) {
		this._updated = _updated;
	}

	abstract public double getOutputRegister( int index );
	abstract public void setInputRegister( int index, double value );
	
	abstract public void stepModule();
	
}
