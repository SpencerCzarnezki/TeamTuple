drop database if exists meetup;
create database if not exists meetup;
use meetup;

drop table if exists app_user_role;
drop table if exists role;
drop table if exists user;


create table event(
eventId int not null  primary key auto_increment,
title varchar(100) not null,
description varchar(500)

);

create table user(
userId int primary key auto_increment,
fname varchar(20),
lname varchar(20),
username varchar(100) not null unique,
email varchar(100) not null unique,
password_hash varchar(2048) not null,
disabled boolean not null default 0
);

create table location(
loactionId int not null primary key auto_increment,
title varchar(100),
city varchar(50) not null,
address varchar(100) not null,
zipcode int(10),
state varchar(2)
);

create table role(
roleId int primary key auto_increment,
title varchar(20) not null unique
);

create table app_user_role (
    app_user_id int not null,
    app_role_id int not null,
    constraint pk_app_user_role
        primary key (app_user_id, app_role_id),
    constraint fk_app_user_role_user_id
        foreign key (userId)
         references user(userId),
	constraint fk_app_user_role_role_id
         foreign key (roleId)
         references role(roleId)
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