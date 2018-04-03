package application;
	
import java.io.IOException;

import controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;


public class Main extends Application {
	private static Stage stage;
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
			stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void changeScene(String sceneName) throws IOException {
		Parent root  = FXMLLoader.load(Main.class.getResource(sceneName));
		stage.setScene(new Scene(root));
		stage.centerOnScreen();
	}
	
	public static void newStage(String sceneName) throws IOException{
		Parent root = FXMLLoader.load(Main.class.getResource(sceneName));
		Stage newStage = new Stage();
		newStage.setScene(new Scene(root));
		newStage.centerOnScreen();
		newStage.show();
	}
	
	public static Stage getStage() {
		return stage;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
