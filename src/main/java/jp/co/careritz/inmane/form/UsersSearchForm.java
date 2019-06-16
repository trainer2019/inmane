package jp.co.careritz.inmane.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * ユーザ情報検索用フォーム.
 */
public class UsersSearchForm {

  /** ユーザID. */
  private String userId;
  /** ユーザ名. */
  private String userName;

  @NotEmpty(message = "必須入力の項目です。")
  @Pattern(regexp = "ALL|USER|ADMIN", message = "不正な値が設定されています。")
  private String roleName;
  /** 削除済を含む. */
  private String nonDeleted;

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

  public String getNonDeleted() {
    return nonDeleted;
  }

  public void setNonDeleted(String nonDeleted) {
    this.nonDeleted = nonDeleted;
  }

}
