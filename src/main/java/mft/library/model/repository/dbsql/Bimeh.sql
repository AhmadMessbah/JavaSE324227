create table bimeh
(
    b_id             number primary key,
    policyNumber     nvarchar2(30) not null unique,
    insuranceType    nvarchar2(30) not null
        check (insuranceType in ('CAR', 'LIFE', 'HEALTH', 'PROPERTY')),
    startDate        date not null,
    endDate          date not null,
    status           nvarchar2(30) not null
        check (status in ('ACTIVE', 'EXPIRED', 'CANCELLED')),

    person_id        number not null references persons(p_id)
);
create sequence bimeh_seq start with 1 increment by 1;