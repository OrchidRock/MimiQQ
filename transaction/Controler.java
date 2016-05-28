package transaction;
/**
 * Transaction processing handler
 * @author rock
 *
 */

public class Controler {
	public final static int TOCLIENT_TAG=1;
	public final static int TOSERVER_TAG=2;
	public final static int ALLCLIENT_TAG=4;
	public final static int SIGNALCLIENT_TAG=8;
	
	private static String server="localhost";
	private static int serverport=80;
	public Controler(){
		/*create a new thread to connect to server*/
		
		
		Runnable connectserverHandle=new ConnectServerHandle(server, serverport);
		Thread thread=new Thread(connectserverHandle);
		thread.start();
	}
}
