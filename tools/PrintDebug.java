package tools;

public class PrintDebug {
	public static enum ErrorType{
		USAGE_ERR,NULL_POINTER,DISCOMFOR
	}
	public static void PD(String className,String methodName,ErrorType type){
		String inform="";
		switch (type) {
		case USAGE_ERR:
			inform="UsageError..";
			break;
		case NULL_POINTER:
			inform="NULL pointer access..";
			break;
		case DISCOMFOR:
			inform="data discomfor...";
			break;
		default:
			break;
		}
		System.out.println(className+": "+methodName+"---"+": "+inform);
	}
}
