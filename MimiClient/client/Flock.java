package client;

import java.util.ArrayList;
import java.util.List;

public class Flock extends TalkObject{
	 String flockName;
	 String createrID;
	 String createDate;
	 byte[] image;
	 
	public static enum FlockNumberRoleType{
		CREATE,NNUMBER,APPLICANT
	} 
	
	List<FlcokNumber> numbers;/*If myRole is applicant,then numbers list
	 					 is null. id myRole is nnumber,the numbers is the 
	 					 all numbers.If myrole is creater,the numbers include
	 					 all numbers and all applicant*/
	public Flock(){
		numbers=null;
		flockName=null;
		createDate=null;
		createrID=null;
		image=null;
	} 
	public List<FlcokNumber> getFlockNumbers(){
		return numbers;
	}
	public boolean isApplicant(){
		return numbers==null;
	}
	public void addNewNumber(FlcokNumber flcokNumber){
		if(numbers==null)
			numbers=new ArrayList<>();
		numbers.add(flcokNumber);
	}
	public String getCreaterID(){
		if(createrID==null){
			if(numbers==null){
				for(FlcokNumber number:numbers){
					if(number.myType==FlockNumberRoleType.CREATE)
						return number.getTalkObjectID();
				}
			}
		}else
			return createrID;
		return null;
	}
	public void setCreaterID(String id){
		createrID=id;
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
		return ID.equals(otherFlock.getTalkObjectID());
	}
	
	//static subclass extends User.
		static class FlcokNumber extends User{
			FlockNumberRoleType myType;
			public FlcokNumber(){
				super();
			}
		}
}
