package transaction;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;


import tools.TDU;
import tools.TDU.TDUType;

/**
 * Transaction processing handler
 * 
 * @author rock
 *
 */

public class Controler {
	public final static int TO_SERVER_TAG = 1;
	public final static int TO_MANYCLIENT_TAG = 2;
	public final static int TO_SIGNALCLIENT_TAG = 4;

	private final static int TDU_QUEUE_SERVER_SIZE = 100;

	private static String serverIP = "localhost";
	private static int serverPort = 80;

	public static String clientIP = "localhost";
	public static String clientPort = "53";// DNS default port
	// block queue
	BlockingQueue<TDU> comeTdus = new ArrayBlockingQueue<>(TDU_QUEUE_SERVER_SIZE);
	BlockingQueue<TDU> toTdus = new ArrayBlockingQueue<>(TDU_QUEUE_SERVER_SIZE);

	public Controler() {
		/* create a new thread to send TDU */
		SendTDUHandle sendhandle = new SendTDUHandle(serverIP, serverPort, comeTdus, toTdus);
		Thread sendthread = new Thread(sendhandle);
		sendthread.setDaemon(true);
		sendthread.start();
		
		/* create a new thread to recv TDU */
		RecvTDUHandle recvhandle = new RecvTDUHandle(clientIP, clientPort, comeTdus);
		Thread recvthread = new Thread(recvhandle);
		recvthread.setDaemon(true);
		recvthread.start();
		
	}
	public void send(TDU tdu) {
		try {
			if (tdu.type == TDUType.LOGIN) {
				// get local backups;
				//comeTdus.put(e);
			}
			toTdus.put(tdu);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public TDU recv(TDUType type,boolean iswaiting) {
		TDU result=null;
		try {
			if(iswaiting){
				result=comeTdus.take();
			}
			else
				result= comeTdus.poll(100, TimeUnit.MILLISECONDS);
			if(result!=null){
				if(result.type!=type){
					comeTdus.put(result);
					result=null;
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	public BlockingQueue<TDU> getComeTdus(){
		return comeTdus;
	}
}
