package client;

import java.util.ArrayList;
import java.util.List;

import client.CreateRelActivity.Rel;

public class Flock extends TalkObject{
	String createrID;
	String createDate;	 
	String notes;
	List<User> numbers;
	public Flock(){
		super();
		numbers=null;
		createDate="";
		createrID="";
		notes="";
	} 
	@Override
	public boolean equals(Object other){
		if(this==other)
			return true;
		if(other==null)
			return false;
		if(getClass()!=other.getClass())
			return false;
		Flock otherFlock=(Flock)other;
		return ID.equals(otherFlock.ID);
	}
	
}
