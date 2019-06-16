package jp.co.careritz.inmane.constant;

/**
 * アプリケーション共通定数定義.
 */
public class AppConst {

  /* ---------------------------------------------------- */
  /* 非同期処理関連 */
  /* ---------------------------------------------------- */
  /** 非同期処理ステータス 正常. */
  public static final Integer AJAX_STATE_SUCCESS = 1;
  /** 非同期処理ステータス 警告. */
  public static final Integer AJAX_STATE_WARNING = 2;
  /** 非同期処理ステータス 例外. */
  public static final Integer AJAX_STATE_FAILURE = 3;

  /** 非同期処理完了メッセージID 正常. */
  public static final String APP_COMPLETE_MESSAGE_ID_SUCCESS = "appCompleteMessageSuccess";
  /** 非同期処理完了メッセージID 警告. */
  public static final String APP_COMPLETE_MESSAGE_ID_WARNING = "appCompleteMessageWarning";
  /** 非同期処理完了メッセージID 例外. */
  public static final String APP_COMPLETE_MESSAGE_ID_FAILURE = "appCompleteMessageFailure";

  /* ---------------------------------------------------- */
  /* メッセージID */
  /* ---------------------------------------------------- */
  /** 共通エラーメッセージID. */
  public static final String APP_ERROR_MESSAGE_ID = "appErrorMessage";

}
