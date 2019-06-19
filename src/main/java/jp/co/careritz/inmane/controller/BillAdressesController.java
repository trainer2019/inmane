package jp.co.careritz.inmane.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import jp.co.careritz.inmane.controller.commons.AbstractAppController;

/**
 * 取引先管理コントローラ.
 */
@Controller
public class BillAdressesController extends AbstractAppController {
  @RequestMapping(value = "bill_adresses", method = RequestMethod.GET)
  String showStaffList(Model model) {
    return "bill_adresses_search";
  }
}
