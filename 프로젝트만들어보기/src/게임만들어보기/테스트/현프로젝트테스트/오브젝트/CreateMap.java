package 게임만들어보기.테스트.현프로젝트테스트.오브젝트;

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
import 게임만들어보기.테스트.현프로젝트테스트.이미지바인드.RoadTile.RoadTile;
import 게임만들어보기.테스트.현프로젝트테스트.컨트롤러_스테이지.ControlAPs;

public class CreateMap {
	private List<ImageView> raodImageList = new ArrayList<>();		// 로드 타일 이미지 리스트
	private List<Rectangle> tilelist = new ArrayList<>();;		// 타일 리스트
	private List<Rectangle> objectList = new ArrayList<>();		// 오브젝트 리스트
	public List<Rectangle> outTileList = new ArrayList<>();	// 바깥 타일 리스트
	public CreateRandomInnerWall CRI;
	@FXML private AnchorPane AP;
	public boolean[][] randomTileNode;
	public CreateWeapon CW;
	public CreateFood CF;
	public CreateWater CWater;
	public CreateEnemy CE;
	public Rectangle area;
	public Rectangle player;

	// 빨간 테두리 = 통과 못하는 외벽
	// 파란 테두리 = 통과 가능한 바닥
	// 보라 테두리 = 통과 못하는 내벽 집,  부서진 건물 등
	// 미디엄 보라 테두리  = 울타리		얘는 Map으로 키 는 숫자형, 값은 List로 저장해서 각 집의 울타리를 한꺼번에 이미지로 바꿔놓기
	// 노랑 테두리 = 통과 가능 ( 집 타일 영역 )
	// 오렌지 테두리 = 통과 못하는 자동차 ( 여유 있으면 자동차를 타는 기능 추가 )
	// 녹색 테두리 = 무기
	// 봄녹색 테두리 = 음식
	// 바닷녹색 테두리 = 물
	// 회색 테두리 = 적
	// 검정 테두기 = 플레이어
	
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
					RoadTile rt = new RoadTile(area, "안개");
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
