package client;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import client.CreateRelActivity.Rel;
import client.Record.RecordType;
import client.Record.TargetType;
import tools.TDU;
import tools.TDU.TDUType;
import transaction.Controler;

class ClientMoudle {
	private Loginer loginer;
	Controler controler;

	public ClientMoudle() {
		controler = new Controler();
		loginer = null;
	}

	public List<Record> getFriendRecords(Friend friend) {
		/* send recordreq.xml : get recordreq_back.xml */
		return null;
	}

	public List<Record> getFlockRecords(Flock flock) {
		List<Flock> myFlocks = loginer.flocks;
		if (!myFlocks.contains(flock))
			return null;
		/* send recordreq.xml : get recordreq_back.xml */
		return null;
	}

	public Loginer login(String userID, String password) {
		/* send login.xml : get login_back.xml */
		/* udp each of online friend a 'IAMBACK' record */
		TDU loginTdu = new TDU(TDUType.LOGIN);
		loginTdu.TAG = Controler.TO_SERVER_TAG;
		String[] text = { userID, password, Controler.clientIP, Controler.clientPort };
		loginTdu.setTextsByArray(text);
		controler.send(loginTdu);

		TDU loginBackTdu = controler.recv();// may be sleep;
		if (loginBackTdu == null)
			return null;
		text = loginBackTdu.getTextsArray();
		if (loginer == null)
			loginer = new Loginer();
		int j = 0;
		loginer.ID = text[j++];
		loginer.name = text[j++];
		loginer.emailAddress = text[j++];
		loginer.imageurl = text[j++];
		loginer.ipaddress = Controler.clientIP;
		loginer.port = Controler.clientPort;

		List<User> onlineFriends = new ArrayList<>();
		if (text.length > j) { // friendlist
			int friendcount = Integer.valueOf(text[j++]);
			for (int i = 0; i < friendcount; i++) {
				Friend friend = new Friend();
				friend.ID = text[j++];
				friend.name = text[j++];
				friend.emailAddress = text[j++];
				friend.imageurl = text[j++];
				friend.onlineState = (text[j++] == "Y") ? true : false;
				if (friend.onlineState) {
					friend.ipaddress = text[j++];
					friend.port = text[j++];
					onlineFriends.add(friend);
				}
				friend.groupname = text[j++];
				friend.remark = text[j++];
				friend.hasSession = (text[j++] == "Y") ? true : false;
				loginer.fullNewFriend(friend);
			}
		}
		if (text.length > j) { // flocklist
			int flockcount = Integer.valueOf(text[j++]);
			for (int i = 0; i < flockcount; i++) {
				Flock flock = new Flock();
				flock.ID = text[j++];
				flock.name = text[j++];
				flock.createrID = text[j++];
				flock.createDate = text[j++];
				flock.imageurl = text[j++];
				flock.notes = text[j++];
				loginer.fullNewFlock(flock);
			}
		}
		if (text.length > j) { // crabacklist
			int crabackcount = Integer.valueOf(text[j++]);
			for (int i = 0; i < crabackcount; i++) {
				CreateRelActivity craback = new CreateRelActivity();
				if (text[j] == loginer.ID) {
					craback.ownerObject = loginer;
					j++;
				} else {
					TalkObject owner = new TalkObject();
					owner.ID = text[j++];
					craback.ownerObject = owner;
				}
				if (text[j] == loginer.ID) {
					craback.targetObject = loginer;
					j++;
				} else {
					TalkObject target = new TalkObject();
					target.ID = text[j++];
					craback.targetObject = target;
				}
				craback.notes = text[j++];
				craback.state = Rel.valueOf(Rel.class, text[j++]);
				loginer.fullNewCRAback(craback);
			}
		}

		// sendDataToUser(user, record, tag);
		// record all online friend
		Record record = new Record();
		record.senderID = loginer.ID;
		record.dataType = RecordType.IAMBACK;
		record.data = "";
		for (Friend f : loginer.friends) {
			if (f.onlineState) {
				record.targetID = f.ID;
				record.targetType = TargetType.user;
				sendDataToUser(f, record, Controler.TO_SIGNALCLIENT_TAG);
			}
		}
		return loginer;
	}

	public Loginer signin(User register, String password) {
		/* send signin.xml : get signin_back.xml */
		TDU signinTdu = new TDU();
		signinTdu.TAG = Controler.TO_SERVER_TAG;
		signinTdu.type = TDUType.SIGNIN;
		String[] text = { "", password, register.name, register.emailAddress, register.imageurl, Controler.clientIP,
				Controler.clientPort };
		signinTdu.setTextsByArray(text);
		controler.send(signinTdu);

		TDU signinbackTdu = controler.recv();
		if (signinbackTdu == null)
			return null;
		text = signinbackTdu.getTextsArray();
		if (loginer == null)
			loginer = new Loginer();
		int j = 0;
		loginer.ID = text[j++];
		loginer.name = register.name;
		loginer.emailAddress = register.emailAddress;
		loginer.imageurl = register.imageurl;
		return loginer;
	}

	public List<TalkObject> searchTalkObject(String searchKey) {
		/* send search.xml : get search_back.xml */
		TDU searchTdu = new TDU();
		searchTdu.TAG = Controler.TO_SERVER_TAG;
		searchTdu.type = TDUType.SEARCH;
		String[] text = { searchKey };
		searchTdu.setTextsByArray(text);
		controler.send(searchTdu);

		TDU searchBackTdu = controler.recv();
		if (searchBackTdu == null)
			return null;
		text = searchBackTdu.getTextsArray();
		List<TalkObject> talkObjects = new ArrayList<>();
		int j = 0;
		if (text.length > j) { // userlist
			int usercount = Integer.valueOf(text[j++]);
			for (int i = 0; i < usercount; i++) {
				User user = new User();
				user.ID = text[j++];
				user.name = text[j++];
				user.emailAddress = text[j++];
				user.imageurl = text[j++];
				user.onlineState = (text[j++] == "Y") ? true : false;
				talkObjects.add(user);
			}
		}
		if (text.length > j) { // flocklist
			int flockcount = Integer.valueOf(text[j++]);
			for (int i = 0; i < flockcount; i++) {
				Flock flock = new Flock();
				flock.ID = text[j++];
				flock.name = text[j++];
				flock.createrID = text[j++];
				flock.createDate = text[j++];
				flock.imageurl = text[j++];
				flock.notes = text[j++];
				talkObjects.add(flock);
			}
		}
		return talkObjects;
	}

	public void inviteFriend(CreateRelActivity newCRA) {
		/* send invite.xml(action=new) */
		/*
		 * If target user is onlined then udp a 'CRA_HELLO' record
		 */
		TDU craTdu = new TDU();
		int TAG = 0;
		User user = (User) newCRA.targetObject;
		if (user.onlineState) {
			Record record = new Record();
			record.data = "";
			record.dataType = RecordType.CRA_HELLO;
			record.senderID = loginer.ID;
			record.targetID = user.ID;
			record.targetType = TargetType.user;
			sendDataToUser(user, record, Controler.TO_SIGNALCLIENT_TAG);
		}
		craTdu.type = TDUType.CRA;
		craTdu.TAG = TAG | Controler.TO_SERVER_TAG;
		String[] text = { loginer.ID, newCRA.targetObject.ID, TargetType.user.name(), newCRA.notes, "new" };
		craTdu.setTextsByArray(text);
		controler.send(craTdu);
	}

	public boolean inviteFriendOK(Friend newFriend) {
		/* send invite.xml(action=delete) */
		/* send friendadd.xml */
		/* Note : may be can merge tow XML files */
		TDU craTdu=new TDU();
		craTdu.TAG=Controler.TO_SERVER_TAG;
		craTdu.type=TDUType.CRA;
		String[] text={loginer.ID,newFriend.ID,TargetType.user.name(),"","delete"};
		craTdu.setTextsByArray(text);
		controler.send(craTdu);
		
		TDU friendAddTdu=new TDU();
		friendAddTdu.TAG=Controler.TO_SERVER_TAG;
		friendAddTdu.type=TDUType.FRIENDADD;
		String[] fatext={loginer.ID,newFriend.ID,newFriend.groupname,newFriend.remark};
		friendAddTdu.setTextsByArray(fatext);
		controler.send(friendAddTdu);
		
		loginer.friends.add(newFriend);/**/
		
		return false;
	}

	public void inviteFriendFailed(String friendid) {
		/* send invite.xml(action=delete) */
		TDU craTdu=new TDU();
		craTdu.TAG=Controler.TO_SERVER_TAG;
		craTdu.type=TDUType.CRA;
		String[] text={loginer.ID,friendid,TargetType.user.name(),"","delete"};
		craTdu.setTextsByArray(text);
		controler.send(craTdu);
	}

	public boolean aggreFriendInvite(Friend newFriend) {
		/* send invite.xml(action=setok) */
		/* send friendadd.xml */
		/* Note : may be can merge tow XML files */
		/*
		 * If target user is onlined then udp a 'CRA_OK' record
		 */
		TDU craTdu=new TDU();
		craTdu.TAG=Controler.TO_SERVER_TAG;
		craTdu.type=TDUType.CRA;
		String[] text={newFriend.ID,loginer.ID,TargetType.user.name(),"","setok"};
		craTdu.setTextsByArray(text);
		controler.send(craTdu);
		
		TDU friendAddTdu=new TDU();
		friendAddTdu.TAG=Controler.TO_SERVER_TAG;
		friendAddTdu.type=TDUType.FRIENDADD;
		String[] fatext={loginer.ID,newFriend.ID,newFriend.groupname,newFriend.remark};
		friendAddTdu.setTextsByArray(fatext);
		controler.send(friendAddTdu);
		
		if(newFriend.onlineState){
			Record record = new Record();
			record.data = "";
			record.dataType = RecordType.CRA_OK;
			record.senderID = loginer.ID;
			record.targetID = newFriend.ID;
			record.targetType = TargetType.user;
			sendDataToUser(newFriend, record, Controler.TO_SIGNALCLIENT_TAG);
		}
		
		return true;
	}

	public void rejectFriendInvite(User noFriend) {
		/* send invite.xml(action=setfailed) */
		/*
		 * If target user is onlined then udp a 'CRA_FAILED' record
		 */
		TDU craTdu=new TDU();
		craTdu.TAG=Controler.TO_SERVER_TAG;
		craTdu.type=TDUType.CRA;
		String[] text={noFriend.ID,loginer.ID,TargetType.user.name(),"","setfailed"};
		craTdu.setTextsByArray(text);
		controler.send(craTdu);
		if(noFriend.onlineState){
			Record record = new Record();
			record.data = "";
			record.dataType = RecordType.CRA_FAILED;
			record.senderID = loginer.ID;
			record.targetID = noFriend.ID;
			record.targetType = TargetType.user;
			sendDataToUser(noFriend, record, Controler.TO_SIGNALCLIENT_TAG);
		}
	}

	public void ignoreFriendInvite(String ownerID, String userID) {
		/* send invite.xml(action=delete) */
		TDU craTdu=new TDU();
		craTdu.TAG=Controler.TO_SERVER_TAG;
		craTdu.type=TDUType.CRA;
		String[] text={userID,loginer.ID,TargetType.user.name(),"","delete"};
		craTdu.setTextsByArray(text);
		controler.send(craTdu);
	}

	/* API for flock */
	public void applyForFlock(CreateRelActivity newCRA) {
		/* send invite.xml(action=new) */
		/*
		 * If flock's creater is onlined then udp a 'CRA_HELLO' record
		 */
		TDU craTdu = new TDU();

		Flock flock = (Flock) newCRA.targetObject;
		User user = flock.getCreaterUser();
		if (user.onlineState) {
			Record record = new Record();
			record.data = "";
			record.dataType = RecordType.CRA_HELLO;
			record.senderID = loginer.ID;
			record.targetID = newCRA.targetObject.ID;
			record.targetType = TargetType.flock;
			sendDataToUser(user, record, Controler.TO_SIGNALCLIENT_TAG);
		}
		craTdu.type = TDUType.CRA;
		craTdu.TAG = Controler.TO_SERVER_TAG;
		String[] text = { loginer.ID, newCRA.targetObject.ID, TargetType.flock.name(), newCRA.notes, "new" };
		craTdu.setTextsByArray(text);
		controler.send(craTdu);
	}

	public boolean aggreFlockApplyFor(String flockID, String userID) {
		/* send apply.xml(action=setok) */
		/* send flockaddnumber.xml */
		/* send a record.xml(type=flock:HESHEJOIN) */
		/* udp all online number this record */
		/* Note : may be can merge tow XML files */
		/*
		 * If target user is onlined then udp a 'CRA_OK' record
		 */
		TDU craTdu=new TDU();
		craTdu.TAG=Controler.TO_SERVER_TAG;
		craTdu.type=TDUType.CRA;
		String[] text={userID,flockID,TargetType.user.name(),"","setok"};
		craTdu.setTextsByArray(text);
		controler.send(craTdu);
		
		TDU flockaddNumberTdu=new TDU();
		flockaddNumberTdu.TAG=Controler.TO_SERVER_TAG;
		flockaddNumberTdu.type=TDUType.FLOCKNUMBER;
		String[] fantext={flockID,userID,"add"};
		flockaddNumberTdu.setTextsByArray(fantext);
		controler.send(flockaddNumberTdu);
		
		
		return false;
	}

	public void rejectFlockApplyFor(String flockID, String userID) {
		/* send apply.xml(action=setfailed) */
		/*
		 * If target user is onlined then udp a 'CRA_FAILED' record
		 */
	}

	public void ignoreFlockApplyFor(String flockID, String userID) {
		/* send apply.xml(action=delete) */
	}

	public boolean applyForFlockOk(String flockID) {
		/* send apply.xml(action=delete) */
		/* Note : may be can merge tow XML files */
		return false;
	}

	public void applyForFlockFailed(String flockID) {
		/* send apply.xml(action=delete) */
	}

	public boolean deleteFriend(String friendID) {
		/* send frienddelete.xml */
		/* send a record.xml(type=user:breakoff) */
		/* udp a 'breakoff' record to friend */
		return false;
	}

	public boolean quitFlock(String flockID) {
		/* send flocknumberquit.xml */
		/* send a record.xml(type=flock:IAMQUIT) */
		/* udp all online number this record */
		return false;
	}

	public Flock createNewFlock(Flock newflock) {
		/* send flockcreate.xml : get flockcreate_back.xml */
		return newflock;
	}

	public boolean dissolveFlock(String flockID) {
		/* send flockdelete.xml */
		return false;
	}

	/*
	 * Tag : Controler.TO_*
	 */
	public void sendDataToUser(User user, Record record, int tag) {
		/* send record.xml(type=user:message or file) */
		/*
		 * if friend is onlined then udp a 'message or others' record
		 */
		TDU recordTdu = new TDU();
		recordTdu.type = TDUType.RECORD;
		recordTdu.TAG = tag;
		DateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		List<String> list = new ArrayList<>();
		list.add(record.dataType.name());
		list.add(record.senderID);
		if (record.dataType == RecordType.IAMBACK) {
			list.add(loginer.ipaddress);
			list.add(loginer.port);
		}
		list.add(record.targetID);
		list.add(record.targetType.name());
		list.add(format.format(new Date()));
		list.add("");
		recordTdu.setTextsByList(list);
		recordTdu.ONLINEUSER=user;
		controler.send(recordTdu);
	}

	public void sendDataToFlock(String flockID, Record record) {
		/* send record.xml(type=flock:message or file) */
		/* udp each of numbers of flock a 'message or others' record */
	}

	public void exit() {
		/* send exit.xml */
		/* udp each of online friend a 'GOODBAY' record */
	}
}
