package com.astar;

import java.util.Date;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@GetMapping("hello")
	public String sayHello() {
		return "Hello Collabera!";
	}

	@GetMapping("hellobyname")
	public String sayHelloByName(
			@RequestParam(name = "name", defaultValue = "Collabera", required = false) String myname) {
		return "Hello " + myname;
	}

	@GetMapping("hello/{name}")
	public String sayHelloByNamePath(@PathVariable(name = "name") String myname) {
		return "Hello " + myname;
	}

	@GetMapping(path = "helloobj", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
			MediaType.TEXT_XML_VALUE })

	public Hello sayHelloObj() {
		return new Hello("Raj", "Hi", new Date());
	}

	@PostMapping(path = "helloobj", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })

	public Hello addHelloObj(@RequestBody Hello hello) {
		System.out.println(hello);
		return hello;
	}

}
