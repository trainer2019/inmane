package jp.co.careritz.inmane.basis.common.top.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.careritz.inmane.basis.common.AbstractAppController;

/**
 * 案件管理コントローラ.
 */
@Controller
public class TopController extends AbstractAppController {
  @RequestMapping(value = "top", method = RequestMethod.GET)
  String showStaffList(Model model) {
    return "top";
  }
}
