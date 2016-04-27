package transaction;
/**
 * Transaction processing handler
 * @author rock
 *
 */

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import client.Flock;
import client.Friend;
import client.SesssionRecord;
import client.Friend.FriendRoleType;
import client.TalkObject;
import client.User;
import transaction.Connecter.SearchSchema;
public class TPControler {
	private QUser qloginer;
	private Connecter connecter;
	
	public TPControler(){
		qloginer=null;
		//qFlocks=null;
		//qFriends=null;
		connecter=null;
	}
	public User qlogin(String userID,String password){
		if(connecter==null)
			connecter=new Connecter();
		
		qloginer=connecter.newQUserLogin(userID, password);
		if(qloginer==null)
			return null;
		return (User)qloginer;
	}
	
	public User qregister(User rUser){
		if(connecter==null)
			connecter=new Connecter();
		qloginer=connecter.newUserRegister(rUser);
		if(qloginer==null)
			return null;
		return (User)qloginer;
	}
	public List<Friend> fatchUserFriends(String loginerID){
		if(!qloginer.getTalkObjectID().equals(loginerID)){
			System.err.println("TPControler: fatchUserFriends---Error:currentUserID"
					+ "is inconformity.");
			return null;
		}
		List<Friend> friends=new ArrayList<>();
		friends.addAll(qloginer.getQFriendList());
		return friends;
	}
	public List<Flock> fatchUserFlocks(String loginerID){
		if(!qloginer.getTalkObjectID().equals(loginerID)){
			System.err.println("TPControler: fatchUserFriends---Error:currentUserID"
					+ "is inconformity.");
			return null;
		}
		List<Flock> flocks=new ArrayList<>();
		flocks.addAll(qloginer.getQFlockList());
		return flocks;
	}
	public List<User> searchUserByKey(String searchKey){
		Pattern pattern=Pattern.compile("[1-9][0-9]{7}");
		Matcher matcher=pattern.matcher(searchKey);
		List<TalkObject> user_list;
		if(matcher.matches()){
			user_list=connecter.search(searchKey, SearchSchema.USER_ID);
		}else
			user_list=connecter.search(searchKey, SearchSchema.USER_NICKNAME);
		List<User> users=new ArrayList<>();
		for(int i=0;i<user_list.size();i++){
			users.add(i, (User)user_list.get(i));
		}
		return users;
	}
	public List<Flock> searchFlockByKey(String searchKey){
		Pattern pattern=Pattern.compile("[1-9][0-9]{7}");
		Matcher matcher=pattern.matcher(searchKey);
		List<TalkObject> flock_list;
		if(matcher.matches()){
			flock_list=connecter.search(searchKey, SearchSchema.FLOCK_ID);
		}else
			flock_list=connecter.search(searchKey, SearchSchema.FLOCK_NAME);
		List<Flock> flocks=new ArrayList<>();
		for(int i=0;i<flock_list.size();i++){
			flocks.add(i, (Flock)flock_list.get(i));
		}
		return flocks;
	}
	public Friend inviteNewFriend(String currentUserID,String newFriendID){
		if(!currentUserID.equals(qloginer.getTalkObjectID())){
			System.err.println("TPControler:inviteNewFriend---Error:currentUserID"
					+ "is inconformity.");
			return null;
		}
		QFriend qfriend=new QFriend();
		qfriend.setTalkObject(newFriendID);
		if(connecter.addNewFriendToQLoginer(currentUserID,newFriendID)){
			qfriend.setFriendType(FriendRoleType.INVITEE);
			qloginer.qFriends.add(qfriend);
		}else
			return null;
		return qfriend;
	} 
	public Flock applicantNewFlock(String currentUserID,String newflockID){
		QFlock qflock=new QFlock();
		qflock.setTalkObject(newflockID);
		if(connecter.addNewApplicantToFlock(currentUserID, newflockID)){
			qloginer.qFlocks.add(qflock);
		}else
			return null;
		return qflock;
	}
	
	/*
	 * Create own flock.
	 * @param the same as addNew*(*,*) @param description.
	 */
	public boolean createMyFlock(String currentUserID,Flock myCreateflock){
		QFlock myFlock=new QFlock();
		myFlock.setCreaterID(currentUserID);
		//
		return false;
	}
	
	/*
	 * Delete friend whose ID is friendID
	 */
	public boolean deleteFriend(String currentUserID,String friendID){
		return false;
	}
	/*
	 *  quit flock whose ID is fockID
	 */
	public boolean quitFlock(String currentUserID,String flockID){
		return false;
	}
	/*
	 * dissolve own flock.
	 */
	public boolean dissolveFlock(String currentUserID,String flockID){
		return false;
	}
	
	/*
	 * Following are some session methods.
	 * Note:All session methods only try to 
	 * inform server to change the hasSession-state
	 * of friend whose ID is friendID.   
	 */
	/*public boolean createNewSession(String currentUserID,String friendID){
		return false;
	}
	public boolean deleteSession(String currentUserID,String friendID){
		return false;
	}
	
	SesssionRecord checkSessionRecord(User friend){
		return null;
	}
	SesssionRecord checkSesssionRecord(Flock myFlock){
		return null;
	}*/
}
