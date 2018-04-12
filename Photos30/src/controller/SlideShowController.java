package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.UserState;

/**
 * Logic for automatic slideshow screen. Displays new photo every 30 seconds
 * @author alh220
 * @author jmuccino
 *
 */
public class SlideShowController implements Initializable {
	/** 
	 * Index of photo in slideshow must be declared globally in order to be changed.
	 */
	int index = 0;
	/**
	 * ImageView that holds current photo in slideshow.
	 */
	@FXML private ImageView photoDisplay;
	
	/**
	 * On close, cancel and purge timer, then close the stage.
	 * @param event passed in on button click.
	 * @throws IOException exception thrown if loading class fails
	 */
	public void close(ActionEvent event) throws IOException{
	    index = -1;
	    Stage stage  = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    stage.close();
	}
	
	/**
	 * Initial code called on load of screen.
	 * Sets timer to switch out picture every 3 seconds using Timer and TimerTask.
	 * @see java.util.Timer 
	 * @see java.util.TimerTask 
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		UserState.timer = new Timer();
		UserState.timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if(index == -1) {
					UserState.timer.cancel();
					return;
				}
				photoDisplay.setImage(UserState.getCurrentAlbum().getPhotos().get(index).getImage().getImage());
				photoDisplay.fitWidthProperty().bind(((Stage)photoDisplay.getScene().getWindow()).widthProperty());
				
				if(index == -1) {
					UserState.timer.cancel();
					return;
				}
				
				index++;
				if(index >= UserState.getCurrentAlbum().getPhotos().size()) {
					index = 0;
				}
			}
		}, 0, 3000);
	}
}
