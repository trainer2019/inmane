package jp.co.careritz.inmane.businesses;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import jp.co.careritz.inmane.basis.InManeBasisApplication;

public class ServletInitializer extends SpringBootServletInitializer {

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(InManeBasisApplication.class);
  }

}
