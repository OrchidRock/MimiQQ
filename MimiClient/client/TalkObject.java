package client;

/**
 * This class is s super class
 * of User and Flock.
 * 
 */
public class TalkObject {
	protected String ID;
	public static enum TalkRoleType{
		USER,FLOCK
	}
	private TalkRoleType myTalkRole;
	public TalkObject(){
		ID=null;
		myTalkRole=TalkRoleType.USER;
	}
	
	public TalkRoleType getMyTalkRoleType(){
		return myTalkRole;
	}
	
	public String getTalkObjectID(){
		return ID;
	}
	public void setTalkObject(String newID){
		if(newID==null || newID.length()<1){
			System.err.println("TalkObject: setTalkObject---UsgError:new ID argument"
					+ "is not allowed");
			return;
		}
		ID=newID;
	}
}
