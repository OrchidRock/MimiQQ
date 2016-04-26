package client;

class Friend extends User{
	private String groupname;
	private String remark;
	private String loginerID;
	
	private boolean hasSession;
	private String recordID;
	
	private long ipaddress;
	private int port;
	private boolean onlineState;
	
	private String friendType;
	
	public Friend(){};
	
	public boolean getHasSessionState(){
		return hasSession;
	} 
	public String getFriendType(){
		return friendType;
	}
}
