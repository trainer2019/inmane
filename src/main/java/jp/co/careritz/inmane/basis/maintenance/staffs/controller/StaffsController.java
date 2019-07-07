package jp.co.careritz.inmane.basis.maintenance.staffs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import jp.co.careritz.inmane.basis.common.AbstractAppController;

/**
 * スタッフ管理コントローラ.
 */
@Controller
public class StaffsController extends AbstractAppController {

  // ----------------------------------------------------------------------
  // 定数
  // ----------------------------------------------------------------------
  // テンプレート配置先パス
  final static String TEMPLATES_PATH = "basis/staffs/";

  @RequestMapping(value = "maintenance/staffs", method = RequestMethod.GET)
  String showStaffList(Model model) {
    return TEMPLATES_PATH + "search";
  }
}
