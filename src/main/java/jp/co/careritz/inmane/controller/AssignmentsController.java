package jp.co.careritz.inmane.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import jp.co.careritz.inmane.controller.commons.AbstractAppController;

/**
 * 稼働状況情報コントローラ.
 */
@Controller
public class AssignmentsController extends AbstractAppController {
  @RequestMapping(value = "assignments", method = RequestMethod.GET)
  String showStaffList(Model model) {
    return "assignments_search";
  }
}
