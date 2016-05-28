package transaction;

import java.util.ArrayList;
import java.util.List;

import tools.TDU;

class ConnectServerHandle implements Runnable{
	private String server;
	private int port;
	
	List<TDU> waitingtdus=new ArrayList<>();
	List<TDU> cometdus=new ArrayList<>();
	
	public ConnectServerHandle(String serveraddress,int port) {
		this.server=serveraddress;
		this.port=port;
	}
	@Override
	public void run() {
		
	}
	
}
