package com.verinite.commons;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/commons")
public class Controller {

	@GetMapping("/execute")
    public String executeJs() {
        try (Context context = Context.create()) {
            // Execute a simple JavaScript code
            Value result = context.eval("js", "print('Hello from JavaScript!');");
            return "JavaScript Execution Result: " + result.toString();
        }
    }
	
}
