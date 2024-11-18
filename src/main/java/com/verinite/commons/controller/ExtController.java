package com.verinite.commons.controller;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.verinite.commons.service.ExtService;

@RestController
@RequestMapping("/commons")
public class ExtController {

	@Autowired
	private ExtService extService;

	@GetMapping("/execute")
	public String executeJs(String jsFunc) {
		return extService.executeJs(jsFunc);
	}

}
