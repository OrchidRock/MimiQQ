package client;

import java.util.List;

public class Loginer extends User{
	protected List<Friend> friends;
	protected List<Flock> flocks;
	
	public Loginer(){
		friends=null;
		flocks=null;
	}
	public List<Friend> getFriendList(){
		return friends;
	}
	public List<Flock> getFlockList(){
		return flocks;
	}
}
