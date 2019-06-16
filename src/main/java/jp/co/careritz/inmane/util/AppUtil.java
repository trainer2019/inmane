package jp.co.careritz.inmane.util;

import org.springframework.util.StringUtils;

/**
 * アプリケーション共通ユーティリティ.
 */
public class AppUtil {

  /**
   * 検証対象文字列に値が入力されているかどうかを検証する.
   *
   * @param input 検証対象文字列
   * @return
   *         <dl>
   *         <dt>true</dt>
   *         <dd>検証対象文字列がnullまたは空文字の場合</dd>
   *         <dt>false</dt>
   *         <dd>検証対象文字列が存在する場合</dd>
   *         </dl>
   */
  public static boolean isEmptyStr(CharSequence input) {
    return StringUtils.isEmpty(input);
  }

  /**
   * 検証対象文字列に値が入力されているかどうかを検証する.
   *
   * @param input 検証対象文字列
   * @return
   *         <dl>
   *         <dt>true</dt>
   *         <dd>検証対象文字列が存在する場合</dd>
   *         <dt>false</dt>
   *         <dd>検証対象文字列がnullまたは空文字の場合</dd>
   *         </dl>
   */
  public static boolean isNotEmptyStr(CharSequence input) {
    return !isEmptyStr(input);
  }

}
