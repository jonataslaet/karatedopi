package br.com.karatedopi.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	
	@RequestMapping(value="/cumprimentos", method=RequestMethod.GET)
	String cumprimenta() {
		return "Hello world";
	}

}
