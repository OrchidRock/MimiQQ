package client;

import java.util.List;



/**
 * This module class definition the first level interfaces.
 * The View class will use it.
 * @author rock
 *
 */
class ClientControler {
	
	public ClientControler(){}
	
	/*
	 * <p>User login by their UserID and password.</p>
	 * @return the right user if login success.
	 * 			null: login failed.
	 */
	public User getUserByLogin(String userID,String password){
		return null;
	}
	/*
	 * New user register.
	 * @param newuser object obtain base information about itself.
	 * include nickname,password,online_state,[email],[phone number],[image].
	 * @return the ID be created by server if register success.
	 *        null:register failed.
	 */
	public String register(User newuser){
		return null;
	}
	/*
	 * Get all talkObject(include friend and flock) of user 
	 * whose ID is currentUserID.
	 * @return the talkObject list of this user.
	 */
	@Deprecated
	public List<TalkObject> getTalkObjectList(String currentUserID){
		//getFlockList(currentUserID);
		//getFriendList(currentUserID);
		return null;
	}
	public List<Friend> fatchFriendList(String currentUserID){
		return null;
	}
	public List<Flock> fatchFlockList(String currentUserID){
		return null;
	}
	/*
	 * Search talk_objects by search_key.
	 * @param the searchKey can be userID(flockID) or nickname.
	 * @return the talk_object List that matching the searchKey.
	 */
	@Deprecated
	public List<TalkObject> searchTalkObject(String searchKey){
		searchFlock(searchKey);
		searchUser(searchKey);
		return null;
	}
	public List<User> searchUser(String searchKey){
		return null;
	}
	public List<Flock> searchFlock(String searchKey){
		return null;
	}
	
	/*
	 * @param currentUserID : the master user.
	 * @param newuser: the User that will be added the friend table
	 * of master user.
	 */
	public Friend inviteNewFriend(String currentUserID,String newFriendID){
		return null;
	}
	public Flock applicantNewFlock(String currentUserID,String newflockname){
		return null;
	}
	
	/*
	 * Create own flock.
	 * @param the same as addNew*(*,*) @param description.
	 * @return The flock_ID created by server if create success.
	 *         null: create failed.
	 */
	public String createMyFlock(String currentUserID,Flock myCreateflock){
		return null;
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
	public boolean createNewSession(String currentUserID,String friendID){
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
	}
}
