USE hostel_db;

-- Admin
INSERT INTO staff (name, email, password, phone, role)
VALUES ('Admin', 'admin@hostel.com', 'admin123', '0000000000', 'admin')
ON DUPLICATE KEY UPDATE email=email;

-- Rooms: 4 floors, 4 rooms each, capacity 2
INSERT INTO rooms (floor, room_number, capacity, occupied) VALUES (1, 101, 2, 0);
INSERT INTO rooms (floor, room_number, capacity, occupied) VALUES (1, 102, 2, 0);
INSERT INTO rooms (floor, room_number, capacity, occupied) VALUES (1, 103, 2, 0);
INSERT INTO rooms (floor, room_number, capacity, occupied) VALUES (1, 104, 2, 0);
INSERT INTO rooms (floor, room_number, capacity, occupied) VALUES (2, 201, 2, 0);
INSERT INTO rooms (floor, room_number, capacity, occupied) VALUES (2, 202, 2, 0);
INSERT INTO rooms (floor, room_number, capacity, occupied) VALUES (2, 203, 2, 0);
INSERT INTO rooms (floor, room_number, capacity, occupied) VALUES (2, 204, 2, 0);
INSERT INTO rooms (floor, room_number, capacity, occupied) VALUES (3, 301, 2, 0);
INSERT INTO rooms (floor, room_number, capacity, occupied) VALUES (3, 302, 2, 0);
INSERT INTO rooms (floor, room_number, capacity, occupied) VALUES (3, 303, 2, 0);
INSERT INTO rooms (floor, room_number, capacity, occupied) VALUES (3, 304, 2, 0);
INSERT INTO rooms (floor, room_number, capacity, occupied) VALUES (4, 401, 2, 0);
INSERT INTO rooms (floor, room_number, capacity, occupied) VALUES (4, 402, 2, 0);
INSERT INTO rooms (floor, room_number, capacity, occupied) VALUES (4, 403, 2, 0);
INSERT INTO rooms (floor, room_number, capacity, occupied) VALUES (4, 404, 2, 0);
