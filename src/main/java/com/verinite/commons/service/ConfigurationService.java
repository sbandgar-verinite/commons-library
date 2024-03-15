package com.verinite.commons.service;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.verinite.commons.controlleradvice.BadRequestException;
import com.verinite.commons.dto.ConfigResponse;
import com.verinite.commons.dto.StatusResponse;

@Service
public interface ConfigurationService {

	StatusResponse addConfiguration(String key, Object value) throws BadRequestException;

	ConfigResponse getConfiguration(String key) throws BadRequestException, JsonMappingException, JsonProcessingException;

	Object getAllKeys();

}
