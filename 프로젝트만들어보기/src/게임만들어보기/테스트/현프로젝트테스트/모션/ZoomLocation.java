package 게임만들어보기.테스트.현프로젝트테스트.모션;

import 게임만들어보기.테스트.현프로젝트테스트.컨트롤러_스테이지.ControlAPs;

public class ZoomLocation {
	public static double currentZoomX;
	public static double currentZoomY;
	private static double scale = 3.3;
	
	public static double getScale() {
		return scale;
	}
	
	public static void setScale(double s) {
		scale = s;
	}
	
	public static void setInit() {
		currentZoomX =  ControlAPs.initX -1650 + ControlAPs.initX / 3 * 7;
		currentZoomY =  ControlAPs.initY -1150 + ControlAPs.initY / 3 * 7;
	}
}
