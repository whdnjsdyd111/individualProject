package ���Ӹ�����.�׽�Ʈ.��������Ʈ�׽�Ʈ.Ű�ڵ鷯;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import ���Ӹ�����.�׽�Ʈ.��������Ʈ�׽�Ʈ.������Ʈ.CreateMap;
import ���Ӹ�����.�׽�Ʈ.��������Ʈ�׽�Ʈ.������Ʈ.CreateObjects;

public class KeyReleasedHandler extends KeyHandler {	// Ű�� ���� ��
	
	public KeyReleasedHandler(CreateObjects CO, CreateMap CM) {
		super(CO, CM);
	}
	
	@Override
	public void handle(KeyEvent event) {	// Ű �˾Ƴ���
		
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
		
		if(event.getCode() == KeyCode.Z) {		// ���⸦ ��ų� ���� Ű
			if(touchedRect == null) {		// ���⸦ ���� �ʰ� �ִ� ������ �Ǵ�
				touchedRect = touchObj(CO.player.getLayoutX(), CO.player.getLayoutY());		// ��� �ִ� ������Ʈ�� �ִٸ� �ش� ������Ʈ ��ȯ
				if(touchedRect != null) {		// ��� �ִ� ������Ʈ�� �ִٸ�
					mountItem();		// ���� ����
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
