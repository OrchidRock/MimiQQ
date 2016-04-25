package client;

import java.util.List;

/**
 * This moudle class difination the first level interafaces.
 * The View class will use it.
 * @author rock
 *
 */
class ClientMoudle {
	/*
	 * @param UserID is the User ID number
	 * password is it's password.
	 * @return true login success.
	 * 			false login failed.
	 */
	boolean login(String UserID,String password){
		return false;
	}
	/*
	 * @param newUser register
	 * @return true register success
	 *         false register failed
	 */
	boolean register(User newuser){
		return false;
	}
	/*
	 * @param userID is the current user's ID
	 * @return the friend list for this user.
	 */
	List<User> getFriendList(String userID){
		return null;
	}
	/*
	 * @return the flock list for the user
	 * whose ID is userID
	 */
	List<Flock> getFlockList(String userID){
		return null;
	}
	/*
	 * @param the searchKey can be userID or nickname.
	 * @return the userList that matching the searchKey.
	 */
	List<User> searchUser(String searchKey){
		return null;
	}
	/*
	 * Note:This method and the Last methon'searchUser'
	 * may can be merge.
	 */
	List<Flock> searchFlock(String searchKey){
		return null;
	}
	boolean addNewUser(User user){
		return false;
	}
	boolean addNewFlock(Flock flock){
		return false;
	}
	/*
	 * create own flock.
	 */
	boolean createMyFlock(Flock flock){
		return false;
	}
	
	boolean deleteFriend(User friend){
		return false;
	}
	boolean deleteFlock(Flock flock){
		return false;
	}
	boolean dissolveFlock(Flock myFlock){
		return false;
	}
	
	//Session methods
	
	boolean createNewSession(User friend){
		return false;
	}
	boolean createNewSession(Flock myFlock){
		return false;
	}
	
	boolean deleteSession(User friend){
		return false;
	}
	boolean deleteSession(Flock myFlock){
		return false;
	}
	SesssionRecord checkSessionRecord(User friend){
		return null;
	}
	SesssionRecord checkSesssionRecord(Flock myFlock){
		return null;
	}
}
