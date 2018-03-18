package picoevo.app.SymbolicRegression;

import java.util.ArrayList;

import picoevo.core.representation.Individual;

public abstract class Element_Node_TreeGP_Terminal extends Element_Node_TreeGP {

	public Element_Node_TreeGP_Terminal(Individual __individualOwner, ArrayList __elementLevelOperatorList) {
		super(__individualOwner, __elementLevelOperatorList);
	}
	
	public ArrayList getNonTerminalElements ( ArrayList __list )
	{
		return ( __list); // terminate
	}

	public ArrayList getTerminalElements ( ArrayList __list )
	{
		__list.add(this);
		return ( __list ); // add self and terminate
	}
	
	public ArrayList getAllElements ( ArrayList __list )
	{
		__list.add(this);
		return (__list ); // add self and terminate
	}
	
	/**
	 * 
	 * @return distance to the deepest node in the tree starting this node. This node being a terminal node, distance is zero.
	 */
	public int getDistanceToDeepestTerminal()
	{
		return (0);
	}

	/**
	 * 
	 * @return the number of nodes in the sub tree starting this node (included) 
	 * - root node does not count.
	 */
	public int getNumberOfNodes()
	{
		return (1);  // [developper's note : see also corresponding methods in Element_Node_TreeGP_Terminal and Element_Node_TreeGP]
	}

	public void trace ()
	{
	}

}
