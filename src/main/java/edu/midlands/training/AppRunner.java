package edu.midlands.training;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppRunner {

  private static Logger logger = LoggerFactory.getLogger(AppRunner.class);

  public static void main(String[] args) {
    logger.info("Application starting up...");
    SpringApplication.run(AppRunner.class, args);
  }
}
