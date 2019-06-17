package jp.co.careritz.inmane.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jp.co.careritz.inmane.config.PropertyConfig;
import jp.co.careritz.inmane.constant.AppConst;
import jp.co.careritz.inmane.controller.commons.AbstractAppController;
import jp.co.careritz.inmane.dto.UsersDto;
import jp.co.careritz.inmane.form.UsersCreateForm;
import jp.co.careritz.inmane.form.UsersSearchForm;
import jp.co.careritz.inmane.model.security.SecurityUserModel;
import jp.co.careritz.inmane.service.UsersService;

/**
 * ユーザ管理コントローラ.
 */
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
  // ロガー
  static Logger log = LoggerFactory.getLogger(UsersController.class);
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
  public String viewSearch(Model model, @ModelAttribute @Valid UsersSearchForm form,
      BindingResult bindingResult) {
    if (log.isTraceEnabled()) {
      log.debug("userId:" + form.getUserId());
      log.debug("userName:" + form.getUserName());
      log.debug("roleName:" + form.getRoleName());
      log.debug("nonDeleted:" + form.getNonDeleted());
    }

    if (bindingResult.hasErrors()) {
      model.addAttribute("UsersSearchForm", form);
      model.addAttribute("usersList", new ArrayList<UsersDto>());
      return "users_search";
    }

    UsersDto dto = new UsersDto();

    dto.setUserId(form.getUserId());
    dto.setUserName(form.getUserName());
    dto.setRoleName(AppConst.ROLE_NAME_ALL.equals(form.getRoleName()) ? "" : form.getRoleName());

    List<UsersDto> usersList =
        usersService.find(dto, AppConst.NON_DELETED_ON.equals(form.getNonDeleted()));

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
    form.setRoleName(AppConst.ROLE_NAME_USER);

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

    if (result == 0) {
      String msg = propertyConfig.get("ok.app.complete");
      // 画面に表示するエラーメッセージを設定
      model.addAttribute("appCompleteMessage", msg);
    } else {
      String errMsg = (result == 1) ? propertyConfig.get("error.app.user.deplicated")
          : propertyConfig.get("error.app.fatal");
      // エラーメッセージを設定
      model.addAttribute("createFailureMessage", errMsg);
      // 入力内容を再表示するためフォームを再設定
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
    dto.setDeleted(form.getDeleted());
    dto.setUpdaterId(userDetails.getUserId());
    dto.setUpdatedAt(now);

    int result = usersService.updateByPk(dto);

    if (result == 0) {
      String msg = propertyConfig.get("ok.app.complete");
      // 画面に表示するエラーメッセージを設定
      model.addAttribute("appCompleteMessage", msg);
    } else {
      String errMsg = (result == 1) ? propertyConfig.get("error.app.user.deplicated")
          : propertyConfig.get("error.app.fatal");
      // エラーメッセージを設定
      model.addAttribute("createFailureMessage", errMsg);
      // 入力内容を再表示するためフォームを再設定
      model.addAttribute("usersCreateForm", form);

      return "users_create";
    }
    return "redirect:/maintenance/users/detail?userId=" + dto.getUserId();
  }
}
