drop database if exists meetup;
create database if not exists meetup;
use meetup;

CREATE TABLE location (
    locationId INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100),
    city VARCHAR(50) NOT NULL,
    address VARCHAR(100) NOT NULL,
    zipcode INT(10),
    state VARCHAR(2) NOT NULL
);

CREATE TABLE resources (
	resourceId INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    resource VARCHAR(200) NOT NULL,
    location_id INT NOT NULL,
    CONSTRAINT fk_location_resources_resource FOREIGN KEY (location_id)
        REFERENCES location (locationId)
);

CREATE TABLE `user` (
    userId INT PRIMARY KEY AUTO_INCREMENT,
    fname VARCHAR(20),
    lname VARCHAR(20),
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(100) UNIQUE,
    password_hash VARCHAR(2048) NOT NULL,
    disabled BOOLEAN NOT NULL DEFAULT 0
);

CREATE TABLE `event` (
    eventId INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    `description` VARCHAR(500),
    event_date TIMESTAMP NOT NULL,
    duration INT,
    capacity INT NOT NULL,
    eventLocationId INT NOT NULL,
    category VARCHAR(35) NOT NULL,
    organizerId INT,
    `status` BOOLEAN NOT NULL,
    imageUrl varchar(512) null,
    CONSTRAINT fk_event_location_address FOREIGN KEY (eventLocationId)
        REFERENCES location (locationId),
    CONSTRAINT fk_event_category_group FOREIGN KEY (organizerId)
        REFERENCES `user` (userId)
);

CREATE TABLE app_user_event (
    app_user_id INT NOT NULL,
    app_event_id INT NOT NULL,
    CONSTRAINT pk_app_user_event PRIMARY KEY (app_user_id , app_event_id),
    CONSTRAINT fk_app_user_event_user_id FOREIGN KEY (app_user_id)
        REFERENCES `user` (userId),
    CONSTRAINT fk_app_user_event_event_id FOREIGN KEY (app_event_id)
        REFERENCES `event` (eventId)
);

CREATE TABLE `role` (
    roleId INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE app_user_role (
    app_user_id INT NOT NULL,
    app_role_id INT NOT NULL,
    CONSTRAINT pk_app_user_role PRIMARY KEY (app_user_id , app_role_id),
    CONSTRAINT fk_app_user_role_user_id FOREIGN KEY (app_user_id)
        REFERENCES `user` (userId),
    CONSTRAINT fk_app_user_role_role_id FOREIGN KEY (app_role_id)
        REFERENCES `role` (roleId)
);

delimiter //
create procedure set_known_good_state()
begin
delete from app_user_role;
delete from `role`;
alter table `role` auto_increment=1;
delete from `user`;
alter table `user` auto_increment=1;



insert into `user`(fname, lname, username, email, password_hash, disabled)
values
    ("Nic", "Stuiber", "Nic","Nic@email.com", "$2a$10$BcShvLROaG8KOmH4wc3GMuXhBJRwYwyT5xAXUn0vMcmC6jk9//.Cy", 0),
    ("Trever", "Cramer", "Trever","Trever@email.com", "$2a$10$1vy.12nZqg7.adQsbDFs1O03qLe0RVBn1TS7ftqZ0o00DKHGfTCI.", 0),
    ("Spencer", "Czarnezki", "Spencer","Spencer@email.com", "$2a$10$ylf1jNfQWSPNl6FFZYt5hu.8x2zeejGzcK/AADYfxpJZj3o8mUvfO", 0);



insert into role (title) values
                             ("USER"),
                             ("ADMIN");
insert into app_user_role values
                              (1,2),
                              (2,2),
                              (3,2);



end //

          delimiter ;
