drop database if exists greenhouse;
create database if not exists greenhouse;

use greenhouse;

create table user(
    user_id varchar(15)primary key ,
    name varchar(100)not null,
    password varchar(20)not null,
    job_role varchar(50)not null
);

create table water_tank(
    wt_id varchar(30)primary key,
    temp int not null ,
    ph double not null
);

insert into user values ("U001","Dilan","Dilan123","Manager");

create table supplier(
    sup_id varchar(30) primary key ,
    name varchar(155)not null,
    company varchar(50)not null,
    tel varchar(20),
    user_id varchar(30)not null ,
    constraint foreign key (user_id) references user(user_id)
    on delete cascade on delete cascade
);

create table lettuce(
  l_id varchar(50)primary key ,
  name varchar(100)not null ,
  Temp int not null ,
  humidity int not null ,
  sup_id varchar(30)not null ,
  constraint foreign key (sup_id) references supplier(sup_id)
  on delete cascade on delete cascade

);
