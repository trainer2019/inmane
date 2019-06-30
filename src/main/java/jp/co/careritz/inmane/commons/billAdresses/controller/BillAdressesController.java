package jp.co.careritz.inmane.commons.billAdresses.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import jp.co.careritz.inmane.commons.users.controller.common.AbstractAppController;

/**
 * 取引先管理コントローラ.
 */
@Controller
public class BillAdressesController extends AbstractAppController {
  // ----------------------------------------------------------------------
  // 定数
  // ----------------------------------------------------------------------
  // テンプレート配置先パス
  final static String TEMPLATES_PATH = "commons/bill_adresses/";

  @RequestMapping(value = "bill_adresses", method = RequestMethod.GET)
  String showStaffList(Model model) {
    return TEMPLATES_PATH + "bill_adresses_search";
  }
}
