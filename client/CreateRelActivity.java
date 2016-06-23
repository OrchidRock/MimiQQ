package client;

import client.Record.TargetType;

public class CreateRelActivity {
	//TalkObject ownerObject;
	String ownerID;
	String targetID;
	//TalkObject targetObject;
	TargetType targetType;
	String notes;	
	Rel state;
	
	public CreateRelActivity(){
		//ownerObject=null;
		//targetObject=null;
		ownerID=null;
		targetID=null;
		targetType=TargetType.user;
		notes="";
		state=Rel.WAITING;
	}
	public enum Rel{
		WAITING,OK,FAILED
	};
}
