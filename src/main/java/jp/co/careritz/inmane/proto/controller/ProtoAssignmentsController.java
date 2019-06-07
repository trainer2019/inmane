package jp.co.careritz.inmane.proto.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import jp.co.careritz.inmane.controller.commons.AbstractAppController;
import jp.co.careritz.inmane.proto.dto.AssignmentsDto;
import jp.co.careritz.inmane.proto.service.AssignmentsService;

@Controller
public class ProtoAssignmentsController extends AbstractAppController {
  
  @Autowired
  AssignmentsService assignmentsService;
  
  @RequestMapping(value = "proto/assignments", method = RequestMethod.GET)
  String showProtoStaffList(Model model) {
    //    System.out.println("### userId:" + form.getUserId());
    //    System.out.println("### userName:" + form.getUserName());
    //    System.out.println("### roleName:" + form.getRoleName());
    //    System.out.println("### nonDeleted:" + form.getNonDeleted());
    
    //    AssignmentsDto dto = new AssignmentsDto();
    //
    //    dto.setUserId(form.getUserId());
    //    dto.setUserName(form.getUserName());
    //    dto.setRoleName("ALL".equals(form.getRoleName()) ? "" : form.getRoleName());

    List<AssignmentsDto> assignments = assignmentsService.find();

    // 検索結果を設定
    model.addAttribute("assignmentsList", assignments);
    return "proto/assignments_search";
  }
}
