package tools;

import java.io.OutputStream;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import tools.PrintDebug.ErrorType;


public class XMLBuilder {
	private static XMLOutputFactory factory=XMLOutputFactory.newInstance();
	public enum XBType{
		LOGIN,SIGNIN,RECORDREQ,RECORD,
		SEARCH,CRA,FRIENDADD,FLOCKNUMBER,FRIENDDELETE,
		FLOCKCREATE,FLOCKDELETE,FLOCKNUMBERSREQ,GETONLINEUSERAP,
		EXIT
	};
	public XMLBuilder(){}
	public static void builder(OutputStream outputStream,XBType xbtype,String[] texts) throws XMLStreamException{
		XMLStreamWriter writer=factory.createXMLStreamWriter(outputStream, "UTF-8");
		writer.writeStartDocument();
		writer.writeDTD("<!DOCTYPE mimiqq SYSTEM \"xml/mimiqq.dtd\">");
		writer.writeStartElement("mimiqq");
		writer.writeDefaultNamespace("www.orchid.party/mimiqq");
		switch (xbtype) {
		case LOGIN:
			login(writer, texts);break;
		case SIGNIN:
			signin(writer, texts);break;
		case RECORD:
			record(writer, texts);break;
		case RECORDREQ:
			recordreq(writer, texts);break;
		case SEARCH:
			search(writer, texts);break;
		case CRA:
			CRA(writer,texts);break;
		case FRIENDADD:
			friendadd(writer, texts);break;
		case FLOCKNUMBER:
			flocknumber(writer, texts);break;
		case FRIENDDELETE:
			frienddelete(writer, texts);break;
		case FLOCKCREATE:
			flockcreate(writer, texts);break;
		case FLOCKDELETE:
			flockdelete(writer, texts);break;
		case FLOCKNUMBERSREQ:
			flocknumbersreq(writer, texts);break;
		case GETONLINEUSERAP:
			getonlineuserap(writer, texts);break;
		case EXIT:
			exit(writer, texts);break;
		default:
			PrintDebug.PD("XMLBuilder", "builder", ErrorType.DISCOMFOR);
			break;
		}
		
		writer.writeEndDocument();
	}
	private static void login(XMLStreamWriter writer,String[] texts) throws XMLStreamException{
		writer.writeStartElement("login");
		writer.writeStartElement("user");
		writer.writeStartElement("uid");
		writer.writeCharacters(texts[0]);
		writer.writeEndElement();
		writer.writeStartElement("password");
		writer.writeCharacters(texts[1]);
		writer.writeEndElement();
		writer.writeStartElement("ipaddress");
		writer.writeCharacters(texts[2]);
		writer.writeEndElement();
		writer.writeStartElement("openport");
		writer.writeCharacters(texts[3]);
	}
	private static void signin(XMLStreamWriter writer,String[] texts) throws XMLStreamException{
		writer.writeStartElement("signin");
		writer.writeStartElement("user");
		writer.writeStartElement("password");
		writer.writeCharacters(texts[1]);
		writer.writeEndElement();
		writer.writeStartElement("nickname");
		writer.writeCharacters(texts[2]);
		writer.writeEndElement();
		writer.writeStartElement("email");
		writer.writeCharacters(texts[3]);
		writer.writeEndElement();
		writer.writeStartElement("imageurl");
		writer.writeCharacters(texts[4]);
		writer.writeEndElement();
		writer.writeStartElement("ipaddress");
		writer.writeCharacters(texts[5]);
		writer.writeEndElement();
		writer.writeStartElement("openport");
		writer.writeCharacters(texts[6]);
	}
	private static void record(XMLStreamWriter writer,String[] texts) throws XMLStreamException{
		writer.writeStartElement("record");
		writer.writeAttribute("recordtype", texts[0]);
		writer.writeStartElement("ownerid");
		writer.writeCharacters(texts[1]);
		writer.writeEndElement();
		writer.writeStartElement("targetid");
		writer.writeAttribute("type", texts[2]);
		writer.writeCharacters(texts[3]);
		writer.writeEndElement();
		writer.writeStartElement("recorddate");
		writer.writeCharacters(texts[4]);
		writer.writeEndElement();
		writer.writeStartElement("data");
		writer.writeCharacters(texts[5]);
		writer.writeEndElement();
		
	}
	private static void recordreq(XMLStreamWriter writer,String[] texts) throws XMLStreamException{
		writer.writeStartElement("recordreq");
		writer.writeStartElement("ownerid");
		writer.writeCharacters(texts[0]);
		writer.writeEndElement();
		writer.writeStartElement("targetid");
		writer.writeAttribute("type", texts[1]);
		writer.writeCharacters(texts[2]);
		writer.writeEndElement();
		writer.writeStartElement("beforeday");
		writer.writeCharacters(texts[3]);
	}
	private static void search(XMLStreamWriter writer,String[] texts) throws XMLStreamException{
		writer.writeStartElement("search");
		writer.writeStartElement("searchkey");
		writer.writeCharacters(texts[0]);
		writer.writeEndElement();
	}
	private static void CRA(XMLStreamWriter writer,String[] texts) throws XMLStreamException{
		writer.writeStartElement("CRA");
		writer.writeStartElement("cra");
		writer.writeStartElement("ownerid");
		writer.writeCharacters(texts[0]);
		writer.writeEndElement();
		writer.writeStartElement("targetid");
		writer.writeAttribute("type", texts[1]);
		writer.writeCharacters(texts[2]);
		writer.writeEndElement();
		writer.writeStartElement("notes");
		writer.writeCharacters(texts[3]);
		writer.writeEndElement();
		writer.writeEmptyElement("action");
		writer.writeAttribute("actiontype", texts[4]);
		writer.writeEndElement();
	}
	private static void friendadd(XMLStreamWriter writer,String[] texts) throws XMLStreamException{
		writer.writeStartElement("friendadd");
		writer.writeStartElement("uid");
		writer.writeCharacters(texts[0]);
		writer.writeEndElement();
		writer.writeStartElement("friend");
		writer.writeStartElement("uid");
		writer.writeCharacters(texts[1]);
		writer.writeEndElement();
		writer.writeStartElement("groupname");
		writer.writeCharacters(texts[2]);
		writer.writeEndElement();
		writer.writeStartElement("remark");
		writer.writeCharacters(texts[3]);
		writer.writeEndElement();
	}
	private static void flocknumber(XMLStreamWriter writer,String[] texts) throws XMLStreamException{
		writer.writeStartElement("flocknumber");
		writer.writeStartElement("fid");
		writer.writeCharacters(texts[0]);
		writer.writeEndElement();
		writer.writeStartElement("uid");
		writer.writeCharacters(texts[1]);
		writer.writeEndElement();
		writer.writeEmptyElement("operator");
		writer.writeAttribute("optype", texts[2]);
		writer.writeEndElement();
	}
	private static void frienddelete(XMLStreamWriter writer,String[] texts) throws XMLStreamException{
		writer.writeStartElement("frienddelete");
		writer.writeStartElement("ownerid");
		writer.writeCharacters(texts[0]);
		writer.writeEndElement();
		writer.writeStartElement("targetid");
		writer.writeCharacters(texts[1]);
		writer.writeEndElement();
	}
	private static void flockcreate(XMLStreamWriter writer,String[] texts) throws XMLStreamException{
		writer.writeStartElement("flockcreate");
		writer.writeStartElement("flock");
		writer.writeStartElement("name");
		writer.writeCharacters(texts[0]);
		writer.writeEndElement();
		writer.writeStartElement("createrid");
		writer.writeCharacters(texts[1]);
		writer.writeEndElement();
		writer.writeStartElement("createdate");
		writer.writeCharacters(texts[2]);
		writer.writeEndElement();
		writer.writeStartElement("imageurl");
		writer.writeCharacters(texts[3]);
		writer.writeEndElement();
		writer.writeStartElement("notes");
		writer.writeCharacters(texts[4]);
		writer.writeEndElement();
	}
	private static void flockdelete(XMLStreamWriter writer,String[] texts) throws XMLStreamException{
		writer.writeStartElement("flockdelete");
		writer.writeStartElement("fid");
		writer.writeCharacters(texts[0]);
		writer.writeEndElement();
	}
	private static void flocknumbersreq(XMLStreamWriter writer,String[] texts) throws XMLStreamException{
		writer.writeStartElement("flocknumberreq");
		writer.writeStartElement("fid");
		writer.writeCharacters(texts[0]);
		writer.writeEndElement();
	}
	private static void getonlineuserap(XMLStreamWriter writer,String[] texts) throws XMLStreamException{
		writer.writeStartElement("getonlineuserap");
		writer.writeStartElement("uid");
		writer.writeCharacters(texts[0]);
		writer.writeEndElement();
	}
	private static void exit(XMLStreamWriter writer,String[] texts) throws XMLStreamException{
		writer.writeStartElement("exit");
		writer.writeStartElement("uid");
		writer.writeCharacters(texts[0]);
		writer.writeEndElement();
		writer.writeStartElement("friendlist");
		for(int i=1;i<texts.length;i++){
			writer.writeStartElement("friend");
			writer.writeStartElement("uid");
			writer.writeEmptyElement("hassession");
			writer.writeAttribute("hstype", texts[i]);
			writer.writeEndElement();
			writer.writeEndElement();
			writer.writeEndElement();
		}
		writer.writeEndElement();
	}
	
}





















