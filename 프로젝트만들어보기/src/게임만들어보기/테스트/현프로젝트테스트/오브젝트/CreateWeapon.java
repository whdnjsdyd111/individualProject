package 게임만들어보기.테스트.현프로젝트테스트.오브젝트;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class CreateWeapon implements Objects {
	private Map<Rectangle, Integer> weapon = new HashMap<>();
	private CreateRandomInnerWall CRI = null;
	private Map<Rectangle, List<Rectangle>> randomFence = null;
	
	public CreateWeapon(CreateRandomInnerWall CRI) {
		this.CRI = CRI;
		randomFence = CRI.getRandomFence();
		makeWeapons();
	}
	
	private void makeWeapons() {
		int ran = (int) (Math.random() * 4) + 1;	// 1 ~ 4
		
		for (int i = 0; i < ran; i++) {
			double chance = Math.random();
			if(chance < 0.15) {
				weapon.put(makeObject(0), 2);
			} else if(chance < 0.3) {
				weapon.put(makeObject(1), 3);
			} else if(chance < 0.42) {
				weapon.put(makeObject(2), 3);
			} else if(chance < 0.54) {
				weapon.put(makeObject(3), 2);
			} else if(chance < 0.66) {
				weapon.put(makeObject(4), 2);
			} else if(chance < 0.76) {
				weapon.put(makeObject(5), 3);
			} else if(chance < 0.84) {
				weapon.put(makeObject(6), 3);
			} else if(chance < 0.9) {
				weapon.put(makeObject(7), 4);
			} else if(chance < 0.96) {
				weapon.put(makeObject(8), 5);
			} else if(chance < 1) {
				weapon.put(makeObject(9), 5);
			}
		}
	}
	
	@Override
	public Rectangle makeObject(int num) {
		Rectangle rect = new Rectangle();
		
		rect.setWidth(weightHeightWeapon[num][0]);
		rect.setHeight(weightHeightWeapon[num][1]);
		
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
		Image image = new Image(getClass().getResource("../이미지바인드/WeaponImage/" + weapons[num]).toString());
		
		rect.setStroke(Color.GREEN);
		rect.setFill(new ImagePattern(image));
		return rect;
	}
	
	public Map<Rectangle, Integer> getWeapons() {
		return weapon;
	}
}

