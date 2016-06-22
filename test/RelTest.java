package test;
import client.*;
import client.CreateRelActivity.*;
public class RelTest {
	public static void main(String[] args) {
		Rel test=Rel.valueOf(Rel.class, "OK");
		System.out.println(test.name());
	}
}
