package client;

import javax.ejb.EJB;

import facade.handlers.IClassServiceRemote;

/**
 * A simple application client that uses both services.
 *
 * @author fmartins
 * @author alopes
 * @version 1.3 (02/05/2017)
 */
public class Main {
	
	// 1. confirm that in aulasges project directory, you have aulasges-ear/target/aulasges-ear-1.0.ear
	// you can get a copy from from PATH-TO-WILDFLY/standalone/deployments or 
	// make Eclipse produce it by using export from the context menu, over aulas-ges-ear project
	// 2. run this program with the following command inside the aulasges project directory:
	// PATH-TO-WILDFLY/bin/appclient.sh aulasges-ear/target/aulasges-ear-1.0.ear#aulasges-gui-client.jar
	
    @EJB
    private static IClassServiceRemote classService;

	/**
	 * An utility class should not have public constructors
	 */
	private Main() {
	}

    /**
     * A simple interaction with the application services
     *
     * @param args Command line parameters
     */
    public static void main(String[] args) {
    	presentation.fx.Startup.startGUI(classService);
    }
}