package com.springBoot.restFruitStart;

import java.util.List;
import domain.Fruit;
import service.FruitService;

public class FruitRestController {


    private FruitService fruitService;


    public Fruit getFruitDetail(  int fruitId) {
        return fruitService.getFruitDetail(fruitId);
    }
    
    public List<Fruit> getAllFruits() {
        return fruitService.getAllFruits();
    }
}
