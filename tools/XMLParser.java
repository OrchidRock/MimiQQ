package tools;

import java.io.InputStream;

import javax.swing.text.AbstractDocument.LeafElement;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import tools.PrintDebug.ErrorType;
import tools.TDU.TDUType;

public class XMLParser {
	private static XMLInputFactory factory = XMLInputFactory.newInstance();

	public XMLParser() {
	}

	public static TDU parser(InputStream inputStream) throws XMLStreamException {
		// factory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, false);
		// factory.setProperty(XMLInputFactory.IS_VALIDATING, true);
		XMLStreamReader reader = factory.createXMLStreamReader(inputStream);
		// factory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, false);
		TDU tdu = new TDU();

		while (reader.hasNext()) {
			int event = reader.next();
			if (event == XMLStreamConstants.DTD) {
				System.out.println(reader.getText());
			}
			if ((event == XMLStreamConstants.START_ELEMENT) && reader.getLocalName().equals("mimiqq")) {
				reader.nextTag();
				TDUType xptype = TDUType.valueOf(reader.getLocalName().toUpperCase());
				switch (xptype) {
				case LOGINBACK:
					loginback(reader, tdu);
					break;
				case SIGNINBACK:
					signinback(reader, tdu);
					break;
				case RECORDREQBACK:
					recordreqback(reader, tdu);
					break;
				case SEARCHBACK:
					searchback(reader, tdu);
					break;
				case FLOCKNUMBERSREQBACK:
					flocknumbersreqback(reader, tdu);
					break;
				case GETONLINEUSERAPBACK:
					getonlineuserapback(reader, tdu);
					break;
				case RECORD:
					tdu.type = TDUType.RECORD;
					tdu.clear();
					record(reader, tdu);
					break;
				case CRABACK:
					tdu.type=TDUType.CRABACK;
					tdu.clear();
					craback(reader,tdu);
					break;
				case FLOCKCREATEBACK:
					flockcreateback(reader,tdu);
					break;
				default:
					PrintDebug.PD("XMLParser", "builder", ErrorType.USAGE_ERR);
					break;
				}
				break;
			}
		}
		return tdu;
	}

	

	private static void flockcreateback(XMLStreamReader reader, TDU tdu) throws XMLStreamException {
		tdu.type = TDUType.FLOCKCREATEBACK;
		tdu.clear();
		while (reader.hasNext()) {
			int event = reader.next();
			if (event == XMLStreamConstants.START_ELEMENT && reader.getLocalName().equals("fid")) {
				findIWantTextAndToTDU(reader, "fid", tdu, false);
			}
		}
	}

	private static void loginback(XMLStreamReader reader, TDU tdu) throws XMLStreamException {
		tdu.type = TDUType.LOGINBACK;
		tdu.clear();
		while (reader.hasNext()) {
			int event = reader.next();
			if (event == XMLStreamConstants.START_ELEMENT) {
				switch (reader.getLocalName()) {
				case "user":
					reader.nextTag();
					findIWantTextAndToTDU(reader, "uid", tdu, true);
					findIWantTextAndToTDU(reader, "nickname", tdu, true);
					findIWantTextAndToTDU(reader, "email", tdu, true);
					findIWantTextAndToTDU(reader, "imageurl", tdu, false);
					break;
				case "friendlist":
					parserFriendList(reader, tdu);
					break;
				case "flocklist":
					parserFlockList(reader, tdu);
					break;
				case "crabacklist":
					parserCrabackList(reader, tdu);
					break;
				case "recordlist":
					parserRecordList(reader, tdu);
					break;
				default:
					PrintDebug.PD("XMLParser", "loginback", ErrorType.USAGE_ERR);
					break;
				}
			}
		}
	}

	private static void signinback(XMLStreamReader reader, TDU tdu) throws XMLStreamException {
		tdu.type = TDUType.SIGNINBACK;
		tdu.clear();
		while (reader.hasNext()) {
			int event = reader.next();
			if (event == XMLStreamConstants.START_ELEMENT && reader.getLocalName().equals("uid")) {
				findIWantTextAndToTDU(reader, "uid", tdu, false);
			}
		}
	}

	private static void recordreqback(XMLStreamReader reader, TDU tdu) throws XMLStreamException {
		tdu.type = TDUType.RECORDREQ;
		tdu.clear();
		while (reader.hasNext()) {
			int event = reader.next();
			if (event == XMLStreamConstants.START_ELEMENT) {
				switch (reader.getLocalName()) {
				case "recordlist":
					parserRecordList(reader, tdu);
					break;
				case "beforeday":
					findIWantTextAndToTDU(reader, "beforeday", tdu, false);
					break;
				default:
					PrintDebug.PD("XMLParser", "recordreqback", ErrorType.USAGE_ERR);
					break;
				}
			}
		}
	}

	private static void searchback(XMLStreamReader reader, TDU tdu) throws XMLStreamException {
		tdu.type = TDUType.SEARCHBACK;
		tdu.clear();
		while (reader.hasNext()) {
			int event = reader.next();
			if (event == XMLStreamConstants.START_ELEMENT) {
				switch (reader.getLocalName()) {
				case "userlist":
					parserUserList(reader, tdu);
					break;
				case "flocklist":
					parserFlockList(reader, tdu);
					break;
				default:
					PrintDebug.PD("XMLParser", "searchback", ErrorType.USAGE_ERR);
					break;
				}
			}
		}
	}

	private static void flocknumbersreqback(XMLStreamReader reader, TDU tdu) throws XMLStreamException {
		tdu.type = TDUType.FLOCKNUMBERSREQBACK;
		tdu.clear();
		while (reader.hasNext()) {
			int event = reader.next();
			if (event == XMLStreamConstants.START_ELEMENT) {
				findIWantTextAndToTDU(reader, "fid", tdu, true);
				parserUserList(reader, tdu);
			}
		}
	}

	private static void getonlineuserapback(XMLStreamReader reader, TDU tdu) throws XMLStreamException {
		tdu.type = TDUType.GETONLINEUSERAPBACK;
		tdu.clear();
		while (reader.hasNext()) {
			int event = reader.next();
			if (reader.isEndElement() && reader.getLocalName().equals("getonlineuserapback"))
				break;
			if (event == XMLStreamConstants.START_ELEMENT && reader.getLocalName().equals("user")) {
				reader.nextTag();
				findIWantTextAndToTDU(reader, "uid", tdu, true);
				findIWantTextAndToTDU(reader, "ipaddress", tdu, true);
				findIWantTextAndToTDU(reader, "openport", tdu, false);
			}
		}
	}

	private static void record(XMLStreamReader reader, TDU tdu) throws XMLStreamException {
		while (reader.hasNext()) {
			if (reader.isEndElement() && reader.getLocalName().equals("record"))
				break;
			if (reader.isStartElement()) {
				findIWantAttributeAndToTDU(reader, "record", tdu);
				reader.nextTag();
				findIWantTextAndToTDU(reader, "ownerid", tdu, true);
				findIWantAttributeAndToTDU(reader, "targetid", tdu);
				tdu.append(reader.getText());
				reader.nextTag();
				reader.nextTag();
				findIWantTextAndToTDU(reader, "recorddate", tdu, true);
				findIWantTextAndToTDU(reader, "data", tdu, false);
			}
			reader.next();
		}
	}
	private static void craback(XMLStreamReader reader, TDU tdu) throws XMLStreamException {
		while (reader.hasNext()) {
			if (reader.isEndElement() && reader.getLocalName().equals("craback"))
				break;
			if(reader.isStartElement() && reader.getLocalName().equals("craback")){
				reader.nextTag();
				findIWantTextAndToTDU(reader, "ownerid", tdu, true);
				findIWantAttributeAndToTDU(reader, "targetid", tdu);
				tdu.append(reader.getText());
				reader.nextTag();
				reader.nextTag();
				findIWantTextAndToTDU(reader, "notes", tdu, true);
				//findIWantTextAndToTDU(reader, "state", tdu, false);
				findIWantAttributeAndToTDU(reader, "state", tdu);
			}
			reader.next();
		}
	}
	private static void parserFriendList(XMLStreamReader reader, TDU tdu) throws XMLStreamException {
		while (reader.hasNext()) {
			int event = reader.nextTag();
			if (reader.isEndElement() && reader.getLocalName().equals("friendlist"))
				break;
			if ((event == XMLStreamConstants.START_ELEMENT) && reader.getLocalName().equals("listlength")) {
				String listlength = findIWantTextAndToTDU(reader, "listlength", tdu, true);
				for (int i = 0; i < Integer.valueOf(listlength); i++) {
					reader.nextTag();//skip <friend> tag
					reader.nextTag();//skip <user> tag
					findIWantTextAndToTDU(reader, "uid", tdu, true);
					findIWantTextAndToTDU(reader, "nickname", tdu, true);
					findIWantTextAndToTDU(reader, "email", tdu, true);
					findIWantTextAndToTDU(reader, "imageurl", tdu, true);
					String olstype=findIWantAttributeAndToTDU(reader, "onlinestate", tdu);
					if(olstype.equals("Y")){
						reader.nextTag();
						findIWantTextAndToTDU(reader, "ipaddress", tdu, true);
						findIWantTextAndToTDU(reader, "openport", tdu, true);
					}
					//reader.nextTag();
					reader.nextTag();//skip </user> tag
					findIWantTextAndToTDU(reader, "groupname", tdu, true);
					findIWantTextAndToTDU(reader, "remark", tdu, true);
					findIWantAttributeAndToTDU(reader, "hassession", tdu);
				}
			}
		}
	}

	private static void parserFlockList(XMLStreamReader reader, TDU tdu) throws XMLStreamException {
		while (reader.hasNext()) {
			int event = reader.nextTag();
			if (reader.isEndElement() && reader.getLocalName().equals("flocklist"))
				break;
			if ((event == XMLStreamConstants.START_ELEMENT) && reader.getLocalName().equals("listlength")) {
				String listlength = findIWantTextAndToTDU(reader, "listlength", tdu, true);
				for (int i = 0; i < Integer.valueOf(listlength); i++) {
					reader.nextTag();
					findIWantTextAndToTDU(reader, "fid", tdu, true);
					findIWantTextAndToTDU(reader, "name", tdu, true);
					findIWantTextAndToTDU(reader, "createrid", tdu, true);
					findIWantTextAndToTDU(reader, "createdate", tdu, true);
					findIWantTextAndToTDU(reader, "imageurl", tdu, true);
					findIWantTextAndToTDU(reader, "notes", tdu, false);
				}
			}
		}
	}

	private static void parserCrabackList(XMLStreamReader reader, TDU tdu) throws XMLStreamException {
		while (reader.hasNext()) {
			int event = reader.nextTag();
			//System.out.println(reader.getLocalName());
			if (reader.isEndElement() && reader.getLocalName().equals("crabacklist"))
				break;
			if ((event == XMLStreamConstants.START_ELEMENT) && reader.getLocalName().equals("listlength")) {
				String listlength = findIWantTextAndToTDU(reader, "listlength", tdu, false);
				for (int i = 0; i < Integer.valueOf(listlength); i++) {
					reader.nextTag();
					craback(reader, tdu);
					//reader.nextTag();
				}
			}
		}
	}

	private static void parserRecordList(XMLStreamReader reader, TDU tdu) throws XMLStreamException {
		while (reader.hasNext()) {
			if (reader.isEndElement() && reader.getLocalName().equals("recordlist"))
				break;
			int event = reader.next();
			if ((event == XMLStreamConstants.START_ELEMENT) && reader.getLocalName().equals("listlength")) {
				String listlength = findIWantTextAndToTDU(reader, "listlength", tdu, true);
				for (int i = 0; i < Integer.valueOf(listlength); i++) {
					record(reader, tdu);
					reader.nextTag();
				}
			}
		}
	}

	private static void parserUserList(XMLStreamReader reader, TDU tdu) throws XMLStreamException {
		while (reader.hasNext()) {
			if (reader.isEndElement() && reader.getLocalName().equals("userlist"))
				break;
			int event = reader.next();
			if ((event == XMLStreamConstants.START_ELEMENT) && reader.getLocalName().equals("listlength")) {
				String listlength = findIWantTextAndToTDU(reader, "listlength", tdu, true);
				for (int i = 0; i < Integer.valueOf(listlength); i++) {
					reader.nextTag();
					//System.out.println(reader.getLocalName());
					findIWantTextAndToTDU(reader, "uid", tdu, true);
					findIWantTextAndToTDU(reader, "nickname", tdu, true);
					findIWantTextAndToTDU(reader, "email", tdu, true);
					findIWantTextAndToTDU(reader, "imageurl", tdu, true);
					String olstype=findIWantAttributeAndToTDU(reader, "onlinestate", tdu);
					if(olstype.equals("Y")){
						reader.nextTag();
						findIWantTextAndToTDU(reader, "ipaddress", tdu, true);
						findIWantTextAndToTDU(reader, "openport", tdu, true);
						reader.nextTag();
						continue;
					}
					reader.nextTag();
					reader.nextTag();
				}
			}
		}
	}

	@Deprecated
	private static void setNextTextToTDU(XMLStreamReader reader, TDU tdu) throws XMLStreamException {
		reader.next();
		if (reader.isCharacters()) {
			tdu.append(reader.getText());
			reader.next(); /* skip end_element */
		} else
			PrintDebug.PD("XMLParser", "setNextTextToTDU", ErrorType.DISCOMFOR);

	}

	/*
	 * IF not find then set "unknow" to TDU.
	 */
	private static String findIWantTextAndToTDU(XMLStreamReader reader, String elementname, TDU tdu, boolean tag)
			throws XMLStreamException {
		String result = null;
		if (reader.getLocalName().equals(elementname)) {
			reader.next();
			tdu.append(reader.getText());
			result = reader.getText();
			reader.nextTag();
			if (tag)
				reader.nextTag();
		} else {
			tdu.append("unknow");
			result="unknow";
		}
		return result;
	}

	private static String findIWantAttributeAndToTDU(XMLStreamReader reader, String elementname, TDU tdu)
			throws XMLStreamException {
		String result=null;
		if (reader.getLocalName().equals(elementname)) {
			tdu.append(reader.getAttributeValue(0));
			result=reader.getAttributeValue(0);
			reader.next();
		} else {
			tdu.append("unknow");
			result="unknow";
		}
		return result;
	}
}
