package jp.co.careritz.inmane.basis.maintenance.clients.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 取引先管理コントローラ.
 */
@Controller
public class ClientsController {
  // ----------------------------------------------------------------------
  // 定数
  // ----------------------------------------------------------------------
  // テンプレート配置先パス
  final static String TEMPLATES_PATH = "basis/clients/";

  @RequestMapping(value = "maintenance/clients", method = RequestMethod.GET)
  String showStaffList(Model model) {
    return TEMPLATES_PATH + "clients_search";
  }
}
