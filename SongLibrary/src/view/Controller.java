package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public interface Controller {
	public ObservableList<String> obsList = FXCollections.observableArrayList("song1");
	
}
