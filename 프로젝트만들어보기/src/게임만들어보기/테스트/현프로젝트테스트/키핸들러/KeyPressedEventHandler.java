package 게임만들어보기.테스트.현프로젝트테스트.키핸들러;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import 게임만들어보기.테스트.현프로젝트테스트.오브젝트.CreateMap;
import 게임만들어보기.테스트.현프로젝트테스트.오브젝트.CreateObjects;

public class KeyPressedEventHandler extends KeyHandler {
	
	public KeyPressedEventHandler(CreateObjects CO, CreateMap CM) {
		super(CO, CM);
	}
	
	@Override
	public void handle(KeyEvent event) {
		switch (event.getCode()) {
		case UP:
		case W:
			up = true;
			break;
		case DOWN:
		case S:
			down = true;
			break;
		case LEFT:
		case A:
			left = true;
			break;
		case RIGHT:
		case D:
			right = true;
			break;
		default:
			break;
		} //
		
		if(event.getCode() == KeyCode.SPACE && timelineRoTateTh1 == null && timelineRotate2 == null && touchedRect != null) {
			if(CM.CW.getWeapons().containsKey(touchedRect))
				attack();
			else if(CM.CF.getFoods().containsKey(touchedRect))
				eat();
			else if(CM.CWater.getWaters().containsKey(touchedRect))
				drink();
			else
				return;
		}

		if(Weaponrt == null || timelineRoTateTh1 != null || timelineRotate2 != null || CO.player == null)
			return;
		
		if(up && left) {
			Weaponrt.setAngle(-60);
			CO.player.toFront();
		} else if(up && right) {
			Weaponrt.setAngle(-120);
			CO.player.toFront();
		} else if(down && left) {
			touchedRect.toFront();
			Weaponrt.setAngle(-60);
		} else if(down && right) {
			touchedRect.toFront();
			Weaponrt.setAngle(-120);
		} else if(left) {
			touchedRect.toFront();
			Weaponrt.setAngle(-60);
		} else if(right) {
			touchedRect.toFront();
			Weaponrt.setAngle(-120);
		}
		
	}
	
}
