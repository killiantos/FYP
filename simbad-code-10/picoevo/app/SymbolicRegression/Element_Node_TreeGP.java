/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 20 janv. 2006
 * 
 */

package picoevo.app.SymbolicRegression;

import java.util.ArrayList;

import picoevo.core.representation.Individual;
import picoevo.ext.representation.Element_Node;
import picoevo.toolbox.Display;
import picoevo.toolbox.Misc;

abstract public class Element_Node_TreeGP extends Element_Node {
	
	public boolean _isCloned = false;
	
	public Element_Node_TreeGP(Individual __individualOwner, ArrayList __elementLevelOperatorList) {
		super(__individualOwner, __elementLevelOperatorList);
	}
	
	/*
	public double evaluate()
	{
		Misc.notImplemented(this,"better idea to write your own evaluateMyOwnType... or use Caml :-)");
		System.exit(-1);
		return -1;
	}
	*/
	
	public double evaluateDouble ()
	{
		Misc.notImplemented(this,"do not handle double values.");
		System.exit(-1);
		return -1;
	}
	
	public boolean evaluateBoolean ()
	{
		Misc.notImplemented(this,"do not handle boolean values.");
		System.exit(-1);
		return false;
	}
	
	// if currentLength+1 = maxLength : successor is terminal
	// if current node is terminal-unary : terminate
	// if successor is N-ary, create N successors.
	//abstract public void createSuccessor( int __currentLength, int __maxLength );

	// modified : call internal super method, then go on with successors
	public void performVariations()
	{
		super.performVariations();
		for ( int i = 0 ; i != this.getSuccessors().size() ; i++ )
			((Element_Node)this.getSuccessors().get(i)).performVariations();
	}

	public void performInitialisation ( int __maxDepth, boolean __growFull , boolean __firstNode)
	{
		//try  {
			((InitialisationOperator_Element_Node_TreeGP)this.getTemplate().getElementInitialisationOperator()).initialise(this,__maxDepth, __growFull, __firstNode );
		/*}
		catch ( NullPointerException e )
		{
			Display.error(""+this.getClass().getName()+"::performInitialisation(.) - no operator available"); 
		}*/
	}

	/**
	 * Substitute this element with newElement. (1) newElement's predecessor will be that of the current element. 
	 * (2) Predecessor will now point to newElement instead of current element. Note : current element may be
	 * garbage collected after this method call (assuming no other pointer exists).
	 * @param __newElement the element that is to replace this element in the tree.
	 */
	final public void substituteWith (Element_Node_TreeGP __newElement)
	{
		((Element_Node_TreeGP)this.getPredecessors().get(0)).substituteSuccessor(this,__newElement);
		__newElement.resetPredecessorConnections();
		__newElement.addPredecessor((Element_Node_TreeGP)this.getPredecessors().get(0));
	}
	
	/**
	 * Substitute a successor with another (new) successor. Should be used only by the "substituteWith" method.
	 * @param __newElement
	 */
	final private void substituteSuccessor (Element_Node_TreeGP __oldElement, Element_Node_TreeGP __newElement)
	{
		int targetIndex = this.getSuccessors().indexOf(__oldElement);
		this.getSuccessors().add(targetIndex ,__newElement);
		this.getSuccessors().remove(__oldElement);
	}

	/**
	 * Swap two subtree starting with current and target elements. The two elements swaps Predecessors and predecessors are updated.
	 * @param __swapElement the starting node of the target subtree to swap with the subtree starting with the current element
	 */
	final public void swapSubTrees (Element_Node_TreeGP __swapElement)
	{
		((Element_Node_TreeGP)this.getPredecessors().get(0)).substituteSuccessor(this,__swapElement);
		((Element_Node_TreeGP)__swapElement.getPredecessors().get(0)).substituteSuccessor(__swapElement,this);
		Element_Node_TreeGP temp = (Element_Node_TreeGP)__swapElement.getPredecessors().get(0);
		__swapElement.resetPredecessorConnections();
		__swapElement.addPredecessor((Element_Node_TreeGP)this.getPredecessors().get(0));
		this.resetPredecessorConnections();
		this.addPredecessor(temp);
	}

	// implementation notes : might be forced to switch from protected to public (if rewriting methods in outer package is needed)
	abstract protected ArrayList getNonTerminalElements ( ArrayList __list );
	abstract protected ArrayList getTerminalElements ( ArrayList __list );
	abstract protected ArrayList getAllElements ( ArrayList __list );
		
	/**
	 * 
	 * @return all non terminal elements that can be reached from this node 
	 * note : (1) starting element included (2) root node never included
	 */
	final public ArrayList getNonTerminalElements ()
	{
		ArrayList list = new ArrayList();
		return (this.getNonTerminalElements(list));
	}

	/**
	 * 
	 * @return all terminal elements that can be reached from this node 
	 * note : (1) starting element included (if terminal)
	 */
	final public ArrayList getTerminalElements ()
	{
		ArrayList list = new ArrayList();
		return (this.getTerminalElements(list));
	}
	
	/**
	 * 
	 * @return all elements that can be reached from this node (root node never included) 
	 * note : (1) starting element included (2) root node never included
	 */
	final public ArrayList getAllElements ()
	{
		ArrayList list = new ArrayList();
		return (this.getAllElements(list));
	}
	
	/**
	 * Trace all elements from this starting element - no return value.
	 * By default, this method performs nothing except -- however, descendant classes may overwrite this method to perform user-specified code.
	 * e.g.: specific operator/terminal code for post-evaluation monitoring purpose. 
	 *
	 */
	public void trace ()
	{
		for ( int i = 0 ; i != this.getSuccessors().size() ; i++ )
			((Element_Node_TreeGP)this.getSuccessors().get(i)).trace();
	}
	
	/**
	 * clone element *and* successor elements
	 */
	public Object clone ()
	{
		Element_Node_TreeGP elementClone = null;
		elementClone = ( Element_Node_TreeGP ) (super.clone());
		elementClone.resetAllConnections();
		
		for ( int i = 0 ; i != this.getSuccessors().size() ; i++ )
		{
			Element_Node_TreeGP nextClone = (Element_Node_TreeGP)(((Element_Node_TreeGP)(this.getSuccessors().get(i))).clone());
			nextClone.addPredecessor(elementClone);
			elementClone.addSuccessor(nextClone);
		}
		
		return (elementClone);
	}
	
	/** 
	 * 
	 * @return node depth in the tree (consider distance from *root node*, i.e. no node except root node is at depth 0)
	 */
	public int getCurrentDepth ()
	{
		return (((Element_Node_TreeGP)this.getPredecessors().get(0)).getCurrentDepth()+1); // [developper's note : termination criteria can be found in Element_Node_TreeGP_Root] 
	}
	
	
	/**
	 * 
	 * @return distance to the deepest node in the tree starting this node 
	 * - if node is terminal, distance is zero.
	 * - since this a distance to the deepest node, current node is not taken into account.
	 * - consequence : calling this from root node returns tree depth.
	 */
	public int getDistanceToDeepestTerminal()
	{
		int maxRunningDepth = 0;
		for ( int i = 0 ; i != this.getSuccessors().size() ; i++ )
		{
			int runningDepth = ((Element_Node_TreeGP)this.getSuccessors().get(i)).getDistanceToDeepestTerminal();
			if ( maxRunningDepth < runningDepth )
				maxRunningDepth = runningDepth;
		}
		return (maxRunningDepth+1);  // [developper's note : see also corresponding methods in Element_Node_TreeGP_Terminal and Element_Node_TreeGP]
	}


	/**
	 * 
	 * @return the number of nodes in the sub tree starting this node (included) 
	 * - root node does not count.
	 */
	public int getNumberOfNodes()
	{
		int numberOfNodes = 0;
		for ( int i = 0 ; i != this.getSuccessors().size() ; i++ )
		{
			numberOfNodes += ((Element_Node_TreeGP)this.getSuccessors().get(i)).getNumberOfNodes();
		}
		return (numberOfNodes+1);  // [developper's note : see also corresponding methods in Element_Node_TreeGP_Terminal and Element_Node_TreeGP]
	}
	
	
	/**
	 * display Individual information - tree is displayed TPN format (Reverse Polish Notation)
	 */
	public void displayInformation ()
	{
		//Display.info_nocr(this.getDisplayValue());
		ArrayList list = this.getSuccessors();
		if ( list.size() == 0 ) // terminal
			Display.info_nocr(" "+this.getDisplayValue());
		else
			if ( list.size() == 1 ) // unary operator
			{
				Display.info_nocr(" " + this.getDisplayValue());
				((Element_Node)list.get(0)).displayInformation();
			}
			else
				if ( list.size() == 2 ) // binary operator
				{
					Display.info_nocr(" "+this.getDisplayValue());
					((Element_Node)list.get(0)).displayInformation();
					((Element_Node)list.get(1)).displayInformation();
				}
				else // n-ary operator
				{
					Display.info_nocr(" " + this.getDisplayValue());
					for ( int i = 0 ; i != list.size() ; i++ )
					{
						((Element_Node)list.get(i)).displayInformation();
					}
				}
	}

	public String getDisplayInformation ()
	{
		String s = new String();
		ArrayList list = this.getSuccessors();
		if ( list.size() == 0 ) // terminal
			s+=" "+this.getDisplayValue();
		else
			if ( list.size() == 1 ) // unary operator
			{
				s+=" " + this.getDisplayValue();
				s+=((Element_Node_TreeGP)list.get(0)).getDisplayInformation();
			}
			else
				if ( list.size() == 2 ) // binary operator
				{
					s+=" "+this.getDisplayValue();
					s+=((Element_Node_TreeGP)list.get(0)).getDisplayInformation();
					s+=((Element_Node_TreeGP)list.get(1)).getDisplayInformation();
				}
				else // n-ary operator
				{
					s+=" " + this.getDisplayValue();
					for ( int i = 0 ; i != list.size() ; i++ )
					{
						s+=((Element_Node_TreeGP)list.get(i)).getDisplayInformation();
					}
				}
		return s;
	}
}
