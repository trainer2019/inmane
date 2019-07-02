package jp.co.careritz.inmane.basis.config;

import java.text.MessageFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import jp.co.careritz.inmane.basis.common.constant.AppConst;

/**
 * プロパティ設定.
 */
@Configuration
@PropertySource({"classpath:/ValidationMessages.properties",
    "classpath:/MessageResources.properties", "classpath:/application.properties"})
public class PropertyConfig {

  @Autowired
  private Environment env;

  /**
   * プロパティ値を取得します.
   * 
   * @param key プロパティキー
   * @return プロパティ値
   */
  public String get(String key) {
    return env.getProperty(key);
  }

  /**
   * 引数付きのプロパティ値を取得します.
   *
   * @param key プロパティキー
   * @param args 引数
   * @return プロパティ値
   */
  public String get(String key, Object... args) {
    final String prop = env.getProperty(key);
    if (prop != null) {
      return MessageFormat.format(prop, args);
    } else {
      return AppConst.EMPTY;
    }
  }
}
