package 게임만들어보기.테스트.다른게임;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AnotherGame extends Application {
	//size in pixels of the main game canvas
	   private final int SIZE_W = 800;
	   private final int SIZE_H = 600;
	   //one second in nanoseconds
	   private final long ONE_SECOND = 1000000000;
	   //used to store the current time to calculate fps
	   private long currentTime = 0;
	   //used to store the last time to calculate fps
	   private long lastTime = 0;
	   //fps counter
	   private int fps = 0;
	   //acumulated difference between current time and last time
	   private double delta = 0;
	   //text to display fps
	   private Text tFPS;
	   //the game loop timer
	   private AnimationTimer mainLoop;
	   //the shape to test the main loop
	   private Polygon star;
	   //direction x,y of the star shape 
	   private int dirx = 1;
	   private int diry = 1;
	   
	   
	   @Override
	   public void start(Stage stage) throws Exception {
	      
	      //main game canvas
	      Pane root = new Pane();
	      root.setPrefSize(SIZE_W, SIZE_H);
	      root.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
	      
	      //initialize all objects of the main loop
	      initNodes(root.getChildren());
	      //define the main loop
	      initMainLoop(root.getChildren());
	      //main scene
	      Scene scene = new Scene(root);
	      //the main window
	      stage.setScene(scene);
	      stage.setResizable(false);
	      stage.setOnCloseRequest(event -> {
	         mainLoop.stop();
	         System.exit(0);
	      });
	      stage.show();
	      //start the main loop
	      mainLoop.start();
	      
	      
	   }
	   
	   public void initNodes(ObservableList<Node> rootPane){
	      tFPS = new Text("FPS : ");
	      tFPS.setTranslateX(SIZE_W-180);
	      tFPS.setTranslateY(60);
	      tFPS.setFill(Color.WHITE);
	      tFPS.setFont(new Font(40));
	      rootPane.add(tFPS);
	      initAnimation(rootPane);
	   }
	   
	   public void initAnimation(ObservableList<Node> rootPane){
	      star = new Polygon();
	      Double[] starCoord = new Double[]{ 
	            35.0, 120.5, 37.9, 129.1, 46.9, 129.1, 39.7, 134.5, 42.3,
	            143.1, 35.0 , 139.0, 27.7, 143.1, 30.3, 134.5, 23.1, 129.1,
	            32.1,129.1};
	      star.getPoints().addAll(starCoord);
	      star.setTranslateX(100);
	      star.setTranslateY(100);
	      star.setFill(Color.RED);
	      rootPane.add(star);

	   }
	   
	   public void initMainLoop(ObservableList<Node> rootPane){
	      lastTime = System.nanoTime();
	      mainLoop = new AnimationTimer() {
	         
	         @Override
	         public void handle(long now) {
	            currentTime = now;
	            fps++;
	            delta += currentTime-lastTime;
	            
	            updateAnimation(rootPane);
	            
	            if(delta > ONE_SECOND){
	               tFPS.setText("FPS : "+fps);
	               delta -= ONE_SECOND;
	               fps = 0;
	            }
	            
	            lastTime = currentTime;
	         }
	      };
	   }
	   
	   public void updateAnimation(ObservableList<Node> rootPane){
	      Bounds starbounds = star.getBoundsInParent();
	      
	      double posx = star.getTranslateX();
	      double posy = star.getTranslateY();

	      if((starbounds.getMaxX() >= SIZE_W) || (starbounds.getMinX() <= 0.0)){
	         dirx = -dirx;
	         star.setScaleX(1);
	         star.setScaleY(1);
	      }
	      if((starbounds.getMaxY() >= SIZE_H) || (starbounds.getMinY() <= 0.0)){
	         diry = -diry;
	         star.setScaleX(1);
	         star.setScaleY(1);
	      }
	      star.setTranslateX(posx+(3*dirx));
	      star.setTranslateY(posy+(3*diry));
	      star.setScaleX(star.getScaleX()+0.1);
	      star.setScaleY(star.getScaleY()+0.1);

	   }
	   
	   public static void main(String[] args){
	      launch(args);
	   }
}
