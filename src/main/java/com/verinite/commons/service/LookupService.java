package com.verinite.commons.service;

import org.apache.coyote.BadRequestException;

public interface LookupService {

	void createLookup(String name) throws BadRequestException;

}
