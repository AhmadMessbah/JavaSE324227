create table persons
(
    id         number primary key,
    name       nvarchar2(30) not null,
    family     nvarchar2(30) not null,
    birth_date date,
    username   varchar2(30) unique,
    password   varchar2(16)  not null,
    is_active  number(1) default 1
);

create sequence member_seq start with 1 increment by 1;

