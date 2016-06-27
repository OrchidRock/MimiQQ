package client;

import java.util.ArrayList;
import java.util.List;

public class Loginer extends User{
	List<Friend> friends;
	List<Flock> flocks;
	List<CreateRelActivity> createRelActivities;
	List<Record> newRecords;
	public Loginer(){
		super();
		friends=null;
		flocks=null;
		createRelActivities=null;
		newRecords=null;
	}
	public void fullNewFriend(Friend friend){
		if(friends==null)
			friends=new ArrayList<>();
		friends.add(friend);
	}
	public void fullNewFlock(Flock flock){
		if(flocks==null)
			flocks=new ArrayList<>();
		flocks.add(flock);
	}
	public void fullNewCRAback(CreateRelActivity craback){
		if(createRelActivities==null)
			createRelActivities=new ArrayList<>();
		createRelActivities.add(craback);
	}
	public Flock getFlock(String flockID){
		if(flocks==null)
			return null;
		for(Flock  flock : flocks){
			if(flock.ID.equals(flockID))
				return flock;
		}
		return null;
	}
}
