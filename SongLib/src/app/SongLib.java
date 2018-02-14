//Anne Whitman (alh220)
//Jason Muccino (jmuccino)

package app;

import java.io.FileNotFoundException;
import java.io.IOException;

import controller.SongController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;



public class SongLib extends Application {
	private Stage stage;
	
	public void start(Stage primaryStage) 
		throws IOException {
			stage = primaryStage;
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/song.fxml"));

			BorderPane root = (BorderPane)loader.load();
			
			SongController songController = loader.getController();
			songController.start(stage);

			Scene scene = new Scene(root, 885.0, 530.0);
			
			stage.setTitle("Song Playlist");
			
			stage.setScene(scene);
			
			//set listener for window closing
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent we) {
					try {
						songController.saveFile();
		        		} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		      });
			
			stage.show(); 
			
			       
		}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
