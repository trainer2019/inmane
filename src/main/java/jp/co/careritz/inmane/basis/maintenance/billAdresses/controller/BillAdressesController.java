package jp.co.careritz.inmane.basis.maintenance.billAdresses.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 取引先管理コントローラ.
 */
@Controller
public class BillAdressesController {
  // ----------------------------------------------------------------------
  // 定数
  // ----------------------------------------------------------------------
  // テンプレート配置先パス
  final static String TEMPLATES_PATH = "basis/billAdresses/";

  @RequestMapping(value = "maintenance/bill_adresses", method = RequestMethod.GET)
  String showStaffList(Model model) {
    return TEMPLATES_PATH + "bill_adresses_search";
  }
}
