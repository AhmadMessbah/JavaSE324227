create table persons
(
    p_id         number primary key,
    name       nvarchar2(30) not null,
    family     nvarchar2(30) not null,
    birth_date date,
    username   varchar2(30) unique,
    password   varchar2(16)  not null,
    is_active  number(1) default 1
);

create sequence member_seq start with 1 increment by 1;


create table jobs
(
    j_id         number primary key,
    job      varchar2(16)  not null,
    company   varchar2(16)  not null,
    start_date date,
    end_date date,
    description  varchar2(100)  not null,
    person_id references persons
);
create sequence job_seq start with 1 increment by 1;


select *
from persons
         join jobs on jobs.person_id = persons.p_id;

select *
from persons
         left join jobs on jobs.person_id = persons.p_id;

select *
from persons
         right join jobs on jobs.person_id = persons.p_id;

select *
from persons
         full outer join jobs on jobs.person_id = persons.p_id;

select * from persons
                  full outer join jobs on jobs.person_id = persons.p_id
where persons.p_id is null;

select * from persons
                  full outer join jobs on jobs.person_id = persons.p_id
where jobs.j_id is null;


select *
from persons
         join jobs on jobs.person_id = persons.p_id
where jobs.job = 'edari';


select jobs.job
from persons
         join jobs on jobs.person_id = persons.p_id
where persons.family='rezaii';