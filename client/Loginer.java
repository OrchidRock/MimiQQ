package client;

import java.util.List;

public class Loginer extends User{
	List<Friend> friends;
	List<Flock> flocks;
	List<CreateRelActivity> createRelActivities;
	
	public Loginer(){
		super();
		friends=null;
		flocks=null;
		createRelActivities=null;
	}
}
