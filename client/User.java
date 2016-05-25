package client;

import java.util.List;

/**
 * User who is base object be operated by client.
 * @author rock
 *
 */
public class User extends TalkObject{
	
	String emailAddress; 
    boolean onlineState;
	
	public User(){
		super();
		emailAddress="";
		onlineState=false;
	}
	
}
