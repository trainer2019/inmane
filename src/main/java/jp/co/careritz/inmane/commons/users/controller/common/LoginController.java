package jp.co.careritz.inmane.commons.users.controller.common;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * ログインコントローラ.
 */
@Controller
public class LoginController extends AbstractAppController {

  // ----------------------------------------------------------------------
  // インスタンスメソッド
  // ----------------------------------------------------------------------
  /**
   * 初期読込処理.
   */
  @RequestMapping(value = "login", method = RequestMethod.GET)
  public String index(Principal principal) {

    return "login";
  }

  @RequestMapping(value = "index", method = RequestMethod.GET)
  String index(Model model) {
    return "index";
  }
}
