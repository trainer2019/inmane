package jp.co.careritz.inmane.dao;

import static jp.co.careritz.inmane.util.AppUtil.isEmptyStr;
import static jp.co.careritz.inmane.util.AppUtil.isNotEmptyStr;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;
import jp.co.careritz.inmane.config.PropertyConfig;
import jp.co.careritz.inmane.dto.UsersDto;
import jp.co.careritz.inmane.service.UsersService;

/**
 * ユーザ情報DAO.
 */
@Repository
public class UsersDao {

  @Autowired
  private PropertyConfig propertyConfig;
  @Autowired
  private DataSource dataSource;

  // ロガー
  static Logger log = LoggerFactory.getLogger(UsersService.class);

  /**
   * ユーザを取得する.
   * 
   * @param userId
   *
   * @return ユーザを格納したdto
   */
  public UsersDto selectOne(final String userId) {

    // 取得結果を格納するdto
    UsersDto dto = null;
    // SQL文
    StringBuilder sqlBuilder = new StringBuilder();
    sqlBuilder.append("SELECT * ");
    sqlBuilder.append("FROM USERS ");
    sqlBuilder.append("WHERE USER_ID LIKE ? ");

    log.info("SQL:" + sqlBuilder.toString());

    // DBとの接続を行う
    try (Connection con = DataSourceUtils.getConnection(dataSource);
        PreparedStatement ps = con.prepareStatement(sqlBuilder.toString());) {
      // バインド
      ps.setString(1, userId);

      // SQLを実行して取得結果をリザルトセットに格納
      try (ResultSet rs = ps.executeQuery();) {

        // リザルトセットから1レコードずつデータを取り出す
        if (rs.next()) {
          // 取得結果を格納するdtoをインスタンス化
          dto = new UsersDto();
          // dtoに取得結果を格納
          dto.setUserId(rs.getString("USER_ID"));
          dto.setPassword(rs.getString("PASSWORD"));
          dto.setUserName(rs.getString("USER_NAME"));
          dto.setRoleName(rs.getString("ROLE_NAME"));
          dto.setLoginFailureCount(rs.getInt("LOGIN_FAILURE_COUNT"));
          dto.setLoginDeniedAt(rs.getDate("LOGIN_DENIED_AT"));
          dto.setIsInvalid(rs.getInt("IS_INVALID"));
          dto.setUpdaterId(rs.getString("UPDATER_ID"));
          dto.setUpdatedAt(rs.getDate("UPDATED_AT"));
          dto.setCreaterId(rs.getString("CREATER_ID"));
          dto.setCreatedAt(rs.getDate("CREATED_AT"));
        }
      }
    } catch (SQLException e) {
      log.error(propertyConfig.get("{error.app.log.unexpected}"), e);
    }

    // 呼び出し元に取得結果を返却
    return dto;
  }


  /**
   * ユーザを取得する.
   * 
   * @param dto UsersDto
   * @param nonIsInvalid 無効を除外フラグ
   * 
   * @return ユーザを格納したdtoリスト
   */
  public List<UsersDto> select(final UsersDto dto, final boolean nonIsInvalid) {

    // SQL文
    StringBuilder sqlBuilder = new StringBuilder();
    sqlBuilder.append("SELECT * ");
    sqlBuilder.append("  FROM USERS ");
    sqlBuilder.append(" WHERE USER_ID   LIKE ? ");
    sqlBuilder.append("   AND USER_NAME LIKE ? ");
    sqlBuilder.append("   AND ROLE_NAME LIKE ? ");

    // 削除済を除外フラグがある場合の条件を設定
    if (nonIsInvalid) {
      sqlBuilder.append("AND   IS_INVALID = 0 ");
    }
    sqlBuilder.append("ORDER BY USER_ID ");

    log.info("SQL:" + sqlBuilder.toString());

    // 取得結果を格納するdto
    List<UsersDto> users = new ArrayList<>();
    // DBとの接続を行う
    try (Connection con = DataSourceUtils.getConnection(dataSource);
        PreparedStatement ps = con.prepareStatement(sqlBuilder.toString());) {

      // バインド。nullの場合はLike '%'で検索(ユーザーID、ユーザ名は前方一致)
      ps.setString(1, isEmptyStr(dto.getUserId()) ? "%" : dto.getUserId() + "%");
      ps.setString(2, isEmptyStr(dto.getUserName()) ? "%" : dto.getUserName() + "%");
      ps.setString(3, isEmptyStr(dto.getRoleName()) ? "%" : dto.getRoleName());

      // SQLを実行して取得結果をリザルトセットに格納
      try (ResultSet rs = ps.executeQuery();) {
        // リザルトセットから1レコードずつデータを取り出す
        while (rs.next()) {
          // 取得結果を格納するdtoをインスタンス化
          UsersDto resultDto = new UsersDto();
          // dtoに取得結果を格納
          resultDto.setUserId(rs.getString("USER_ID"));
          resultDto.setPassword(rs.getString("PASSWORD"));
          resultDto.setUserName(rs.getString("USER_NAME"));
          resultDto.setRoleName(rs.getString("ROLE_NAME"));
          resultDto.setLoginFailureCount(rs.getInt("LOGIN_FAILURE_COUNT"));
          resultDto.setLoginDeniedAt(rs.getDate("LOGIN_DENIED_AT"));
          resultDto.setIsInvalid(rs.getInt("IS_INVALID"));
          resultDto.setUpdaterId(rs.getString("UPDATER_ID"));
          resultDto.setUpdatedAt(rs.getDate("UPDATED_AT"));
          resultDto.setCreaterId(rs.getString("CREATER_ID"));
          resultDto.setCreatedAt(rs.getDate("CREATED_AT"));

          users.add(resultDto);
        }
      }

    } catch (SQLException e) {
      log.error(propertyConfig.get("{error.app.log.unexpected}"), e);
    }
    // 呼び出し元に取得結果を返却
    return users;
  }

  /**
   * ユーザを更新する.
   * 
   * @param dto UsersDto
   *
   * @return 処理結果（0:正常終了, 1:一意制約エラー, 9:想定外エラー）
   */
  public int update(final UsersDto dto) {

    // SQL文
    StringBuilder sqlBuilder = new StringBuilder();
    sqlBuilder.append("UPDATE USERS ");
    sqlBuilder.append("SET ");
    // パスワードは再設定時以外は更新しない

    if (isNotEmptyStr(dto.getPassword())) {
      sqlBuilder.append("PASSWORD = ? ,");
    }
    if (isNotEmptyStr(dto.getUserName())) {
      sqlBuilder.append("USER_NAME = ? ,");
    }
    if (isNotEmptyStr(dto.getRoleName())) {
      sqlBuilder.append("ROLE_NAME = ? ,");
    }

    sqlBuilder.append("LOGIN_FAILURE_COUNT = ? ,");
    if (dto.getLoginDeniedAt() != null) {
      sqlBuilder.append("LOGIN_DENIED_AT = ? ,");
    }
    sqlBuilder.append("IS_INVALID = ? ,");
    sqlBuilder.append("UPDATER_ID = ? ,");
    sqlBuilder.append("UPDATED_AT = ? ");
    sqlBuilder.append("WHERE USER_ID = ? ");

    log.info("SQL:" + sqlBuilder.toString());

    // DBとの接続を行う
    try (Connection con = DataSourceUtils.getConnection(dataSource);
        PreparedStatement ps = con.prepareStatement(sqlBuilder.toString());) {

      // 各項目値をバインド
      int idx = 1;
      // パスワード再設定時以外は設定しない
      if (isNotEmptyStr(dto.getPassword())) {
        ps.setString(idx++, dto.getPassword());
      }
      if (isNotEmptyStr(dto.getUserName())) {
        ps.setString(idx++, dto.getUserName());
      }
      if (isNotEmptyStr(dto.getRoleName())) {
        ps.setString(idx++, dto.getRoleName());
      }
      ps.setInt(idx++, dto.getLoginFailureCount());
      if (dto.getLoginDeniedAt() != null) {
        ps.setDate(idx++, dto.getLoginDeniedAt());
      }
      ps.setInt(idx++, dto.getIsInvalid());
      ps.setString(idx++, dto.getUpdaterId());
      ps.setDate(idx++, dto.getUpdatedAt());
      ps.setString(idx, dto.getUserId());

      // SQLを実行
      ps.executeUpdate();

    } catch (SQLException e) {
      int errCode = e.getErrorCode();
      log.debug("SQLException#getErrorCode = " + errCode);
      if (errCode == 1) {
        log.error(propertyConfig.get("error.app.log.db.uniqueConstraint"), e);
        return 1;
      } else {
        log.error(propertyConfig.get("{error.app.log.unexpected}"), e);
        return 9;
      }
    }
    return 0;
  }

  /**
   * ユーザを登録する.
   * 
   * @param dto UsersDto
   *
   * @return 処理結果（0:正常終了, 1:一意制約エラー, 9:想定外エラー）
   */
  public int create(final UsersDto dto) {

    // SQL文
    StringBuilder sqlBuilder = new StringBuilder();
    sqlBuilder.append("INSERT INTO USERS (");
    sqlBuilder.append(" USER_ID ");
    sqlBuilder.append(",PASSWORD ");
    sqlBuilder.append(",USER_NAME ");
    sqlBuilder.append(",ROLE_NAME ");
    sqlBuilder.append(",LOGIN_FAILURE_COUNT ");
    sqlBuilder.append(",LOGIN_DENIED_AT ");
    sqlBuilder.append(",IS_INVALID ");
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

    log.info("SQL:" + sqlBuilder.toString());

    // DBとの接続を行う
    try (Connection con = DataSourceUtils.getConnection(dataSource);
        PreparedStatement ps = con.prepareStatement(sqlBuilder.toString());) {

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
      log.debug("SQLException#getErrorCode = " + errCode);
      if (errCode == 1) {
        log.error(propertyConfig.get("error.app.log.db.uniqueConstraint"), e);
        return 1;
      } else {
        log.error(propertyConfig.get("{error.app.log.unexpected}"), e);
        return 9;
      }
    }
    return 0;
  }

  /**
   * ユーザを削除する.
   * 
   * @param userId
   *
   * @return 処理結果（0:正常終了, 9:想定外エラー）
   */
  public int delete(final String userId) {
    // SQL文
    StringBuilder sqlBuilder = new StringBuilder();
    sqlBuilder.append("DELETE FROM USERS ");
    sqlBuilder.append(" WHERE USER_ID = ? ");

    log.info("SQL:" + sqlBuilder.toString());

    // DBとの接続を行う
    try (Connection con = DataSourceUtils.getConnection(dataSource);
        PreparedStatement ps = con.prepareStatement(sqlBuilder.toString());) {

      // 各項目値をバインド
      ps.setString(1, userId);

      // SQLを実行
      ps.executeUpdate();

    } catch (SQLException e) {
      log.error(propertyConfig.get("{error.app.log.unexpected}"), e);
      return 9;
    }
    return 0;
  }

}
