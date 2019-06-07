package jp.co.careritz.inmane.controller;

import java.sql.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.support.SessionFlashMapManager;
import jp.co.careritz.inmane.config.PropertyConfig;
import jp.co.careritz.inmane.controller.commons.AbstractAppController;
import jp.co.careritz.inmane.dto.UsersDto;
import jp.co.careritz.inmane.form.UsersCreateForm;
import jp.co.careritz.inmane.form.UsersSearchForm;
import jp.co.careritz.inmane.model.security.SecurityUserModel;
import jp.co.careritz.inmane.service.UsersService;

@Controller
@RequestMapping("maintenance/users")
public class UsersController extends AbstractAppController {

  // ----------------------------------------------------------------------
  // インスタンス変数
  // ----------------------------------------------------------------------
  @Autowired
  private PropertyConfig propertyConfig;
  @Autowired
  UsersService usersService;

  // ----------------------------------------------------------------------
  // インスタンスメソッド
  // ----------------------------------------------------------------------

  /**
   * ユーザ検索画面を表示する.
   * 
   * @param userId
   *
   * @return ユーザ検索画面
   */
  @GetMapping("search")
  public String viewSearch(@ModelAttribute UsersSearchForm form, Model model) {
    System.out.println("### userId:" + form.getUserId());
    System.out.println("### userName:" + form.getUserName());
    System.out.println("### roleName:" + form.getRoleName());
    System.out.println("### nonDeleted:" + form.getNonDeleted());

    UsersDto dto = new UsersDto();

    dto.setUserId(form.getUserId());
    dto.setUserName(form.getUserName());
    dto.setRoleName("ALL".equals(form.getRoleName()) ? "" : form.getRoleName());

    List<UsersDto> usersList = usersService.find(dto, "on".equals(form.getNonDeleted()));

    // 検索条件を設定
    model.addAttribute("usersSearchForm", form);
    // 検索結果を設定
    model.addAttribute("usersList", usersList);

    return "users_search";
  }

  /**
   * ユーザ登録画面を表示する.
   * 
   * @param userId
   *
   * @return ユーザ作成画面
   */
  @GetMapping("new")
  public String viewCreate(Model model) {

    UsersCreateForm form = new UsersCreateForm();
    form.setRoleName("USER");

    model.addAttribute("usersCreateForm", form);

    return "users_create";
  }

  /**
   * ユーザ作成処理を実行する.
   * 
   * @param userId
   *
   * @return ユーザ登録画面
   */
  @PostMapping("new")
  public String create(Model model, @ModelAttribute @Valid UsersCreateForm form,
      BindingResult bindingResult, @AuthenticationPrincipal SecurityUserModel userDetails,
      RedirectAttributes redirectAttributes, HttpServletRequest req, HttpServletResponse res) {

    if (bindingResult.hasErrors()) {
      model.addAttribute("usersCreateForm", form);
      return "users_create";
    }

    final String prefix = propertyConfig.get("user.defaultpass.prefix");
    final String password = new BCryptPasswordEncoder().encode(prefix + form.getUserId());

    Date now = new Date(System.currentTimeMillis());
    UsersDto dto = new UsersDto();
    dto.setUserId(form.getUserId());
    dto.setPassword(password);
    dto.setUserName(form.getUserName());
    dto.setRoleName(form.getRoleName());
    dto.setDeleted(0);
    dto.setCreaterId(userDetails.getUserId());
    dto.setCreatedAt(now);

    int result = usersService.create(dto);

    FlashMap flashMap = RequestContextUtils.getOutputFlashMap(req);
    FlashMapManager flashMapManager = new SessionFlashMapManager();

    if (result == 0) {
      String msg = propertyConfig.get("ok.app.complete");
      // Flashスコープで画面に表示するエラーメッセージを設定
      flashMap.put("appCompleteMessage", msg);
      flashMapManager.saveOutputFlashMap(flashMap, req, res);
    } else {
      String errMsg = (result == 1) ? propertyConfig.get("error.app.user.deplicated")
          : propertyConfig.get("error.app.fatal");
      // Flashスコープで画面に表示するエラーメッセージを設定
      flashMap.put("createFailureMessage", errMsg);
      flashMapManager.saveOutputFlashMap(flashMap, req, res);

      model.addAttribute("usersCreateForm", form);
      return "users_create";
    }
    return "redirect:/maintenance/users/detail?userId=" + dto.getUserId();
  }

  /**
   * ユーザ詳細画面を表示する.
   * 
   * @param userId
   *
   * @return ユーザ詳細画面
   */
  @GetMapping("detail")
  public String viewDetail(Model model,
      @RequestParam(name = "userId", defaultValue = "") String userId) {

    if ("".equals(userId)) {
      return "redirect:/error/403";
    }

    UsersDto dto = usersService.findByPk(userId);

    if (dto == null) {
      return "redirect:/error/403";
    }
    UsersCreateForm form = new UsersCreateForm();
    form.setUserId(dto.getUserId());
    form.setUserName(dto.getUserName());
    form.setRoleName(dto.getRoleName());
    form.setLoginFailureCount(dto.getLoginFailureCount());
    form.setLoginDeniedAt(dto.getLoginDeniedAt());
    form.setDeleted(dto.getDeleted());
    form.setUpdaterId(dto.getUpdaterId());
    form.setUpdatedAt(dto.getUpdatedAt());
    form.setCreaterId(dto.getCreaterId());
    form.setCreatedAt(dto.getCreatedAt());

    model.addAttribute("usersCreateForm", form);

    return "users_create";
  }

  /**
   * ユーザ編集画面を表示する.
   * 
   * @param userId
   *
   * @return ユーザ編集画面
   */
  @GetMapping("edit")
  public String viewEdit(Model model,
      @RequestParam(name = "userId", defaultValue = "") String userId) {

    if ("".equals(userId)) {
      return "redirect:/error/403";
    }

    UsersDto dto = usersService.findByPk(userId);

    if (dto == null) {
      return "redirect:/error/403";
    }

    UsersCreateForm form = new UsersCreateForm();
    form.setUserId(dto.getUserId());
    form.setUserName(dto.getUserName());
    form.setRoleName(dto.getRoleName());
    form.setLoginFailureCount(dto.getLoginFailureCount());
    form.setLoginDeniedAt(dto.getLoginDeniedAt());
    form.setDeleted(dto.getDeleted());
    form.setUpdaterId(dto.getUpdaterId());
    form.setUpdatedAt(dto.getUpdatedAt());
    form.setCreaterId(dto.getCreaterId());
    form.setCreatedAt(dto.getCreatedAt());

    model.addAttribute("usersCreateForm", form);

    return "users_create";
  }

  /**
   * ユーザ更新処理を実行する.
   * 
   * @param userId
   *
   * @return ユーザ編集画面
   */
  @PostMapping("edit")
  public String update(Model model, @ModelAttribute @Valid UsersCreateForm form,
      BindingResult bindingResult, @AuthenticationPrincipal SecurityUserModel userDetails,
      RedirectAttributes redirectAttributes, HttpServletRequest req, HttpServletResponse res) {

    if (bindingResult.hasErrors()) {
      model.addAttribute("usersCreateForm", form);
      return "users_create";
    }

    final String password = new BCryptPasswordEncoder().encode(form.getPassword());

    Date now = new Date(System.currentTimeMillis());
    UsersDto dto = new UsersDto();
    dto.setUserId(form.getUserId());
    dto.setPassword(password);
    dto.setUserName(form.getUserName());
    dto.setRoleName(form.getRoleName());
    dto.setDeleted(0);
    dto.setUpdaterId(userDetails.getUserId());
    dto.setUpdatedAt(now);

    int result = usersService.updateByPk(dto);

    FlashMap flashMap = RequestContextUtils.getOutputFlashMap(req);
    FlashMapManager flashMapManager = new SessionFlashMapManager();

    if (result == 0) {
      String msg = propertyConfig.get("ok.app.complete");
      // Flashスコープで画面に表示するエラーメッセージを設定
      flashMap.put("appCompleteMessage", msg);
      flashMapManager.saveOutputFlashMap(flashMap, req, res);
    } else {
      String errMsg = (result == 1) ? propertyConfig.get("error.app.user.deplicated")
          : propertyConfig.get("error.app.fatal");
      // Flashスコープで画面に表示するエラーメッセージを設定
      flashMap.put("createFailureMessage", errMsg);
      flashMapManager.saveOutputFlashMap(flashMap, req, res);

      model.addAttribute("usersCreateForm", form);
      return "users_create";
    }
    return "redirect:/maintenance/users/detail?userId=" + dto.getUserId();
  }
}
