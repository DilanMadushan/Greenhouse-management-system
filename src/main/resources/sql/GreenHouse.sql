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
    on update cascade on delete cascade
);

create table lettuce(
  l_id varchar(50)primary key ,
  name varchar(100)not null ,
  Temp int not null ,
  humidity int not null ,
  qty_on_hand double not null ,
  sup_id varchar(30)not null ,
  constraint foreign key (sup_id) references supplier(sup_id)
  on update cascade on delete cascade

);

create table fertilizer(
    f_id varchar(30)primary key ,
    name varchar(100)not null ,
    company varchar(100)not null ,
    qty int not null ,
    sup_id varchar(30) not null ,
    l_id varchar(50)not null ,
    constraint foreign key (sup_id)references supplier(sup_id)
    on update cascade on delete cascade,
    constraint foreign key (l_id)references lettuce(l_id)
    on update cascade on delete cascade
);

create table payment(
    pay_id  varchar(30)primary key,
    amount double not null,
    date DATE not null,
    type varchar(50) not null
);

create table customer(
    cus_id varchar(50)primary key ,
    name varchar(150)not null ,
    address varchar(100)not null ,
    tel varchar(30)not null
);

create table orders(
    order_id varchar(30)primary key ,
    date DATE not null ,
    cus_id varchar(50),
    pay_id  varchar(30),
    constraint foreign key (cus_id)references customer(cus_id)
    on update cascade on delete cascade,
    constraint foreign key (pay_id) references payment(pay_id)
    on update cascade on delete cascade
);

create table order_details(
    order_id varchar(30)not null ,
    l_id varchar(50)not null ,
    qty int not null ,
    constraint foreign key(order_id)references orders(order_id)
    on update cascade on delete cascade,
    constraint foreign key (l_id)references lettuce(l_id)
    on update cascade on delete cascade
);

create table salary(
    sal_id varchar(50)primary key,
    days int not null ,
    full_amount double not null
);

create table employee(
    emp_id varchar(50)primary key ,
    name varchar(100)not null,
    age int not null ,
    address varchar(100)not null ,
    job_role varchar(50)not null ,
    sal_id varchar(50)not null ,
    constraint foreign key (sal_id)references salary(sal_id)
    on update cascade on delete cascade
);

create table greenhouse(
    g_id varchar(50)primary key ,
    name varchar(100)not null ,
    size double not null,
    l_id varchar(50)not null ,
    wt_id varchar(30)not null ,
    constraint foreign key (l_id)references lettuce(l_id)
    on update cascade on delete cascade,
    constraint foreign key (wt_id)references water_tank(wt_id)
    on update cascade on delete cascade
);

create table greenhouse_employee_details(
    emp_id varchar(50)not null ,
    g_id varchar(50)not null ,
    date DATE not null ,
    constraint foreign key (emp_id)references employee(emp_id)
    on update cascade on delete cascade ,
    constraint foreign key (g_id)references greenhouse(g_id)
    on update cascade on delete cascade
);


insert into supplier values ("S001","Kamal","Green Lanka","0763458370","U001");





