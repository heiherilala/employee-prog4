ALTER TABLE employee ADD COLUMN ref varchar;

create table if not exists ref_incrementetion (
    id                varchar
    constraint incrementetion_pk primary key                 default uuid_generate_v4(),
    inccremeentaion_employee int not null
);