package jp.co.careritz.inmane.commons.users.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jp.co.careritz.inmane.commons.users.dao.UsersDao;
import jp.co.careritz.inmane.commons.users.dto.UsersDto;

@Service
public class UsersService {

  @Autowired
  private UsersDao dao;

  /**
   * ユーザ情報を取得するサービス.
   *
   * @param userid ユーザID
   * 
   * @return ユーザを格納したDTO
   */
  public UsersDto findByPk(String userid) {

    return dao.selectOne(userid);
  }

  /**
   * ユーザ情報を取得するサービス.
   * 
   * @param dto 検索条件を格納したdto
   * @param nonDeleted 削除済を含むフラグ
   *
   * @return ユーザを格納したリスト
   */
  public List<UsersDto> find(UsersDto dto, boolean nonDeleted) {

    return dao.select(dto, nonDeleted);
  }

  /**
   * ユーザ情報を更新するサービス.
   *
   * @param dto ユーザを格納したリスト
   */
  public int updateByPk(UsersDto dto) {

    return dao.update(dto);
  }

  public int create(UsersDto dto) {

    return dao.create(dto);
  }

  public int deleteByPk(String userId) {

    return dao.delete(userId);
  }
}
