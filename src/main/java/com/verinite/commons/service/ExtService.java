package com.verinite.commons.service;

import org.springframework.stereotype.Service;

@Service
public interface ExtService {

	String executeJs(String jsFunc);
}