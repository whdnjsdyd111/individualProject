package 게임만들어보기.로그라이크;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class TutorialController implements Initializable {
	@FXML private AnchorPane AP;
	@FXML private Label back;
	@FXML private Rectangle player;
	@FXML private Scene scene;

	@FXML private List<Rectangle> PassObj = new ArrayList<>();				// 통과할 수 있는 오브젝트 (아이템)
	@FXML private List<Rectangle> NonePassObj = new ArrayList<>();			// 통과할 수 없는 오브젝트 외벽, 내벽 등
	
	@FXML private List<Rectangle> NonePassEnemy = new ArrayList<>();		// 적을 담은 객체
	@FXML private Map<Rectangle, Boolean> DamegedEnemy = new HashMap<>();	// 적이 데미지 입었는지 판별하는 Map
	@FXML private Map<Rectangle, Integer> EnemyHP = new HashMap<>();		// 적의 HP를 담은 Map
	
	@FXML private Rectangle touchedRect = null;
	double hypotenuse = 0;
	private Rotate Weaponrt = null;
	
	private Timeline timelineRotate2 = null;
	Thread timelineRoTateTh1 = null;
	
	private boolean up = false;
	private boolean down = false;
	private boolean left = false;
	private boolean right = false;
	private boolean once = true;
	private boolean attacking = false;
	
	private double currentZoomX = 0.0;
	private double currentZoomY = 0.0;
	private static final double scale = 3.3;
	
	private double playerx = 0.0;
	private double playery = 0.0;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		
		RandomMap map = new RandomMap(AP);		// 맵 객체 생성
		
		List<Rectangle> list = new ArrayList<>();
		list = map.BorderLine(100);		// list에 맵 저장
		
		AP.getChildren().addAll(list);		// AP에 맵 생성
		
		AP.setScaleX(scale);
		AP.setScaleY(scale);
		
		back.setOnMouseClicked(e -> handleBackBtn(e));		//	back 누르면 메인으로 돌아감

		AP.setOnMouseEntered(e -> {		// 시작 시
			if(once) {	// 한번만 실행
				ActionToScene();
				once = false;
				back.toFront();
				for (int i = 0; i < AP.getChildren().size(); i++) {
					if(AP.getChildren().get(i) instanceof Rectangle) {
						Rectangle rect = (Rectangle) AP.getChildren().get(i);
						
						if(rect.getStroke() == Color.BLACK)
							player = rect;
						
						if(rect.getStroke() == Color.RED)
							NonePassObj.add(rect);
						
						else if(rect.getStroke() == Color.GREEN)
							PassObj.add(rect);
						else if(rect.getStroke() == Color.GRAY) {
							NonePassEnemy.add(rect);
							DamegedEnemy.put(rect, false);
							EnemyHP.put(rect, 3);
						}
					}
				}
				player.toFront();
			}
		});
		
		AnimationTimer timer = new AnimationTimer() {	// 플레이어 움직임을 감지하는 애니메이션 타이머 시작
			@Override
			public void handle(long now) {
				int dx = 0, dy = 0;

				if (up)
					dy -= 1;
				if (down)
					dy += 1;
				if (left)
					dx -= 1;
				if (right)
					dx += 1;

				movePlayerBy(dx, dy);
			}
		};
		timer.start();

	}

	private void ActionToScene() {
		scene = AP.getScene();		// 씬 객체 얻기
		
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {	// 키를 눌렀을 때

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
				}
				

				
				if(event.getCode() == KeyCode.SPACE) {
					attack();
				}
				
				
				
				if(Weaponrt == null || timelineRoTateTh1 != null || timelineRotate2 != null)
					return;
				
				if(up && left) {
					Weaponrt.setAngle(-60);
					player.toFront();
				} else if(up && right) {
					Weaponrt.setAngle(-120);
					player.toFront();
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
				
			};
		});

		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {	// 키를 땠을 때
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
				
				if(!(Weaponrt == null || timelineRoTateTh1 != null || timelineRotate2 != null)){
				
					if(up && left) {
						Weaponrt.setAngle(-60);
						player.toFront();
					} else if(up && right) {
						Weaponrt.setAngle(-120);
						player.toFront();
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
				
				if(event.getCode() == KeyCode.Z) {
					if(touchedRect == null) {
						touchedRect = touchObj(playerx, playery);
						if(touchedRect != null) {
							touchedRect.toFront();
							Weaponrt = new Rotate();
							Weaponrt.pivotXProperty().bind(touchedRect.rotateProperty());
				        	Weaponrt.pivotYProperty().bind(touchedRect.rotateProperty());
							touchedRect.getTransforms().add(Weaponrt);
							hypotenuse = Math.sqrt(Math.pow(touchedRect.getWidth(), 2) + Math.pow(touchedRect.getHeight(), 2));
						}
					} else {
						if(timelineRoTateTh1 != null) {
							return;
						}
						
						if(timelineRotate2 != null) {
							return;
						}
						
						player.toFront();
						Weaponrt.setAngle(0);
						touchedRect = null;
						Weaponrt = null;
						hypotenuse = 0;
						
					}
				}
			};
		});
	}

	private void movePlayerBy(int dx, int dy) {		// 플레이어 움직이기
		if (dx == 0 && dy == 0)
			return;
		
		playerx = player.getLayoutX() - player.getStrokeWidth() / 2;
		playery = player.getLayoutY() - player.getStrokeWidth() / 2;
		
		double speed = 1.5;

		double x = playerx + dx * speed;
		double y = playery + dy * speed;
		
		movePlayerTo(x ,y, playerx, playery, dx, dy, speed);
		
		MountingWeapon();	// 무기 위치
	}
	
	public void movePlayerTo(double x, double y, double playerx, double playery, int dx, int dy, double speed) {	// 벽인지 판단하고 움직이기
		boolean touchXY = false;
		boolean touchX = false;
		boolean touchY = false;
		
		double rectX1 = 0.0;
		double rectY1 = 0.0;
		double rectX2 = 0.0;
		double rectY2 = 0.0;
		
		for (int i = 0; i < NonePassObj.size(); i++) {
			rectX1 = NonePassObj.get(i).getLayoutX() - player.getWidth();
			rectX2 = NonePassObj.get(i).getLayoutX() + NonePassObj.get(i).getWidth();
			rectY1 = NonePassObj.get(i).getLayoutY() - player.getHeight();
			rectY2 = NonePassObj.get(i).getLayoutY() + NonePassObj.get(i).getHeight();
			
			if((x < rectX1 || x > rectX2) || (y < rectY1 || y > rectY2)) {	// 해당 오브젝으 영역 밖
				
			} else if(x > rectX1 && x < rectX2) {	// 이동 하려는 곳이 X 영역 안
					
				if(playery > rectY1 && playery < rectY2) {	// 세로면 이 닿고 있다고 판정
					touchX = true;
				} else if(y > rectY1 && y < rectY2){
					touchY = true;	// 가로면이 닿고 있다고 판정
				}
			}
		}

		for(int i = 0 ; i < NonePassEnemy.size(); i++) {
			rectX1 = NonePassEnemy.get(i).getLayoutX() - player.getWidth();
			rectX2 = NonePassEnemy.get(i).getLayoutX() + NonePassEnemy.get(i).getWidth();
			rectY1 = NonePassEnemy.get(i).getLayoutY() - player.getHeight();
			rectY2 = NonePassEnemy.get(i).getLayoutY() + NonePassEnemy.get(i).getHeight();
			
			if((x < rectX1 || x > rectX2) || (y < rectY1 || y > rectY2)) {	// 해당 오브젝으 영역 밖
				
			} else if(x > rectX1 && x < rectX2) {	// 이동 하려는 곳이 X 영역 안
					
				if(playery > rectY1 && playery < rectY2) {	// 세로면 이 닿고 있다고 판정
					touchX = true;
				} else if(y > rectY1 && y < rectY2){
					touchY = true;	// 가로면이 닿고 있다고 판정
				}
			}
		}
		
		if(touchXY || (touchY && touchX)) {
			player.relocate(playerx, playery);
			moveZoom(0, 0);
		} else if(touchY) {
			player.relocate(x, playery);
			moveZoom(dx * speed, 0);
		} else if(touchX) {
			player.relocate(playerx, y);
			moveZoom(0, dy * speed);
		} else {
			player.relocate(x, y);
			moveZoom(dx * speed, dy * speed);
		}
	}
	
	public void moveZoom(double dx, double dy) {	// 캐릭터 줌
		currentZoomX += dx * scale;
		currentZoomY += dy * scale;
		
		AP.setTranslateX(-currentZoomX);
		AP.setTranslateY(-currentZoomY);
	}
	
	public Rectangle touchObj(double playerx, double playery) {
		
		double rectX1 = 0.0;
		double rectY1 = 0.0;
		double rectX2 = 0.0;
		double rectY2 = 0.0;
		
		for (int i = 0; i < PassObj.size(); i++) {
			rectX1 = PassObj.get(i).getLayoutX() - player.getWidth();
			rectX2 = PassObj.get(i).getLayoutX() + PassObj.get(i).getWidth();
			rectY1 = PassObj.get(i).getLayoutY() - player.getHeight();
			rectY2 = PassObj.get(i).getLayoutY() + PassObj.get(i).getHeight();
			
			if((playerx > rectX1 && playerx < rectX2) && (playery > rectY1 && playery < rectY2)) {	// 해당 오브젝으 영역 안
				return PassObj.get(i);
			}
		}
		
		return null;
		
	}
	
	public void MountingWeapon() {		// 무기 장착하기 
		if(touchedRect == null)
			return;
		
		touchedRect.setLayoutX(player.getLayoutX() + player.getWidth() / 2);
		touchedRect.setLayoutY(player.getLayoutY() + player.getHeight() / 2);
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
	        	

	        	InitDamegedBoolean();		// 휘두른 후 데미지 입었는지의 판별을 false로 되돌림
	    		timelineRotate2.setCycleCount(1);	// 무기 원래 각도로 되돌리는 Timeline 실행
	    		
	    		timelineRotate2.setOnFinished( e -> {
	    			attacking = false;		// 원래 각도로 되돌리면 공격 중을 false로 
	    			timelineRotate2 = null;	// timeline2를 널로 없애주기
	    			timelineRoTateTh1 = null;	// 동일
	    		});
	        	timelineRotate2.play();
			});
		
		
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
	                    new KeyFrame(Duration.seconds(0.5), new KeyValue(rotation.angleProperty(), 0)));
	        	
	        	InitDamegedBoolean();
	    		timelineRotate2.setCycleCount(1);
	    		
	    		timelineRotate2.setOnFinished( e -> {
	    			attacking = false;
	    			timelineRotate2 = null;
	    			timelineRoTateTh1 = null;
	    		});
	        	timelineRotate2.play();
			});
		
		
			timelineRoTateTh1.start();
			attacking = true;
		}
	}

	public void handleBackBtn(MouseEvent e) {		// 메인 화면으로 넘어가기
		try {
			StackPane root = (StackPane) back.getScene().getRoot();
			root.getChildren().remove(AP);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
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
		
		EnemyDameged(height, width);
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
		
		EnemyDameged(height, width);
	}
	
	public void EnemyDameged(double height, double width) {
		
		double playerx = this.playerx + player.getWidth() / 2;		// 플레이어의 정중앙 X 좌표
		double playery = this.playery + player.getHeight() / 2;		// 플레이어의 정중앙 Y 좌표

		for(int i = 0; i < NonePassEnemy.size(); i++) {		// 무기에 닿은 적을 판별하고 HP를 깍기
			Rectangle rect = NonePassEnemy.get(i);
			
			if(DamegedEnemy.get(rect))		// 공격 모션 중 이미 HP가 깍인 적은 넘어가기
				continue;
			
			double enemyX1 = rect.getLayoutX();						// 적 X1 좌표
			double enemyX2 = rect.getLayoutX() + rect.getWidth();	// 적 X2 좌표
			double enemyY1 = rect.getLayoutY();						// 적 Y1 좌표
			double enemyY2 = rect.getLayoutY() + rect.getHeight();	// 적 Y2 좌표
			
			double WeaponX = playerx + width;
			double WeaponY = playery + height;
			
			if((enemyX1 <= WeaponX && WeaponX <= enemyX2) &&
					enemyY1 <= WeaponY && WeaponY <= enemyY2) 
					{		// 무기 끝에 닿을 때
				DamegedEnemy.replace(rect, false, true);
				reduceHP(rect);
				continue;
			} else if( (((playerx <= enemyX1 && enemyX1 <= WeaponX) &&
					  	(playerx <= enemyX2 && enemyX2 <= WeaponX)) ||
					   ((WeaponX <= enemyX1 && enemyX1 <= playerx) &&
					    (WeaponX <= enemyX2 && enemyX2 <= playerx))) &&
					
					   (((playery <= enemyY1 && enemyY1 <= WeaponY) &&
					   (playery <= enemyY2 && enemyY2 <= WeaponY)) ||
					   ((WeaponY <= enemyY1 && enemyY1 <= playery) &&
				       (WeaponY <= enemyY2 && enemyY2 <= playery)))
					) {			// 플레이어의 X, Y 좌표가 적의 X, Y 바깥이면서 무기 범위 안일 때
				DamegedEnemy.replace(rect, false, true);
				reduceHP(rect);
				continue;
			} else if((	((playerx <= enemyX1 && enemyX1 <= WeaponX) &&
				  		(playerx <= enemyX2 && enemyX2 <= WeaponX)) ||
						((WeaponX <= enemyX1 && enemyX1 <= playerx) &&
						(WeaponX <= enemyX2 && enemyX2 <= playerx))) &&
				   
						(enemyY1 <= playery && playery <= enemyY2)
					) {			// 플레이어의 X, 적의 X 가 같고 무기 영역 안일 때
				DamegedEnemy.replace(rect, false, true);
				reduceHP(rect);
				continue;
			} else if((	((playery <= enemyY1 && enemyY1 <= WeaponY) &&
						(playery <= enemyY2 && enemyY2 <= WeaponY) ) ||
						((WeaponY <= enemyY1 && enemyY1 <= playery) &&
						(WeaponY <= enemyY2 && enemyY2 <= playery))) &&
					
						(enemyX1 <= playerx && playerx <= enemyX2)
					){			// 플레이어의 Y, 적의 Y 가 같고 무기 영역 안일 때
				DamegedEnemy.replace(rect, false, true);
				reduceHP(rect);
				continue;
			}
		}
		
	}
	
	public void InitDamegedBoolean() {		// 공격 모션 스레드가 끝나면 DamegedEnemy 모두 false로 바꾸는 메소드
		Set<Rectangle> set = DamegedEnemy.keySet();
		Iterator<Rectangle> iterator = set.iterator();
		
		while(iterator.hasNext()) {
			Rectangle rect = iterator.next();
			DamegedEnemy.replace(rect, false);
		}
	}
	
	public void reduceHP(Rectangle rect) {		// 적의 HP를 깍는 메소드
		EnemyHP.replace(rect, EnemyHP.get(rect), EnemyHP.get(rect) - 1);
		
		RemoveEnemy(EnemyHP.get(rect), rect);
	}
	
	public void RemoveEnemy(Integer HP, Rectangle rect) {		// HP가 0 이된 적 없애기
		System.out.println(HP);
		if(HP > 0)
			return;
		
		EnemyHP.remove(rect);
		DamegedEnemy.remove(rect);
		NonePassEnemy.remove(rect);
		
		Platform.runLater(() -> {
			AP.getChildren().remove(rect);		// AP에서 해당 객체 없애기
		});
	}
}
