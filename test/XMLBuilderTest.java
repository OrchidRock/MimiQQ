package test;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.xml.stream.XMLStreamException;

import tools.XMLBuilder;
import tools.XMLBuilder.XBType;

public class XMLBuilderTest {

	public static void main(String[] args) {
		File file=new File("xmlbuildertest.xml");
		try {
			OutputStream outputStream=Files.newOutputStream(file.toPath());
			String[] logintexts={"10000000","puppy","localhost","8000"};
			XMLBuilder.builder(outputStream, XBType.LOGIN,logintexts);
			
			String[] signintexts={"1058712592","Rock","summer",
					  "rocksevencolor@gamil.com","1058712592.png","localhost","8001"};
			XMLBuilder.builder(outputStream, XBType.SIGNIN, signintexts);
			
			String[] recordreqtexts={"1058712592","user","10000000","1"};
			XMLBuilder.builder(outputStream, XBType.RECORDREQ,recordreqtexts);
			
			String[] searchtexts={"1058712592"};
			XMLBuilder.builder(outputStream, XBType.SEARCH,searchtexts);
			
			String[] cratexts={"1058712592","user","10000000",
					"Hello,I am rock","new"};
			XMLBuilder.builder(outputStream, XBType.CRA,cratexts);
			
			String[] friendaddtexts={"1058712592","1000000","goodfriend","LiLi"};
			XMLBuilder.builder(outputStream, XBType.FRIENDADD,friendaddtexts);
			
			Date date=new Date();
			DateFormat format=new SimpleDateFormat("yy-MM-dd HH:mm:ss");
			String[] flockcreatetexts={"LinuxHarcker","1058712592",format.format(date)
					,"linuxlog.png","This a linux hackers tribe"};
			XMLBuilder.builder(outputStream, XBType.FLOCKCREATE,flockcreatetexts);
			
			String[] flocknumbertexts={"LinuxHarcker","1058712592","add",};
			XMLBuilder.builder(outputStream, XBType.FLOCKNUMBER,flocknumbertexts);
			
			String[] frienddeletetexts={"1058712592","1000000"};
			XMLBuilder.builder(outputStream, XBType.FRIENDDELETE,frienddeletetexts);
			
			String[] flockdeletetexts={"LinuxHarcker"};
			XMLBuilder.builder(outputStream, XBType.FLOCKDELETE,flockdeletetexts);
			
			String[] flocknumbersreqtexts={"LinuxHarcker"};
			XMLBuilder.builder(outputStream, XBType.FLOCKNUMBERSREQ,flocknumbersreqtexts);
			
			String[] getonlineuseraptexts={"1000000"};
			XMLBuilder.builder(outputStream, XBType.GETONLINEUSERAP,getonlineuseraptexts);
			
			String[] exittexts={"1000000"};
			XMLBuilder.builder(outputStream, XBType.EXIT,exittexts);
			
			
			String[] recordtexts={"MESSAGE","1058712592","user","10000000",
					format.format(new Date()),"I Love you"};
			XMLBuilder.builder(outputStream, XBType.RECORD,recordtexts);
			outputStream.close();
			System.out.println("Done.");
		} catch (IOException | XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
