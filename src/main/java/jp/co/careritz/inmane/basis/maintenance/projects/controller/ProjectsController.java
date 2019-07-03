package jp.co.careritz.inmane.basis.maintenance.projects.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import jp.co.careritz.inmane.basis.common.AbstractAppController;

/**
 * 案件管理コントローラ.
 */
@Controller
public class ProjectsController extends AbstractAppController {
  // ----------------------------------------------------------------------
  // 定数
  // ----------------------------------------------------------------------
  // テンプレート配置先パス
  final static String TEMPLATES_PATH = "basis/projects/";

  @RequestMapping(value = "projects", method = RequestMethod.GET)
  String showStaffList(Model model) {
    return TEMPLATES_PATH + "projects_search";
  }
}
