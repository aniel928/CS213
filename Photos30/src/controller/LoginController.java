package controller;

import application.Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.User;
import model.UserState;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginController {
	@FXML private TextField username;
	@FXML private Text invalidUserError;
	
	public void start(Stage mainStage) throws IOException {
		//load list of users from file.
		List<User> users = new ArrayList<>();
		users.add(new User("admin"));
		users.add(new User("stock"));
		UserState.setAllUsers(users);
	}
	
	/**
	 * Change scene to either admin screen or user home screen.
	 * @param event
	 * @throws IOException
	 */
	public void login(ActionEvent event) throws IOException {
		//if user is admin, open admin screen
		if(username.getText().equals("admin")) {
			//open admin screen
			Main.changeScene("/view/admin.fxml");			
		}
		//if user is blank, throw error
		else if(username.getText().isEmpty()){
			invalidUserError.setVisible(true);
		}
		//otherwise make sure user exists and if they do set current user and change screen, if not throw error.
		else {
			for(User user : UserState.getAllUsers()) {
				if(user.getUserName().equals(username.getText())) {
					UserState.setCurrentUser(user);
					Main.changeScene("/view/userhome.fxml");
				}
			}
			invalidUserError.setVisible(true);
			username.setText("");
		}
	}

}