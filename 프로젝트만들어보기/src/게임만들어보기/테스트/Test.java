package 게임만들어보기.테스트;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Test extends Application{
	@Override
	public void start(Stage primaryStage) throws Exception {
		VBox root = new VBox();
		root.setPrefSize(500, 500);
		root.setAlignment(Pos.CENTER);
		root.setSpacing(20);
		
		Image ig = new Image(getClass().getResource("효과.gif").toString());
		
		ImageView iv = new ImageView();
		iv.setImage(ig);
		
		
		
		root.getChildren().add(iv);
		
		Scene scene = new Scene(root);
		
		primaryStage.setTitle("AppMain");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
