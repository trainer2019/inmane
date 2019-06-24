package jp.co.careritz.inmane.businesses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"jp.co.careritz.inmane.commons", "jp.co.careritz.inmane.businesses"})
@SpringBootApplication
public class InManeBusinessesApplication {

  public static void main(String[] args) {
    SpringApplication.run(InManeBusinessesApplication.class, args);
  }
}
