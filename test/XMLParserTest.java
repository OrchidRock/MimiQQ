package test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import javax.xml.stream.XMLStreamException;

import tools.TDU;
import tools.XMLParser;

public class XMLParserTest {

	public static void main(String[] args) {
		//File file=new File("loginback.xml");
		//File file=new File("signinback.xml");
		//File file=new File("xmlbuildertest.xml");
		File file=new File("recordreqback.xml");
		//File file=new File("searchback.xml");
		try {
			InputStream inputStream=Files.newInputStream(file.toPath());
			TDU tdu=XMLParser.parser(inputStream);
			System.out.println(tdu.toString());
			inputStream.close();
		} catch (IOException | XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
