package com.verinite.commons.util;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
import org.springframework.stereotype.Component;

@Component
public class CommonUtils {

	public static String executeJs(String jsSnippet) {
		try (Context context = Context.create()) {
			Value result = context.eval("js", jsSnippet);
			return result.toString();
		}
	}

}
