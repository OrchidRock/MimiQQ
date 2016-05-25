package client;

import java.util.List;

public class Loginer extends User{
	protected List<Friend> friends;
	protected List<Flock> flocks;
	
	public Loginer(){
		friends=null;
		flocks=null;
	}
	/**
	 * The getFriendList() and getFlockList()
	 * method are breaking the encapsulation character
	 * of OOP.(We can use clone() to avoid this problem)
	 */
	@Deprecated
	public List<Friend> getFriendList(){
		return friends;
	}
	@Deprecated
	public List<Flock> getFlockList(){
		return flocks;
	}
}
