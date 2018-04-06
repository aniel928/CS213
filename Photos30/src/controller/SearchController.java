package controller;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class SearchController implements Initializable {
	@FXML RadioButton all;
	@FXML RadioButton any;
	@FXML DatePicker startDate;
	@FXML DatePicker endDate;
	@FXML TextField tag1;
	@FXML TextField tag2;
	@FXML TextField tag3;
	@FXML TextField tag4;
	@FXML TextField tag5;
	@FXML TextField value1;
	@FXML TextField value2;
	@FXML TextField value3;
	@FXML TextField value4;
	@FXML TextField value5;
	
	public void logout(ActionEvent event) throws IOException {
		Main.changeScene("/view/login.fxml");
	}
	
	public void home(ActionEvent event) throws IOException{
		Main.changeScene("/view/userhome.fxml");
	}
	
	@FXML
	public void search() {
		//open new window with search results.
		if(!(all.isSelected() || any.isSelected())) {
			Alert alert = new Alert(AlertType.ERROR, "Please select matching type.");
			alert.showAndWait();
		}
		LocalDate start = startDate.getValue();
		LocalDate end = endDate.getValue();
		String stringTag1 = tag1.getText();
		String stringValue1 = value1.getText();
		
		System.out.println(start);
		System.out.println(end);
		System.out.println(stringTag1);
		System.out.println(stringValue1);
		
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//add Tags later then replace text fields with combo box.

	}
}
