package picoevo.toolbox;

/** This class contains static constants that defines values for specific terms in human
 * readable form (e.g. "maximisation"/"minimization" instead of true/false). User should 
 * refer to this class whenever cryptic parameter are required */
public final class Dictionary {
	
	// optimisation method
	public static boolean Minimisation = false;
	public static boolean Maximisation = true;
	
	// growing trees method for Tree GP
	public static boolean fullMethod = true;
	public static boolean growMethod = false;
}
