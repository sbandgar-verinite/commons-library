package com.verinite.commons.service;

import org.springframework.stereotype.Service;

import com.verinite.commons.controlleradvice.BadRequestException;
import com.verinite.commons.dto.StatusResponse;

@Service
public interface ConfigurationService {

	StatusResponse addConfiguration(String key, Object value) throws BadRequestException;

	Object getConfiguration(String key) throws BadRequestException;

}
