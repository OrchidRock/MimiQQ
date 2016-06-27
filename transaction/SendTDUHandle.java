package transaction;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import javax.xml.stream.XMLStreamException;

import client.User;
import tools.TDU;
import tools.XMLBuilder;

class SendTDUHandle implements Runnable {
	private String server = null;
	private int port = 0;

	private BlockingQueue<TDU> toTdus = null;
	private BlockingQueue<TDU> comeTdus = null;
	private OutputStream outputStreamServer = null;

	public SendTDUHandle(String serveraddress, int port, BlockingQueue<TDU> come, BlockingQueue<TDU> to) {

		this.server = serveraddress;
		this.port = port;
		this.comeTdus = come;
		this.toTdus = to;
	}

	@Override
	public void run() {
		try {
			while (true) {
				TDU tdu = toTdus.take();
				outputStreamServer=System.out;
				if ((tdu.TAG & Controler.TO_SERVER_TAG) > 0) {
					if(outputStreamServer==null)
						outputStreamServer=new UDPOutputStream(server, port);
					XMLBuilder.builder(outputStreamServer, tdu);
				}
				if ((tdu.TAG & Controler.TO_SIGNALCLIENT_TAG) > 0) {
					User user = tdu.ONLINE_USER_LIST.get(0);
					OutputStream outputStream = new UDPOutputStream(user.ipaddress, Integer.valueOf(user.port));
					XMLBuilder.builder(outputStream, tdu);
				}
				if ((tdu.TAG & Controler.TO_MANYCLIENT_TAG) > 0) {
					List<User> users = tdu.ONLINE_USER_LIST;
					for (User user : users) {
						OutputStream outputStream = new UDPOutputStream(user.ipaddress, Integer.valueOf(user.port));
						XMLBuilder.builder(outputStream, tdu);
					}
				}
			}
		} catch (InterruptedException | IOException | XMLStreamException e) {
			e.printStackTrace();
		} finally {
			if (toTdus != null)
				toTdus.clear();
			try {
				outputStreamServer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
