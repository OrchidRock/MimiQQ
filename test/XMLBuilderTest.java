package test;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.xml.stream.XMLStreamException;

import tools.TDU;
import tools.XMLBuilder;
import tools.TDU.TDUType;

public class XMLBuilderTest {

	public static void main(String[] args) {
		File file=new File("xmlbuildertest.xml");
		try {
			OutputStream outputStream=Files.newOutputStream(file.toPath());
			TDU tdu=new TDU();
			DateFormat format=new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		/*	tdu.type=TDUType.LOGIN;
			String[] logintexts={"10000000","puppy","localhost","8000"};
			tdu.setTextsByArray(logintexts);
			XMLBuilder.builder(outputStream,tdu);
			
			tdu.type=TDUType.SIGNIN;
			String[] signintexts={"1058712592","Rock","summer",
					  "rocksevencolor@gamil.com","1058712592.png","localhost","8001"};
			tdu.setTextsByArray(signintexts);
			XMLBuilder.builder(outputStream, tdu);
			
			tdu.type=TDUType.RECORDREQ;
			String[] recordreqtexts={"1058712592","user","10000000","1"};
			tdu.setTextsByArray(recordreqtexts);
			XMLBuilder.builder(outputStream, tdu);
			
			tdu.type=TDUType.SEARCH;
			String[] searchtexts={"1058712592"};
			tdu.setTextsByArray(searchtexts);
			XMLBuilder.builder(outputStream, tdu);
			
			tdu.type=TDUType.CRA;
			String[] cratexts={"1058712592","user","10000000",
					"Hello,I am rock","new"};
			tdu.setTextsByArray(cratexts);
			XMLBuilder.builder(outputStream, tdu);
			
			tdu.type=TDUType.FRIENDADD;
			String[] friendaddtexts={"1058712592","1000000","goodfriend","LiLi"};
			tdu.setTextsByArray(friendaddtexts);
			XMLBuilder.builder(outputStream, tdu);
			
			tdu.type=TDUType.FLOCKCREATE;
			Date date=new Date();
			String[] flockcreatetexts={"LinuxHarcker","1058712592",format.format(date)
					,"linuxlog.png","This a linux hackers tribe"};
			tdu.setTextsByArray(flockcreatetexts);
			XMLBuilder.builder(outputStream, tdu);
			
			tdu.type=TDUType.FLOCKNUMBER;
			String[] flocknumbertexts={"LinuxHarcker","1058712592","add",};
			tdu.setTextsByArray(flocknumbertexts);
			XMLBuilder.builder(outputStream, tdu);
			
			tdu.type=TDUType.FRIENDDELETE;
			String[] frienddeletetexts={"1058712592","1000000"};
			tdu.setTextsByArray(frienddeletetexts);
			XMLBuilder.builder(outputStream, tdu);
			
			tdu.type=TDUType.FLOCKDELETE;
			String[] flockdeletetexts={"LinuxHarcker"};
			tdu.setTextsByArray(flockdeletetexts);
			XMLBuilder.builder(outputStream, tdu);
			
			tdu.type=TDUType.FLOCKNUMBERSREQ;
			String[] flocknumbersreqtexts={"LinuxHarcker"};
			tdu.setTextsByArray(flocknumbersreqtexts);
			XMLBuilder.builder(outputStream, tdu);
			
			tdu.type=TDUType.GETONLINEUSERAP;
			String[] getonlineuseraptexts={"1000000"};
			tdu.setTextsByArray(getonlineuseraptexts);
			XMLBuilder.builder(outputStream, tdu);
			
			tdu.type=TDUType.EXIT;
			String[] exittexts={"1000000"};
			tdu.setTextsByArray(exittexts);
			XMLBuilder.builder(outputStream, tdu);
			*/
			tdu.type=TDUType.RECORD;
			String[] recordtexts={"MESSAGE","1058712592","user","10000000",
					format.format(new Date()),"I Love you"};
			tdu.setTextsByArray(recordtexts);
			XMLBuilder.builder(outputStream, tdu);
			outputStream.close();
			System.out.println("Done.");
		} catch (IOException | XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
