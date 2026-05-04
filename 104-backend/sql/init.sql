DROP DATABASE IF EXISTS license_compliance_104;
CREATE DATABASE license_compliance_104 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE license_compliance_104;

CREATE TABLE sys_user (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(50) COMMENT '账号',
  password VARCHAR(80) COMMENT '密码',
  nickname VARCHAR(50) COMMENT '姓名',
  role VARCHAR(30) COMMENT '角色',
  department VARCHAR(80) COMMENT '部门',
  phone VARCHAR(30) COMMENT '电话',
  email VARCHAR(100) COMMENT '邮箱',
  status TINYINT COMMENT '状态',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE organization_team (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  team_name VARCHAR(80) COMMENT '团队名称',
  leader_name VARCHAR(50) COMMENT '负责人',
  contact_phone VARCHAR(30) COMMENT '联系电话',
  description VARCHAR(255) COMMENT '团队说明',
  status VARCHAR(30) COMMENT '状态',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE project_repository (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  repo_name VARCHAR(100) COMMENT '项目名称',
  repo_code VARCHAR(60) COMMENT '项目编码',
  owner_team VARCHAR(80) COMMENT '负责团队',
  git_url VARCHAR(255) COMMENT '仓库地址',
  language VARCHAR(40) COMMENT '技术语言',
  risk_level VARCHAR(30) COMMENT '风险等级',
  status VARCHAR(30) COMMENT '状态',
  last_scan_time VARCHAR(40) COMMENT '最近扫描',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE repository_branch (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  repository_id BIGINT COMMENT '项目ID',
  branch_name VARCHAR(80) COMMENT '分支名称',
  commit_hash VARCHAR(120) COMMENT '提交哈希',
  default_flag TINYINT COMMENT '默认分支',
  status VARCHAR(30) COMMENT '状态',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE dependency_package (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  repository_id BIGINT COMMENT '项目ID',
  package_name VARCHAR(120) COMMENT '依赖名称',
  package_version VARCHAR(60) COMMENT '版本号',
  package_manager VARCHAR(40) COMMENT '包管理器',
  license_name VARCHAR(80) COMMENT '许可证',
  scope_name VARCHAR(50) COMMENT '依赖范围',
  source_url VARCHAR(255) COMMENT '来源地址',
  status VARCHAR(30) COMMENT '状态',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE license_policy (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  license_name VARCHAR(80) COMMENT '许可证',
  risk_level VARCHAR(30) COMMENT '风险等级',
  compatible_flag TINYINT COMMENT '兼容',
  commercial_use_flag TINYINT COMMENT '商用',
  policy_text VARCHAR(255) COMMENT '策略说明',
  status VARCHAR(30) COMMENT '状态',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE compliance_baseline (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  baseline_name VARCHAR(100) COMMENT '基线名称',
  repository_id BIGINT COMMENT '项目ID',
  license_scope VARCHAR(120) COMMENT '许可证范围',
  required_level VARCHAR(30) COMMENT '要求等级',
  effective_date VARCHAR(40) COMMENT '生效日期',
  status VARCHAR(30) COMMENT '状态',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE scan_task (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  task_no VARCHAR(60) COMMENT '任务编号',
  repository_id BIGINT COMMENT '项目ID',
  branch_name VARCHAR(80) COMMENT '扫描分支',
  trigger_type VARCHAR(40) COMMENT '触发方式',
  scan_mode VARCHAR(40) COMMENT '扫描模式',
  status VARCHAR(30) COMMENT '状态',
  started_at VARCHAR(40) COMMENT '开始时间',
  finished_at VARCHAR(40) COMMENT '结束时间',
  summary VARCHAR(255) COMMENT '扫描摘要',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE scan_result (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  task_id BIGINT COMMENT '任务ID',
  package_name VARCHAR(120) COMMENT '依赖名称',
  package_version VARCHAR(60) COMMENT '版本号',
  license_name VARCHAR(80) COMMENT '许可证',
  risk_level VARCHAR(30) COMMENT '风险等级',
  rule_matched VARCHAR(120) COMMENT '命中规则',
  evidence VARCHAR(255) COMMENT '证据说明',
  status VARCHAR(30) COMMENT '状态',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE risk_issue (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  issue_no VARCHAR(60) COMMENT '问题编号',
  repository_id BIGINT COMMENT '项目ID',
  result_id BIGINT COMMENT '结果ID',
  issue_title VARCHAR(120) COMMENT '问题标题',
  severity VARCHAR(30) COMMENT '严重程度',
  issue_type VARCHAR(50) COMMENT '问题类型',
  owner_name VARCHAR(50) COMMENT '负责人',
  status VARCHAR(30) COMMENT '状态',
  due_date VARCHAR(40) COMMENT '截止日期',
  description VARCHAR(255) COMMENT '问题说明',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE rectification_task (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  issue_id BIGINT COMMENT '问题ID',
  assignee VARCHAR(50) COMMENT '处理人',
  action_plan VARCHAR(255) COMMENT '整改方案',
  progress_note VARCHAR(255) COMMENT '进展说明',
  status VARCHAR(30) COMMENT '状态',
  due_date VARCHAR(40) COMMENT '截止日期',
  finished_at VARCHAR(40) COMMENT '完成时间',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE audit_report (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  report_no VARCHAR(60) COMMENT '报告编号',
  repository_id BIGINT COMMENT '项目ID',
  task_id BIGINT COMMENT '任务ID',
  report_title VARCHAR(120) COMMENT '报告标题',
  risk_summary VARCHAR(255) COMMENT '风险摘要',
  conclusion VARCHAR(255) COMMENT '结论',
  status VARCHAR(30) COMMENT '状态',
  published_at VARCHAR(40) COMMENT '发布时间',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE approval_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  business_type VARCHAR(50) COMMENT '业务类型',
  business_id BIGINT COMMENT '业务ID',
  applicant VARCHAR(50) COMMENT '申请人',
  approver VARCHAR(50) COMMENT '审批人',
  decision VARCHAR(30) COMMENT '审批结果',
  opinion VARCHAR(255) COMMENT '审批意见',
  status VARCHAR(30) COMMENT '状态',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE operation_log (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(50) COMMENT '操作人',
  action VARCHAR(80) COMMENT '操作动作',
  target_type VARCHAR(60) COMMENT '对象类型',
  target_id BIGINT COMMENT '对象ID',
  detail VARCHAR(255) COMMENT '操作详情',
  ip_address VARCHAR(40) COMMENT 'IP地址',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO sys_user (username, password, nickname, role, department, phone, email, status, created_time, updated_time) VALUES
('admin', '123456', '系统管理员', 'ADMIN', '合规平台部', '13800000401', 'admin@license.com', 1, NOW(), NOW()),
('compliance', '123456', '合规专员', 'COMPLIANCE', '开源治理组', '13800000402', 'compliance@license.com', 1, NOW(), NOW()),
('developer', '123456', '研发负责人', 'DEVELOPER', '研发一组', '13800000403', 'developer@license.com', 1, NOW(), NOW()),
('auditor', '123456', '审计员', 'AUDITOR', '内控审计组', '13800000404', 'auditor@license.com', 1, NOW(), NOW());

INSERT INTO organization_team (team_name, leader_name, contact_phone, description, status, created_time, updated_time) VALUES
('研发一组', '周明', '021-88001001', '负责核心业务系统研发', 'ACTIVE', NOW(), NOW()),
('开源治理组', '陈佳', '021-88001002', '负责许可证策略与审计', 'ACTIVE', NOW(), NOW());

INSERT INTO project_repository (repo_name, repo_code, owner_team, git_url, language, risk_level, status, last_scan_time, created_time, updated_time) VALUES
('订单服务', 'ORD-SVC', '研发一组', 'https://git.example.com/order-service.git', 'Java', 'MEDIUM', 'ACTIVE', '2026-04-12 10:00', NOW(), NOW()),
('前端门户', 'WEB-PORTAL', '研发一组', 'https://git.example.com/web-portal.git', 'Vue', 'LOW', 'ACTIVE', '2026-04-15 14:20', NOW(), NOW());

INSERT INTO repository_branch (repository_id, branch_name, commit_hash, default_flag, status, created_time, updated_time) VALUES
(1, 'main', 'a18c7f01', 1, 'ACTIVE', NOW(), NOW()),
(1, 'release-2026', 'b51ad902', 0, 'ACTIVE', NOW(), NOW()),
(2, 'main', 'c71ba229', 1, 'ACTIVE', NOW(), NOW());

INSERT INTO dependency_package (repository_id, package_name, package_version, package_manager, license_name, scope_name, source_url, status, created_time, updated_time) VALUES
(1, 'spring-boot-starter-web', '2.7.18', 'Maven', 'Apache-2.0', 'compile', 'https://mvnrepository.com', 'ACTIVE', NOW(), NOW()),
(1, 'jjwt', '0.9.1', 'Maven', 'Apache-2.0', 'compile', 'https://mvnrepository.com', 'ACTIVE', NOW(), NOW()),
(2, 'element-plus', '2.4.4', 'NPM', 'MIT', 'dependencies', 'https://npmjs.com', 'ACTIVE', NOW(), NOW());

INSERT INTO license_policy (license_name, risk_level, compatible_flag, commercial_use_flag, policy_text, status, created_time, updated_time) VALUES
('MIT', 'LOW', 1, 1, '允许商用和二次分发，需保留版权声明', 'ACTIVE', NOW(), NOW()),
('Apache-2.0', 'LOW', 1, 1, '允许商用，需保留许可证和专利声明', 'ACTIVE', NOW(), NOW()),
('GPL-3.0', 'HIGH', 0, 0, '强传染性许可证，需合规审批', 'ACTIVE', NOW(), NOW());

INSERT INTO compliance_baseline (baseline_name, repository_id, license_scope, required_level, effective_date, status, created_time, updated_time) VALUES
('核心系统许可证基线', 1, 'MIT,Apache-2.0,BSD', 'STRICT', '2026-01-01', 'ACTIVE', NOW(), NOW()),
('前端项目许可证基线', 2, 'MIT,Apache-2.0,ISC', 'NORMAL', '2026-01-01', 'ACTIVE', NOW(), NOW());

INSERT INTO scan_task (task_no, repository_id, branch_name, trigger_type, scan_mode, status, started_at, finished_at, summary, created_time, updated_time) VALUES
('SCAN-104-001', 1, 'main', 'MANUAL', 'FULL', 'FINISHED', '2026-04-12 09:30', '2026-04-12 09:45', '扫描依赖32个，发现中风险2项', NOW(), NOW()),
('SCAN-104-002', 2, 'main', 'SCHEDULE', 'INCREMENT', 'RUNNING', '2026-04-15 14:00', '', '正在扫描前端依赖', NOW(), NOW());

INSERT INTO scan_result (task_id, package_name, package_version, license_name, risk_level, rule_matched, evidence, status, created_time, updated_time) VALUES
(1, 'spring-boot-starter-web', '2.7.18', 'Apache-2.0', 'LOW', 'Apache-2.0允许商用', 'pom.xml line 21', 'PASSED', NOW(), NOW()),
(1, 'legacy-gpl-lib', '1.2.0', 'GPL-3.0', 'HIGH', 'GPL-3.0不在基线范围', 'pom.xml line 66', 'RISK', NOW(), NOW());

INSERT INTO risk_issue (issue_no, repository_id, result_id, issue_title, severity, issue_type, owner_name, status, due_date, description, created_time, updated_time) VALUES
('ISS-104-001', 1, 2, '订单服务引入GPL高风险依赖', 'HIGH', 'LICENSE_FORBIDDEN', '李雷', 'CONFIRMED', '2026-05-20', '需替换为Apache或MIT许可依赖', NOW(), NOW()),
('ISS-104-002', 1, 2, '许可证声明缺失', 'MEDIUM', 'NOTICE_MISSING', '韩梅', 'OPEN', '2026-05-18', '发布包缺少第三方许可证声明', NOW(), NOW());

INSERT INTO rectification_task (issue_id, assignee, action_plan, progress_note, status, due_date, finished_at, created_time, updated_time) VALUES
(1, '李雷', '替换legacy-gpl-lib并回归测试', '已完成替代包调研', 'ASSIGNED', '2026-05-20', '', NOW(), NOW()),
(2, '韩梅', '补充THIRD-PARTY-NOTICES文件', '待审计确认模板', 'OPEN', '2026-05-18', '', NOW(), NOW());

INSERT INTO audit_report (report_no, repository_id, task_id, report_title, risk_summary, conclusion, status, published_at, created_time, updated_time) VALUES
('RPT-104-001', 1, 1, '订单服务开源许可证审计报告', '高风险1项，中风险1项', '需整改后再发布', 'DRAFT', '', NOW(), NOW()),
('RPT-104-002', 2, 2, '前端门户许可证巡检报告', '暂未发现高风险', '可继续观察', 'PUBLISHED', '2026-04-15 16:00', NOW(), NOW());

INSERT INTO approval_record (business_type, business_id, applicant, approver, decision, opinion, status, created_time, updated_time) VALUES
('ISSUE', 1, 'developer', 'compliance', 'PENDING', '申请临时保留依赖等待替换', 'PENDING', NOW(), NOW()),
('REPORT', 2, 'compliance', 'auditor', 'APPROVED', '报告内容完整', 'APPROVED', NOW(), NOW());

INSERT INTO operation_log (username, action, target_type, target_id, detail, ip_address, created_time, updated_time) VALUES
('admin', '登录系统', 'sys_user', 1, '管理员登录合规平台', '127.0.0.1', NOW(), NOW()),
('compliance', '发布报告', 'audit_report', 2, '发布前端门户审计报告', '127.0.0.1', NOW(), NOW());
