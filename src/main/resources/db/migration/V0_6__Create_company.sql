CREATE TABLE if not exists company (
         id                varchar
             constraint company_pk primary key                 default uuid_generate_v4(),
         name VARCHAR(255),
         description TEXT,
         logo BYTEA,
         slogan VARCHAR(255),
         address VARCHAR(255),
         email VARCHAR(255),
         stat VARCHAR(255),
         rcs VARCHAR(255)
);

ALTER TABLE employee ADD COLUMN if not exists  company_id varchar references company (id);
ALTER TABLE ref_incrementetion ADD COLUMN if not exists  company_id varchar references company (id);