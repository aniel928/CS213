package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Photo;

public class LoginController {
	@FXML Button loginButton;
	@FXML TextField username;
	
	public void start(Stage mainStage) throws IOException {
		try {
		}catch(Exception e) {

		}
	}
	
	public void login(ActionEvent event) throws IOException {
		if(username.getText().equals("admin")) {
			//open admin screen
			Main.changeScene("view/admin.fxml");
		}
		else {
			//open user screen
			Main.changeScene("view/userhome.fxml");
		}
	}
}
