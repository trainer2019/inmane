-- システム名:稼働状況管理システム
-- スキーマ名:INMNUSR


-- 【！注意！】全テーブル削除
--DROP TABLE CLIENTS;
--DROP TABLE CONTRACTS;
--DROP TABLE CONTRACTS_TRAN;
--DROP TABLE USERS;
--DROP TABLE STAFFS;
--DROP TABLE BULLETIN_BOARD;
--DROP TABLE PROJECTS;
--DROP TABLE BILL_ADDRESSES;

-- テーブル作成
CREATE TABLE USERS( 
  USER_ID VARCHAR2(10) NOT NULL
  , PASSWORD VARCHAR2(60) NOT NULL
  , USER_NAME VARCHAR2(80) NOT NULL
  , ROLE_NAME VARCHAR2(5) NOT NULL
  , LOGIN_FAILURE_COUNT NUMBER(1) DEFAULT 0 NOT NULL
  , LOGIN_DENIED_AT DATE
  , IS_VALID NUMBER(1) DEFAULT 0 NOT NULL
  , UPDATER_ID VARCHAR2(10)
  , UPDATED_AT DATE
  , CREATER_ID VARCHAR2(10) NOT NULL
  , CREATED_AT DATE NOT NULL
  , CONSTRAINT PK_USERS PRIMARY KEY (USER_ID)
); 

CREATE TABLE STAFFS( 
  STAFF_NO CHAR(5) NOT NULL
  , STAFF_TYPE CHAR(2) NOT NULL
  , STAFF_NAME VARCHAR2(60) NOT NULL
  , STAFF_NAME_KANA VARCHAR2(120) NOT NULL
  , BIRTH_DATE DATE NOT NULL
  , JOINED_DATE DATE NOT NULL
  , IS_VALID NUMBER(1, 0) DEFAULT 0 NOT NULL
  , UPDATER_ID VARCHAR2(10)
  , UPDATED_AT DATE
  , CREATER_ID VARCHAR2(10) NOT NULL
  , CREATED_AT DATE NOT NULL
  , CONSTRAINT PK_STAFFS PRIMARY KEY (STAFF_NO)
); 

CREATE TABLE CLIENTS( 
  CLIENT_ID VARCHAR2(36) NOT NULL
  , CLIENT_NAME VARCHAR2(200) NOT NULL
  , SALES_STAFF_NO VARCHAR2(10) NOT NULL
  , IS_VALID NUMBER(1, 0) DEFAULT 0 NOT NULL
  , UPDATER_ID VARCHAR2(10)
  , UPDATED_AT DATE
  , CREATER_ID VARCHAR2(10) NOT NULL
  , CREATED_AT DATE NOT NULL
  , CONSTRAINT PK_CLIENTS PRIMARY KEY (CLIENT_ID)
); 

CREATE TABLE BULLETIN_BOARD( 
  BULLETIN_BOARD_ID VARCHAR2(36) NOT NULL
  , CONTENTS VARCHAR2(400) NOT NULL
  , IS_VALID NUMBER(1, 0) DEFAULT 0 NOT NULL
  , CREATER_ID VARCHAR2(10) NOT NULL
  , CREATED_AT DATE NOT NULL
  , CONSTRAINT PK_BULLETIN_BOARD PRIMARY KEY (BULLETIN_BOARD_ID)
); 

CREATE TABLE PROJECTS( 
  PROJECT_ID VARCHAR2(36) NOT NULL
  , PROJECT_NAME VARCHAR2(120) NOT NULL
  , CLIENT_ID VARCHAR2(36) NOT NULL
  , BILL_ADDRESS_ID VARCHAR2(36) NOT NULL
  , SKILLS VARCHAR2(400)
  , OVERVIEWS VARCHAR2(800)
  , REMARKS VARCHAR2(800)
  , IS_VALID NUMBER(1, 0) DEFAULT 0 NOT NULL
  , UPDATER_ID VARCHAR2(10)
  , UPDATED_AT DATE
  , CREATER_ID VARCHAR2(10) NOT NULL
  , CREATED_AT DATE NOT NULL
  , CONSTRAINT PK_PROJECTS PRIMARY KEY (PROJECT_ID)
); 

CREATE TABLE CONTRACTS( 
  CONTRACT_ID VARCHAR2(36) NOT NULL
  , PROJECT_ID VARCHAR2(36) NOT NULL
  , STAFF_NO VARCHAR2(5) NOT NULL
  , IS_VALID NUMBER(1, 0) DEFAULT 0 NOT NULL
  , UPDATER_ID VARCHAR2(10)
  , UPDATED_AT DATE
  , CREATER_ID VARCHAR2(10) NOT NULL
  , CREATED_AT DATE NOT NULL
  , CONSTRAINT PK_CONTRACTS PRIMARY KEY (CONTRACT_ID)
); 

CREATE TABLE CONTRACTS_TRAN( 
  CONTRACT_ID VARCHAR2(36) NOT NULL
  , SUB_NO CHAR(3) NOT NULL
  , CONTRACT_PERIOD_FROM DATE NOT NULL
  , CONTRACT_PERIOD_TO DATE NOT NULL
  , CONTRACT_TYPE NUMBER(1, 0) NOT NULL
  , UNIT_PRICE NUMBER(7, 0) DEFAULT 0 NOT NULL
  , WORKING_HOURS_MIN NUMBER(3, 0)
  , WORKING_HOURS_MAX NUMBER(3, 0)
  , OVERTIME_PREMIUM_PRICE NUMBER(7, 0)
  , SHORTAGE_DEDUCTIONS_PRICE NUMBER(7, 0)
  , REMARKS VARCHAR2(800)
  , IS_VALID NUMBER(1, 0) DEFAULT 0 NOT NULL
  , UPDATER_ID VARCHAR2(10)
  , UPDATED_AT DATE
  , CREATER_ID VARCHAR2(10) NOT NULL
  , CREATED_AT DATE NOT NULL
  , CONSTRAINT PK_CONTRACTS_TRAN PRIMARY KEY (CONTRACT_ID, SUB_NO)
); 

CREATE TABLE BILL_ADDRESSES( 
  BILL_ADDRESS_ID VARCHAR2(36) NOT NULL
  , CLIENT_ID VARCHAR2(36) NOT NULL
  , SPECIFIED_FORMAT_TYPE NUMBER(1, 0) DEFAULT 0 NOT NULL
  , SITE NUMBER(3, 0)
  , BILL_UNIT_TYPE NUMBER(1, 0)
  , BILL_UNIT NUMBER(3, 0) DEFAULT 0
  , ZIP_CODE CHAR (7)
  , ADRESS1 VARCHAR2(100)
  , ADRESS2 VARCHAR2(100)
  , COMPANY_NAME1 VARCHAR2(100)
  , COMPANY_NAME2 VARCHAR2(100)
  , COMPANY_NAME3 VARCHAR2(100)
  , COMPANY_NAME4 VARCHAR2(100)
  , IS_VALID NUMBER(1, 0) DEFAULT 0 NOT NULL
  , UPDATER_ID VARCHAR2(10)
  , UPDATED_AT DATE
  , CREATER_ID VARCHAR2(10) NOT NULL
  , CREATED_AT DATE NOT NULL
  , CONSTRAINT PK_BILL_ADDRESSES PRIMARY KEY (BILL_ADDRESS_ID)
); 

-- コメント作成（論理名）
comment on table USERS is 'ユーザーマスタ';
comment on column USERS.USER_ID is 'ユーザID';
comment on column USERS.PASSWORD is 'パスワード';
comment on column USERS.USER_NAME is 'ユーザ名';
comment on column USERS.ROLE_NAME is 'ロール名';
comment on column USERS.LOGIN_FAILURE_COUNT is 'ログイン失敗回数';
comment on column USERS.LOGIN_DENIED_AT is 'ログイン拒否日時';
comment on column USERS.IS_VALID is '有効フラグ';
comment on column USERS.UPDATER_ID is '更新者';
comment on column USERS.UPDATED_AT is '更新年月日';
comment on column USERS.CREATER_ID is '登録者';
comment on column USERS.CREATED_AT is '登録年月日';

comment on table STAFFS is 'スタッフマスタ';
comment on column STAFFS.STAFF_NO is 'スタッフNo';
comment on column STAFFS.STAFF_TYPE is 'スタッフ種別';
comment on column STAFFS.STAFF_NAME is '氏名';
comment on column STAFFS.STAFF_NAME_KANA is '氏名（かな）';
comment on column STAFFS.BIRTH_DATE is '生年月日';
comment on column STAFFS.JOINED_DATE is '入社年月日';
comment on column STAFFS.IS_VALID is '有効フラグ';
comment on column STAFFS.UPDATER_ID is '更新者';
comment on column STAFFS.UPDATED_AT is '更新年月日';
comment on column STAFFS.CREATER_ID is '登録者';
comment on column STAFFS.CREATED_AT is '登録年月日';

comment on table CLIENTS is '取引先マスタ';
comment on column CLIENTS.CLIENT_ID is '取引先ID';
comment on column CLIENTS.CLIENT_NAME is '取引先名';
comment on column CLIENTS.SALES_STAFF_NO is '担当営業No';
comment on column CLIENTS.IS_VALID is '有効フラグ';
comment on column CLIENTS.UPDATER_ID is '更新者';
comment on column CLIENTS.UPDATED_AT is '更新年月日';
comment on column CLIENTS.CREATER_ID is '登録者';
comment on column CLIENTS.CREATED_AT is '登録年月日';

comment on table BULLETIN_BOARD is 'お知らせ';
comment on column BULLETIN_BOARD.BULLETIN_BOARD_ID is 'お知らせID';
comment on column BULLETIN_BOARD.CONTENTS is '内容';
comment on column BULLETIN_BOARD.IS_VALID is '有効フラグ';
comment on column BULLETIN_BOARD.CREATER_ID is '登録者';
comment on column BULLETIN_BOARD.CREATED_AT is '登録年月日';

comment on table PROJECTS is '案件マスタ';
comment on column PROJECTS.PROJECT_ID is '案件ID';
comment on column PROJECTS.PROJECT_NAME is '案件名';
comment on column PROJECTS.CLIENT_ID is '取引先ID';
comment on column PROJECTS.BILL_ADDRESS_ID is '請求先ID';
comment on column PROJECTS.SKILLS is '主要技術';
comment on column PROJECTS.OVERVIEWS is '業務内容';
comment on column PROJECTS.REMARKS is '備考';
comment on column PROJECTS.IS_VALID is '有効フラグ';
comment on column PROJECTS.UPDATER_ID is '更新者';
comment on column PROJECTS.UPDATED_AT is '更新年月日';
comment on column PROJECTS.CREATER_ID is '登録者';
comment on column PROJECTS.CREATED_AT is '登録年月日';

comment on table CONTRACTS is '契約ヘッダ情報';
comment on column CONTRACTS.CONTRACT_ID is '契約ID';
comment on column CONTRACTS.PROJECT_ID is '案件ID';
comment on column CONTRACTS.STAFF_NO is 'スタッフNo';
comment on column CONTRACTS.IS_VALID is '有効フラグ';
comment on column CONTRACTS.UPDATER_ID is '更新者';
comment on column CONTRACTS.UPDATED_AT is '更新年月日';
comment on column CONTRACTS.CREATER_ID is '登録者';
comment on column CONTRACTS.CREATED_AT is '登録年月日';

comment on table CONTRACTS_TRAN is '契約トラン情報';
comment on column CONTRACTS_TRAN.CONTRACT_ID is '契約ID';
comment on column CONTRACTS_TRAN.SUB_NO is '契約枝番';
comment on column CONTRACTS_TRAN.CONTRACT_PERIOD_FROM is '契約期間From';
comment on column CONTRACTS_TRAN.CONTRACT_PERIOD_TO is '契約期間To';
comment on column CONTRACTS_TRAN.CONTRACT_TYPE is '契約種別';
comment on column CONTRACTS_TRAN.UNIT_PRICE is '単価';
comment on column CONTRACTS_TRAN.WORKING_HOURS_MIN is '稼働時間(下限)';
comment on column CONTRACTS_TRAN.WORKING_HOURS_MAX is '稼働時間(上限)';
comment on column CONTRACTS_TRAN.OVERTIME_PREMIUM_PRICE is '超過割増分単価';
comment on column CONTRACTS_TRAN.SHORTAGE_DEDUCTIONS_PRICE is '未達控除分単価';
comment on column CONTRACTS_TRAN.REMARKS is '備考';
comment on column CONTRACTS_TRAN.IS_VALID is '有効フラグ';
comment on column CONTRACTS_TRAN.UPDATER_ID is '更新者';
comment on column CONTRACTS_TRAN.UPDATED_AT is '更新年月日';
comment on column CONTRACTS_TRAN.CREATER_ID is '登録者';
comment on column CONTRACTS_TRAN.CREATED_AT is '登録年月日';

comment on table BILL_ADDRESSES is '請求先マスタ';
comment on column BILL_ADDRESSES.BILL_ADDRESS_ID is '請求先ID';
comment on column BILL_ADDRESSES.CLIENT_ID is '取引先ID';
comment on column BILL_ADDRESSES.SPECIFIED_FORMAT_TYPE is '客先指定';
comment on column BILL_ADDRESSES.SITE is 'サイト';
comment on column BILL_ADDRESSES.BILL_UNIT_TYPE is '請求単位種別';
comment on column BILL_ADDRESSES.BILL_UNIT is '請求単位';
comment on column BILL_ADDRESSES.ZIP_CODE is '郵便番号';
comment on column BILL_ADDRESSES.ADRESS1 is '住所１';
comment on column BILL_ADDRESSES.ADRESS2 is '住所２';
comment on column BILL_ADDRESSES.COMPANY_NAME1 is '会社名１';
comment on column BILL_ADDRESSES.COMPANY_NAME2 is '会社名２';
comment on column BILL_ADDRESSES.COMPANY_NAME3 is '会社名３';
comment on column BILL_ADDRESSES.COMPANY_NAME4 is '会社名４';
comment on column BILL_ADDRESSES.IS_VALID is '有効フラグ';
comment on column BILL_ADDRESSES.UPDATER_ID is '更新者';
comment on column BILL_ADDRESSES.UPDATED_AT is '更新年月日';
comment on column BILL_ADDRESSES.CREATER_ID is '登録者';
comment on column BILL_ADDRESSES.CREATED_AT is '登録年月日';

