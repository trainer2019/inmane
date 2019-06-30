package jp.co.careritz.inmane.commons.clients.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import jp.co.careritz.inmane.commons.users.controller.common.AbstractAppController;

/**
 * 取引先管理コントローラ.
 */
@Controller
public class ClientsController extends AbstractAppController {
  // ----------------------------------------------------------------------
  // 定数
  // ----------------------------------------------------------------------
  // テンプレート配置先パス
  final static String TEMPLATES_PATH = "commons/clients/";

  @RequestMapping(value = "clients", method = RequestMethod.GET)
  String showStaffList(Model model) {
    return TEMPLATES_PATH + "clients_search";
  }
}
