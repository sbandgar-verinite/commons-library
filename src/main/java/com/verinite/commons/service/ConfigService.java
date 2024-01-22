package com.verinite.commons.service;

import com.verinite.commons.controlleradvice.BadRequestException;
import com.verinite.commons.dto.StatusResponse;

public interface ConfigService {

	StatusResponse addConfiguration(String key, Object value) throws BadRequestException;

	Object getConfiguration(String key) throws BadRequestException;

}
