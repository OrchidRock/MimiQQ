package client;

import java.util.List;

/**
 * User who is base object be operated by client.
 * @author rock
 *
 */
public class User extends TalkObject{
	/*Can be inherited by Friend*/
   // String userID;
	protected String nickname;
	protected String emailAddress; 
	protected String phonenumber;
	protected byte [] imageBytes;
	
	protected boolean onlineState;
	
	public User(){
		super();
		nickname=null;
		emailAddress=null;
		phonenumber=null;  
		imageBytes=null;
		onlineState=false;
	}
	public String getUserShowInfo(){
		StringBuilder builder=new StringBuilder();
		builder.append(nickname+":");
		builder.append(emailAddress+":");
		builder.append(phonenumber);
		return builder.toString();
	}
	public byte[] getImageByte(){
		return imageBytes;
	}
}
