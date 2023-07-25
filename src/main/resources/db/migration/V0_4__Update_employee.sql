do
$$
begin
        if not exists(select from pg_type where typname = 'csp') then
create type csp as enum ('M1_1A', 'M2_1B', 'OS1_2A', 'OS2_2B', 'OS3_3A', 'OP1', 'OP1A_3B', 'OP1B_4A', 'OP2B_5A', 'OP3_5B');
end if;
        if not exists(select from pg_type where typname = 'sex') then
create type sex as enum ('M', 'F');
end if;
end
$$;

ALTER TABLE employee ADD COLUMN if not exists  sex varchar default 'M';
ALTER TABLE employee ADD COLUMN if not exists address varchar;
ALTER TABLE employee ADD COLUMN if not exists personal_email  varchar unique ;
ALTER TABLE employee ADD COLUMN if not exists professional_email varchar unique ;
ALTER TABLE employee ADD COLUMN if not exists cin_number varchar;
ALTER TABLE employee ADD COLUMN if not exists cin_create_date date;
ALTER TABLE employee ADD COLUMN if not exists cin_create_place varchar;
ALTER TABLE employee ADD COLUMN if not exists position varchar;
ALTER TABLE employee ADD COLUMN if not exists children_number int;
ALTER TABLE employee ADD COLUMN if not exists hire_date date;
ALTER TABLE employee ADD COLUMN if not exists departure_date date;
ALTER TABLE employee ADD COLUMN if not exists csp varchar default 'M1_1A';
ALTER TABLE employee ADD COLUMN if not exists cnaps_number varchar;