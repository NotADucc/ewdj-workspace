package com.springBoot.EWDJ_End.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import domain.LocaleException;

@RestControllerAdvice
class RoomRestErrorAdvice {

  @ResponseBody
  @ExceptionHandler(LocaleException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String roomNotFoundHandler(LocaleException ex) {
    return ex.getMessage();
  }
}
