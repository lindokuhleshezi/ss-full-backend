/* ======================================= ADDRESS ====================================== */
INSERT INTO address (
id, city, address_line1, address_line2, province, version, zip_code, created_by, created_date) VALUES (
'1'::bigint, 'Pretoria'::character varying, 'Line 1'::character varying, 'Line 2'::character varying, 'Gauteng'::character varying, '1'::bigint, '7441'::character varying, 'system', NOW()::timestamp)
 returning id;
/* ======================================= PROFILE ====================================== */
INSERT INTO profile (
id, contact, email, first_name, full_name, id_number, last_name, version, address_id, created_by, created_date) VALUES (
'1'::bigint, '\xc30d040703027784547e4c5f795d70d24901e6f45637a91e82d23593e3c6af60d529f0a9b1bf3803421ab14787846eea4c7ece74f9cd7556b7595c946cfd2fe84d55271166b824539a930fb1c67a8d2e607370fa14b962cfc52f'::character varying, 'email@email.com'::character varying, 'Slabbert'::character varying, 'Default'::character varying, '\xc30d04070302e214e0aeb83443a868d249013a987dadce9a3a4d94504917daa37d9e0aaec9030afbce3a63da226411eb80fe0e24996b446b32f493d2fb25345f7bbd89c3e3512238f019d759d737d4131526e75f155d7dc8739d'::character varying, 'Slabbert'::character varying, '1'::bigint, '1'::bigint, 'system', NOW()::timestamp)
 returning id;
/* ======================================= ROLES ====================================== */
INSERT INTO role (
id, name, created_by, created_date) VALUES (
'1'::bigint, 'Admin'::character varying, 'system', NOW()::timestamp)
 returning id;
INSERT INTO role (
id, name, created_by, created_date) VALUES (
'2'::bigint, 'Employee'::character varying, 'system', NOW()::timestamp)
 returning id;
INSERT INTO role (
id, name, created_by, created_date) VALUES (
'3'::bigint, 'Customer'::character varying, 'system', NOW()::timestamp)
 returning id;
/* ======================================= USERS ====================================== */
INSERT INTO users (
id, email, enabled, last_name, password, username, version, person_id, created_by, created_date) VALUES (
'1'::bigint, 'slabbutp@slabbert-admin.co.za'::character varying, '1'::integer, 'Administrator'::character varying, '{bcrypt}$2a$10$QHkA0ApKKqS0/Q/QgiSmeeTJqjNyIdBDKtCCgzZqVRH.yjDNGTMl6'::character varying, 'slabbutp@slabbert-admin.co.za'::character varying, '1'::bigint, '1'::bigint, 'system', NOW()::timestamp)
 returning id;

/* ======================================= USER_ROLES ====================================== */
INSERT INTO user_roles(user_id, role_id)VALUES (1, 1);
INSERT INTO user_roles(user_id, role_id)VALUES (1, 2);
INSERT INTO user_roles(user_id, role_id)VALUES (1, 3);

/*INSERT INTO oauth_client_details(
client_id, client_secret, web_server_redirect_uri, scope, access_token_validity, refresh_token_validity, resource_ids, authorized_grant_types, authorities, additional_information, autoapprove)
VALUES ('web','{bcrypt}$2a$10$QHkA0ApKKqS0/Q/QgiSmeeTJqjNyIdBDKtCCgzZqVRH.yjDNGTMl6','','READ,WRITE',4300,4300,'slabbert','password,authorization_code, refresh_token','','','');*/

INSERT INTO oauth_client_details(
            client_id, client_secret, web_server_redirect_uri, scope, access_token_validity, 
            refresh_token_validity, resource_ids, authorized_grant_types, autoapprove)
    VALUES ('slabbert','{bcrypt}$2a$10$QHkA0ApKKqS0/Q/QgiSmeeTJqjNyIdBDKtCCgzZqVRH.yjDNGTMl6','http://localhost:8282','READ,WRITE',43000,43000,'slabbert','password,refresh_token,authorization_code','1');

INSERT INTO category (id, name, status, required, created_by, created_date) VALUES ('1'::bigint, 'Adult', TRUE, TRUE, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, created_by, created_date) VALUES ('2'::bigint, 'Minor', TRUE, TRUE, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, created_by, created_date) VALUES ('3'::bigint, 'Deceased', TRUE, TRUE, 'system', NOW()::timestamp);
--adult sub categories
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('4'::bigint, 'Claimant Identity document', TRUE, TRUE, '1'::bigint, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('5'::bigint, 'Injured person Identity document', TRUE, TRUE, '1'::bigint, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('6'::bigint, 'Special Power of Attorney', TRUE, TRUE, '1'::bigint, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('7'::bigint, 'Consent', TRUE, TRUE, '1'::bigint, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('8'::bigint, 'Affidavit', TRUE, TRUE, '1'::bigint, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('9'::bigint, 'Medical Records', TRUE, TRUE, '1'::bigint, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('10'::bigint, 'RAF 1 form', TRUE, TRUE, '1'::bigint, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('11'::bigint, 'Accident Report', TRUE, TRUE, '1'::bigint, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('12'::bigint, 'Medical Expenses', TRUE, TRUE, '1'::bigint, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('13'::bigint, 'Invoices / Receipts', TRUE, TRUE, '1'::bigint, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('14'::bigint, 'Payslips', TRUE, TRUE, '1'::bigint, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('15'::bigint, 'Employment Certificate', TRUE, TRUE, '1'::bigint, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('16'::bigint, 'Witness Statements', TRUE, TRUE, '1'::bigint, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('17'::bigint, 'Senior Certificate/Qualifications', TRUE, TRUE, '1'::bigint, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('18'::bigint, 'Fee Agreement', TRUE, TRUE, '1'::bigint, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('19'::bigint, 'Photos of Injuries', TRUE, TRUE, '1'::bigint, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('20'::bigint, 'Photos of Accident Scene (Vehicle/s)', TRUE, TRUE, '1'::bigint, 'system', NOW()::timestamp);

--minor sub categories
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('21'::bigint, 'Claimant Identity document', TRUE, TRUE, '2'::bigint, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('22'::bigint, 'Unabridged Birth Certificate', TRUE, TRUE, '2'::bigint, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('23'::bigint, 'Special Power of Attorney', TRUE, TRUE, '2'::bigint, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('24'::bigint, 'Consent', TRUE, TRUE, '2'::bigint, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('25'::bigint, 'Affidavit', TRUE, TRUE, '2'::bigint, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('26'::bigint, 'Medical Records', TRUE, TRUE, '2'::bigint, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('27'::bigint, 'RAF 1 form', TRUE, TRUE, '2'::bigint, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('28'::bigint, 'Accident Report', TRUE, TRUE, '2'::bigint, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('29'::bigint, 'Medical Expenses', TRUE, TRUE, '2'::bigint, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('30'::bigint, 'Invoices / Receipts', TRUE, TRUE, '2'::bigint, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('31'::bigint, 'Parent Qualifications', TRUE, TRUE, '2'::bigint, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('32'::bigint, 'Witness Statements', TRUE, TRUE, '2'::bigint, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('33'::bigint, 'School Reports', TRUE, TRUE, '2'::bigint, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('34'::bigint, 'Fee Agreement', TRUE, TRUE, '2'::bigint, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('35'::bigint, 'Photos of Injuries', TRUE, TRUE, '2'::bigint, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('36'::bigint, 'Photos of Accident Scene (Vehicle/s)', TRUE, TRUE, '2'::bigint, 'system', NOW()::timestamp);


--deceased sub categories
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('37'::bigint, 'Claimant Identity document', TRUE, TRUE, '3'::bigint, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('38'::bigint, 'Death Certificate', TRUE, TRUE, '3'::bigint, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('39'::bigint, 'Special Power of Attorney', TRUE, TRUE, '3'::bigint, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('40'::bigint, 'Consent', TRUE, TRUE, '3'::bigint, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('41'::bigint, 'Affidavit', TRUE, TRUE, '3'::bigint, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('42'::bigint, 'Medical Records', TRUE, TRUE, '3'::bigint, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('43'::bigint, 'RAF 1 form', TRUE, TRUE, '3'::bigint, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('44'::bigint, 'Accident Report', TRUE, TRUE, '3'::bigint, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('45'::bigint, 'Medical Expenses', TRUE, TRUE, '3'::bigint, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('46'::bigint, 'Invoices / Receipts', TRUE, TRUE, '3'::bigint, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('47'::bigint, 'Payslips', TRUE, TRUE, '3'::bigint, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('48'::bigint, 'Witness Statements', TRUE, TRUE, '3'::bigint, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('49'::bigint, 'School Reports', TRUE, TRUE, '3'::bigint, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('50'::bigint, 'Fee Agreement', TRUE, TRUE, '3'::bigint, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('51'::bigint, 'Post Mortim Report', TRUE, TRUE, '3'::bigint, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('52'::bigint, 'Marriage Certificate', TRUE, TRUE, '3'::bigint, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('53'::bigint, 'Unabridged Birth Certificate', TRUE, TRUE, '3'::bigint, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('54'::bigint, 'Notice of Death', TRUE, TRUE, '3'::bigint, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('55'::bigint, 'Funeral Expenses', TRUE, TRUE, '3'::bigint, 'system', NOW()::timestamp);
INSERT INTO category (id, name, status, required, parent_id, created_by, created_date) VALUES ('56'::bigint, 'Burial Order', TRUE, TRUE, '3'::bigint, 'system', NOW()::timestamp);

alter sequence address_sequence restart with 2;
alter sequence users_sequence restart with 2;
alter sequence profile_sequence restart with 2;
