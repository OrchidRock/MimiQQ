package client;

/**
 * User 
 * @author rock
 *
 */
class User{
	
	/*Can be inherited by Friend*/
    String userID;
	String nickname;
	String emailAddress;
	String phonenumber;
	byte [] imageBytes;
	
	/*
	 * Can not be inherited.Because loginer don't
	 *  need to down friend's password 
	 */
	//private String password;
	public User(){
		
	}
	public String getUserID(){return userID;}
}
