package 게임만들어보기.테스트.현프로젝트테스트.모션;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import 게임만들어보기.테스트.현프로젝트테스트.오브젝트.CreateMap;
import 게임만들어보기.테스트.현프로젝트테스트.오브젝트.CreateObjects;
import 게임만들어보기.테스트.현프로젝트테스트.컨트롤러_스테이지.ControlAPs;
import 게임만들어보기.테스트.현프로젝트테스트.컨트롤러_스테이지.TutorialController;
import 게임만들어보기.테스트.현프로젝트테스트.키핸들러.KeyHandler;

public class MotionPlayer {
	@FXML private AnchorPane AP = null;
	private CreateObjects CO;
	private CreateMap CM;
	private TutorialController TC = null;
	private double speed = 1.5;
	private Button moveBtn;
	private ChangeListener<Number> changeX = new ChangeListener<Number>() {
		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			moveBtn.setLayoutX(CO.player.getLayoutX() - moveBtn.getWidth() / 2);
		}
	};
	
	private ChangeListener<Number> changeY = new ChangeListener<Number>() {
		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			moveBtn.setLayoutY(CO.player.getLayoutY() - moveBtn.getHeight() / 2);
		}
	};
	
	public MotionPlayer(AnchorPane AP, CreateObjects CO, CreateMap CM, TutorialController TC) {
		this.AP = AP;
		this.CO = CO;
		this.CM = CM;
		this.TC = TC;
	}
	
	public void movePlayerBy(int dx, int dy) {		// 플레이어 움직이기
		if (dx == 0 && dy == 0)
			return;
		
		if(CO.player == null)
			return;

		double playerx = CO.player.getLayoutX() - CO.player.getStrokeWidth() / 2;
		double playery = CO.player.getLayoutY() - CO.player.getStrokeWidth() / 2;
		
		speed = 1.5;

		double x = playerx + dx * speed;
		double y = playery + dy * speed;
		try {
			movePlayerTo(x ,y, playerx, playery, dx, dy);
		} catch (NullPointerException e) {}
			
		MountingWeapon();	// 무기 위치
	}
	
	private void movePlayerTo(double x, double y, double playerx, double playery, int dx, int dy)
		throws NullPointerException{	// 벽인지 판단하고 움직이기
		
		boolean touchXY = false;
		boolean touchX = false;
		boolean touchY = false;
		
		double rectX1 = 0.0;
		double rectY1 = 0.0;
		double rectX2 = 0.0;
		double rectY2 = 0.0;
		
		for (int i = 0; i < CO.NonePassObj.size(); i++) {
			rectX1 = CO.NonePassObj.get(i).getLayoutX() - CO.player.getWidth();
			rectX2 = CO.NonePassObj.get(i).getLayoutX() + CO.NonePassObj.get(i).getWidth();
			rectY1 = CO.NonePassObj.get(i).getLayoutY() - CO.player.getHeight();
			rectY2 = CO.NonePassObj.get(i).getLayoutY() + CO.NonePassObj.get(i).getHeight();
			
				
			if((x < rectX1 || x > rectX2) || (y < rectY1 || y > rectY2)) {	// 해당 오브젝으 영역 밖

			} else if(x > rectX1 && x < rectX2) {	// 이동 하려는 곳이 X 영역 안
					
				if(playery > rectY1 && playery < rectY2) {	// 세로면 이 닿고 있다고 판정
					touchX = true;
					if(CM.outTileList.contains(CO.NonePassObj.get(i)))
						handleMoveStage();
				} else if(y > rectY1 && y < rectY2){
					touchY = true;	// 가로면이 닿고 있다고 판정
					if(CM.outTileList.contains(CO.NonePassObj.get(i)))
						handleMoveStage();
				}
			}
		}

		for(int i = 0 ; i < CO.NonePassEnemy.size(); i++) {
			rectX1 = CO.NonePassEnemy.get(i).getLayoutX() - CO.player.getWidth();
			rectX2 = CO.NonePassEnemy.get(i).getLayoutX() + CO.NonePassEnemy.get(i).getWidth();
			rectY1 = CO.NonePassEnemy.get(i).getLayoutY() - CO.player.getHeight();
			rectY2 = CO.NonePassEnemy.get(i).getLayoutY() + CO.NonePassEnemy.get(i).getHeight();
			
			if((x < rectX1 || x > rectX2) || (y < rectY1 || y > rectY2)) {	// 해당 오브젝으 영역 밖
				
			} else if(x > rectX1 && x < rectX2) {	// 이동 하려는 곳이 X 영역 안
					
				if(playery > rectY1 && playery < rectY2) {	// 세로면 이 닿고 있다고 판정
					touchX = true;
					CO.damegedPlayer(CO.NonePassEnemy.get(i), dx ,dy);
					moveZoom((-dx) * 7, (-dy) * 7);
				} else if(y > rectY1 && y < rectY2){
					touchY = true;	// 가로면이 닿고 있다고 판정
					CO.damegedPlayer(CO.NonePassEnemy.get(i), dx, dy);
					moveZoom((-dx) * 7, (-dy) * 7);
				}
			}
				
		}
		
		if(touchXY || (touchY && touchX)) {
			CO.player.relocate(playerx, playery);
			moveZoom(0, 0);
			moveInventory();
		} else if(touchY) {
			CO.player.relocate(x, playery);
			moveZoom(dx * speed, 0);
			moveInventory();
		} else if(touchX) {
			CO.player.relocate(playerx, y);
			moveZoom(0, dy * speed);
			moveInventory();
		} else {
			CO.player.relocate(x, y);
			moveZoom(dx * speed, dy * speed);
			moveInventory();
			CO.player.layoutXProperty().removeListener(changeX);
			CO.player.layoutYProperty().removeListener(changeY);
			Platform.runLater(() -> {
				AP.getChildren().remove(moveBtn);
				moveBtn = null;
			});
		}
	}
	
	private void moveZoom(double dx, double dy) {	// 캐릭터 줌
		ZoomLocation.currentZoomX += dx * ZoomLocation.getScale();
		ZoomLocation.currentZoomY += dy * ZoomLocation.getScale();
		
		AP.setTranslateX(-ZoomLocation.currentZoomX);
		AP.setTranslateY(-ZoomLocation.currentZoomY);
	}
	
	private void moveInventory() {
		CO.inventory.setLayoutX(CO.player.getLayoutX() +
				CO.inventory.getPracticalPlusLayoutX());
		
		CO.inventory.setLayoutY(CO.player.getLayoutY() +
				CO.inventory.getPracticalPlusLayoutY());
	}
	
	private void MountingWeapon() {		// 무기 장착하기 
		if(KeyHandler.touchedRect == null || CO.player == null)
			return;
		
		KeyHandler.touchedRect.setLayoutX(CO.player.getLayoutX() + CO.player.getWidth() / 2);
		KeyHandler.touchedRect.setLayoutY(CO.player.getLayoutY() + CO.player.getHeight() / 2);
	}
	
	private void handleMoveStage() {
		if(moveBtn != null)
			return;
		
		moveBtn  = new Button("Do you want to move\n on to the next stage?");
		
		moveBtn.setStyle("-fx-font-size: 6");
		moveBtn.setPrefWidth(80);
		moveBtn.setPrefHeight(50);
		
		moveBtn.setOnAction(e -> {
			moveStage();
			ZoomLocation.setInit();
		});
		
		Platform.runLater(() -> {
			moveBtn.setLayoutX(CO.player.getLayoutX() - moveBtn.getWidth() / 2);
			moveBtn.setLayoutY(CO.player.getLayoutY() - moveBtn.getHeight() / 2);			
		});

		CO.player.layoutXProperty().addListener(changeX);
		CO.player.layoutYProperty().addListener(changeY);
		
		AP.getChildren().add(moveBtn);
		moveBtn.toFront();
	}
	
	private void moveStage() {
		ControlAPs.setInit(CO.player);
		CO.player = null;
		CO.removeKH();
		TC.changeStage();
	}
}
