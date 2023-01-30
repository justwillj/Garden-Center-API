package edu.midlands.training.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "The data you have entered is not valid")
public class ConflictData extends RuntimeException {

  public ConflictData() {
  }
  public ConflictData (String message) {
    super(message);
  }
}
