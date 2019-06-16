package jp.co.careritz.inmane.proto.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import jp.co.careritz.inmane.proto.dto.AssignmentsDto;
import jp.co.careritz.inmane.util.DataSourceProperty;

/**
 * 稼働状況情報DAO.
 */
@Repository
public class AssignmentsDao {

  @Autowired
  DataSourceProperty dsProp;

  /**
   * 稼働状況情報を取得する.
   * 
   * @return 稼働状況情報を格納したリスト
   */
  public List<AssignmentsDto> select() {
    // DBの接続情報をプロパティから取得
    String driverName = dsProp.getDriverName();
    String jdbcUrl = dsProp.getJdbcUrl();
    String dsUserName = dsProp.getDsUserName();
    String dsPassword = dsProp.getDsPassword();

    Connection con = null;
    ResultSet rs = null;
    PreparedStatement ps = null;

    // 取得結果を格納するDto
    List<AssignmentsDto> assignments = new ArrayList<>();
    try {
      StringBuilder sqlBuilder = new StringBuilder();
      sqlBuilder.append("SELECT ");
      sqlBuilder.append("  S.STAFF_NO ");
      sqlBuilder.append("  , S.STAFF_NAME ");
      sqlBuilder.append("  , S.JOINED_DATE ");
      sqlBuilder.append("  , C.CONTRACT_ID ");
      sqlBuilder.append("  , CT.SUB_NO ");
      sqlBuilder.append("  , CL.CLIENT_NAME ");
      sqlBuilder.append("  , CL.SALES_STAFF_NO ");
      sqlBuilder.append("  , SS.STAFF_NAME AS SALES_STAFF_NAME ");
      sqlBuilder.append("  , P.PROJECT_NAME ");
      sqlBuilder.append("  , CT.CONTRACT_PERIOD_FROM ");
      sqlBuilder.append("  , CT.CONTRACT_PERIOD_TO ");
      sqlBuilder.append("  , CT.CONTRACT_TYPE ");
      sqlBuilder.append("FROM ");
      sqlBuilder.append("  STAFFS S  ");
      sqlBuilder.append("  LEFT OUTER JOIN CONTRACTS C  ");
      sqlBuilder.append("    ON S.STAFF_NO = C.STAFF_NO  ");
      sqlBuilder.append("    AND C.DELETED = 0  ");
      sqlBuilder.append("  LEFT OUTER JOIN CONTRACTS_TRAN CT  ");
      sqlBuilder.append("    ON C.CONTRACT_ID = CT.CONTRACT_ID  ");
      sqlBuilder.append("    AND CT.DELETED = 0  ");
      sqlBuilder.append("  LEFT OUTER JOIN PROJECTS P  ");
      sqlBuilder.append("    ON C.PROJECT_ID = P.PROJECT_ID  ");
      sqlBuilder.append("    AND P.DELETED = 0  ");
      sqlBuilder.append("  LEFT OUTER JOIN CLIENTS CL  ");
      sqlBuilder.append("    ON P.CLIENT_ID = CL.CLIENT_ID  ");
      sqlBuilder.append("    AND CL.DELETED = 0  ");
      sqlBuilder.append("    LEFT OUTER JOIN STAFFS SS  ");
      sqlBuilder.append("    ON CL.SALES_STAFF_NO = SS.STAFF_NO  ");
      sqlBuilder.append("    AND SS.STAFF_TYPE = 'SS'  ");
      sqlBuilder.append("    AND SS.DELETED = 0  ");
      sqlBuilder.append("WHERE ");
      sqlBuilder.append("  S.STAFF_TYPE IN ('EE', 'EO', 'EF')  ");
      sqlBuilder.append("  AND NOT EXISTS (  ");
      sqlBuilder.append("    SELECT 1 FROM CONTRACTS_TRAN CT2  ");
      sqlBuilder.append("    WHERE ");
      sqlBuilder.append("      CT.CONTRACT_ID = CT2.CONTRACT_ID  ");
      sqlBuilder.append("      AND CT.SUB_NO < CT2.SUB_NO  ");
      sqlBuilder.append("      AND CT2.DELETED = 0 ");
      sqlBuilder.append("  )  ");
      sqlBuilder.append("  AND S.DELETED = 0 ");
      sqlBuilder.append("ORDER BY S.STAFF_NO");

      // JDBCドライバのロード
      Class.forName(driverName);
      // DBとの接続を行う
      con = DriverManager.getConnection(jdbcUrl, dsUserName, dsPassword);

      ps = con.prepareStatement(sqlBuilder.toString());
      // SQLを実行して取得結果をリザルトセットに格納
      rs = ps.executeQuery();

      // リザルトセットから1レコードずつデータを取り出す
      while (rs.next()) {
        // 取得結果を格納するDtoをインスタンス化
        AssignmentsDto resultDto = new AssignmentsDto();
        // Dtoに取得結果を格納
        resultDto.setStaffNo(rs.getString("STAFF_NO"));
        resultDto.setStaffName(rs.getString("STAFF_NAME"));
        resultDto.setJoinedDate(rs.getDate("JOINED_DATE"));
        // resultDto.setContractId(rs.getString("CONTRACT_ID"));
        // resultDto.setSubNo(rs.getString("SUB_NO"));
        resultDto.setClientName(rs.getString("CLIENT_NAME"));
        resultDto.setSalesStaffName(rs.getString("SALES_STAFF_NAME"));
        resultDto.setProjectName(rs.getString("PROJECT_NAME"));
        resultDto.setContractPeriodFrom(rs.getDate("CONTRACT_PERIOD_FROM"));
        resultDto.setContractPeriodTo(rs.getDate("CONTRACT_PERIOD_TO"));
        // resultDto.setContractType(rs.getString("CONTRACT_TYPE"));
        assignments.add(resultDto);
      }
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null) {
          rs.close();
          rs = null;
        }
        if (ps != null) {
          ps.close();
          ps = null;
        }
        if (con != null) {
          con.close();
          con = null;
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    // 呼び出し元に取得結果を返却
    return assignments;
  }

}
