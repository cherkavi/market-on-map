package com.cherkashyn.vitalii.market.datasource;

/**
 * element which emulate block WHERE for SQL query 
 */
public class FilterElement {
	private String fieldName;
	private FilterOperation operation;
	private String value;
	
	public FilterElement(String fieldName, FilterOperation operation,
			String value) {
		super();
		this.fieldName = fieldName;
		this.operation = operation;
		this.value = value;
	}
	
	
	public String getFieldName() {
		return fieldName;
	}
	public FilterOperation getOperation() {
		return operation;
	}
	public String getValue() {
		return value;
	}
	
	
}
