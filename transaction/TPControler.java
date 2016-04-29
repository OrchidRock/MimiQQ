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
import client.Flock.FlockNumberRoleType;
import client.Friend;
import client.Friend.FriendRoleType;
import client.Loginer;
import client.TalkObject;
import client.User;
import transaction.Connecter.SearchSchema;
public class TPControler {
	private QLoginer qloginer;
	private Connecter connecter;
	
	public TPControler(){
		qloginer=null;
		connecter=null;
	}
	public Loginer qlogin(String userID,String password){
		if(connecter==null)
			connecter=new Connecter();
		if(qloginer==null)
			qloginer=new QLoginer();
		boolean loginRes=connecter.verifyQUserSelectInviteAndFlock_number(qloginer,userID, password);
		if(!loginRes)
			return null;
		return (Loginer)qloginer;
	}
	
	public Loginer qregister(User rUser){
		if(connecter==null)
			connecter=new Connecter();
		if(qloginer==null)
			qloginer=new QLoginer(rUser);
		if(connecter.addQUser(qloginer,rUser))
			return null;
		return (Loginer)qloginer;
	}
	@Deprecated
	public List<Friend> fatchUserFriends(String loginerID){
		if(!qloginer.getTalkObjectID().equals(loginerID)){
			System.err.println("TPControler: fatchUserFriends---Error:currentUserID"
					+ "is inconformity.");
			return null;
		}
		return qloginer.getFriendList();
	}
	@Deprecated
	public List<Flock> fatchUserFlocks(String loginerID){
		if(!qloginer.getTalkObjectID().equals(loginerID)){
			System.err.println("TPControler: fatchUserFriends---Error:currentUserID"
					+ "is inconformity.");
			return null;
		}
		return qloginer.getFlockList();
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
	public boolean inviteNewFriend(String currentUserID,User gUser){
		if(!currentUserID.equals(qloginer.getTalkObjectID())){
			System.err.println("TPControler:inviteNewFriend---Error:currentUserID"
					+ "is inconformity.");
			return false;
		}
		Friend qfriend=new Friend(gUser);
		qfriend.setOwnerID(currentUserID);
		//qfriend.setFriendType(FriendRoleType.INVITEE);
		if(connecter.addInvite(currentUserID,qfriend.getTalkObjectID())){
			qfriend.setFriendType(FriendRoleType.INVITEE);
			qloginer.getFriendList().add(qfriend);
		}else
			return false;
		return true;
	} 
	public boolean applicantNewFlock(String currentUserID,Flock flock){
		if(connecter.addFlock_number(currentUserID, flock.getTalkObjectID())){
			qloginer.getFlockList().add(flock);
		}else
			return false;
		return true;
	}
	
	public boolean agreeUserInvite(String currentUserID,Friend friend){
		if(connecter.deleteInviteAddFriend(currentUserID,friend.getTalkObjectID())){
			friend.setFriendType(FriendRoleType.REAL);
		}else
			return false;
		return true;
	}
	public boolean agreeApplicant(String flockID,User applicant) {
		if(connecter.updateFlock_number(flockID,applicant.getTalkObjectID()))
			return true;
		return false;
	}
	public boolean rejectInvite(Friend friend){
		if(!connecter.onlyDeleteInvite(qloginer.getTalkObjectID(),friend.getTalkObjectID()))
			return false;
		qloginer.getFriendList().remove(friend);
		return true;
	}
	public boolean rejectApplicant(Flock flock,String numberID){
		qloginer.getFlockList().remove(flock);
		return connecter.deleteFlock_number(flock.getTalkObjectID(),numberID);
	}
	/*
	 * Create own flock.
	 * @param the same as addNew*(*,*) @param description.
	 */
	public boolean createMyFlock(String currentUserID,Flock myCreateflock){
		myCreateflock.setCreaterID(currentUserID);
		myCreateflock.addNewNumber(qloginer, FlockNumberRoleType.CREATE);
		qloginer.getFlockList().add(myCreateflock);
		return connecter.addFlock(myCreateflock);
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
	public boolean dissolveFlock(Flock fl){
		qloginer.getFlockList().remove(fl);
		return connecter.deleteFlock(fl.getTalkObjectID());
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
