package tools;

import java.util.ArrayList;
import java.util.List;

import client.User;

/**
 * The Transmit data unit
 *
 */
public class TDU {
	private List<String> texts=new ArrayList<>();
	public TDUType type;
	public int TAG=0;
	//public User ONLINEUSER=null;
	public List<User> ONLINE_USER_LIST=null;
	
	public enum TDUType{
		LOGIN,SIGNIN,RECORDREQ,RECORD,
		SEARCH,CRA,CRABACK,FRIENDADD,FLOCKNUMBER,FRIENDDELETE,
		FLOCKCREATE,FLOCKDELETE,FLOCKNUMBERSREQ,GETONLINEUSERAP,
		EXIT,
		LOGINBACK,SIGNINBACK,RECORDREQBACK,SEARCHBACK,FLOCKCREATEBACK,
		FLOCKNUMBERSREQBACK,GETONLINEUSERAPBACK
	};
	public TDU(){
		/*default type */
		type=null;
	}
	public TDU(TDUType type){
		this.type=type;
	}
	public void setTextsByArray(String[] tStrings){
		texts.clear();
		for(int i=0;i<tStrings.length;i++)
			texts.add(tStrings[i]);
	}
	public void setTextsByList(List<String> list){
		texts=list;
	}
	public String[] getTextsArray(){
		String[] t=new String[texts.size()];
		for(int i=0;i<texts.size();i++){
			t[i]=texts.get(i);
		}
		return t;
	}
	public String toString(){
		String r=type.name()+"{";
		for(int i=0;i<texts.size();i++){
			r+=texts.get(i)+",";
		}
		r+="}";
		return r;
	}
	public void clear(){
		texts.clear();
	}
	public void append(String t){
		texts.add(t);
	}
}
