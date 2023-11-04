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
