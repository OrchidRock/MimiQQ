package transaction;

import java.util.List;

import client.TalkObject;
import client.User;

class Connecter {
	private long serverIpAddress;/*the server ip*/
	private int serverOpenPort;/*the server open port*/
	
	public static enum SearchSchema{
		USER_ID,USER_NICKNAME,FLOCK_ID,FLOCK_NAME
	}
	
	/**
	 * Note:there are two lines to get the login data
	 * One come from server,other come from local_cache.
	 * query two tables: qfriend and invite;
	 */
	public QUser newQUserLogin(String userID,String password){
		// new thread
		
		return null;
	}

	/**
	 * update table:quser,quser_log
	 */
	public QUser newUserRegister(User rUser) {
		
		return null;
	}
	public List<TalkObject> search(String searchKey,SearchSchema schema){
		switch (schema) {
		case USER_ID:
			break;
		case USER_NICKNAME:
			break;
		case FLOCK_ID:
			break;
		case FLOCK_NAME:
			break;
		default:
			break;
		}
		return null;
	}

	public boolean addNewFriendToQLoginer(String currentUserID, String newFriendID) {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean addNewApplicantToFlock(String applicant,String flockID){
		return false;
	}
	public boolean createNewFlock(){
		return false;
	}
}
