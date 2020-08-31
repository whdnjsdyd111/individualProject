package ���Ӹ�����.�׽�Ʈ.��������Ʈ�׽�Ʈ.������Ʈ;

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
import ���Ӹ�����.�׽�Ʈ.��������Ʈ�׽�Ʈ.Ű�ڵ鷯.KeyHandler;
import ���Ӹ�����.�׽�Ʈ.��������Ʈ�׽�Ʈ.Ű�ڵ鷯.KeyPressedEventHandler;
import ���Ӹ�����.�׽�Ʈ.��������Ʈ�׽�Ʈ.Ű�ڵ鷯.KeyReleasedHandler;

public class CreateObjects {
	@FXML public List<Rectangle> PassObj = new ArrayList<>();				// ����� �� �ִ� ������Ʈ (������)
	@FXML public List<Rectangle> NonePassObj = new ArrayList<>();			// ����� �� ���� ������Ʈ �ܺ�, ���� ��
	@FXML public List<Rectangle> NonePassEnemy = new ArrayList<>();		// ���� ���� ��ü
	@FXML public Map<Rectangle, Boolean> DamegedEnemy = new HashMap<>();	// ���� ������ �Ծ����� �Ǻ��ϴ� Map
	@FXML public Map<Rectangle, Integer> EnemyHP = new HashMap<>();		// ���� HP�� ���� Map
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
		AP.setOnMouseEntered(e -> {		// ���� ��
			if(once) {	// �ѹ��� ����
				actionToScene();
				once = false;
				for (int i = 0; i < AP.getChildren().size(); i++) {
					if(AP.getChildren().get(i) instanceof Rectangle) {
						Rectangle rect = (Rectangle) AP.getChildren().get(i);
						
						if(rect.getStroke() == Color.RED || rect.getStroke() == Color.PURPLE || rect.getStroke() == Color.MEDIUMPURPLE
								|| rect.getStroke() == Color.ORANGE) {
							NonePassObj.add(rect);		// ��� ���ϴ� �ܺ�
						} else if(rect.getStroke() == Color.GREEN || rect.getStroke() == Color.SPRINGGREEN 
								|| rect.getStroke() == Color.SEAGREEN)	// ��� ������ ������
							PassObj.add(rect);
						else if(rect.getStroke() == Color.GRAY) {	// ��� ���ϴ� ��
							NonePassEnemy.add(rect);
							DamegedEnemy.put(rect, false);
							EnemyHP = CM.CE.getEnemy();
						}

						if(rect.getStroke() == Color.BLACK)	{	// �÷��̾�
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
		
		double playerx = player.getLayoutX() + player.getWidth() / 2;		// �÷��̾��� ���߾� X ��ǥ
		double playery = player.getLayoutY() + player.getHeight() / 2;		// �÷��̾��� ���߾� Y ��ǥ
		
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
					) {			// �÷��̾��� X, Y ��ǥ�� ���� X, Y �ٱ��̸鼭 ���� ���� ���� ��
				DamegedEnemy.replace(rect, false, true);
				reduceHP(rect, damege);
				continue;
			} else if((	((playerx <= enemyX1 && enemyX1 <= WeaponX) &&
				  		(playerx <= enemyX2 && enemyX2 <= WeaponX)) ||
						((WeaponX <= enemyX1 && enemyX1 <= playerx) &&
						(WeaponX <= enemyX2 && enemyX2 <= playerx))) &&
				   
						(enemyY1 <= playery && playery <= enemyY2)
					) {			// �÷��̾��� X, ���� X �� ���� ���� ���� ���� ��
				DamegedEnemy.replace(rect, false, true);
				reduceHP(rect, damege);
				continue;
			} else if((	((playery <= enemyY1 && enemyY1 <= WeaponY) &&
						(playery <= enemyY2 && enemyY2 <= WeaponY) ) ||
						((WeaponY <= enemyY1 && enemyY1 <= playery) &&
						(WeaponY <= enemyY2 && enemyY2 <= playery))) &&
					
						(enemyX1 <= playerx && playerx <= enemyX2)
					){			// �÷��̾��� Y, ���� Y �� ���� ���� ���� ���� ��
				DamegedEnemy.replace(rect, false, true);
				reduceHP(rect, damege);
				continue;
			}
		}	
	}

	public void initDamegedBoolean() {		// ���� ��� �����尡 ������ DamegedEnemy ��� false�� �ٲٴ� �޼ҵ�
		Set<Rectangle> set = DamegedEnemy.keySet();
		Iterator<Rectangle> iterator = set.iterator();
	
		while(iterator.hasNext()) {
			Rectangle rect = iterator.next();
			DamegedEnemy.replace(rect, false);
		}
	}
	
	
	
	public void reduceHP(Rectangle rect, Integer damege) {		// ���� HP�� ��� �޼ҵ�
		EnemyHP.replace(rect, EnemyHP.get(rect), EnemyHP.get(rect) - damege);
		
		removeEnemy(EnemyHP.get(rect), rect);
	}
	
	
	
	public void removeEnemy(Integer HP, Rectangle rect) {		// HP�� 0 �̵� �� ���ֱ�
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
			AP.getChildren().remove(rect);		// AP���� �ش� ��ü ���ֱ�
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
