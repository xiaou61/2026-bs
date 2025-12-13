CREATE TABLE IF NOT EXISTS elder (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_id BIGINT,
  name VARCHAR(64) NOT NULL,
  gender VARCHAR(16) NOT NULL,
  birth_date DATE NOT NULL,
  phone VARCHAR(32) NOT NULL,
  address VARCHAR(255),
  emergency_contact VARCHAR(255),
  created_at DATETIME NOT NULL,
  INDEX idx_elder_user(user_id),
  CONSTRAINT fk_elder_user FOREIGN KEY (user_id) REFERENCES sys_user(id)
);
CREATE TABLE IF NOT EXISTS measurement (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  elder_id BIGINT NOT NULL,
  type VARCHAR(32) NOT NULL,
  value1 DOUBLE,
  value2 DOUBLE,
  unit VARCHAR(32),
  measured_at DATETIME NOT NULL,
  created_at DATETIME NOT NULL,
  INDEX idx_meas_elder_time(elder_id, measured_at DESC),
  CONSTRAINT fk_meas_elder FOREIGN KEY (elder_id) REFERENCES elder(id)
);
CREATE TABLE IF NOT EXISTS sys_user (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(64) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  role VARCHAR(32) NOT NULL,
  status TINYINT NOT NULL,
  created_at DATETIME NOT NULL
);
CREATE TABLE IF NOT EXISTS appointment (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  elder_id BIGINT NOT NULL,
  doctor_user_id BIGINT NOT NULL,
  start_time DATETIME NOT NULL,
  reason VARCHAR(255),
  status VARCHAR(32) NOT NULL,
  created_at DATETIME NOT NULL,
  INDEX idx_appt_doctor_time(doctor_user_id, start_time),
  INDEX idx_appt_elder(elder_id),
  CONSTRAINT fk_appt_elder FOREIGN KEY (elder_id) REFERENCES elder(id),
  CONSTRAINT fk_appt_doctor FOREIGN KEY (doctor_user_id) REFERENCES sys_user(id)
);
CREATE TABLE IF NOT EXISTS follow_up (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  elder_id BIGINT NOT NULL,
  doctor_user_id BIGINT NOT NULL,
  type VARCHAR(64) NOT NULL,
  due_date DATE NOT NULL,
  note VARCHAR(255),
  status VARCHAR(32) NOT NULL,
  created_at DATETIME NOT NULL,
  INDEX idx_fu_doctor_due(doctor_user_id, due_date),
  INDEX idx_fu_elder(elder_id),
  CONSTRAINT fk_fu_elder FOREIGN KEY (elder_id) REFERENCES elder(id),
  CONSTRAINT fk_fu_doctor FOREIGN KEY (doctor_user_id) REFERENCES sys_user(id)
);
CREATE TABLE IF NOT EXISTS notification (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  title VARCHAR(128) NOT NULL,
  content VARCHAR(255) NOT NULL,
  status VARCHAR(16) NOT NULL,
  created_at DATETIME NOT NULL,
  INDEX idx_notice_user_time(user_id, created_at),
  CONSTRAINT fk_notice_user FOREIGN KEY (user_id) REFERENCES sys_user(id)
);
