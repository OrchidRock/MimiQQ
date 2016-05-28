package tools;

import java.util.ArrayList;
import java.util.List;

/**
 * The Transmit data unit
 *
 */
public class TDU {
	public List<String> texts=new ArrayList<>();
	public TDUType type;
	public enum TDUType{
		LOGIN,SIGNIN,RECORDREQ,RECORD,
		SEARCH,CRA,FRIENDADD,FLOCKNUMBER,FRIENDDELETE,
		FLOCKCREATE,FLOCKDELETE,FLOCKNUMBERSREQ,GETONLINEUSERAP,
		EXIT,
		LOGINBACK,SIGNINBACK,RECORDREQBACK,SEARCHBACK,
		FLOCKNUMBERSREQBACK,GETONLINEUSERAPBACK
	};
	public TDU(){
		type=TDUType.RECORD;
	}
	public TDU(TDUType type){
		this.type=type;
	}
	public void setTextsByArray(String[] tStrings){
		texts.clear();
		for(int i=0;i<tStrings.length;i++)
			texts.add(tStrings[i]);
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
