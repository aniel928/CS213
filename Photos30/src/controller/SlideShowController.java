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

public class SlideShowController implements Initializable {
	int index = 0;
	@FXML private ImageView photoDisplay;
	
	public void close(ActionEvent event) throws IOException{
	    UserState.timer.cancel();
	    UserState.timer = new Timer();
	    Stage stage  = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    stage.close();
	}
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		UserState.timer.schedule(new TimerTask() {
			@Override
			public void run() {
				photoDisplay.setImage(UserState.getCurrentAlbum().getPhotos().get(index).getImage().getImage());
				photoDisplay.fitWidthProperty().bind(((Stage)photoDisplay.getScene().getWindow()).widthProperty());
				
				index++;
				if(index >= UserState.getCurrentAlbum().getPhotos().size()) {
					index = 0;
				}
			}
		}, 0, 3000);
	}
}
