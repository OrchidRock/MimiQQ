package transaction;

import tools.*;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

import javax.xml.stream.XMLStreamException;

class RecvTDUHandle implements Runnable {
	private String clientIp = null;
	private String clientPort = null;

	private BlockingQueue<TDU> comeTdus = null;
	private UDPInputStream inputStream = null;

	public RecvTDUHandle(String ip, String port, BlockingQueue<TDU> come) {
		this.clientIp = ip;
		this.clientPort = port;
		this.comeTdus = come;
		// inputStream=new UDPInputStream(clientIp,clientPort);
	}

	@Override
	public void run() {
		try {
			if (inputStream == null)
				inputStream = new UDPInputStream(clientIp, Integer.valueOf(clientPort));
			Scanner scanner=new Scanner(inputStream);
			while(true){
				if(scanner.hasNext()){
					TDU tdu=XMLParser.parser(inputStream);
					comeTdus.put(tdu);
				}
			}
		} catch (NumberFormatException | UnknownHostException | SocketException | 
				XMLStreamException | InterruptedException e) {
			e.printStackTrace();
		}
		finally{
			try {
				inputStream.close();
				if(comeTdus!=null)
					comeTdus.clear();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
