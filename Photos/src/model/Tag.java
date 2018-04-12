package model;

import java.io.Serializable;

/**
 * Model of the tag.  Has a tag and value property.
 * 
 * @author alh220
 * @author jmuccino
 *
 */
public class Tag implements Serializable {
	/**
	 * auto-generated serialization ID:
	 */
	private static final long serialVersionUID = 4300979057337404473L;
	/**
	 * Tag name
	 */
	private String tag;
	/**
	 * Value of tag
	 */
	private String value;
	
/*
 * CONSTRUCTOR(S)
 */
	/**
	 * Create new tag object, sets tag/value pair.
	 * 
	 * @param tag String value representing tag
	 * @param value String value representing value of tag.
	 */
	public Tag(String tag, String value) {
		setTag(tag);
		setValue(value);
	}
	
/*
 * GETTERS
 */
	/**
	 * Retrieves the tag itself.
	 * 
	 * @return String representing tag
	 */
	public String getTag() {
		return this.tag;
	}
	
	/**
	 * Retrieves the value of the tag.
	 * 
	 * @return String representing value of tag.
	 */
	public String getValue() {
		return this.value;
	}	
	
/*
 * SETTERS	
 */
	/**
	 * Sets the tag name (used in constructor).
	 * @param value
	 */
	public void setTag(String value) {
		this.tag = value;
	}
	/**
	 * Sets the tag value (used in constructor);
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}
	

}
