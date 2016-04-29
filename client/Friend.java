package client;

public class Friend extends User{
	protected String groupname;
	protected String remark;
	protected String ownerID;/*owner*/
	protected boolean hasSession;
	
	
	private long ipaddress;
	private int openport;/*TCP port*/
	
	private String recordID;
	
	
	public static enum FriendRoleType{
		REAL,INVITEE,INVITER
	}
	protected boolean onlineState;
	
	protected FriendRoleType friendType;
	
	public Friend(){
		super();
		recordID=null;
	}
	public Friend(User newUser){
		super();
		recordID=null;
		ID=newUser.getTalkObjectID();
		String[] otherInfo=newUser.getUserShowInfo().split(":");
		nickname=otherInfo[0];
		emailAddress=otherInfo[1];
		phonenumber=otherInfo[2];
		imageBytes=newUser.getImageByte();
	}
	public boolean getHasSessionState(){
		return hasSession;
	} 
	public FriendRoleType getFriendType(){
		return friendType;
	}
	public void setFriendType(FriendRoleType type){
		friendType=type;
	}
	public void setOwnerID(String id){
		ownerID=id;
	}
}
