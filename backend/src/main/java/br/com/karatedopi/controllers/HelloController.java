package br.com.karatedopi.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

	@GetMapping
	@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
	public String cumprimentar() {
		return "hello";
	}
	
}
