package ���Ӹ�����.�׽�Ʈ.��������Ʈ�׽�Ʈ.Ű�ڵ鷯;

import java.util.Map;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import ���Ӹ�����.�׽�Ʈ.��������Ʈ�׽�Ʈ.������Ʈ.CreateMap;
import ���Ӹ�����.�׽�Ʈ.��������Ʈ�׽�Ʈ.������Ʈ.CreateObjects;

public class KeyHandler implements EventHandler<KeyEvent> {
	public static boolean up = false;
	public static boolean down = false;
	public static boolean left = false;
	public static boolean right = false;
	
	@FXML static public Rotate Weaponrt = null;
	@FXML static public Rectangle touchedRect = null;
	protected CreateObjects CO;
	protected CreateMap CM;
	
	
	public static double hypotenuse = 0;		// �ֵθ��� ���� ����
	protected boolean attacking = false;
	
	public static Thread timelineRoTateTh1 = null;
	public static @FXML Timeline timelineRotate2 = null;
	
	@Override
	public void handle(KeyEvent event) {
		
	}
	
	public KeyHandler(CreateObjects CO, CreateMap CM) {
		this.CO = CO;
		this.CM = CM;
	}
	
	public Rectangle touchObj(double playerx, double playery) {
		
		double rectX1 = 0.0;
		double rectY1 = 0.0;
		double rectX2 = 0.0;
		double rectY2 = 0.0;
		
		for (int i = 0; i < CO.PassObj.size(); i++) {
			rectX1 = CO.PassObj.get(i).getLayoutX() - CO.player.getWidth();
			rectX2 = CO.PassObj.get(i).getLayoutX() + CO.PassObj.get(i).getWidth();
			rectY1 = CO.PassObj.get(i).getLayoutY() - CO.player.getHeight();
			rectY2 = CO.PassObj.get(i).getLayoutY() + CO.PassObj.get(i).getHeight();

			if((playerx >= rectX1 && playerx <= rectX2) && (playery >= rectY1 && playery <= rectY2)) {	// �ش� �������� ���� ��
				return CO.PassObj.get(i);
			}
		}
		
		return null;
		
	}
	
	public void attack() {			// ���� �ϱ�
		if(touchedRect == null || attacking)	// ���� ���Ⱑ ���ų� ���� ���̶�� �������� �ʱ�
			return;
		
		Rotate rotation = new Rotate();		// ���� ��ü
        rotation.pivotXProperty().bind(touchedRect.rotateProperty());		// ���� ��ü ������ ������ ���ε�
        rotation.pivotYProperty().bind(touchedRect.rotateProperty());		// ���� ��ü ������ ������ ���ε�
        touchedRect.getTransforms().add(rotation);							// ������ Ʈ�������� ���� ��ü �߰�
		
		if(Weaponrt.getAngle() == -120) {			// ���� �� ������ -120 �϶�
			timelineRoTateTh1 = new Thread(() -> {		// ���� ��� ������ ����		(Timeline���� �� �� ������ ����ȭ�Ͽ� �������� �ʾ� Thread�� ��ü)
	        	double StartRt = 0;		// ���� ����
	        	double EndRt = 210;		// �� ����
	        	double turn = EndRt / 100;	// ������ ���� ��
	        	
	        	while(StartRt <= EndRt) {
	        		rotation.setAngle(StartRt += turn);
	        		touchEnemy120(rotation);		// ������ ���� ������ ��ġ�� ����
	        		try {
	        			Thread.sleep(1);			// 0.001�� ���� ���� �Ͽ� 0.1�ʷ� ��
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
	        	}

	        	timelineRotate2 = new Timeline(		// �ֵθ� ���⸦ ���� ������ �ǵ�����
	                    new KeyFrame(Duration.ZERO, new KeyValue(rotation.angleProperty(), 210)),
	                    new KeyFrame(Duration.seconds(0.5), new KeyValue(rotation.angleProperty(), 0)));
	        	

	        	CO.initDamegedBoolean();		// �ֵθ� �� ������ �Ծ������� �Ǻ��� false�� �ǵ���
	    		timelineRotate2.setCycleCount(1);	// ���� ���� ������ �ǵ����� Timeline ����
	    		
	    		timelineRotate2.setOnFinished( e -> {
	    			attacking = false;		// ���� ������ �ǵ����� ���� ���� false�� 
	    			timelineRotate2 = null;	// timeline2�� �η� �����ֱ�
	    			timelineRoTateTh1 = null;	// ����
	    		});
	        	timelineRotate2.play();
	        	
			});
		
			timelineRoTateTh1.setName("Attack Motion");
			timelineRoTateTh1.start();
			attacking = true;	// ���� ����� �����ϰ� ���� ���� true��
		
		} else {
			
			timelineRoTateTh1 = new Thread(() -> {
	        	double StartRt = 0;
	        	double EndRt = -210;
	        	double turn = EndRt / 100;
	        	
	        	while(StartRt >= EndRt) {
	        		rotation.setAngle(StartRt += turn);
	        		touchEnemy60(rotation);
	        		try {
	        			Thread.sleep(1);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
	        	}
	        	
	        	timelineRotate2 = new Timeline(
	                    new KeyFrame(Duration.ZERO, new KeyValue(rotation.angleProperty(), -210)),
	                    new KeyFrame(Duration.seconds(0.5), new KeyValue(rotation.angleProperty(), 0))
	                    );
	        	
	        	CO.initDamegedBoolean();
	    		timelineRotate2.setCycleCount(1);
	    		
	    		timelineRotate2.setOnFinished( e -> {
	    			attacking = false;
	    			timelineRotate2 = null;
	    			timelineRoTateTh1 = null;
	    		});
	        	timelineRotate2.play();
			});
		
			timelineRoTateTh1.setName("Attack Motion");
			timelineRoTateTh1.start();
			attacking = true;
		}
	}
	
	public void eat() {
		if(touchedRect == null || attacking)	// ���� ���Ⱑ ���ų� ���� ���̶�� �������� �ʱ�
			return;
		
		if(Weaponrt.getAngle() == -120) {			// ���� �� ������ -120 �϶�

	        timelineRotate2 = new Timeline(		// �ֵθ� ���⸦ ���� ������ �ǵ�����
	        		new KeyFrame(Duration.ZERO, new KeyValue(touchedRect.layoutXProperty(), touchedRect.getLayoutX()), 
	        				new KeyValue(touchedRect.layoutYProperty(), touchedRect.getLayoutY())),
	        		new KeyFrame(Duration.seconds(0.15), new KeyValue(touchedRect.layoutXProperty(), touchedRect.getLayoutX() - 5),
	        				new KeyValue(touchedRect.layoutYProperty(), touchedRect.getLayoutY() + 10)),
	        		new KeyFrame(Duration.seconds(0.3), new KeyValue(touchedRect.layoutXProperty(), touchedRect.getLayoutX()), 
	        				new KeyValue(touchedRect.layoutYProperty(), touchedRect.getLayoutY())));
	        	
	    	timelineRotate2.setCycleCount(3);	// ���� ���� ������ �ǵ����� Timeline ����
	    		
	    	timelineRotate2.setOnFinished( e -> {
	    		attacking = false;		// ���� ������ �ǵ����� ���� ���� false�� 
	    		timelineRotate2 = null;	// timeline2�� �η� �����ֱ�
	    		CO.upEnergy();
	    	});
	    		
	    	timelineRotate2.play();
			attacking = true;	// ���� ����� �����ϰ� ���� ���� true��
		
		} else {
			timelineRotate2 = new Timeline(		// �ֵθ� ���⸦ ���� ������ �ǵ�����
	        		new KeyFrame(Duration.ZERO, new KeyValue(touchedRect.layoutXProperty(), touchedRect.getLayoutX()), 
	        				new KeyValue(touchedRect.layoutYProperty(), touchedRect.getLayoutY())),
	        		new KeyFrame(Duration.seconds(0.15), new KeyValue(touchedRect.layoutXProperty(), touchedRect.getLayoutX() - 10),
	        				new KeyValue(touchedRect.layoutYProperty(), touchedRect.getLayoutY() + 5)),
	        		new KeyFrame(Duration.seconds(0.3), new KeyValue(touchedRect.layoutXProperty(), touchedRect.getLayoutX()), 
	        				new KeyValue(touchedRect.layoutYProperty(), touchedRect.getLayoutY())));
	        	
	    	timelineRotate2.setCycleCount(3);
	    		
	    	timelineRotate2.setOnFinished( e -> {
	    		attacking = false;
	    		timelineRotate2 = null;
	    		CO.upEnergy();
	    	});
	    	
	        timelineRotate2.play();
	        attacking = true;
		}
	}
	
	public void drink() {
		if(touchedRect == null || attacking)	// ���� ���Ⱑ ���ų� ���� ���̶�� �������� �ʱ�
			return;
		
		Weaponrt.setAngle(-180);
		timelineRotate2 = new Timeline(		// �ֵθ� ���⸦ ���� ������ �ǵ�����
				new KeyFrame(Duration.ZERO, new KeyValue(touchedRect.layoutXProperty(), touchedRect.getLayoutX() + 3), 
						new KeyValue(touchedRect.layoutYProperty(), touchedRect.getLayoutY())),
	        	new KeyFrame(Duration.seconds(0.15), new KeyValue(touchedRect.layoutXProperty(), touchedRect.getLayoutX() + 3),
	        			new KeyValue(touchedRect.layoutYProperty(), touchedRect.getLayoutY() - 10)),
	        	new KeyFrame(Duration.seconds(0.3), new KeyValue(touchedRect.layoutXProperty(), touchedRect.getLayoutX() + 3), 
	        			new KeyValue(touchedRect.layoutYProperty(), touchedRect.getLayoutY())));
		
		timelineRotate2.setCycleCount(3);	// ���� ���� ������ �ǵ����� Timeline ����
	    		
	    timelineRotate2.setOnFinished( e -> {
	    	attacking = false;		// ���� ������ �ǵ����� ���� ���� false�� 
	    	timelineRotate2 = null;	// timeline2�� �η� �����ֱ�
	    	CO.upThirst();
	    });
	    		
	    timelineRotate2.play();
		attacking = true;	// ���� ����� �����ϰ� ���� ���� true��
	}
	
	public void touchEnemy120(Rotate rotation) {	// ���⸦ �� ������ -120 �϶� ������ ��ġ
		double height = 0.0;	
		double width = 0.0;

		if(rotation.getAngle() <= 30) {
			double angle = Math.toRadians(60.0 + rotation.getAngle());
			height = Math.sin(Math.abs(angle)) * hypotenuse;
			width = -Math.cos(Math.abs(angle)) * hypotenuse;
			
		} else if(rotation.getAngle() <= 120) {
			double angle = Math.toRadians(120.0 - rotation.getAngle());
			height = Math.sin(Math.abs(angle)) * hypotenuse;
			width = Math.cos(Math.abs(angle)) * hypotenuse;
		} else {
			double angle = Math.toRadians(-120.0 + rotation.getAngle());
			height = -Math.sin(Math.abs(angle)) * hypotenuse;
			width = Math.cos(Math.abs(angle)) * hypotenuse;
		}
		
		CO.enemyDameged(height, width, getDamege(touchedRect));
	}
	
	public void touchEnemy60(Rotate rotation) {		// ���⸦ �� ������ -60�� �� �� ������ ��ġ 
		double height = 0.0;
		double width = 0.0;

		if(rotation.getAngle() >= -30) {
			double angle = Math.toRadians(60.0 - rotation.getAngle());
			height = Math.sin(Math.abs(angle)) * hypotenuse;
			width = Math.cos(Math.abs(angle)) * hypotenuse;
		} else if(rotation.getAngle() >= -120) {
			double angle = Math.toRadians(120.0 + rotation.getAngle());
			height = Math.sin(Math.abs(angle)) * hypotenuse;
			width = -Math.cos(Math.abs(angle)) * hypotenuse;
		} else {
			double angle = Math.toRadians(-120.0 - rotation.getAngle());
			height = -Math.sin(Math.abs(angle)) * hypotenuse;
			width = -Math.cos(Math.abs(angle)) * hypotenuse;
		}
		
		CO.enemyDameged(height, width, getDamege(touchedRect));
	}
	
	public void dropItem() {
		CO.player.toFront();
		Weaponrt.setAngle(0);
		touchedRect = null;
		Weaponrt = null;
		hypotenuse = 0;
	}
	
	public void mountItem() {
		touchedRect.toFront();
		Weaponrt = new Rotate();
		Weaponrt.pivotXProperty().bind(touchedRect.rotateProperty());
    	Weaponrt.pivotYProperty().bind(touchedRect.rotateProperty());
		touchedRect.getTransforms().add(Weaponrt);
		hypotenuse = Math.sqrt(Math.pow(touchedRect.getWidth(), 2) + Math.pow(touchedRect.getHeight(), 2));
	}
	
	public void mountItem(double angle) {
		touchedRect.toFront();
		Weaponrt = new Rotate();
		Weaponrt.pivotXProperty().bind(touchedRect.rotateProperty());
    	Weaponrt.pivotYProperty().bind(touchedRect.rotateProperty());
		touchedRect.getTransforms().add(Weaponrt);
		hypotenuse = Math.sqrt(Math.pow(touchedRect.getWidth(), 2) + Math.pow(touchedRect.getHeight(), 2));
		Weaponrt.setAngle(angle);
		
		if(up)
			CO.player.toFront();
	}
	
	private Integer getDamege(Rectangle rect) {
		Map<Rectangle, Integer> map = CM.CW.getWeapons();
		
		if(map.get(rect) != null)
			return map.get(rect);
		
		map = CM.CF.getFoods();
		
		if(map.get(rect) != null)
			return map.get(rect);
		
		map = CM.CWater.getWaters();
		
		if(map.get(rect) != null)
			return map.get(rect);
		
		return 0;
	}
}
