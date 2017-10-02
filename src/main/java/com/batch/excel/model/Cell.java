package com.batch.excel.model;

public class Cell implements Comparable <Cell>{
	
	private String name;
	private Object value;
	private Object type;
	private Boolean valid;
	private Boolean required;
	/*
	public Cell(String name, String value, Object type){
		this.name = name;
		this.value = value;
		this.type = type;
	}
	*/
	public Cell(String name, String value){
		this.name = name;
		this.value = value;
	}
	
	public Cell(String name, Object type, Boolean required){
		this.name = name;
		this.type = type;
		this.required = required;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Object getType() {
		return type;
	}

	public void setType(Object type) {
		this.type = type;
	}

	public Boolean isValid() {
		return valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}

	public Boolean isRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}
	
	public int compareTo(Cell c) {
		return this.name.compareTo(c.name);
	}
	
}