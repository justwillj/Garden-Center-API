package edu.midlands.training.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This will throw a status code 409 if an error happens
 */
@ResponseStatus(value = HttpStatus.CONFLICT)
public class ConflictData extends RuntimeException {

  public ConflictData() {
  }

  public ConflictData(String message) {
    super(message);
  }
}
