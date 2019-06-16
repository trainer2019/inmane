package jp.co.careritz.inmane.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DataSourceProperty {

  @Value("${spring.datasource.driver-class-name}")
  private String driverName;

  @Value("${spring.datasource.url}")
  private String jdbcUrl;

  @Value("${spring.datasource.username}")
  private String dsUserName;

  @Value("${spring.datasource.password}")
  private String dsPassword;

  public String getDriverName() {
    return driverName;
  }

  public String getJdbcUrl() {
    return jdbcUrl;
  }

  public String getDsUserName() {
    return dsUserName;
  }

  public String getDsPassword() {
    return dsPassword;
  }

}
