package client;

import java.util.List;

import transaction.Controler;
class ClientMoudle {
	private Loginer loginer;
	Controler controler;
	public ClientMoudle(){
		controler=new Controler();
		loginer=null;
	}
	public List<Record> getFriendRecords(Friend friend){
		/* send recordreq.xml : get recordreq_back.xml */
		return null;
	}
	public List<Record> getFlockRecords(Flock flock){
		List<Flock> myFlocks=loginer.flocks;
		if(!myFlocks.contains(flock))
			return null;
		/* send recordreq.xml : get recordreq_back.xml */
		return null;
	}
	public Loginer login(String userID,String password){
		/* send login.xml : get login_back.xml*/
		/* udp each of online friend a 'IAMBACK' record */
		
		return loginer;
	}
	public Loginer signin(User register){
		/* send signin.xml : get signin_back.xml*/
		return loginer;
	}
	public List<TalkObject> searchTalkObject(String srearchKey){
		
		/* send search.xml : get search_back.xml */
		return null;
	}
	public void inviteFriend(CreateRelActivity newCRA){
		/* send invite.xml(action=new)*/
		/* If target user is onlined then udp
		   a 'CRA_HELLO' record */
		
	}
	public boolean inviteFriendOK(Friend newFriend){
		/* send invite.xml(action=delete)*/
		/* send friendadd.xml */
		/* Note : may be can merge tow XML files */
		return false;
	}
	public void inviteFriendFailed(String friendid){
		/* send invite.xml(action=delete)*/
	}
	public boolean aggreFriendInvite(Friend newFriend){
		/* send invite.xml(action=setok)*/
		/* send friendadd.xml */
		/* Note : may be can merge tow XML files */
		/* If target user is onlined then udp
		 a 'CRA_OK' record */
		return false;
	}
	public void rejectFriendInvite(String ownerID,String userID){
		/* send invite.xml(action=setfailed)*/
		/* If target user is onlined then udp
		   a 'CRA_FAILED' record */
	}
	public void ignoreFriendInvite(String ownerID,String userID){
		/* send invite.xml(action=delete)*/
	}
	
	/* API for flock */
	public void applyForFlock(CreateRelActivity newCRA){
		/* send invite.xml(action=new)*/
		/* If flock's creater is onlined then udp
		 a 'CRA_HELLO' record */
	}
	public boolean aggreFlockApplyFor(String flockID,String userID){
		/* send apply.xml(action=setok)*/
		/* send flockaddnumber.xml */
		/* send a record.xml(type=flock:HESHEJOIN)*/
		/* udp all online number this record */
		/* Note : may be can merge tow XML files */
		/* If target user is onlined then udp
		   a 'CRA_OK' record*/
		return false;
	}
	public void rejectFlockApplyFor(String flockID,String userID){
		/* send apply.xml(action=setfailed)*/
		/* If target user is onlined then udp
		   a 'CRA_FAILED' record */
	}
	public void ignoreFlockApplyFor(String flockID,String userID){
		/* send apply.xml(action=delete)*/
	}
	public boolean applyForFlockOk(String flockID){
		/* send apply.xml(action=delete)*/
		/* Note : may be can merge tow XML files */
		return false;
	}
	public void applyForFlockFailed(String flockID){
		/* send apply.xml(action=delete)*/
	}
	
	public boolean deleteFriend(String friendID){
		/* send frienddelete.xml */
		/* send a record.xml(type=user:breakoff) */
		/* udp a 'breakoff' record to friend */
		return false;
	}
	public boolean quitFlock(String flockID){
		/* send flocknumberquit.xml */
		/* send a record.xml(type=flock:IAMQUIT)*/
		/* udp all online number this record */
		return false;
	}
	
	public Flock createNewFlock(Flock newflock){
		/* send flockcreate.xml : get flockcreate_back.xml*/
		return newflock;
	}
	
	public boolean dissolveFlock(String flockID){
		/*send flockdelete.xml  */
		return false;
	}
	
	public void sendDataToFriend(String friendID,Record record){
		/* send record.xml(type=user:message or file) */
		/* if friend is onlined then udp a
		   'message or others' record*/
	}
	public void sendDataToFlock(String flockID,Record record){
		/* send record.xml(type=flock:message or file) */
		/* udp each of numbers of flock a 'message or others' record */
	}
	public void exit(){
		/* send exit.xml*/
		/* udp each of online friend a 'GOODBAY' record */
	}
}

















