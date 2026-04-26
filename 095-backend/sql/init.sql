DROP DATABASE IF EXISTS football_league_management_095;
CREATE DATABASE football_league_management_095 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE football_league_management_095;

CREATE TABLE sys_user (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_no VARCHAR(50) NOT NULL,
  username VARCHAR(50) NOT NULL,
  password VARCHAR(100) NOT NULL,
  nickname VARCHAR(50) NOT NULL,
  phone VARCHAR(20),
  email VARCHAR(100),
  avatar VARCHAR(255),
  role VARCHAR(20) NOT NULL,
  status INT DEFAULT 1,
  last_login_time DATETIME,
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY uk_user_no (user_no),
  UNIQUE KEY uk_username (username)
);

CREATE TABLE league_info (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  league_no VARCHAR(50) NOT NULL,
  name VARCHAR(100) NOT NULL,
  region VARCHAR(50),
  organizer VARCHAR(100),
  level_type VARCHAR(50),
  start_date DATE,
  end_date DATE,
  status INT DEFAULT 1,
  remark VARCHAR(255),
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY uk_league_no (league_no)
);

CREATE TABLE season_info (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  season_no VARCHAR(50) NOT NULL,
  league_id BIGINT NOT NULL,
  season_name VARCHAR(100) NOT NULL,
  year_label VARCHAR(50),
  start_date DATE,
  end_date DATE,
  rounds INT DEFAULT 0,
  status INT DEFAULT 1,
  remark VARCHAR(255),
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY uk_season_no (season_no)
);

CREATE TABLE club_info (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  club_no VARCHAR(50) NOT NULL,
  club_name VARCHAR(100) NOT NULL,
  short_name VARCHAR(50),
  city VARCHAR(50),
  founded_year INT,
  chairman VARCHAR(50),
  contact_phone VARCHAR(20),
  status INT DEFAULT 1,
  description VARCHAR(255),
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY uk_club_no (club_no)
);

CREATE TABLE venue_info (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  venue_no VARCHAR(50) NOT NULL,
  name VARCHAR(100) NOT NULL,
  city VARCHAR(50),
  address VARCHAR(255),
  capacity INT DEFAULT 0,
  turf_type VARCHAR(50),
  status INT DEFAULT 1,
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY uk_venue_no (venue_no)
);

CREATE TABLE team_info (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  team_no VARCHAR(50) NOT NULL,
  season_id BIGINT NOT NULL,
  club_id BIGINT NOT NULL,
  venue_id BIGINT NOT NULL,
  team_name VARCHAR(100) NOT NULL,
  home_color VARCHAR(50),
  away_color VARCHAR(50),
  goal_target VARCHAR(100),
  status INT DEFAULT 1,
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY uk_team_no (team_no)
);

CREATE TABLE coach_info (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  coach_no VARCHAR(50) NOT NULL,
  team_id BIGINT NOT NULL,
  name VARCHAR(50) NOT NULL,
  nationality VARCHAR(50),
  age INT,
  formation VARCHAR(50),
  tenure_start DATE,
  phone VARCHAR(20),
  status INT DEFAULT 1,
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY uk_coach_no (coach_no)
);

CREATE TABLE player_info (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  player_no VARCHAR(50) NOT NULL,
  team_id BIGINT NOT NULL,
  name VARCHAR(50) NOT NULL,
  jersey_number INT,
  position VARCHAR(30),
  age INT,
  nationality VARCHAR(50),
  goal_count INT DEFAULT 0,
  assist_count INT DEFAULT 0,
  status INT DEFAULT 1,
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY uk_player_no (player_no)
);

CREATE TABLE match_schedule (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  match_no VARCHAR(50) NOT NULL,
  season_id BIGINT NOT NULL,
  round_no INT DEFAULT 1,
  home_team_id BIGINT NOT NULL,
  away_team_id BIGINT NOT NULL,
  venue_id BIGINT NOT NULL,
  kick_off_time DATETIME,
  home_score INT DEFAULT 0,
  away_score INT DEFAULT 0,
  status VARCHAR(20) DEFAULT 'SCHEDULED',
  referee VARCHAR(50),
  remark VARCHAR(255),
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY uk_match_no (match_no)
);

CREATE TABLE standing_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  season_id BIGINT NOT NULL,
  team_id BIGINT NOT NULL,
  played_count INT DEFAULT 0,
  win_count INT DEFAULT 0,
  draw_count INT DEFAULT 0,
  loss_count INT DEFAULT 0,
  goal_for INT DEFAULT 0,
  goal_against INT DEFAULT 0,
  goal_diff INT DEFAULT 0,
  points INT DEFAULT 0,
  ranking INT DEFAULT 0,
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE news_notice (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(100) NOT NULL,
  content TEXT,
  author_id BIGINT NOT NULL,
  notice_type VARCHAR(50) DEFAULT 'NEWS',
  status INT DEFAULT 1,
  publish_time DATETIME,
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE fan_follow (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  team_id BIGINT NOT NULL,
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO sys_user (id, user_no, username, password, nickname, phone, email, avatar, role, status, last_login_time) VALUES
(1, 'U202603180001', 'admin', '123456', '系统管理员', '13800000001', 'admin@league.com', 'https://example.com/admin.png', 'ADMIN', 1, NOW()),
(2, 'U202603180002', 'manager', '123456', '俱乐部经理', '13800000002', 'manager@league.com', 'https://example.com/manager.png', 'MANAGER', 1, NOW()),
(3, 'U202603180003', 'fan', '123456', '热血球迷', '13800000003', 'fan@league.com', 'https://example.com/fan.png', 'FAN', 1, NOW());

INSERT INTO league_info (id, league_no, name, region, organizer, level_type, start_date, end_date, status, remark) VALUES
(1, 'L202603180001', '华东城市足球超级联赛', '华东', '华东足球协会', '城市联赛', '2026-03-01', '2026-11-30', 1, '当前主赛季'),
(2, 'L202603180002', '高校冠军邀请赛', '全国', '大学生体育联盟', '校园联赛', '2026-04-10', '2026-07-20', 1, '暑期邀请赛');

INSERT INTO season_info (id, season_no, league_id, season_name, year_label, start_date, end_date, rounds, status, remark) VALUES
(1, 'S202603180001', 1, '2026 赛季', '2026', '2026-03-01', '2026-11-30', 18, 1, '主赛季进行中'),
(2, 'S202603180002', 2, '2026 夏季赛', '2026-SUMMER', '2026-04-10', '2026-07-20', 6, 0, '报名阶段');

INSERT INTO club_info (id, club_no, club_name, short_name, city, founded_year, chairman, contact_phone, status, description) VALUES
(1, 'C202603180001', '星海竞技俱乐部', '星海', '上海', 2016, '赵川', '13911110001', 1, '主打法节奏控制'),
(2, 'C202603180002', '东港联足球俱乐部', '东港联', '宁波', 2014, '徐锋', '13911110002', 1, '防守反击风格明显'),
(3, 'C202603180003', '金陵青年足球俱乐部', '金陵青年', '南京', 2018, '陈岳', '13911110003', 1, '青训体系完善'),
(4, 'C202603180004', '杭城先锋足球俱乐部', '杭城先锋', '杭州', 2015, '周齐', '13911110004', 1, '边路推进能力突出');

INSERT INTO venue_info (id, venue_no, name, city, address, capacity, turf_type, status) VALUES
(1, 'V202603180001', '星海体育场', '上海', '浦东新区海港大道 18 号', 32000, '天然草', 1),
(2, 'V202603180002', '东港中心球场', '宁波', '鄞州区体育路 6 号', 26000, '混合草', 1),
(3, 'V202603180003', '金陵青年公园球场', '南京', '建邺区奥体东路 28 号', 18000, '天然草', 1),
(4, 'V202603180004', '杭城先锋竞技场', '杭州', '滨江区冠军路 88 号', 24000, '混合草', 1);

INSERT INTO team_info (id, team_no, season_id, club_id, venue_id, team_name, home_color, away_color, goal_target, status) VALUES
(1, 'T202603180001', 1, 1, 1, '星海竞技一队', '蓝白', '深蓝', '冲击冠军', 1),
(2, 'T202603180002', 1, 2, 2, '东港联一队', '红黑', '白色', '争取前三', 1),
(3, 'T202603180003', 1, 3, 3, '金陵青年一队', '黄色', '黑色', '稳定前四', 1),
(4, 'T202603180004', 1, 4, 4, '杭城先锋一队', '绿色', '白绿', '打进争冠组', 1);

INSERT INTO coach_info (id, coach_no, team_id, name, nationality, age, formation, tenure_start, phone, status) VALUES
(1, 'H202603180001', 1, '林拓', '中国', 43, '4-3-3', '2025-12-01', '13711110001', 1),
(2, 'H202603180002', 2, '高远', '中国', 45, '5-4-1', '2025-11-10', '13711110002', 1),
(3, 'H202603180003', 3, '严峻', '中国', 39, '4-2-3-1', '2026-01-05', '13711110003', 1),
(4, 'H202603180004', 4, '顾宁', '中国', 41, '3-4-3', '2025-10-22', '13711110004', 1);

INSERT INTO player_info (id, player_no, team_id, name, jersey_number, position, age, nationality, goal_count, assist_count, status) VALUES
(1, 'P202603180001', 1, '苏扬', 9, '前锋', 25, '中国', 6, 2, 1),
(2, 'P202603180002', 1, '韩青', 10, '前腰', 24, '中国', 3, 5, 1),
(3, 'P202603180003', 2, '程朗', 11, '边锋', 27, '中国', 4, 1, 1),
(4, 'P202603180004', 2, '邵泽', 1, '门将', 29, '中国', 0, 0, 1),
(5, 'P202603180005', 3, '季峰', 7, '边锋', 23, '中国', 2, 3, 1),
(6, 'P202603180006', 3, '周策', 6, '后腰', 26, '中国', 1, 4, 1),
(7, 'P202603180007', 4, '沈驰', 19, '前锋', 24, '中国', 5, 1, 1),
(8, 'P202603180008', 4, '许安', 8, '中场', 25, '中国', 2, 2, 1);

INSERT INTO match_schedule (id, match_no, season_id, round_no, home_team_id, away_team_id, venue_id, kick_off_time, home_score, away_score, status, referee, remark, create_time, update_time) VALUES
(1, 'M202603180001', 1, 1, 1, 2, 1, DATE_SUB(NOW(), INTERVAL 4 DAY), 2, 1, 'FINISHED', '张磊', '星海竞技下半场完成反超', DATE_SUB(NOW(), INTERVAL 5 DAY), DATE_SUB(NOW(), INTERVAL 4 DAY)),
(2, 'M202603180002', 1, 1, 3, 4, 3, DATE_SUB(NOW(), INTERVAL 3 DAY), 1, 1, 'FINISHED', '李鸣', '双方各拿一分', DATE_SUB(NOW(), INTERVAL 4 DAY), DATE_SUB(NOW(), INTERVAL 3 DAY)),
(3, 'M202603180003', 1, 2, 1, 4, 1, DATE_ADD(NOW(), INTERVAL 2 DAY), 0, 0, 'SCHEDULED', '王涛', '焦点战', DATE_SUB(NOW(), INTERVAL 1 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY)),
(4, 'M202603180004', 1, 2, 2, 3, 2, DATE_ADD(NOW(), INTERVAL 3 DAY), 0, 0, 'SCHEDULED', '陈涛', '争四关键战', DATE_SUB(NOW(), INTERVAL 1 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY));

INSERT INTO standing_record (id, season_id, team_id, played_count, win_count, draw_count, loss_count, goal_for, goal_against, goal_diff, points, ranking) VALUES
(1, 1, 1, 1, 1, 0, 0, 2, 1, 1, 3, 1),
(2, 1, 4, 1, 0, 1, 0, 1, 1, 0, 1, 2),
(3, 1, 3, 1, 0, 1, 0, 1, 1, 0, 1, 3),
(4, 1, 2, 1, 0, 0, 1, 1, 2, -1, 0, 4);

INSERT INTO news_notice (id, title, content, author_id, notice_type, status, publish_time, create_time, update_time) VALUES
(1, '华东城市足球超级联赛首轮战报', '首轮比赛已经结束，星海竞技凭借下半场反超暂列榜首。', 1, 'NEWS', 1, DATE_SUB(NOW(), INTERVAL 6 HOUR), DATE_SUB(NOW(), INTERVAL 6 HOUR), DATE_SUB(NOW(), INTERVAL 6 HOUR)),
(2, '第二轮比赛时间调整通知', '受场地协调影响，第二轮个别场次开球时间调整，请关注最新赛程。', 1, 'NOTICE', 1, DATE_SUB(NOW(), INTERVAL 2 HOUR), DATE_SUB(NOW(), INTERVAL 2 HOUR), DATE_SUB(NOW(), INTERVAL 2 HOUR));

INSERT INTO fan_follow (id, user_id, team_id, create_time) VALUES
(1, 3, 1, DATE_SUB(NOW(), INTERVAL 1 DAY)),
(2, 3, 4, DATE_SUB(NOW(), INTERVAL 3 HOUR));
