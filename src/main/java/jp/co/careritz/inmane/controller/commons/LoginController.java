package jp.co.careritz.inmane.controller.commons;

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

    return "common/login";
  }

  @RequestMapping(value = "top", method = RequestMethod.GET)
  String top(Model model) {
    return "common/top";
  }
}
