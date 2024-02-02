package com.verinite.commons.dto;

public class LookupResponse {

	private Long id;

	private String key;

	private String label;

	private String value;

	public LookupResponse(Long id, String key, String label, String value) {
		this.id = id;
		this.key = key;
		this.label = label;
		this.value = value;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
