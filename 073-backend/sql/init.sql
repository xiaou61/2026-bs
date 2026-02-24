DROP DATABASE IF EXISTS hrm_system;
CREATE DATABASE hrm_system DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE hrm_system;

CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    name VARCHAR(50),
    avatar VARCHAR(255),
    role VARCHAR(20) NOT NULL DEFAULT 'employee',
    employee_id BIGINT,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE department (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    parent_id BIGINT DEFAULT 0,
    leader_id BIGINT,
    sort INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE position (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    level INT DEFAULT 1,
    min_salary DECIMAL(10,2),
    max_salary DECIMAL(10,2),
    description TEXT,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE employee (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    emp_no VARCHAR(20) NOT NULL UNIQUE,
    name VARCHAR(50) NOT NULL,
    gender VARCHAR(10),
    phone VARCHAR(20),
    email VARCHAR(100),
    id_card VARCHAR(20),
    birthday DATE,
    address VARCHAR(255),
    department_id BIGINT,
    position_id BIGINT,
    entry_date DATE,
    leave_date DATE,
    status VARCHAR(20) DEFAULT 'trial',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE attendance (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    employee_id BIGINT NOT NULL,
    date DATE NOT NULL,
    clock_in DATETIME,
    clock_out DATETIME,
    status VARCHAR(20) DEFAULT 'normal',
    remark VARCHAR(255),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE leave_request (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    employee_id BIGINT NOT NULL,
    type VARCHAR(20) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    days DECIMAL(4,1),
    reason VARCHAR(500),
    status VARCHAR(20) DEFAULT 'pending',
    approver_id BIGINT,
    approve_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE salary (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    employee_id BIGINT NOT NULL,
    year_month VARCHAR(10) NOT NULL,
    base_salary DECIMAL(10,2),
    performance DECIMAL(10,2),
    allowance DECIMAL(10,2),
    deduction DECIMAL(10,2),
    actual_salary DECIMAL(10,2),
    status VARCHAR(20) DEFAULT 'pending',
    pay_date DATE,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE recruitment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    position_id BIGINT,
    department_id BIGINT,
    count INT DEFAULT 1,
    salary_range VARCHAR(50),
    requirements TEXT,
    status VARCHAR(20) DEFAULT 'open',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE resume (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    recruitment_id BIGINT,
    name VARCHAR(50) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(100),
    education VARCHAR(50),
    experience TEXT,
    status VARCHAR(20) DEFAULT 'pending',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE training (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    trainer VARCHAR(50),
    start_time DATETIME,
    end_time DATETIME,
    location VARCHAR(200),
    max_count INT DEFAULT 50,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE contract (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    employee_id BIGINT NOT NULL,
    contract_no VARCHAR(50),
    type VARCHAR(50),
    start_date DATE,
    end_date DATE,
    status VARCHAR(20) DEFAULT 'active',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE announcement (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    publisher_id BIGINT,
    is_top INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO department VALUES (1, '总经办', 0, NULL, 1, 1, NOW());
INSERT INTO department VALUES (2, '人力资源部', 1, NULL, 2, 1, NOW());
INSERT INTO department VALUES (3, '技术研发部', 1, NULL, 3, 1, NOW());
INSERT INTO department VALUES (4, '市场营销部', 1, NULL, 4, 1, NOW());
INSERT INTO department VALUES (5, '财务部', 1, NULL, 5, 1, NOW());

INSERT INTO position VALUES (1, '总经理', 10, 30000, 50000, '公司最高管理者', 1, NOW());
INSERT INTO position VALUES (2, 'HR经理', 7, 15000, 25000, '人力资源部门负责人', 1, NOW());
INSERT INTO position VALUES (3, 'HR专员', 4, 6000, 10000, '负责招聘、考勤等日常事务', 1, NOW());
INSERT INTO position VALUES (4, '技术总监', 8, 25000, 40000, '技术部门负责人', 1, NOW());
INSERT INTO position VALUES (5, '高级工程师', 6, 15000, 25000, '高级技术开发人员', 1, NOW());
INSERT INTO position VALUES (6, '工程师', 4, 8000, 15000, '技术开发人员', 1, NOW());
INSERT INTO position VALUES (7, '市场经理', 7, 12000, 20000, '市场部门负责人', 1, NOW());
INSERT INTO position VALUES (8, '财务主管', 6, 10000, 18000, '财务部门负责人', 1, NOW());

INSERT INTO employee VALUES (1, 'EMP001', '张三', '男', '13800138001', 'zhangsan@company.com', '110101199001011234', '1990-01-01', '北京市海淀区', 3, 5, '2020-03-15', NULL, 'regular', NOW());
INSERT INTO employee VALUES (2, 'EMP002', '李四', '男', '13800138002', 'lisi@company.com', '110101199103021234', '1991-03-02', '北京市朝阳区', 3, 6, '2021-06-01', NULL, 'regular', NOW());
INSERT INTO employee VALUES (3, 'EMP003', '王五', '女', '13800138003', 'wangwu@company.com', '110101199205031234', '1992-05-03', '北京市西城区', 2, 3, '2022-01-10', NULL, 'regular', NOW());
INSERT INTO employee VALUES (4, 'EMP004', '赵六', '男', '13800138004', 'zhaoliu@company.com', '110101199307041234', '1993-07-04', '北京市东城区', 4, 7, '2022-09-01', NULL, 'regular', NOW());
INSERT INTO employee VALUES (5, 'EMP005', '钱七', '女', '13800138005', 'qianqi@company.com', '110101199409051234', '1994-09-05', '北京市丰台区', 5, 8, '2023-02-20', NULL, 'regular', NOW());
INSERT INTO employee VALUES (6, 'EMP006', '孙八', '男', '13800138006', 'sunba@company.com', '110101199511061234', '1995-11-06', '北京市昌平区', 3, 6, '2023-07-01', NULL, 'trial', NOW());
INSERT INTO employee VALUES (7, 'EMP007', '周九', '女', '13800138007', 'zhoujiu@company.com', '110101199601071234', '1996-01-07', '北京市通州区', 3, 6, '2024-01-15', NULL, 'trial', NOW());
INSERT INTO employee VALUES (8, 'EMP008', '吴十', '男', '13800138008', 'wushi@company.com', '110101199703081234', '1997-03-08', '北京市大兴区', 2, 3, '2024-03-01', NULL, 'trial', NOW());
INSERT INTO employee VALUES (9, 'EMP009', '郑明', '男', '13800138009', 'zhengming@company.com', '110101199805091234', '1998-05-09', '北京市顺义区', 4, 7, '2023-11-01', NULL, 'regular', NOW());
INSERT INTO employee VALUES (10, 'EMP010', '陈华', '女', '13800138010', 'chenhua@company.com', '110101199907101234', '1999-07-10', '北京市房山区', 5, 8, '2024-02-01', NULL, 'trial', NOW());

INSERT INTO user VALUES (1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', '管理员', NULL, 'admin', NULL, 1, NOW());
INSERT INTO user VALUES (2, 'hr', 'e10adc3949ba59abbe56e057f20f883e', 'HR王五', NULL, 'hr', 3, 1, NOW());
INSERT INTO user VALUES (3, 'zhangsan', 'e10adc3949ba59abbe56e057f20f883e', '张三', NULL, 'employee', 1, 1, NOW());

UPDATE department SET leader_id = 1 WHERE id = 1;
UPDATE department SET leader_id = 3 WHERE id = 2;
UPDATE department SET leader_id = 1 WHERE id = 3;
UPDATE department SET leader_id = 4 WHERE id = 4;
UPDATE department SET leader_id = 5 WHERE id = 5;

INSERT INTO attendance VALUES (1, 1, CURDATE(), CONCAT(CURDATE(), ' 08:55:00'), CONCAT(CURDATE(), ' 18:05:00'), 'normal', NULL, NOW());
INSERT INTO attendance VALUES (2, 2, CURDATE(), CONCAT(CURDATE(), ' 09:10:00'), CONCAT(CURDATE(), ' 18:00:00'), 'late', '迟到10分钟', NOW());
INSERT INTO attendance VALUES (3, 3, CURDATE(), CONCAT(CURDATE(), ' 08:50:00'), CONCAT(CURDATE(), ' 18:30:00'), 'normal', NULL, NOW());
INSERT INTO attendance VALUES (4, 4, CURDATE(), CONCAT(CURDATE(), ' 09:00:00'), NULL, 'normal', NULL, NOW());
INSERT INTO attendance VALUES (5, 1, DATE_SUB(CURDATE(), INTERVAL 1 DAY), CONCAT(DATE_SUB(CURDATE(), INTERVAL 1 DAY), ' 08:58:00'), CONCAT(DATE_SUB(CURDATE(), INTERVAL 1 DAY), ' 18:02:00'), 'normal', NULL, NOW());
INSERT INTO attendance VALUES (6, 2, DATE_SUB(CURDATE(), INTERVAL 1 DAY), CONCAT(DATE_SUB(CURDATE(), INTERVAL 1 DAY), ' 08:45:00'), CONCAT(DATE_SUB(CURDATE(), INTERVAL 1 DAY), ' 17:30:00'), 'early', '早退30分钟', NOW());

INSERT INTO leave_request VALUES (1, 1, 'annual', DATE_ADD(CURDATE(), INTERVAL 7 DAY), DATE_ADD(CURDATE(), INTERVAL 9 DAY), 3, '回老家探亲', 'approved', 2, NOW(), NOW());
INSERT INTO leave_request VALUES (2, 2, 'sick', DATE_ADD(CURDATE(), INTERVAL 3 DAY), DATE_ADD(CURDATE(), INTERVAL 4 DAY), 2, '身体不适需要休息', 'pending', NULL, NULL, NOW());
INSERT INTO leave_request VALUES (3, 6, 'personal', DATE_ADD(CURDATE(), INTERVAL 5 DAY), DATE_ADD(CURDATE(), INTERVAL 5 DAY), 1, '处理私人事务', 'pending', NULL, NULL, NOW());

INSERT INTO salary VALUES (1, 1, '2024-01', 15000, 3000, 1500, 500, 19000, 'paid', '2024-02-05', NOW());
INSERT INTO salary VALUES (2, 2, '2024-01', 10000, 2000, 1000, 300, 12700, 'paid', '2024-02-05', NOW());
INSERT INTO salary VALUES (3, 3, '2024-01', 8000, 1500, 800, 200, 10100, 'paid', '2024-02-05', NOW());
INSERT INTO salary VALUES (4, 1, '2024-02', 15000, 3500, 1500, 450, 19550, 'pending', NULL, NOW());
INSERT INTO salary VALUES (5, 2, '2024-02', 10000, 2200, 1000, 280, 12920, 'pending', NULL, NOW());

INSERT INTO recruitment VALUES (1, 6, 3, 2, '10000-18000', '1. 本科及以上学历\n2. 3年以上Java开发经验\n3. 熟悉Spring Boot、MyBatis等框架\n4. 良好的沟通能力和团队协作精神', 'open', NOW());
INSERT INTO recruitment VALUES (2, 3, 2, 1, '6000-10000', '1. 大专及以上学历\n2. 1年以上人力资源工作经验\n3. 熟悉劳动法律法规\n4. 具备良好的沟通协调能力', 'open', NOW());
INSERT INTO recruitment VALUES (3, 7, 4, 1, '12000-20000', '1. 本科及以上学历\n2. 3年以上市场营销经验\n3. 有互联网行业经验优先\n4. 具备较强的市场分析能力', 'closed', NOW());

INSERT INTO resume VALUES (1, 1, '刘建国', '13900139001', 'liujianguo@email.com', '本科-计算机科学', '5年Java开发经验，熟悉分布式系统开发', 'interview', NOW());
INSERT INTO resume VALUES (2, 1, '王小明', '13900139002', 'wangxiaoming@email.com', '硕士-软件工程', '3年开发经验，精通Spring Cloud微服务', 'pending', NOW());
INSERT INTO resume VALUES (3, 2, '李小红', '13900139003', 'lixiaohong@email.com', '本科-人力资源管理', '2年HR经验，熟悉招聘流程', 'passed', NOW());

INSERT INTO training VALUES (1, 'Spring Boot高级开发培训', '深入学习Spring Boot框架的高级特性和最佳实践', '技术总监', DATE_ADD(NOW(), INTERVAL 7 DAY), DATE_ADD(NOW(), INTERVAL 8 DAY), '三楼会议室', 30, 1, NOW());
INSERT INTO training VALUES (2, '新员工入职培训', '公司文化、规章制度、办公系统使用培训', 'HR经理', DATE_ADD(NOW(), INTERVAL 3 DAY), DATE_ADD(NOW(), INTERVAL 3 DAY), '一楼培训室', 20, 1, NOW());
INSERT INTO training VALUES (3, '项目管理方法论', 'Scrum敏捷开发方法培训', '外聘讲师', DATE_ADD(NOW(), INTERVAL 14 DAY), DATE_ADD(NOW(), INTERVAL 15 DAY), '五楼多功能厅', 50, 1, NOW());

INSERT INTO contract VALUES (1, 1, 'HT2020001', '正式合同', '2020-03-15', '2025-03-14', 'active', NOW());
INSERT INTO contract VALUES (2, 2, 'HT2021001', '正式合同', '2021-06-01', '2024-05-31', 'active', NOW());
INSERT INTO contract VALUES (3, 3, 'HT2022001', '正式合同', '2022-01-10', '2025-01-09', 'active', NOW());
INSERT INTO contract VALUES (4, 6, 'HT2023001', '试用期合同', '2023-07-01', '2024-01-01', 'active', NOW());
INSERT INTO contract VALUES (5, 7, 'HT2024001', '试用期合同', '2024-01-15', '2024-07-14', 'active', NOW());

INSERT INTO announcement VALUES (1, '关于2024年春节放假安排的通知', '根据国务院办公厅通知精神，结合公司实际情况，现将2024年春节放假安排通知如下：\n1. 放假时间：2024年2月9日至2月17日，共9天\n2. 2月18日（星期日）正常上班\n请各部门提前做好工作安排，祝大家春节快乐！', 1, 1, 1, NOW());
INSERT INTO announcement VALUES (2, '关于开展年度体检的通知', '为关爱员工身体健康，公司将组织全体员工进行年度体检，具体安排如下：\n1. 体检时间：2024年3月1日-3月15日\n2. 体检地点：XX医院体检中心\n请各部门统计体检人员名单，于2月25日前提交至人力资源部。', 2, 0, 1, NOW());
INSERT INTO announcement VALUES (3, '技术分享会通知', '本周五下午3点将在三楼会议室举行技术分享会，主题：微服务架构设计实践。欢迎感兴趣的同事参加。', 1, 0, 1, NOW());
