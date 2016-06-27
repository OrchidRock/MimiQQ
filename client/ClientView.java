package client;

import java.io.IOException;
import java.util.Scanner;

public class ClientView {
	
	private ClientMoudle moudle;
	public ClientView(){
		moudle=new ClientMoudle();
		/*monitor for moudle*/
		TDUMonitorHandle handle=new TDUMonitorHandle(moudle,this);
		Thread thread=new Thread(handle);
		thread.setDaemon(true);
		thread.start();
	}
	public void run(){
		System.out.println("-----Welecom to MimiQQ!------");
		Scanner scanner=new Scanner(System.in);
		System.out.println(">");
		while(scanner.hasNext()){
				moudle.login("123456", "1234567");
				System.out.println(">");
				System.out.println(scanner.nextLine()+",I am running!");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	public void recvMessageFromFriend(Friend friend,String message){
		
	}
}
