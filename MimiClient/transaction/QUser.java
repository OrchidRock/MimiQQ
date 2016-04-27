package transaction;

import java.util.List;

import client.Friend;
import client.User;

/**
 * QUser extends User.QUser shall operate
 * much sensitive data that client classes
 * do not need to know.
 * @author rock
 *
 */
class QUser extends User{
	private long ipaddress;
	private int openport;/*TCP port*/
	
	 List<QFriend> qFriends;
	 List<QFlock> qFlocks;
	
	public QUser() {
		qFriends=null;
		qFlocks=null;
	}
	public QUser(User newUser){
		super();
		ID=newUser.getTalkObjectID();
		String[] otherInfo=newUser.getUserShowInfo().split(":");
		nickname=otherInfo[0];
		emailAddress=otherInfo[1];
		phonenumber=otherInfo[2];
		imageBytes=newUser.getImageByte();
	}
	
	public List<QFriend> getQFriendList(){
		return qFriends;
	}
	public List<QFlock> getQFlockList(){
		return qFlocks;
	}
}
