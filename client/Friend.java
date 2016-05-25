package client;

public class Friend extends User{
	String groupname;
	String remark;
	String ownerID;/*owner*/
	boolean hasSession;

	public Friend(){
		super();
		groupname="myfriend";
		remark="";
		hasSession=false;
		ownerID="";
	}
}
