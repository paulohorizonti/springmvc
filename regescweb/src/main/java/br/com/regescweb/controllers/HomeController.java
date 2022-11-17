package br.com.regescweb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping("/")
	public String home() {
		
		return "home"; //renderiza pagina home que esta em templtes
		
	}

}
