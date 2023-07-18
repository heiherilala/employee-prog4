create table if not exists employee_phon_number (
    id                varchar
    constraint incrementetion_pk primary key                 default uuid_generate_v4(),
    phone_number varchar unique,
    employee_id varchar references employee(id) not null
);