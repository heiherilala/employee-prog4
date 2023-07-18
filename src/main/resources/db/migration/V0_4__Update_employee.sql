do
$$
begin
        if not exists(select from pg_type where typname = 'csp') then
create type csp as enum ('M1-1A', 'M2-1B', 'OS1-2A', 'OS2-2B', 'OS3-3A', 'OP1', 'OP1A–3B', 'OP1B–4A', 'OP2B–5A', 'OP3–5B');
end if;
        if not exists(select from pg_type where typname = 'sex') then
create type sex as enum ('M', 'F');
end if;
end
$$;

ALTER TABLE employee ADD COLUMN sex sex;
ALTER TABLE employee ADD COLUMN address varchar;
ALTER TABLE employee ADD COLUMN personal_email  varchar unique ;
ALTER TABLE employee ADD COLUMN professional_email varchar unique ;
ALTER TABLE employee ADD COLUMN cin_number varchar;
ALTER TABLE employee ADD COLUMN cin_create_date date;
ALTER TABLE employee ADD COLUMN cin_create_place varchar;
ALTER TABLE employee ADD COLUMN position varchar;
ALTER TABLE employee ADD COLUMN children_number int;
ALTER TABLE employee ADD COLUMN hire_date date;
ALTER TABLE employee ADD COLUMN departure_date date;
ALTER TABLE employee ADD COLUMN csp csp;
ALTER TABLE employee ADD COLUMN cnaps_number varchar;