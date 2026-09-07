DROP DATABASE IF EXISTS community_party_155;
CREATE DATABASE community_party_155 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE community_party_155;

CREATE TABLE sys_user (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(60) NOT NULL UNIQUE,
  password VARCHAR(100) NOT NULL,
  nickname VARCHAR(80),
  role VARCHAR(40),
  department VARCHAR(100),
  phone VARCHAR(30),
  email VARCHAR(100),
  status INT DEFAULT 1,
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO sys_user (username, password, nickname, role, department, phone, email, status, created_time, updated_time) VALUES
('admin', '123456', '系统管理员', 'ADMIN', '社区党建服务中心', '13915500001', 'admin@party155.local', 1, NOW(), NOW()),
('secretary', '123456', '党务书记', 'SECRETARY', '社区党建服务中心', '13915500002', 'secretary@party155.local', 1, NOW(), NOW()),
('organizer', '123456', '活动组织员', 'ORGANIZER', '社区党建服务中心', '13915500003', 'organizer@party155.local', 1, NOW(), NOW()),
('volunteer', '123456', '志愿骨干', 'VOLUNTEER', '社区党建服务中心', '13915500004', 'volunteer@party155.local', 1, NOW(), NOW()),
('resident', '123456', '社区党员', 'RESIDENT', '社区党建服务中心', '13915500005', 'resident@party155.local', 1, NOW(), NOW());

CREATE TABLE party_branch (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(120),
  record_name VARCHAR(120),
  category VARCHAR(120),
  owner_name VARCHAR(120),
  plan_time VARCHAR(120),
  status VARCHAR(40),
  remark VARCHAR(255),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO party_branch (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('155-01-001', '党组织维护示例一', '组织类型', '负责人A', '2026-05-16 09:00', 'BOOKED', '组织编号、组织名称、组织类型、负责人、成立时间和组织状态维护', NOW(), NOW()),
('155-01-002', '党组织维护示例二', '组织类型', '负责人B', '2026-05-17 14:00', 'VERIFIED', '党组织维护演示数据二', NOW(), NOW());

CREATE TABLE member_profile (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(120),
  record_name VARCHAR(120),
  category VARCHAR(120),
  owner_name VARCHAR(120),
  plan_time VARCHAR(120),
  status VARCHAR(40),
  remark VARCHAR(255),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO member_profile (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('155-02-001', '党员档案示例一', '所属支部', '联系方式A', '2026-05-16 09:00', 'SCHEDULED', '党员编号、党员姓名、所属支部、联系方式、入党时间和档案状态维护', NOW(), NOW()),
('155-02-002', '党员档案示例二', '所属支部', '联系方式B', '2026-05-17 14:00', 'PROCESSING', '党员档案演示数据二', NOW(), NOW());

CREATE TABLE party_activity (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(120),
  record_name VARCHAR(120),
  category VARCHAR(120),
  owner_name VARCHAR(120),
  plan_time VARCHAR(120),
  status VARCHAR(40),
  remark VARCHAR(255),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO party_activity (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('155-03-001', '党建活动示例一', '活动类型', '组织人A', '2026-05-16 09:00', 'VERIFIED', '活动编号、活动主题、活动类型、组织人、活动时间和发布状态维护', NOW(), NOW()),
('155-03-002', '党建活动示例二', '活动类型', '组织人B', '2026-05-17 14:00', 'FINISHED', '党建活动演示数据二', NOW(), NOW());

CREATE TABLE activity_registration (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(120),
  record_name VARCHAR(120),
  category VARCHAR(120),
  owner_name VARCHAR(120),
  plan_time VARCHAR(120),
  status VARCHAR(40),
  remark VARCHAR(255),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO activity_registration (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('155-04-001', '活动报名示例一', '报名类型', '报名人A', '2026-05-16 09:00', 'PROCESSING', '报名编号、党建活动、报名类型、报名人、报名时间和报名状态维护', NOW(), NOW()),
('155-04-002', '活动报名示例二', '报名类型', '报名人B', '2026-05-17 14:00', 'WARNING', '活动报名演示数据二', NOW(), NOW());

CREATE TABLE attendance_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(120),
  record_name VARCHAR(120),
  category VARCHAR(120),
  owner_name VARCHAR(120),
  plan_time VARCHAR(120),
  status VARCHAR(40),
  remark VARCHAR(255),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO attendance_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('155-05-001', '签到记录示例一', '签到方式', '签到人A', '2026-05-16 09:00', 'FINISHED', '签到编号、关联活动、签到方式、签到人、签到时间和签到状态维护', NOW(), NOW()),
('155-05-002', '签到记录示例二', '签到方式', '签到人B', '2026-05-17 14:00', 'PUBLISHED', '签到记录演示数据二', NOW(), NOW());

CREATE TABLE volunteer_task (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(120),
  record_name VARCHAR(120),
  category VARCHAR(120),
  owner_name VARCHAR(120),
  plan_time VARCHAR(120),
  status VARCHAR(40),
  remark VARCHAR(255),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO volunteer_task (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('155-06-001', '志愿任务示例一', '服务类型', '负责人A', '2026-05-16 09:00', 'WARNING', '任务编号、任务名称、服务类型、负责人、服务时间和任务状态维护', NOW(), NOW()),
('155-06-002', '志愿任务示例二', '服务类型', '负责人B', '2026-05-17 14:00', 'ACTIVE', '志愿任务演示数据二', NOW(), NOW());

CREATE TABLE point_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(120),
  record_name VARCHAR(120),
  category VARCHAR(120),
  owner_name VARCHAR(120),
  plan_time VARCHAR(120),
  status VARCHAR(40),
  remark VARCHAR(255),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO point_record (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('155-07-001', '积分记录示例一', '积分类型', '获得人A', '2026-05-16 09:00', 'PUBLISHED', '积分编号、积分事项、积分类型、获得人、积分时间和积分状态维护', NOW(), NOW()),
('155-07-002', '积分记录示例二', '积分类型', '获得人B', '2026-05-17 14:00', 'BOOKED', '积分记录演示数据二', NOW(), NOW());

CREATE TABLE point_exchange (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(120),
  record_name VARCHAR(120),
  category VARCHAR(120),
  owner_name VARCHAR(120),
  plan_time VARCHAR(120),
  status VARCHAR(40),
  remark VARCHAR(255),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO point_exchange (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('155-08-001', '积分兑换示例一', '兑换类型', '申请人A', '2026-05-16 09:00', 'ACTIVE', '兑换编号、兑换项目、兑换类型、申请人、申请时间和兑换状态维护', NOW(), NOW()),
('155-08-002', '积分兑换示例二', '兑换类型', '申请人B', '2026-05-17 14:00', 'SCHEDULED', '积分兑换演示数据二', NOW(), NOW());

CREATE TABLE organization_transfer (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(120),
  record_name VARCHAR(120),
  category VARCHAR(120),
  owner_name VARCHAR(120),
  plan_time VARCHAR(120),
  status VARCHAR(40),
  remark VARCHAR(255),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO organization_transfer (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('155-09-001', '组织关系示例一', '转接类型', '经办人A', '2026-05-16 09:00', 'BOOKED', '转接编号、转接对象、转接类型、经办人、申请时间和转接状态维护', NOW(), NOW()),
('155-09-002', '组织关系示例二', '转接类型', '经办人B', '2026-05-17 14:00', 'VERIFIED', '组织关系演示数据二', NOW(), NOW());

CREATE TABLE honor_ranking (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(120),
  record_name VARCHAR(120),
  category VARCHAR(120),
  owner_name VARCHAR(120),
  plan_time VARCHAR(120),
  status VARCHAR(40),
  remark VARCHAR(255),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO honor_ranking (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('155-10-001', '榜单统计示例一', '榜单类型', '统计人A', '2026-05-16 09:00', 'SCHEDULED', '榜单编号、榜单名称、榜单类型、统计人、统计周期和发布状态维护', NOW(), NOW()),
('155-10-002', '榜单统计示例二', '榜单类型', '统计人B', '2026-05-17 14:00', 'PROCESSING', '榜单统计演示数据二', NOW(), NOW());

CREATE TABLE notice_board (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(120),
  record_name VARCHAR(120),
  category VARCHAR(120),
  owner_name VARCHAR(120),
  plan_time VARCHAR(120),
  status VARCHAR(40),
  remark VARCHAR(255),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO notice_board (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('155-11-001', '通知公告示例一', '公告类型', '发布人A', '2026-05-16 09:00', 'VERIFIED', '公告编号、公告标题、公告类型、发布人、发布时间和公告状态维护', NOW(), NOW()),
('155-11-002', '通知公告示例二', '公告类型', '发布人B', '2026-05-17 14:00', 'FINISHED', '通知公告演示数据二', NOW(), NOW());

CREATE TABLE operation_log (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  record_no VARCHAR(120),
  record_name VARCHAR(120),
  category VARCHAR(120),
  owner_name VARCHAR(120),
  plan_time VARCHAR(120),
  status VARCHAR(40),
  remark VARCHAR(255),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO operation_log (record_no, record_name, category, owner_name, plan_time, status, remark, created_time, updated_time) VALUES
('155-12-001', '操作日志示例一', '操作类型', '操作人A', '2026-05-16 09:00', 'PROCESSING', '日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护', NOW(), NOW()),
('155-12-002', '操作日志示例二', '操作类型', '操作人B', '2026-05-17 14:00', 'WARNING', '操作日志演示数据二', NOW(), NOW());
