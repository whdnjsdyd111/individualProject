package 게임만들어보기.테스트.현프로젝트테스트.컨트롤러_스테이지;

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
import 게임만들어보기.테스트.현프로젝트테스트.오브젝트.HP;

public class MainController implements Initializable {
	@FXML private Pane StartBtn;
	@FXML private Pane CreditBtn;
	@FXML private Pane OptionBtn;
	@FXML private Pane TutorialBtn;
	@FXML private Stage PrimaryStage;
	
	public void setPrimaryStage(Stage PrimaryStage) {		// MainApp에게 스테이지 받기
		this.PrimaryStage = PrimaryStage;
		
	}
	
	@FXML
	List<Pane> ivList = new ArrayList<Pane>();	// Pane 객체들 List에 저장
	
	double currentWidth = 0.0;		// 현재 가로길이 저장
	double currentHeight = 0.0;		// 현재 세로길이 저장
	double currentX = 0.0;			// 현재 X 위치 저장
	double currentY = 0.0;			// 현재 Y 위치 저장
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		
		ivList.add(StartBtn);
		ivList.add(CreditBtn);
		ivList.add(OptionBtn);
		ivList.add(TutorialBtn);		// List에 버튼들 저장
		
		for (int i = 0; i < ivList.size(); i++) {

			ivList.get(i).setOnMouseEntered(e -> {		// 각 버튼들에 마우스가 들어 갔을 때 이벤트
				
				Pane pane = (Pane) e.getTarget();		// 이벤트가 일어난 타겟 (Pane)
				
				ImageView iv = (ImageView) pane.getChildren().get(0);	//	해당 Pane의 자식 객체인 버튼 이미지 저장
				
				currentWidth = iv.getFitWidth();		// 현재 길이 저장
				currentHeight = iv.getFitHeight();		
				currentX = iv.getX();					// 현재 위치 저장
				currentY = iv.getY();
				
				iv.setFitWidth(currentWidth - currentWidth / 10.0);	
				iv.setFitHeight(currentHeight - currentHeight / 10.0);
				iv.setX(currentX + currentWidth / 20.0);
				iv.setY(currentY + currentHeight / 20.0);		// 적당히 크기, 위치 줄이기
			});
			
			ivList.get(i).setOnMouseExited(e -> {		// 각 버튼들에 마우스가 나갔을 때 이벤트
				
				Pane pane = (Pane) e.getTarget();
				
				ImageView iv = (ImageView) pane.getChildren().get(0);
				iv.setFitWidth(currentWidth);
				iv.setFitHeight(currentHeight);
				iv.setX(currentX);
				iv.setY(currentY);
			});
		}

		CreditBtn.setOnMouseClicked(e ->  handleCreditBtn(e) );		// 크레딧 화면으로 넘어가기
		TutorialBtn.setOnMouseClicked(e -> handleTutorialBtn(e) );	// 튜토리얼 화면으로 넘어가기
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
			ControlAPs.setInit();
			Parent tutorial = FXMLLoader.load(getClass().getResource("TutorialStage.fxml"));
			StackPane root = (StackPane) TutorialBtn.getScene().getRoot();
			root.getChildren().add(tutorial);
			HP.setInit();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
