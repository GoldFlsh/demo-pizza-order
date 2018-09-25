package org.ryank.pizza.create.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException {
  public ConflictException() {
    super();
  }

  public ConflictException(String message) {
    super(message);
  }

  public ConflictException(String message, Throwable cause) {
    super(message, cause);
  }

  public ConflictException(Throwable cause) {
    super(cause);
  }
}
