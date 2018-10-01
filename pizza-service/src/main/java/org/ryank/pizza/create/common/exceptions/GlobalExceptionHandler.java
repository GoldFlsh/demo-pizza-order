package org.ryank.pizza.create.common.exceptions;

import java.util.Collections;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = NotFoundException.class)
  public ResponseEntity<ErrorResponse> handleNotFoundException(RuntimeException e, WebRequest request) {
    return buildErrorResponse(HttpStatus.NOT_FOUND, e);
  }

  @ExceptionHandler(value = BadRequestException.class)
  public ResponseEntity<ErrorResponse> handleBadRequestException(RuntimeException e, WebRequest request) {
    return buildErrorResponse(HttpStatus.BAD_REQUEST, e);
  }

  @ExceptionHandler(value = ConflictException.class)
  public ResponseEntity<ErrorResponse> handleConflictException(RuntimeException e, WebRequest request) {
    return buildErrorResponse(HttpStatus.CONFLICT, e);
  }

  @ExceptionHandler({ Exception.class })
  public ResponseEntity<ErrorResponse> defaultExceptionHandler(Exception e, WebRequest request) {
    return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e);
  }

  private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, Exception e) {
    ErrorResponse errorResponse = new ErrorResponse(
        status,
        e.getLocalizedMessage());
    return new ResponseEntity<>(
        errorResponse, new HttpHeaders(), errorResponse.getStatus());
  }


}
