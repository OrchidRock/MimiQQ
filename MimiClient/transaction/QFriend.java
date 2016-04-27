package transaction;
import client.Friend;
class QFriend extends Friend {
	private long ipaddress;
	private int openport;/*TCP port*/
	
	private String recordID;
	
	public QFriend(){
		super();
		recordID=null;
		//
	}
}
