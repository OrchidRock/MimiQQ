package client;

/**
 * User who is base object be operated by client.
 * @author rock
 *
 */
public class User extends TalkObject{
	
	String emailAddress; 
    boolean onlineState;
	public String ipaddress;
	public String port;
	public User(){
		super();
		emailAddress="";
		onlineState=false;
		ipaddress="";
		port="";
	}
	
}
