package client;

import java.util.List;

class Flock{
	private String flockID;
	private String flockName;
	private String createrID;
	private String createData;
	private byte[] image;
	
	List<User> numbers;/*If myRole is applicant,then numbers list
	 					 is null. id myRole is nnumber,the numbers is the 
	 					 all numbers.If myrole is creater,the numbers include
	 					 all numbers and all applicant*/
	String myRole;/*creater or number or applicant*/
	public Flock(){
		numbers=null;
	}
	
	public String getFlockID(){
		return flockID;
	}
}
