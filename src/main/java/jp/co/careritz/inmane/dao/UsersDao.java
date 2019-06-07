package jp.co.careritz.inmane.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import jp.co.careritz.inmane.common.dao.BaseDao;
import jp.co.careritz.inmane.dto.UsersDto;

@Repository
public class UsersDao extends BaseDao {

  /**
   * ユーザを取得する.
   * 
   * @param userId
   *
   * @return ユーザを格納したDTO
   */
  public UsersDto selectOne(String userId) {
    // DBの接続情報をプロパティから取得
    String driverName = this.getDriverName();
    String jdbcUrl = this.getJdbcUrl();
    String dsUserName = this.getDsUserName();
    String dsPassword = this.getDsPassword();

    Connection con = null;
    ResultSet rs = null;
    PreparedStatement ps = null;

    // 取得結果を格納するDto
    UsersDto dto = null;

    try {
      System.out.println("### driverName:" + driverName);
      System.out.println("### jdbcUrl:" + jdbcUrl);
      System.out.println("### dsUserName:" + dsUserName);
      System.out.println("### dsPassword:" + dsPassword);

      StringBuilder sqlBuilder = new StringBuilder();
      sqlBuilder.append("SELECT * ");
      sqlBuilder.append("FROM USERS ");
      sqlBuilder.append("WHERE USER_ID LIKE ? ");

      System.out.println("### SQL:" + sqlBuilder.toString());

      // JDBCドライバのロード
      Class.forName(driverName);
      // DBとの接続を行う
      con = DriverManager.getConnection(jdbcUrl, dsUserName, dsPassword);

      ps = con.prepareStatement(sqlBuilder.toString());

      // バインド
      ps.setString(1, userId);

      // SQLを実行して取得結果をリザルトセットに格納
      rs = ps.executeQuery();

      // リザルトセットから1レコードずつデータを取り出す
      if (rs.next()) {
        // 取得結果を格納するDtoをインスタンス化
        dto = new UsersDto();
        // Dtoに取得結果を格納
        dto.setUserId(rs.getString("USER_ID"));
        dto.setPassword(rs.getString("PASSWORD"));
        dto.setUserName(rs.getString("USER_NAME"));
        dto.setRoleName(rs.getString("ROLE_NAME"));
        dto.setLoginFailureCount(rs.getInt("LOGIN_FAILURE_COUNT"));
        dto.setLoginDeniedAt(rs.getDate("LOGIN_DENIED_AT"));
        dto.setDeleted(rs.getInt("DELETED"));
        dto.setUpdaterId(rs.getString("UPDATER_ID"));
        dto.setUpdatedAt(rs.getDate("UPDATED_AT"));
        dto.setCreaterId(rs.getString("CREATER_ID"));
        dto.setCreatedAt(rs.getDate("CREATED_AT"));
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
    return dto;
  }


  /**
   * ユーザを取得する.
   * 
   * @param dto UsersDto
   * @param nonDeleted 削除済を除外フラグ
   * 
   * @return ユーザを格納したリスト
   */
  public List<UsersDto> select(UsersDto dto, boolean nonDeleted) {
    // DBの接続情報をプロパティから取得
    String driverName = this.getDriverName();
    String jdbcUrl = this.getJdbcUrl();
    String dsUserName = this.getDsUserName();
    String dsPassword = this.getDsPassword();

    Connection con = null;
    ResultSet rs = null;
    PreparedStatement ps = null;

    // 取得結果を格納するDto
    List<UsersDto> users = new ArrayList<>();
    try {
      StringBuilder sqlBuilder = new StringBuilder();
      sqlBuilder.append("SELECT * ");
      sqlBuilder.append("FROM USERS ");
      sqlBuilder.append("WHERE USER_ID    LIKE ? ");
      sqlBuilder.append("AND   USER_NAME  LIKE ? ");
      sqlBuilder.append("AND   ROLE_NAME LIKE ? ");
      // 削除済を除外フラグがある場合の条件を設定
      if (nonDeleted) {
        sqlBuilder.append("AND   DELETED = 0 ");
      }
      sqlBuilder.append("ORDER BY USER_ID ");

      System.out.println("### SQL:" + sqlBuilder.toString());

      // JDBCドライバのロード
      Class.forName(driverName);
      // DBとの接続を行う
      con = DriverManager.getConnection(jdbcUrl, dsUserName, dsPassword);

      ps = con.prepareStatement(sqlBuilder.toString());

      // nullの場合はLike '%'で検索(ユーザーID、ユーザ名は前方一致)
      ps.setString(1,
          dto.getUserId() == null || dto.getUserId().equals("") ? "%" : dto.getUserId() + "%");
      ps.setString(2, dto.getUserName() == null || dto.getUserName().equals("") ? "%"
          : dto.getUserName() + "%");
      ps.setString(3,
          dto.getRoleName() == null || dto.getRoleName().equals("") ? "%" : dto.getRoleName());

      // SQLを実行して取得結果をリザルトセットに格納
      rs = ps.executeQuery();

      // リザルトセットから1レコードずつデータを取り出す
      while (rs.next()) {
        // 取得結果を格納するDtoをインスタンス化
        UsersDto resultDto = new UsersDto();
        // Dtoに取得結果を格納
        resultDto.setUserId(rs.getString("USER_ID"));
        resultDto.setPassword(rs.getString("PASSWORD"));
        resultDto.setUserName(rs.getString("USER_NAME"));
        resultDto.setRoleName(rs.getString("ROLE_NAME"));
        resultDto.setLoginFailureCount(rs.getInt("LOGIN_FAILURE_COUNT"));
        resultDto.setLoginDeniedAt(rs.getDate("LOGIN_DENIED_AT"));
        resultDto.setDeleted(rs.getInt("DELETED"));
        resultDto.setUpdaterId(rs.getString("UPDATER_ID"));
        resultDto.setUpdatedAt(rs.getDate("UPDATED_AT"));
        resultDto.setCreaterId(rs.getString("CREATER_ID"));
        resultDto.setCreatedAt(rs.getDate("CREATED_AT"));

        users.add(resultDto);
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
    return users;
  }

  /**
   * ユーザを更新する.
   * 
   * @param userId
   *
   * @return ユーザを格納したDTO
   */
  public int update(UsersDto dto) {
    String driverName = this.getDriverName();
    String jdbcUrl = this.getJdbcUrl();
    String dsUserName = this.getDsUserName();
    String dsPassword = this.getDsPassword();

    Connection con = null;
    PreparedStatement ps = null;

    // DBとの接続を行う
    try {
      StringBuilder sqlBuilder = new StringBuilder();
      sqlBuilder.append("UPDATE USERS ");
      sqlBuilder.append("SET ");
      // パスワード再設定時以外は更新しない
      if (dto.getPassword() != null && !dto.getPassword().equals("")) {
        sqlBuilder.append("PASSWORD = ? ,");
      }
      if (dto.getUserName() != null && !dto.getUserName().equals("")) {
        sqlBuilder.append("USER_NAME = ? ,");
      }
      if (dto.getRoleName() != null && !dto.getRoleName().equals("")) {
        sqlBuilder.append("ROLE_NAME = ? ,");
      }

      sqlBuilder.append("LOGIN_FAILURE_COUNT = ? ,");

      if (dto.getLoginDeniedAt() != null) {
        sqlBuilder.append("LOGIN_DENIED_AT = ? ,");
      }
      sqlBuilder.append("DELETED = ? ,");
      sqlBuilder.append("UPDATER_ID = ? ,");
      sqlBuilder.append("UPDATED_AT = ? ");
      sqlBuilder.append("WHERE USER_ID = ? ");

      System.out.println("### SQL:" + sqlBuilder.toString());

      // JDBCドライバのロード
      Class.forName(driverName);
      // DBとの接続を行う
      con = DriverManager.getConnection(jdbcUrl, dsUserName, dsPassword);

      ps = con.prepareStatement(sqlBuilder.toString());

      // 各項目値をバインド
      int idx = 1;
      // パスワード再設定時以外は設定しない
      if (dto.getPassword() != null && !dto.getPassword().equals("")) {
        System.out.println("### pram" + idx + ":" + dto.getPassword());
        ps.setString(idx++, dto.getPassword());
      }
      if (dto.getUserName() != null && !dto.getUserName().equals("")) {
        System.out.println("### pram" + idx + ":" + dto.getUserName());
        ps.setString(idx++, dto.getUserName());
      }
      if (dto.getRoleName() != null && !dto.getRoleName().equals("")) {
        System.out.println("### pram" + idx + ":" + dto.getRoleName());
        ps.setString(idx++, dto.getRoleName());
      }
      System.out.println("### pram" + idx + ":" + dto.getLoginFailureCount());
      ps.setInt(idx++, dto.getLoginFailureCount());
      if (dto.getLoginDeniedAt() != null) {
        System.out.println("### pram" + idx + ":" + dto.getLoginDeniedAt());
        ps.setDate(idx++, dto.getLoginDeniedAt());
      }
      System.out.println("### pram" + idx + ":" + dto.getDeleted());
      ps.setInt(idx++, dto.getDeleted());
      System.out.println("### pram" + idx + ":" + dto.getUpdaterId());
      ps.setString(idx++, dto.getUpdaterId());
      System.out.println("### pram" + idx + ":" + dto.getUpdatedAt());
      ps.setDate(idx++, dto.getUpdatedAt());
      System.out.println("### pram" + idx + ":" + dto.getUserId());
      ps.setString(idx, dto.getUserId());

      // SQLを実行
      ps.executeUpdate();

    } catch (SQLException e) {
      int errCode = e.getErrorCode();
      e.printStackTrace();
      System.out.println("### SQLException#getErrorCode = " + errCode);
      if (errCode == 1) {
        System.out.println("### 一意制約エラー");
        return 1;
      } else {
        return 9;
      }
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      return 9;
    } finally {
      try {
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
    return 0;
  }

  /**
   * ユーザを登録する.
   * 
   * @param userId
   *
   * @return ユーザを格納したDTO
   */
  public int create(UsersDto dto) {
    String driverName = this.getDriverName();
    String jdbcUrl = this.getJdbcUrl();
    String dsUserName = this.getDsUserName();
    String dsPassword = this.getDsPassword();

    Connection con = null;
    PreparedStatement ps = null;

    // DBとの接続を行う
    try {
      StringBuilder sqlBuilder = new StringBuilder();
      sqlBuilder.append("INSERT INTO USERS (");
      sqlBuilder.append(" USER_ID ");
      sqlBuilder.append(",PASSWORD ");
      sqlBuilder.append(",USER_NAME ");
      sqlBuilder.append(",ROLE_NAME ");
      sqlBuilder.append(",LOGIN_FAILURE_COUNT ");
      sqlBuilder.append(",LOGIN_DENIED_AT ");
      sqlBuilder.append(",DELETED ");
      sqlBuilder.append(",UPDATER_ID ");
      sqlBuilder.append(",UPDATED_AT ");
      sqlBuilder.append(",CREATER_ID ");
      sqlBuilder.append(",CREATED_AT ");
      sqlBuilder.append(") VALUES (");
      sqlBuilder.append(" ? ");
      sqlBuilder.append(",? ");
      sqlBuilder.append(",? ");
      sqlBuilder.append(",? ");
      sqlBuilder.append(",? ");
      sqlBuilder.append(",? ");
      sqlBuilder.append(",? ");
      sqlBuilder.append(",? ");
      sqlBuilder.append(",? ");
      sqlBuilder.append(",? ");
      sqlBuilder.append(",? ");
      sqlBuilder.append(") ");


      System.out.println("### SQL:" + sqlBuilder.toString());

      // JDBCドライバのロード
      Class.forName(driverName);
      // DBとの接続を行う
      con = DriverManager.getConnection(jdbcUrl, dsUserName, dsPassword);

      ps = con.prepareStatement(sqlBuilder.toString());

      // 各項目値をバインド
      ps.setString(1, dto.getUserId());
      ps.setString(2, dto.getPassword());
      ps.setString(3, dto.getUserName());
      ps.setString(4, dto.getRoleName());
      ps.setInt(5, 0);
      ps.setDate(6, null);
      ps.setInt(7, 0);
      ps.setString(8, null);
      ps.setDate(9, null);
      ps.setString(10, dto.getCreaterId());
      ps.setDate(11, dto.getCreatedAt());

      // SQLを実行
      ps.executeUpdate();

    } catch (SQLException e) {
      int errCode = e.getErrorCode();
      e.printStackTrace();
      System.out.println("SQLException#getErrorCode = " + errCode);
      if (errCode == 1) {
        System.out.println("一意制約エラー");
        return 1;
      } else {
        return 9;
      }

    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      return 9;
    } finally {
      try {
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
    return 0;
  }



}
