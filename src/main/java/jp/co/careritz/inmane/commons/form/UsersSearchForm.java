package jp.co.careritz.inmane.commons.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * ユーザ情報検索用フォーム.
 */
public class UsersSearchForm {

  /** ユーザID. */
  @Size(max = 10, message = "{errors.validation.MaxSize.message}")
  @Pattern(regexp = "[a-zA-Z0-9]*", message = "{errors.validation.HalfWidthAlphanumeric.message}")
  private String userId;
  /** ユーザ名. */
  @Size(max = 20, message = "{errors.validation.MaxSize.message}")
  private String userName;
  /** ロール名. */
  @NotEmpty(message = "{errors.validation.Required.message}")
  @Pattern(regexp = "ALL|USER|ADMIN", message = "{errors.validation.IllegalData.message}")
  private String roleName;
  /** 削除済を含む. */
  @Pattern(regexp = "on", message = "{errors.validation.IllegalData.message}")
  private String nonIsInvalid;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
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

  public String getNonIsInvalid() {
    return nonIsInvalid;
  }

  public void setNonIsInvalid(String nonIsInvalid) {
    this.nonIsInvalid = nonIsInvalid;
  }

}
