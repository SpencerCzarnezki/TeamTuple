drop database if exists meetup;
create database if not exists meetup;
use meetup;

drop table if exists app_user_role;
drop table if exists role;
drop table if exists user;


CREATE TABLE category (
    `group` VARCHAR(35) PRIMARY KEY UNIQUE
);

CREATE TABLE event (
    eventId INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    event_date TIMESTAMP NOT NULL,
    duration INT,
    CONSTRAINT fk_event_location_address FOREIGN KEY (address)
        REFERENCES location (address),
    CONSTRAINT fk_event_category_group FOREIGN KEY (`group`)
        REFERENCES category (`group`)
);

CREATE TABLE app_user_event (
    app_user_id INT NOT NULL,
    app_event_id INT NOT NULL,
    CONSTRAINT pk_app_user_event PRIMARY KEY (app_user_id , app_event_id),
    CONSTRAINT fk_app_user_event_user_id FOREIGN KEY (userId)
        REFERENCES user (userId),
    CONSTRAINT fk_app_user_event_event_id FOREIGN KEY (eventId)
        REFERENCES event (eventId)
);

CREATE TABLE user (
    userId INT PRIMARY KEY AUTO_INCREMENT,
    fname VARCHAR(20),
    lname VARCHAR(20),
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(2048) NOT NULL,
    disabled BOOLEAN NOT NULL DEFAULT 0
);

CREATE TABLE location (
    loactionId INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100),
    city VARCHAR(50) NOT NULL,
    address VARCHAR(100) NOT NULL,
    zipcode INT(10),
    state VARCHAR(2),
    resoures varchar(2048)
);

CREATE TABLE role (
    roleId INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE app_user_role (
    app_user_id INT NOT NULL,
    app_role_id INT NOT NULL,
    CONSTRAINT pk_app_user_role PRIMARY KEY (app_user_id , app_role_id),
    CONSTRAINT fk_app_user_role_user_id FOREIGN KEY (userId)
        REFERENCES user (userId),
    CONSTRAINT fk_app_user_role_role_id FOREIGN KEY (roleId)
        REFERENCES role (roleId)
);

insert into role (`name`) values 
    ('USER'),
    ('ADMIN');

insert into user (username, password_hash) values
    ('user', '$2a$10$ZMZEHR/CeSDPh7o0dIUPJeSh6r2TGDMUuBSC.Vff6VGL2CGNUdB5q'),
    ('admin', '$2a$10$L4c8Ky5D5O7WsVm89lTiPO3/pPtME0itveW5GuSI1.vVWSBS1bCJ2');

insert into app_user_role (app_user_id, app_role_id) values
	(1, 1),
    (2, 2);