package ���Ӹ�����.�׽�Ʈ.��������Ʈ�׽�Ʈ.������Ʈ;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import ���Ӹ�����.�׽�Ʈ.��������Ʈ�׽�Ʈ.�̹������ε�.RoadTile.RoadTile;
import ���Ӹ�����.�׽�Ʈ.��������Ʈ�׽�Ʈ.��Ʈ�ѷ�_��������.ControlAPs;

public class CreateMap {
	private List<ImageView> raodImageList = new ArrayList<>();		// �ε� Ÿ�� �̹��� ����Ʈ
	private List<Rectangle> tilelist = new ArrayList<>();;		// Ÿ�� ����Ʈ
	private List<Rectangle> objectList = new ArrayList<>();		// ������Ʈ ����Ʈ
	public List<Rectangle> outTileList = new ArrayList<>();	// �ٱ� Ÿ�� ����Ʈ
	public CreateRandomInnerWall CRI;
	@FXML private AnchorPane AP;
	public boolean[][] randomTileNode;
	public CreateWeapon CW;
	public CreateFood CF;
	public CreateWater CWater;
	public CreateEnemy CE;
	public Rectangle area;
	public Rectangle player;

	// ���� �׵θ� = ��� ���ϴ� �ܺ�
	// �Ķ� �׵θ� = ��� ������ �ٴ�
	// ���� �׵θ� = ��� ���ϴ� ���� ��,  �μ��� �ǹ� ��
	// �̵�� ���� �׵θ�  = ��Ÿ��		��� Map���� Ű �� ������, ���� List�� �����ؼ� �� ���� ��Ÿ���� �Ѳ����� �̹����� �ٲ����
	// ��� �׵θ� = ��� ���� ( �� Ÿ�� ���� )
	// ������ �׵θ� = ��� ���ϴ� �ڵ��� ( ���� ������ �ڵ����� Ÿ�� ��� �߰� )
	// ��� �׵θ� = ����
	// ����� �׵θ� = ����
	// �ٴ��� �׵θ� = ��
	// ȸ�� �׵θ� = ��
	// ���� �׵α� = �÷��̾�
	
	public CreateMap(AnchorPane AP) {
		this.AP = AP;
		CRI = new CreateRandomInnerWall(100);	
		randomTileNode = CRI.getRandomTileNode();
		CW = new CreateWeapon(CRI);
		CF = new CreateFood(CRI);
		CWater = new CreateWater(CRI);
		
		roadTiles(100);
		objects();
		
		this.AP.getChildren().addAll(tilelist);
		this.AP.getChildren().addAll(raodImageList);
		
		this.AP.getChildren().addAll(CRI.getRandomInnerWall());
		this.AP.getChildren().addAll(CRI.getRandomHouseWall());
		this.AP.getChildren().addAll(CRI.getHouseImage());

		Set<Rectangle> set = CRI.getRandomFence().keySet();
		Iterator<Rectangle> iterator = set.iterator();
		
		while(iterator.hasNext()) {
			this.AP.getChildren().addAll(CRI.getRandomFence().get(iterator.next()));
		}
		
		this.AP.getChildren().addAll(objectList);
	}

	private void roadTiles(int area) {
		final double WID = AP.getPrefWidth();
		final double HEI = AP.getPrefHeight();
		
		final int WTile = (int) WID / 100;
		final int HTile = (int) HEI / 100;
		
		for(int i = 0; i <= HTile; i++) {
			for (int j = 0; j <= WTile; j++) {
				if(i == 0 || i == HTile - 1 || j == 0 || j == WTile - 1) {
					Rectangle rect = new Rectangle();
					RoadTile rt = new RoadTile(area, "�Ȱ�");
					rect.setWidth(area);
					rect.setHeight(area);
					rect.setFill(Color.WHITE);
					rect.setStroke(Color.RED);
					rect.setLayoutX(j * area);
					rect.setLayoutY(i * area);
					rt.setLayoutX(j * area);
					rt.setLayoutY(i * area);
					tilelist.add(rect);
					raodImageList.add(rt);
					outTileList.add(rect);
				} else {
					Rectangle rect = new Rectangle();
					RoadTile rt = new RoadTile(area);
					rect.setWidth(area);
					rect.setHeight(area);
					rect.setFill(Color.WHITE);
					rect.setStroke(Color.BLUE);
					rect.setLayoutX(j * area);
					rect.setLayoutY(i * area);
					rt.setLayoutX(j * area);
					rt.setLayoutY(i * area);
					tilelist.add(rect);
					raodImageList.add(rt);
				}
			}
		}
	}
	
	private void objects() {
		
		Set<Rectangle> set = CW.getWeapons().keySet();
		Iterator<Rectangle> iterator = set.iterator();
		
		while(iterator.hasNext())
			objectList.add(iterator.next());
		
		set = CF.getFoods().keySet();
		iterator = set.iterator();
		
		while(iterator.hasNext())
			objectList.add(iterator.next());
		
		set = CWater.getWaters().keySet();
		iterator = set.iterator();
		
		while(iterator.hasNext())
			objectList.add(iterator.next());
		
		Rectangle rect = new Rectangle();
		rect = new Rectangle();
		rect.setWidth(10);
		rect.setHeight(10);
		rect.setStroke(Color.BLACK);
		rect.setFill(Color.BLUE);
		rect.setLayoutX(ControlAPs.initX);
		rect.setLayoutY(ControlAPs.initY);
		objectList.add(rect);
	}
	
	public void playerArea(Rectangle player) {
		area = new Rectangle(150, 150, Color.AQUA);
		
		area.setLayoutX(player.getLayoutX() - area.getWidth() / 2);
		area.setLayoutY(player.getLayoutY() - area.getHeight() / 2);
		
		player.layoutXProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				area.setLayoutX(player.getLayoutX() - area.getWidth() / 2);
			}
		});
		
		player.layoutYProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				area.setLayoutY(player.getLayoutY() - area.getHeight() / 2);
			}
		});
		
		area.setOpacity(0);
		CE = new CreateEnemy(area, CRI, AP);
	}
}
