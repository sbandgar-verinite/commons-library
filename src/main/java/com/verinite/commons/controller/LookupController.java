package com.verinite.commons.controller;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.verinite.commons.service.LookupService;

@RestController
@RequestMapping("/lookup")
public class LookupController {

	@Autowired
	private LookupService lookupService;

	@PostMapping
	public void createLookup(@RequestParam String name) throws BadRequestException {
		lookupService.createLookup(name);
	}

}
