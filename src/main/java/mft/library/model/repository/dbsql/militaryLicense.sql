create table military_license
(
--     militaryId, person, startMilitaryDate, endMilitaryDate, type, city
    id                  number generated always as identity primary key,
    military_id         number        not null,
    first_name          nvarchar2(30) not null,
    last_name           nvarchar2(30) not null,
    military_type       nvarchar2(30) not null,
    province            nvarchar2(40) not null,
    start_military_date date,
    end_military_date   date,
    is_deleted number(1) default 0
);