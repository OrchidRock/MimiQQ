package transaction;

import java.util.concurrent.BlockingQueue;

import tools.TDU;

class SendTDUHandle implements Runnable{
	private String server=null;
	private String port=null;
	
	private BlockingQueue<TDU> toTdus=null;
	private BlockingQueue<TDU> comeTdus=null;
	
	public SendTDUHandle(String serveraddress,String port, 
			BlockingQueue<TDU> come, BlockingQueue<TDU> to) {
		this.server=serveraddress;
		this.port=port;
		this.comeTdus=come;
		this.toTdus=to;
	}
	@Override
	public void run() {
		
	}
	
}
