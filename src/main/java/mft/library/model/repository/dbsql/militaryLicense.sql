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

create sequence person_seq start with 1 increment by 1;

create table military_license
(
--     militaryId, person, startMilitaryDate, endMilitaryDate, type, city
    id                  number generated always as identity primary key,
    military_id         number        not null,
--     military_card_num   nvarchar2(10) null unique,
    military_type       nvarchar2(30) not null,
    province            nvarchar2(40) not null,
    start_military_date date,
    end_military_date   date,
    is_deleted          number(1) default 0,
    person_id           number        not null references persons (id)
);

-- create sequence military_license_seq start with 1 increment by 1;