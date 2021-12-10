-- Table: oauth_access_token
-- DROP TABLE oauth_access_token;
CREATE TABLE IF NOT EXISTS oauth_access_token
(
   token_id character varying (256),
   token bytea,
   authentication_id character varying (256) NOT NULL,
   user_name character varying (256),
   client_id character varying (256),
   authentication bytea,
   refresh_token character varying (256),
   CONSTRAINT oauth_access_token_pkey PRIMARY KEY (authentication_id)
)
WITH
(
   OIDS= FALSE
);
ALTER TABLE oauth_access_token OWNER TO ss;
-----------------------------------------------------------------
CREATE TABLE IF NOT EXISTS oauth_approvals
(
   userid character varying (256),
   clientid character varying (256),
   scope character varying (256),
   status character varying (10),
   expiresat timestamp without time zone,
   lastmodifiedat timestamp without time zone
)
WITH
(
   OIDS= FALSE
);
ALTER TABLE oauth_approvals OWNER TO ss;
-----------------------------------------------------
CREATE TABLE IF NOT EXISTS oauth_client_details
(
   client_id character varying (255) NOT NULL,
   client_secret character varying (255) NOT NULL,
   web_server_redirect_uri character varying (2048) DEFAULT NULL::character varying,
   scope character varying (255) DEFAULT NULL::character varying,
   access_token_validity integer,
   refresh_token_validity integer,
   resource_ids character varying (1024) DEFAULT NULL::character varying,
   authorized_grant_types character varying (1024) DEFAULT NULL::character varying,
   authorities character varying (1024) DEFAULT NULL::character varying,
   additional_information character varying (4096) DEFAULT NULL::character varying,
   autoapprove character varying (255) DEFAULT NULL::character varying,
   CONSTRAINT oauth_client_details_pkey PRIMARY KEY (client_id)
)
WITH
(
   OIDS= FALSE
);
ALTER TABLE oauth_client_details OWNER TO ss;
-----------------------------------------------------
-- Table: oauth_client_token
-- DROP TABLE oauth_client_token;
CREATE TABLE IF NOT EXISTS oauth_client_token
(
   token_id character varying (256),
   token bytea,
   authentication_id character varying (256) NOT NULL,
   user_name character varying (256),
   client_id character varying (256),
   CONSTRAINT oauth_client_token_pkey PRIMARY KEY (authentication_id)
)
WITH
(
   OIDS= FALSE
);
ALTER TABLE oauth_client_token OWNER TO ss;
CREATE TABLE IF NOT EXISTS oauth_code
(
   code character varying (256),
   authentication bytea
)
WITH
(
   OIDS= FALSE
);
ALTER TABLE oauth_code OWNER TO ss;
CREATE TABLE IF NOT EXISTS oauth_refresh_token
(
   token_id character varying (256),
   token bytea,
   authentication bytea
)
WITH
(
   OIDS= FALSE
);
ALTER TABLE oauth_refresh_token OWNER TO ss;
CREATE EXTENSION IF NOT EXISTS pgcrypto;
CREATE SEQUENCE IF NOT EXISTS referencecode START 3000;
create TABLE IF NOT EXISTS address
(
   id int8 not null,
   city varchar (255),
   address_line1 varchar (255),
   address_line2 varchar (255),
   province varchar (255),
   version int8,
   zip_code varchar (255),
   created_by varchar (255),
   created_date timestamp,
   last_modified_by varchar (255),
   last_modified_date timestamp,
   primary key (id)
);
create TABLE IF NOT EXISTS auth_otp
(
   id int8 not null,
   created_date timestamp,
   user_email varchar (255),
   otp int4,
   primary key (id)
);
create TABLE IF NOT EXISTS category
(
   id int8 not null,
   created_by varchar (255),
   created_date timestamp,
   last_modified_by varchar (255),
   last_modified_date timestamp,
   name varchar (255),
   status boolean,
   parent_id int8,
   primary key (id)
);
create TABLE IF NOT EXISTS contact_person
(
   id int8 not null,
   created_by varchar (255),
   created_date timestamp,
   last_modified_by varchar (255),
   last_modified_date timestamp,
   contact_no varchar (255),
   first_name varchar (255),
   relationship int4,
   version int8,
   person_id int8,
   primary key (id)
);
create TABLE IF NOT EXISTS customer_auth_token
(
   id int8 not null,
   created_date_time timestamp,
   expire timestamp,
   id_number varchar (255),
   token uuid,
   primary key (id)
);
create TABLE IF NOT EXISTS doctor
(
   id int8 not null,
   created_by varchar (255),
   created_date timestamp,
   last_modified_by varchar (255),
   last_modified_date timestamp,
   business_name varchar (255),
   contact_number varchar (255),
   email varchar (255),
   practice_no varchar (255),
   company_registration_number varchar (255),
   version int8,
   address_id int8,
   primary key (id)
);
create TABLE IF NOT EXISTS doctor_raf_appointment
(
   road_accident_fund_id int8 not null,
   doctor_appointment_id int8 not null,
   primary key
   (
      road_accident_fund_id,
      doctor_appointment_id
   )
);
create TABLE IF NOT EXISTS doctor_appointment
(
   id int8 not null,
   created_by varchar (255),
   created_date timestamp,
   last_modified_by varchar (255),
   last_modified_date timestamp,
   appointment_date date,
   time varchar (255),
   version int8,
   doctor_id int8,
   primary key (id)
);
create TABLE IF NOT EXISTS documents
(
   id int8 not null,
   document_process varchar (255),
   file_url varchar (255) not null,
   name varchar (255) not null,
   path varchar (255),
   personal_name varchar (255),
   reference_no int8 not null,
   size float8 not null,
   type varchar (255),
   version int8,
   category_id int8,
   created_by varchar (255),
   created_date timestamp,
   last_modified_by varchar (255),
   last_modified_date timestamp,
   primary key (id)
);
create TABLE IF NOT EXISTS permission
(
   id int8 not null,
   created_by varchar (255),
   created_date timestamp,
   last_modified_by varchar (255),
   last_modified_date timestamp,
   name varchar (255),
   version int8,
   primary key (id)
);
create TABLE IF NOT EXISTS profile
(
   id int8 not null,
   created_by varchar (255),
   created_date timestamp,
   last_modified_by varchar (255),
   last_modified_date timestamp,
   contact varchar (255),
   email varchar (255) not null,
   first_name varchar (255),
   full_name varchar (255),
   id_number varchar (255) not null,
   last_name varchar (255),
   passport varchar (255),
   version int8,
   address_id int8 not null,
   users_id int8,
   primary key (id)
);
create TABLE IF NOT EXISTS raf_document
(
   road_accident_fund_id int8,
   id int8 not null,
   primary key (id)
);
create TABLE IF NOT EXISTS raf_person
(
   person_id int8,
   raf_id int8 not null,
   primary key (raf_id)
);
create TABLE IF NOT EXISTS reset_token
(
   id int8 not null,
   token uuid,
   username varchar (255) not null,
   version int8,
   primary key (id)
);
create TABLE IF NOT EXISTS road_accident_fund
(
   id int8 not null,
   created_by varchar (255),
   created_date timestamp,
   last_modified_by varchar (255),
   last_modified_date timestamp,
   claim_lodge_date timestamp,
   court_date date,
   date_of_accident date,
   date_submitted date,
   hospital varchar (255),
   hospital_contact_number varchar (255),
   litigation varchar (255),
   merit varchar (255),
   reference_no varchar (255),
   required_document varchar (255),
   version int8,
   primary key (id)
);
create TABLE IF NOT EXISTS role
(
   id int8 not null,
   name varchar (255),
   created_by varchar (255),
   created_date timestamp,
   last_modified_by varchar (255),
   last_modified_date timestamp,
   primary key (id)
);
create TABLE IF NOT EXISTS user_roles
(
   user_id int8 not null,
   role_id int8 not null
);
create TABLE IF NOT EXISTS users
(
   id int8 not null,
   created_by varchar (255),
   created_date timestamp,
   last_modified_by varchar (255),
   last_modified_date timestamp,
   email varchar (255),
   enabled int4 not null,
   last_name varchar (255),
   password varchar (255) not null,
   username varchar (255) not null,
   version int8,
   person_id int8 not null,
   primary key (id)
);
create TABLE IF NOT EXISTS history_jobs
(
   id uuid not null,
   name varchar (255),
   execution_time timestamp not null,
   job_content varchar (255),
   status boolean,
   version int8,
   primary key (id)
);
create TABLE IF NOT EXISTS emergency_contact_person
(
   contact_person_id int8 not null,
   person_id int8 not null,
   primary key
   (
      contact_person_id,
      person_id
   )
);
create TABLE IF NOT EXISTS doctor_address
(
   id int8 not null,
   city varchar (255),
   address_line1 varchar (255),
   address_line2 varchar (255),
   province varchar (255),
   version int8,
   zip_code varchar (255),
   created_by varchar (255),
   created_date timestamp,
   last_modified_by varchar (255),
   last_modified_date timestamp,
   primary key (id)
);
create table email_template
(
   id int8 not null,
   attachment oid,
   body varchar (255),
   to_address varchar (255),
   name varchar (255),
   subject varchar (255),
   created_by varchar (255),
   created_date timestamp,
   last_modified_by varchar (255),
   last_modified_date timestamp,
   primary key (id)
);
alter table emergency_contact_person add constraint FKq9dt8c45gwkkkwuqxcv3sl7vau foreign key (contact_person_id) references contact_person;
alter table emergency_contact_person add constraint FKr0v4l12rbbq2oufsje1hsannfa foreign key (person_id) references profile;
create index idx_id_business_name on doctor
(
   id,
   business_name
);
--alter table doctor_raf_appointment drop constraint UK_2o3g6csyd59kl9yr08m49cnc7;
alter table doctor_raf_appointment add constraint UK_2o3g6csyd59kl9yr08m49cnc7 unique (doctor_appointment_id);
--alter table documents drop constraint UK_5bqbur1fdiut4g90qiglo2tb4;
alter table documents add constraint UK_5bqbur1fdiut4g90qiglo2tb4 unique (file_url);
--alter table documents drop constraint UK_f2ja8c7khxmlkq1y8c1gfchbf;
alter table documents add constraint UK_f2ja8c7khxmlkq1y8c1gfchbf unique (name);
--alter table documents drop constraint UK_a3hshvjouv72v78np25d00ib5;
alter table documents add constraint UK_a3hshvjouv72v78np25d00ib5 unique (reference_no);
create index IDXsli8ydwa494j1r9xc65javt05 on profile
(
   id,
   email
);
--alter table profile drop constraint UK_h4lfia6yb55m850jjmcftwn2p;
alter table profile add constraint UK_h4lfia6yb55m850jjmcftwn2p unique (address_id);
--alter table profile drop constraint UK_9d5dpsf2ufa6rjbi3y0elkdcd;
alter table profile add constraint UK_9d5dpsf2ufa6rjbi3y0elkdcd unique (email);
--alter table profile drop constraint UK_3fmyv5j3ttnt0p2oipomf3624;
alter table profile add constraint UK_3fmyv5j3ttnt0p2oipomf3624 unique (id_number);
--alter table reset_token drop constraint UK_1oefhnc8wglkctym0y0613a8x;
alter table reset_token add constraint UK_1oefhnc8wglkctym0y0613a8x unique (username);
create index idx_id_reference_no on road_accident_fund
(
   id,
   reference_no
);
--alter table road_accident_fund drop constraint UK_da9co1aci27rho8vitamvd3yf;
alter table road_accident_fund add constraint UK_da9co1aci27rho8vitamvd3yf unique (reference_no);
create index idx_id_email_username on users
(
   id,
   email,
   username
);
--alter table users drop constraint UK_r43af9ap4edm43mmtq01oddj6;
alter table users add constraint UK_r43af9ap4edm43mmtq01oddj6 unique (username);
create sequence IF NOT EXISTS hibernate_sequence start 1 increment 1;
alter table category add constraint FK2y94svpmqttx80mshyny85wqr foreign key (parent_id) references category;
alter table contact_person add constraint FK1ndlfkwplv88i5lkwqovt8klt foreign key (person_id) references profile;
alter table doctor_raf_appointment add constraint FKq9dt8c45gwkkkwuqxcv3sl7vu foreign key (doctor_appointment_id) references doctor_appointment;
alter table doctor_raf_appointment add constraint FKr0v4l12rbbq2oufsje1hsannf foreign key (road_accident_fund_id) references road_accident_fund;
alter table doctor_appointment add constraint FKuurnj3pc0o1p3dwlvl4tm6us foreign key (doctor_id) references doctor;
alter table documents add constraint FKf3pjkmt7utlriehmjls24pw88 foreign key (category_id) references category;
alter table profile add constraint FK2hsdsntwy25qr73fsvd7l3wu7 foreign key (address_id) references address;
alter table profile add constraint FKi6d1noonlpe6oyk6pnwc1q49e foreign key (users_id) references users;
alter table raf_document add constraint FKe9vkryqdltuwcw0sifejh4pjj foreign key (road_accident_fund_id) references road_accident_fund;
alter table raf_document add constraint FK6l4lkna1m9kbxwd3jp7lj14cp foreign key (id) references documents;
alter table raf_person add constraint FKiayuw0q8qetf3r6f0g5kc0k6j foreign key (person_id) references profile;
alter table raf_person add constraint FKg50grttu04ro81159rqphmk86 foreign key (raf_id) references road_accident_fund;
alter table user_roles add constraint FKrhfovtciq1l558cw6udg0h0d3 foreign key (role_id) references role;
alter table user_roles add constraint FKhfh9dx7w3ubf1co1vdev94g3f foreign key (user_id) references users;
alter table users add constraint FK79y8jmrg51a1pf778dlqk38if foreign key (person_id) references profile;
alter table category add column required boolean not null default TRUE;
ALTER TABLE road_accident_fund DROP COLUMN IF EXISTS required_document;
alter table road_accident_fund add column category_id int8;
ALTER TABLE road_accident_fund ADD CONSTRAINT categoryfk1 FOREIGN KEY (category_id) REFERENCES category (id);
ALTER TABLE documents DROP COLUMN IF EXISTS personal_name;
ALTER TABLE documents DROP COLUMN IF EXISTS file_url;
ALTER TABLE documents DROP COLUMN IF EXISTS reference_no;
ALTER TABLE profile ALTER COLUMN id_number DROP NOT NULL;
alter table doctor_appointment add column MEDICO_STATUS varchar (255);
ALTER TABLE doctor ADD CONSTRAINT addressfk1 FOREIGN KEY (address_id) REFERENCES doctor_address (id);
ALTER TABLE address ADD COLUMN person_id int8;
ALTER TABLE address ADD CONSTRAINT addressfk FOREIGN KEY (person_id) REFERENCES profile (id);