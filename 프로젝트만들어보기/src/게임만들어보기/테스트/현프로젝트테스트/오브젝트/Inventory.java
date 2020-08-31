package ���Ӹ�����.�׽�Ʈ.��������Ʈ�׽�Ʈ.������Ʈ;

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
import ���Ӹ�����.�׽�Ʈ.��������Ʈ�׽�Ʈ.Ű�ڵ鷯.KeyHandler;

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
	public static Map<Rectangle, int[]> itemsInfo = new HashMap<>();	// 1�̸� ����, 2�̸� ����, 3�̸� ��
	
	public Inventory(AnchorPane AP, CreateObjects CO, KeyHandler KH, CreateMap CM) {		// �κ��丮�� �����ڷ� �ʱ�ȭ
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
		setInventorySize(4, 2, 10);		// ó�� ������ 4 * 2 ������
		setItems();					// ������ �迭 ����
	}
	

	public int getPracticalPlusLayoutX() {
		return practicalPlusLayoutX;
	}

	public int getPracticalPlusLayoutY() {
		return practicalPlusLayoutY;
	}
	
	public void setInventorySize(int y, int x, double space) {		// �κ��丮 ������ ��ŭ ���� �ʱ�ȭ
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

	public void setInventoryResize(int y, int x) {		// �κ��丮 ����� �ø��� ȣ��
		int space = 10;
		if(x == 3)
			space = 5;
		else if(x == 4)
			space = 3;
		
		setInventorySize(y, x, space);
		setItems();
	}
	
	public void setItems() {	// ������ ��ü�� ���� �ʾ��� �� (���� ũ�⸦ �ø��ų� �Ͽ� �����۵� �ٽ� �����ؾ� �� �� ȣ��)
	
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
	
	public void setItems(Rectangle rect) {		// ������ ��ü�� �޾��� �� ȣ��
		for (int i = 0; i < items.length; i++) {
			for (int j = 0; j < items[i].length; j++) {
				if(items[i][j] != null)		// �ش� �ڸ��� �̹� �ڸ��� �ִٸ� ��Ƽ��
					continue;
				
				items[i][j] = rect;
				currentInsertItem.add(rect);		//
				itemsInfo.put(rect, getInfo(rect));
				this.AP.getChildren().remove(rect);
				KH.dropItem();			// ��� �ִ� ������ ��ü�� ���õ� �͵� null�� �ٲٱ�
				super.getChildren().add(items[i][j]);		// �κ��丮�� �߰�
				
				double XSpace = inventorySize[i][j].getLayoutX() + (inventorySize[i][j].getWidth() - items[i][j].getWidth()) / 2;
				double YSpace = inventorySize[i][j].getLayoutY() + (inventorySize[i][j].getHeight() - items[i][j].getHeight()) / 2;
				
				items[i][j].setLayoutX(XSpace);
				items[i][j].setLayoutY(YSpace);
				
				return;		// �߰��ϰ� ����
			}
		}
	}
	
	public void getItems() {		// X�� ���� �ֱٿ� ���� �������� �������� �� ��
		if(currentInsertItem.size() == 0)	// �ֱٿ� ���� �������� ������ ����
			return;

		for (int i = 0; i < inventorySize.length; i++) {
			for (int j = 0; j < inventorySize[i].length; j++) {

				if(items[i][j] == currentInsertItem.get(currentInsertItem.size() - 1)) {		// �ֱٿ� ���� ������ ã��
					
					super.getChildren().remove(items[i][j]);	// �ش� ������ �κ��丮���� ����
					items[i][j] = null;		// �ش� �ڸ� �η� �����
					KeyHandler.touchedRect = currentInsertItem.get(currentInsertItem.size() - 1);	// �ֱٿ� ���� ������ ������
					this.AP.getChildren().add(KeyHandler.touchedRect);		// AP�� �߰�
					Rectangle rect = currentInsertItem.get(currentInsertItem.size() - 1);
					currentInsertItem.remove(rect);		// �ֱٿ� ���� ������ �� �ε��� ����
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
	
	public void getItems(int num) {		// ���� �е带 �Է��Ͽ� �ش� �ڸ��� �������� �������� �� ��
		if(currentInsertItem.size() == 0 || KeyHandler.touchedRect != null)	// �ֱٿ� ���� �������� ���ų� �̹� ���⸦ ���� �ִٸ� ����
			return;
		
		num --;
		
		int numX = num % inventorySize[0].length;
		int numY = num / inventorySize[0].length;
		
		if(items[numY][numX] == null)
			return;
		
		Rectangle rect = items[numY][numX];
		super.getChildren().remove(rect);	// �ش� ������ �κ��丮���� ����
		currentInsertItem.remove(rect);	// �ֱ� ���� ������ list���� ����
		itemsInfo.remove(rect);
		KeyHandler.touchedRect = rect;	// �ش� ������ ������
		this.AP.getChildren().add(KeyHandler.touchedRect);		// AP�� �߰�
		items[numY][numX] = null;	// �κ��丮���� ����
					
		if(KeyHandler.left)
			KH.mountItem(-60);
		else
			KH.mountItem(-120);
					
		KeyHandler.touchedRect.setLayoutX(CO.player.getLayoutX() +
		CO.player.getWidth() / 2);
		KeyHandler.touchedRect.setLayoutY(CO.player.getLayoutY() +
		CO.player.getHeight() / 2);
	}
	
	public void moveItems(int y, int x) {		// �κ��丮�� �������� �ִ� ��Ȳ���� ���� ũ�Ⱑ �þ �� ������ �ű��
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
