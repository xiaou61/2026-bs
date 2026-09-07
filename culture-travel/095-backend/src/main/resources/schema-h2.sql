CREATE TABLE sys_user (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_no VARCHAR(50) NOT NULL,
  username VARCHAR(50) NOT NULL,
  password VARCHAR(100) NOT NULL,
  nickname VARCHAR(50) NOT NULL,
  phone VARCHAR(20),
  email VARCHAR(100),
  avatar VARCHAR(255),
  role VARCHAR(20) NOT NULL,
  status INT DEFAULT 1,
  last_login_time TIMESTAMP,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  UNIQUE (user_no),
  UNIQUE (username)
);

CREATE TABLE league_info (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  league_no VARCHAR(50) NOT NULL,
  name VARCHAR(100) NOT NULL,
  region VARCHAR(50),
  organizer VARCHAR(100),
  level_type VARCHAR(50),
  start_date DATE,
  end_date DATE,
  status INT DEFAULT 1,
  remark VARCHAR(255),
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  UNIQUE (league_no)
);

CREATE TABLE season_info (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  season_no VARCHAR(50) NOT NULL,
  league_id BIGINT NOT NULL,
  season_name VARCHAR(100) NOT NULL,
  year_label VARCHAR(50),
  start_date DATE,
  end_date DATE,
  rounds INT DEFAULT 0,
  status INT DEFAULT 1,
  remark VARCHAR(255),
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  UNIQUE (season_no)
);

CREATE TABLE club_info (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  club_no VARCHAR(50) NOT NULL,
  club_name VARCHAR(100) NOT NULL,
  short_name VARCHAR(50),
  city VARCHAR(50),
  founded_year INT,
  chairman VARCHAR(50),
  contact_phone VARCHAR(20),
  status INT DEFAULT 1,
  description VARCHAR(255),
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  UNIQUE (club_no)
);

CREATE TABLE venue_info (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  venue_no VARCHAR(50) NOT NULL,
  name VARCHAR(100) NOT NULL,
  city VARCHAR(50),
  address VARCHAR(255),
  capacity INT DEFAULT 0,
  turf_type VARCHAR(50),
  status INT DEFAULT 1,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  UNIQUE (venue_no)
);

CREATE TABLE team_info (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  team_no VARCHAR(50) NOT NULL,
  season_id BIGINT NOT NULL,
  club_id BIGINT NOT NULL,
  venue_id BIGINT NOT NULL,
  team_name VARCHAR(100) NOT NULL,
  home_color VARCHAR(50),
  away_color VARCHAR(50),
  goal_target VARCHAR(100),
  status INT DEFAULT 1,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  UNIQUE (team_no)
);

CREATE TABLE coach_info (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  coach_no VARCHAR(50) NOT NULL,
  team_id BIGINT NOT NULL,
  name VARCHAR(50) NOT NULL,
  nationality VARCHAR(50),
  age INT,
  formation VARCHAR(50),
  tenure_start DATE,
  phone VARCHAR(20),
  status INT DEFAULT 1,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  UNIQUE (coach_no)
);

CREATE TABLE player_info (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
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
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  UNIQUE (player_no)
);

CREATE TABLE match_schedule (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  match_no VARCHAR(50) NOT NULL,
  season_id BIGINT NOT NULL,
  round_no INT DEFAULT 1,
  home_team_id BIGINT NOT NULL,
  away_team_id BIGINT NOT NULL,
  venue_id BIGINT NOT NULL,
  kick_off_time TIMESTAMP,
  home_score INT DEFAULT 0,
  away_score INT DEFAULT 0,
  status VARCHAR(20) DEFAULT 'SCHEDULED',
  referee VARCHAR(50),
  remark VARCHAR(255),
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  UNIQUE (match_no)
);

CREATE TABLE standing_record (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
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
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE news_notice (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(100) NOT NULL,
  content CLOB,
  author_id BIGINT NOT NULL,
  notice_type VARCHAR(50) DEFAULT 'NEWS',
  status INT DEFAULT 1,
  publish_time TIMESTAMP,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE fan_follow (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  team_id BIGINT NOT NULL,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
