package ���Ӹ�����.�׽�Ʈ.��������Ʈ�׽�Ʈ.��Ʈ�ѷ�_��������;

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
import ���Ӹ�����.�׽�Ʈ.��������Ʈ�׽�Ʈ.������Ʈ.HP;

public class MainController implements Initializable {
	@FXML private Pane StartBtn;
	@FXML private Pane CreditBtn;
	@FXML private Pane OptionBtn;
	@FXML private Pane TutorialBtn;
	@FXML private Stage PrimaryStage;
	
	public void setPrimaryStage(Stage PrimaryStage) {		// MainApp���� �������� �ޱ�
		this.PrimaryStage = PrimaryStage;
		
	}
	
	@FXML
	List<Pane> ivList = new ArrayList<Pane>();	// Pane ��ü�� List�� ����
	
	double currentWidth = 0.0;		// ���� ���α��� ����
	double currentHeight = 0.0;		// ���� ���α��� ����
	double currentX = 0.0;			// ���� X ��ġ ����
	double currentY = 0.0;			// ���� Y ��ġ ����
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		
		ivList.add(StartBtn);
		ivList.add(CreditBtn);
		ivList.add(OptionBtn);
		ivList.add(TutorialBtn);		// List�� ��ư�� ����
		
		for (int i = 0; i < ivList.size(); i++) {

			ivList.get(i).setOnMouseEntered(e -> {		// �� ��ư�鿡 ���콺�� ��� ���� �� �̺�Ʈ
				
				Pane pane = (Pane) e.getTarget();		// �̺�Ʈ�� �Ͼ Ÿ�� (Pane)
				
				ImageView iv = (ImageView) pane.getChildren().get(0);	//	�ش� Pane�� �ڽ� ��ü�� ��ư �̹��� ����
				
				currentWidth = iv.getFitWidth();		// ���� ���� ����
				currentHeight = iv.getFitHeight();		
				currentX = iv.getX();					// ���� ��ġ ����
				currentY = iv.getY();
				
				iv.setFitWidth(currentWidth - currentWidth / 10.0);	
				iv.setFitHeight(currentHeight - currentHeight / 10.0);
				iv.setX(currentX + currentWidth / 20.0);
				iv.setY(currentY + currentHeight / 20.0);		// ������ ũ��, ��ġ ���̱�
			});
			
			ivList.get(i).setOnMouseExited(e -> {		// �� ��ư�鿡 ���콺�� ������ �� �̺�Ʈ
				
				Pane pane = (Pane) e.getTarget();
				
				ImageView iv = (ImageView) pane.getChildren().get(0);
				iv.setFitWidth(currentWidth);
				iv.setFitHeight(currentHeight);
				iv.setX(currentX);
				iv.setY(currentY);
			});
		}

		CreditBtn.setOnMouseClicked(e ->  handleCreditBtn(e) );		// ũ���� ȭ������ �Ѿ��
		TutorialBtn.setOnMouseClicked(e -> handleTutorialBtn(e) );	// Ʃ�丮�� ȭ������ �Ѿ��
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
