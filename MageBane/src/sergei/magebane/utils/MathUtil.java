package sergei.magebane.utils;

public class MathUtil {
	public static float distance(float x1, float y1, float x2, float y2){
		return (float) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}
	
	public static double hAngle(float x1, float y1, float x2, float y2){
		return 360 - ((Math.toDegrees(Math.atan2(x2-x1,y2-y1)) + 630) % 360);
	}
}
