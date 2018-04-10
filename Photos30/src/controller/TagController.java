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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Photo;
import model.Tag;
import model.UserState;

public class TagController implements Initializable {
	private Photo currentPhoto;
	private ObservableList<Tag> obsTagList;
	
	@FXML private TableView<Tag> table;
	@FXML private TableColumn<Tag, String> tagCol;
	@FXML private TableColumn<Tag, String> valueCol;
	@FXML private TextField tagField;
	@FXML private TextField valueField;
	@FXML private Button saveButton;
	
	public void back(ActionEvent event) throws IOException { 
	    Stage stage  = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    stage.close();
	}
		
	@FXML
	public void showTagFields() {
		boolean bool = saveButton.isVisible();
		valueField.setText("");
		tagField.setText("");
		tagField.setVisible(!bool);
		valueField.setVisible(!bool);
		saveButton.setVisible(!bool);
	}
	
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
	
	@FXML
	public void deleteTag() {
		if(table.getSelectionModel().getSelectedIndex() == -1)
		{
			Alert alert = new Alert(AlertType.ERROR, "Please select an item.");
			alert.showAndWait();
		}	
		else {
			
			Tag tag = table.getSelectionModel().getSelectedItem();
			currentPhoto.getAllTags().remove(tag);
			obsTagList.remove(tag);
		}
	}
	
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
