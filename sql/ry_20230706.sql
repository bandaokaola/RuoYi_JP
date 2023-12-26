-- ----------------------------
-- 1、部門テーブル
-- ----------------------------
drop table if exists sys_dept;
create table sys_dept (
  dept_id           bigint(20)      not null auto_increment    comment '部門ID',
  parent_id         bigint(20)      default 0                  comment '親部門ID',
  ancestors         varchar(50)     default ''                 comment '祖先リスト',
  dept_name         varchar(30)     default ''                 comment '部門名',
  order_num         int(4)          default 0                  comment '表示順序',
  leader            varchar(20)     default null               comment '責任者',
  phone             varchar(11)     default null               comment '電話番号',
  email             varchar(50)     default null               comment 'メール',
  status            char(1)         default '0'                comment '部門の状態（0正常 1停止）',
  del_flag          char(1)         default '0'                comment '削除フラグ（0存在 2削除）',
  create_by         varchar(64)     default ''                 comment '作成者',
  create_time       datetime                                   comment '作成日時',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新日時',
  primary key (dept_id)
) engine=innodb auto_increment=200 comment = '部門テーブル';

-- ----------------------------
-- 初期化-部門テーブルデータ
-- ----------------------------
insert into sys_dept values(100,  0,   '0',          '若依科技',   0, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(101,  100, '0,100',      '深圳総公司', 1, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(102,  100, '0,100',      '長沙分公司', 2, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(103,  101, '0,100,101',  '研發部門',   1, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(104,  101, '0,100,101',  '市場部門',   2, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(105,  101, '0,100,101',  'テスト部門',   3, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(106,  101, '0,100,101',  '財務部門',   4, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(107,  101, '0,100,101',  '運用部門',   5, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(108,  102, '0,100,102',  '市場部門',   1, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(109,  102, '0,100,102',  '財務部門',   2, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', sysdate(), '', null);


-- ----------------------------
-- 2、ユーザー情報テーブル
-- ----------------------------
drop table if exists sys_user;
create table sys_user (
  user_id           bigint(20)      not null auto_increment    comment 'ユーザーID',
  dept_id           bigint(20)      default null               comment '部門ID',
  login_name        varchar(30)     not null                   comment 'ログインアカウント',
  user_name         varchar(30)     default ''                 comment 'ユーザー名',
  user_type         varchar(2)      default '00'               comment 'ユーザータイプ（00システムユーザー 01登録ユーザー）',
  email             varchar(50)     default ''                 comment 'ユーザーメール',
  phonenumber       varchar(11)     default ''                 comment '携帯電話番号',
  sex               char(1)         default '0'                comment 'ユーザー性別（0男性 1女性 2未知）',
  avatar            varchar(100)    default ''                 comment 'アバターパス',
  password          varchar(50)     default ''                 comment 'パスワード',
  salt              varchar(20)     default ''                 comment '塩加密',
  status            char(1)         default '0'                comment 'アカウントの状態（0正常 1停止）',
  del_flag          char(1)         default '0'                comment '削除フラグ（0存在 2削除）',
  login_ip          varchar(128)    default ''                 comment '最終ログインIP',
  login_date        datetime                                   comment '最終ログイン時間',
  pwd_update_date   datetime                                   comment 'パスワード最終更新時間',
  create_by         varchar(64)     default ''                 comment '作成者',
  create_time       datetime                                   comment '作成時間',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新時間',
  remark            varchar(500)    default null               comment '備考',
  primary key (user_id)
) engine=innodb auto_increment=100 comment = 'ユーザー情報テーブル';

-- ----------------------------
-- 初期化-ユーザー情報テーブルデータ
-- ----------------------------
insert into sys_user values(1,  103, 'admin', '若依', '00', 'ry@163.com', '15888888888', '1', '', '29c67a30398638269fe600f73a054934', '111111', '0', '0', '127.0.0.1', sysdate(), sysdate(), 'admin', sysdate(), '', null, '管理者');
insert into sys_user values(2,  105, 'ry',    '若依', '00', 'ry@qq.com',  '15666666666', '1', '', '8e6d98b90472783cc73c17047ddccf36', '222222', '0', '0', '127.0.0.1', sysdate(), sysdate(), 'admin', sysdate(), '', null, 'テスター');


-- ----------------------------
-- 3、職位情報テーブル
-- ----------------------------
drop table if exists sys_post;
create table sys_post
(
  post_id       bigint(20)      not null auto_increment    comment '職位ID',
  post_code     varchar(64)     not null                   comment '職位コード',
  post_name     varchar(50)     not null                   comment '職位名',
  post_sort     int(4)          not null                   comment '表示順序',
  status        char(1)         not null                   comment 'ステータス（0正常 1停止）',
  create_by     varchar(64)     default ''                 comment '作成者',
  create_time   datetime                                   comment '作成時間',
  update_by     varchar(64)     default ''			       comment '更新者',
  update_time   datetime                                   comment '更新時間',
  remark        varchar(500)    default null               comment '備考',
  primary key (post_id)
) engine=innodb comment = '職位情報テーブル';

-- ----------------------------
-- 初期化-職位情報テーブルデータ
-- ----------------------------
insert into sys_post values(1, 'ceo',  '社長',    1, '0', 'admin', sysdate(), '', null, '');
insert into sys_post values(2, 'se',   'プロジェクトマネージャー',  2, '0', 'admin', sysdate(), '', null, '');
insert into sys_post values(3, 'hr',   '人事',  3, '0', 'admin', sysdate(), '', null, '');
insert into sys_post values(4, 'user', '一般社員',  4, '0', 'admin', sysdate(), '', null, '');


-- ----------------------------
-- 4、ロール情報テーブル
-- ----------------------------
drop table if exists sys_role;
create table sys_role (
  role_id           bigint(20)      not null auto_increment    comment 'ロールID',
  role_name         varchar(30)     not null                   comment 'ロール名',
  role_key          varchar(100)    not null                   comment 'ロールの権限文字列',
  role_sort         int(4)          not null                   comment '表示順序',
  data_scope        char(1)         default '1'                comment 'データ範囲（1：全データ権限 2：カスタムデータ権限 3：自部門データ権限 4：自部門以下データ権限）',
  status            char(1)         not null                   comment 'ロールの状態（0正常 1停止）',
  del_flag          char(1)         default '0'                comment '削除フラグ（0存在 2削除）',
  create_by         varchar(64)     default ''                 comment '作成者',
  create_time       datetime                                   comment '作成時間',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新時間',
  remark            varchar(500)    default null               comment '備考',
  primary key (role_id)
) engine=innodb auto_increment=100 comment = 'ロール情報テーブル';

-- ----------------------------
-- 初期化-ロール情報テーブルデータ
-- ----------------------------
insert into sys_role values('1', 'スーパー管理者', 'admin',  1, 1, '0', '0', 'admin', sysdate(), '', null, 'スーパー管理者');
insert into sys_role values('2', '通常のロール',   'common', 2, 2, '0', '0', 'admin', sysdate(), '', null, '通常のロール');


-- ----------------------------
-- 5、メニュー権限テーブル
-- ----------------------------
drop table if exists sys_menu;
create table sys_menu (
  menu_id           bigint(20)      not null auto_increment    comment 'メニューID',
  menu_name         varchar(50)     not null                   comment 'メニュー名',
  parent_id         bigint(20)      default 0                  comment '親メニューID',
  order_num         int(4)          default 0                  comment '表示順序',
  url               varchar(200)    default '#'                comment 'リクエストアドレス',
  target            varchar(20)     default ''                 comment 'オープン方法（menuItemページタブ menuBlank新しいウィンドウ）',
  menu_type         char(1)         default ''                 comment 'メニュータイプ（Mディレクトリ Cメニュー Fボタン）',
  visible           char(1)         default 0                comment 'メニュー状態（0表示 1非表示）',
  is_refresh        char(1)         default 1                comment 'リフレッシュするか（0リフレッシュ 1リフレッシュしない）',
  perms             varchar(100)    default null               comment '権限識別',
  icon              varchar(100)    default '#'                comment 'メニューアイコン',
  create_by         varchar(64)     default ''                 comment '作成者',
  create_time       datetime                                   comment '作成時間',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新時間',
  remark            varchar(500)    default ''                 comment '備考',
  primary key (menu_id)
) engine=innodb auto_increment=2000 comment = 'メニュー権限テーブル';

-- ----------------------------
-- 初期化-メニュー情報テーブルデータ
-- ----------------------------
-- 一次メニュー
insert into sys_menu values('1', 'システム管理', '0', '1', '#', '', 'M', '0', '1', '', 'fa fa-gear', 'admin', sysdate(), '', null, 'システム管理ディレクトリ');
insert into sys_menu values('2', 'システム監視', '0', '2', '#', '', 'M', '0', '1', '', 'fa fa-video-camera', 'admin', sysdate(), '', null, 'システム監視ディレクトリ');
insert into sys_menu values('3', 'システムツール', '0', '3', '#', '', 'M', '0', '1', '', 'fa fa-bars', 'admin', sysdate(), '', null, 'システムツールディレクトリ');
insert into sys_menu values('4', '若依情報管理システム', '0', '4','https://github.com/bandaokaola/RuoYi_JP', 'menuBlank', 'C', '0', '1', '', 'fa fa-location-arrow', 'admin', sysdate(), '', null, '若依情報管理システムアドレス');
-- 二次メニュー
insert into sys_menu values('100', 'ユーザー管理', '1', '1', '/system/user', '', 'C', '0', '1', 'system:user:view', 'fa fa-user-o', 'admin', sysdate(), '', null, 'ユーザー管理メニュー');
insert into sys_menu values('101', 'ロール管理', '1', '2', '/system/role', '', 'C', '0', '1', 'system:role:view', 'fa fa-user-secret', 'admin', sysdate(), '', null, 'ロール管理メニュー');
insert into sys_menu values('102', 'メニュー管理', '1', '3', '/system/menu', '', 'C', '0', '1', 'system:menu:view', 'fa fa-th-list', 'admin', sysdate(), '', null, 'メニュー管理メニュー');
insert into sys_menu values('103', '部門管理', '1', '4', '/system/dept', '', 'C', '0', '1', 'system:dept:view', 'fa fa-outdent', 'admin', sysdate(), '', null, '部門管理メニュー');
insert into sys_menu values('104', '職位管理', '1', '5', '/system/post', '', 'C', '0', '1', 'system:post:view', 'fa fa-address-card-o', 'admin', sysdate(), '', null, '職位管理メニュー');
insert into sys_menu values('105', '辞書管理', '1', '6', '/system/dict', '', 'C', '0', '1', 'system:dict:view', 'fa fa-bookmark-o', 'admin', sysdate(), '', null, '辞書管理メニュー');
insert into sys_menu values('106', 'パラメータ設定', '1', '7', '/system/config', '', 'C', '0', '1', 'system:config:view', 'fa fa-sun-o', 'admin', sysdate(), '', null, 'パラメータ設定メニュー');
insert into sys_menu values('107', '通知告知', '1', '8', '/system/notice', '', 'C', '0', '1', 'system:notice:view', 'fa fa-bullhorn', 'admin', sysdate(), '', null, '通知告知メニュー');
insert into sys_menu values('108', 'ログ管理', '1', '9', '#', '', 'M', '0', '1', '', 'fa fa-pencil-square-o', 'admin', sysdate(), '', null, 'ログ管理メニュー');
insert into sys_menu values('109', 'オンラインユーザー', '2', '1', '/monitor/online', '', 'C', '0', '1', 'monitor:online:view', 'fa fa-user-circle', 'admin', sysdate(), '', null, 'オンラインユーザーメニュー');
insert into sys_menu values('110', 'タイマータスク', '2', '2', '/monitor/job', '', 'C', '0', '1', 'monitor:job:view', 'fa fa-tasks', 'admin', sysdate(), '', null, 'タイマータスクメニュー');
insert into sys_menu values('111', 'データ監視', '2', '3', '/monitor/data', '', 'C', '0', '1', 'monitor:data:view', 'fa fa-bug', 'admin', sysdate(), '', null, 'データ監視メニュー');
insert into sys_menu values('112', 'サービス監視', '2', '4', '/monitor/server', '', 'C', '0', '1', 'monitor:server:view', 'fa fa-server', 'admin', sysdate(), '', null, 'サービス監視メニュー');
insert into sys_menu values('113', 'キャッシュ監視', '2', '5', '/monitor/cache', '', 'C', '0', '1', 'monitor:cache:view', 'fa fa-cube', 'admin', sysdate(), '', null, 'キャッシュ監視メニュー');
insert into sys_menu values('114', 'フォームビルダー', '3', '1', '/tool/build', '', 'C', '0', '1', 'tool:build:view', 'fa fa-wpforms', 'admin', sysdate(), '', null, 'フォームビルダーメニュー');
insert into sys_menu values('115', 'コード生成', '3', '2', '/tool/gen', '', 'C', '0', '1', 'tool:gen:view', 'fa fa-code', 'admin', sysdate(), '', null, 'コード生成メニュー');
insert into sys_menu values('116', 'システムインターフェース', '3', '3', '/tool/swagger', '', 'C', '0', '1', 'tool:swagger:view', 'fa fa-gg', 'admin', sysdate(), '', null, 'システムインターフェースメニュー');
-- 三次メニュー
insert into sys_menu values('500', '操作ログ', '108', '1', '/monitor/operlog', '', 'C', '0', '1', 'monitor:operlog:view', 'fa fa-address-book', 'admin', sysdate(), '', null, '操作ログメニュー');
insert into sys_menu values('501', 'ログインログ', '108', '2', '/monitor/logininfor', '', 'C', '0', '1', 'monitor:logininfor:view', 'fa fa-file-image-o', 'admin', sysdate(), '', null, 'ログインログメニュー');
-- ユーザー管理ボタン
insert into sys_menu values('1000', 'ユーザー検索', '100', '1', '#', '', 'F', '0', '1', 'system:user:list', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1001', 'ユーザー追加', '100', '2', '#', '', 'F', '0', '1', 'system:user:add', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1002', 'ユーザー修正', '100', '3', '#', '', 'F', '0', '1', 'system:user:edit', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1003', 'ユーザー削除', '100', '4', '#', '', 'F', '0', '1', 'system:user:remove', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1004', 'ユーザーエクスポート', '100', '5', '#', '', 'F', '0', '1', 'system:user:export', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1005', 'ユーザーインポート', '100', '6', '#', '', 'F', '0', '1', 'system:user:import', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1006', 'パスワードリセット', '100', '7', '#', '', 'F', '0', '1', 'system:user:resetPwd', '#', 'admin', sysdate(), '', null, '');
-- ロール管理ボタン
insert into sys_menu values('1007', 'ロール検索', '101', '1', '#', '', 'F', '0', '1', 'system:role:list', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1008', 'ロール追加', '101', '2', '#', '', 'F', '0', '1', 'system:role:add', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1009', 'ロール修正', '101', '3', '#', '', 'F', '0', '1', 'system:role:edit', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1010', 'ロール削除', '101', '4', '#', '', 'F', '0', '1', 'system:role:remove', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1011', 'ロールエクスポート', '101', '5', '#', '', 'F', '0', '1', 'system:role:export', '#', 'admin', sysdate(), '', null, '');
-- メニュー管理ボタン
insert into sys_menu values('1012', 'メニュー検索', '102', '1', '#', '', 'F', '0', '1', 'system:menu:list', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1013', 'ｓメニュー追加', '102', '2', '#', '', 'F', '0', '1', 'system:menu:add', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1014', 'メニュー修正', '102', '3', '#', '', 'F', '0', '1', 'system:menu:edit', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1015', 'メニュー削除', '102', '4', '#', '', 'F', '0', '1', 'system:menu:remove', '#', 'admin', sysdate(), '', null, '');
-- 部門管理ボタン
insert into sys_menu values('1016', '部門検索', '103', '1', '#', '', 'F', '0', '1', 'system:dept:list', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1017', '部門追加', '103', '2', '#', '', 'F', '0', '1', 'system:dept:add', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1018', '部門修正', '103', '3', '#', '', 'F', '0', '1', 'system:dept:edit', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1019', '部門削除', '103', '4', '#', '', 'F', '0', '1', 'system:dept:remove', '#', 'admin', sysdate(), '', null, '');
-- 役職管理ボタン
insert into sys_menu values('1020', '役職検索', '104', '1',  '#', '',  'F', '0', '1', 'system:post:list',        '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1021', '役職追加', '104', '2',  '#', '',  'F', '0', '1', 'system:post:add',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1022', '役職変更', '104', '3',  '#', '',  'F', '0', '1', 'system:post:edit',        '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1023', '役職削除', '104', '4',  '#', '',  'F', '0', '1', 'system:post:remove',      '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1024', '役職エクスポート', '104', '5',  '#', '',  'F', '0', '1', 'system:post:export',      '#', 'admin', sysdate(), '', null, '');
-- 辞書管理ボタン
insert into sys_menu values('1025', '辞書検索', '105', '1',  '#', '',  'F', '0', '1', 'system:dict:list',        '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1026', '辞書追加', '105', '2',  '#', '',  'F', '0', '1', 'system:dict:add',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1027', '辞書変更', '105', '3',  '#', '',  'F', '0', '1', 'system:dict:edit',        '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1028', '辞書削除', '105', '4',  '#', '',  'F', '0', '1', 'system:dict:remove',      '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1029', '辞書エクスポート', '105', '5',  '#', '',  'F', '0', '1', 'system:dict:export',      '#', 'admin', sysdate(), '', null, '');
-- パラメータ設定ボタン
insert into sys_menu values('1030', 'パラメータ検索', '106', '1',  '#', '',  'F', '0', '1', 'system:config:list',      '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1031', 'パラメータ追加', '106', '2',  '#', '',  'F', '0', '1', 'system:config:add',       '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1032', 'パラメータ変更', '106', '3',  '#', '',  'F', '0', '1', 'system:config:edit',      '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1033', 'パラメータ削除', '106', '4',  '#', '',  'F', '0', '1', 'system:config:remove',    '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1034', 'パラメータエクスポート', '106', '5',  '#', '',  'F', '0', '1', 'system:config:export',    '#', 'admin', sysdate(), '', null, '');
-- お知らせボタン
insert into sys_menu values('1035', 'お知らせ検索', '107', '1',  '#', '',  'F', '0', '1', 'system:notice:list',      '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1036', 'お知らせ追加', '107', '2',  '#', '',  'F', '0', '1', 'system:notice:add',       '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1037', 'お知らせ変更', '107', '3',  '#', '',  'F', '0', '1', 'system:notice:edit',      '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1038', 'お知らせ削除', '107', '4',  '#', '',  'F', '0', '1', 'system:notice:remove',    '#', 'admin', sysdate(), '', null, '');
-- 操作ログボタン
insert into sys_menu values('1039', '操作検索', '500', '1',  '#', '',  'F', '0', '1', 'monitor:operlog:list',    '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1040', '操作削除', '500', '2',  '#', '',  'F', '0', '1', 'monitor:operlog:remove',  '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1041', '詳細情報', '500', '3',  '#', '',  'F', '0', '1', 'monitor:operlog:detail',  '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1042', 'ログエクスポート', '500', '4',  '#', '',  'F', '0', '1', 'monitor:operlog:export',  '#', 'admin', sysdate(), '', null, '');
-- ログインログボタン
insert into sys_menu values('1043', 'ログイン検索', '501', '1',  '#', '',  'F', '0', '1', 'monitor:logininfor:list',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1044', 'ログイン削除', '501', '2',  '#', '',  'F', '0', '1', 'monitor:logininfor:remove',       '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1045', 'ログエクスポート', '501', '3',  '#', '',  'F', '0', '1', 'monitor:logininfor:export',       '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1046', 'アカウントロック解除', '501', '4',  '#', '',  'F', '0', '1', 'monitor:logininfor:unlock',       '#', 'admin', sysdate(), '', null, '');
-- オンラインユーザーボタン
insert into sys_menu values('1047', 'オンライン検索', '109', '1',  '#', '',  'F', '0', '1', 'monitor:online:list',             '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1048', '一括強制ログアウト', '109', '2',  '#', '',  'F', '0', '1', 'monitor:online:batchForceLogout', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1049', '単一強制ログアウト', '109', '3',  '#', '',  'F', '0', '1', 'monitor:online:forceLogout',      '#', 'admin', sysdate(), '', null, '');
-- タイマータスクボタン
insert into sys_menu values('1050', 'タスク検索', '110', '1',  '#', '',  'F', '0', '1', 'monitor:job:list',                '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1051', 'タスク追加', '110', '2',  '#', '',  'F', '0', '1', 'monitor:job:add',                 '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1052', 'タスク変更', '110', '3',  '#', '',  'F', '0', '1', 'monitor:job:edit',                '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1053', 'タスク削除', '110', '4',  '#', '',  'F', '0', '1', 'monitor:job:remove',              '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1054', '状態変更', '110', '5',  '#', '',  'F', '0', '1', 'monitor:job:changeStatus',        '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1055', 'タスク詳細', '110', '6',  '#', '',  'F', '0', '1', 'monitor:job:detail',              '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1056', 'タスクエクスポート', '110', '7',  '#', '',  'F', '0', '1', 'monitor:job:export',              '#', 'admin', sysdate(), '', null, '');
-- コード生成ボタン
insert into sys_menu values('1057', '生成検索', '115', '1',  '#', '',  'F', '0', '1', 'tool:gen:list',     '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1058', '生成変更', '115', '2',  '#', '',  'F', '0', '1', 'tool:gen:edit',     '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1059', '生成削除', '115', '3',  '#', '',  'F', '0', '1', 'tool:gen:remove',   '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1060', 'コードプレビュー', '115', '4',  '#', '',  'F', '0', '1', 'tool:gen:preview',  '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1061', 'コード生成', '115', '5',  '#', '',  'F', '0', '1', 'tool:gen:code',     '#', 'admin', sysdate(), '', null, '');


-- ----------------------------
-- 6、ユーザーとロールの関連テーブル ユーザーN-1ロール
-- ----------------------------
drop table if exists sys_user_role;
create table sys_user_role (
  user_id   bigint(20) not null comment 'ユーザーID',
  role_id   bigint(20) not null comment 'ロールID',
  primary key(user_id, role_id)
) engine=innodb comment = 'ユーザーとロールの関連テーブル';

-- ----------------------------
-- 初期化-ユーザーとロールの関連テーブルのデータ
-- ----------------------------
insert into sys_user_role values ('1', '1');
insert into sys_user_role values ('2', '2');


-- ----------------------------
-- 7、ロールとメニューの関連テーブル ロール1-Nメニュー
-- ----------------------------
drop table if exists sys_role_menu;
create table sys_role_menu (
  role_id   bigint(20) not null comment 'ロールID',
  menu_id   bigint(20) not null comment 'メニューID',
  primary key(role_id, menu_id)
) engine=innodb comment = 'ロールとメニューの関連テーブル';

-- ----------------------------
-- 初期化-ロールとメニューの関連テーブルのデータ
-- ----------------------------
insert into sys_role_menu values ('2', '1');
insert into sys_role_menu values ('2', '2');
insert into sys_role_menu values ('2', '3');
insert into sys_role_menu values ('2', '4');
insert into sys_role_menu values ('2', '100');
insert into sys_role_menu values ('2', '101');
insert into sys_role_menu values ('2', '102');
insert into sys_role_menu values ('2', '103');
insert into sys_role_menu values ('2', '104');
insert into sys_role_menu values ('2', '105');
insert into sys_role_menu values ('2', '106');
insert into sys_role_menu values ('2', '107');
insert into sys_role_menu values ('2', '108');
insert into sys_role_menu values ('2', '109');
insert into sys_role_menu values ('2', '110');
insert into sys_role_menu values ('2', '111');
insert into sys_role_menu values ('2', '112');
insert into sys_role_menu values ('2', '113');
insert into sys_role_menu values ('2', '114');
insert into sys_role_menu values ('2', '115');
insert into sys_role_menu values ('2', '116');
insert into sys_role_menu values ('2', '500');
insert into sys_role_menu values ('2', '501');
insert into sys_role_menu values ('2', '1000');
insert into sys_role_menu values ('2', '1001');
insert into sys_role_menu values ('2', '1002');
insert into sys_role_menu values ('2', '1003');
insert into sys_role_menu values ('2', '1004');
insert into sys_role_menu values ('2', '1005');
insert into sys_role_menu values ('2', '1006');
insert into sys_role_menu values ('2', '1007');
insert into sys_role_menu values ('2', '1008');
insert into sys_role_menu values ('2', '1009');
insert into sys_role_menu values ('2', '1010');
insert into sys_role_menu values ('2', '1011');
insert into sys_role_menu values ('2', '1012');
insert into sys_role_menu values ('2', '1013');
insert into sys_role_menu values ('2', '1014');
insert into sys_role_menu values ('2', '1015');
insert into sys_role_menu values ('2', '1016');
insert into sys_role_menu values ('2', '1017');
insert into sys_role_menu values ('2', '1018');
insert into sys_role_menu values ('2', '1019');
insert into sys_role_menu values ('2', '1020');
insert into sys_role_menu values ('2', '1021');
insert into sys_role_menu values ('2', '1022');
insert into sys_role_menu values ('2', '1023');
insert into sys_role_menu values ('2', '1024');
insert into sys_role_menu values ('2', '1025');
insert into sys_role_menu values ('2', '1026');
insert into sys_role_menu values ('2', '1027');
insert into sys_role_menu values ('2', '1028');
insert into sys_role_menu values ('2', '1029');
insert into sys_role_menu values ('2', '1030');
insert into sys_role_menu values ('2', '1031');
insert into sys_role_menu values ('2', '1032');
insert into sys_role_menu values ('2', '1033');
insert into sys_role_menu values ('2', '1034');
insert into sys_role_menu values ('2', '1035');
insert into sys_role_menu values ('2', '1036');
insert into sys_role_menu values ('2', '1037');
insert into sys_role_menu values ('2', '1038');
insert into sys_role_menu values ('2', '1039');
insert into sys_role_menu values ('2', '1040');
insert into sys_role_menu values ('2', '1041');
insert into sys_role_menu values ('2', '1042');
insert into sys_role_menu values ('2', '1043');
insert into sys_role_menu values ('2', '1044');
insert into sys_role_menu values ('2', '1045');
insert into sys_role_menu values ('2', '1046');
insert into sys_role_menu values ('2', '1047');
insert into sys_role_menu values ('2', '1048');
insert into sys_role_menu values ('2', '1049');
insert into sys_role_menu values ('2', '1050');
insert into sys_role_menu values ('2', '1051');
insert into sys_role_menu values ('2', '1052');
insert into sys_role_menu values ('2', '1053');
insert into sys_role_menu values ('2', '1054');
insert into sys_role_menu values ('2', '1055');
insert into sys_role_menu values ('2', '1056');
insert into sys_role_menu values ('2', '1057');
insert into sys_role_menu values ('2', '1058');
insert into sys_role_menu values ('2', '1059');
insert into sys_role_menu values ('2', '1060');
insert into sys_role_menu values ('2', '1061');

-- ----------------------------
-- 8、ロールと部署の関連テーブル ロール1-N部署
-- ----------------------------
drop table if exists sys_role_dept;
create table sys_role_dept (
  role_id   bigint(20) not null comment 'ロールID',
  dept_id   bigint(20) not null comment '部署ID',
  primary key(role_id, dept_id)
) engine=innodb comment = 'ロールと部署の関連テーブル';

-- ----------------------------
-- 初期化-ロールと部署の関連テーブルのデータ
-- ----------------------------
insert into sys_role_dept values ('2', '100');
insert into sys_role_dept values ('2', '101');
insert into sys_role_dept values ('2', '105');

-- ----------------------------
-- 9、ユーザーとポストの関連テーブル ユーザー1-Nポスト
-- ----------------------------
drop table if exists sys_user_post;
create table sys_user_post
(
  user_id   bigint(20) not null comment 'ユーザーID',
  post_id   bigint(20) not null comment 'ポストID',
  primary key (user_id, post_id)
) engine=innodb comment = 'ユーザーとポストの関連テーブル';

-- ----------------------------
-- 初期化-ユーザーとポストの関連テーブルのデータ
-- ----------------------------
insert into sys_user_post values ('1', '1');
insert into sys_user_post values ('2', '2');


-- ----------------------------
-- 10、操作ログ記録
-- ----------------------------
drop table if exists sys_oper_log;
create table sys_oper_log (
  oper_id           bigint(20)      not null auto_increment    comment 'ログ主キー',
  title             varchar(50)     default ''                 comment 'モジュールタイトル',
  business_type     int(2)          default 0                  comment '業務タイプ（0その他 1新規 2修正 3削除）',
  method            varchar(100)    default ''                 comment 'メソッド名',
  request_method    varchar(10)     default ''                 comment 'リクエスト方式',
  operator_type     int(1)          default 0                  comment '操作カテゴリ（0その他 1バックエンドユーザー 2モバイルユーザー）',
  oper_name         varchar(50)     default ''                 comment '操作者',
  dept_name         varchar(50)     default ''                 comment '部署名',
  oper_url          varchar(255)    default ''                 comment 'リクエストURL',
  oper_ip           varchar(128)    default ''                 comment 'ホストアドレス',
  oper_location     varchar(255)    default ''                 comment '操作場所',
  oper_param        varchar(2000)   default ''                 comment 'リクエストパラメータ',
  json_result       varchar(2000)   default ''                 comment '戻りパラメータ',
  status            int(1)          default 0                  comment '操作ステータス（0正常 1異常）',
  error_msg         varchar(2000)   default ''                 comment 'エラーメッセージ',
  oper_time         datetime                                   comment '操作時間',
  cost_time         bigint(20)      default 0                  comment '処理時間',
  primary key (oper_id),
  key idx_sys_oper_log_bt (business_type),
  key idx_sys_oper_log_s  (status),
  key idx_sys_oper_log_ot (oper_time)
) engine=innodb auto_increment=100 comment = '操作ログ記録';


-- ----------------------------
-- 11、辞書タイプテーブル
-- ----------------------------
drop table if exists sys_dict_type;
create table sys_dict_type
(
  dict_id          bigint(20)      not null auto_increment    comment '辞書主キー',
  dict_name        varchar(100)    default ''                 comment '辞書名',
  dict_type        varchar(100)    default ''                 comment '辞書タイプ',
  status           char(1)         default '0'                comment 'ステータス（0正常 1停止）',
  create_by        varchar(64)     default ''                 comment '作成者',
  create_time      datetime                                   comment '作成日時',
  update_by        varchar(64)     default ''                 comment '更新者',
  update_time      datetime                                   comment '更新日時',
  remark           varchar(500)    default null               comment '備考',
  primary key (dict_id),
  unique (dict_type)
) engine=innodb auto_increment=100 comment = '辞書タイプテーブル';

insert into sys_dict_type values(1,  'ユーザー性別', 'sys_user_sex',        '0', 'admin', sysdate(), '', null, 'ユーザー性別リスト');
insert into sys_dict_type values(2,  'メニューステータス', 'sys_show_hide',       '0', 'admin', sysdate(), '', null, 'メニューステータスリスト');
insert into sys_dict_type values(3,  'システムスイッチ', 'sys_normal_disable',  '0', 'admin', sysdate(), '', null, 'システムスイッチリスト');
insert into sys_dict_type values(4,  'ジョブステータス', 'sys_job_status',      '0', 'admin', sysdate(), '', null, 'ジョブステータスリスト');
insert into sys_dict_type values(5,  'ジョブグループ', 'sys_job_group',       '0', 'admin', sysdate(), '', null, 'ジョブグループリスト');
insert into sys_dict_type values(6,  'システムの有無', 'sys_yes_no',          '0', 'admin', sysdate(), '', null, 'システムの有無リスト');
insert into sys_dict_type values(7,  '通知タイプ', 'sys_notice_type',     '0', 'admin', sysdate(), '', null, '通知タイプリスト');
insert into sys_dict_type values(8,  '通知ステータス', 'sys_notice_status',   '0', 'admin', sysdate(), '', null, '通知ステータスリスト');
insert into sys_dict_type values(9,  '操作タイプ', 'sys_oper_type',       '0', 'admin', sysdate(), '', null, '操作タイプリスト');
insert into sys_dict_type values(10, 'システムステータス', 'sys_common_status',   '0', 'admin', sysdate(), '', null, 'ログインステータスリスト');


-- ----------------------------
-- 12、辞書データテーブル
-- ----------------------------
drop table if exists sys_dict_data;
create table sys_dict_data
(
  dict_code        bigint(20)      not null auto_increment    comment '辞書コード',
  dict_sort        int(4)          default 0                  comment '辞書ソート',
  dict_label       varchar(100)    default ''                 comment '辞書ラベル',
  dict_value       varchar(100)    default ''                 comment '辞書キー',
  dict_type        varchar(100)    default ''                 comment '辞書タイプ',
  css_class        varchar(100)    default null               comment 'スタイル属性（その他の拡張スタイル）',
  list_class       varchar(100)    default null               comment 'テーブル再表示スタイル',
  is_default       char(1)         default 'N'                comment 'デフォルトかどうか（YはNいいえ）',
  status           char(1)         default '0'                comment 'ステータス（0正常 1停止）',
  create_by        varchar(64)     default ''                 comment '作成者',
  create_time      datetime                                   comment '作成日時',
  update_by        varchar(64)     default ''                 comment '更新者',
  update_time      datetime                                   comment '更新日時',
  remark           varchar(500)    default null               comment '備考',
  primary key (dict_code)
) engine=innodb auto_increment=100 comment = '辞書データテーブル';

insert into sys_dict_data values(1,  1,  '男',       '0',       'sys_user_sex',        '',   '',        'Y', '0', 'admin', sysdate(), '', null, '性別男');
insert into sys_dict_data values(2,  2,  '女',       '1',       'sys_user_sex',        '',   '',        'N', '0', 'admin', sysdate(), '', null, '性別女');
insert into sys_dict_data values(3,  3,  '未知',     '2',       'sys_user_sex',        '',   '',        'N', '0', 'admin', sysdate(), '', null, '性別未知');
insert into sys_dict_data values(4,  1,  '表示',     '0',       'sys_show_hide',       '',   'primary', 'Y', '0', 'admin', sysdate(), '', null, '表示メニュー');
insert into sys_dict_data values(5,  2,  '非表示',     '1',       'sys_show_hide',       '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '非表示メニュー');
insert into sys_dict_data values(6,  1,  '正常',     '0',       'sys_normal_disable',  '',   'primary', 'Y', '0', 'admin', sysdate(), '', null, '正常ステータス');
insert into sys_dict_data values(7,  2,  '停止',     '1',       'sys_normal_disable',  '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '停止ステータス');
insert into sys_dict_data values(8,  1,  '正常',     '0',       'sys_job_status',      '',   'primary', 'Y', '0', 'admin', sysdate(), '', null, '正常ステータス');
insert into sys_dict_data values(9,  2,  '一時停止',     '1',       'sys_job_status',      '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '停止ステータス');
insert into sys_dict_data values(10, 1,  'デフォルト',     'DEFAULT', 'sys_job_group',       '',   '',        'Y', '0', 'admin', sysdate(), '', null, 'デフォルトグループ');
insert into sys_dict_data values(11, 2,  'システム',     'SYSTEM',  'sys_job_group',       '',   '',        'N', '0', 'admin', sysdate(), '', null, 'システムグループ');
insert into sys_dict_data values(12, 1,  'はい',       'Y',       'sys_yes_no',          '',   'primary', 'Y', '0', 'admin', sysdate(), '', null, 'システムデフォルトはい');
insert into sys_dict_data values(13, 2,  'いいえ',       'N',       'sys_yes_no',          '',   'danger',  'N', '0', 'admin', sysdate(), '', null, 'システムデフォルトいいえ');
insert into sys_dict_data values(14, 1,  '通知',     '1',       'sys_notice_type',     '',   'warning', 'Y', '0', 'admin', sysdate(), '', null, '通知');
insert into sys_dict_data values(15, 2,  '公告',     '2',       'sys_notice_type',     '',   'success', 'N', '0', 'admin', sysdate(), '', null, '公告');
insert into sys_dict_data values(16, 1,  '正常',     '0',       'sys_notice_status',   '',   'primary', 'Y', '0', 'admin', sysdate(), '', null, '正常ステータス');
insert into sys_dict_data values(17, 2,  '閉じる',     '1',       'sys_notice_status',   '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '閉じるステータス');
insert into sys_dict_data values(18, 99, 'その他',     '0',       'sys_oper_type',       '',   'info',    'N', '0', 'admin', sysdate(), '', null, 'その他操作');
insert into sys_dict_data values(19, 1,  '新規',     '1',       'sys_oper_type',       '',   'info',    'N', '0', 'admin', sysdate(), '', null, '新規操作');
insert into sys_dict_data values(20, 2,  '修正',     '2',       'sys_oper_type',       '',   'info',    'N', '0', 'admin', sysdate(), '', null, '修正操作');
insert into sys_dict_data values(21, 3,  '削除',     '3',       'sys_oper_type',       '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '削除操作');
insert into sys_dict_data values(22, 4,  '認可',     '4',       'sys_oper_type',       '',   'primary', 'N', '0', 'admin', sysdate(), '', null, '認可操作');
insert into sys_dict_data values(23, 5,  'エクスポート',     '5',       'sys_oper_type',       '',   'warning', 'N', '0', 'admin', sysdate(), '', null, 'エクスポート操作');
insert into sys_dict_data values(24, 6,  'インポート',     '6',       'sys_oper_type',       '',   'warning', 'N', '0', 'admin', sysdate(), '', null, 'インポート操作');
insert into sys_dict_data values(25, 7,  '強制退出',     '7',       'sys_oper_type',       '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '強制退出操作');
insert into sys_dict_data values(26, 8,  'コード生成', '8',       'sys_oper_type',       '',   'warning', 'N', '0', 'admin', sysdate(), '', null, '生成操作');
insert into sys_dict_data values(27, 9,  'データクリア', '9',       'sys_oper_type',       '',   'danger',  'N', '0', 'admin', sysdate(), '', null, 'データクリア操作');
insert into sys_dict_data values(28, 1,  '成功',     '0',       'sys_common_status',   '',   'primary', 'N', '0', 'admin', sysdate(), '', null, '正常ステータス');
insert into sys_dict_data values(29, 2,  '失敗',     '1',       'sys_common_status',   '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '停止ステータス');


-- ----------------------------
-- 13、パラメータ設定テーブル
-- ----------------------------
drop table if exists sys_config;
create table sys_config (
  config_id         int(5)          not null auto_increment    comment 'パラメータ主キー',
  config_name       varchar(100)    default ''                 comment 'パラメータ名称',
  config_key        varchar(100)    default ''                 comment 'パラメータキー名',
  config_value      varchar(500)    default ''                 comment 'パラメータキー値',
  config_type       char(1)         default 'N'                comment 'システム組み込み（Yははい Nはいいえ）',
  create_by         varchar(64)     default ''                 comment '作成者',
  create_time       datetime                                   comment '作成日時',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新日時',
  remark            varchar(500)    default null               comment '備考',
  primary key (config_id)
) engine=innodb auto_increment=100 comment = 'パラメータ設定テーブル';

insert into sys_config values(1,  'メインフレームページ-デフォルトのスキンスタイル名',     'sys.index.skinName',               'skin-blue',     'Y', 'admin', sysdate(), '', null, '青 skin-blue、緑 skin-green、紫 skin-purple、赤 skin-red、黄 skin-yellow');
insert into sys_config values(2,  'ユーザー管理-アカウント初期パスワード',         'sys.user.initPassword',            '123456',        'Y', 'admin', sysdate(), '', null, '初期化パスワード 123456');
insert into sys_config values(3,  'メインフレームページ-サイドバーのテーマ',           'sys.index.sideTheme',              'theme-dark',    'Y', 'admin', sysdate(), '', null, 'ダークテーマtheme-dark、ライトテーマtheme-light、ブルーテーマtheme-blue');
insert into sys_config values(4,  'アカウントセルフサービス-ユーザー登録機能を有効にするかどうか', 'sys.account.registerUser',         'false',         'Y', 'admin', sysdate(), '', null, 'ユーザー登録機能を有効にするかどうか（true有効、false無効）');
insert into sys_config values(5,  'ユーザー管理-パスワードの文字範囲',         'sys.account.chrtype',              '0',             'Y', 'admin', sysdate(), '', null, 'デフォルトは任意の文字範囲、0任意（パスワードには任意の文字が入力できます）、1数字（パスワードは0-9の数字のみ）、2英字（パスワードはa-zおよびA-Zの英字のみ）、3英数字（パスワードには英字、数字が必要）,4英数字と特殊文字（現在サポートされている特殊文字は次のとおりです：~!@#$%^&*()-=_+）');
insert into sys_config values(6,  'ユーザー管理-初期パスワード変更ポリシー',     'sys.account.initPasswordModify',   '0',             'Y', 'admin', sysdate(), '', null, '0：初期パスワード変更ポリシーは無効で、何のヒントもありません、1：ユーザーに通知、初期パスワードが変更されていない場合、ログイン時にパスワード変更ダイアログが表示されます');
insert into sys_config values(7,  'ユーザー管理-アカウントパスワード更新サイクル',     'sys.account.passwordValidateDays', '0',             'Y', 'admin', sysdate(), '', null, 'パスワード更新サイクル（数字を入力し、データの初期値は0で制限されません、変更する場合は0より大きく365より小さい正の整数でなければなりません）、このサイクルを超えてシステムにログインすると、ログイン時にパスワード変更ダイアログが表示されます');
insert into sys_config values(8,  'メインフレームページ-メニューナビゲーションスタイル',     'sys.index.menuStyle',              'DEFAULT',       'Y', 'admin', sysdate(), '', null, 'メニューナビゲーションのスタイル（defaultは左側のナビゲーションメニュー、topnavはトップのナビゲーションメニュー）');
insert into sys_config values(9,  'メインフレームページ-フッターを有効にするかどうか',         'sys.index.footer',                 'true',          'Y', 'admin', sysdate(), '', null, 'フッターを有効にするかどうか（true表示、false非表示）');
insert into sys_config values(10, 'メインフレームページ-タブを有効にするかどうか',         'sys.index.tagsView',               'true',          'Y', 'admin', sysdate(), '', null, 'メニューの多重タブを有効にするかどうか（true表示、false非表示）');
insert into sys_config values(11, 'ユーザーログイン-ブラックリスト',           'sys.login.blackIPList',            '',              'Y', 'admin', sysdate(), '', null, 'ログインIPブラックリスト制限を設定し、複数の一致項目は;で区切られ、一致サポート（*ワイルドカード、ネットワーク）');


-- ----------------------------
-- 14、システムアクセス記録
-- ----------------------------
drop table if exists sys_logininfor;
create table sys_logininfor (
  info_id        bigint(20)     not null auto_increment   comment 'アクセスID',
  login_name     varchar(50)    default ''                comment 'ログインアカウント',
  ipaddr         varchar(128)   default ''                comment 'ログインIPアドレス',
  login_location varchar(255)   default ''                comment 'ログイン場所',
  browser        varchar(50)    default ''                comment 'ブラウザタイプ',
  os             varchar(50)    default ''                comment 'オペレーティングシステム',
  status         char(1)        default '0'               comment 'ログインステータス（0成功 1失敗）',
  msg            varchar(255)   default ''                comment 'メッセージ',
  login_time     datetime                                 comment 'アクセス時間',
  primary key (info_id),
  key idx_sys_logininfor_s  (status),
  key idx_sys_logininfor_lt (login_time)
) engine=innodb auto_increment=100 comment = 'システムアクセス記録';


-- ----------------------------
-- 15、オンラインユーザー記録
-- ----------------------------
drop table if exists sys_user_online;
create table sys_user_online (
  sessionId         varchar(50)   default ''                comment 'ユーザーセッションID',
  login_name        varchar(50)   default ''                comment 'ログインアカウント',
  dept_name         varchar(50)   default ''                comment '部門名',
  ipaddr            varchar(128)  default ''                comment 'ログインIPアドレス',
  login_location    varchar(255)  default ''                comment 'ログイン場所',
  browser           varchar(50)   default ''                comment 'ブラウザタイプ',
  os                varchar(50)   default ''                comment 'オペレーティングシステム',
  status            varchar(10)   default ''                comment 'オンラインステータスon_lineオンラインoff_lineオフライン',
  start_timestamp   datetime                                comment 'セッション作成時間',
  last_access_time  datetime                                comment 'セッション最終アクセス時間',
  expire_time       int(5)        default 0                 comment 'タイムアウト時間、単位は分',
  primary key (sessionId)
) engine=innodb comment = 'オンラインユーザー記録';


-- ----------------------------
-- 16、定期実行タスクスケジュールテーブル
-- ----------------------------
drop table if exists sys_job;
create table sys_job (
  job_id              bigint(20)    not null auto_increment    comment 'タスクID',
  job_name            varchar(64)   default ''                 comment 'タスク名',
  job_group           varchar(64)   default 'DEFAULT'          comment 'タスクグループ名',
  invoke_target       varchar(500)  not null                   comment '呼び出しターゲット文字列',
  cron_expression     varchar(255)  default ''                 comment 'cron実行式',
  misfire_policy      varchar(20)   default '3'                comment '計画実行エラーポリシー（1即時実行 2一回のみ実行 3実行を放棄）',
  concurrent          char(1)       default '1'                comment '並行実行するかどうか（0許可 1禁止）',
  status              char(1)       default '0'                comment 'ステータス（0正常 1一時停止）',
  create_by           varchar(64)   default ''                 comment '作成者',
  create_time         datetime                                 comment '作成日時',
  update_by           varchar(64)   default ''                 comment '更新者',
  update_time         datetime                                 comment '更新日時',
  remark              varchar(500)  default ''                 comment '備考情報',
  primary key (job_id, job_name, job_group)
) engine=innodb auto_increment=100 comment = '定期実行タスクスケジュールテーブル';

insert into sys_job values(1, 'システムデフォルト（パラメータなし）', 'DEFAULT', 'ryTask.ryNoParams',        '0/10 * * * * ?', '3', '1', '1', 'admin', sysdate(), '', null, '');
insert into sys_job values(2, 'システムデフォルト（パラメータあり）', 'DEFAULT', 'ryTask.ryParams(\'ry\')',  '0/15 * * * * ?', '3', '1', '1', 'admin', sysdate(), '', null, '');
insert into sys_job values(3, 'システムデフォルト（複数パラメータあり）', 'DEFAULT', 'ryTask.ryMultipleParams(\'ry\', true, 2000L, 316.50D, 100)',  '0/20 * * * * ?', '3', '1', '1', 'admin', sysdate(), '', null, '');


-- ----------------------------
-- 17、定期実行タスクスケジュールログテーブル
-- ----------------------------
drop table if exists sys_job_log;
create table sys_job_log (
  job_log_id          bigint(20)     not null auto_increment    comment 'タスクログID',
  job_name            varchar(64)    not null                   comment 'タスク名',
  job_group           varchar(64)    not null                   comment 'タスクグループ名',
  invoke_target       varchar(500)   not null                   comment '呼び出しターゲット文字列',
  job_message         varchar(500)                              comment 'ログ情報',
  status              char(1)        default '0'                comment '実行ステータス（0正常 1失敗）',
  exception_info      varchar(2000)  default ''                 comment '例外情報',
  create_time         datetime                                  comment '作成日時',
  primary key (job_log_id)
) engine=innodb comment = '定期実行タスクスケジュールログテーブル';


-- ----------------------------
-- 18、お知らせ表
-- ----------------------------
drop table if exists sys_notice;
create table sys_notice (
  notice_id         int(4)          not null auto_increment    comment 'お知らせID',
  notice_title      varchar(50)     not null                   comment 'お知らせタイトル',
  notice_type       char(1)         not null                   comment 'お知らせタイプ（1通知 2お知らせ）',
  notice_content    varchar(2000)   default null               comment 'お知らせ内容',
  status            char(1)         default '0'                comment 'お知らせステータス（0正常 1閉鎖）',
  create_by         varchar(64)     default ''                 comment '作成者',
  create_time       datetime                                   comment '作成日時',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新日時',
  remark            varchar(255)    default null               comment '備考',
  primary key (notice_id)
) engine=innodb auto_increment=10 comment = 'お知らせ表';

-- ----------------------------
-- 初期化 - お知らせ情報表 データ
-- ----------------------------
insert into sys_notice values('1', 'お知らせ：2018-07-01 若依の新バージョンがリリースされました', '2', '新バージョンの内容', '0', 'admin', sysdate(), '', null, '管理者');
insert into sys_notice values('2', 'メンテナンス通知：2018-07-01 若依システムの深夜メンテナンス', '1', 'メンテナンスの内容', '0', 'admin', sysdate(), '', null, '管理者');


-- ----------------------------
-- 19、コード生成ビジネステーブル
-- ----------------------------
drop table if exists gen_table;
create table gen_table (
  table_id             bigint(20)      not null auto_increment    comment '番号',
  table_name           varchar(200)    default ''                 comment 'テーブル名',
  table_comment        varchar(500)    default ''                 comment 'テーブルの説明',
  sub_table_name       varchar(64)     default null               comment '関連するサブテーブルのテーブル名',
  sub_table_fk_name    varchar(64)     default null               comment 'サブテーブルに関連する外部キー名',
  class_name           varchar(100)    default ''                 comment 'エンティティクラスの名前',
  tpl_category         varchar(200)    default 'crud'             comment '使用するテンプレート（crud単一テーブル操作 treeツリーテーブル操作 sub親子テーブル操作）',
  package_name         varchar(100)                               comment '生成パッケージパス',
  module_name          varchar(30)                                comment '生成モジュール名',
  business_name        varchar(30)                                comment '生成ビジネス名',
  function_name        varchar(50)                                comment '生成機能名',
  function_author      varchar(50)                                comment '生成機能の作者',
  gen_type             char(1)         default '0'                comment '生成コードの方法（0 ZIP圧縮ファイル 1カスタムパス）',
  gen_path             varchar(200)    default '/'                comment '生成パス（未記入の場合はデフォルトのプロジェクトパス）',
  options              varchar(1000)                              comment 'その他の生成オプション',
  create_by            varchar(64)     default ''                 comment '作成者',
  create_time 	       datetime                                   comment '作成日時',
  update_by            varchar(64)     default ''                 comment '更新者',
  update_time          datetime                                   comment '更新日時',
  remark               varchar(500)    default null               comment '備考',
  primary key (table_id)
) engine=innodb auto_increment=1 comment = 'コード生成ビジネステーブル';


-- ----------------------------
-- 20、コード生成ビジネステーブルのフィールド
-- ----------------------------
drop table if exists gen_table_column;
create table gen_table_column (
  column_id         bigint(20)      not null auto_increment    comment '番号',
  table_id          bigint(20)                                 comment '所属テーブル番号',
  column_name       varchar(200)                               comment '列名',
  column_comment    varchar(500)                               comment '列の説明',
  column_type       varchar(100)                               comment '列の型',
  java_type         varchar(500)                               comment 'JAVAの型',
  java_field        varchar(200)                               comment 'JAVAのフィールド名',
  is_pk             char(1)                                    comment '主キーかどうか（1はい）',
  is_increment      char(1)                                    comment '自動増分かどうか（1はい）',
  is_required       char(1)                                    comment '必須かどうか（1はい）',
  is_insert         char(1)                                    comment '挿入フィールドかどうか（1はい）',
  is_edit           char(1)                                    comment '編集フィールドかどうか（1はい）',
  is_list           char(1)                                    comment 'リストフィールドかどうか（1はい）',
  is_query          char(1)                                    comment 'クエリフィールドかどうか（1はい）',
  query_type        varchar(200)    default 'EQ'               comment 'クエリの方法（等しい、等しくない、より大きい、より小さい、範囲）',
  html_type         varchar(200)                               comment '表示タイプ（テキストボックス、テキストエリア、ドロップダウン、チェックボックス、ラジオボタン、日付ピッカー）',
  dict_type         varchar(200)    default ''                 comment '辞書の型',
  sort              int                                        comment '並び替え',
  create_by         varchar(64)     default ''                 comment '作成者',
  create_time 	    datetime                                   comment '作成日時',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新日時',
  primary key (column_id)
) engine=innodb auto_increment=1 comment = 'コード生成ビジネステーブルのフィールド';
