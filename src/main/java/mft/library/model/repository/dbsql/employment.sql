
create table employment
(
    e_id            number primary key,
    job           varchar2(16)  not null,
    department    varchar2(16)  not null,
    start_date    date,
    end_date      date,
    salary        number  not null,
    person_id        number not null references persons(p_id));
create sequence employment_seq start with 1 increment by 1;


