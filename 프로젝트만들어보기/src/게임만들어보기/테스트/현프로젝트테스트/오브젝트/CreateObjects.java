package 게임만들어보기.테스트.현프로젝트테스트.오브젝트;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import 게임만들어보기.테스트.현프로젝트테스트.키핸들러.KeyHandler;
import 게임만들어보기.테스트.현프로젝트테스트.키핸들러.KeyPressedEventHandler;
import 게임만들어보기.테스트.현프로젝트테스트.키핸들러.KeyReleasedHandler;

public class CreateObjects {
	@FXML public List<Rectangle> PassObj = new ArrayList<>();				// 통과할 수 있는 오브젝트 (아이템)
	@FXML public List<Rectangle> NonePassObj = new ArrayList<>();			// 통과할 수 없는 오브젝트 외벽, 내벽 등
	@FXML public List<Rectangle> NonePassEnemy = new ArrayList<>();		// 적을 담은 객체
	@FXML public Map<Rectangle, Boolean> DamegedEnemy = new HashMap<>();	// 적이 데미지 입었는지 판별하는 Map
	@FXML public Map<Rectangle, Integer> EnemyHP = new HashMap<>();		// 적의 HP를 담은 Map
	@FXML public Rectangle player = null;
	@FXML private AnchorPane AP = null;
	private CreateMap CM = null;
	@FXML public Inventory inventory = null;
	@FXML public HP HP = null;
	
	private KeyHandler KH = new KeyHandler(this, CM);
	
	private boolean once = true;
	
	public CreateObjects(AnchorPane AP, CreateMap CM) {
		this.AP = AP;
		this.CM = CM;
		AP.setOnMouseEntered(e -> {		// 시작 시
			if(once) {	// 한번만 실행
				actionToScene();
				once = false;
				for (int i = 0; i < AP.getChildren().size(); i++) {
					if(AP.getChildren().get(i) instanceof Rectangle) {
						Rectangle rect = (Rectangle) AP.getChildren().get(i);
						
						if(rect.getStroke() == Color.RED || rect.getStroke() == Color.PURPLE || rect.getStroke() == Color.MEDIUMPURPLE
								|| rect.getStroke() == Color.ORANGE) {
							NonePassObj.add(rect);		// 통과 못하는 외벽
						} else if(rect.getStroke() == Color.GREEN || rect.getStroke() == Color.SPRINGGREEN 
								|| rect.getStroke() == Color.SEAGREEN)	// 통과 가능한 아이템
							PassObj.add(rect);
						else if(rect.getStroke() == Color.GRAY) {	// 통과 못하는 적
							NonePassEnemy.add(rect);
							DamegedEnemy.put(rect, false);
							EnemyHP = CM.CE.getEnemy();
						}

						if(rect.getStroke() == Color.BLACK)	{	// 플레이어
							player = rect;
							CM.playerArea(player);
							AP.getChildren().add(CM.area);
							CM.CE.setCO(this);
							
							Set<Rectangle> set = CM.CE.getEnemy().keySet();
							Iterator<Rectangle> iterator = set.iterator();
							
							while(iterator.hasNext())
								AP.getChildren().add(iterator.next());
						}
					}
				}

				player.toFront();
				inventory = new Inventory(AP, this, KH, CM);
				HP = new HP(AP, this);
				PassObj.addAll(inventory.getItemList());
				
				Set<Rectangle> set = Inventory.itemsInfo.keySet();
				Iterator<Rectangle> iterator = set.iterator();
				
				while(iterator.hasNext()) {
					Rectangle rect = iterator.next();
					
					if(Inventory.itemsInfo.get(rect)[0] == 1)
						CM.CW.getWeapons().put(rect, Inventory.itemsInfo.get(rect)[1]);
					else if(Inventory.itemsInfo.get(rect)[0] == 2)
						CM.CF.getFoods().put(rect, Inventory.itemsInfo.get(rect)[1]);
					else
						CM.CWater.getWaters().put(rect, Inventory.itemsInfo.get(rect)[1]);
				}
				
				if(KeyHandler.touchedRect != null) {
					KeyHandler.touchedRect.setLayoutX(player.getLayoutX() + player.getWidth() / 2);
					KeyHandler.touchedRect.setLayoutY(player.getLayoutY() + player.getHeight() / 2);
				}
			}
		});
	}
	
	public void actionToScene() {
		AP.getScene().setOnKeyPressed(e -> {
			KH = new KeyPressedEventHandler(this, CM);
			
			KH.handle(e);
		});
		
		AP.getScene().setOnKeyReleased(e -> {
			KH = new KeyReleasedHandler(this, CM);
			
			KH.handle(e);
		});
	}
	
	public void enemyDameged(double height, double width, Integer damege) {
		
		double playerx = player.getLayoutX() + player.getWidth() / 2;		// 플레이어의 정중앙 X 좌표
		double playery = player.getLayoutY() + player.getHeight() / 2;		// 플레이어의 정중앙 Y 좌표
		
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
				reduceHP(rect, damege);
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
				reduceHP(rect, damege);
				continue;
			} else if((	((playerx <= enemyX1 && enemyX1 <= WeaponX) &&
				  		(playerx <= enemyX2 && enemyX2 <= WeaponX)) ||
						((WeaponX <= enemyX1 && enemyX1 <= playerx) &&
						(WeaponX <= enemyX2 && enemyX2 <= playerx))) &&
				   
						(enemyY1 <= playery && playery <= enemyY2)
					) {			// 플레이어의 X, 적의 X 가 같고 무기 영역 안일 때
				DamegedEnemy.replace(rect, false, true);
				reduceHP(rect, damege);
				continue;
			} else if((	((playery <= enemyY1 && enemyY1 <= WeaponY) &&
						(playery <= enemyY2 && enemyY2 <= WeaponY) ) ||
						((WeaponY <= enemyY1 && enemyY1 <= playery) &&
						(WeaponY <= enemyY2 && enemyY2 <= playery))) &&
					
						(enemyX1 <= playerx && playerx <= enemyX2)
					){			// 플레이어의 Y, 적의 Y 가 같고 무기 영역 안일 때
				DamegedEnemy.replace(rect, false, true);
				reduceHP(rect, damege);
				continue;
			}
		}	
	}

	public void initDamegedBoolean() {		// 공격 모션 스레드가 끝나면 DamegedEnemy 모두 false로 바꾸는 메소드
		Set<Rectangle> set = DamegedEnemy.keySet();
		Iterator<Rectangle> iterator = set.iterator();
	
		while(iterator.hasNext()) {
			Rectangle rect = iterator.next();
			DamegedEnemy.replace(rect, false);
		}
	}
	
	
	
	public void reduceHP(Rectangle rect, Integer damege) {		// 적의 HP를 깍는 메소드
		EnemyHP.replace(rect, EnemyHP.get(rect), EnemyHP.get(rect) - damege);
		
		removeEnemy(EnemyHP.get(rect), rect);
	}
	
	
	
	public void removeEnemy(Integer HP, Rectangle rect) {		// HP가 0 이된 적 없애기
		if(HP > 0)
			return;
		
		
		CM.CE.getContinueMoveEnemy().interrupt();
		CM.CE.getFollowPlayerEnemy().interrupt();
		
		EnemyHP.remove(rect);
		DamegedEnemy.remove(rect);
		NonePassEnemy.remove(rect);
		CM.CE.getEnemy().remove(rect);
		CM.CE.getRecognEnemy().remove(rect);
		CM.CE.getNoRecognEnemy().remove(rect);
		
		Platform.runLater(() -> {
			AP.getChildren().remove(rect);		// AP에서 해당 객체 없애기
		});
		
		
	}
	
	public void upEnergy() {
		KeyHandler.Weaponrt = null;
		KeyHandler.hypotenuse = 0;
		
		Map<Rectangle, Integer> map = CM.CF.getFoods();
		
		HP.upEnergy(map.get(KeyHandler.touchedRect) * 2);
		
		AP.getChildren().remove(KeyHandler.touchedRect);
		
		KeyHandler.touchedRect = null;
	}
	
	public void upThirst() {
		KeyHandler.Weaponrt = null;
		KeyHandler.hypotenuse = 0;
		
		Map<Rectangle, Integer> map = CM.CWater.getWaters();
		
		HP.upThirst(map.get(KeyHandler.touchedRect) * 2);
		
		AP.getChildren().remove(KeyHandler.touchedRect);
		
		KeyHandler.touchedRect = null;
	}
	

	public void damegedPlayer(Rectangle rect, int dx, int dy) {
		HP.reduceHp(5);
		
		Platform.runLater(() -> {
			try {
				player.setLayoutX(player.getLayoutX() + (-dx * 7));
				player.setLayoutY(player.getLayoutY() + (-dy * 7));
			} catch(NullPointerException e) {}
		});
		
	}
	
	public void removeKH() {
		this.KH = null;
	}
	
	public KeyHandler getKeyHadler() {
		return KH;
	}
	
	public void setInit() {
		NonePassObj.clear();
		NonePassEnemy.clear();
	}
}
