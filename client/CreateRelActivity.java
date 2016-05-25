package client;

public class CreateRelActivity {
	TalkObject ownerObject;
	TalkObject targetObject;
	String notes;	
	Rel state;
	
	public CreateRelActivity(){
		ownerObject=null;
		targetObject=null;
		notes="";
		state=Rel.WAITING;
	}
	public enum Rel{
		WAITING,OK,FAILED
	};
}
