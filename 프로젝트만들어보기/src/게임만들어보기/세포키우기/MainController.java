package 게임만들어보기.세포키우기;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainController implements Initializable {
	@FXML private Pane StartBtn;
	@FXML private Pane CreditBtn;
	@FXML private Pane OptionBtn;
	@FXML private Pane TutorialBtn;
	
	@FXML private Stage PrimaryStage;
	
	public void setPrimaryStage(Stage PrimaryStage) {
		this.PrimaryStage = PrimaryStage;
	}
	
	@FXML
	List<Pane> ivList = new ArrayList<Pane>();
	
	double currentWidth = 0.0;
	double currentHeight = 0.0;
	double currentX = 0.0;
	double currentY = 0.0;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ivList.add(StartBtn);
		ivList.add(CreditBtn);
		ivList.add(OptionBtn);
		ivList.add(TutorialBtn);
		
		for (int i = 0; i < ivList.size(); i++) {
			ivList.get(i).setOnMouseEntered(e -> {
				
				Pane pane = (Pane) e.getTarget();
				
				ImageView iv = (ImageView) pane.getChildren().get(0);
				
				currentWidth = iv.getFitWidth();
				currentHeight = iv.getFitHeight();
				currentX = iv.getX();
				currentY = iv.getY();
				
				iv.setFitWidth(currentWidth - currentWidth / 10.0);
				iv.setFitHeight(currentHeight - currentHeight / 10.0);
				iv.setX(currentX + currentWidth / 20.0);
				iv.setY(currentY + currentHeight / 20.0);
			});
			
			ivList.get(i).setOnMouseExited(e -> {
				
				Pane pane = (Pane) e.getTarget();
				
				ImageView iv = (ImageView) pane.getChildren().get(0);
				iv.setFitWidth(currentWidth);
				iv.setFitHeight(currentHeight);
				iv.setX(currentX);
				iv.setY(currentY);
			});
		}
		
		CreditBtn.setOnMouseClicked(e ->  handleCreditBtn(e) );
		TutorialBtn.setOnMouseClicked(e -> handleTutorialBtn(e) );
	}
	
	public void handleCreditBtn(MouseEvent e) {
		try {
			Parent credit = FXMLLoader.load(getClass().getResource("CreditStage.fxml"));
			StackPane root = (StackPane) CreditBtn.getScene().getRoot();
			root.getChildren().add(credit);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void handleTutorialBtn(MouseEvent e) {
		try {
			Parent tutorial = FXMLLoader.load(getClass().getResource("TutorialStage.fxml"));
			StackPane root = (StackPane) TutorialBtn.getScene().getRoot();
			root.getChildren().add(tutorial);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
