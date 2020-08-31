package ���Ӹ�����.�׽�Ʈ.��������Ʈ�׽�Ʈ.��Ʈ�ѷ�_��������;

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
import ���Ӹ�����.�׽�Ʈ.��������Ʈ�׽�Ʈ.���.MotionPlayer;
import ���Ӹ�����.�׽�Ʈ.��������Ʈ�׽�Ʈ.���.ZoomLocation;
import ���Ӹ�����.�׽�Ʈ.��������Ʈ�׽�Ʈ.������Ʈ.CreateMap;
import ���Ӹ�����.�׽�Ʈ.��������Ʈ�׽�Ʈ.������Ʈ.CreateObjects;
import ���Ӹ�����.�׽�Ʈ.��������Ʈ�׽�Ʈ.������Ʈ.Inventory;
import ���Ӹ�����.�׽�Ʈ.��������Ʈ�׽�Ʈ.Ű�ڵ鷯.KeyHandler;

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
		
		AnimationTimer timer = new AnimationTimer() {	// �÷��̾� �������� �����ϴ� �ִϸ��̼� Ÿ�̸� ����
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
		
		map = new CreateMap(AP);		// �� ��ü ����
		CO = new CreateObjects(AP, map);	// ������Ʈ ���� Ŭ����
		motions = new MotionPlayer(AP, CO, map, this);	// ��� Ŭ����
		
		AP.setScaleX(ZoomLocation.getScale());	// AP�� ������ �ֱ�
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
