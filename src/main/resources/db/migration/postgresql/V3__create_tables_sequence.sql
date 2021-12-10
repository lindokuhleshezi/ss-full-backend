CREATE SEQUENCE IF NOT EXISTS address_sequence start 1 increment 1;
ALTER SEQUENCE address_sequence OWNER TO ss;
ALTER SEQUENCE address_sequence OWNED by address.id;

CREATE SEQUENCE IF NOT EXISTS contact_person_sequence start 1 increment 1;
ALTER SEQUENCE contact_person_sequence OWNER TO ss;
ALTER SEQUENCE contact_person_sequence OWNED by profile.id;

CREATE SEQUENCE IF NOT EXISTS profile_sequence start 1 increment 1;
ALTER SEQUENCE profile_sequence OWNER TO ss;
ALTER SEQUENCE profile_sequence OWNED by profile.id;

CREATE SEQUENCE IF NOT EXISTS users_sequence start 1 increment 1;
ALTER SEQUENCE users_sequence OWNER TO ss;
ALTER SEQUENCE users_sequence OWNED by users.id;

CREATE SEQUENCE IF NOT EXISTS category_sequence start 1 increment 1;
ALTER SEQUENCE category_sequence OWNER TO ss;
ALTER SEQUENCE category_sequence OWNED by category.id;

CREATE SEQUENCE IF NOT EXISTS doctor_sequence start 1 increment 1;
ALTER SEQUENCE doctor_sequence OWNER TO ss;
ALTER SEQUENCE doctor_sequence OWNED by doctor.id;


