package transaction;
import tools.*;
import java.util.concurrent.BlockingQueue;

class RecvTDUHandle implements Runnable{
	private String clientIp=null;
	private String clientPort=null;
	
	private BlockingQueue<TDU> comeTdus=null;
	
	public RecvTDUHandle(String ip,String port,BlockingQueue<TDU> come) {
		this.clientIp=ip;
		this.clientPort=port;
		this.comeTdus=come;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
