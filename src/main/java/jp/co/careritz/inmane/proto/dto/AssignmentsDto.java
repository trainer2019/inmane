package jp.co.careritz.inmane.proto.dto;

import java.sql.Date;

public class AssignmentsDto {

  /** シリアルバージョンUID. */
  @SuppressWarnings("unused")
  private static final long serialVersionUID = 1L;

  /** 社員No.. */
  private String staffNo;
  /** 社員No.. */
  private String staffName;
  /** 入社年月日. */
  private Date joinedDate;
  /** 客先. */
  private String clientName;
  /** 案件名. */
  private String projectName;
  /** 契約期間From. */
  private Date contractPeriodFrom;
  /** 契約期間To. */
  private Date contractPeriodTo;
  /** 取引先担当. */
  private String salesStaffName;
  
  public String getStaffNo() {
    return staffNo;
  }
  
  public void setStaffNo(String staffNo) {
    this.staffNo = staffNo;
  }
  
  public String getStaffName() {
    return staffName;
  }
  
  public void setStaffName(String staffName) {
    this.staffName = staffName;
  }
  
  public Date getJoinedDate() {
    return joinedDate;
  }
  
  public void setJoinedDate(Date joinedDate) {
    this.joinedDate = joinedDate;
  }
  
  public String getClientName() {
    return clientName;
  }
  
  public void setClientName(String clientName) {
    this.clientName = clientName;
  }
  
  public String getProjectName() {
    return projectName;
  }
  
  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }
  
  public Date getContractPeriodFrom() {
    return contractPeriodFrom;
  }
  
  public void setContractPeriodFrom(Date contractPeriodFrom) {
    this.contractPeriodFrom = contractPeriodFrom;
  }
  
  public Date getContractPeriodTo() {
    return contractPeriodTo;
  }
  
  public void setContractPeriodTo(Date contractPeriodTo) {
    this.contractPeriodTo = contractPeriodTo;
  }
  
  public String getSalesStaffName() {
    return salesStaffName;
  }
  
  public void setSalesStaffName(String salesStaffName) {
    this.salesStaffName = salesStaffName;
  }
}
