create extension if not exists "uuid-ossp";

create table if not exists employee
(
    id                varchar
        constraint group_pk primary key                 default uuid_generate_v4(),
    birth_date date not null default now(),
    first_name varchar not null,
    last_name varchar
);
