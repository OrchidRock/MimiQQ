package client;

public class Record {
	String senderID;
	String targetID;
	String recordDate;
	String data;
	RecordType dataType;
	TargetType targetType;
	
	public Record(){
		senderID=null;
		targetID=null;
		recordDate=null;
		data=null;
		dataType=RecordType.MESSAGE;
		targetType=TargetType.USER;
	}
	
	
	public enum RecordType{
		MESSAGE,PICTURE,FILE,IAMBACK,
		GOODBAY,BREAKOFF,SEEHERE,IAMQUIT,HESHEJOIN,
		CRA_HELLO,CRA_OK,CRA_FAILED
	};
	public enum TargetType{
		USER,FLOCK
	};
}
