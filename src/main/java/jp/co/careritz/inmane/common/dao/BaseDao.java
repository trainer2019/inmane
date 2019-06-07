package jp.co.careritz.inmane.common.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import jp.co.careritz.inmane.config.PropertyConfig;

@Repository
public class BaseDao {

  @Autowired
  private PropertyConfig propertyConfig;

  public PropertyConfig getPropertyConfig() {
    return propertyConfig;
  }

  // DBの接続情報をプロパティから取得
  public String getDriverName() {
    return propertyConfig.get("spring.datasource.driver-class-name");
  }

  public String getJdbcUrl() {
    return propertyConfig.get("spring.datasource.url");
  }

  public String getDsUserName() {
    return propertyConfig.get("spring.datasource.username");
  }

  public String getDsPassword() {
    return propertyConfig.get("spring.datasource.password");
  }

}
