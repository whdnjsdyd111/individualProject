package ���Ӹ�����.�α׶���ũ;

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

	@FXML private List<Rectangle> PassObj = new ArrayList<>();				// ����� �� �ִ� ������Ʈ (������)
	@FXML private List<Rectangle> NonePassObj = new ArrayList<>();			// ����� �� ���� ������Ʈ �ܺ�, ���� ��
	
	@FXML private List<Rectangle> NonePassEnemy = new ArrayList<>();		// ���� ���� ��ü
	@FXML private Map<Rectangle, Boolean> DamegedEnemy = new HashMap<>();	// ���� ������ �Ծ����� �Ǻ��ϴ� Map
	@FXML private Map<Rectangle, Integer> EnemyHP = new HashMap<>();		// ���� HP�� ���� Map
	
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
		
		
		
		RandomMap map = new RandomMap(AP);		// �� ��ü ����
		
		List<Rectangle> list = new ArrayList<>();
		list = map.BorderLine(100);		// list�� �� ����
		
		AP.getChildren().addAll(list);		// AP�� �� ����
		
		AP.setScaleX(scale);
		AP.setScaleY(scale);
		
		back.setOnMouseClicked(e -> handleBackBtn(e));		//	back ������ �������� ���ư�

		AP.setOnMouseEntered(e -> {		// ���� ��
			if(once) {	// �ѹ��� ����
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
		
		AnimationTimer timer = new AnimationTimer() {	// �÷��̾� �������� �����ϴ� �ִϸ��̼� Ÿ�̸� ����
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
		scene = AP.getScene();		// �� ��ü ���
		
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {	// Ű�� ������ ��

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

		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {	// Ű�� ���� ��
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

	private void movePlayerBy(int dx, int dy) {		// �÷��̾� �����̱�
		if (dx == 0 && dy == 0)
			return;
		
		playerx = player.getLayoutX() - player.getStrokeWidth() / 2;
		playery = player.getLayoutY() - player.getStrokeWidth() / 2;
		
		double speed = 1.5;

		double x = playerx + dx * speed;
		double y = playery + dy * speed;
		
		movePlayerTo(x ,y, playerx, playery, dx, dy, speed);
		
		MountingWeapon();	// ���� ��ġ
	}
	
	public void movePlayerTo(double x, double y, double playerx, double playery, int dx, int dy, double speed) {	// ������ �Ǵ��ϰ� �����̱�
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
			
			if((x < rectX1 || x > rectX2) || (y < rectY1 || y > rectY2)) {	// �ش� �������� ���� ��
				
			} else if(x > rectX1 && x < rectX2) {	// �̵� �Ϸ��� ���� X ���� ��
					
				if(playery > rectY1 && playery < rectY2) {	// ���θ� �� ��� �ִٰ� ����
					touchX = true;
				} else if(y > rectY1 && y < rectY2){
					touchY = true;	// ���θ��� ��� �ִٰ� ����
				}
			}
		}

		for(int i = 0 ; i < NonePassEnemy.size(); i++) {
			rectX1 = NonePassEnemy.get(i).getLayoutX() - player.getWidth();
			rectX2 = NonePassEnemy.get(i).getLayoutX() + NonePassEnemy.get(i).getWidth();
			rectY1 = NonePassEnemy.get(i).getLayoutY() - player.getHeight();
			rectY2 = NonePassEnemy.get(i).getLayoutY() + NonePassEnemy.get(i).getHeight();
			
			if((x < rectX1 || x > rectX2) || (y < rectY1 || y > rectY2)) {	// �ش� �������� ���� ��
				
			} else if(x > rectX1 && x < rectX2) {	// �̵� �Ϸ��� ���� X ���� ��
					
				if(playery > rectY1 && playery < rectY2) {	// ���θ� �� ��� �ִٰ� ����
					touchX = true;
				} else if(y > rectY1 && y < rectY2){
					touchY = true;	// ���θ��� ��� �ִٰ� ����
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
	
	public void moveZoom(double dx, double dy) {	// ĳ���� ��
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
			
			if((playerx > rectX1 && playerx < rectX2) && (playery > rectY1 && playery < rectY2)) {	// �ش� �������� ���� ��
				return PassObj.get(i);
			}
		}
		
		return null;
		
	}
	
	public void MountingWeapon() {		// ���� �����ϱ� 
		if(touchedRect == null)
			return;
		
		touchedRect.setLayoutX(player.getLayoutX() + player.getWidth() / 2);
		touchedRect.setLayoutY(player.getLayoutY() + player.getHeight() / 2);
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
	        	

	        	InitDamegedBoolean();		// �ֵθ� �� ������ �Ծ������� �Ǻ��� false�� �ǵ���
	    		timelineRotate2.setCycleCount(1);	// ���� ���� ������ �ǵ����� Timeline ����
	    		
	    		timelineRotate2.setOnFinished( e -> {
	    			attacking = false;		// ���� ������ �ǵ����� ���� ���� false�� 
	    			timelineRotate2 = null;	// timeline2�� �η� �����ֱ�
	    			timelineRoTateTh1 = null;	// ����
	    		});
	        	timelineRotate2.play();
			});
		
		
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

	public void handleBackBtn(MouseEvent e) {		// ���� ȭ������ �Ѿ��
		try {
			StackPane root = (StackPane) back.getScene().getRoot();
			root.getChildren().remove(AP);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
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
		
		EnemyDameged(height, width);
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
		
		EnemyDameged(height, width);
	}
	
	public void EnemyDameged(double height, double width) {
		
		double playerx = this.playerx + player.getWidth() / 2;		// �÷��̾��� ���߾� X ��ǥ
		double playery = this.playery + player.getHeight() / 2;		// �÷��̾��� ���߾� Y ��ǥ

		for(int i = 0; i < NonePassEnemy.size(); i++) {		// ���⿡ ���� ���� �Ǻ��ϰ� HP�� ���
			Rectangle rect = NonePassEnemy.get(i);
			
			if(DamegedEnemy.get(rect))		// ���� ��� �� �̹� HP�� ���� ���� �Ѿ��
				continue;
			
			double enemyX1 = rect.getLayoutX();						// �� X1 ��ǥ
			double enemyX2 = rect.getLayoutX() + rect.getWidth();	// �� X2 ��ǥ
			double enemyY1 = rect.getLayoutY();						// �� Y1 ��ǥ
			double enemyY2 = rect.getLayoutY() + rect.getHeight();	// �� Y2 ��ǥ
			
			double WeaponX = playerx + width;
			double WeaponY = playery + height;
			
			if((enemyX1 <= WeaponX && WeaponX <= enemyX2) &&
					enemyY1 <= WeaponY && WeaponY <= enemyY2) 
					{		// ���� ���� ���� ��
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
					) {			// �÷��̾��� X, Y ��ǥ�� ���� X, Y �ٱ��̸鼭 ���� ���� ���� ��
				DamegedEnemy.replace(rect, false, true);
				reduceHP(rect);
				continue;
			} else if((	((playerx <= enemyX1 && enemyX1 <= WeaponX) &&
				  		(playerx <= enemyX2 && enemyX2 <= WeaponX)) ||
						((WeaponX <= enemyX1 && enemyX1 <= playerx) &&
						(WeaponX <= enemyX2 && enemyX2 <= playerx))) &&
				   
						(enemyY1 <= playery && playery <= enemyY2)
					) {			// �÷��̾��� X, ���� X �� ���� ���� ���� ���� ��
				DamegedEnemy.replace(rect, false, true);
				reduceHP(rect);
				continue;
			} else if((	((playery <= enemyY1 && enemyY1 <= WeaponY) &&
						(playery <= enemyY2 && enemyY2 <= WeaponY) ) ||
						((WeaponY <= enemyY1 && enemyY1 <= playery) &&
						(WeaponY <= enemyY2 && enemyY2 <= playery))) &&
					
						(enemyX1 <= playerx && playerx <= enemyX2)
					){			// �÷��̾��� Y, ���� Y �� ���� ���� ���� ���� ��
				DamegedEnemy.replace(rect, false, true);
				reduceHP(rect);
				continue;
			}
		}
		
	}
	
	public void InitDamegedBoolean() {		// ���� ��� �����尡 ������ DamegedEnemy ��� false�� �ٲٴ� �޼ҵ�
		Set<Rectangle> set = DamegedEnemy.keySet();
		Iterator<Rectangle> iterator = set.iterator();
		
		while(iterator.hasNext()) {
			Rectangle rect = iterator.next();
			DamegedEnemy.replace(rect, false);
		}
	}
	
	public void reduceHP(Rectangle rect) {		// ���� HP�� ��� �޼ҵ�
		EnemyHP.replace(rect, EnemyHP.get(rect), EnemyHP.get(rect) - 1);
		
		RemoveEnemy(EnemyHP.get(rect), rect);
	}
	
	public void RemoveEnemy(Integer HP, Rectangle rect) {		// HP�� 0 �̵� �� ���ֱ�
		System.out.println(HP);
		if(HP > 0)
			return;
		
		EnemyHP.remove(rect);
		DamegedEnemy.remove(rect);
		NonePassEnemy.remove(rect);
		
		Platform.runLater(() -> {
			AP.getChildren().remove(rect);		// AP���� �ش� ��ü ���ֱ�
		});
	}
}
