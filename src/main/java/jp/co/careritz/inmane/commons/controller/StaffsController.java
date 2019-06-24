package jp.co.careritz.inmane.commons.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import jp.co.careritz.inmane.commons.controller.common.AbstractAppController;

/**
 * スタッフ管理コントローラ.
 */
@Controller
public class StaffsController extends AbstractAppController {

  // ----------------------------------------------------------------------
  // 定数
  // ----------------------------------------------------------------------
  // テンプレート配置先パス
  final static String TEMPLATES_PATH = "commons/staffs/";

  @RequestMapping(value = "staffs", method = RequestMethod.GET)
  String showStaffList(Model model) {
    return TEMPLATES_PATH + "staffs_search";
  }
}
