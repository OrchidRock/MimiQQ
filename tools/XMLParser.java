package tools;

import java.awt.RadialGradientPaint;
import java.io.InputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.sun.swing.internal.plaf.basic.resources.basic;

import tools.PrintDebug.ErrorType;
import tools.TDU.TDUType;

public class XMLParser {
	private static XMLInputFactory factory=XMLInputFactory.newInstance();
	public XMLParser(){
	}
	public static TDU parser(InputStream inputStream) throws XMLStreamException{
		//factory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, false);
		//factory.setProperty(XMLInputFactory.IS_VALIDATING, true);
		XMLStreamReader reader=factory.createXMLStreamReader(inputStream);
		TDU tdu=new TDU();
		
		while(reader.hasNext()){
			int event=reader.next();
			if((event==XMLStreamConstants.START_ELEMENT)
					&& reader.getLocalName().equals("mimiqq")){
				reader.next();
				TDUType xptype=TDUType.valueOf(reader.getLocalName().toUpperCase());
				switch (xptype) {
				case LOGINBACK:
					loginback(reader, tdu);break;
				case SIGNINBACK:
					signinback(reader, tdu);break;
				case RECORDREQBACK:
					recordreqback(reader, tdu);break;
				case SEARCHBACK:
					searchback(reader, tdu);break;
				case FLOCKNUMBERSREQBACK:
					flocknumbersreqback(reader, tdu);break;
				case GETONLINEUSERAPBACK:
					getonlineuserapback(reader, tdu);break;
				case RECORD:
					tdu.type=TDUType.RECORD;
					tdu.clear();
					record(reader, tdu);break;
				default:
					PrintDebug.PD("XMLParser", "builder", ErrorType.USAGE_ERR);
					break;
				}
				break;
			}
		}
		return tdu;
	} 
	private static void loginback(XMLStreamReader reader,TDU tdu) throws XMLStreamException{
		tdu.type=TDUType.LOGINBACK;
		tdu.clear();
		while(reader.hasNext()){
			int event=reader.next();
			if(event==XMLStreamConstants.START_ELEMENT){
				switch (reader.getLocalName()) {
				case "user":
					reader.next();
					findIWantTextAndToTDU(reader,"nickname",tdu,true);
					findIWantTextAndToTDU(reader, "email", tdu,true);
					findIWantTextAndToTDU(reader, "imageurl", tdu,false);
					break;
				case "friendlist":
					parserFriendList(reader, tdu);break;
				case "flocklist":
					parserFlockList(reader, tdu);break;
				case "crabacklist":
					parserCrabackList(reader, tdu);break;
				default:
					PrintDebug.PD("XMLParser", "loginback", ErrorType.USAGE_ERR);
					break;
				}
			}
		}
	}
	
	private static void signinback(XMLStreamReader reader,TDU tdu) throws XMLStreamException{
		tdu.type=TDUType.SIGNINBACK;
		tdu.clear();
		while(reader.hasNext()){
			int event=reader.next();
			if(event==XMLStreamConstants.START_ELEMENT){
				findIWantTextAndToTDU(reader, "uid", tdu,false);
			}
		}
	}
	private static void recordreqback(XMLStreamReader reader,TDU tdu) throws XMLStreamException{
		tdu.type=TDUType.RECORDREQ;
		tdu.clear();
		while(reader.hasNext()){
			int event=reader.next();
			if(event==XMLStreamConstants.START_ELEMENT){
				switch (reader.getLocalName()) {
				case "recordlist":
					parserRecordList(reader,tdu);break;
				case "beforeday":
					findIWantTextAndToTDU(reader, "beforeday", tdu);break;
				default:
					PrintDebug.PD("XMLParser", "recordreqback", ErrorType.USAGE_ERR);
					break;
				}
			}
		}
	}
	private static void searchback(XMLStreamReader reader,TDU tdu) throws XMLStreamException{
		tdu.type=TDUType.SEARCHBACK;
		tdu.clear();
		while(reader.hasNext()){
			int event=reader.next();
			if(event==XMLStreamConstants.START_ELEMENT){
				switch (reader.getLocalName()) {
				case "userlist":
					parserUserList(reader,tdu);break;
				case "flocklist":
					parserFlockList(reader, tdu);break;
				default:
					PrintDebug.PD("XMLParser", "searchback", ErrorType.USAGE_ERR);
					break;	
				}
			}
		}
	}
	private static void flocknumbersreqback(XMLStreamReader reader,TDU tdu) throws XMLStreamException{
		tdu.type=TDUType.FLOCKNUMBERSREQBACK;
		tdu.clear();
		while(reader.hasNext()){
			int event=reader.next();
			if(event==XMLStreamConstants.START_ELEMENT){
				findIWantTextAndToTDU(reader, "fid", tdu,true);
				parserUserList(reader, tdu);
			}
		}
	}
	private static void getonlineuserapback(XMLStreamReader reader,TDU tdu) throws XMLStreamException{
		tdu.type=TDUType.GETONLINEUSERAPBACK;
		tdu.clear();
		while(reader.hasNext()){
			int event=reader.next();
			if(reader.isEndElement() && reader.getLocalName().equals("getonlineuserapback"))
				break;
			if(event==XMLStreamConstants.START_ELEMENT
					&& reader.getLocalName().equals("user")){
				reader.next();
				findIWantTextAndToTDU(reader, "uid", tdu,true);
				findIWantTextAndToTDU(reader, "ipaddress", tdu,true);
				findIWantTextAndToTDU(reader, "openport", tdu,false);
			}
		}
	}
	private static void record(XMLStreamReader reader,TDU tdu) throws XMLStreamException{
		while(reader.hasNext()){
			if(reader.isEndElement() && reader.getLocalName().equals("record"))
				break;
			if(reader.isStartElement()){
				findIWantAttributeAndToTDU(reader, "record", tdu);
				findIWantTextAndToTDU(reader, "ownerid", tdu,true);
				findIWantAttributeAndToTDU(reader, "targetid", tdu);
				tdu.append(reader.getText());
				reader.next();
				reader.next();
				findIWantTextAndToTDU(reader, "recorddate", tdu,true);
				findIWantTextAndToTDU(reader, "data", tdu,false);
			}
			reader.next();
		}
	}
	private static void parserFriendList(XMLStreamReader reader,TDU tdu) throws XMLStreamException{
		while(reader.hasNext()){
			int event=reader.next();
			if(reader.isEndElement() && reader.getLocalName().equals("friendlist"))
				break;
			if((event==XMLStreamConstants.START_ELEMENT)
					&&reader.getLocalName().equals("friend")){
				reader.next();
				findIWantTextAndToTDU(reader, "uid", tdu,true);
				findIWantTextAndToTDU(reader, "nickname", tdu,true);
				findIWantTextAndToTDU(reader, "email", tdu,true);
				findIWantTextAndToTDU(reader, "imageurl", tdu,true);
				findIWantAttributeAndToTDU(reader,"onlinestate",tdu);
				reader.next();
				findIWantTextAndToTDU(reader, "groupname", tdu,true);
				findIWantTextAndToTDU(reader, "remark", tdu,true);
				findIWantAttributeAndToTDU(reader, "hassession", tdu);
			}
		}
	}
	private static void parserFlockList(XMLStreamReader reader,TDU tdu) throws XMLStreamException{
		while(reader.hasNext()){
			int event=reader.next();
			if(reader.isEndElement() && reader.getLocalName().equals("flocklist"))
				break;
			if((event==XMLStreamConstants.START_ELEMENT)
					&& reader.getLocalName().equals("flock")){
					reader.next();
					findIWantTextAndToTDU(reader, "fid", tdu,true);
					findIWantTextAndToTDU(reader, "name", tdu,true);
					findIWantTextAndToTDU(reader, "createrid", tdu,true);
					findIWantTextAndToTDU(reader, "createdate", tdu,true);
					findIWantTextAndToTDU(reader, "imageurl", tdu,true);
					findIWantTextAndToTDU(reader, "notes", tdu,false);
				}
			}
	}
	private static void parserCrabackList(XMLStreamReader reader,TDU tdu) throws XMLStreamException{
		while(reader.hasNext()){
			int event=reader.next();
			if(reader.isEndElement() && reader.getLocalName().equals("crabacklist"))
				break;
			if((event==XMLStreamConstants.START_ELEMENT)
					&& reader.getLocalName().equals("craback")){
				reader.next();
				findIWantTextAndToTDU(reader, "ownerid", tdu,true);
				findIWantAttributeAndToTDU(reader, "targetid", tdu);
				tdu.append(reader.getText());
				reader.next();
				reader.next();
				findIWantTextAndToTDU(reader, "notes", tdu,true);
				findIWantTextAndToTDU(reader, "state", tdu,false);
			}
		}
	}
	private static void parserRecordList(XMLStreamReader reader, TDU tdu) throws XMLStreamException {
		while(reader.hasNext()){
			if(reader.isEndElement() && reader.getLocalName().equals("recordlist"))
				break;
			int event=reader.next();
			if((event==XMLStreamConstants.START_ELEMENT)
					&& reader.getLocalName().equals("record")){
				record(reader, tdu);
			}
		}
	}
	private static void parserUserList(XMLStreamReader reader, TDU tdu) throws XMLStreamException {
		while(reader.hasNext()){
			if(reader.isEndElement() && reader.getLocalName().equals("userlist"))
				break;
			int event=reader.next();
			if((event==XMLStreamConstants.START_ELEMENT)
					&& reader.getLocalName().equals("user")){
				reader.next();
				findIWantTextAndToTDU(reader, "uid", tdu,true);
				findIWantTextAndToTDU(reader,"nickname",tdu,true);
				findIWantTextAndToTDU(reader, "email", tdu,true);
				findIWantTextAndToTDU(reader, "imageurl", tdu,true);
				findIWantAttributeAndToTDU(reader, "onlinestate", tdu);
			}
		}
	}
	@Deprecated
	private static void setNextTextToTDU(XMLStreamReader reader,TDU tdu) throws XMLStreamException{
		reader.next();
		if(reader.isCharacters()){
			tdu.append(reader.getText());
			reader.next(); /*skip end_element*/
		}
		else
			PrintDebug.PD("XMLParser", "setNextTextToTDU", ErrorType.DISCOMFOR);
			
	}
	/*
	 *IF not find then set "unknow" to TDU. 
	 */
	private static void findIWantTextAndToTDU(XMLStreamReader reader,String elementname, TDU tdu) throws XMLStreamException {
		if(reader.getLocalName().equals(elementname)){
			reader.next();
			tdu.append(reader.getText());
			reader.next();
		}else{
			tdu.append("unknow");
		}
	}
	private static void findIWantTextAndToTDU(XMLStreamReader reader,String elementname, TDU tdu,boolean tag) throws XMLStreamException {
		if(reader.getLocalName().equals(elementname)){
			reader.next();
			tdu.append(reader.getText());
			reader.next();
			if(tag) reader.next();
		}else{
			tdu.append("unknow");
		}
	}
	private static void findIWantAttributeAndToTDU(XMLStreamReader reader, String elementname, TDU tdu) throws XMLStreamException {
		if(reader.getLocalName().equals(elementname)){
			tdu.append(reader.getAttributeValue(0));
			reader.next();
		}else{
			tdu.append("unknow");
		}
	}
}