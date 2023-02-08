package edu.midlands.training.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ConflictData extends RuntimeException {

  public ConflictData() {
  }

  public ConflictData(String message) {
    super(message);
  }
}
