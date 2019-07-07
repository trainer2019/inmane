package jp.co.careritz.inmane.businesses.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * 稼働状況情報コントローラ.
 */
@Controller
public class AssignmentsController {
  @RequestMapping(value = "assignments", method = RequestMethod.GET)
  String showStaffList(Model model) {
    return "assignments/search";
  }
}
