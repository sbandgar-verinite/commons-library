package com.verinite.commons.controller;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.verinite.commons.dto.StatusResponse;
import com.verinite.commons.service.ConfigurationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/config")
@RequiredArgsConstructor
public class ConfigurationController {

	@Autowired
	private ConfigurationService configService;

	@PostMapping("/{key}")
	public ResponseEntity<StatusResponse> addConfiguration(@PathVariable String key, @RequestBody Object value)
			throws BadRequestException {
		return ResponseEntity.ok(configService.addConfiguration(key, value));
	}

	@GetMapping("/{key}")
	public ResponseEntity<Object> getConfiguration(@PathVariable String key) throws BadRequestException, JsonMappingException, JsonProcessingException, com.verinite.commons.controlleradvice.BadRequestException {
		return ResponseEntity.ok(configService.getConfiguration(key));
	}

	@GetMapping
	public ResponseEntity<Object> getAllKeys() {
		return ResponseEntity.ok(configService.getAllKeys());
	}
}