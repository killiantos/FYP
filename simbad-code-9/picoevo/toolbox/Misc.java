/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 15 janv. 2006
 * 
 */

package picoevo.toolbox;

import java.util.Calendar;

public class Misc {
	static public String getCurrentTimeAsCompactString ()
	{
		String string = new String();

		Calendar now = Calendar.getInstance();
				
		int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1; 
        int day = now.get(Calendar.DAY_OF_MONTH); 
        int hour = now.get(Calendar.HOUR_OF_DAY);  
        int minute = now.get(Calendar.MINUTE);
        int second = now.get(Calendar.SECOND);
        
        string = ""+year;
        if ( month < 10 ) string += "0";
        string += month;
        if ( day < 10 ) string += "0";
        string += day;
        string += "_";
        if ( hour < 10 ) string += "0";
        string += hour+"h";
        if ( minute < 10 ) string += "0";
        string += minute+"m";
        if ( second < 10 ) string += "0";
        string += second+"s";
        
        return string;
	}
	
	public static void notImplemented()
	{
		Display.warning("Method not implemented.");
	}

	public static void notImplemented( String __target)
	{
		Display.warning(""+__target + " - Method not implemented.");
	}

	public static void notImplemented(Object __o)
	{
		Display.warning(""+__o.getClass().getName()+" - not implemented.");
	}

	public static void notImplemented(Object __o, String __target )
	{
		Display.warning(""+__o.getClass().getName()+"::"+__target+" - Method not implemented.");
	}
}
