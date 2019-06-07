package jp.co.careritz.inmane.controller.commons;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import jp.co.careritz.inmane.config.PropertyConfig;
import jp.co.careritz.inmane.model.security.SecurityUserModel;


/**
 * アプリケーション基底コントローラ.
 */
@Controller
public abstract class AbstractAppController {

  // --------------------------------------------------------------------------
  // インスタンス変数
  // --------------------------------------------------------------------------
  /** プロパティ設定. */
  @Autowired
  protected PropertyConfig propertyConfig;

  // --------------------------------------------------------------------------
  // 共通定義
  // --------------------------------------------------------------------------
  /**
   * コントローラ共通処理.
   * 
   * @param req HTTPサーブレットリクエスト
   * @param model モデル
   */
  @ModelAttribute
  public void initModel(HttpServletRequest req, Model model) {
    // セッションからユーザ情報を取得
    SecurityUserModel user = getSessionUser();
    String userName = user != null ? user.getUserName() : "anonymousUser";
    String userRole = user != null ? user.getRoleName() : "NONE";

    // ログインユーザ名
    model.addAttribute("sessionUserName", userName);
    model.addAttribute("sessionUserRole", userRole);
  }


  /**
   * ログインユーザ情報をセッションから取得します.
   *
   * @return ログインユーザモデル.
   */
  public SecurityUserModel getSessionUser() {

    Object sessionData = null;

    if (SecurityContextHolder.getContext() != null
        && SecurityContextHolder.getContext().getAuthentication() != null) {
      if ("anonymousUser".equals(
          sessionData = SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
        return null;
      }
    }
    return (SecurityUserModel) sessionData;
  }

}
