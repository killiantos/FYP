package picoevo.app.SymbolicRegression;

import java.util.ArrayList;

import picoevo.core.*;
import picoevo.core.evolution.*;
import picoevo.core.representation.*;
import picoevo.ext.*;
import picoevo.ext.evolution.ParameterSet_Evolution_mulambdaES;
import picoevo.ext.representation.*;
import picoevo.toolbox.*;


public class InitialisationOperator_Element_Node_TreeGP extends InitialisationOperator_Element {

	// ### Data ###
	
	// ### Constructors ###
	
	public InitialisationOperator_Element_Node_TreeGP () 
	{
		super();
	}
	
	public InitialisationOperator_Element_Node_TreeGP (String __name) 
	{ 
		super(__name); 
	}
	
	// ### Methods ###

	public void initialise( Element __o , int __maxDepth, boolean __fullInit ) 
	{
		this.initialise(__o,__maxDepth,false);
	}

	/**
	 * initialise a new element and launch initialisation for successor until __maxDepth is <= 1.
	 * if __maxDepth value is 1, create a terminal element (note: in fact, any value <=1 create a terminal).
	 */
	public void initialise( Element __o , int __maxDepth, boolean __fullInit, boolean __firstNode)
	{
		//Misc.notImplemented(this);

		//try {
		
			//Display.debug("+ " + __maxDepth);
		
			Element_Node_TreeGP element = (Element_Node_TreeGP)__o;
			
			if ( element.getArity() < 0 ) 
			{
				Display.error(""+this.getClass().getName()+"::initialise - arity not set");
			}
			
			// TODO :
			//  1. mutationRates differ according to node type (i.e. mutationRate_...node|node_double)
			//  2. stopping criteria
			//  3. memoryModule (x value)
			
			if ( element.getClass() == Element_Node_TreeGP_EphemeralConstant.class )
				((Element_Node_TreeGP_EphemeralConstant)element).setValue (Math.random());
			
			//element.addOperator( new VariationOperator_Individual_TreeGP_Mutation("mutation", ((ParameterSet_Evolution_mulambdaES)element.getTemplate()).getMutationRate()) );
			
			for ( int i = 0 ; i != element.getArity() ; i++ )
			{
				double dice = Math.random();
				if ( __maxDepth > 1 )
				{
					double Pterminal;
					double nbTerminals = 1; //2
					double nbOperators = 8;
					if ( __fullInit == true || __firstNode == true)
						Pterminal = 0.0; // FULL : until max depth is reached ... or ... first node of tree : (koza says:) "cannot be a terminal" 
					else
						Pterminal = (nbTerminals)/(nbOperators+nbTerminals); // GROW : until max but may stop anytime
					
					// random choice of a terminal
					//if ( dice < Pterminal/2.0 )
					if ( dice < Pterminal/2.0 )
					{
						Element_Node_TreeGP_Variable newelement = new Element_Node_TreeGP_Variable(element.getOwner(), new ArrayList () );
						element.addSuccessor(newelement);
						newelement.addPredecessor(element);
						newelement.performInitialisation();
					}
					else
						if ( dice < Pterminal )
						{
							Element_Node_TreeGP_EphemeralConstant newelement = new Element_Node_TreeGP_EphemeralConstant(element.getOwner(), new ArrayList () );
							newelement.addOperator( new VariationOperator_Element_TreeGP_EphemeralConstantMutation("constant.mutation") );
							element.addSuccessor(newelement);
							newelement.addPredecessor(element);
							newelement.performInitialisation();
						}
						else
						{
							dice = Math.random();

							if ( dice < 1.0/nbOperators )
							{
								Element_Node_TreeGP_Operator_Plus newelement = new Element_Node_TreeGP_Operator_Plus(element.getOwner(), new ArrayList () );
								element.addSuccessor(newelement);
								newelement.addPredecessor(element);
								newelement.performInitialisation(__maxDepth-1, __fullInit, false);
							}
							else
								if ( dice < 2.0/nbOperators )
								{
									Element_Node_TreeGP_Operator_Multiply newelement = new Element_Node_TreeGP_Operator_Multiply(element.getOwner(), new ArrayList () );
									element.addSuccessor(newelement);
									newelement.addPredecessor(element);
									newelement.performInitialisation(__maxDepth-1, __fullInit, false);
								}
								else
									if ( dice < 3.0/nbOperators )
									{
										Element_Node_TreeGP_Operator_Divide newelement = new Element_Node_TreeGP_Operator_Divide(element.getOwner(), new ArrayList () );
										element.addSuccessor(newelement);
										newelement.addPredecessor(element);
										newelement.performInitialisation(__maxDepth-1, __fullInit, false);
									}
									else
										if ( dice < 4.0/nbOperators )
										{
											Element_Node_TreeGP_Operator_Minus newelement = new Element_Node_TreeGP_Operator_Minus(element.getOwner(), new ArrayList () );
											element.addSuccessor(newelement);
											newelement.addPredecessor(element);
											newelement.performInitialisation(__maxDepth-1, __fullInit, false);
										}
										else
											if ( dice < 5.0/nbOperators )
											{
												Element_Node_TreeGP_Operator_exp newelement = new Element_Node_TreeGP_Operator_exp(element.getOwner(), new ArrayList () );
												element.addSuccessor(newelement);
												newelement.addPredecessor(element);
												newelement.performInitialisation(__maxDepth-1, __fullInit, false);
											}
											else
												if ( dice < 6.0/nbOperators )
												{
													Element_Node_TreeGP_Operator_rlog newelement = new Element_Node_TreeGP_Operator_rlog(element.getOwner(), new ArrayList () );
													element.addSuccessor(newelement);
													newelement.addPredecessor(element);
													newelement.performInitialisation(__maxDepth-1, __fullInit, false);
												}
												else
													if ( dice < 7.0/nbOperators )
													{
														Element_Node_TreeGP_Operator_sin newelement = new Element_Node_TreeGP_Operator_sin(element.getOwner(), new ArrayList () );
														element.addSuccessor(newelement);
														newelement.addPredecessor(element);
														newelement.performInitialisation(__maxDepth-1, __fullInit, false);
													}
													else
													{
														Element_Node_TreeGP_Operator_cos newelement = new Element_Node_TreeGP_Operator_cos(element.getOwner(), new ArrayList () );
														element.addSuccessor(newelement);
														newelement.addPredecessor(element);
														newelement.performInitialisation(__maxDepth-1, __fullInit, false);
													}/**/
						}
				}
				else
				{
					if ( dice < 0.5 )
					{
						Element_Node_TreeGP_Variable newelement = new Element_Node_TreeGP_Variable(element.getOwner(), new ArrayList () );
						element.addSuccessor(newelement);
						newelement.addPredecessor(element);
						newelement.performInitialisation();
					}
					else
					{
						Element_Node_TreeGP_EphemeralConstant newelement = new Element_Node_TreeGP_EphemeralConstant(element.getOwner(), new ArrayList () );
						element.addSuccessor(newelement);
						newelement.addPredecessor(element);
						newelement.performInitialisation();
					}
				}
				
			}
			
			// comment passer un parametre le long d'une initialisation?
			// 1. methode sale : on demande le param au template...
			// 2. methode pas pratique : on reecrit performInit...
			// 3. methode ...?
			
			// TODO
		//}		
		//catch ( java.lang.ClassCastException e )
		//{
		//	Display.error(""+this.getClass().getName()+"::initialise(.) - Class cast exception");
		//}
	}

	// terminal node
	public void initialise(Element __o) 
	{

		
		//Display.error(""+this.getClass().getName()+"::initialize(Element) : use initialize(Element,maxDepth) instead.");
		Element_Node_TreeGP element = (Element_Node_TreeGP)__o;

		//Display.debug("= terminal");
		
		if ( element.getArity() < 0 ) 
		{
			Display.error(""+this.getClass().getName()+"::initialise - arity not set");
		}
		
		if ( element.getClass() == Element_Node_TreeGP_EphemeralConstant.class )
			((Element_Node_TreeGP_EphemeralConstant)element).setValue (Math.random());
		
		//element.addOperator( new VariationOperator_Individual_TreeGP_Mutation("mutation", ((ParameterSet_Evolution_mulambdaES)element.getTemplate()).getMutationRate()) );

	}
}
