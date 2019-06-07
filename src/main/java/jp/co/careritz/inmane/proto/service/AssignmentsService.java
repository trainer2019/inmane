package jp.co.careritz.inmane.proto.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jp.co.careritz.inmane.proto.dao.AssignmentsDao;
import jp.co.careritz.inmane.proto.dto.AssignmentsDto;

@Service
public class AssignmentsService {

  @Autowired
  private AssignmentsDao dao;

  /**
   * 稼働状況情報を取得するサービス.
   *
   * @return 稼働状況情報を格納したリスト
   */
  public List<AssignmentsDto> find() {

    return dao.select();
  }
}
