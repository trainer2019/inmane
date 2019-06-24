package jp.co.careritz.inmane.commons;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"jp.co.careritz.inmane.commons", "jp.co.careritz.inmane.businesses.controller"})
@SpringBootApplication
public class InManeCommonsApplication {

  public static void main(String[] args) {
    SpringApplication.run(InManeCommonsApplication.class, args);
  }
}
