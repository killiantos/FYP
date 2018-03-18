/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 15 fev. 2006
 * 
 * 
 */

package piconode.toolbox;

public class Version {

	static String _versionInfo = "PicoNode";
	static String _versionNum = "1.99beta2";
	static String _versionBuild = "200602250051";
	
	static public void displayCurrentReleaseInformation()
	{
		Display.info("\n# " + _versionInfo + "\n# Author: nicolas.bredeche(at)lri.fr\n# Version " + _versionNum + "\n# build " + _versionBuild+"\n\n");
	}

}
