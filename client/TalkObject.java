package client;


/**
 * This class is s super class
 * of User and Flock.
 * 
 */
public class TalkObject {
	String ID;
	String name;
	byte[] image;
	public TalkObject(){
		ID="";
		name="";
		image=null;
	}
	public TalkObject(String id,String name,byte[] image){
		this.ID=id;
		this.name=name;
		this.image=image;
	}
	public String toString(){
		return "id="+ID+",name="+name;
	}
}
