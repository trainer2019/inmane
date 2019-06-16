package jp.co.careritz.inmane.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import jp.co.careritz.inmane.controller.commons.AbstractAppController;

/**
 * 案件管理コントローラ.
 */
@Controller
public class ProjectsController extends AbstractAppController {
  @RequestMapping(value = "projects", method = RequestMethod.GET)
  String showStaffList(Model model) {
    return "projects_search";
  }
}
