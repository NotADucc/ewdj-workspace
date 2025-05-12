package com.springBoot.restFruitStart;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import exceptions.FruitNotFoundException;

@RestControllerAdvice
class FruitErrorAdvice {
	@ResponseBody
	@ExceptionHandler(FruitNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String fruitNotFoundHandler(FruitNotFoundException ex) {
		return ex.getMessage();
	}
}
