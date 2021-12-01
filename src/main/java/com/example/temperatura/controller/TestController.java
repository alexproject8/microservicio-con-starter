package com.example.temperatura.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import starter.Temperatura;

@RestController
public class TestController {

	@Autowired
	private Temperatura temp;
	
	@Value("${some.value}")
	private String grado;
	
	private final static Logger logger = LoggerFactory.getLogger(TestController.class);
	private Counter celcius;
	private Counter fahren;
	
	public TestController(MeterRegistry registery) {
		this.celcius = Counter.builder("invocaciones-consult.celcius").description("Invocaciones totales").register(registery);
		this.fahren = Counter.builder("invocaciones-conver.fahren").description("Invocaciones totales").register(registery);
	}
	
	@GetMapping(path="/grados")
	public String mostrarCel() {
		return this.grado;
	}
	
	@GetMapping(path = "/temperaturac/{celsius}")
	public double celsiusAfahrenheit(@PathVariable("celsius")int celsius) {
		celcius.increment();
		logger.info("LLamada al endpoint "+celcius.count());
		return temp.celsiusAfahrenheit(celsius);

	}
	
	@GetMapping(path = "/temperaturaf/{fahrenheit}")
	public double fahrenheitAcelsius(@PathVariable("fahrenheit") int fahrenheit) {
		fahren.increment();
		logger.info("LLamada al endpoint "+fahren.count());
		return temp.fahrenheitAcelsius(fahrenheit);

	}
}
