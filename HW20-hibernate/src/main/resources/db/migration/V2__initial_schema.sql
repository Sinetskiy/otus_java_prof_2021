ALTER TABLE client
    ADD
        address_id bigint;

create table phone
(
    id   bigint not null primary key,
    phone_number varchar(50),
    client_id bigint
);

create table address
(
    id   bigint not null primary key,
    city varchar(50),
    street varchar(50),
    house varchar(50)
);
