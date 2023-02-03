package edu.midlands.training.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
public class ServiceUnavailable extends RuntimeException {

  public ServiceUnavailable() {
  }

  public ServiceUnavailable(String message) {
    super(message);
  }

  public ServiceUnavailable(Exception e) {
    super(e.getCause());
  }
}
