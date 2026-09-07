CREATE TABLE sys_role (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(64),
    code VARCHAR(64)
);

CREATE TABLE sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(64) UNIQUE,
    password VARCHAR(128),
    role_id BIGINT,
    status VARCHAR(16),
    created_at DATETIME,
    FOREIGN KEY (role_id) REFERENCES sys_role(id)
);

CREATE TABLE supplier (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(128),
    contact VARCHAR(64),
    phone VARCHAR(32)
);

CREATE TABLE customer (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(128),
    contact VARCHAR(64),
    phone VARCHAR(32)
);

CREATE TABLE warehouse (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(128),
    code VARCHAR(64),
    address VARCHAR(255)
);

CREATE TABLE zone (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    warehouse_id BIGINT,
    name VARCHAR(128),
    type VARCHAR(32),
    FOREIGN KEY (warehouse_id) REFERENCES warehouse(id)
);

CREATE TABLE location (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    zone_id BIGINT,
    code VARCHAR(64),
    capacity INT,
    FOREIGN KEY (zone_id) REFERENCES zone(id)
);

CREATE TABLE sku (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    code VARCHAR(64) UNIQUE,
    name VARCHAR(128),
    spec VARCHAR(128),
    shelf_life_days INT,
    barcode VARCHAR(64),
    supplier_id BIGINT,
    FOREIGN KEY (supplier_id) REFERENCES supplier(id)
);

CREATE TABLE batch (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    sku_id BIGINT,
    batch_no VARCHAR(64),
    production_date DATE,
    expire_date DATE,
    UNIQUE KEY uq_batch (sku_id, batch_no)
);

CREATE TABLE inventory (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    sku_id BIGINT,
    warehouse_id BIGINT,
    location_id BIGINT,
    batch_id BIGINT,
    quantity_available INT,
    quantity_locked INT,
    quantity_frozen INT,
    UNIQUE KEY uq_inventory (sku_id, warehouse_id, location_id, batch_id)
);

CREATE TABLE inventory_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    sku_id BIGINT,
    batch_id BIGINT,
    warehouse_id BIGINT,
    location_id BIGINT,
    change_type VARCHAR(32),
    change_qty INT,
    ref_type VARCHAR(32),
    ref_id BIGINT,
    created_at DATETIME
);

CREATE TABLE inbound_order (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_no VARCHAR(64),
    warehouse_id BIGINT,
    supplier_id BIGINT,
    status VARCHAR(32),
    created_at DATETIME
);

CREATE TABLE inbound_order_item (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    inbound_id BIGINT,
    sku_id BIGINT,
    batch_no VARCHAR(64),
    quantity INT,
    location_id BIGINT,
    production_date DATE,
    expire_date DATE,
    FOREIGN KEY (inbound_id) REFERENCES inbound_order(id)
);

CREATE TABLE outbound_order (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_no VARCHAR(64),
    warehouse_id BIGINT,
    customer_id BIGINT,
    status VARCHAR(32),
    created_at DATETIME
);

CREATE TABLE outbound_order_item (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    outbound_id BIGINT,
    sku_id BIGINT,
    batch_id BIGINT,
    quantity INT,
    location_id BIGINT,
    FOREIGN KEY (outbound_id) REFERENCES outbound_order(id)
);

CREATE TABLE transfer_order (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_no VARCHAR(64),
    from_warehouse_id BIGINT,
    to_warehouse_id BIGINT,
    status VARCHAR(32),
    created_at DATETIME
);

CREATE TABLE transfer_order_item (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    transfer_id BIGINT,
    sku_id BIGINT,
    batch_id BIGINT,
    quantity INT,
    from_location_id BIGINT,
    to_location_id BIGINT,
    FOREIGN KEY (transfer_id) REFERENCES transfer_order(id)
);

CREATE TABLE stock_lock (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_no VARCHAR(64),
    sku_id BIGINT,
    batch_id BIGINT,
    warehouse_id BIGINT,
    location_id BIGINT,
    quantity INT,
    status VARCHAR(32),
    created_at DATETIME
);

CREATE TABLE stock_check (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_no VARCHAR(64),
    warehouse_id BIGINT,
    status VARCHAR(32),
    created_at DATETIME
);

CREATE TABLE stock_check_item (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    check_id BIGINT,
    sku_id BIGINT,
    batch_id BIGINT,
    location_id BIGINT,
    system_qty INT,
    counted_qty INT,
    diff_qty INT,
    FOREIGN KEY (check_id) REFERENCES stock_check(id)
);

CREATE TABLE notification_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    type VARCHAR(32),
    target VARCHAR(64),
    content VARCHAR(255),
    status VARCHAR(32),
    created_at DATETIME
);

INSERT INTO sys_role(name, code) VALUES ('管理员','ADMIN'), ('仓库员','WAREHOUSE');
INSERT INTO sys_user(username,password,role_id,status,created_at) VALUES ('admin','admin',1,'ENABLED',NOW());
