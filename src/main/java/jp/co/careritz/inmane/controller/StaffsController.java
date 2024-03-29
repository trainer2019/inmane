package jp.co.careritz.inmane.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import jp.co.careritz.inmane.controller.commons.AbstractAppController;

/**
 * スタッフ管理コントローラ.
 */
@Controller
public class StaffsController extends AbstractAppController {
  @RequestMapping(value = "staffs", method = RequestMethod.GET)
  String showStaffList(Model model) {
    return "staffs_search";
  }
}
