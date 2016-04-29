package transaction;

import java.util.List;
import client.Flock;
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
	public boolean verifyQUserSelectInviteAndFlock_number(QLoginer loginer,String userID,String password){
		// new thread
		
		return false;
	}

	/**
	 * update table:quser,quser_log
	 */
	public boolean addQUser(QLoginer qloginer,User rUser) {
		
		return false;
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

	public boolean addInvite(String currentUserID, String friendid) {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean addFlock_number(String applicant,String flockID){
		return false;
	}
	public boolean createNewFlock(){
		return false;
	}

	public boolean deleteInviteAddFriend(String currentUserID, String talkObjectID) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean updateFlock_number(String flockID, String talkObjectID) {
		
		return false;
	}

	public boolean onlyDeleteInvite(String talkObjectID, String talkObjectID2) {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean deleteFlock_number(String talkObjectID, String numberID) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean deleteFlock(String flockID) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean addFlock(Flock myCreateflock) {/*update myCreateflock object attitude*/
		// TODO Auto-generated method stub
		return false;
	}
}
