package 게임만들어보기.테스트.현프로젝트테스트.오브젝트;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import 게임만들어보기.테스트.현프로젝트테스트.모션.ZoomLocation;

public class HP {
	@FXML private AnchorPane AP;
	private CreateObjects CO;
	private Pane pane = null;
	private static Rectangle hp = new Rectangle(125, 15, Color.RED);
	private static Rectangle energy = new Rectangle(60, 15, Color.BLUE);
	private static Rectangle thirst = new Rectangle(60, 15, Color.FORESTGREEN);
	
	private Runnable run = new Runnable() {
	
		@Override
		public void run() {
			boolean run = true;
			while(run) {
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			
				energy.setWidth(energy.getWidth() - 0.5);
				thirst.setWidth(thirst.getWidth() - 0.5);
				
				if(energy.getWidth() <= 0 ||  thirst.getWidth() <= 0) {
					run = false;
					die();
				}
				
			}
			return;
		}
	};
	
	public Thread thread1 = null;
	
	public HP(AnchorPane AP, CreateObjects CO) {
		this.AP = AP;
		this.CO = CO;
		pane = new Pane();
		pane.setPrefWidth(200);
		pane.setPrefHeight(100);
		pane.setLayoutX(CO.player.getLayoutX() + 10);
		pane.setLayoutY(CO.player.getLayoutY() + 50);
		
		hp.setLayoutX(0);
		hp.setLayoutY(0);
		hp.setFill(Color.RED);
		
		energy.setLayoutX(0);
		energy.setLayoutY(25);
		energy.setFill(Color.BLUE);
		
		thirst.setLayoutX(65);
		thirst.setLayoutY(25);
		thirst.setFill(Color.FORESTGREEN);
		
		hp.setOpacity(0.5);
		energy.setOpacity(0.5);
		thirst.setOpacity(0.5);
		
		pane.getChildren().add(hp);
		pane.getChildren().add(energy);
		pane.getChildren().add(thirst);
		
		this.AP.getChildren().add(pane);
		CO.player.layoutXProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				pane.setLayoutX((double) newValue + 10);
			}
		});
		CO.player.layoutYProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				pane.setLayoutY((double) newValue + 50);
			}
		});
		
		thread1 = new Thread(run);
		thread1.setName("Energy & Thirst Bar");
		thread1.setDaemon(true);
		thread1.start();
		
	}
	
	public void reduceHp(int gauge) {
		if(hp.getWidth() - gauge <= 0) {
			hp.setWidth(0);
			die();
		}
		else
			hp.setWidth(hp.getWidth() - gauge);
	}
	
	public void upHp(int gauge) {
		if(hp.getWidth() + gauge >= 125)
			hp.setWidth(125);
		else
			hp.setWidth(hp.getWidth() + gauge);
	}
	
	public void upEnergy(int gauge) {
		if(energy.getWidth() + gauge >= 60)
			energy.setWidth(60);
		else
			energy.setWidth(energy.getWidth() + gauge);
		
		upHp(5);
	}
	
	public void upThirst(int gauge) {
		if(thirst.getWidth() + gauge >= 60)
			thirst.setWidth(60);
		else
			thirst.setWidth(thirst.getWidth() + gauge);
	}
	
	@SuppressWarnings("deprecation")
	private void die() {
		Button cancelBtn  = new Button("The End");
		cancelBtn.setPrefWidth(80);
		cancelBtn.setPrefHeight(50);
		cancelBtn.setLayoutX(CO.player.getLayoutX() - cancelBtn.getPrefWidth() / 2);
		cancelBtn.setLayoutY(CO.player.getLayoutY() - cancelBtn.getPrefHeight() / 2);
		
		CO.player = null;
		CO.removeKH();
		thread1.stop();
		
		Platform.runLater(() -> {
			AP.getChildren().add(cancelBtn);
		});
		
		cancelBtn.setOnAction(e -> {
			handleCancleBtn();
			ZoomLocation.setInit();
		});
	}
	
	private void handleCancleBtn() {
		StackPane root = (StackPane) AP.getScene().getRoot();
		root.getChildren().remove(AP);
		AP = null;

	}
	
	public static void setInit() {
		hp.setWidth(125);
		thirst.setWidth(60);
		energy.setWidth(60);
	}
}
