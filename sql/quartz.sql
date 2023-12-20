-- テーブルの削除
DROP TABLE IF EXISTS QRTZ_FIRED_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_PAUSED_TRIGGER_GRPS;
DROP TABLE IF EXISTS QRTZ_SCHEDULER_STATE;
DROP TABLE IF EXISTS QRTZ_LOCKS;
DROP TABLE IF EXISTS QRTZ_SIMPLE_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_SIMPROP_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_CRON_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_BLOB_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_JOB_DETAILS;
DROP TABLE IF EXISTS QRTZ_CALENDARS;

-- ----------------------------
-- 1. 各設定されたジョブ詳細の情報を保存
-- ----------------------------
CREATE TABLE QRTZ_JOB_DETAILS (
    sched_name           VARCHAR(120)    NOT NULL            COMMENT 'スケジュール名',
    job_name             VARCHAR(200)    NOT NULL            COMMENT 'ジョブ名',
    job_group            VARCHAR(200)    NOT NULL            COMMENT 'ジョブグループ名',
    description          VARCHAR(250)    NULL                COMMENT '関連説明',
    job_class_name       VARCHAR(250)    NOT NULL            COMMENT '実行ジョブクラス名',
    is_durable           VARCHAR(1)      NOT NULL            COMMENT '永続化',
    is_nonconcurrent     VARCHAR(1)      NOT NULL            COMMENT '同時実行',
    is_update_data       VARCHAR(1)      NOT NULL            COMMENT 'データ更新',
    requests_recovery    VARCHAR(1)      NOT NULL            COMMENT '復元実行を受け入れる',
    job_data             BLOB            NULL                COMMENT '永続化ジョブオブジェクト',
    PRIMARY KEY (sched_name, job_name, job_group)
) ENGINE=InnoDB COMMENT = 'ジョブ詳細情報テーブル';

-- ----------------------------
-- 2. 設定されたトリガーの情報を保存
-- ----------------------------
CREATE TABLE QRTZ_TRIGGERS (
    sched_name           VARCHAR(120)    NOT NULL            COMMENT 'スケジュール名',
    trigger_name         VARCHAR(200)    NOT NULL            COMMENT 'トリガー名',
    trigger_group        VARCHAR(200)    NOT NULL            COMMENT 'トリガーグループ名',
    job_name             VARCHAR(200)    NOT NULL            COMMENT 'qrtz_job_detailsテーブルjob_nameの外部キー',
    job_group            VARCHAR(200)    NOT NULL            COMMENT 'qrtz_job_detailsテーブルjob_groupの外部キー',
    description          VARCHAR(250)    NULL                COMMENT '関連説明',
    next_fire_time       BIGINT(13)      NULL                COMMENT '前回の実行時間（ミリ秒）',
    prev_fire_time       BIGINT(13)      NULL                COMMENT '次回の実行時間（デフォルトは-1で実行なしを示す）',
    priority             INTEGER         NULL                COMMENT '優先度',
    trigger_state        VARCHAR(16)     NOT NULL            COMMENT 'トリガーの状態',
    trigger_type         VARCHAR(8)      NOT NULL            COMMENT 'トリガータイプ',
    start_time           BIGINT(13)      NOT NULL            COMMENT '開始時間',
    end_time             BIGINT(13)      NULL                COMMENT '終了時間',
    calendar_name        VARCHAR(200)    NULL                COMMENT 'カレンダー名',
    misfire_instr        SMALLINT(2)     NULL                COMMENT '失敗補償の戦略',
    job_data             BLOB            NULL                COMMENT '永続化ジョブオブジェクト',
    PRIMARY KEY (sched_name, trigger_name, trigger_group),
    FOREIGN KEY (sched_name, job_name, job_group) REFERENCES QRTZ_JOB_DETAILS(sched_name, job_name, job_group)
) ENGINE=InnoDB COMMENT = 'トリガー詳細情報テーブル';

-- ----------------------------
-- 3. シンプルトリガーの情報を保存（繰り返し回数、間隔、および実行済み回数を含む）
-- ----------------------------
CREATE TABLE QRTZ_SIMPLE_TRIGGERS (
    sched_name           VARCHAR(120)    NOT NULL            COMMENT 'スケジュール名',
    trigger_name         VARCHAR(200)    NOT NULL            COMMENT 'qrtz_triggersテーブルtrigger_nameの外部キー',
    trigger_group        VARCHAR(200)    NOT NULL            COMMENT 'qrtz_triggersテーブルtrigger_groupの外部キー',
    repeat_count         BIGINT(7)       NOT NULL            COMMENT '繰り返し回数の統計',
    repeat_interval      BIGINT(12)      NOT NULL            COMMENT '繰り返し間隔時間',
    times_triggered      BIGINT(10)      NOT NULL            COMMENT '実行回数',
    PRIMARY KEY (sched_name, trigger_name, trigger_group),
    FOREIGN KEY (sched_name, trigger_name, trigger_group) REFERENCES QRTZ_TRIGGERS(sched_name, trigger_name, trigger_group)
) ENGINE=InnoDB COMMENT = 'シンプルトリガー情報テーブル';

-- ----------------------------
-- 4. Cronトリガーの情報を保存（Cron式とタイムゾーン情報を含む）
-- ---------------------------- 
CREATE TABLE QRTZ_CRON_TRIGGERS (
    sched_name           VARCHAR(120)    NOT NULL            COMMENT 'スケジュール名',
    trigger_name         VARCHAR(200)    NOT NULL            COMMENT 'qrtz_triggersテーブルtrigger_nameの外部キー',
    trigger_group        VARCHAR(200)    NOT NULL            COMMENT 'qrtz_triggersテーブルtrigger_groupの外部キー',
    cron_expression      VARCHAR(200)    NOT NULL            COMMENT 'Cron式',
    time_zone_id         VARCHAR(80)                         COMMENT 'タイムゾーン',
    PRIMARY KEY (sched_name, trigger_name, trigger_group),
    FOREIGN KEY (sched_name, trigger_name, trigger_group) REFERENCES QRTZ_TRIGGERS(sched_name, trigger_name, trigger_group)
) ENGINE=InnoDB COMMENT = 'Cronタイプのトリガーテーブル';
-- ----------------------------
-- 5、 Blob型トリガーの格納（QuartzユーザーがJDBCを使用してカスタムトリガータイプを作成し、JobStoreがインスタンスの保存方法を知らない場合に使用）
-- ---------------------------- 
CREATE TABLE QRTZ_BLOB_TRIGGERS (
    sched_name           VARCHAR(120)    NOT NULL            COMMENT 'スケジュール名',
    trigger_name         VARCHAR(200)    NOT NULL            COMMENT 'qrtz_triggersテーブルtrigger_nameの外部キー',
    trigger_group        VARCHAR(200)    NOT NULL            COMMENT 'qrtz_triggersテーブルtrigger_groupの外部キー',
    blob_data            BLOB            NULL                COMMENT '永続化トリガーオブジェクト',
    PRIMARY KEY (sched_name, trigger_name, trigger_group),
    FOREIGN KEY (sched_name, trigger_name, trigger_group) REFERENCES QRTZ_TRIGGERS(sched_name, trigger_name, trigger_group)
) ENGINE=InnoDB COMMENT = 'Blob型トリガーテーブル';

-- ----------------------------
-- 6、 Blob型でカレンダー情報を格納，Quartzは時間範囲を指定するためにカレンダーを構成できます
-- ---------------------------- 
CREATE TABLE QRTZ_CALENDARS (
    sched_name           VARCHAR(120)    NOT NULL            COMMENT 'スケジュール名',
    calendar_name        VARCHAR(200)    NOT NULL            COMMENT 'カレンダー名',
    calendar             BLOB            NOT NULL            COMMENT '永続化カレンダーオブジェクト',
    PRIMARY KEY (sched_name, calendar_name)
) ENGINE=InnoDB COMMENT = 'カレンダー情報テーブル';

-- ----------------------------
-- 7、 一時停止中のトリガーグループの情報を格納
-- ---------------------------- 
CREATE TABLE QRTZ_PAUSED_TRIGGER_GRPS (
    sched_name           VARCHAR(120)    NOT NULL            COMMENT 'スケジュール名',
    trigger_group        VARCHAR(200)    NOT NULL            COMMENT 'qrtz_triggersテーブルtrigger_groupの外部キー',
    PRIMARY KEY (sched_name, trigger_group)
) ENGINE=InnoDB COMMENT = '一時停止中のトリガーテーブル';

-- ----------------------------
-- 8、 既にトリガーされたトリガーに関連する状態情報，および関連するジョブの実行情報を格納
-- ---------------------------- 
CREATE TABLE QRTZ_FIRED_TRIGGERS (
    sched_name           VARCHAR(120)    NOT NULL            COMMENT 'スケジュール名',
    entry_id             VARCHAR(95)     NOT NULL            COMMENT 'スケジューラインスタンスID',
    trigger_name         VARCHAR(200)    NOT NULL            COMMENT 'qrtz_triggersテーブルtrigger_nameの外部キー',
    trigger_group        VARCHAR(200)    NOT NULL            COMMENT 'qrtz_triggersテーブルtrigger_groupの外部キー',
    instance_name        VARCHAR(200)    NOT NULL            COMMENT 'スケジューラインスタンス名',
    fired_time           BIGINT(13)      NOT NULL            COMMENT 'トリガー発生時刻',
    sched_time           BIGINT(13)      NOT NULL            COMMENT 'スケジュール時刻',
    priority             INTEGER         NOT NULL            COMMENT '優先度',
    state                VARCHAR(16)     NOT NULL            COMMENT '状態',
    job_name             VARCHAR(200)    NULL                COMMENT 'ジョブ名',
    job_group            VARCHAR(200)    NULL                COMMENT 'ジョブグループ名',
    is_nonconcurrent     VARCHAR(1)      NULL                COMMENT '非同期かどうか',
    requests_recovery    VARCHAR(1)      NULL                COMMENT '復元実行を要求するかどうか',
    PRIMARY KEY (sched_name, entry_id)
) ENGINE=InnoDB COMMENT = '発生済みトリガーテーブル';

-- ----------------------------
-- 9、 スケジューラに関するわずかな状態情報を格納，クラスタ内で使用される場合、他のスケジューラインスタンスが見えるかもしれません
-- ---------------------------- 
CREATE TABLE QRTZ_SCHEDULER_STATE (
    sched_name           VARCHAR(120)    NOT NULL            COMMENT 'スケジュール名',
    instance_name        VARCHAR(200)    NOT NULL            COMMENT 'インスタンス名',
    last_checkin_time    BIGINT(13)      NOT NULL            COMMENT '最終チェックイン時刻',
    checkin_interval     BIGINT(13)      NOT NULL            COMMENT 'チェックイン間隔',
    PRIMARY KEY (sched_name, instance_name)
) ENGINE=InnoDB COMMENT = 'スケジューラ状態テーブル';

-- ----------------------------
-- 10、 プログラムの悲観的ロック情報を格納（悲観的ロックが使用されている場合）
-- ---------------------------- 
CREATE TABLE QRTZ_LOCKS (
    sched_name           VARCHAR(120)    NOT NULL            COMMENT 'スケジュール名',
    lock_name            VARCHAR(40)     NOT NULL            COMMENT '悲観的ロック名',
    PRIMARY KEY (sched_name, lock_name)
) ENGINE=InnoDB COMMENT = '悲観的ロック情報テーブル';

-- ----------------------------
-- 11、 Quartzクラスタの同期メカニズムの行ロックテーブル
-- ---------------------------- 
CREATE TABLE QRTZ_SIMPROP_TRIGGERS (
    sched_name           VARCHAR(120)    NOT NULL            COMMENT 'スケジュール名',
    trigger_name         VARCHAR(200)    NOT NULL            COMMENT 'qrtz_triggersテーブルtrigger_nameの外部キー',
    trigger_group        VARCHAR(200)    NOT NULL            COMMENT 'qrtz_triggersテーブルtrigger_groupの外部キー',
    str_prop_1           VARCHAR(512)    NULL                COMMENT 'String型トリガーの第一パラメータ',
    str_prop_2           VARCHAR(512)    NULL                COMMENT 'String型トリガーの第二パラメータ',
    str_prop_3           VARCHAR(512)    NULL                COMMENT 'String型トリガーの第三パラメータ',
    int_prop_1           INT             NULL                COMMENT 'int型トリガーの第一パラメータ',
    int_prop_2           INT             NULL                COMMENT 'int型トリガーの第二パラメータ',
    long_prop_1          BIGINT          NULL                COMMENT 'long型トリガーの第一パラメータ',
    long_prop_2          BIGINT          NULL                COMMENT 'long型トリガーの第二パラメータ',
    dec_prop_1           NUMERIC(13,4)   NULL                COMMENT 'decimal型トリガーの第一パラメータ',
    dec_prop_2           NUMERIC(13,4)   NULL                COMMENT 'decimal型トリガーの第二パラメータ',
    bool_prop_1          VARCHAR(1)      NULL                COMMENT 'Boolean型トリガーの第一パラメータ',
    bool_prop_2          VARCHAR(1)      NULL                COMMENT 'Boolean型トリガーの第二パラメータ',
    PRIMARY KEY (sched_name, trigger_name, trigger_group),
    FOREIGN KEY (sched_name, trigger_name, trigger_group) REFERENCES QRTZ_TRIGGERS(sched_name, trigger_name, trigger_group)
) ENGINE=InnoDB COMMENT = '同期メカニズムの行ロックテーブル';

COMMIT;
