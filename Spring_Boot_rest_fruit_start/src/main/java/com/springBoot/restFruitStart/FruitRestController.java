package com.springBoot.restFruitStart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import domain.Fruit;
import service.FruitService;

@RestController
@RequestMapping("/fruit")
public class FruitRestController {

	@Autowired
	private FruitService fruitService;
	
	@GetMapping("/{fruitId}")
	public Fruit getFruitDetail(@PathVariable int fruitId) {
		return fruitService.getFruitDetail(fruitId);
	}

	@GetMapping("/all")
	public List<Fruit> getAllFruits() {
		return fruitService.getAllFruits();
	}
}
