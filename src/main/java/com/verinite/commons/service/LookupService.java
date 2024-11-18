package com.verinite.commons.service;

import java.util.List;

import org.apache.coyote.BadRequestException;

import com.verinite.commons.dto.StatusResponse;

public interface LookupService {

	StatusResponse createLookup(String name) throws BadRequestException;

	StatusResponse addValues(String lookup, Object data) throws BadRequestException;

	List<Object> getLookups(String name);

}
