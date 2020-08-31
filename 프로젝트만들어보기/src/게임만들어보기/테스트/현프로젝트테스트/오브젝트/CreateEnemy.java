package 게임만들어보기.테스트.현프로젝트테스트.오브젝트;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CreateEnemy implements Objects {
	@FXML private AnchorPane AP;
	Rectangle area = null;
	private CreateRandomInnerWall CRI = null;
	private CreateObjects CO = null;
	private Map<Rectangle, List<Rectangle>> randomFence = null;
	private Map<Rectangle, Integer> enemy = new HashMap<>();
	private Set<Rectangle> recognEnemy = new HashSet<>();
	private Set<Rectangle> noRecognEnemy = new HashSet<>();
	private Thread createContinueEnemy = null;
	private Thread continueMoveEnemy = null;
	private Thread followPlayerEnemy = null;
	
	public CreateEnemy(Rectangle area, CreateRandomInnerWall CRI, AnchorPane AP) {
		this.area = area;
		this.CRI = CRI;
		this.AP = AP;
		randomFence = CRI.getRandomFence();
		
		int random = (int)(Math.random() * 20 + 10); // 20 ~ 40
		
		for (int i = 0; i < random; i++) {
			int ran = (int)(Math.random() * 5 + 10);	// 10 ~ 15
			Rectangle rect = makeObject(0);
			enemy.put(rect, ran);
			noRecognEnemy.add(rect);
		}
		
		createContinueEnemy = new Thread() {
			@Override
			public void run() {
				while(true) {
					if(enemy.size() >= 35)
						continue;
					try {
						int random = (int)(Math.random() * 3 + 1);
						Thread.sleep(2000 * random);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					int randomX = (int)(Math.random() * 2);
					int randomY = (int)(Math.random() * 2);
					
					double X = 0.0;
					double Y = 0.0;
					
					if(randomX == 0)
						X = Math.random() * 10 + 100;
					else
						X = Math.random() * 10 + 780;
					
					if(randomY == 0)
						Y = Math.random() * 100 + 200;
					else
						Y = Math.random() * 100 + 400;
					
					Rectangle rect = new Rectangle(10, 10, Color.GRAY);
					rect.setFill(Color.GRAY);
					rect.setLayoutX(X);
					rect.setLayoutY(Y);
					Platform.runLater(() -> {
						AP.getChildren().add(rect);
					});
					enemy.put(rect, 9);
					noRecognEnemy.add(rect);
					CO.NonePassEnemy.add(rect);
					CO.DamegedEnemy.put(rect, false);
					CO.EnemyHP.put(rect, 9);
				}
			}
		};
		
		continueMoveEnemy = new Thread() {
			@Override
			public void run() {
				while(true) {
					try {
						int random = (int)(Math.random() * 3 + 1);
						Thread.sleep(250 * random);
					} catch (InterruptedException e) {
						
					}
					
					Iterator<Rectangle> iterator = noRecognEnemy.iterator();
					
					while(iterator.hasNext()) {
						try {
							int random = (int)(Math.random() * 2 + 1);
							Thread.sleep(5 * random);
						} catch (InterruptedException e) {
							break;
						}
						Rectangle rect = null;
						try {
							Thread.sleep(1);
							rect = iterator.next();
						} catch (ConcurrentModificationException e) {
							break;
						} catch (InterruptedException e) {
							break;
						}
						rect.setLayoutX(rect.getLayoutX() + (int)(Math.random() * 3) - 1);
						rect.setLayoutY(rect.getLayoutY() + (int)(Math.random() * 3) - 1);
					}
				}
			}
		};
		
		followPlayerEnemy = new Thread() {
			@Override
			public void run() {
				while(true) {
					Iterator<Rectangle> iterator = recognEnemy.iterator();
					
					while(iterator.hasNext()) {
						Rectangle rect = null;
						
						try {
							Thread.sleep(1);
							rect = iterator.next();
						} catch (ConcurrentModificationException e) {
							break;
						} catch (InterruptedException e) {
							break;
						}
						
						if(Math.abs(CO.player.getLayoutX() - rect.getLayoutX()) >= 100 ||
								Math.abs(CO.player.getLayoutY() - rect.getLayoutY()) >= 100) {
							noRecognEnemy.add(rect);
							iterator.remove();
							continue;
						}
						
						double deltaX = Math.abs(CO.player.getLayoutX() - rect.getLayoutX());
						double deltaY = Math.abs(CO.player.getLayoutY() - rect.getLayoutY());
						
						double angle = Math.atan2(deltaY, deltaX) * (180d / Math.PI);
						
						double height = Math.abs(Math.cos(Math.abs(angle)) * 2);
						double width = Math.abs(Math.sin(Math.abs(angle)) * 2);
															
						if(rect.getLayoutX() > CO.player.getLayoutX())
							width *= -1;
						
						if(rect.getLayoutY() > CO.player.getLayoutY())
							height *= -1;
						
						
						rect.setLayoutX(rect.getLayoutX() + width);
						rect.setLayoutY(rect.getLayoutY() + height);
						
						try {
							Thread.sleep(100 / recognEnemy.size());
						} catch (InterruptedException e) {
							break;
						}
					}
				}
			}
		};
		
		area.layoutXProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				removeRecognEnemy();
			}
		});
		
		area.layoutYProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				removeRecognEnemy();
			}
		});
	}

	@Override
	public Rectangle makeObject(int num) {
		Rectangle rect = new Rectangle();
		
		rect.setWidth(10);
		rect.setHeight(10);
		
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
			
			if(checkIn(ranX1, ranY1, ranX2, ranY2, area))
				run = true;
			else if(checkMid(ranX1, ranY1, ranX2, ranY2, area))
				run = true;
			
			if(!run) {
				rect.setLayoutX(ranX1);
				rect.setLayoutY(ranY1);
			}
			
		}
		
		rect.setStroke(Color.GRAY);
		rect.setFill(Color.GRAY);
		return rect;
	}
	
	private void removeRecognEnemy() {
		Iterator<Rectangle> iterator = noRecognEnemy.iterator();
		while(iterator.hasNext()) {
			Rectangle rect = null;
			
			try {
				rect = iterator.next();
			} catch (ConcurrentModificationException e) {
				break;
			}
			if(	(area.getLayoutX() <= rect.getLayoutX() && rect.getLayoutX() <= area.getLayoutX() + area.getWidth()) &&
				(area.getLayoutY() <= rect.getLayoutY() && rect.getLayoutY() <= area.getLayoutY() + area.getHeight())) {
				recognEnemy.add(rect);
				iterator.remove();
			}		
		}
	}
	
	public Map<Rectangle, Integer> getEnemy() {
		return enemy;
	}
	
	public Set<Rectangle> getRecognEnemy() {
		return recognEnemy;
	}

	public Set<Rectangle> getNoRecognEnemy() {
		return noRecognEnemy;
	}

	public Rectangle getArea() {
		return area;
	}

	public CreateRandomInnerWall getCRI() {
		return CRI;
	}

	public Map<Rectangle, List<Rectangle>> getRandomFence() {
		return randomFence;
	}

	public Thread getCreateContinueEnemy() {
		return createContinueEnemy;
	}

	public Thread getContinueMoveEnemy() {
		return continueMoveEnemy;
	}

	public Thread getFollowPlayerEnemy() {
		return followPlayerEnemy;
	}

	public void setCO(CreateObjects CO) {
		this.CO = CO;
		
		createContinueEnemy.setName("Continue To Create Enemy Thread");
		createContinueEnemy.setDaemon(true);
		createContinueEnemy.start();
		
//		continueMoveEnemy.setName("Continue To Move NoRecognEnemy Thread");
//		continueMoveEnemy.setDaemon(true);
//		continueMoveEnemy.start();
//		
//		followPlayerEnemy.setName("Follow To Player Thread");
//		followPlayerEnemy.setDaemon(true);
//		followPlayerEnemy.start();
	}
	
}
