DROP DATABASE IF EXISTS food_inspection_146;
CREATE DATABASE food_inspection_146 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE food_inspection_146;

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
('admin', '123456', '系统管理员', 'ADMIN', '食安监管中心', '139146000100', 'admin@demo.local', 1, NOW(), NOW()),
('inspector', '123456', '抽检员', 'INSPECTOR', '现场抽检组', '139146000200', 'inspector@demo.local', 1, NOW(), NOW()),
('merchant', '123456', '商户负责人', 'MERCHANT', '商户服务组', '139146000300', 'merchant@demo.local', 1, NOW(), NOW()),
('reviewer', '123456', '监管审核员', 'REVIEWER', '结果审核科', '139146000400', 'reviewer@demo.local', 1, NOW(), NOW());

CREATE TABLE inspection_plan (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  plan_no VARCHAR(120),
  plan_name VARCHAR(120),
  inspection_type VARCHAR(120),
  region_name VARCHAR(120),
  inspection_cycle VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO inspection_plan (plan_no, plan_name, inspection_type, region_name, inspection_cycle, status, created_time, updated_time) VALUES
('PLAN-146-001', '夏季冷链食品专项抽检', '专项抽检', '江宁区', '2026-05', 'ACTIVE', NOW(), NOW()),
('PLAN-146-002', '校园周边餐饮例行抽检', '例行抽检', '秦淮区', '2026-06', 'PENDING', NOW(), NOW());

CREATE TABLE food_item (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  food_no VARCHAR(120),
  food_name VARCHAR(120),
  food_type VARCHAR(120),
  batch_no VARCHAR(120),
  inspection_status VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO food_item (food_no, food_name, food_type, batch_no, inspection_status, status, created_time, updated_time) VALUES
('FOOD-146-001', '鲜切三文鱼', '冷链食品', 'BATCH-20260501', '待抽样', 'ACTIVE', NOW(), NOW()),
('FOOD-146-002', '现制奶茶原液', '饮品原料', 'BATCH-20260506', '检测中', 'PROCESSING', NOW(), NOW());

CREATE TABLE merchant_profile (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  merchant_no VARCHAR(120),
  merchant_name VARCHAR(120),
  contact_name VARCHAR(120),
  contact_phone VARCHAR(120),
  business_address VARCHAR(255),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO merchant_profile (merchant_no, merchant_name, contact_name, contact_phone, business_address, status, created_time, updated_time) VALUES
('MER-146-001', '宁鲜冷链配送中心', '王晓峰', '138146000501', '南京市江宁区空港物流园 6 号', 'PROCESSING', NOW(), NOW()),
('MER-146-002', '秦淮轻食工坊', '周雨晨', '138146000502', '南京市秦淮区中华路 128 号', 'ACTIVE', NOW(), NOW());

CREATE TABLE sampling_task (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  task_no VARCHAR(120),
  task_title VARCHAR(120),
  sampling_location VARCHAR(255),
  sampling_time VARCHAR(120),
  inspector_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO sampling_task (task_no, task_title, sampling_location, sampling_time, inspector_name, status, created_time, updated_time) VALUES
('TASK-146-001', '冷链食品现场抽样', '江宁区众彩批发市场', '2026-05-16 09:30', '李静', 'SUBMITTED', NOW(), NOW()),
('TASK-146-002', '奶茶原料复核抽样', '秦淮区轻食工坊门店', '2026-05-17 14:00', '陈航', 'APPROVED', NOW(), NOW());

CREATE TABLE agency_profile (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  agency_no VARCHAR(120),
  agency_name VARCHAR(120),
  qualification_level VARCHAR(120),
  entry_date VARCHAR(120),
  specialty_area VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO agency_profile (agency_no, agency_name, qualification_level, entry_date, specialty_area, status, created_time, updated_time) VALUES
('AGY-146-001', '江苏省食品质量检测中心', 'CMA/CNAS', '2025-09-01', '冷链食品', 'APPROVED', NOW(), NOW()),
('AGY-146-002', '金陵生物检测实验室', 'CMA', '2026-02-15', '餐饮原料', 'SUBMITTED', NOW(), NOW());

CREATE TABLE sample_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  sample_no VARCHAR(120),
  food_name VARCHAR(120),
  sample_type VARCHAR(120),
  sampling_remark VARCHAR(255),
  recorded_time VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO sample_record (sample_no, food_name, sample_type, sampling_remark, recorded_time, status, created_time, updated_time) VALUES
('SMP-146-001', '鲜切三文鱼', '成品样', '冷链温度记录完整，现场封样', '2026-05-16 10:00', 'SUBMITTED', NOW(), NOW()),
('SMP-146-002', '现制奶茶原液', '原料样', '补充提供供应商批次照片', '2026-05-17 14:30', 'APPROVED', NOW(), NOW());

CREATE TABLE test_result (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  result_no VARCHAR(120),
  food_name VARCHAR(120),
  test_conclusion VARCHAR(120),
  test_time VARCHAR(120),
  tester_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO test_result (result_no, food_name, test_conclusion, test_time, tester_name, status, created_time, updated_time) VALUES
('RES-146-001', '鲜切三文鱼', '合格', '2026-05-18 11:20', '郑岚', 'FINISHED', NOW(), NOW()),
('RES-146-002', '现制奶茶原液', '菌落总数偏高', '2026-05-19 16:40', '沈哲', 'PROCESSING', NOW(), NOW());

CREATE TABLE recheck_application (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  application_no VARCHAR(120),
  food_name VARCHAR(120),
  recheck_reason VARCHAR(255),
  application_time VARCHAR(120),
  applicant_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO recheck_application (application_no, food_name, recheck_reason, application_time, applicant_name, status, created_time, updated_time) VALUES
('RCK-146-001', '现制奶茶原液', '申请复核菌落总数检测结果', '2026-05-20 09:00', '周雨晨', 'ACTIVE', NOW(), NOW()),
('RCK-146-002', '鲜切三文鱼', '希望补充查看冷链运输证明', '2026-05-18 13:00', '王晓峰', 'FINISHED', NOW(), NOW());

CREATE TABLE disposal_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  disposal_no VARCHAR(120),
  food_name VARCHAR(120),
  disposal_method VARCHAR(120),
  disposal_time VARCHAR(120),
  responsible_unit VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO disposal_record (disposal_no, food_name, disposal_method, disposal_time, responsible_unit, status, created_time, updated_time) VALUES
('DSP-146-001', '现制奶茶原液', '责令下架并限期整改', '2026-05-20 15:30', '秦淮区市场监管局', 'ACTIVE', NOW(), NOW()),
('DSP-146-002', '鲜切三文鱼', '归档留样并结束跟踪', '2026-05-18 18:00', '江宁区市场监管局', 'FINISHED', NOW(), NOW());

CREATE TABLE public_report (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  report_no VARCHAR(120),
  food_name VARCHAR(120),
  report_title VARCHAR(120),
  publish_channel VARCHAR(120),
  publish_time VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO public_report (report_no, food_name, report_title, publish_channel, publish_time, status, created_time, updated_time) VALUES
('REP-146-001', '鲜切三文鱼', '江宁区五月冷链食品抽检结果公示', '政务网站', '2026-05-19 10:00', 'PUBLISHED', NOW(), NOW()),
('REP-146-002', '现制奶茶原液', '秦淮区饮品原料复检结果待发布', '公众号', '2026-05-21 09:30', 'PROCESSING', NOW(), NOW());

CREATE TABLE risk_warning (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  warning_no VARCHAR(120),
  food_name VARCHAR(120),
  warning_time VARCHAR(120),
  risk_type VARCHAR(120),
  disposal_suggestion VARCHAR(255),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO risk_warning (warning_no, food_name, warning_time, risk_type, disposal_suggestion, status, created_time, updated_time) VALUES
('WRN-146-001', '现制奶茶原液', '2026-05-19 17:00', '微生物超标', '建议启动复检并重点核查供应链', 'PROCESSING', NOW(), NOW()),
('WRN-146-002', '鲜切三文鱼', '2026-05-18 09:00', '冷链断点风险', '建议抽查运输温控记录', 'FINISHED', NOW(), NOW());

CREATE TABLE inspection_notice (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  notice_no VARCHAR(120),
  food_name VARCHAR(120),
  notice_type VARCHAR(120),
  notice_content VARCHAR(255),
  receiver_name VARCHAR(120),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO inspection_notice (notice_no, food_name, notice_type, notice_content, receiver_name, status, created_time, updated_time) VALUES
('NTC-146-001', '现制奶茶原液', '整改通知', '请于 48 小时内提交复检样品与整改说明', '周雨晨', 'PROCESSING', NOW(), NOW()),
('NTC-146-002', '鲜切三文鱼', '结果告知', '抽检结果合格，可继续经营该批次商品', '王晓峰', 'FINISHED', NOW(), NOW());

CREATE TABLE operation_log (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  operator_name VARCHAR(120),
  module_name VARCHAR(120),
  action_type VARCHAR(120),
  target_name VARCHAR(120),
  detail_info VARCHAR(255),
  status VARCHAR(120),
  created_time DATETIME,
  updated_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO operation_log (operator_name, module_name, action_type, target_name, detail_info, status, created_time, updated_time) VALUES
('李静', '抽样任务', '提交', 'TASK-146-001', '提交夏季冷链食品现场抽样任务', 'SUCCESS', NOW(), NOW()),
('郑岚', '结果公示', '发布', 'REP-146-001', '发布江宁区五月冷链食品抽检结果公示', 'SUCCESS', NOW(), NOW());
