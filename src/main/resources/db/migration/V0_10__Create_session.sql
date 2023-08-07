CREATE TABLE if not exists session_user_token (
    id                varchar
    constraint session_user_token_pk primary key default uuid_generate_v4(),
    expered timestamp default now() + interval '2 days',
    employee_id VARCHAR,
    CONSTRAINT fk_employee FOREIGN KEY (employee_id) REFERENCES employee (id)
    );

