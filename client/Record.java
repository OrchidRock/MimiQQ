package client;

public class Record {
	String senderID;
	String targetID;
	String recordDate;
	String data;
	RecordType dataType;
	TargetType targetType;
	boolean isforwarding=false;
	public Record(){
		senderID=null;
		targetID=null;
		recordDate=null;
		data=null;
		dataType=RecordType.MESSAGE;
		targetType=TargetType.user;
	}
	
	
	public enum RecordType{
		MESSAGE,PICTURE,FILE,IAMBACK,
		GOODBAY,BREAKOFF,SEEHERE,IAMQUIT,HESHEJOIN
	};
	public enum TargetType{
		user,flock
	};
}
