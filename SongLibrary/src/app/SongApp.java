package app;

import java.io.IOException;

import javafx.application.Application;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import view.SongController;
import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.StackPane;


public class SongApp extends Application {
	
	
	@Override
//	public void start(Stage primaryStage) {
//		try {
//			BorderPane root = new BorderPane();
//			Scene scene = new Scene(root,1000,500);
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//			primaryStage.setTitle("Song Library");
//			primaryStage.setScene(scene);
//			primaryStage.show();
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	public void start(Stage primaryStage) 
		throws IOException {
			FXMLLoader loader = new FXMLLoader();   
			loader.setLocation(getClass().getResource("/view/song.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			
			SongController songController = loader.getController();
			songController.start(primaryStage);

			Scene scene = new Scene(root, 500, 500);
			
			primaryStage.setTitle("Song Playlist");
			
			primaryStage.setScene(scene);
			primaryStage.show(); 
		}
	
	public static void main(String[] args) {
		launch(args);
	}
}
