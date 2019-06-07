package jp.co.careritz.inmane.service.security;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import jp.co.careritz.inmane.config.PropertyConfig;
import jp.co.careritz.inmane.dto.UsersDto;
import jp.co.careritz.inmane.model.security.SecurityUserModel;
import jp.co.careritz.inmane.service.UsersService;


/**
 * ログイン認証サービス.
 */
@Service
public class SecurityLoginService implements UserDetailsService {

  // ----------------------------------------------------------------------
  // インスタンス変数
  // ----------------------------------------------------------------------
  @Autowired
  private PropertyConfig propertyConfig;
  @Autowired
  private UsersService usersService;

  // ----------------------------------------------------------------------
  // インスタンスメソッド
  // ----------------------------------------------------------------------
  @Override
  public UserDetails loadUserByUsername(String userid)
      throws UsernameNotFoundException {

    /* ---------------------------------------------------- */
    /* ローカル変数の宣言と初期化 */
    /* ---------------------------------------------------- */
    // ログイン認証情報
    SecurityUserModel user = new SecurityUserModel();

    // アカウントロック対象失敗件数
    Integer lockAttemptCount = Integer.valueOf(propertyConfig.get("login.lock.attempt.count"));
    System.out.println("propertyConfig:" + propertyConfig.get("login.lock.attempt.count"));
    // アカウントロック時間（秒）
    Long lockSecond = Long.valueOf(propertyConfig.get("login.lock.second"));
    // 社員情報の取得
    UsersDto usersDto = usersService.findByPk(userid);

    /* ---------------------------------------------------- */
    /* 認証処理 */
    /* ---------------------------------------------------- */
    if (usersDto != null) {
      System.out.println("### userDto.userId:" + usersDto.getUserId());
      System.out.println("### userDto.roleName:" + usersDto.getRoleName());
      // ログイン認証情報に社員情報をコピー
      BeanUtils.copyProperties(usersDto, user);

      SimpleGrantedAuthority authority = new SimpleGrantedAuthority(usersDto.getRoleName());
      List<SimpleGrantedAuthority> authorities = new ArrayList<>();
      authorities.add(authority);
      user.setAuthorities(authorities);

      // アカウントロック中かどうかの検証
      if (lockAttemptCount.compareTo(user.getLoginFailureCount() + 1) <= 0) {
        if (lockSecond.compareTo(getDiffDateSecond(user.getLoginDeniedAt(), new Date())) >= 0) {
          user.setAccountLocked(true);
        }
      }

      return user;
    }

    throw new UsernameNotFoundException("user not found");
  }

  /**
   * 引数で指定された日付型オブジェクトの秒差を返します.
   *
   * @param from 検証対象日付(FROM)
   * @param to 検証対象日付(TO)
   * @return 検証対象日付(FROM)と検証対象日付(TO)の秒差
   */
  public static Long getDiffDateSecond(Date from, Date to) {

    // 入力検証
    if (from == null || to == null) {
      return null;
    }
    // 日付差
    long dayDif = (to.getTime() - from.getTime()) / 1000;

    return Long.valueOf(dayDif);
  }
}
