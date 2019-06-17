package jp.co.careritz.inmane.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import jp.co.careritz.inmane.constant.AppConst;
import jp.co.careritz.inmane.service.security.SecurityLoginFailureHandler;
import jp.co.careritz.inmane.service.security.SecurityLoginProvider;
import jp.co.careritz.inmane.service.security.SecurityLoginService;


/**
 * Spring Security設定.
 */
@Configuration
@EnableWebSecurity
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private SecurityLoginService loginService;
  @Autowired
  private SecurityLoginFailureHandler loginFailureHandler;

  // @Value("${security.require-ssl}")
  // private boolean requireHttps;

  /**
   * configure of WebSecurity.
   */
  @Override
  public void configure(WebSecurity web) throws Exception {

    // セキュリティ設定を無視するリクエスト設定
    web.ignoring()
        // 静的リソースに対するアクセスはセキュリティ設定を無視する
        .antMatchers("/css/**", "/images/**", "/js/**", "/plugins/**", "/unify/**");
  }

  /**
   * configure of HttpSecurity.
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {

    // HTTPS設定
    // if (this.requireHttps) {
    // // Spring内部のリダイレクトをHTTPSに変更
    // http.requiresChannel().anyRequest().requiresSecure().and();
    // http.portMapper().http(8080).mapsTo(443).and();
    // }

    // 認可の設定
    http.authorizeRequests()
        // ログイン、セキュリティ認証、エラー画面以外は認証が必要
        .antMatchers("/login/**", "/security/**", "/api/**", "/error/**").permitAll()
        .mvcMatchers("/maintenance/**").hasAuthority(AppConst.ROLE_NAME_ADMIN)
        // .antMatchers("/maintenance/").hasRole("ADMIN") // ADMIN権限がないとアクセスできないURL
        .anyRequest().authenticated() // /js、/css以外へのアクセスに対しては認証を要求
        .and()
        // ログイン設定
        .formLogin().loginPage("/login") // 認証元のログイン画面
        .loginProcessingUrl("/security/login") // 認証URL
        .usernameParameter("userId") // ユーザ名のリクエストパラメータ名
        .passwordParameter("password") // パスワードのリクエストパラメータ名
        .defaultSuccessUrl("/top", true) // 認証成功時の遷移先URL
        .failureHandler(loginFailureHandler) // 認証失敗時のカスタム処理
        // .failureUrl("/loginForm?error=true") // 認証失敗時の遷移先URL
        .permitAll();

    // // ログアウト設定
    http.logout()
        // 認証URL
        .logoutRequestMatcher(new AntPathRequestMatcher("/security/logout"));

  }

  /**
   * configure of AuthenticationManagerBuilder.
   */
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(loginProvider());
  }

  /**
   * loginProvider.
   */
  @Bean
  public SecurityLoginProvider loginProvider() throws Exception {
    SecurityLoginProvider provider = new SecurityLoginProvider();

    provider.setPasswordEncoder(new BCryptPasswordEncoder());
    provider.setUserDetailsService(loginService);

    return provider;
  }
}
