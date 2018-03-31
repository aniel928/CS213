package controller;

import java.io.IOException;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Photo;

public class PhotoController {//implements Initializable {
	@FXML Button loginButton;
	@FXML TextField username;
	@FXML Text invalidUserError;
	
	public void start(Stage mainStage) throws IOException {
		try {
		}catch(Exception e) {

		}
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

	public void logout(ActionEvent event) throws IOException {
		Main.changeScene("/view/login.fxml");
	}
}
