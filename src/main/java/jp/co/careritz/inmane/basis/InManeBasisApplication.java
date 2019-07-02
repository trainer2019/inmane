package jp.co.careritz.inmane.basis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"jp.co.careritz.inmane.commons", "jp.co.careritz.inmane.businesses.controller"})
@SpringBootApplication
public class InManeBasisApplication {

  public static void main(String[] args) {
    SpringApplication.run(InManeBasisApplication.class, args);
  }
}
