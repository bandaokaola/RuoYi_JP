-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('お客様テーブル', '3', '1', '/system/customer', 'C', '0', 'system:customer:view', '#', 'admin', sysdate(), '', null, 'お客様テーブル菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('お客様テーブル查询', @parentId, '1',  '#',  'F', '0', 'system:customer:list',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('お客様テーブル新增', @parentId, '2',  '#',  'F', '0', 'system:customer:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('お客様テーブル修改', @parentId, '3',  '#',  'F', '0', 'system:customer:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('お客様テーブル删除', @parentId, '4',  '#',  'F', '0', 'system:customer:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('お客様テーブル导出', @parentId, '5',  '#',  'F', '0', 'system:customer:export',       '#', 'admin', sysdate(), '', null, '');
