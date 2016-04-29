package transaction;

import java.util.List;

import client.Friend;
import client.Loginer;
import client.User;

/**
 * QUser extends User.QUser shall operate
 * much sensitive data that client classes
 * do not need to know.
 * @author rock
 *
 */
class QLoginer extends Loginer{
	private long ipaddress;
	private int openport;/*TCP port*/
	
	public QLoginer() {
		super();
	}
	public QLoginer(User newUser){
		super();
		ID=newUser.getTalkObjectID();
		String[] otherInfo=newUser.getUserShowInfo().split(":");
		nickname=otherInfo[0];
		emailAddress=otherInfo[1];
		phonenumber=otherInfo[2];
		imageBytes=newUser.getImageByte();
	}
}
