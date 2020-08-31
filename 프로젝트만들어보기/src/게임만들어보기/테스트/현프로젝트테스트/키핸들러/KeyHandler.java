package 게임만들어보기.테스트.현프로젝트테스트.키핸들러;

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
import 게임만들어보기.테스트.현프로젝트테스트.오브젝트.CreateMap;
import 게임만들어보기.테스트.현프로젝트테스트.오브젝트.CreateObjects;

public class KeyHandler implements EventHandler<KeyEvent> {
	public static boolean up = false;
	public static boolean down = false;
	public static boolean left = false;
	public static boolean right = false;
	
	@FXML static public Rotate Weaponrt = null;
	@FXML static public Rectangle touchedRect = null;
	protected CreateObjects CO;
	protected CreateMap CM;
	
	
	public static double hypotenuse = 0;		// 휘두르는 중축 길이
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

			if((playerx >= rectX1 && playerx <= rectX2) && (playery >= rectY1 && playery <= rectY2)) {	// 해당 오브젝으 영역 안
				return CO.PassObj.get(i);
			}
		}
		
		return null;
		
	}
	
	public void attack() {			// 공격 하기
		if(touchedRect == null || attacking)	// 집은 무기가 없거나 공격 중이라면 실행하지 않기
			return;
		
		Rotate rotation = new Rotate();		// 각도 객체
        rotation.pivotXProperty().bind(touchedRect.rotateProperty());		// 각도 객체 무기의 각도와 바인드
        rotation.pivotYProperty().bind(touchedRect.rotateProperty());		// 각도 객체 무기의 각도와 바인드
        touchedRect.getTransforms().add(rotation);							// 무기의 트랜스폼에 각도 객체 추가
		
		if(Weaponrt.getAngle() == -120) {			// 무기 든 방향이 -120 일때
			timelineRoTateTh1 = new Thread(() -> {		// 공격 모션 스레드 실행		(Timeline으로 할 시 각도가 세분화하여 실행하지 않아 Thread로 대체)
	        	double StartRt = 0;		// 시작 각도
	        	double EndRt = 210;		// 끝 각도
	        	double turn = EndRt / 100;	// 각도를 도는 양
	        	
	        	while(StartRt <= EndRt) {
	        		rotation.setAngle(StartRt += turn);
	        		touchEnemy120(rotation);		// 각도에 따른 무기의 위치를 감시
	        		try {
	        			Thread.sleep(1);			// 0.001초 마다 돌게 하여 0.1초로 돔
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
	        	}

	        	timelineRotate2 = new Timeline(		// 휘두른 무기를 원래 각도로 되돌리기
	                    new KeyFrame(Duration.ZERO, new KeyValue(rotation.angleProperty(), 210)),
	                    new KeyFrame(Duration.seconds(0.5), new KeyValue(rotation.angleProperty(), 0)));
	        	

	        	CO.initDamegedBoolean();		// 휘두른 후 데미지 입었는지의 판별을 false로 되돌림
	    		timelineRotate2.setCycleCount(1);	// 무기 원래 각도로 되돌리는 Timeline 실행
	    		
	    		timelineRotate2.setOnFinished( e -> {
	    			attacking = false;		// 원래 각도로 되돌리면 공격 중을 false로 
	    			timelineRotate2 = null;	// timeline2를 널로 없애주기
	    			timelineRoTateTh1 = null;	// 동일
	    		});
	        	timelineRotate2.play();
	        	
			});
		
			timelineRoTateTh1.setName("Attack Motion");
			timelineRoTateTh1.start();
			attacking = true;	// 공격 모션을 시작하고 공격 중을 true로
		
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
		if(touchedRect == null || attacking)	// 집은 무기가 없거나 공격 중이라면 실행하지 않기
			return;
		
		if(Weaponrt.getAngle() == -120) {			// 무기 든 방향이 -120 일때

	        timelineRotate2 = new Timeline(		// 휘두른 무기를 원래 각도로 되돌리기
	        		new KeyFrame(Duration.ZERO, new KeyValue(touchedRect.layoutXProperty(), touchedRect.getLayoutX()), 
	        				new KeyValue(touchedRect.layoutYProperty(), touchedRect.getLayoutY())),
	        		new KeyFrame(Duration.seconds(0.15), new KeyValue(touchedRect.layoutXProperty(), touchedRect.getLayoutX() - 5),
	        				new KeyValue(touchedRect.layoutYProperty(), touchedRect.getLayoutY() + 10)),
	        		new KeyFrame(Duration.seconds(0.3), new KeyValue(touchedRect.layoutXProperty(), touchedRect.getLayoutX()), 
	        				new KeyValue(touchedRect.layoutYProperty(), touchedRect.getLayoutY())));
	        	
	    	timelineRotate2.setCycleCount(3);	// 무기 원래 각도로 되돌리는 Timeline 실행
	    		
	    	timelineRotate2.setOnFinished( e -> {
	    		attacking = false;		// 원래 각도로 되돌리면 공격 중을 false로 
	    		timelineRotate2 = null;	// timeline2를 널로 없애주기
	    		CO.upEnergy();
	    	});
	    		
	    	timelineRotate2.play();
			attacking = true;	// 공격 모션을 시작하고 공격 중을 true로
		
		} else {
			timelineRotate2 = new Timeline(		// 휘두른 무기를 원래 각도로 되돌리기
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
		if(touchedRect == null || attacking)	// 집은 무기가 없거나 공격 중이라면 실행하지 않기
			return;
		
		Weaponrt.setAngle(-180);
		timelineRotate2 = new Timeline(		// 휘두른 무기를 원래 각도로 되돌리기
				new KeyFrame(Duration.ZERO, new KeyValue(touchedRect.layoutXProperty(), touchedRect.getLayoutX() + 3), 
						new KeyValue(touchedRect.layoutYProperty(), touchedRect.getLayoutY())),
	        	new KeyFrame(Duration.seconds(0.15), new KeyValue(touchedRect.layoutXProperty(), touchedRect.getLayoutX() + 3),
	        			new KeyValue(touchedRect.layoutYProperty(), touchedRect.getLayoutY() - 10)),
	        	new KeyFrame(Duration.seconds(0.3), new KeyValue(touchedRect.layoutXProperty(), touchedRect.getLayoutX() + 3), 
	        			new KeyValue(touchedRect.layoutYProperty(), touchedRect.getLayoutY())));
		
		timelineRotate2.setCycleCount(3);	// 무기 원래 각도로 되돌리는 Timeline 실행
	    		
	    timelineRotate2.setOnFinished( e -> {
	    	attacking = false;		// 원래 각도로 되돌리면 공격 중을 false로 
	    	timelineRotate2 = null;	// timeline2를 널로 없애주기
	    	CO.upThirst();
	    });
	    		
	    timelineRotate2.play();
		attacking = true;	// 공격 모션을 시작하고 공격 중을 true로
	}
	
	public void touchEnemy120(Rotate rotation) {	// 무기를 든 방향이 -120 일때 무기의 위치
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
	
	public void touchEnemy60(Rotate rotation) {		// 무기를 든 방향이 -60도 일 때 무기의 위치 
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
