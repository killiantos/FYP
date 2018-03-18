/*
 * Author: nicolas.bredeche(@)lri.fr
 * Created on 15 jan. 2006
 * 
 * 
 */

package picoevo.toolbox;

public class Version {

	// candidate names :
	// serious: Evoorg, Evorgue, evoluciel, evoke, evoque, evolune, evorchidee, evorchis, evoledge, nuEvo, "Evo World!" 
	// not serious : PicoEvo(*), EvoCoinCoin, EvoYoupi, Evotchutchu(*), EvoTchouTchou, EvoTchooTchoo, Evolove :-), ...
	
	static String _versionInfo = "PicoEvo";
	static String _versionNum = "0.9beta2";
	static String _versionBuild = "200601301714";
	
	static public void displayCurrentReleaseInformation()
	{
		Display.info("\n# " + _versionInfo + "\n# Author: nicolas.bredeche(at)lri.fr\n# Version " + _versionNum + "\n# build " + _versionBuild+"\n\n");
	}

}
