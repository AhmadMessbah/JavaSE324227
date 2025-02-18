create table jobs
(
    id         number primary key,
    person      varchar2(50)  not null,
    job      varchar2(16)  not null,
    company   varchar2(16)  not null,
    start_date date,
    end_date date,
    description  varchar2(100)  not null

);
create sequence job_seq start with 1 increment by 1;