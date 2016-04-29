package test;
import client.*;
import client.Friend.FriendRoleType;
public class Flock_TalkObject_test {

	public static void main(String[] args) {
		Friend friend=new Friend();
		friend.setFriendType(FriendRoleType.REAL);
		friend.setTalkObjectID("12345678");
		
		System.out.println("friend1:type:"+friend.getFriendType()+"ID:"+friend.getTalkObjectID());
		
		User user=(User)friend;
		
		System.out.println("User:ID:"+user.getTalkObjectID());
		
		Friend friend2=(Friend)user;
		
		System.out.println("friend2:type:"+friend2.getFriendType()+"ID:"+friend2.getTalkObjectID());
		
	}

}
