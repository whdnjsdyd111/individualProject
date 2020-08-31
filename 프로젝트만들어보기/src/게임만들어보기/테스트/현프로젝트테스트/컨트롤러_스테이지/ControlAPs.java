package 게임만들어보기.테스트.현프로젝트테스트.컨트롤러_스테이지;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

public class ControlAPs {
	public static AnchorPane[][] APs = new AnchorPane[5][5];
	public static double initX;
	public static double initY;
	public static boolean bottom = false;
	
	public static void setInit(Rectangle rect) {
		System.out.println(rect.getLayoutX());
		if(rect.getLayoutY() < 102) {
			initX = rect.getLayoutX();
			initY = 489;
			return;
		} else if(rect.getLayoutY() > 489) {
			initX = rect.getLayoutX();
			initY = 101;
			bottom = true;
			return;
		}
		
		if(rect.getLayoutX() < 102) {
			initY = rect.getLayoutY();
			initX = 789;
		} else if(rect.getLayoutX() > 789) {
			initY = rect.getLayoutY();
			initX = 101;
		}
	}
	
	public static void setInit() {
		initX = Math.random() * 100 + 300;
		int ranY = (int)(Math.random() * 2);
		
		if(ranY == 0)
			initY = Math.random() * 90 + 200;
		else
			initY = Math.random() * 90 + 400;
	}
}
