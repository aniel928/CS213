package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.User;
import model.UserState;

public class AdminController implements Initializable{
	private static ObservableList<String> obsList = FXCollections.observableArrayList();;
	@FXML private ListView<String> userListView;
	@FXML private Button newUserSaveButton;
	@FXML private TextField username;
	@FXML private Text invalidUserError;
	@FXML private Text userExistsError;
	@FXML private Text userNameLabel;
	@FXML private TextField newUsername;
	
	private List<User> userList;
	
	/**
	 * Change scene to either admin screen or user home screen.
	 * @param event
	 * @throws IOException
	 */

	public void logout(ActionEvent event) throws IOException {
		obsList.clear();
		Main.changeScene("/view/login.fxml");
	}
	
	public int findUserIndex(String uname) {
		int index = 0;
		while(index < obsList.size()) {
			int compare = (obsList.get(index).toLowerCase()).compareTo((uname).toLowerCase()); 
			if(compare == 0){
				userExistsError.setVisible(true);
				index = -1;
				break;
			}
			if(compare > 0) {
				break;
			}
			index++;
		}
		return index;
	}
	
	@FXML
	public void createUser() {
		String uName = newUsername.getText().toLowerCase();
		System.out.println("uName");
		User user = new User(uName);
		int index = findUserIndex(uName);
		if(index != -1) {
			System.out.println(index);
			userList.add(index, user);
			obsList.add(index, uName);
			userListView.setItems(obsList);  
			userListView.getSelectionModel().select(index);
			newUsername.setVisible(false);
			newUsername.setText("");
			userNameLabel.setVisible(false);
			newUserSaveButton.setVisible(false);
		}
	}
	
	public void deleteUser() {
		System.out.println("here");
		int index = userListView.getSelectionModel().getSelectedIndex();
		String userName = userList.get(index).getUserName();
		if(userName.equals("admin")) {
			Alert alert = new Alert(AlertType.ERROR, "Sorry, you cannot delete \"admin\" user!");
			alert.showAndWait();
		} else {
			Alert alert = new Alert(AlertType.WARNING, "You are about to delete "+userName + ".  This action cannot be undone.  Would you like to continue?", ButtonType.YES, ButtonType.CANCEL);
			alert.showAndWait();
			
			if (alert.getResult() == ButtonType.YES) {
			    //do stuff
				userList.remove(index);
				obsList.remove(index);
				if(obsList.size() != 0) {
					if(index != 0) {
						index--;
					}
					userListView.getSelectionModel().select(index);
				}		
			
				for(User user : userList) {
					if(user.getUserName().equals(userName)) {
						userList.remove(user);
						break;
					}
				}
			}
		}
	}
	
	@FXML
	public void showAdminFields(){
		newUsername.setVisible(true);
		newUsername.requestFocus();
		userNameLabel.setVisible(true);
		newUserSaveButton.setVisible(true);
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		userList = UserState.getAllUsers();
		// TODO Auto-generated method stub
		if(userList.size() == 0) {
			userList.add(new User("admin"));
			userList.add(new User("stock"));
		}
		System.out.println(userList.size());
		for(User user : userList) {
			obsList.add(user.getUserName());
		}
		userListView.setItems(obsList);
		
	}
	
	
}