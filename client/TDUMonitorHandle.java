package client;

import java.util.concurrent.BlockingQueue;

import tools.TDU;
import transaction.Controler;

class TDUMonitorHandle implements Runnable{

	private Controler controler=null;
	private Loginer loginer=null;
	public TDUMonitorHandle(Controler controler,Loginer loginer) {
		this.controler=controler;
		this.loginer=loginer;
	}
	@Override
	public void run() {
		BlockingQueue<TDU> comeTdus=controler.getComeTdus();
		while(true){
			try {
				TDU tdu=comeTdus.take();
				switch (tdu.type) {
				case LOGINBACK:
					break;
				case RECORD:
				default:
					break;
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
