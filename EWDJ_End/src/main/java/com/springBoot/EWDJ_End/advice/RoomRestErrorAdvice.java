package com.springBoot.EWDJ_End.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.springBoot.EWDJ_End.rest.RoomRestController;

import domain.LocaleException;
import dto.model.ErrorOutputDTO;

@RestControllerAdvice(assignableTypes = {RoomRestController.class})
class RoomRestErrorAdvice {

  @ResponseBody
  @ExceptionHandler(LocaleException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  ErrorOutputDTO roomNotFoundHandler(LocaleException ex) {
    return new ErrorOutputDTO(ex.getMessage(), HttpStatus.NOT_FOUND.value());
  }
}