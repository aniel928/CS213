package controller;

import application.Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.User;
import model.UserState;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class LoginController {
	@FXML private TextField username;
	@FXML private Text invalidUserError;
	
	@SuppressWarnings("unchecked")
	public void start(Stage mainStage) throws IOException, ClassNotFoundException {
		//load list of users from file.
		List<User> users = new ArrayList<>();
		users.add(new User("admin"));
		
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data" + File.separator + "users.dat"));
		users = (List<User>) ois.readObject();
		UserState.setAllUsers(users);
		ois.close();
	}
	
	/**
	 * Change scene to either admin screen or user home screen.
	 * @param event
	 * @throws IOException
	 */
	public void login(ActionEvent event) throws IOException {
		//if user is admin, open admin screen
		if(username.getText().toLowerCase().equals("admin")) {
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
				if(user.getUserName().toLowerCase().equals(username.getText().toLowerCase())) {
					UserState.setCurrentUser(user);
					Main.changeScene("/view/userhome.fxml");
				}
			}
			invalidUserError.setVisible(true);
			username.setText("");
		}
	}

}