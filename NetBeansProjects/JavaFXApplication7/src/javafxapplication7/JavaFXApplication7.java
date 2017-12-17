/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication7;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.time.Duration;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author PowerUser
 */
public class JavaFXApplication7 extends Application {
     Button b1 = new Button("pLay");
      Button b2 = new Button("pAuse");
      Button b3 = new Button("oPen");
        VBox root = new VBox(10);
       Slider bar = new Slider();
        HBox root2 = new HBox(30);
        MediaPlayer player;
        MediaView view;
        Media media;
    @Override
    public void start(Stage primaryStage) {
      
       // Media media = new Media("file:///C:/Users/PowerUser/Videos/halloffame.mp4");
      b3.setOnAction(e-> {
      FileChooser chooser = new FileChooser();
      File file = chooser.showOpenDialog(primaryStage);
      if(file!= null){
          String str = file.toURI().toString();
      myPlayer(str);
      
      }
      });
      
      
      
       view = new MediaView(player);
       //you need to create new view only once, rest you only have to set view
     root2.getChildren().addAll(b3,b1,b2);
     root.getChildren().add(root2);
       root.getChildren().add(view);
    root.getChildren().add(bar);
          
        Scene scene = new Scene(root, 840, 480);
        
        Image icon = new Image(getClass().getResourceAsStream("picon.png"));
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("pVideo");
        primaryStage.setScene(scene);
        primaryStage.show();
        
       /* player.setOnPlaying(new Runnable() {

        @Override
          public void run() {
           javafx.util.Duration curtime = player.getCurrentTime();
      bar.setValue(curtime.toSeconds()); 
          }
      });*/
       /* bar.valueChangingProperty().addListener(new InvalidationListener() {
            Duration duration;
          @Override
          public void invalidated(Observable observable) {
             player.seek(duration.multipliedBy((long) bar.getValue())); 
          }
      });*/
        
    }  
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void myPlayer(String str) {
        if(str == null){
        return;
        }
        if(player!= null){
          player.stop();
          player.dispose();
            player = null;
        }
        media = new Media(str);
        myPlay(media);
    }

    private void myPlay(Media media) {
        
        
     player = new MediaPlayer(media);
        view.setMediaPlayer(player);
        
        mySlider();
        player.setAutoPlay(true);
        DoubleProperty height = view.fitHeightProperty();
        DoubleProperty width = view.fitWidthProperty();
        width.bind(Bindings.selectDouble(view.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(view.sceneProperty(), "height"));
        b1.setOnAction(e -> player.play());
       b2.setOnAction(e -> player.pause());
    }

    private void mySlider() {
             bar.setMin(0.0);
        bar.setValue(0.0);
       bar.setMax(player.getTotalDuration().toSeconds());
        /* player.currentTimeProperty().addListener(new ChangeListener<javafx.util.Duration> () {

                 @Override
                 public void changed(ObservableValue<? extends javafx.util.Duration> observable, javafx.util.Duration oldValue, javafx.util.Duration newValue) {
                  
                     bar.setValue(newValue.toSeconds());
                 }
             });*/
       player.currentTimeProperty().addListener(new InvalidationListener() {

                 @Override
                 public void invalidated(Observable observable) {
                     double str= player.getCurrentTime().toSeconds();
                     bar.setValue(str);
                 }
             });
    }

    
    
    
    
}
