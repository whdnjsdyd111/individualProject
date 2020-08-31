package 게임만들어보기.가위바위보;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class RootController implements Initializable {
	@FXML private ToggleGroup group;
	@FXML private RadioButton Rock;
	@FXML private RadioButton Sissor;
	@FXML private RadioButton Paper;
	@FXML private ImageView UserImage;
	@FXML private ImageView ComputerImage;
	@FXML private Text ComputerText;
	@FXML private Text centerText;
	@FXML private Text WinningRate;
	@FXML private Button startButton;
	@FXML private ProgressBar progressBar;
	
	int playNo = 0;
	int win = 0;
	int lose = 0;
	String userSelected = null;
	String computerSelected = null;
	String notice = null;
	String stRate = null;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {	// ToggleGroup의 selectedToggle 속성 감시 리스너 설정
			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				Image image = new Image(getClass().getResource("images/" + newValue.getUserData().toString() + ".jpg").toString());
				UserImage.setImage(image);
				userSelected = newValue.getUserData().toString();
			}
		});
		startButton.setOnAction( e -> handleStartBtn(e) );
		progressBar.setProgress(0.0);
	}
	
	public void handleStartBtn(ActionEvent e) {
		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				startButton.setDisable(true);
				Rock.setDisable(false);
				Sissor.setDisable(false);
				Paper.setDisable(false);
				
				int random = (int) (Math.random() * 3) + 1;
				if(random == 1) {
					computerSelected = "가위";
				} else if(random == 2) {
					computerSelected = "바위";
				} else if(random == 3) {
					computerSelected = "보";
				}
				
				Platform.runLater( () -> {
					centerText.setText("안 내면");
					ComputerText.setText("");
				});
				try { Thread.sleep(1000); } catch(Exception e) { e.printStackTrace(); }
				Platform.runLater( () -> centerText.setText("진다") );
				try { Thread.sleep(1000); } catch(Exception e) { e.printStackTrace(); }
				Platform.runLater( () -> centerText.setText("가위") );
				try { Thread.sleep(500); } catch(Exception e) { e.printStackTrace(); }
				Platform.runLater( () -> centerText.setText("바위") );
				try { Thread.sleep(500); } catch(Exception e) { e.printStackTrace(); }

				Platform.runLater( () -> {
					centerText.setText("보!");
					if(random == 1) {
						ComputerText.setText("가위");
					} else if(random == 2) {
						ComputerText.setText("바위");
					} else if(random == 3) {
						ComputerText.setText("보");
					}
					Image image = new Image(getClass().getResource("images/" + ComputerText.getText() + ".jpg").toString());
					ComputerImage.setImage(image);
				});
				
				startButton.setDisable(false);
				Rock.setDisable(true);
				Sissor.setDisable(true);
				Paper.setDisable(true);

				playNo++;
				
				if(userSelected.equals(computerSelected)) {
					notice = "비김";
				} else if( (userSelected.equals("가위") && computerSelected.equals("바위")) ||
							(userSelected.equals("바위") && computerSelected.equals("보")) ||
							(userSelected.equals("보") && computerSelected.equals("가위")) ||
							(userSelected == null))	{
					notice = "패배";
					lose++;
				} else if( (userSelected.equals("가위") && computerSelected.equals("보")) ||
						(userSelected.equals("바위") && computerSelected.equals("가위")) ||
						(userSelected.equals("보") && computerSelected.equals("바위")) )	{
					notice = "승리";
					win++;
				}
				double rate = (double) win / playNo;
				
				progressBar.setProgress(rate);
				
				stRate = String.valueOf((int)(rate * 100));
				
				Platform.runLater( () -> { 
					centerText.setText(notice); 
					WinningRate.setText("승률: " + String.valueOf(stRate) + "%");
				});
				
				return null;
				
			};
		};
		
		Thread thread = new Thread(task);
		thread.start();
	}
}
