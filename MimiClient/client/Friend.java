package client;

public class Friend extends User{
	protected String groupname;
	protected String remark;
	protected String ownerID;/*owner*/
	protected boolean hasSession;
	
	public static enum FriendRoleType{
		REAL,INVITEE,INVITER
	}
	protected boolean onlineState;
	
	protected FriendRoleType friendType;
	
	public Friend(){};
	
	public boolean getHasSessionState(){
		return hasSession;
	} 
	public FriendRoleType getFriendType(){
		return friendType;
	}
	public void setFriendType(FriendRoleType type){
		friendType=type;
	}
}
