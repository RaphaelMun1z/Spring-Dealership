CREATE TABLE public.tb_branches (
  created_at date NULL,
  updated_at date NULL,
  branch_address_id character varying(255) NOT NULL,
  branch_type character varying(255) NOT NULL,
  email character varying(255) NOT NULL,
  id character varying(255) NOT NULL,
  manager_name character varying(255) NOT NULL,
  name character varying(255) NOT NULL,
  opening_hours character varying(255) NOT NULL,
  phone_number character varying(255) NULL,
  status character varying(255) NULL
);

ALTER TABLE public.tb_branches ADD CONSTRAINT tb_branches_pkey PRIMARY KEY (id);