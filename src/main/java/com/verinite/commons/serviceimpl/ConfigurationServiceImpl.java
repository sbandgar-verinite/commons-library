package com.verinite.commons.serviceimpl;

import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.verinite.commons.controlleradvice.BadRequestException;
import com.verinite.commons.dto.StatusResponse;
import com.verinite.commons.model.Config;
import com.verinite.commons.repo.ConfigurationRepository;
import com.verinite.commons.service.ConfigurationService;
import com.verinite.commons.util.Constants;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConfigurationServiceImpl implements ConfigurationService {

	private final Logger logger = LoggerFactory.getLogger(ConfigurationServiceImpl.class);

	@Autowired
	private ConfigurationRepository configRepo;

	@Override
	public StatusResponse addConfiguration(String key, Object value) throws BadRequestException {
		logger.info("[SERVICE] Request received to add configuration for key : {}", key);
		logger.debug("Value for key {} = {}", key, value);
		if (key == null) {
			throw new BadRequestException("Please pass a valid key");
		}
		if (value == null) {
			throw new BadRequestException("Please pass a valid value");
		}

		Optional<Config> existingConfig = configRepo.findByKeyName(key);
//		if (existingConfig.isPresent()) {
//			throw new BadRequestException("Duplicate Key. Key already exists");
//		}

		if(existingConfig.isEmpty()) {
			Config config = new Config();
			config.setKeyName(key);
			try {
				config.setData(new ObjectMapper().writeValueAsString(value));
			} catch (JsonProcessingException e) {
				throw new BadRequestException("Invalid Value");
			}
			configRepo.save(config);
			logger.info("Configuration saved successfully for key : {}", key);
		}
		else {
			try {
				existingConfig.get().setData(new ObjectMapper().writeValueAsString(value));
			} catch (JsonProcessingException e) {
				throw new BadRequestException("Invalid Value");
			}
			configRepo.save(existingConfig.get());
			logger.info("Configuration saved successfully for key : {}", key);
		}
		return new StatusResponse(Constants.SUCCESS, HttpStatus.CREATED.value(), "Configuration Saved Successfully");
	}

	@Override
	public Object getConfiguration(String key) throws BadRequestException {
		logger.info("[SERVICE] Request received to get configuration for key : {}", key);
		if (key == null) {
			throw new BadRequestException("Please pass a valid key");
		}
		Optional<Config> config = configRepo.findByKeyName(key);
		if (config.isEmpty()) {
			throw new BadRequestException("No data found for config key");
		}

		Map<String, Object> data = null;
		try {
			data = new ObjectMapper().readValue(config.get().getData(), Map.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return data;
	}

}