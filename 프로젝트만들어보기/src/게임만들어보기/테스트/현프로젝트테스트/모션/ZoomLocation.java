package ���Ӹ�����.�׽�Ʈ.��������Ʈ�׽�Ʈ.���;

import ���Ӹ�����.�׽�Ʈ.��������Ʈ�׽�Ʈ.��Ʈ�ѷ�_��������.ControlAPs;

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
