package 게임만들어보기.테스트.현프로젝트테스트.오브젝트;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import 게임만들어보기.테스트.현프로젝트테스트.키핸들러.KeyHandler;

public class Inventory extends Pane {
	@FXML private AnchorPane AP;
	private CreateObjects CO;
	private CreateMap CM;
	private KeyHandler KH;
	private int practicalPlusLayoutX = 35;
	private int practicalPlusLayoutY = -65;
	
	private static Rectangle inventorySize[][] = null;
	private static Rectangle items[][] = null;
	private static List<Rectangle> currentInsertItem = new ArrayList<>();
	public static Map<Rectangle, int[]> itemsInfo = new HashMap<>();	// 1이면 무기, 2이면 음식, 3이면 물
	
	public Inventory(AnchorPane AP, CreateObjects CO, KeyHandler KH, CreateMap CM) {		// 인벤토리를 생성자로 초기화
		this.AP = AP;
		this.CO = CO;
		this.KH = KH;
		this.CM = CM;
		super.setPrefWidth(100);
		super.setPrefHeight(115);
		super.setLayoutX(CO.player.getLayoutX() + practicalPlusLayoutX);
		super.setLayoutY(CO.player.getLayoutY() + practicalPlusLayoutY);
		super.setVisible(false);
		AP.getChildren().add(this);
		setInventorySize(4, 2, 10);		// 처음 사이즈 4 * 2 사이즈
		setItems();					// 아이템 배열 생성
	}
	

	public int getPracticalPlusLayoutX() {
		return practicalPlusLayoutX;
	}

	public int getPracticalPlusLayoutY() {
		return practicalPlusLayoutY;
	}
	
	public void setInventorySize(int y, int x, double space) {		// 인벤토리 사이즈 만큼 공간 초기화
		super.getChildren().clear();
		
		int textNum = 1;
		
		inventorySize = new Rectangle[y][x];
		
		if(items == null) {
			items = new Rectangle[y][x];
		}
		else {
			moveItems(y, x);
		}
		
		Rectangle rect = new Rectangle();
		
		rect.setWidth(super.getPrefWidth());
		rect.setHeight(super.getPrefHeight());
		rect.setLayoutX(0);
		rect.setLayoutY(0);
		rect.setStroke(Color.DIMGRAY);
		rect.setFill(Color.LIGHTGRAY);
		
		super.getChildren().add(rect);
		
		double XSpace = space;
		double XSize = (super.getPrefWidth() - (inventorySize[0].length + 1) * XSpace) / inventorySize[0].length;
		
		double YSpace = space;
		double YSize = (super.getPrefHeight() - (inventorySize.length + 1) * YSpace) / inventorySize.length;
		
		int currentY = 0;
		for(int i = 0; i < inventorySize.length; i++) {
			
			int currentX = 0;
			currentY += YSpace;
			
			for(int j = 0; j < inventorySize[i].length; j++) {
				currentX += XSpace;
				
				rect = new Rectangle();
				rect.setStroke(Color.BLACK);
				rect.setFill(Color.DARKSLATEBLUE);
				rect.setWidth(XSize);
				rect.setHeight(YSize);
				
				rect.setLayoutX(currentX);
				rect.setLayoutY(currentY);
				
				super.getChildren().add(rect);
				inventorySize[i][j] = rect;
				
				Text text = new Text();
				
				text.setText(String.valueOf(textNum));
				text.setFill(Color.PALEVIOLETRED);
				text.setStyle("-fx-font: 4 arial;");
				text.setLayoutX(inventorySize[i][j].getLayoutX() + 4);
				text.setLayoutY(inventorySize[i][j].getLayoutY() + 4);
				super.getChildren().add(text);
			
				
				textNum++;
				
				currentX += XSize;
			}
			
			currentY += YSize;
		}
	}

	public void setInventoryResize(int y, int x) {		// 인벤토리 사이즈를 늘릴때 호출
		int space = 10;
		if(x == 3)
			space = 5;
		else if(x == 4)
			space = 3;
		
		setInventorySize(y, x, space);
		setItems();
	}
	
	public void setItems() {	// 아이템 객체를 받지 않았을 때 (가방 크기를 늘리거나 하여 아이템들 다시 세팅해야 할 때 호출)
	
		for (int i = 0; i < items.length; i++) {
			for (int j = 0; j < items[i].length; j++) {
				if(items[i][j] == null)
					continue;
				
				double XSpace = inventorySize[i][j].getLayoutX() + (inventorySize[i][j].getWidth() - items[i][j].getWidth()) / 2;
				double YSpace = inventorySize[i][j].getLayoutY() + (inventorySize[i][j].getHeight() - items[i][j].getHeight()) / 2;
				
				super.getChildren().add(items[i][j]);
				items[i][j].setLayoutX(XSpace);
				items[i][j].setLayoutY(YSpace);
				items[i][j].toFront();
			}
		}
	}
	
	public void setItems(Rectangle rect) {		// 아이템 객체를 받았을 때 호출
		for (int i = 0; i < items.length; i++) {
			for (int j = 0; j < items[i].length; j++) {
				if(items[i][j] != null)		// 해당 자리에 이미 자리가 있다면 컨티뉴
					continue;
				
				items[i][j] = rect;
				currentInsertItem.add(rect);		//
				itemsInfo.put(rect, getInfo(rect));
				this.AP.getChildren().remove(rect);
				KH.dropItem();			// 들고 있는 아이템 객체와 관련된 것들 null로 바꾸기
				super.getChildren().add(items[i][j]);		// 인벤토리에 추가
				
				double XSpace = inventorySize[i][j].getLayoutX() + (inventorySize[i][j].getWidth() - items[i][j].getWidth()) / 2;
				double YSpace = inventorySize[i][j].getLayoutY() + (inventorySize[i][j].getHeight() - items[i][j].getHeight()) / 2;
				
				items[i][j].setLayoutX(XSpace);
				items[i][j].setLayoutY(YSpace);
				
				return;		// 추가하고 리턴
			}
		}
	}
	
	public void getItems() {		// X를 눌러 최근에 넣은 아이템을 꺼내려고 할 시
		if(currentInsertItem.size() == 0)	// 최근에 넣은 아이템이 없으면 리턴
			return;

		for (int i = 0; i < inventorySize.length; i++) {
			for (int j = 0; j < inventorySize[i].length; j++) {

				if(items[i][j] == currentInsertItem.get(currentInsertItem.size() - 1)) {		// 최근에 넣은 아이템 찾기
					
					super.getChildren().remove(items[i][j]);	// 해당 아이템 인벤토리에서 제거
					items[i][j] = null;		// 해당 자리 널로 만들기
					KeyHandler.touchedRect = currentInsertItem.get(currentInsertItem.size() - 1);	// 최근에 넣은 아이템 꺼내기
					this.AP.getChildren().add(KeyHandler.touchedRect);		// AP에 추가
					Rectangle rect = currentInsertItem.get(currentInsertItem.size() - 1);
					currentInsertItem.remove(rect);		// 최근에 넣은 아이템 끝 인덱스 제거
					itemsInfo.remove(rect);
					
					if(KeyHandler.left)
						KH.mountItem(-60);
					else
						KH.mountItem(-120);
					
					KeyHandler.touchedRect.setLayoutX(CO.player.getLayoutX() +
							CO.player.getWidth() / 2);
					KeyHandler.touchedRect.setLayoutY(CO.player.getLayoutY() +
							CO.player.getHeight() / 2);
					return;
				}
			}
		}
	}
	
	public void getItems(int num) {		// 숫자 패드를 입력하여 해당 자리의 아이템을 얻으려고 할 때
		if(currentInsertItem.size() == 0 || KeyHandler.touchedRect != null)	// 최근에 넣은 아이템이 없거나 이미 무기를 집고 있다면 리턴
			return;
		
		num --;
		
		int numX = num % inventorySize[0].length;
		int numY = num / inventorySize[0].length;
		
		if(items[numY][numX] == null)
			return;
		
		Rectangle rect = items[numY][numX];
		super.getChildren().remove(rect);	// 해당 아이템 인벤토리에서 제거
		currentInsertItem.remove(rect);	// 최근 넣은 아이템 list에서 제거
		itemsInfo.remove(rect);
		KeyHandler.touchedRect = rect;	// 해당 아이템 꺼내기
		this.AP.getChildren().add(KeyHandler.touchedRect);		// AP에 추가
		items[numY][numX] = null;	// 인벤토리에서 제거
					
		if(KeyHandler.left)
			KH.mountItem(-60);
		else
			KH.mountItem(-120);
					
		KeyHandler.touchedRect.setLayoutX(CO.player.getLayoutX() +
		CO.player.getWidth() / 2);
		KeyHandler.touchedRect.setLayoutY(CO.player.getLayoutY() +
		CO.player.getHeight() / 2);
	}
	
	public void moveItems(int y, int x) {		// 인벤토리에 아이템이 있는 상황에서 가방 크기가 늘어날 때 아이템 옮기기
		List<Rectangle> list = new ArrayList<>();
		
		for (int i = 0; i < items.length; i++) {
			for (int j = 0; j < items[i].length; j++) {
				if(items[i][j] != null)
					list.add(items[i][j]);
			}
		}
		
		items = new Rectangle[y][x];
		
		int num = 0;
		for (int i = 0; i < items.length; i++) {
			for (int j = 0; j < items[i].length; j++) {
				if(num == list.size())
					return;
				items[i][j] = list.get(num++);
			}
		}
	}
	
	public int[] getInfo(Rectangle rect) {
		int[] info = new int[2];
		
		if(CM.CW.getWeapons().containsKey(rect)) {
			info[0] = 1;
			info[1] = CM.CW.getWeapons().get(rect);
		} else if(CM.CF.getFoods().containsKey(rect)) {
			info[0] = 2;
			info[1] = CM.CF.getFoods().get(rect);
		} else {
			info[0] = 3;
			info[1] = CM.CWater.getWaters().get(rect);
		}
		
		return info;
	}
	
	public List<Rectangle> getItemList() {
		return currentInsertItem;
	}
	
	public static void setInit() {
		inventorySize = null;
		items = null;
		currentInsertItem = null;
		itemsInfo = null;
	}
}
