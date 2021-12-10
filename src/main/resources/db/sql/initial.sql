======================================= ADDRESS ======================================
INSERT INTO public.address (
id, city, address_line1, address_line2, province, version, zip_code) VALUES (
'1'::bigint, 'Cape Town'::character varying, 'Line 1'::character varying, 'Line 2'::character varying, 'Gauteng'::character varying, '1'::bigint, '7441'::character varying)
 returning id;
======================================= PROFILE ======================================
INSERT INTO public.profile (
id, contact, email, first_name, full_name, id_number, last_name, version, address_id) VALUES (
'1'::bigint, '\xc30d04070302e214e0aeb83443a868d249013a987dadce9a3a4d94504917daa37d9e0aaec9030afbce3a63da226411eb80fe0e24996b446b32f493d2fb25345f7bbd89c3e3512238f019d759d737d4131526e75f155d7dc8739d'::character varying, '3263551@myuwc.ac.za'::character varying, 'Slabbert'::character varying, 'Default'::character varying, '\xc30d04070302e214e0aeb83443a868d249013a987dadce9a3a4d94504917daa37d9e0aaec9030afbce3a63da226411eb80fe0e24996b446b32f493d2fb25345f7bbd89c3e3512238f019d759d737d4131526e75f155d7dc8739d'::character varying, 'Slabbert'::character varying, '1'::bigint, '1'::bigint)
 returning id;
======================================= ROLES ======================================
INSERT INTO public.role (
id, name) VALUES (
'1'::bigint, 'Admin'::character varying)
 returning id;
INSERT INTO public.role (
id, name) VALUES (
'2'::bigint, 'Employee'::character varying)
 returning id;
======================================= USERS ======================================
INSERT INTO public.users (
id, enabled, password, username) VALUES (
'1'::bigint, '1'::integer, '{bcrypt}$2a$10$QHkA0ApKKqS0/Q/QgiSmeeTJqjNyIdBDKtCCgzZqVRH.yjDNGTMl6'::character varying(255), 'seleta'::character varying(255))
 returning id;

INSERT INTO public.role_user (
user_id, role_id) VALUES (
'1'::bigint, '1'::bigint)
 returning role_id;
id, email, enabled, last_name, password, username, version, person_id) VALUES (
'1'::bigint, '3263551@myuwc.ac.za'::character varying, '1'::integer, 'Admin'::character varying, '{bcrypt}$2a$10$QHkA0ApKKqS0/Q/QgiSmeeTJqjNyIdBDKtCCgzZqVRH.yjDNGTMl6'::character varying, '3263551@myuwc.ac.za'::character varying, '1'::bigint, '1'::bigint)
 returning id;

======================================= USER_ROLES ======================================
INSERT INTO public.user_roles(users_id, roles_id)VALUES (1, 1);

====================================== OAUTH USER ACCOUNT ===================================
INSERT INTO public.oauth_client_details(
            client_id, client_secret, web_server_redirect_uri, scope, access_token_validity, 
            refresh_token_validity, resource_ids, authorized_grant_types, autoapprove)
    VALUES ('web','{bcrypt}$2a$10$QHkA0ApKKqS0/Q/QgiSmeeTJqjNyIdBDKtCCgzZqVRH.yjDNGTMl6','http://localhost:8282','READ,WRITE',43000,43000,'slabbert','password,refresh_token,authorization_code','1');
    
    
    
###########Reference number
Drop SEQUENCE reference_sq;
CREATE SEQUENCE reference_sq
INCREMENT 1
START 0001;
