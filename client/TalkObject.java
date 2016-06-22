package client;


/**
 * This class is s super class
 * of User and Flock.
 * 
 */
public class TalkObject {
	String ID;
	String name;
	String imageurl;
	public TalkObject(){
		ID="";
		name="";
		imageurl="";
	}
	public TalkObject(String id,String name,String image){
		this.ID=id;
		this.name=name;
		this.imageurl=image;
	}
	public String toString(){
		return "id="+ID+",name="+name;
	}
}
