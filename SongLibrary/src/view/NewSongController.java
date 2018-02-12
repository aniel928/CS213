package view;


import java.io.IOException;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
//import javafx.scene.control.TextInputDialog;
//import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class NewSongController implements Controller{
	@FXML private ListView<String> listView;  
	
	@FXML private Text songText;
	@FXML private Button addButton;
	@FXML private Button mainButton;
	@FXML private Button saveButton;
	@FXML private TextField songName;
	@FXML private TextField artistName;
	@FXML private TextField albumName;
	@FXML private TextField albumYear;
	
	
	@FXML
	
	public void mainScreen(ActionEvent event) throws IOException{
		//listView.setItems(obsList);
		
		Stage stage=(Stage) mainButton.getScene().getWindow();
	    
	    //load up OTHER FXML document
	    Parent root = FXMLLoader.load(getClass().getResource("Song.fxml"));
	    
	    //create a new scene with root and set the stage
	    Scene scene = new Scene(root);
	    stage.setScene(scene);
	    //stage.show();
	    
	}
	
	//create new song and add to list
	public void create(ActionEvent event) throws IOException {
		//get details from screen
		String song = songName.getText();
		String artist = artistName.getText();
		String album = albumName.getText();
		String year = albumYear.getText();
		
		System.out.println(song + " " + artist + " " + album + " " + year);
		
		//add to obsList
		obsList.add(song);
		
		Stage stage=(Stage) saveButton.getScene().getWindow();
	    
	    //load up OTHER FXML document
	    Parent root = FXMLLoader.load(getClass().getResource("song.fxml"));
	    
	    //create a new scene with root and set the stage
	    Scene scene = new Scene(root);
	    stage.setScene(scene);
	   // start(stage);
	}
	
	}
