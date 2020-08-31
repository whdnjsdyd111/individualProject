package 게임만들어보기.테스트.현프로젝트테스트.컨트롤러_스테이지;

import java.io.IOException;
import java.lang.management.MemoryUsage;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import 게임만들어보기.테스트.현프로젝트테스트.모션.MotionPlayer;
import 게임만들어보기.테스트.현프로젝트테스트.모션.ZoomLocation;
import 게임만들어보기.테스트.현프로젝트테스트.오브젝트.CreateMap;
import 게임만들어보기.테스트.현프로젝트테스트.오브젝트.CreateObjects;
import 게임만들어보기.테스트.현프로젝트테스트.오브젝트.Inventory;
import 게임만들어보기.테스트.현프로젝트테스트.키핸들러.KeyHandler;

public class TutorialController implements Initializable {
	@FXML private AnchorPane AP;
	@FXML private Rectangle player;
	@FXML private Scene scene;
	private MotionPlayer motions;
	private CreateMap map;
	private CreateObjects CO;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		setInit();
		
		AnimationTimer timer = new AnimationTimer() {	// 플레이어 움직임을 감지하는 애니메이션 타이머 시작
			@Override
			public void handle(long now) {
				int dx = 0, dy = 0;

				if (KeyHandler.up)
					dy -= 1;
				if (KeyHandler.down)
					dy += 1;
				if (KeyHandler.left)
					dx -= 1;
				if (KeyHandler.right)
					dx += 1;

				motions.movePlayerBy(dx, dy);
			}
		};
		
		timer.start();

	}
	
	public void setInit() {
		motions = null;
		CO = null;
		map = null;
		
		map = new CreateMap(AP);		// 맵 객체 생성
		CO = new CreateObjects(AP, map);	// 오브젝트 관리 클래스
		motions = new MotionPlayer(AP, CO, map, this);	// 모션 클래스
		
		AP.setScaleX(ZoomLocation.getScale());	// AP에 스케일 넣기
		AP.setScaleY(ZoomLocation.getScale());		
		ZoomLocation.setInit();
		AP.setTranslateX(-ZoomLocation.currentZoomX);
		AP.setTranslateY(-ZoomLocation.currentZoomY);
	}
	
	@SuppressWarnings("deprecation")
	public void changeStage() {
		try {
			StackPane root = (StackPane) AP.getScene().getRoot();
			AP.getChildren().remove(KeyHandler.touchedRect);
			root.getChildren().remove(AP);
			AP = null;
			if(KeyHandler.touchedRect != null)
				Inventory.itemsInfo.put(KeyHandler.touchedRect, CO.inventory.getInfo(KeyHandler.touchedRect));
			CO.HP.thread1.stop();
			Parent tutorial = FXMLLoader.load(getClass().getResource("TutorialStage.fxml"));
			AP = (AnchorPane) tutorial;
			root.getChildren().add(AP);
			if(KeyHandler.touchedRect != null)
				AP.getChildren().add(KeyHandler.touchedRect);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
