INSERT INTO addresses (country, city, postal_code, address,deleted) VALUES ('Serbia', 'Novi Sad', '21000', '54 Alekse Santica',false);
INSERT INTO addresses (country, city, postal_code, address,deleted) VALUES ('Serbia', 'Novi Sad', '21000', '54 Futoska',false);
INSERT INTO addresses (country, city, postal_code, address,deleted) VALUES ('Serbia', 'Novi Sad', '21000', '54 Gogoljeva',false);
INSERT INTO addresses (country, city, postal_code, address,deleted) VALUES ('Serbia', 'Novi Sad', '21000', '54 Puskinova',false);
INSERT INTO addresses (country, city, postal_code, address,deleted) VALUES ('Serbia', 'Novi Sad', '21000', '54 Jevrejska',false);
INSERT INTO addresses (country, city, postal_code, address,deleted) VALUES ('Serbia', 'Novi Sad', '21000', '54 Narodnog fronta',false);
INSERT INTO addresses (country, city, postal_code, address,deleted) VALUES ('Serbia', 'Novi Sad', '21000', '54 Zitni Trg',false);
INSERT INTO addresses (country, city, postal_code, address,deleted) VALUES ('Serbia', 'Novi Sad', '21000', '54 Mise Dimitrijevica',false);
INSERT INTO addresses (country, city, postal_code, address,deleted) VALUES ('Serbia', 'Novi Sad', '21000', '54 Danila Kisa',false);
-- INSERT INTO addresses (country, city, postal_code, address,deleted) VALUES ('Serbia', 'Novi Sad', '21000', '3 Puskinova',false);


INSERT INTO role (name) VALUES ('ROLE_HOST');
INSERT INTO role (name) VALUES ('ROLE_GUEST');
INSERT INTO role (name) VALUES ('ROLE_ADMIN');


INSERT INTO accounts(username, password, status,deleted) VALUES ('pera@example.com', '123', 'ACTIVE',false);
INSERT INTO accounts(username, password, status,deleted) VALUES ('mika@example.com', '123', 'ACTIVE',false);
INSERT INTO accounts(username, password, status,deleted) VALUES ('zika@example.com', '123', 'ACTIVE',false);
INSERT INTO accounts(username, password, status,deleted) VALUES ('kika@example.com', '123', 'ACTIVE',false);



INSERT INTO account_role VALUES (1,1);
INSERT INTO account_role VALUES (2,2);
INSERT INTO account_role VALUES (3,1);
INSERT INTO account_role VALUES (4,3);


INSERT INTO users (first_name, last_name, address_id, phone_number, account_id, last_password_reset_date,deleted)
VALUES ('pera', 'peric', 1, '1234', 1, '2023-01-01',false);
insert into users(first_name,last_name,address_id,phone_number,account_id,last_password_reset_date,deleted)
values ('mika','peric',1,'1234',2,'2023-01-01',false);
insert into users(first_name,last_name,address_id,phone_number,account_id,last_password_reset_date,deleted)
values ('zika','peric',1,'1234',3,'2023-01-01',false);
insert into users(first_name,last_name,address_id,phone_number,account_id,last_password_reset_date,deleted)
values ('kika','peric',1,'1234',4,'2023-01-01',false);



insert into guests values (2);
insert into hosts values (1);
insert into hosts values (3);
--
-- insert into notification_settings (user_id) values (1);
-- insert into notification_settings (user_id) values (2);
-- insert into notification_settings (user_id) values (3);
-- insert into host_notification_settings (id, is_reservation_cancelled, is_request_created, is_rated, is_accommodation_rated) values (1, true, false, true, true);
-- insert into host_notification_settings (id, is_reservation_cancelled, is_request_created, is_rated, is_accommodation_rated) values (3, true, false, true, true);
-- insert into guest_notification_settings (id, is_request_responded) values (2, false);
--
--
-- INSERT INTO timeslots (start_date, end_date, deleted)
-- VALUES ('2024-01-22 10:00:00','2024-01-24 12:00:00', false);
-- INSERT INTO timeslots (start_date, end_date, deleted)
-- VALUES ('2024-03-21 10:00:00','2024-03-30 12:00:00', false);
-- INSERT INTO timeslots (start_date, end_date, deleted)
-- VALUES ('2024-01-21 10:00:00','2024-01-24 12:00:00', false);
-- INSERT INTO timeslots (start_date, end_date, deleted)
-- VALUES ('2024-04-01 10:00:00','2024-04-11 12:00:00', false);
-- INSERT INTO timeslots (start_date, end_date, deleted)
-- VALUES ('2024-04-01 10:00:00','2024-04-11 12:00:00', false);
--
-- INSERT INTO timeslots (start_date, end_date, deleted)
-- VALUES ('2024-04-01 10:00:00','2024-04-11 12:00:00', false);
-- INSERT INTO timeslots (start_date, end_date, deleted)
-- VALUES ('2024-04-01 10:00:00','2024-04-11 12:00:00', false);
-- INSERT INTO timeslots (start_date, end_date, deleted)
-- VALUES ('2024-04-01 10:00:00','2024-04-11 12:00:00', false);
--
-- INSERT INTO timeslots (start_date, end_date, deleted)
-- VALUES ('2023-12-22 10:00:00','2024-01-10 12:00:00', false);
--
-- INSERT INTO timeslots (start_date, end_date, deleted)
-- VALUES ('2023-12-25 10:00:00','2023-12-28 12:00:00', false);
--
--
-- INSERT INTO amenities (amenity_name, deleted) VALUES ('wifi', false);
-- INSERT INTO amenities (amenity_name, deleted) VALUES ('pool', false);
-- INSERT INTO amenities (amenity_name, deleted) VALUES ('air conditioning', false);
-- INSERT INTO amenities (amenity_name, deleted) VALUES ('spa', false);
-- INSERT INTO amenities (amenity_name, deleted) VALUES ('tv', false);
-- INSERT INTO amenities (amenity_name, deleted) VALUES ('breakfast', false);
-- INSERT INTO amenities (amenity_name, deleted) VALUES ('pet friendly', false);
-- INSERT INTO amenities (amenity_name, deleted) VALUES ('bar', false);
-- INSERT INTO amenities (amenity_name, deleted) VALUES ('kitchen', false);
-- INSERT INTO amenities (amenity_name, deleted) VALUES ('balcony', false);
--
--
INSERT INTO accommodations (
    name, description, address_id, min_guest, max_guest,
    acc_type, price_per_guest, automatic_conf, acc_status,
    reservation_deadline,host_id, deleted)
VALUES (
           'Accommodation Name 1',
           'Accommodation Description',
           1,
           1,
           5,
           'HOTEL',
           true,
           true,
           'ACCEPTED',
           2,3,false
       );


INSERT INTO accommodations (
    name, description, address_id, min_guest, max_guest,
    acc_type, price_per_guest, automatic_conf, acc_status,
    reservation_deadline,host_id, deleted)
VALUES (
           'Accommodation Name 2',
           'Accommodation Description',
           2,
           1,
           5,
           'HOTEL',
           true,
           true,
           'ACCEPTED',
           2,3,false
       );


INSERT INTO accommodations (
    name, description, address_id, min_guest, max_guest,
    acc_type, price_per_guest, automatic_conf, acc_status,
    reservation_deadline,host_id, deleted)
VALUES (
           'Accommodation Name 13',
           'Accommodation Description',
           3,
           1,
           5,
           'HOTEL',
           true,
           true,
           'ACCEPTED',
           2,3,false
       );


INSERT INTO accommodations (
    name, description, address_id, min_guest, max_guest,
    acc_type, price_per_guest, automatic_conf, acc_status,
    reservation_deadline,host_id, deleted)
VALUES (
           'Accommodation Name 4',
           'Accommodation Description',
           4,
           1,
           5,
           'HOTEL',
           true,
           true,
           'ACCEPTED',
           2,3,false
       );


INSERT INTO accommodations (
    name, description, address_id, min_guest, max_guest,
    acc_type, price_per_guest, automatic_conf, acc_status,
    reservation_deadline,host_id, deleted)
VALUES (
           'Accommodation Name 5',
           'Accommodation Description',
           5,
           1,
           5,
           'HOTEL',
           true,
           true,
           'ACCEPTED',
           2,3,false
       );


INSERT INTO accommodations (
    name, description, address_id, min_guest, max_guest,
    acc_type, price_per_guest, automatic_conf, acc_status,
    reservation_deadline,host_id, deleted)
VALUES (
           'Accommodation Name 6',
           'Accommodation Description',
           6,
           1,
           5,
           'HOTEL',
           true,
           true,
           'ACCEPTED',
           2,3,false
       );

INSERT INTO accommodations (
    name, description, address_id, min_guest, max_guest,
    acc_type, price_per_guest, automatic_conf, acc_status,
    reservation_deadline,host_id, deleted)
VALUES (
           'Accommodation Name 7',
           'Accommodation Description',
           7,
           1,
           5,
           'HOTEL',
           true,
           true,
           'ACCEPTED',
           2,3,false
       );


INSERT INTO accommodations (
    name, description, address_id, min_guest, max_guest,
    acc_type, price_per_guest, automatic_conf, acc_status,
    reservation_deadline,host_id, deleted)
VALUES (
           'Accommodation Name 8',
           'Accommodation Description',
           8,
           1,
           5,
           'HOTEL',
           true,
           true,
           'ACCEPTED',
           2,3,false
       );

-- INSERT INTO accommodations (
--     name, description, address_id, min_guest, max_guest,
--     acc_type, price_per_guest, automatic_conf, acc_status,
--     reservation_deadline,host_id,deleted)
-- VALUES (
--            'Accommodation Name 2',
--            'Accommodation Description',
--            2,
--            1,
--            5,
--            'HOTEL',
--            true,
--            true,
--            'ACCEPTED',
--            7,1, false
--        );
-- INSERT INTO accommodations (
--     name, description, address_id, min_guest, max_guest,
--     acc_type, price_per_guest, automatic_conf, acc_status,
--     reservation_deadline,host_id,deleted)
-- VALUES (
--            'Accommodation Name 3',
--            'Accommodation Description',
--            3,
--            1,
--            5,
--            'HOTEL',
--            true,
--            true,
--            'ACCEPTED',
--            7,3,false
--        );
--

INSERT INTO timeslots (start_date, end_date, deleted)
VALUES (DATE '2024-01-20',DATE '2024-01-30', false);

INSERT INTO timeslots (start_date, end_date, deleted)
VALUES (DATE '2024-01-20',DATE  '2024-01-30', false);

INSERT INTO timeslots (start_date, end_date, deleted)
VALUES (DATE '2024-02-10',DATE '2024-02-25', false);

INSERT INTO timeslots (start_date, end_date, deleted)
VALUES (DATE '2024-01-20',DATE  '2024-01-30', false);

INSERT INTO timeslots (start_date, end_date, deleted)
VALUES (DATE '2024-01-20',DATE  '2024-01-30', false);

INSERT INTO timeslots (start_date, end_date, deleted)
VALUES (DATE '2024-01-20',DATE  '2024-01-30', false);

INSERT INTO timeslots (start_date, end_date, deleted)
VALUES (DATE '2024-01-20',DATE  '2024-01-30', false);

INSERT INTO timeslots (start_date, end_date, deleted)
VALUES (DATE '2024-01-20',DATE  '2024-01-30', false);

INSERT INTO timeslots (start_date, end_date, deleted)
VALUES (DATE '2024-01-20',DATE  '2024-01-30', false);

INSERT INTO timeslots (start_date, end_date, deleted)
VALUES (DATE '2024-01-20',DATE  '2024-01-30', false);

INSERT INTO timeslots (start_date, end_date, deleted)
VALUES (DATE '2024-01-20',DATE  '2024-01-30', false);

INSERT INTO timeslots (start_date, end_date, deleted)
VALUES (DATE '2024-01-20',DATE  '2024-01-30', false);

INSERT INTO timeslots (start_date, end_date, deleted)
VALUES (DATE '2024-01-20',DATE  '2024-01-30', false);

INSERT INTO timeslots (start_date, end_date, deleted)
VALUES (DATE '2024-01-20',DATE  '2024-01-30', false);

INSERT INTO timeslots (start_date, end_date, deleted)
VALUES (DATE '2024-01-20',DATE  '2024-01-30', false);

INSERT INTO timeslots (start_date, end_date, deleted)
VALUES (DATE '2024-01-20',DATE  '2024-01-30', false);

INSERT INTO timeslots (start_date, end_date, deleted)
VALUES (DATE '2024-01-20',DATE  '2024-01-30', false);






INSERT INTO pricelist_items (time_slot_id, price, deleted) VALUES (1,100.00, false);
INSERT INTO pricelist_items (time_slot_id, price, deleted) VALUES (10,100.00, false);
INSERT INTO pricelist_items (time_slot_id, price, deleted) VALUES (11,100.00, false);
INSERT INTO pricelist_items (time_slot_id, price, deleted) VALUES (12,100.00, false);
INSERT INTO pricelist_items (time_slot_id, price, deleted) VALUES (13,100.00, false);
INSERT INTO pricelist_items (time_slot_id, price, deleted) VALUES (14,100.00, false);
INSERT INTO pricelist_items (time_slot_id, price, deleted) VALUES (15,100.00, false);
INSERT INTO pricelist_items (time_slot_id, price, deleted) VALUES (16,100.00, false);

--
INSERT INTO accommodations_price_list VALUES (1,1);
-- INSERT INTO amenities_accommodation VALUES (1,1);
-- INSERT INTO amenities_accommodation VALUES (1,2);
-- INSERT INTO amenities_accommodation VALUES (2,1);
INSERT INTO accommodations_free_time_slots VALUES (1,2);
INSERT INTO accommodations_free_time_slots VALUES (2,17);
INSERT INTO accommodations_free_time_slots VALUES (3,4);
INSERT INTO accommodations_free_time_slots VALUES (4,5);
INSERT INTO accommodations_free_time_slots VALUES (5,6);
INSERT INTO accommodations_free_time_slots VALUES (6,7);
INSERT INTO accommodations_free_time_slots VALUES (7,8);
-- INSERT INTO accommodations_free_time_slots VALUES (8,9);

INSERT INTO accommodations_price_list VALUES (2,2);
INSERT INTO accommodations_price_list VALUES (3,3);
INSERT INTO accommodations_price_list VALUES (4,4);
INSERT INTO accommodations_price_list VALUES (5,5);
INSERT INTO accommodations_price_list VALUES (6,6);
INSERT INTO accommodations_price_list VALUES (7,7);
INSERT INTO accommodations_price_list VALUES (8,8);

-- INSERT INTO accommodations_free_time_slots VALUES (1,2);
-- INSERT INTO accommodations_free_time_slots VALUES (1,3);
-- INSERT INTO accommodations_free_time_slots VALUES (2,4);
-- INSERT INTO accommodations_free_time_slots VALUES (2,5);
-- INSERT INTO accommodations_free_time_slots VALUES (2,6);
--
-- --
-- -- INSERT INTO comments (comment_text, date, rating, comment_status, guest_id,id, deleted)
-- -- VALUES ('For this price great!', '2023-12-11', 4.5, 'PENDING', 2,1,false);
-- -- INSERT INTO comments (comment_text, date, rating, comment_status, guest_id,id, deleted)
-- -- VALUES ('Okay, can be better.', '2023-12-11', 3.0, 'REPORTED', 2,2,false);
-- -- INSERT INTO comments (comment_text, date, rating, comment_status, guest_id,id,deleted)
-- -- VALUES ('Loved it!', '2023-12-11', 5.0, 'PENDING', 2,3,false);
-- -- INSERT INTO comments (comment_text, date, rating, comment_status, guest_id,id, deleted)
-- -- VALUES ('Very poor', '2023-12-11', 2.0, 'REPORTED', 2,4,false);
-- -- INSERT INTO comments (comment_text, date, rating, comment_status, guest_id,id, deleted)
-- -- VALUES ('Host is great person!', '2023-12-11', 4.5, 'PENDING', 2,5,false);
-- -- INSERT INTO comments (comment_text, date, rating, comment_status, guest_id,id, deleted)
-- -- VALUES ('The man is nice, he did not bothered us at all.', '2023-12-11', 3.0, 'PENDING', 2,6,false);
-- -- INSERT INTO comments (comment_text, date, rating, comment_status, guest_id,id,deleted)
-- -- VALUES ('Good,I am very happy to get to know this nice man!', '2023-12-11', 5.0, 'PENDING', 2,7,false);
-- -- INSERT INTO comments (comment_text, date, rating, comment_status, guest_id,id, deleted)
-- -- VALUES ('Not bad.', '2023-12-11', 2.0, 'ACTIVE', 2,8,false);
-- --
-- -- insert into accommodation_comments values(2,1);
-- -- insert into accommodation_comments values(2,2);
-- -- insert into accommodation_comments values(2,3);
-- -- insert into accommodation_comments values(2,4);
-- --
-- -- insert into host_comments values (1,5);
-- -- insert into host_comments values (1,6);
-- -- insert into host_comments values (1,7);
-- -- insert into host_comments values (1,8);
--
--
--
-- -- INSERT INTO accommodations (name, description, address_id, min_guest, max_guest, acc_type, price_per_guest, automatic_conf, host_id, acc_status, reservation_deadline)
-- -- VALUES
-- --     ('Cozy Apartment', 'A comfortable place for your stay', 1, 2, 4, 'APARTMENT', true, true, 1, 'ACTIVE', 7);
-- -- INSERT INTO accommodations (name, description, address_id, min_guest, max_guest, acc_type, price_per_guest, automatic_conf, host_id, acc_status, reservation_deadline)
-- -- VALUES
-- --     ('Luxury Villa', 'Experience luxury in our beautiful villa', 2, 6, 10, 'VILLA', true, false, 2, 'ACTIVE', 14);
-- -- INSERT INTO accommodations (name, description, address_id, min_guest, max_guest, acc_type, price_per_guest, automatic_conf, host_id, acc_status, reservation_deadline)
-- -- VALUES
-- --     ('Mountain Cabin', 'Escape to nature in our rustic cabin', 1, 2, 4, 'CABIN', true, true, 1, 'INACTIVE', 10);
--
--
-- -- insert into favorite_accommodation values(1,2);
--
--
-- -- INSERT INTO requests (time_slot_id,price,accommodation_id,guest_number,request_status)
-- -- VALUES (,150.00,1, 2,'ACCEPTED' );
-- -- --
-- -- INSERT INTO requests (time_slot_id,price,accommodation_id,guest_number,request_status)
-- -- VALUES (2,3450.00,1, 5,'ACCEPTED' );
--
-- -- INSERT INTO requests (time_slot_id,price,accommodation_id,guest_number,request_status)
-- -- VALUES (21,150.00,10, 2,'ACCEPTED');
--
-- INSERT INTO timeslots (start_date, end_date, deleted)
-- VALUES ('2023-01-29 10:00:00','2023-02-04 12:00:00', false);
-- INSERT INTO timeslots (start_date, end_date, deleted)
-- VALUES ('2023-02-03 10:00:00','2023-02-14 12:00:00', false);
-- INSERT INTO timeslots (start_date, end_date, deleted)
-- VALUES ('2024-03-22 10:00:00','2024-03-26 12:00:00', false);
-- INSERT INTO timeslots (start_date, end_date, deleted)
-- VALUES ('2023-04-22 10:00:00','2023-04-26 12:00:00', false);
-- INSERT INTO timeslots (start_date, end_date, deleted)
-- VALUES ('2024-06-22 10:00:00','2024-06-26 12:00:00', false);
--
INSERT INTO requests (time_slot_id,price,accommodation_id,guest_number,request_status,guest_id,deleted)
VALUES (3,3500.00,1, 5,'ACCEPTED' ,2,false);
--
-- INSERT INTO requests (time_slot_id,price,accommodation_id,guest_number,request_status,guest_id,deleted)
-- VALUES (11,2450.00,3, 2,'ACCEPTED' ,2,false);
--
-- INSERT INTO requests (time_slot_id,price,accommodation_id,guest_number,request_status,guest_id,deleted)
-- VALUES (12,3000.00,3, 2,'ACCEPTED' ,2,false);
--
-- INSERT INTO requests (time_slot_id,price,accommodation_id,guest_number,request_status,guest_id,deleted)
-- VALUES (13,5000.00,1, 2,'ACCEPTED' ,2,false);
--
-- INSERT INTO requests (time_slot_id,price,accommodation_id,guest_number,request_status,guest_id,deleted)
-- VALUES (14,4300.00,1, 2,'ACCEPTED' ,2,false);
--