-- Table: public.sign_up

-- DROP TABLE IF EXISTS public.sign_up;

CREATE TABLE IF NOT EXISTS public.sign_up
(
    auth_code character varying(6) COLLATE pg_catalog."default" NOT NULL,
    phone_number character varying(256) COLLATE pg_catalog."default" NOT NULL,
    status integer,
    signup_session_id character varying(32) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT sign_up_pkey PRIMARY KEY (signup_session_id)
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.sign_up
    OWNER to postgres;