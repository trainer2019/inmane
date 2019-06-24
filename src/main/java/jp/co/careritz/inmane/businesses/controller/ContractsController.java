package jp.co.careritz.inmane.businesses.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import jp.co.careritz.inmane.commons.controller.common.AbstractAppController;

/**
 * 契約管理コントローラ.
 */
@Controller
public class ContractsController extends AbstractAppController {
  @RequestMapping(value = "contracts", method = RequestMethod.GET)
  String showContractsList(Model model) {
    return "contracts_search";
  }
}
