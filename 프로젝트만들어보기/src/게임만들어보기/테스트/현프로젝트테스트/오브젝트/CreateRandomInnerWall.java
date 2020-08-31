package ���Ӹ�����.�׽�Ʈ.��������Ʈ�׽�Ʈ.������Ʈ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import ���Ӹ�����.�׽�Ʈ.��������Ʈ�׽�Ʈ.�̹������ε�.HouseImage.HouseTile;
import ���Ӹ�����.�׽�Ʈ.��������Ʈ�׽�Ʈ.��Ʈ�ѷ�_��������.ControlAPs;

public class CreateRandomInnerWall {
	private boolean[][] randomTileNode;
	private List<Rectangle> randomInnerWall = new ArrayList<Rectangle>();	// �ʷϻ� �ܵ� Ÿ��
	private List<Rectangle> randomHouseWall = new ArrayList<Rectangle>();	// ����� �� Ÿ��
	private Map<Rectangle, List<Rectangle>> randomFence = new HashMap<>();	// �̵�� ����� ��Ÿ�� Ÿ��
	private List<ImageView> houseImage = new ArrayList<>();	// �� �̹���
	
	int area;
	double dividedArea;
	
	public CreateRandomInnerWall(int area) {		// CreateRandomInnerWall ������
		this.area = area;					// Ÿ�� ���� 100
		this.dividedArea = area / 3;		// Ÿ���� ������ 3���� 1
		this.randomTileNode = RandomTile();	// 
		setInnerWall();
		setRandomHouseTile();
		setFenceTile();
	}

	private boolean[][] RandomTile() {
		
		boolean random[][] = new boolean[4][7];		// ��� ������ Ÿ�� ��� true
		
		int randomNum1 = (int) (Math.random() * 2) + 3;	// 3 4
	
		int num1 = 0;
		
		if(ControlAPs.bottom) {
			random[0][(int)(ControlAPs.initX / 100) - 1] = true;
			num1++;
			if((ControlAPs.initX - (int) (ControlAPs.initX / 100) * 100) / 10 > 9) {
				random[0][(int)(ControlAPs.initX / 100)] = true;
				num1++;
			}
		}
		
		while(num1 < randomNum1) {
			int ran = (int) (Math.random() * 7);
			
			if(random[0][ran])
				continue;
			else {
				random[0][ran] = true;
				num1++;
			}
		}
		
		ControlAPs.bottom = false;
		int randomNum2 = (int) (Math.random() * 2) + 3;	// 3 4
		
		int num2 = 0;
		while(num2 < randomNum2) {
			int ran = (int) (Math.random() * 7);
			if(random[2][ran])
				continue;
			else {
				random[2][ran] = true;
				num2++;
			}
		}
		return random;
	}
	
	public void setInnerWall() {
		for (int i = 0; i < randomTileNode.length; i++) {
			if(i == 1 | i == 3)
				continue;
			for (int j = 0; j < randomTileNode[i].length; j++) {
				if(!randomTileNode[i][j]) {
					int random = (int)(Math.random() * 4 + 1);
					Rectangle rect = new Rectangle();
					rect.setWidth(area);
					rect.setHeight(area);
					rect.setFill(new ImagePattern(new Image(
							getClass().getResource("../�̹������ε�/RoadTile/HouseTile" + random +  ".png").toString())));
					rect.setStroke(Color.YELLOW);
					rect.setLayoutX(area + j * area);
					rect.setLayoutY(area + i * area);
					randomInnerWall.add(rect);
				}	
			}
		}
	}

	private void setRandomHouseTile() {
		Iterator<Rectangle> iterator = randomInnerWall.iterator();
		
		while(iterator.hasNext()) {
			Rectangle rect = iterator.next();
			
			randomHouseWall.addAll(makeHouseTile(rect.getLayoutX(), rect.getLayoutY()));
		}
	}
	
	private List<Rectangle> makeHouseTile(double x, double y) {
		List<Rectangle> list = new ArrayList<Rectangle>();
		
		Rectangle house = new Rectangle();
		
		house.setStroke(Color.PURPLE);
		house.setFill(Color.MEDIUMPURPLE);
		
		house.setLayoutX(x);
		house.setLayoutY(y);
		house.setWidth(area);
		house.setHeight(dividedArea * 2);
		house.setOpacity(0);
		
		list.add(house);
		
		ImageView hi = new HouseTile(area);
		hi.setLayoutX(x - 5);
		hi.setLayoutY(y);
		
		houseImage.add(hi);
		
		return list;
	}
	
	private void setFenceTile() {
		
		for (int i = 0; i < randomInnerWall.size(); i++) {
			List<Rectangle> list = makeFenceTile(randomInnerWall.get(i).getLayoutX(), 
					randomInnerWall.get(i).getLayoutY());
			
			randomFence.put(randomInnerWall.get(i), list);
		}
		
	}
	
	private List<Rectangle> makeFenceTile(double x, double y) {
		List<Rectangle> list = new ArrayList<Rectangle>();
		int random = (int)(Math.random() * 4 + 1); 
		
		Image image1 = new Image(getClass().getResource("../�̹������ε�/FenceImage/��Ÿ��" + random +  "-1.png").toString());
		Image image2 = new Image(getClass().getResource("../�̹������ε�/FenceImage/��Ÿ��" + random +  "-2.png").toString());
		
		Rectangle rect = new Rectangle(10, dividedArea);
		rect.setLayoutX(x);
		rect.setLayoutY(y + dividedArea * 2);
		rect.setStroke(Color.MEDIUMPURPLE);
		rect.setFill(new ImagePattern(image1));
		
		list.add(rect);
		
		Rectangle rect1 = new Rectangle(10, dividedArea);
		rect1.setLayoutX(x + area - rect.getWidth());
		rect1.setLayoutY(y + dividedArea * 2);
		rect1.setStroke(Color.MEDIUMPURPLE);
		rect1.setFill(new ImagePattern(image1));
		
		list.add(rect1);
		
		Rectangle[] arr = { new Rectangle(area, 10),
				new Rectangle(dividedArea * 1, 10), 
				new Rectangle(dividedArea * 1, 10)};
		
		int num = (int) (Math.random() * 3); 
		
		if(num == 0) {
			
		} else if(num == 1) {
			arr[0].setStroke(Color.MEDIUMPURPLE);
			arr[0].setFill(new ImagePattern(image2));
			arr[0].setLayoutX(x);
			arr[0].setLayoutY(y + area - 10);
			list.add(arr[0]);
		} else {
			arr[1].setStroke(Color.MEDIUMPURPLE);
			arr[1].setFill(new ImagePattern(image2));
			arr[1].setLayoutX(x);
			arr[1].setLayoutY(y + area - 10);
			list.add(arr[1]);
			
			arr[2].setStroke(Color.MEDIUMPURPLE);
			arr[2].setFill(new ImagePattern(image2));
			arr[2].setLayoutX(x + area - dividedArea);
			arr[2].setLayoutY(y + area - 10);
			list.add(arr[2]);
		}
		
		
		return list;
	}
	
	public boolean[][] getRandomTileNode() {
		return this.randomTileNode;
	}
	
	public List<Rectangle> getRandomInnerWall(){
		return this.randomInnerWall;
	}
	
	public List<Rectangle> getRandomHouseWall(){
		return this.randomHouseWall;
	}
	
	public Map<Rectangle, List<Rectangle>> getRandomFence() {
		return this.randomFence;
	}
	
	public List<ImageView> getHouseImage(){
		return this.houseImage;
	}
}
