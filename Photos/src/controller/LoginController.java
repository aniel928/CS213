package controller;

import application.Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
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

	/** 
	 * Method called on first load of program. Retrieves serialized information from file and restores that data.
	 * Builds stock photo library if it doesn't exist on load.
	 * 
	 * @param mainStage
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked") //suppressing the warning for casting the object as a list.
	public void start(Stage mainStage) throws IOException, ClassNotFoundException {
		//load list of users from file.
		List<User> users = new ArrayList<>();
		
		//if file exists, set serialized data
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data" + File.separator + "users.dat"));
			users = (List<User>) ois.readObject();
			UserState.setAllUsers(users);
			ois.close();
		}catch(Exception e) {
			//do nothing;
		}
		
		//if stock doesn't exist, add it.  In case the serialized file gets deleted.
		if(UserState.getAllUsers().isEmpty()) {
			User stock = new User("stock");
			Album album = new Album("Animals");
			album.addPhoto(new Photo (new File("resources/stock/animals/angryCat.jpg")));
			album.addPhoto(new Photo (new File("resources/stock/animals/fox.jpg")));
			album.addPhoto(new Photo (new File("resources/stock/animals/lion.jpg")));
			album.addPhoto(new Photo (new File("resources/stock/animals/tigerPlays.jpg")));
			album.addPhoto(new Photo (new File("resources/stock/animals/zebras.jpg")));
			stock.addAlbum(album);

			album = new Album("Flowers");
			album.addPhoto(new Photo (new File("resources/stock/flowers/dahilia.jpg")));
			album.addPhoto(new Photo (new File("resources/stock/flowers/prettyFlower.jpg")));
			album.addPhoto(new Photo (new File("resources/stock/flowers/rainbowFlowers.jpg")));
			album.addPhoto(new Photo (new File("resources/stock/flowers/sunflower.jpg")));
			stock.addAlbum(album);
			
			album = new Album("Landscapes");
			album.addPhoto(new Photo (new File("resources/stock/landscapes/lake.jpg")));
			album.addPhoto(new Photo (new File("resources/stock/landscapes/mountain.jpg")));
			album.addPhoto(new Photo (new File("resources/stock/landscapes/pier.jpeg")));
			album.addPhoto(new Photo (new File("resources/stock/landscapes/waterfall.jpeg")));
			stock.addAlbum(album);
			
			UserState.getAllUsers().add(stock);
		}
		
	}
	
	/**
	 * Change screen to display either the Admin screen (User Maintenance), or user home screen (displaying photo albums).
	 * @param event
	 * @throws IOException
	 */
	public void login(ActionEvent event) throws IOException {
		//if user is admin, open admin screen
		if(username.getText().toLowerCase().equals("admin")) {
			//open admin screen
			Main.changeScene("/view/admin.fxml", "Administrator Dashboard");			
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
					Main.changeScene("/view/userhome.fxml", "User Home");
				}
			}
			invalidUserError.setVisible(true);
			username.setText("");
		}
	}

}