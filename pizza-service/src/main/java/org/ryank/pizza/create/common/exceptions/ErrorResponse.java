package org.ryank.pizza.create.common.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
class ErrorResponse {

  private final HttpStatus status;
  private final String message;
}
