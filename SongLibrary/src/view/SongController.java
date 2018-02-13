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
import model.Song;

public class SongController {
	
	private ObservableList<String> obsList = FXCollections.observableArrayList();
	
	private List<Song> allSongs = new ArrayList<Song>();
//	private List<List<String>> allSongs = new ArrayList<List<String>>();
	
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
    		if(index == -1) {
    			editButton.setVisible(false);
    			deleteButton.setVisible(false);
    		}
    		else {
    			songText.setText(allSongs.get(index).getTitle());
    			artistText.setText(allSongs.get(index).getArtist());
    			if(allSongs.get(index).getAlbum().equals("")) {
    				albumText.setText("(empty)");
    			}else {
    				albumText.setText(allSongs.get(index).getAlbum());
    			}
    			if(allSongs.get(index).getYear().equals("")) {
    				yearText.setText("(empty)");
    			}else {
    				yearText.setText(allSongs.get(index).getYear());
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
		
		newSong.setText(allSongs.get(index).getTitle());
		newArtist.setText(allSongs.get(index).getArtist());
		newAlbum.setText(allSongs.get(index).getAlbum());
		newYear.setText(allSongs.get(index).getYear());
	}
	
	//first method to run, hide all add/edit fields and populate listView, read from file.
	public void start(Stage mainStage) throws IOException {
		try {
			BufferedReader br = new BufferedReader(new FileReader("songLib.txt"));
			String line = br.readLine();
			
			while(line != null) {
				String title = line;
				line = br.readLine();
				String artist = line;
				line = br.readLine();
				String album = line;
				line = br.readLine();
				String year = line;
				line = br.readLine();

				Song song = new Song(title, artist, album, year);
				
				allSongs.add(song);
			}
			br.close();
			for(Song song : allSongs) {
				obsList.add(song.toString());
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
			Song song = new Song(newSong.getText(), newArtist.getText(), newAlbum.getText(), newYear.getText());
						
			//add to obsList
			int index = getIndex(song.toString());
			
			if(index != -1) {
				allSongs.add(index, song);
				obsList.add(index, song.toString());
				hide("add");
				listView.getSelectionModel().select(index);
				songDetails();
			}
		}
	}
	
	//find where to insert
	public int getIndex(String songString) {
		int index = 0;
		while(index < obsList.size()) {
			int compare = (obsList.get(index).toLowerCase()).compareTo((songString).toLowerCase()); 
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
			Song song = new Song(newSong.getText(), newArtist.getText(), newAlbum.getText(), newYear.getText());
						
			//add to obsList
			Song oldSong = allSongs.get(index);
			allSongs.remove(index);
			obsList.remove(index);
			
			int newIndex = getIndex(song.toString());
			
			if(newIndex != -1) {
				allSongs.add(newIndex, song);
				obsList.add(newIndex, song.toString());
				listView.getSelectionModel().select(newIndex);
				hide("edit");
				songDetails();
			}else {
				allSongs.add(index, oldSong);
				obsList.add(index, oldSong.toString());
				listView.getSelectionModel().select(index);
				errorText.setText("That song already exists");
				errorText.setVisible(true);
				show("edit");
				newSong.setText(allSongs.get(index).getTitle());
				newArtist.setText(allSongs.get(index).getArtist());
				newAlbum.setText(allSongs.get(index).getAlbum());
				newYear.setText(allSongs.get(index).getYear());
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
		int index = listView.getSelectionModel().getSelectedIndex();
		String song = allSongs.get(index).getTitle();
		String artist = allSongs.get(index).getArtist();
		Alert alert = new Alert(AlertType.WARNING, "You are about to delete "+ song +" by " + artist + ".  This action cannot be undone.  Would you like to continue?", ButtonType.YES, ButtonType.CANCEL);
		alert.showAndWait();

		if (alert.getResult() == ButtonType.YES) {
		    //do stuff
		
			
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
			
			for(Song song : allSongs) {
					out.println(song.getTitle());
					out.println(song.getArtist());
					out.println(song.getAlbum());
					out.println(song.getYear());
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
