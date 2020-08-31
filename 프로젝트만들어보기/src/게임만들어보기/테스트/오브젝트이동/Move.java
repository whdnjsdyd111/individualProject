package 게임만들어보기.테스트.오브젝트이동;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Move extends Application {
	int x = 0;
    int y = 100;
    
   @Override
   public void start(Stage primaryStage) 
   {           
    Rectangle rec1 = new Rectangle (40,40);
    rec1.setFill(Color.DARKGRAY);
    rec1.setTranslateX(200);
    
     
    Rectangle rec2 = new Rectangle (40,40);
    rec2.setFill(Color.DARKCYAN);

     
    Rectangle myHero = new Rectangle (50,50);
    myHero.setTranslateY(100);
     
    Button b1 = new Button ("->");
    b1.setTranslateX(300);
    b1.setTranslateY(280);
    b1.setOnAction ( e->{   
        x += 20;
        myHero.setTranslateX(x);     
    });
     
    Button b2 = new Button ("<-");
    b2.setTranslateX(260);
    b2.setTranslateY(280);
    b2.setOnAction ( e->{   
        x -= 20;
        myHero.setTranslateX(x);     
    });
     
    Button b3 = new Button ("Up");
    b3.setTranslateX(330);
    b3.setTranslateY(320);
    b3.setOnAction ( e->{   
        y -= 20;
        myHero.setTranslateY(y);     
        });
     
    Button b4 = new Button ("Down");
    b4.setTranslateX(330);
    b4.setTranslateY(360);
    b4.setOnAction ( e->{   
        y += 20;
        myHero.setTranslateY(y);     
        });
     
                
    Group multipleComponents = new Group();    
    multipleComponents.getChildren().addAll(rec1, rec2, myHero, b1, b2, b3, b4);
    multipleComponents.prefHeight(100); // WHAT IS purpose of this method? I can rec1.seWidth(0 and rec1.setHeight for nodes. But I cant to do this for Group !

      
    AnimationTimer animator = new AnimationTimer()
      {
           public void handle (long arg0) 
           {                                               
                Bounds obstacle = rec1.localToScene( rec1.getBoundsInLocal() );
                Bounds obstacle2 = rec2.localToScene( rec2.getBoundsInLocal() );   
                 
                Bounds myself = myHero.localToScene( myHero.getBoundsInLocal() );  
          
                   if ( myself.intersects(obstacle) )
                   {
                       /*of course I could create new Integer at 16 line, and keep plus'ing here
                        *But image if there is much more elements, working with arrayList can get confusing.
                        */
                       rec1.setTranslateX(20);     
                   }
                    
                   if ( myself.intersects(obstacle2) )
                   {
                       rec2.setTranslateX(20);     
                   }                   
                                                   
          }  
                    
       };  
    
    animator.start();      
      
    Scene scene = new Scene(multipleComponents, 600, 400);
    primaryStage.setScene(scene);
    primaryStage.show();
    

   }
        
   public static void main(String[] args) 
   {
       launch(args);
   }
}
