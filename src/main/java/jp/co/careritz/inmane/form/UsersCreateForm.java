package jp.co.careritz.inmane.form;

import java.sql.Date;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UsersCreateForm {

  /** ユーザID. */
  @NotEmpty(message = "必須入力の項目です。")
  @Pattern(regexp = "[a-zA-Z0-9]*", message = "半角英数字である必要があります。")
  private String userId;
  /** パスワード. */
  @Pattern(regexp = "[a-zA-Z0-9]*", message = "半角英数字である必要があります。")
  private String password;
  /** ユーザ名. */
  @Size(min = 1, max = 20, message = "{min}文字以上{max}文字以下で入力してください。")
  private String userName;
  /** ロール名. */
  @NotEmpty(message = "必須入力の項目です。")
  @Pattern(regexp = "USER|ADMIN")
  private String roleName;
  /** ログイン失敗回数. */
  private int loginFailureCount;
  /** ログイン拒否時間. */
  private Date loginDeniedAt;
  /** 削除済フラグ. */
  private int deleted;
  /** 更新者ID. */
  private String updaterId;
  /** 更新日時. */
  private Date updatedAt;
  /** 作成者ID. */
  private String createrId;
  /** 作成日時. */
  private Date createdAt;

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

  public Date getLoginDeniedAt() {
    return loginDeniedAt;
  }

  public void setLoginDeniedAt(Date loginDeniedAt) {
    this.loginDeniedAt = loginDeniedAt;
  }

  public int getDeleted() {
    return deleted;
  }

  public void setDeleted(int deleted) {
    this.deleted = deleted;
  }

  public String getUpdaterId() {
    return updaterId;
  }

  public void setUpdaterId(String updaterId) {
    this.updaterId = updaterId;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public String getCreaterId() {
    return createrId;
  }

  public void setCreaterId(String createrId) {
    this.createrId = createrId;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

}
