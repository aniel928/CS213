package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Tag {
	
	//regular constructor
	public Tag(String tag, String value) {
		tagProperty().set(tag);
		valueProperty().set(value);
	}
	
	//Caption property and methods
	private StringProperty tag;
	public void setTagn(String value) {
		tagProperty().set(value);
	}
	public String getTag() {
		return tagProperty().get();
	}
	public StringProperty tagProperty() {
		if(tag == null) {
			tag = new SimpleStringProperty(this, "tag");
		}
		return tag;
	}

	
	//Caption property and methods
	private StringProperty value;
	public void setValue(String val) {
		valueProperty().set(val);
	}
	public String getValue() {
		return valueProperty().get();
	}
	public StringProperty valueProperty() {
		if(value == null) {
			value = new SimpleStringProperty(this, "value");
		}
		return value;
	}

	

}
