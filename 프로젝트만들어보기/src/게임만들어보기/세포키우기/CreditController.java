package 게임만들어보기.세포키우기;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class CreditController implements Initializable {
	@FXML private AnchorPane AnchorPane;
	@FXML private Button CancelBtn;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		CancelBtn.setOnAction(event -> {
			handleCancelBtn(event);
		});
		
		AnchorPane Ap = (AnchorPane) AnchorPane.getChildren().get(0);
		
		for (int i = 0; i < Ap.getChildren().size(); i++) {
			MovingText(Ap.getChildren().get(i));
		}
	}
	
	 public void handleCancelBtn(ActionEvent e) {
		try {
			StackPane root = (StackPane) CancelBtn.getScene().getRoot();
			root.getChildren().remove(AnchorPane);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void MovingText(Object obj) {		
		Text text = null;
		if(obj instanceof Text) {
			text = (Text) obj;
		} else {
			return;
		}
		
		KeyValue initKeyValues = new KeyValue(text.translateYProperty(), -200);
		KeyFrame initKeyFrame = new KeyFrame(Duration.ZERO, initKeyValues);
		
		KeyValue endKeyValues = new KeyValue(text.translateYProperty(), 650);
		KeyFrame endKeyFrame = new KeyFrame(Duration.seconds(4.0), endKeyValues);
		
		Timeline tl = new Timeline(initKeyFrame, endKeyFrame);
		
		tl.setCycleCount(Timeline.INDEFINITE);
		tl.play();
	}
}
