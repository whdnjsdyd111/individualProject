package 게임만들어보기.테스트.현프로젝트테스트.키핸들러;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import 게임만들어보기.테스트.현프로젝트테스트.오브젝트.CreateMap;
import 게임만들어보기.테스트.현프로젝트테스트.오브젝트.CreateObjects;

public class KeyReleasedHandler extends KeyHandler {	// 키를 땠을 때
	
	public KeyReleasedHandler(CreateObjects CO, CreateMap CM) {
		super(CO, CM);
	}
	
	@Override
	public void handle(KeyEvent event) {	// 키 알아내기
		
		switch (event.getCode()) {
		case UP:
		case W:
			up = false;
			break;
		case DOWN:
		case S:
			down = false;
			break;
		case LEFT:
		case A:
			left = false;
			break;
		case RIGHT:
		case D:
			right = false;
			break;
		default:
			break;
		}
		
		if(CO.player == null)
			return;
		
		if(!(Weaponrt == null || timelineRoTateTh1 != null || timelineRotate2 != null)){
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
		
		if(event.getCode() == KeyCode.Z) {		// 무기를 들거나 놓는 키
			if(touchedRect == null) {		// 무기를 집지 않고 있는 것으로 판단
				touchedRect = touchObj(CO.player.getLayoutX(), CO.player.getLayoutY());		// 닿고 있는 오브젝트가 있다면 해당 오브젝트 반환
				if(touchedRect != null) {		// 닿고 있는 오브젝트가 있다면
					mountItem();		// 무기 집기
				}
			} else {
				if(timelineRoTateTh1 != null) {
					return;
				}
				
				if(timelineRotate2 != null) {
					return;
				}
				
				dropItem();
				CO.inventory.toFront();
				
			}
		}
		
		if(event.getCode() == KeyCode.I) {
			if(CO.inventory.isVisible())
				CO.inventory.setVisible(false);
			else {
				CO.inventory.setVisible(true);
				CO.inventory.toFront();
			}
		}
		
		if(event.getCode() == KeyCode.X && timelineRoTateTh1 == null) {
			if(touchedRect == null)
				CO.inventory.getItems();
			else 
				CO.inventory.setItems(touchedRect);
		}
		
		if(event.getCode().isDigitKey()) {
			try {
				int i = Integer.parseInt(event.getCode().toString().substring(5));
				if(1 <= i && i <= 8) {
					CO.inventory.getItems(i);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
}
