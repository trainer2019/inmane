package jp.co.careritz.inmane.basis.common.bulletinBoard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 案件管理コントローラ.
 */
@Controller
public class BulletinBoardController {
  @RequestMapping(value = "home", method = RequestMethod.GET)
  String showStaffList(Model model) {
    return "home";
  }
}
