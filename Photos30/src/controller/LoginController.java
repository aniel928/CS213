package controller;

import application.Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
	@FXML private TextField username;
	@FXML private Text invalidUserError;
	
	public void start(Stage mainStage) throws IOException {
		
	}
	
	/**
	 * Change scene to either admin screen or user home screen.
	 * @param event
	 * @throws IOException
	 */
	public void login(ActionEvent event) throws IOException {
		if(username.getText().equals("admin")) {
			//open admin screen
			Main.changeScene("/view/admin.fxml");			
		}
		else if(username.getText().isEmpty()){
			invalidUserError.setVisible(true);
		}else {
			//open user screen
			Main.changeScene("/view/userhome.fxml");
		}
	}

}