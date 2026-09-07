DROP DATABASE IF EXISTS cloud_cost_108;
CREATE DATABASE cloud_cost_108 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE cloud_cost_108;

CREATE TABLE sys_user (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL,
  password VARCHAR(80) NOT NULL,
  nickname VARCHAR(50) NOT NULL,
  role VARCHAR(40) NOT NULL,
  department VARCHAR(80),
  phone VARCHAR(30),
  email VARCHAR(80),
  status TINYINT DEFAULT 1,
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE cloud_account (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  account_name VARCHAR(80) NOT NULL,
  account_code VARCHAR(80) NOT NULL,
  provider VARCHAR(40) NOT NULL,
  owner_name VARCHAR(50),
  access_mode VARCHAR(40),
  monthly_budget DECIMAL(12,2) DEFAULT 0,
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE resource_namespace (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  namespace_name VARCHAR(80) NOT NULL,
  namespace_code VARCHAR(80) NOT NULL,
  account_name VARCHAR(80),
  business_line VARCHAR(80),
  environment_name VARCHAR(40),
  owner_name VARCHAR(50),
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE cost_bill (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  bill_no VARCHAR(80) NOT NULL,
  account_name VARCHAR(80),
  bill_month VARCHAR(20),
  currency VARCHAR(20),
  total_amount DECIMAL(12,2) DEFAULT 0,
  pay_amount DECIMAL(12,2) DEFAULT 0,
  status VARCHAR(30) DEFAULT 'WAIT_CONFIRM',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE cost_item (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  resource_id VARCHAR(100) NOT NULL,
  resource_name VARCHAR(100),
  resource_type VARCHAR(40),
  namespace_name VARCHAR(80),
  bill_month VARCHAR(20),
  cost_amount DECIMAL(12,2) DEFAULT 0,
  usage_quantity DECIMAL(12,2) DEFAULT 0,
  unit_name VARCHAR(30),
  status VARCHAR(30) DEFAULT 'NORMAL',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE budget_policy (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  policy_name VARCHAR(100) NOT NULL,
  namespace_name VARCHAR(80),
  budget_amount DECIMAL(12,2) DEFAULT 0,
  alert_percent DECIMAL(6,2) DEFAULT 0,
  owner_name VARCHAR(50),
  cycle_type VARCHAR(30),
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE cost_allocation (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  allocation_no VARCHAR(80) NOT NULL,
  namespace_name VARCHAR(80),
  business_line VARCHAR(80),
  owner_name VARCHAR(50),
  allocated_amount DECIMAL(12,2) DEFAULT 0,
  allocation_month VARCHAR(20),
  status VARCHAR(30) DEFAULT 'DRAFT',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE idle_resource (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  resource_name VARCHAR(100) NOT NULL,
  resource_type VARCHAR(40),
  account_name VARCHAR(80),
  owner_name VARCHAR(50),
  idle_days INT DEFAULT 0,
  monthly_cost DECIMAL(12,2) DEFAULT 0,
  detected_time DATETIME,
  status VARCHAR(30) DEFAULT 'PENDING',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE optimization_rule (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  rule_name VARCHAR(100) NOT NULL,
  resource_type VARCHAR(40),
  metric_name VARCHAR(80),
  threshold_value DECIMAL(10,2) DEFAULT 0,
  action_type VARCHAR(40),
  priority INT DEFAULT 1,
  status VARCHAR(30) DEFAULT 'ACTIVE',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE optimization_advice (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  advice_no VARCHAR(80) NOT NULL,
  resource_name VARCHAR(100),
  advice_type VARCHAR(40),
  expected_saving DECIMAL(12,2) DEFAULT 0,
  owner_name VARCHAR(50),
  generated_time DATETIME,
  status VARCHAR(30) DEFAULT 'GENERATED',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE saving_plan (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  plan_name VARCHAR(100) NOT NULL,
  account_name VARCHAR(80),
  coverage_scope VARCHAR(100),
  commit_amount DECIMAL(12,2) DEFAULT 0,
  expected_saving DECIMAL(12,2) DEFAULT 0,
  owner_name VARCHAR(50),
  status VARCHAR(30) DEFAULT 'DRAFT',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE anomaly_event (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  event_no VARCHAR(80) NOT NULL,
  account_name VARCHAR(80),
  resource_name VARCHAR(100),
  anomaly_type VARCHAR(40),
  cost_amount DECIMAL(12,2) DEFAULT 0,
  detected_time DATETIME,
  status VARCHAR(30) DEFAULT 'OPEN',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE report_snapshot (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  report_name VARCHAR(120) NOT NULL,
  report_month VARCHAR(20),
  report_type VARCHAR(40),
  generated_by VARCHAR(50),
  summary_text TEXT,
  status VARCHAR(30) DEFAULT 'DRAFT',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE operation_log (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  operator_name VARCHAR(50) NOT NULL,
  module_name VARCHAR(80),
  action_type VARCHAR(50),
  target_name VARCHAR(120),
  detail TEXT,
  ip_address VARCHAR(50),
  status VARCHAR(30) DEFAULT 'SUCCESS',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO sys_user (username, password, nickname, role, department, phone, email, status, created_time, updated_time) VALUES
('admin', '123456', '系统管理员', 'ADMIN', '平台管理组', '13800000801', 'admin@cost.com', 1, NOW(), NOW()),
('finops', '123456', '成本分析师', 'FINOPS', 'FinOps组', '13800000802', 'finops@cost.com', 1, NOW(), NOW()),
('devops', '123456', '云运维工程师', 'DEVOPS', '云平台组', '13800000803', 'devops@cost.com', 1, NOW(), NOW()),
('manager', '123456', '成本主管', 'MANAGER', '成本治理组', '13800000804', 'manager@cost.com', 1, NOW(), NOW());

INSERT INTO cloud_account (account_name, account_code, provider, owner_name, access_mode, monthly_budget, status, created_time, updated_time) VALUES
('生产云账号', 'ACC-PROD-001', 'ALIYUN', '周成本', 'API', 86000.0, 'ACTIVE', NOW(), NOW()),
('测试云账号', 'ACC-TEST-002', 'TENCENT', '林平台', 'BILL_FILE', 32000.0, 'PENDING', NOW(), NOW());

INSERT INTO resource_namespace (namespace_name, namespace_code, account_name, business_line, environment_name, owner_name, status, created_time, updated_time) VALUES
('交易生产空间', 'NS-TRADE-PROD', '生产云账号', '交易中台', '生产', '陈业务', 'ACTIVE', NOW(), NOW()),
('营销测试空间', 'NS-MKT-TEST', '测试云账号', '营销增长', '测试', '钱测试', 'ACTIVE', NOW(), NOW());

INSERT INTO cost_bill (bill_no, account_name, bill_month, currency, total_amount, pay_amount, status, created_time, updated_time) VALUES
('BILL-202604-001', '生产云账号', '2026-04', 'CNY', 78230.45, 76500.0, 'CONFIRMED', NOW(), NOW()),
('BILL-202604-002', '测试云账号', '2026-04', 'CNY', 21360.2, 20900.0, 'WAIT_CONFIRM', NOW(), NOW());

INSERT INTO cost_item (resource_id, resource_name, resource_type, namespace_name, bill_month, cost_amount, usage_quantity, unit_name, status, created_time, updated_time) VALUES
('i-prod-001', '交易应用服务器', 'ECS', '交易生产空间', '2026-04', 12800.5, 720.0, '小时', 'NORMAL', NOW(), NOW()),
('rds-test-002', '测试数据库', 'RDS', '营销测试空间', '2026-04', 6300.0, 310.0, 'GB', 'NORMAL', NOW(), NOW());

INSERT INTO budget_policy (policy_name, namespace_name, budget_amount, alert_percent, owner_name, cycle_type, status, created_time, updated_time) VALUES
('交易月度预算', '交易生产空间', 90000.0, 80.0, '陈业务', 'MONTH', 'ACTIVE', NOW(), NOW()),
('测试资源预算', '营销测试空间', 25000.0, 75.0, '钱测试', 'MONTH', 'ACTIVE', NOW(), NOW());

INSERT INTO cost_allocation (allocation_no, namespace_name, business_line, owner_name, allocated_amount, allocation_month, status, created_time, updated_time) VALUES
('ALLOC-202604-001', '交易生产空间', '交易中台', '陈业务', 54600.0, '2026-04', 'APPROVED', NOW(), NOW()),
('ALLOC-202604-002', '营销测试空间', '营销增长', '钱测试', 18900.0, '2026-04', 'DRAFT', NOW(), NOW());

INSERT INTO idle_resource (resource_name, resource_type, account_name, owner_name, idle_days, monthly_cost, detected_time, status, created_time, updated_time) VALUES
('低负载服务器', 'ECS', '生产云账号', '陈业务', 18, 860.0, '2026-04-20 09:30:00', 'PENDING', NOW(), NOW()),
('旧测试磁盘', 'DISK', '测试云账号', '钱测试', 42, 230.0, '2026-04-22 14:00:00', 'CONFIRMED', NOW(), NOW());

INSERT INTO optimization_rule (rule_name, resource_type, metric_name, threshold_value, action_type, priority, status, created_time, updated_time) VALUES
('低CPU降配规则', 'ECS', 'CPU_UTIL', 10.0, 'DOWNSIZE', 1, 'ACTIVE', NOW(), NOW()),
('空闲磁盘清理规则', 'DISK', 'READ_WRITE_IOPS', 1.0, 'CLEAN', 2, 'ACTIVE', NOW(), NOW());

INSERT INTO optimization_advice (advice_no, resource_name, advice_type, expected_saving, owner_name, generated_time, status, created_time, updated_time) VALUES
('ADV-202604-001', '交易应用服务器', 'DOWNSIZE', 1250.0, '陈业务', '2026-04-24 10:00:00', 'GENERATED', NOW(), NOW()),
('ADV-202604-002', '旧测试磁盘', 'CLEAN', 230.0, '钱测试', '2026-04-25 16:20:00', 'ACCEPTED', NOW(), NOW());

INSERT INTO saving_plan (plan_name, account_name, coverage_scope, commit_amount, expected_saving, owner_name, status, created_time, updated_time) VALUES
('ECS包年节省计划', '生产云账号', '交易生产ECS', 36000.0, 8200.0, '周成本', 'ACTIVE', NOW(), NOW()),
('数据库预留计划', '生产云账号', '核心RDS实例', 22000.0, 3900.0, '陈业务', 'DRAFT', NOW(), NOW());

INSERT INTO anomaly_event (event_no, account_name, resource_name, anomaly_type, cost_amount, detected_time, status, created_time, updated_time) VALUES
('ANO-202604-001', '生产云账号', '交易网关集群', 'SPIKE', 5600.0, '2026-04-26 08:40:00', 'OPEN', NOW(), NOW()),
('ANO-202604-002', '测试云账号', '测试对象存储', 'BUDGET_OVER', 1800.0, '2026-04-27 11:10:00', 'CONFIRMED', NOW(), NOW());

INSERT INTO report_snapshot (report_name, report_month, report_type, generated_by, summary_text, status, created_time, updated_time) VALUES
('4月云成本分析报告', '2026-04', 'MONTHLY', '周成本', '生产成本环比下降，闲置资源仍需治理', 'PUBLISHED', NOW(), NOW()),
('测试资源优化报告', '2026-04', 'OPTIMIZATION', '林平台', '测试资源清理后预计节省明显', 'DRAFT', NOW(), NOW());

INSERT INTO operation_log (operator_name, module_name, action_type, target_name, detail, ip_address, status, created_time, updated_time) VALUES
('admin', '成本账单', '确认账单', 'BILL-202604-001', '确认生产云账号4月账单', '127.0.0.1', 'SUCCESS', NOW(), NOW()),
('finops', '优化建议', '采纳建议', 'ADV-202604-001', '采纳低CPU降配建议', '127.0.0.1', 'SUCCESS', NOW(), NOW());
