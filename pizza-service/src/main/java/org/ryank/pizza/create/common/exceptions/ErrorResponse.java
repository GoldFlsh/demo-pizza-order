package org.ryank.pizza.create.common.exceptions;

import java.util.List;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorResponse {

  private final HttpStatus status;
  private final String message;
  private final List<String> errors;
}
