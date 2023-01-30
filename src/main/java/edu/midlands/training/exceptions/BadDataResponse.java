package edu.midlands.training.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "The User isn't in the system")

public class BadDataResponse extends RuntimeException {

  public BadDataResponse() {
  }

  public BadDataResponse(String message) {
    super(message);
  }
}
