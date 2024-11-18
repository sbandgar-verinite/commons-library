package com.verinite.commons.controller;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.verinite.commons.dto.StatusResponse;
import com.verinite.commons.service.LookupService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/lookup")
public class LookupController {

	@Autowired
	private LookupService lookupService;

	@PostMapping
	public ResponseEntity<StatusResponse> createLookup(@RequestParam String name) throws BadRequestException {
		StatusResponse response = lookupService.createLookup(name);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PostMapping("/{lookup}/values")
	public ResponseEntity<StatusResponse> addValues(@PathVariable String lookup, @RequestBody Object data) throws BadRequestException {
		StatusResponse response = lookupService.addValues(lookup, data);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping("/{lookup}")
	public ResponseEntity<List<Object>> getLookups(@PathVariable String lookup) throws BadRequestException {
		List<Object> response = lookupService.getLookups(lookup);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
