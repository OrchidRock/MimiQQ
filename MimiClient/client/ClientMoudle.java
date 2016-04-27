package client;

import java.util.List;

import client.TalkObject.TalkRoleType;
import transaction.*;
class ClientMoudle {
	private User loginer;
	private List<Friend> friends;
	private List<Flock> flocks;
	TPControler controler;
	public ClientMoudle(){
		controler=new TPControler();
		friends=null;
		flocks=null;
		loginer=null;
	}
	
	/**
	 * get friend list,flock list And session list. 
	 */
	private boolean start(){
		//List<TalkObject> talkObjects=controler.getTalkObjectList(loginer.getUserID());
		friends=controler.fatchUserFriends(loginer.getTalkObjectID());
		if(friends==null){
			System.err.println("Mouble:start--Remaind: friend List is null");
			return false;
		}
		flocks=controler.fatchUserFlocks(loginer.getTalkObjectID());
		if(flocks==null){
			System.err.println("Mouble:start--Remaind: flock List is null");
			return false;
		}
		return true;
	}
	/**
	 * login,get the loginer and loginer's friends and flocks.
	 * @param userID the loginer's ID.
	 * @param password the loginer's password.
	 * @return whether login success.
	 */
	public boolean login(String userID,String password){
		if(userID==null || password==null)
			return false;
		
		loginer=controler.qlogin(userID, password);
		if(loginer==null)
			return false;
		boolean result=start();
		return result;
	}
	public boolean register(User register){
		User temp=controler.qregister(register);
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
	public boolean inviteNewFriend(String newFriendID){
		if(newFriendID.equals(loginer.getTalkObjectID()))
			return true;
		Friend friend=controler.inviteNewFriend(loginer.getTalkObjectID(),newFriendID);
		if(friend==null)
			return false;
		friends.add(friend);
		return true;
	}
	public boolean applicantNewFlock(String newFlockID){
		for(Flock flock:flocks){
			if(flock.getTalkObjectID().equals(newFlockID))
			{
				if(flock.getFlockNumbers()==null){ /*repeat applicant*/
					System.out.println("Moudle:applicantNewFlock--Waining:"
							+ "repeat applicant!");
				}
				return true;
			}
		}
		Flock flock=controler.applicantNewFlock(loginer.getTalkObjectID(), newFlockID);
		if(flock==null)
			return false;
		flocks.add(flock);
		return true;
	}
	
	
	public boolean deleteFriend(Friend friend){
		if(controler.deleteFriend(loginer.getTalkObjectID(), friend.getTalkObjectID())){
			friends.remove(friend);
		}else {
			return false;
		}
		return true;
	}
	public boolean quitFlock(Flock fl){
		if(loginer.getTalkObjectID().equals(fl.getCreaterID())){
			System.err.println("Moudle:quitFlock---Warning:GUI-user is try to quit"
					+ "the flock creater by himself.!");
			return false;
		}
		if(controler.quitFlock(loginer.getTalkObjectID(),fl.getTalkObjectID())){
			flocks.remove(fl);
		}else
			return false;
		return true;
	}
	public boolean dissolveFlock(Flock fl){
		for(Flock f:flocks){
			if(f.equals(fl)){
				if(!f.getCreaterID().equals(loginer.getTalkObjectID())){
					System.err.println("Moudle:quitFlock---Warning:GUI-user is try to"
							+ " dissolve the flock not creater by himself.!");
					return false;
				}
				break;
			}
		}
		if(controler.dissolveFlock(loginer.getTalkObjectID(), fl.getTalkObjectID())){
			flocks.remove(fl);
		}else
			return false;
		return true;
	}
	/**
	 * loginer create a new flock.
	 */
	public boolean createMyFlock(Flock newflock){
		if(controler.createMyFlock(loginer.getTalkObjectID(), newflock)){
			flocks.add(newflock);
		}else
			return false;
		return true;
	}
	public List<Friend> getFriendList(){
		return friends;
	}
	public List<Flock> getFlockList(){
		return flocks;
	}
	public void Exit(){
		// the operation for local cache.	
	}
}
