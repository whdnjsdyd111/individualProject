package 게임만들어보기.로그라이크;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainApp extends Application {
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Font.loadFont(getClass().getResourceAsStream("/PFStardust.ttf"), 12);	// 폰트
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainStage.fxml"));	// 메인스테이지 fxml 로드
		Parent parent = loader.load();		// Parent 객체
		MainController mc = new MainController();	
		mc.setPrimaryStage(primaryStage);	// MainController 객체 생성 및 스테이지 넣기
		
		Scene scene = new Scene(parent);	// Parent를 씬에 넣기
		
		primaryStage.setTitle("RogLike");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);	// 창 크기 고정
		primaryStage.show();
	}
	
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
