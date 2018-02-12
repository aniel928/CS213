//Anne Whitman (alh220)
//Jason Muccino (jmuccino)

package view;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SongController {
	
	private ObservableList<String> obsList = FXCollections.observableArrayList();
	
	private List<List<String>> allSongs = new ArrayList<List<String>>();
	
	@FXML private ListView<String> listView;
	
	@FXML private Button cancelButton;
	@FXML private Button saveAddButton;
	@FXML private Button saveEditButton;
	@FXML private Button editButton;
	@FXML private Button deleteButton;
	@FXML private Text songText;
	@FXML private Text artistText;
	@FXML private Text albumText;
	@FXML private Text yearText;
	@FXML private Text errorText;
	@FXML private Text songName;
	@FXML private Text artistName;
	@FXML private Text albumName;
	@FXML private Text yearName;
	@FXML private TextField newSong;
	@FXML private TextField newArtist;
	@FXML private TextField newAlbum;
	@FXML private TextField newYear;
	
	//hide all add/edit fields
	private void hide(String type){
		newSong.setVisible(false);
		newArtist.setVisible(false);
		newAlbum.setVisible(false);
		newYear.setVisible(false);
		cancelButton.setVisible(false);
		errorText.setText("");
		songName.setVisible(true);
		artistName.setVisible(true);
		albumName.setVisible(true);
		yearName.setVisible(true);
		
		if(type.equals("add") || type.equals("both")){
			saveAddButton.setVisible(false);
		}
		
		if(type.equals("edit") || type.equals("both")){
			saveEditButton.setVisible(false);
		}
		
		newSong.clear();
		newArtist.clear();
		newAlbum.clear();
		newYear.clear();
		
		if(obsList.size() == 0) {
			errorText.setText("There are no songs yet.  Add a song now to get started!");
			errorText.setVisible(true);
			songName.setVisible(false);
			artistName.setVisible(false);
			albumName.setVisible(false);
			yearName.setVisible(false);
		}
	}
	
	//show all add/edit fields
	private void show(String type){
		newSong.setVisible(true);
		newArtist.setVisible(true);
		newAlbum.setVisible(true);
		newYear.setVisible(true);
		cancelButton.setVisible(true);
		deleteButton.setVisible(false);
		editButton.setVisible(false);
		if(type.equals("add") || type.equals("both")){
			saveAddButton.setVisible(true);
		}
		if(type.equals("edit") || type.equals("both")){
			saveEditButton.setVisible(true);
		}
	}
	
	//when song is clicked, display details
	@FXML protected void songDetails() {
    		int index = listView.getSelectionModel().getSelectedIndex();
    		System.out.println(index);
    		if(index == -1) {
    			editButton.setVisible(false);
    			deleteButton.setVisible(false);
    		}
    		else {
    			songText.setText(allSongs.get(index).get(0));
    			artistText.setText(allSongs.get(index).get(1));
    			if(allSongs.get(index).get(2).equals("")) {
    				albumText.setText("(empty)");
    			}else {
    				albumText.setText(allSongs.get(index).get(2));
    			}
    			if(allSongs.get(index).get(3).equals("")) {
    				yearText.setText("(empty)");
    			}else {
    				yearText.setText(allSongs.get(index).get(3));
    			}
    		
    			hide("both");
    			editButton.setVisible(true);
    			deleteButton.setVisible(true);
    		}

    }
	
	//show add song fields/buttons
	@FXML
	private void createScreen(ActionEvent event) throws IOException{
		show("add");
		
	}
	
	//show all edit fields and prefill text boxes.
	@FXML 
	private void editScreen(ActionEvent event) throws IOException{
		show("edit");
		
		int index = listView.getSelectionModel().getSelectedIndex();
		
		newSong.setText(allSongs.get(index).get(0));
		newArtist.setText(allSongs.get(index).get(1));
		newAlbum.setText(allSongs.get(index).get(2));
		newYear.setText(allSongs.get(index).get(3));
	}
	
	//first method to run, hide all add/edit fields and populate listView, read from file.
	public void start(Stage mainStage) throws IOException {
		try {
			BufferedReader br = new BufferedReader(new FileReader("songLib.txt"));
			String line = br.readLine();
			
			while(line != null) {
				ArrayList<String> song = new ArrayList<String>();
				for(int j = 0; j<4;j++) {
					song.add(line);
					line = br.readLine();
				}
				allSongs.add(song);
			}
			br.close();
			for(List<String> song : allSongs) {
				obsList.add(song.get(0) + " - " + song.get(1));
			}
		}catch(Exception e) {

		}
		hide("both");
		editButton.setVisible(false);
		deleteButton.setVisible(false);
		listView.setItems(obsList);  
		listView.getSelectionModel().select(0);
		songDetails();
		
		listView
		.getSelectionModel()
		.selectedIndexProperty()
		.addListener(
				(obs, oldVal, newVal) -> 
				songDetails());
		
	}


	//create new song and add to list
	@FXML 
	private void add(ActionEvent event) throws IOException {
		
		if(newSong.getText().isEmpty()) {
			errorText.setText("Song name is required.");
			errorText.setVisible(true);
		} else if(newArtist.getText().isEmpty()) {
			errorText.setText("Artist name is required.");
			errorText.setVisible(true);
		}
		else {
			ArrayList<String> song = new ArrayList<String>();
			
			//get details from screen
			song.add(newSong.getText());
			song.add(newArtist.getText());
			song.add(newAlbum.getText());
			song.add(newYear.getText());
			
			//add to obsList
			int index = getIndex(newSong.getText(), newArtist.getText());
			
			if(index != -1) {
				allSongs.add(index, song);
				obsList.add(index, newSong.getText() + " - " + newArtist.getText());
				hide("add");
				listView.getSelectionModel().select(index);
				songDetails();
			}
		}
	}
	
	//find where to insert
	public int getIndex(String song, String artist) {
		int index = 0;
		while(index < obsList.size()) {
			int compare = (obsList.get(index).toLowerCase()).compareTo((song + " - " + artist).toLowerCase()); 
			System.out.println(compare);
			if(compare == 0){
				errorText.setText("This song already exists");
				errorText.setVisible(true);
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
	
	//save edited changes.
	@FXML 
	private void edit(ActionEvent event) throws IOException {
		int index = listView.getSelectionModel().getSelectedIndex();
		if(newSong.getText().isEmpty()) {
			errorText.setText("Song name is required.");
		} else if(newArtist.getText().isEmpty()) {
			errorText.setText("Artist name is required.");
		}
		else {
			ArrayList<String> song = new ArrayList<String>();
			
			String songName = newSong.getText();
			String artistName = newArtist.getText();
			String albumName = newAlbum.getText();
			String yearName = newYear.getText();
			
			//get details from screen
			song.add(songName);
			song.add(artistName);
			song.add(albumName);
			song.add(yearName);
			
			//add to obsList
			ArrayList<String> oldSong = (ArrayList<String>) allSongs.get(index);
			allSongs.remove(index);
			obsList.remove(index);
			
			int newIndex = getIndex(songName, artistName);
			
			if(newIndex != -1) {
				allSongs.add(newIndex, song);
				obsList.add(newIndex, songName + " - " + artistName);
				listView.getSelectionModel().select(newIndex);
				hide("edit");
				songDetails();
			}else {
				allSongs.add(index, oldSong);
				obsList.add(index, oldSong.get(0) + " - " + oldSong.get(1));
				listView.getSelectionModel().select(index);
				errorText.setText("That song already exists");
				errorText.setVisible(true);
				show("edit");
				newSong.setText(allSongs.get(index).get(0));
				newArtist.setText(allSongs.get(index).get(1));
				newAlbum.setText(allSongs.get(index).get(2));
				newYear.setText(allSongs.get(index).get(3));
			}
						
		}
	}
	
	//hide the fields/buttons and go back to songDetails
	@FXML 
	private void cancel(ActionEvent event) throws IOException {
		hide("both");
		songDetails();
	}
	
	//remove from lists
	@FXML 
	private void delete(ActionEvent event) throws IOException{
		Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
		alert.showAndWait();

		if (alert.getResult() == ButtonType.YES) {
		    //do stuff
		
			int index = listView.getSelectionModel().getSelectedIndex();
			allSongs.remove(index);
			obsList.remove(index);
	
			if(obsList.size() == 0) {
				songText.setText("");
				artistText.setText("");
				albumText.setText("");
				yearText.setText("");
				errorText.setText("There are no songs yet.  Add a song now to get started!");
				errorText.setVisible(true);
				songName.setVisible(false);
				artistName.setVisible(false);
				albumName.setVisible(false);
				yearName.setVisible(false);
				return;
			}
			else if(obsList.size() == index) {
				System.out.println("last item " + index + ", size: " + (obsList.size()-1));
				index--;
			}

			listView.getSelectionModel().select(index);
			songDetails();
			
		}
	}

	//save list out to file
	public void saveFile() throws FileNotFoundException {
		
		if(obsList.size() > 0) {
			PrintWriter out = new PrintWriter(new FileOutputStream("songlib.txt"));
			
			for(List<String> song : allSongs) {
				for(String detail : song) {
					System.out.println(detail);
					out.println(detail);
				}	
			}
			out.flush();
			out.close();
		}
		else {
			PrintWriter out = new PrintWriter(new FileOutputStream("songlib.txt"));
			out.close();
		}
		System.exit(0);
	}
}
