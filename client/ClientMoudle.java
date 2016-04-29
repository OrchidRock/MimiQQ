package client;

import java.util.ArrayList;
import java.util.List;

import client.Flock.FlockNumber;
import client.Flock.FlockNumberRoleType;
import client.TalkObject.TalkRoleType;
import tools.PrintDebug;
import tools.PrintDebug.ErrorType;
import transaction.TPControler;
class ClientMoudle {
	private Loginer loginer;
	TPControler controler;
	public ClientMoudle(){
		controler=new TPControler();
		loginer=null;
	}
	
	/**
	 * get friend list,flock list And session list. 
	 */
	/*private boolean start(){
		//List<TalkObject> talkObjects=controler.getTalkObjectList(loginer.getUserID());
		loginer.friends=controler.fatchUserFriends(loginer.getTalkObjectID());
		if(friends==null){
			PrintDebug.PD("Moudle", "start", ErrorType.NULL_POINTER);
			return false;
		}
		flocks=controler.fatchUserFlocks(loginer.getTalkObjectID());
		if(flocks==null){
			PrintDebug.PD("Moudle", "start", ErrorType.NULL_POINTER);
			return false;
		}
		return true;
	}*/
	/**
	 * login,get the loginer and loginer's friends and flocks.
	 * @param userID the loginer's ID.
	 * @param password the loginer's password.
	 * @return whether login success.
	 */
	public boolean login(String userID,String password){
		if(userID==null || password==null){
			PrintDebug.PD("Moudle", "login", ErrorType.USAGE_ERR);
			return false;
		}
		
		loginer=controler.qlogin(userID, password);
		if(loginer==null){
			PrintDebug.PD("Moudle", "login", ErrorType.NULL_POINTER);
			return false;
		} 
		return true;
	}
	public boolean register(User register){
		Loginer temp=controler.qregister(register);
		if(temp!=null){
			loginer=temp;
			return true;
		}else
			return false;
	}
	public List<User> searchUser(String searchKey){
		//controler.searchTalkObject(searchKey);	
		return controler.searchUserByKey(searchKey);
	}
	public List<Flock> searchFlock(String searchKey){
		return controler.searchFlockByKey(searchKey);
	}
	
	/**
	 * The most important thing is the friend object
	 * return by controler.inviteNewFriend().It can be REAL friend,
	 * INVITEE friend(NO INVITER).
	 * @return when return true,the view can update it's friend-list
	 *         else,do nothing(show a remain to GUI-user).
	 */
	//
	public boolean inviteOrApplicantNewTalkObject(TalkObject gObject){
		if(gObject.getMyTalkRoleType()==TalkRoleType.USER){
			User user=(User)gObject;
			if(gObject.getTalkObjectID().equals(loginer.getTalkObjectID()))
				return true;
			if(!controler.inviteNewFriend(loginer.getTalkObjectID(), user))
				return false;
		}else{
			Flock flock=(Flock)gObject;
			Flock stFlock=(Flock)findTalkObject(flock.getTalkObjectID(), gObject.getMyTalkRoleType());/*repeat applicant*/
			if(stFlock!=null)
				return true;
			if(!controler.applicantNewFlock(loginer.getTalkObjectID(),flock))
				return false;
		}
		return true;
	}
	public boolean agreeFriendInvite(Friend friend){/*groupname and remark*/
		return controler.agreeUserInvite(loginer.getTalkObjectID(), friend);
	}
	public boolean agreeApplicant(String flockID,FlockNumber applicant){
		applicant.myType=FlockNumberRoleType.NNUMBER;
		return controler.agreeApplicant(flockID,applicant);
	}
	public boolean rejectInvite(Friend friend){
		return controler.rejectInvite(friend);
	}
	public boolean rejectApplicant(Flock flock,FlockNumber number){
		if(controler.rejectApplicant(flock,number.getTalkObjectID())){
			flock.getFlockNumbers().remove(number);
			return true;
		}
		return false;
	}
	
	public boolean deleteFriend(Friend friend){
		return controler.rejectInvite(friend);
	}
	public boolean quitFlock(Flock fl){
		if(loginer.getTalkObjectID().equals(fl.getCreaterID())){
			System.err.println("Moudle:quitFlock---Warning:GUI-user is try to quit"
					+ "the flock creater by himself.!");
			return false;
		}
		return controler.rejectApplicant(fl, loginer.getTalkObjectID());
	}
	public boolean dissolveFlock(Flock fl){
		for(Flock f:loginer.getFlockList()){
			if(f.equals(fl)){
				if(!f.getCreaterID().equals(loginer.getTalkObjectID())){
					System.err.println("Moudle:quitFlock---Warning:GUI-user is try to"
							+ " dissolve the flock not creater by himself.!");
					return false;
				}
				break;
			}
		}
		return controler.dissolveFlock(fl);
	}
	/**
	 * loginer create a new flock.
	 */
	public boolean createMyFlock(Flock newflock){
		return controler.createMyFlock(loginer.getTalkObjectID(), newflock);
	}
	
	public void Exit(){
		// the operation for local cache.	
	}
	
	private TalkObject findTalkObject(String toID,TalkRoleType trt){
		if(trt==TalkRoleType.USER){
			for(Friend f:loginer.getFriendList()){
				if(f.getTalkObjectID().equals(toID))
					return f;
			}
		}else{
			for(Flock flock:loginer.getFlockList()){
				if(flock.getTalkObjectID().equals(toID))
					return flock;
			}
		}
		return null;
	}
	public List<Flock> getMyCreateFlocks(){
		List<Flock> myCreateFlocks=new ArrayList<>();
		for(Flock f:loginer.getFlockList()){
			if(f.createrID.equals(loginer.getTalkObjectID()))
				myCreateFlocks.add(f);
		}
		return myCreateFlocks;
	}
}
