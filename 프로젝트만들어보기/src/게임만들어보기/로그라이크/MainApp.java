package ���Ӹ�����.�α׶���ũ;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainApp extends Application {
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Font.loadFont(getClass().getResourceAsStream("/PFStardust.ttf"), 12);	// ��Ʈ
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainStage.fxml"));	// ���ν������� fxml �ε�
		Parent parent = loader.load();		// Parent ��ü
		MainController mc = new MainController();	
		mc.setPrimaryStage(primaryStage);	// MainController ��ü ���� �� �������� �ֱ�
		
		Scene scene = new Scene(parent);	// Parent�� ���� �ֱ�
		
		primaryStage.setTitle("RogLike");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);	// â ũ�� ����
		primaryStage.show();
	}
	
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
