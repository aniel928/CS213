package controller;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Photo;
import model.Tag;
import model.UserState;

/**
 * Logic for tag screen. Add and remove tags from photo.
 * 
 * @author alh220
 * @author jmuccino
 *
 */
public class TagController implements Initializable {
	/**
	 * Current photo being viewed.  The photo for which we're viewing tgas.
	 */
	private Photo currentPhoto;
	/**
	 * Dynamic list of current photo tags.
	 */
	private ObservableList<Tag> obsTagList;
	/**
	 * Table to hold the list of tags
	 */
	@FXML private TableView<Tag> table;
	/**
	 * Column to hold all tags that are associated with a value in the adjacent column.
	 */
	@FXML private TableColumn<Tag, String> tagCol;
	/**
	 * Column to hold all values that are associated with a tag in the adjacent column.
	 */
	@FXML private TableColumn<Tag, String> valueCol;
	/**
	 * Field to type a new tag into
	 */
	@FXML private TextField tagField;
	/**
	 * Field to type a new value into
	 */
	@FXML private TextField valueField;
	/**
	 * Button to execute adding a new tag.
	 */
	@FXML private Button saveButton;
	
	/**
	 * Closes current window and brings back to the album view.
	 * @param event passed in via button click.
	 * @throws IOException
	 */
	public void back(ActionEvent event) throws IOException { 
	    Stage stage  = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    stage.close();
	}
		
	/**
	 * Shows fields and button needed to add a new tag.
	 */
	@FXML
	public void showTagFields() {
		boolean bool = saveButton.isVisible();
		valueField.setText("");
		tagField.setText("");
		tagField.setVisible(!bool);
		valueField.setVisible(!bool);
		saveButton.setVisible(!bool);
	}
	
	/**
	 * If both fields aren't empty adds new Tag/Value pair.
	 */
	@FXML
	public void addTag() {
		if(tagField.getText().isEmpty() || valueField.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR, "Please fill out tag and value fields");
			alert.showAndWait();
		}
		else{
			for(Tag tag : currentPhoto.getAllTags()) {
				if(tag.getTag().equals(tagField.getText().toLowerCase()) && tag.getValue().equals(valueField.getText().toLowerCase())){
					Alert alert = new Alert(AlertType.ERROR, "Tag already exists for this photo!");
					alert.showAndWait();
					return;
				}
			}
			Tag tag = new Tag(tagField.getText().toLowerCase(), valueField.getText().toLowerCase());
			currentPhoto.getAllTags().add(tag);
			obsTagList.add(tag);
			showTagFields();
		}
		
		
	}
	
	/**
	 * Removes a tag/value pair from the photo.
	 */
	@FXML
	public void deleteTag() {
		if(table.getSelectionModel().getSelectedIndex() == -1)
		{
			Alert alert = new Alert(AlertType.ERROR, "Please select an item.");
			alert.showAndWait();
		}	
		else {
			Alert alert = new Alert(AlertType.WARNING, "You are about to delete this tag.  This action cannot be undone.  Would you like to continue?", ButtonType.YES, ButtonType.CANCEL);
			alert.showAndWait();
			
			if (alert.getResult() == ButtonType.YES) {
				Tag tag = table.getSelectionModel().getSelectedItem();
				currentPhoto.getAllTags().remove(tag);
				obsTagList.remove(tag);
			}
		}
	}
	
	/**
	 * Code called upon initial load of screen. Sets table full of current Tag/Value pairs.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//get user state and set album name
		currentPhoto = UserState.getCurrentPhoto();

		//set table view columns
		tagCol.setCellValueFactory(new PropertyValueFactory<Tag, String>("tag"));
		valueCol.setCellValueFactory(new PropertyValueFactory<Tag, String>("value"));
		
		//set list
		obsTagList = FXCollections.observableArrayList(currentPhoto.getAllTags());
		table.setItems(obsTagList);	
		table.getSelectionModel().select(0);
	}
}
