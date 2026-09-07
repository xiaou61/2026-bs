DROP DATABASE IF EXISTS api_testcase_105;
CREATE DATABASE api_testcase_105 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE api_testcase_105;

CREATE TABLE sys_user (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(50) COMMENT '账号',
  password VARCHAR(80) COMMENT '密码',
  nickname VARCHAR(50) COMMENT '姓名',
  role VARCHAR(30) COMMENT '角色',
  team_name VARCHAR(80) COMMENT '团队',
  phone VARCHAR(30) COMMENT '电话',
  email VARCHAR(100) COMMENT '邮箱',
  status TINYINT COMMENT '状态',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE api_project (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  project_name VARCHAR(100) COMMENT '项目名称',
  project_code VARCHAR(60) COMMENT '项目编码',
  owner_name VARCHAR(50) COMMENT '负责人',
  base_url VARCHAR(255) COMMENT '基础地址',
  auth_type VARCHAR(40) COMMENT '鉴权类型',
  version_no VARCHAR(40) COMMENT '版本号',
  status VARCHAR(30) COMMENT '状态',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE api_group (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  project_id BIGINT COMMENT '项目ID',
  group_name VARCHAR(100) COMMENT '分组名称',
  parent_name VARCHAR(80) COMMENT '父级分组',
  sort_no INT COMMENT '排序',
  description VARCHAR(255) COMMENT '说明',
  status VARCHAR(30) COMMENT '状态',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE api_endpoint (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  project_id BIGINT COMMENT '项目ID',
  group_id BIGINT COMMENT '分组ID',
  api_name VARCHAR(120) COMMENT '接口名称',
  method VARCHAR(20) COMMENT '请求方式',
  path_url VARCHAR(180) COMMENT '接口路径',
  content_type VARCHAR(80) COMMENT '内容类型',
  owner_name VARCHAR(50) COMMENT '维护人',
  status VARCHAR(30) COMMENT '状态',
  description VARCHAR(255) COMMENT '说明',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE request_param (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  endpoint_id BIGINT COMMENT '接口ID',
  param_name VARCHAR(80) COMMENT '参数名',
  param_type VARCHAR(40) COMMENT '参数位置',
  data_type VARCHAR(40) COMMENT '数据类型',
  required_flag TINYINT COMMENT '必填',
  example_value VARCHAR(120) COMMENT '示例值',
  description VARCHAR(255) COMMENT '说明',
  status VARCHAR(30) COMMENT '状态',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE response_field (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  endpoint_id BIGINT COMMENT '接口ID',
  field_name VARCHAR(100) COMMENT '字段名',
  data_type VARCHAR(40) COMMENT '数据类型',
  parent_path VARCHAR(120) COMMENT '父级路径',
  example_value VARCHAR(120) COMMENT '示例值',
  description VARCHAR(255) COMMENT '说明',
  status VARCHAR(30) COMMENT '状态',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE mock_rule (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  endpoint_id BIGINT COMMENT '接口ID',
  rule_name VARCHAR(100) COMMENT '规则名称',
  match_expression VARCHAR(180) COMMENT '匹配表达式',
  response_code INT COMMENT '响应码',
  response_body VARCHAR(500) COMMENT '响应内容',
  delay_ms INT COMMENT '延迟毫秒',
  status VARCHAR(30) COMMENT '状态',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE test_case (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  endpoint_id BIGINT COMMENT '接口ID',
  case_name VARCHAR(120) COMMENT '用例名称',
  case_type VARCHAR(40) COMMENT '用例类型',
  priority VARCHAR(30) COMMENT '优先级',
  request_body VARCHAR(500) COMMENT '请求体',
  expected_result VARCHAR(255) COMMENT '预期结果',
  status VARCHAR(30) COMMENT '状态',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE test_step (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  case_id BIGINT COMMENT '用例ID',
  step_name VARCHAR(120) COMMENT '步骤名称',
  step_order INT COMMENT '步骤序号',
  request_data VARCHAR(500) COMMENT '请求数据',
  assert_expression VARCHAR(255) COMMENT '断言表达式',
  extract_variable VARCHAR(120) COMMENT '提取变量',
  status VARCHAR(30) COMMENT '状态',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE environment_config (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  project_id BIGINT COMMENT '项目ID',
  env_name VARCHAR(80) COMMENT '环境名称',
  base_url VARCHAR(255) COMMENT '基础地址',
  headers VARCHAR(500) COMMENT '请求头',
  variables VARCHAR(500) COMMENT '变量',
  status VARCHAR(30) COMMENT '状态',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE test_execution (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  execution_no VARCHAR(60) COMMENT '执行编号',
  project_id BIGINT COMMENT '项目ID',
  env_name VARCHAR(80) COMMENT '执行环境',
  executor VARCHAR(50) COMMENT '执行人',
  status VARCHAR(30) COMMENT '状态',
  started_at VARCHAR(40) COMMENT '开始时间',
  finished_at VARCHAR(40) COMMENT '结束时间',
  summary VARCHAR(255) COMMENT '执行摘要',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE execution_result (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  execution_id BIGINT COMMENT '执行ID',
  case_id BIGINT COMMENT '用例ID',
  endpoint_id BIGINT COMMENT '接口ID',
  result_status VARCHAR(30) COMMENT '结果状态',
  actual_code INT COMMENT '实际状态码',
  duration_ms INT COMMENT '耗时毫秒',
  assert_message VARCHAR(255) COMMENT '断言信息',
  status VARCHAR(30) COMMENT '状态',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE document_snapshot (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  project_id BIGINT COMMENT '项目ID',
  snapshot_no VARCHAR(60) COMMENT '快照编号',
  title VARCHAR(120) COMMENT '文档标题',
  version_no VARCHAR(40) COMMENT '版本号',
  content_summary VARCHAR(255) COMMENT '内容摘要',
  publisher VARCHAR(50) COMMENT '发布人',
  status VARCHAR(30) COMMENT '状态',
  published_at VARCHAR(40) COMMENT '发布时间',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE operation_log (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(50) COMMENT '操作人',
  action VARCHAR(80) COMMENT '操作动作',
  target_type VARCHAR(60) COMMENT '对象类型',
  target_id BIGINT COMMENT '对象ID',
  detail VARCHAR(255) COMMENT '详情',
  ip_address VARCHAR(40) COMMENT 'IP地址',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO sys_user (username, password, nickname, role, team_name, phone, email, status, created_time, updated_time) VALUES
('admin', '123456', '系统管理员', 'ADMIN', '平台管理组', '13800000501', 'admin@api.com', 1, NOW(), NOW()),
('product', '123456', '产品经理', 'PRODUCT', '接口设计组', '13800000502', 'product@api.com', 1, NOW(), NOW()),
('tester', '123456', '测试工程师', 'TESTER', '质量保障组', '13800000503', 'tester@api.com', 1, NOW(), NOW()),
('developer', '123456', '后端开发', 'DEVELOPER', '研发一组', '13800000504', 'developer@api.com', 1, NOW(), NOW());

INSERT INTO api_project (project_name, project_code, owner_name, base_url, auth_type, version_no, status, created_time, updated_time) VALUES
('会员中心接口', 'MEMBER-API', '林溪', 'http://localhost:9001', 'JWT', 'v1.0', 'ACTIVE', NOW(), NOW()),
('订单服务接口', 'ORDER-API', '许航', 'http://localhost:9002', 'Bearer', 'v2.1', 'ACTIVE', NOW(), NOW());

INSERT INTO api_group (project_id, group_name, parent_name, sort_no, description, status, created_time, updated_time) VALUES
(1, '账号认证', '根分组', 1, '登录、刷新Token和用户信息接口', 'ACTIVE', NOW(), NOW()),
(1, '会员资料', '根分组', 2, '会员档案和积分接口', 'ACTIVE', NOW(), NOW());

INSERT INTO api_endpoint (project_id, group_id, api_name, method, path_url, content_type, owner_name, status, description, created_time, updated_time) VALUES
(1, 1, '用户登录', 'POST', '/api/auth/login', 'application/json', '林溪', 'PUBLISHED', '账号密码登录获取Token', NOW(), NOW()),
(1, 2, '会员详情', 'GET', '/api/member/{id}', 'application/json', '许航', 'DRAFT', '查询会员资料', NOW(), NOW());

INSERT INTO request_param (endpoint_id, param_name, param_type, data_type, required_flag, example_value, description, status, created_time, updated_time) VALUES
(1, 'username', 'BODY', 'String', 1, 'admin', '登录账号', 'ACTIVE', NOW(), NOW()),
(1, 'password', 'BODY', 'String', 1, '123456', '登录密码', 'ACTIVE', NOW(), NOW()),
(2, 'id', 'PATH', 'Long', 1, '1001', '会员ID', 'ACTIVE', NOW(), NOW());

INSERT INTO response_field (endpoint_id, field_name, data_type, parent_path, example_value, description, status, created_time, updated_time) VALUES
(1, 'token', 'String', 'data', 'eyJhbGci', '登录令牌', 'ACTIVE', NOW(), NOW()),
(2, 'nickname', 'String', 'data.user', '张三', '会员昵称', 'ACTIVE', NOW(), NOW());

INSERT INTO mock_rule (endpoint_id, rule_name, match_expression, response_code, response_body, delay_ms, status, created_time, updated_time) VALUES
(1, '登录成功Mock', 'username=admin', 200, '{"code":200,"message":"success"}', 20, 'ACTIVE', NOW(), NOW()),
(2, '会员不存在Mock', 'id=0', 404, '{"message":"not found"}', 10, 'ACTIVE', NOW(), NOW());

INSERT INTO test_case (endpoint_id, case_name, case_type, priority, request_body, expected_result, status, created_time, updated_time) VALUES
(1, '登录成功用例', 'POSITIVE', 'HIGH', '{"username":"admin","password":"123456"}', '返回token', 'ACTIVE', NOW(), NOW()),
(2, '会员详情查询', 'POSITIVE', 'MEDIUM', '{}', '返回会员详情', 'ACTIVE', NOW(), NOW());

INSERT INTO test_step (case_id, step_name, step_order, request_data, assert_expression, extract_variable, status, created_time, updated_time) VALUES
(1, '发送登录请求', 1, '{"username":"admin"}', 'code == 200', 'token', 'ACTIVE', NOW(), NOW()),
(2, '查询会员详情', 1, '{}', 'data.id != null', 'memberId', 'ACTIVE', NOW(), NOW());

INSERT INTO environment_config (project_id, env_name, base_url, headers, variables, status, created_time, updated_time) VALUES
(1, '开发环境', 'http://localhost:9001', '{"Authorization":"Bearer token"}', '{"tenant":"dev"}', 'ACTIVE', NOW(), NOW()),
(1, '测试环境', 'http://test.example.com', '{"X-Env":"test"}', '{"tenant":"test"}', 'ACTIVE', NOW(), NOW());

INSERT INTO test_execution (execution_no, project_id, env_name, executor, status, started_at, finished_at, summary, created_time, updated_time) VALUES
('EXE-105-001', 1, '测试环境', 'tester', 'FINISHED', '2026-04-20 09:00', '2026-04-20 09:10', '执行2条，通过2条', NOW(), NOW()),
('EXE-105-002', 1, '开发环境', 'developer', 'RUNNING', '2026-04-21 10:00', '', '执行中', NOW(), NOW());

INSERT INTO execution_result (execution_id, case_id, endpoint_id, result_status, actual_code, duration_ms, assert_message, status, created_time, updated_time) VALUES
(1, 1, 1, 'PASSED', 200, 128, '断言通过', 'CONFIRMED', NOW(), NOW()),
(1, 2, 2, 'PASSED', 200, 96, '字段存在', 'CONFIRMED', NOW(), NOW());

INSERT INTO document_snapshot (project_id, snapshot_no, title, version_no, content_summary, publisher, status, published_at, created_time, updated_time) VALUES
(1, 'DOC-105-001', '会员中心接口文档', 'v1.0', '包含认证和会员资料接口', 'product', 'PUBLISHED', '2026-04-20 11:00', NOW(), NOW()),
(2, 'DOC-105-002', '订单服务接口文档', 'v2.1', '包含订单创建和查询接口', 'product', 'DRAFT', '', NOW(), NOW());

INSERT INTO operation_log (username, action, target_type, target_id, detail, ip_address, created_time, updated_time) VALUES
('admin', '登录系统', 'sys_user', 1, '管理员登录平台', '127.0.0.1', NOW(), NOW()),
('tester', '执行用例', 'test_execution', 1, '执行会员中心接口用例', '127.0.0.1', NOW(), NOW());
