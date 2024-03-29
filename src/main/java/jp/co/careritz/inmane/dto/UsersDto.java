package jp.co.careritz.inmane.dto;

import java.io.Serializable;
import java.sql.Date;

/**
 * ユーザ情報DTO.
 */
public class UsersDto implements Serializable {

  /** シリアルバージョンUID. */
  private static final long serialVersionUID = 1L;

  /** ユーザID. */
  private String userId;
  /** パスワード. */
  private String password;
  /** ユーザ名. */
  private String userName;
  /** ロール名. */
  private String roleName;
  /** ログイン失敗回数. */
  private int loginFailureCount;
  /** ログイン拒否時間. */
  private Date loginDeniedAt;
  /** 削除済フラグ. */
  private int isInvalid;
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

  public int getIsInvalid() {
    return isInvalid;
  }

  public void setIsInvalid(int isInvalid) {
    this.isInvalid = isInvalid;
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
