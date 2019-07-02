package jp.co.careritz.inmane.basis.maintenance.users.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import jp.co.careritz.inmane.basis.common.validator.SizeIfNotNull;

/**
 * ユーザ情報登録用フォーム.
 */
public class UsersCreateForm {

  /** ユーザID. */
  @NotEmpty(message = "{errors.validation.Required.message}")
  @Size(min = 5, max = 10, message = "{errors.validation.SizeRange.message}")
  @Pattern(regexp = "[a-zA-Z0-9]*", message = "{errors.validation.HalfWidthAlphanumeric.message}")
  private String userId;
  /** パスワード. */
  @Pattern(regexp = "[a-zA-Z0-9]*", message = "{errors.validation.HalfWidthAlphanumeric.message}")
  @SizeIfNotNull(min = 4, max = 10, message = "{errors.validation.SizeRange.message}")
  private String password;
  /** ユーザ名. */
  @Size(min = 1, max = 20, message = "{errors.validation.SizeRange.message}")
  private String userName;
  /** ロール名. */
  @NotEmpty(message = "{errors.validation.Required.message}")
  @Pattern(regexp = "USER|ADMIN", message = "{errors.validation.IllegalData.message}")
  private String roleName;
  /** ログイン失敗回数. */
  private int loginFailureCount;
  /** ログイン拒否時間. */
  private String loginDeniedAt;
  /** 削除済フラグ. */
  @Pattern(regexp = "[0|1]", message = "{errors.validation.IllegalData.message}")
  private String isInvalid;
  /** 更新者ID. */
  private String updaterId;
  /** 更新日時. */
  private String updatedAt;
  /** 作成者ID. */
  private String createrId;
  /** 作成日時. */
  private String createdAt;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  public int getLoginFailureCount() {
    return loginFailureCount;
  }

  public void setLoginFailureCount(int loginFailureCount) {
    this.loginFailureCount = loginFailureCount;
  }

  public String getLoginDeniedAt() {
    return loginDeniedAt;
  }

  public void setLoginDeniedAt(String loginDeniedAt) {
    this.loginDeniedAt = loginDeniedAt;
  }

  public String getIsInvalid() {
    return isInvalid;
  }

  public void setIsInvalid(String isInvalid) {
    this.isInvalid = isInvalid;
  }

  public String getUpdaterId() {
    return updaterId;
  }

  public void setUpdaterId(String updaterId) {
    this.updaterId = updaterId;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  public String getCreaterId() {
    return createrId;
  }

  public void setCreaterId(String createrId) {
    this.createrId = createrId;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

}
