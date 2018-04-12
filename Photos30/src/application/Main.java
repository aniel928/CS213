package application;
	
import java.io.IOException;
import java.util.Timer;

import controller.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.UserState;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

/**
 * Main application code that starts up the first screen and has code to tie everything up upon close of program. 
 *  
 * @author alh220
 * @author jmuccino
 *
 */
public class Main extends Application {
	/**
	 * main stage being used throughout the program
	 */
	private static Stage stage;
	
	/**
	 * Code that initially runs on start up of program.  Sets up stage, displays first screen (login screen), sets a listener
	 * to run particular code on close.
	 * 
	 * @param primaryStage Stage object where scenes are displayed.
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			stage = primaryStage;
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/login.fxml"));
			
			AnchorPane root = (AnchorPane)loader.load();
			
			LoginController loginController = loader.getController();
			loginController.start(stage);
			
			Scene scene = new Scene(root);
			
			stage.setTitle("Log In");
			stage.setScene(scene);
			stage.centerOnScreen();
			
			
			//set listener for window closing
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent we) {
					try {
						if(UserState.timer != null) {
							UserState.timer.cancel();
							UserState.timer.purge();
						}
						UserState.saveFile();
						System.exit(0);
						
		        	} catch (IOException e) {
						// TODO Auto-generated catch block
		        		System.err.println(e.getMessage());
						e.printStackTrace();
					}
				}
		      });
			
			
			stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Changes the scene within the stage.  Used for displaying new screens within same stage.
	 * @param sceneName name of FXML file to load
	 * @param title string to display in title bar
	 * @throws IOException exception thrown if loading class fails
	 */
	public static void changeScene(String sceneName, String title) throws IOException {
		Parent root  = FXMLLoader.load(Main.class.getResource(sceneName));
		stage.setTitle(title);
		stage.setScene(new Scene(root));
		stage.centerOnScreen();
	}
	
	/**
	 * Opens a second stage, displays given scene, and sets a listener to handle closing of stage.
	 * 
	 * @param sceneName String representing scene name
	 * @param title String representing title of display stage.
	 * @throws IOException exception thrown if loading class fails
	 */
	public static void newStage(String sceneName, String title) throws IOException{
		Parent root = FXMLLoader.load(Main.class.getResource(sceneName));
		Stage newStage = new Stage();
		//set listener for window closing
		if(sceneName.equals("/view/slideshow.fxml")) {
			UserState.timer = new Timer();
			newStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent we) {
					if(UserState.timer != null) {
						UserState.timer.cancel();
						UserState.timer.purge();
					}
				}
			});
		}
		newStage.setScene(new Scene(root));
		newStage.centerOnScreen();
		newStage.show();
		
	}
	
	/**
	 * Getter for the primary stage.
	 * @return the main stage being used by the application
	 */
	public static Stage getStage() {
		return stage;
	}
	
	/**
	 * Main method to start up the application.
	 * @param args arguments passed in.
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
