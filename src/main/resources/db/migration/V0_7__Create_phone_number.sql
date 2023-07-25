CREATE TABLE if not exists phone_number (
      id                varchar
          constraint phone_number_pk primary key                 default uuid_generate_v4(),
      number VARCHAR(50) NOT NULL,
      employee_id VARCHAR,
      company_id VARCHAR,
      CONSTRAINT fk_employee FOREIGN KEY (employee_id) REFERENCES employee (id),
      CONSTRAINT fk_company FOREIGN KEY (company_id) REFERENCES company (id)
);
