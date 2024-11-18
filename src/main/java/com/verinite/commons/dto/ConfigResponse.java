package com.verinite.commons.dto;

import com.fasterxml.jackson.databind.JsonNode;

public class ConfigResponse {

	private JsonNode data;

	public JsonNode getData() {
		return data;
	}

	public void setData(JsonNode data) {
		this.data = data;
	}

	public ConfigResponse(JsonNode data) {
		this.data = data;
	}

	public ConfigResponse() {
	}

}
