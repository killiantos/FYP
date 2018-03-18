/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 30 nov. 2005
 * 
 * Using this class static methods instead of System.[out|err].println(...) should make it simpler to fine tune desired display messages.
 * 
 */

package picoevo.toolbox;

public class Display {

	static boolean _debug = true;
	static boolean _info = true;
	static boolean _warning = true;
	static boolean _error = true;
	static boolean _critical = true;
	static boolean _stopOnError = true;
	static boolean _stopOnCritical = true; // always stop - only situation where it can be "false": interactive scripting

	static boolean _silence = false;
	static boolean _memorycheck = true;
	
	static public void debug (String __msg)
	{
		if ( _debug == true && _silence == false )
			System.err.println("[DEBUG] " + __msg);
	}
	static public void info (String __msg)
	{
		if ( _info == true && _silence == false )
			System.out.println(__msg);
	}
	static public void info_nocr (String __msg)
	{
		if ( _info == true && _silence == false )
			System.out.print(__msg);
	}
	static public void warning (String __msg)
	{
		if ( _warning == true && _silence == false )
			System.err.println("[WARNING] " + __msg);
	}
	static public void error (String __msg)
	{
		if ( _error == true )
		{
			System.err.println("[ERROR] " + __msg);
			if ( _stopOnError == true )
				System.exit(-1);
		}
	}
	static public void critical (String __msg)
	{
		//if ( _debug == true )
		{
			System.err.println("[STOP] " + __msg);
			if ( _stopOnCritical == true )
				System.exit(-2);
		}
	}
	
	static public void memorycheck()
	{
        //Runtime.getRuntime().gc();
        if ( _silence == false && _memorycheck == true)
        		Display.info(
        		"Memory heap total: "+Runtime.getRuntime().totalMemory()/1024+
                "k  max: "+Runtime.getRuntime().maxMemory()/1024+
                "k  free: "+Runtime.getRuntime().freeMemory()/1024+"k");
	}
	
}
