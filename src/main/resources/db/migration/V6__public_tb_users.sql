CREATE TABLE public.tb_users (
  id character varying(255) NOT NULL,
  email character varying(255) NOT NULL,
  is_account_non_expired boolean NOT NULL,
  is_account_non_locked boolean NOT NULL,
  is_credentials_non_expired boolean NOT NULL,
  is_enabled boolean NOT NULL,
  name character varying(255) NOT NULL,
  password character varying(255) NOT NULL,
  phone character varying(255) NULL,
  role character varying(255) NULL
);

ALTER TABLE public.tb_users ADD CONSTRAINT tb_users_pkey PRIMARY KEY (id);