INSERT INTO user (id, username, password, nickname, avatar, phone, email, role, balance, status) VALUES
(1, 'admin', '123456', 'Admin', '/avatar/admin.jpg', '13800000001', 'admin@harbin.example', 'admin', 0.00, 1),
(2, 'user', '123456', 'Ice City Visitor', '/avatar/user1.jpg', '13800000002', 'user@harbin.example', 'user', 500.00, 1),
(3, 'tourist', '123456', 'Snow Explorer', '/avatar/user2.jpg', '13800000003', 'tourist@harbin.example', 'user', 100.00, 1);

INSERT INTO scenic_spot (id, name, description, detail, location, category, cover_img, images, open_time, close_time, ticket_price, rating, view_count, status) VALUES
(1, 'Ice World', 'Harbin ice and snow theme park', 'Large ice sculpture park for winter tourism.', 'Harbin Songbei District', 'ice', '/img/spot/ice.jpg', '/img/spot/ice1.jpg', '10:00', '21:30', 88.00, 4.8, 1000, 1),
(2, 'Central Street', 'Historic pedestrian street', 'European style buildings and local food.', 'Harbin Daoli District', 'street', '/img/spot/street.jpg', '/img/spot/street1.jpg', '00:00', '24:00', 0.00, 4.7, 2000, 1);

INSERT INTO route (id, title, description, days, difficulty, category, cover_img, estimated_cost, like_count, status, user_id) VALUES
(1, 'Classic Harbin Two Day Trip', 'Ice World, Central Street, and city highlights.', 2, 'easy', 'classic', '/img/route/classic.jpg', 300.00, 12, 1, 1);

INSERT INTO route_spot (id, route_id, spot_id, order_num, stay_hours) VALUES
(1, 1, 1, 1, 4.0),
(2, 1, 2, 2, 2.0);

INSERT INTO ticket_type (id, spot_id, name, price, stock, max_per_order, description, status) VALUES
(1, 1, 'Adult Ticket', 88.00, 50, 5, 'Ice World adult ticket', 1),
(2, 1, 'Student Ticket', 58.00, 30, 3, 'Student discount ticket', 1);

INSERT INTO hotel (id, name, address, type, price_min, price_max, description, cover_img, facilities, rating, status) VALUES
(1, 'Harbin Riverside Hotel', 'No. 1 River Road', 'hotel', 288.00, 688.00, 'Comfortable hotel near the river.', '/img/hotel/river.jpg', 'WiFi,Parking,Restaurant', 4.6, 1);

INSERT INTO restaurant (id, name, address, cuisine_type, price_per_person, description, cover_img, recommended_dish, tags, rating, status) VALUES
(1, 'Harbin Kitchen', 'Central Street 10', 'local', 68.00, 'Local dishes and warm service.', '/img/rest/local.jpg', 'Guobaorou', 'local,classic', 4.5, 1);

INSERT INTO activity (id, title, description, location, start_time, end_time, cover_img, register_deadline, max_participants, current_participants, status) VALUES
(1, 'Winter Culture Walk', 'Guided city walk for visitors.', 'Central Street', '2026-12-01 09:00:00', '2026-12-01 12:00:00', '/img/activity/walk.jpg', '2026-11-30 18:00:00', 20, 0, 'registering');

INSERT INTO travel_note (id, user_id, title, content, cover_img, tags, spot_id, like_count, view_count, status) VALUES
(1, 2, 'A Day in Ice World', 'Useful tips for visiting Harbin Ice World.', '/img/note/ice.jpg', 'ice,guide', 1, 5, 20, 'published'),
(2, 2, 'Draft Travel Note', 'Pending note content.', '/img/note/draft.jpg', 'draft', 1, 0, 0, 'pending');

INSERT INTO review (id, user_id, target_id, target_type, rating, content, images, status) VALUES
(1, 2, 1, 'spot', 5, 'Great winter view.', NULL, 1);

INSERT INTO announcement (id, title, content, type, is_top, status) VALUES
(1, 'Harbin Tourism Notice', 'Welcome to Harbin tourism service platform.', 'notice', 1, 1);

INSERT INTO favorite (id, user_id, target_id, target_type) VALUES
(1, 2, 1, 'spot');
