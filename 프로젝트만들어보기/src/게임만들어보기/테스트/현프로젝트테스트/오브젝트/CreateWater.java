package 게임만들어보기.테스트.현프로젝트테스트.오브젝트;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class CreateWater implements Objects {
	private Map<Rectangle, Integer> water = new HashMap<>();
	private CreateRandomInnerWall CRI = null;
	private Map<Rectangle, List<Rectangle>> randomFence = null;
	
	public CreateWater(CreateRandomInnerWall CRI) {
		this.CRI = CRI;
		randomFence = CRI.getRandomFence();
		makeWaters();
	}
	
	private void makeWaters() {
		int ran = (int) (Math.random() * 6) + 3;	// 3 ~ 8	
		
		for (int i = 0; i < ran; i++) {
			double chance = Math.random();
			if(chance < 0.2) {
				water.put(makeObject(0), 3);
			} else if(chance < 0.4) {
				water.put(makeObject(1), 3);
			} else if(chance < 0.6) {
				water.put(makeObject(2), 3);
			} else if(chance < 0.8) {
				water.put(makeObject(3), 3);
			} else if(chance < 0.1) {
				water.put(makeObject(4), 3);
			}
		}
	}
	
	@Override
	public Rectangle makeObject(int num) {
		Rectangle rect = new Rectangle();
		
		rect.setWidth(weightHeightWater[num][0]);
		rect.setHeight(weightHeightWater[num][1]);
		
		boolean run = true;
		
		while(run) {
			double ranX1 = Math.random() * 700 + 100;
			double ranX2 = ranX1 + rect.getWidth();
			double ranY1 = Math.random() * 400 + 100;
			double ranY2 = ranY1 + rect.getHeight();
			
			for(int i = 0; i < CRI.getRandomInnerWall().size(); i++) {
				Rectangle ranInner = CRI.getRandomInnerWall().get(i);
				Rectangle ranHome = CRI.getRandomHouseWall().get(i);
				List<Rectangle> fence = randomFence.get(ranInner);

				if(checkIn(ranX1, ranY1, ranX2, ranY2, ranInner)
					&& (ranY1 > ranHome.getLayoutY() + ranHome.getHeight() && ranY2 > ranHome.getLayoutY() + ranHome.getHeight())) {
					run = false;
					
					if(fence.size() == 3) {
						run = true;
						break;
					} else {
						boolean bool = false;
						
						for (int j = 0; j < fence.size(); j++) {
							if(checkMid(ranX1, ranY1, ranX2, ranY2, fence.get(j))) {
								bool = true;
								break;
							}
						}
						
						if(bool) {
							run = true;
							break;
						}
					}
				} else if(checkMid(ranX1, ranY1, ranX2, ranY2, ranInner)) {
					run = true;
					break;
				} else {
					run = false;
				}
			}
			
			if(!run) {
				rect.setLayoutX(ranX1);
				rect.setLayoutY(ranY1);
			}
			
		}
		Image image = new Image(getClass().getResource("../이미지바인드/WaterImage/" + waters[num]).toString());
		
		rect.setStroke(Color.SEAGREEN);
		rect.setFill(new ImagePattern(image));
		return rect;
	}
	
	public Map<Rectangle, Integer> getWaters() {
		return water;
	}
}
