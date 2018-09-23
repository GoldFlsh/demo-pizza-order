package org.ryank.pizza.create.common.exceptions;

public class BadRequestException extends RuntimeException {

  public BadRequestException() {
    super();
  }

  public BadRequestException(String message) {
    super(message);
  }

  public BadRequestException(String message, Throwable cause) {
    super(message, cause);
  }

  public BadRequestException(Throwable cause) {
    super(cause);
  }

  //TODO Global Exception Handler to catch and handle elegant error responses to client(s)
}