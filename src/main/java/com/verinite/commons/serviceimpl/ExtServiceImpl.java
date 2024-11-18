package com.verinite.commons.serviceimpl;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.verinite.commons.service.ExtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExtServiceImpl implements ExtService {

	private final Logger logger = LoggerFactory.getLogger(ExtServiceImpl.class);

	@Override
	public String executeJs(String jsFunc) {
		try (Context context = Context.create()) {
//            Value result = context.eval("js", "print('Hello from JavaScript!');");
//        	String jsFun = "function addDays(date, days) {     let result = new Date(date);     result.setDate(result.getDate() + days);     const day = String(result.getDate()).padStart(2, '0');     const month = String(result.getMonth() + 1).padStart(2, '0');     const year = result.getFullYear();     return `${day}-${month}-${year}`; } let today = new Date(); let futureDate = addDays(today, 10);";
			logger.info(jsFunc);
			Value result = context.eval("js", jsFunc);
			return result.toString();
		}
	}

}