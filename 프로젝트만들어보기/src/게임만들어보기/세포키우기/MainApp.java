package 게임만들어보기.세포키우기;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainApp extends Application {
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Font.loadFont(getClass().getResourceAsStream("/PFStardust.ttf"), 12);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainStage.fxml"));
		Parent parent = loader.load();
		MainController mc = new MainController();
		mc.setPrimaryStage(primaryStage);
		Scene scene = new Scene(parent);
		
		primaryStage.setTitle("RogLike");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
