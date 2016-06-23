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
	private Controler controler;

	public ClientMoudle() {
		controler = new Controler();
		loginer = null;
		/*monitor controler.comeTdus*/
		TDUMonitorHandle handle=new TDUMonitorHandle(controler,loginer);
		Thread thread=new Thread(handle);
		thread.setDaemon(true);
		thread.start();
	}

	public List<Record> getFriendRecords(Friend friend) {
		/* send recordreq.xml : get recordreq_back.xml */
		TDU recordReqTdu = new TDU();
		recordReqTdu.TAG = Controler.TO_SERVER_TAG;
		recordReqTdu.type = TDUType.RECORDREQ;
		String[] text = { loginer.ID, TargetType.user.name(), friend.ID, "1" };
		recordReqTdu.setTextsByArray(text);
		controler.send(recordReqTdu);

		TDU rrbackTdu = controler.recv(TDUType.RECORDREQBACK,false);//
		if (rrbackTdu == null)
			return null;
		return parserRecordReqBackTDU(rrbackTdu);
	}

	public List<Record> getFlockRecords(Flock flock) {
		/*
		 * List<Flock> myFlocks = loginer.flocks; if (!myFlocks.contains(flock))
		 * return null;
		 */
		/* send recordreq.xml : get recordreq_back.xml */
		TDU recordReqTdu = new TDU();
		recordReqTdu.TAG = Controler.TO_SERVER_TAG;
		recordReqTdu.type = TDUType.RECORDREQ;
		String[] text = { loginer.ID, TargetType.flock.name(), flock.ID, "1" };
		recordReqTdu.setTextsByArray(text);
		controler.send(recordReqTdu);

		TDU rrbackTdu = controler.recv(TDUType.RECORDREQBACK,false);//
		if (rrbackTdu == null)
			return null;

		return parserRecordReqBackTDU(rrbackTdu);
	}

	public Loginer login(String userID, String password) {
		/* send login.xml : get login_back.xml */
		/* udp each of online friend a 'IAMBACK' record */
		TDU loginTdu = new TDU(TDUType.LOGIN);
		loginTdu.TAG = Controler.TO_SERVER_TAG;
		String[] text = { userID, password, Controler.clientIP, Controler.clientPort };
		loginTdu.setTextsByArray(text);
		controler.send(loginTdu);

		TDU loginBackTdu = controler.recv(TDUType.LOGINBACK,true);// may be sleep;
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
		// friendlist
		int friendcount = Integer.valueOf(text[j++]);
		for (int i = 0; i < friendcount; i++) {
			Friend friend = new Friend();
			friend.ID = text[j++];
			friend.name = text[j++];
			friend.emailAddress = text[j++];
			friend.imageurl = text[j++];
			friend.onlineState = (text[j++].equals("Y")) ? true : false;
			if (friend.onlineState) {
				friend.ipaddress = text[j++];
				friend.port = text[j++];
				onlineFriends.add(friend);
			}
			friend.groupname = text[j++];
			friend.remark = text[j++];
			friend.hasSession = (text[j++].equals("Y")) ? true : false;
			loginer.fullNewFriend(friend);
		}

		// flocklist
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

		// crabacklist
		int crabackcount = Integer.valueOf(text[j++]);
		for (int i = 0; i < crabackcount; i++) {
			CreateRelActivity craback = new CreateRelActivity();
			craback.ownerID = text[j++];
			craback.targetType = TargetType.valueOf(text[j++]);
			craback.notes = text[j++];
			craback.state = Rel.valueOf(text[j++]);
			loginer.fullNewCRAback(craback);
		}

		// sendDataToUser(user, record, tag);
		// record all online friend
		Record record = new Record();
		record.senderID = loginer.ID;
		record.dataType = RecordType.IAMBACK;
		record.data = "";
		record.targetID = "";
		record.targetType = TargetType.user;

		sendDataToUsers(onlineFriends, record, Controler.TO_MANYCLIENT_TAG);
		return loginer;
	}

	public Loginer signin(User register, String password) {
		/* send signin.xml : get signin_back.xml */
		TDU signinTdu = new TDU();
		signinTdu.TAG = Controler.TO_SERVER_TAG;
		signinTdu.type = TDUType.SIGNIN;
		String[] text = { register.ID, password, register.name, register.emailAddress, register.imageurl,
				Controler.clientIP, Controler.clientPort };
		signinTdu.setTextsByArray(text);
		controler.send(signinTdu);

		TDU signinbackTdu = controler.recv(TDUType.SIGNINBACK,true);
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
		loginer.ipaddress = Controler.clientIP;
		loginer.port = Controler.clientPort;
		return loginer;
	}

	/*
	 * goal : "user","flock","all"
	 */
	public List<TalkObject> searchTalkObject(String searchKey,String goal) {
		/* send search.xml : get search_back.xml */
		TDU searchTdu = new TDU();
		searchTdu.TAG = Controler.TO_SERVER_TAG;
		searchTdu.type = TDUType.SEARCH;
		String[] text = { goal,searchKey };
		searchTdu.setTextsByArray(text);
		controler.send(searchTdu); // may be sleep

		TDU searchBackTdu = controler.recv( TDUType.SEARCHBACK,false);
		if (searchBackTdu == null)
			return null;
		text = searchBackTdu.getTextsArray();
		List<TalkObject> talkObjects = new ArrayList<>();
		int j = 0;
	    // userlist
		int usercount = Integer.valueOf(text[j++]);
		for (int i = 0; i < usercount; i++) {
			User user = new User();
			user.ID = text[j++];
			user.name = text[j++];
			user.emailAddress = text[j++];
			user.imageurl = text[j++];
			user.onlineState = (text[j++].equals("Y")) ? true : false;
			talkObjects.add(user);
		}
		
		// flocklist
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
		return talkObjects;
	}

	public void inviteFriend(CreateRelActivity newCRA) {
		/* send invite.xml(action=new) */
		/*
		 * If target user is onlined then (server do it )udp a 'CRA_HELLO'
		 * record
		 */
		TDU craTdu = new TDU();
		int TAG = 0;
		/*
		 * //User user = (User) newCRA.targetObject; if (user.onlineState) {
		 * Record record = new Record(); record.data = ""; // record.dataType =
		 * RecordType.CRA_HELLO; record.senderID = loginer.ID; record.targetID =
		 * user.ID; record.targetType = TargetType.user; List<User> userlist =
		 * new ArrayList<>(); userlist.add(loginer); sendDataToUser(userlist,
		 * record, Controler.TO_SIGNALCLIENT_TAG); }
		 */
		craTdu.type = TDUType.CRA;
		craTdu.TAG = TAG | Controler.TO_SERVER_TAG;
		String[] text = { loginer.ID, TargetType.user.name(), newCRA.targetID, newCRA.notes, "new" };
		craTdu.setTextsByArray(text);
		controler.send(craTdu);
	}

	public boolean inviteFriendOK(Friend newFriend) {
		/* send invite.xml(action=delete) */
		/* send friendadd.xml */
		/* Note : may be can merge tow XML files */
		TDU craTdu = new TDU();
		craTdu.TAG = Controler.TO_SERVER_TAG;
		craTdu.type = TDUType.CRA;
		String[] text = { loginer.ID, TargetType.user.name(), newFriend.ID, "", "delete" };
		craTdu.setTextsByArray(text);
		controler.send(craTdu);

		TDU friendAddTdu = new TDU();
		friendAddTdu.TAG = Controler.TO_SERVER_TAG;
		friendAddTdu.type = TDUType.FRIENDADD;
		String[] fatext = { loginer.ID, newFriend.ID, newFriend.groupname, newFriend.remark };
		friendAddTdu.setTextsByArray(fatext);
		controler.send(friendAddTdu);

		if (loginer.friends == null)
			loginer.friends = new ArrayList<>();
		loginer.friends.add(newFriend);/**/

		return false;
	}

	public void inviteFriendFailed(String friendid) {
		/* send invite.xml(action=delete) */
		TDU craTdu = new TDU();
		craTdu.TAG = Controler.TO_SERVER_TAG;
		craTdu.type = TDUType.CRA;
		String[] text = { loginer.ID, TargetType.user.name(), friendid, "", "delete" };
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
		TDU craTdu = new TDU();
		craTdu.TAG = Controler.TO_SERVER_TAG;
		craTdu.type = TDUType.CRA;
		String[] text = { newFriend.ID, TargetType.user.name(), loginer.ID, "", "setok" };
		craTdu.setTextsByArray(text);
		controler.send(craTdu);

		TDU friendAddTdu = new TDU();
		friendAddTdu.TAG = Controler.TO_SERVER_TAG;
		friendAddTdu.type = TDUType.FRIENDADD;
		String[] fatext = { loginer.ID, newFriend.ID, newFriend.groupname, newFriend.remark };
		friendAddTdu.setTextsByArray(fatext);
		controler.send(friendAddTdu);
		return true;
	}

	public void rejectFriendInvite(User noFriend) {
		/* send invite.xml(action=setfailed) */
		/*
		 * If target user is onlined then udp a 'CRA_FAILED' record
		 */
		TDU craTdu = new TDU();
		craTdu.TAG = Controler.TO_SERVER_TAG;
		craTdu.type = TDUType.CRA;
		String[] text = { noFriend.ID, TargetType.user.name(), loginer.ID, "", "setfailed" };
		craTdu.setTextsByArray(text);
		controler.send(craTdu);
		/*
		 * if (noFriend.onlineState) { Record record = new Record(); record.data
		 * = ""; // record.dataType = RecordType.CRA_FAILED; record.senderID =
		 * loginer.ID; record.targetID = noFriend.ID; record.targetType =
		 * TargetType.user; List<User> userlist = new ArrayList<>();
		 * userlist.add(noFriend); sendDataToUser(userlist, record,
		 * Controler.TO_SIGNALCLIENT_TAG); }
		 */
	}

	public void ignoreFriendInvite(String ownerID, String userID) {
		/* send invite.xml(action=delete) */
		TDU craTdu = new TDU();
		craTdu.TAG = Controler.TO_SERVER_TAG;
		craTdu.type = TDUType.CRA;
		String[] text = { userID, TargetType.user.name(), loginer.ID, "", "delete" };
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

		// Flock flock = (Flock) newCRA.targetObject;
		/*
		 * User user = flock.getCreaterUser(); if (user.onlineState) { Record
		 * record = new Record(); record.data = ""; // record.dataType =
		 * RecordType.CRA_HELLO; record.senderID = loginer.ID; record.targetID =
		 * newCRA.targetObject.ID; record.targetType = TargetType.flock;
		 * List<User> userlist = new ArrayList<>(); userlist.add(user);
		 * sendDataToUser(userlist, record, Controler.TO_SIGNALCLIENT_TAG); }
		 */
		craTdu.type = TDUType.CRA;
		craTdu.TAG = Controler.TO_SERVER_TAG;
		String[] text = { loginer.ID, TargetType.flock.name(), newCRA.targetID, newCRA.notes, "new" };
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
		TDU craTdu = new TDU();
		craTdu.TAG = Controler.TO_SERVER_TAG;
		craTdu.type = TDUType.CRA;
		String[] text = { userID, TargetType.flock.name(), flockID, "", "setok" };
		craTdu.setTextsByArray(text);
		controler.send(craTdu);

		TDU flockaddNumberTdu = new TDU();
		flockaddNumberTdu.TAG = Controler.TO_SERVER_TAG;
		flockaddNumberTdu.type = TDUType.FLOCKNUMBER;
		String[] fantext = { flockID, userID, "add" };
		flockaddNumberTdu.setTextsByArray(fantext);
		controler.send(flockaddNumberTdu);
		
		Flock flock = loginer.getFlock(flockID);
		User newuser = new User();
		newuser.ID = userID;
		flock.numbers.add(newuser);

		Record record=new Record();
		int TAG=Controler.TO_SERVER_TAG;
		record.data=userID+"join our group";
		record.dataType=RecordType.HESHEJOIN;
		record.senderID=loginer.ID;
		record.targetID=flockID;
		record.targetType=TargetType.flock;
		sendDataToFlock(flock, record, TAG);
		
		return true;
	}

	public void rejectFlockApplyFor(String flockID, String userID) {
		/* send apply.xml(action=setfailed) */
		/*
		 * If target user is onlined then udp a 'CRA_FAILED' record
		 */
		TDU craTdu = new TDU();
		craTdu.TAG = Controler.TO_SERVER_TAG;
		craTdu.type = TDUType.CRA;
		String[] text = { userID, TargetType.flock.name(), flockID, "", "setfailed" };
		craTdu.setTextsByArray(text);
		controler.send(craTdu);
	}

	public void ignoreFlockApplyFor(String flockID, String userID) {
		/* send apply.xml(action=delete) */
		TDU craTdu = new TDU();
		craTdu.TAG = Controler.TO_SERVER_TAG;
		craTdu.type = TDUType.CRA;
		String[] text = { userID, TargetType.flock.name(), flockID, "", "delete" };
		craTdu.setTextsByArray(text);
		controler.send(craTdu);
	}

	public boolean applyForFlockOk(String flockID) {
		/* send apply.xml(action=delete) */
		/* Note : may be can merge tow XML files */
		ignoreFlockApplyFor(flockID, loginer.ID);
		
		Flock flock = new Flock();
		flock.ID = flockID;
		if (loginer.flocks == null)
			loginer.flocks = new ArrayList<>();
		loginer.flocks.add(flock);
		return true;
	}

	public void applyForFlockFailed(String flockID) {
		/* send apply.xml(action=delete) */
		ignoreFlockApplyFor(flockID, loginer.ID);
	}

	public boolean deleteFriend(Friend friend) {
		/* send frienddelete.xml */
		/* send a record.xml(type=user:breakoff) */
		/* udp a 'breakoff' record to friend */
		TDU deleteFTdu = new TDU();
		deleteFTdu.TAG = Controler.TO_SERVER_TAG;
		deleteFTdu.type = TDUType.FRIENDDELETE;
		String[] textDFT = { loginer.ID, TargetType.user.name(), friend.ID };
		deleteFTdu.setTextsByArray(textDFT);
		controler.send(deleteFTdu);

		Record record = new Record();
		int TAG = 0;
		if (friend.onlineState)
			TAG |= Controler.TO_SIGNALCLIENT_TAG;
		record.data = "";
		record.dataType = RecordType.BREAKOFF;
		record.senderID = loginer.ID;
		record.targetID = friend.ID;
		record.targetType = TargetType.user;
		sendDataToSignalUser(friend, record, Controler.TO_SERVER_TAG | TAG);
		return true;
	}

	public boolean quitFlock(String flockID) {
		/* send flocknumberquit.xml */
		/* send a record.xml(type=flock:IAMQUIT) */
		/* udp all online number this record */

		TDU flockdeleteNumberTdu = new TDU();
		flockdeleteNumberTdu.TAG = Controler.TO_SERVER_TAG;
		flockdeleteNumberTdu.type = TDUType.FLOCKNUMBER;
		String[] fantext = { flockID, loginer.ID, "delete" };
		flockdeleteNumberTdu.setTextsByArray(fantext);
		controler.send(flockdeleteNumberTdu);

		Flock flock = loginer.getFlock(flockID);
		loginer.flocks.remove(flock);
		
		Record record=new Record();
		int TAG=Controler.TO_SERVER_TAG;
		record.data=loginer.ID+"quit our group";
		record.dataType=RecordType.IAMQUIT;
		record.senderID=loginer.ID;
		record.targetID=flockID;
		record.targetType=TargetType.flock;
		sendDataToFlock(flock, record, TAG);
		return true;
	}

	public Flock createNewFlock(Flock newflock) {
		/* send flockcreate.xml : get flockcreate_back.xml */
		/* server shall add loginer to new flock */
		TDU flockCreateTdu = new TDU();
		flockCreateTdu.TAG = Controler.TO_SERVER_TAG;
		flockCreateTdu.type = TDUType.FLOCKCREATE;
		String[] fcttext = { "", newflock.name, newflock.createrID, newflock.createDate, newflock.imageurl,
				newflock.notes };
		flockCreateTdu.setTextsByArray(fcttext);
		controler.send(flockCreateTdu);

		if (loginer.flocks == null)
			loginer.flocks = new ArrayList<>();
		loginer.flocks.add(newflock);
		newflock.numbers = new ArrayList<>();
		newflock.numbers.add(loginer);

		TDU flcbackTdu=controler.recv(TDUType.FLOCKCREATEBACK,false);
		if(flcbackTdu==null)
			return null;
		String[] fbttext=flcbackTdu.getTextsArray();
		newflock.ID=fbttext[0];
		return newflock;
	}

	public boolean dissolveFlock(String flockID) {
		/*
		 * send flockdelete.xml server shall send a record to numbers
		 */
		TDU flockdeleteTdu = new TDU();
		flockdeleteTdu.TAG = Controler.TO_SERVER_TAG;
		flockdeleteTdu.type = TDUType.FLOCKDELETE;
		String[] text = { flockID };
		flockdeleteTdu.setTextsByArray(text);
		controler.send(flockdeleteTdu);
		Flock flock = loginer.getFlock(flockID);
		loginer.flocks.remove(flock);
		return true;
	}

	/*
	 * Tag : Controler.TO_*
	 */
	public void sendDataToUsers(List<User> users, Record record, int tag) {
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
		list.add(record.targetType.name());
		list.add(record.targetID);
		list.add(format.format(new Date()));
		list.add("");
		recordTdu.setTextsByList(list);
		recordTdu.ONLINE_USER_LIST = users;
		controler.send(recordTdu);
	}

	public void sendDataToSignalUser(User user, Record record, int tag) {
		List<User> users = new ArrayList<>();
		users.add(user);
		sendDataToUsers(users, record, tag);
	}

	public void sendDataToFlock(Flock flock, Record record,int tag) {
		/* send record.xml(type=flock:message or file) */
		/* udp each of numbers of flock a 'message or others' record */
		TDU flockrecordTdu=new TDU();
		flockrecordTdu.TAG=tag;
		flockrecordTdu.type=TDUType.RECORD;
		String forwarding="Y";
		/*if(flock.numbers!=null){
			forwarding="N";
			List<User> users=new ArrayList<>();
			for(User user:flock.numbers){
				if(user.onlineState && !user.ID.equals(loginer.ID))
					users.add(user);
			}
			flockrecordTdu.ONLINE_USER_LIST=users;
			flockrecordTdu.TAG|=Controler.TO_MANYCLIENT_TAG;
		}*/
		DateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		String[] text={record.dataType.name(),loginer.ID,TargetType.flock.name(),
				flock.ID,format.format(new Date()),record.data,forwarding};
		flockrecordTdu.setTextsByArray(text);
		controler.send(flockrecordTdu);
	}

	public void exit() {
		/* send exit.xml */
		/* udp each of online friend a 'GOODBAY' record */
		TDU exitTdu = new TDU();
		exitTdu.TAG = Controler.TO_SERVER_TAG;
		exitTdu.type = TDUType.EXIT;
		List<String> list = new ArrayList<>();
		list.add(loginer.ID);
		// friendlist
		if (loginer.friends != null) {
			for (int i = 0; i < loginer.friends.size(); i++) {
				Friend friend = loginer.friends.get(i);
				list.add(friend.ID);
				list.add((friend.hasSession) ? "Y" : "N");
			}
		}
		exitTdu.setTextsByList(list);
		controler.send(exitTdu);
	}

	private List<Record> parserRecordReqBackTDU(TDU rrbacktdu) {
		int j = 0;
		String[] text = rrbacktdu.getTextsArray();
		int recordCount = Integer.valueOf(text[j++]);
		if (recordCount == 0)
			return null;
		// String beforeday="";
		List<Record> records = new ArrayList<>();
		for (int i = 0; i < recordCount; i++) {
			Record record = new Record();
			record.dataType = RecordType.valueOf(text[j++]);
			record.senderID = text[j++];
			record.targetType = TargetType.valueOf(text[j++]);
			record.targetID = text[j++];
			record.recordDate = text[j++];
			record.data = text[j++];
			records.add(record);
		}
		return records;
	}
}
