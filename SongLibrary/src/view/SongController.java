package view;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class SongController {
	@FXML         
	ListView<String> listView;                

	private ObservableList<String> obsList;              

	public void start(Stage mainStage) {                
		// create an ObservableList 
		// from an ArrayList              
		obsList = FXCollections.observableArrayList(                               
				"Test Song");               
		listView.setItems(obsList);  
	}

	//create new song and add to list
	public void create() {
		//get details from screen
		//add to obsList
	}
	
	//get data from list and display
	public void read() {
		//get song from screen
		//read data from obsList and display
	}
	
	//edit song details and save
	public void update() {
		//get details from screen
		//edit obsList
	}
	
	//remove song from list
	public void delete() {
		//get song from screen
		//remove from obsList
	}
}
