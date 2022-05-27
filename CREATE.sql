CREATE TABLE IF NOT EXISTS public.product
(
    id integer NOT NULL DEFAULT nextval('product_id_seq'::regclass),
    code character varying(10) COLLATE pg_catalog."default",
    description character varying(255) COLLATE pg_catalog."default",
    is_available boolean NOT NULL,
    name character varying(255) COLLATE pg_catalog."default",
    price_hrk numeric(19,2),
    CONSTRAINT product_pkey PRIMARY KEY (id),
    CONSTRAINT product_code_key UNIQUE (code),
    CONSTRAINT product_price_hrk_check CHECK (price_hrk >= 0::numeric)
)