package client;

import java.util.ArrayList;

import tools.TDU;
import tools.TDU.TDUType;

class TDUMonitorHandle implements Runnable{
	private ClientMoudle moudle=null;
	private ClientView view=null;
	public TDUMonitorHandle(ClientMoudle m,ClientView v) {
		this.moudle=m;
		this.view=v;
	}
	@Override
	public void run() {
		while(true){
				TDUType type=moudle.monitor();
				switch (type) {
				case LOGINBACK:
					break;
				case SIGNINBACK:
					break;//do nothing
				case SEARCHBACK:
					break;//do nothing
				case RECORDREQBACK:
					break;//do nothing
				case CRABACK:
					break;
				case FLOCKCREATEBACK:
					break;//do nothing
				case RECORD:
					break;
				default:
					break;
				}
		}
	}

}
