package jp.co.careritz.inmane.basis.maintenance.billaddresses.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import jp.co.careritz.inmane.basis.common.AbstractAppController;

/**
 * 取引先管理コントローラ.
 */
@Controller
public class BillAddressesController extends AbstractAppController {
  // ----------------------------------------------------------------------
  // 定数
  // ----------------------------------------------------------------------
  // テンプレート配置先パス
  final static String TEMPLATES_PATH = "basis/billaddresses/";

  @RequestMapping(value = "maintenance/bill_addresses", method = RequestMethod.GET)
  String showStaffList(Model model) {
    return TEMPLATES_PATH + "search";
  }
}
