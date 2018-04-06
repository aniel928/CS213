package model;

import java.io.Serializable;

public class Tag implements Serializable {
	//auto-generated serialization ID:
	private static final long serialVersionUID = 4300979057337404473L;
	//Tag name
	private String tag;
	//Value of tag
	private String value;
	
/*
 * CONSTRUCTOR(S)
 */
	public Tag(String tag, String value) {
		setTag(tag);
		setValue(value);
	}
	
/*
 * GETTERS
 */
	public String getTag() {
		return this.tag;
	}
	public String getValue() {
		return this.value;
	}	
	
/*
 * SETTERS	
 */
	
	public void setTag(String value) {
		this.tag = value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	

}
