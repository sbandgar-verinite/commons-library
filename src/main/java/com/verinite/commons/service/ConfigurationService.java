package com.verinite.commons.service;

import org.springframework.stereotype.Service;

import com.verinite.commons.controlleradvice.BadRequestException;
import com.verinite.commons.dto.StatusResponse;
import com.verinite.commons.model.Config;

@Service
public interface ConfigurationService {

	StatusResponse addConfiguration(String key, Object value) throws BadRequestException;

	Config getConfiguration(String key) throws BadRequestException;

	Object getAllKeys();

}
