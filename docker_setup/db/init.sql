-- public.store_product_seq definition
CREATE SEQUENCE public.store_product_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
	CACHE 1
	NO CYCLE;

-- public.store_manufacturer_seq definition
CREATE SEQUENCE public.store_manufacturer_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
	CACHE 1
	NO CYCLE;

-- public.store_product_type_seq definition
CREATE SEQUENCE public.store_product_type_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
	CACHE 1
	NO CYCLE;

-- public.store_product_type definition
CREATE TABLE public.store_product_type (
                                        product_type_id Integer NOT null default nextval('public.store_product_type_seq'),
                                        product_name varchar(100) NOT NULL,
                                        CONSTRAINT store_product_type_pk PRIMARY KEY (product_type_id),
										CONSTRAINT store_product_type_name_key UNIQUE (product_name)
);

-- public.store_manufacturer definition
CREATE TABLE public.store_manufacturer (
                                        manufacturer_id Integer NOT null default nextval('public.store_manufacturer_seq'),
                                        manufacturer_name varchar(100) NOT NULL,
										manufacturer_phone varchar(100) NOT NULL,
										manufacturer_address varchar(100) NOT NULL,
                                        CONSTRAINT store_manufacturer_pk PRIMARY KEY (manufacturer_id),
										CONSTRAINT store_manufacturer_name_key UNIQUE (manufacturer_name)
);

-- public.store_product definition
CREATE TABLE public.store_product (
                                   product_id Integer NOT null default nextval('public.store_product_seq'),
                                   product_name varchar(100) NOT NULL,
								   product_description varchar(100),
								   product_price varchar(100) NOT NULL,
								   product_type int4 NOT NULL,
								   product_manufacturer int4 NOT NULL,
                                   CONSTRAINT store_product_pk PRIMARY KEY (product_id),
                                   CONSTRAINT store_product__name_key UNIQUE (product_name),
                                   CONSTRAINT store_product_fk_1 FOREIGN KEY (product_type) REFERENCES store_product_type(product_type_id),
								   CONSTRAINT store_product_fk_2 FOREIGN KEY (product_manufacturer) REFERENCES store_manufacturer(manufacturer_id)
);

--STORE_PRODUCT_TYPE
INSERT INTO public.store_product_type (product_name) VALUES('PHONE');
INSERT INTO public.store_product_type (product_name) VALUES('LAPTOP');
INSERT INTO public.store_product_type (product_name) VALUES('WATCH');
INSERT INTO public.store_product_type (product_name) VALUES('TABLET');

--STORE_MANUFACTURER
INSERT INTO public.store_manufacturer (manufacturer_name, manufacturer_phone, manufacturer_address)
VALUES('SAMSUNG', '+40723456789', '19-21 Bucuresti-Ploiest ST.');

INSERT INTO public.store_manufacturer (manufacturer_name, manufacturer_phone, manufacturer_address)
VALUES('APPLE', '+40723987654', '47 Theodor Pallady BD.');

INSERT INTO public.store_manufacturer (manufacturer_name, manufacturer_phone, manufacturer_address)
VALUES('HUAWEI', '+40723111222', '23 Basarabia BD.');

--STORE_PRODUCT
INSERT INTO public.store_product (product_name, product_description, product_price, product_type, product_manufacturer)
VALUES('GALAXY-WATCH-5', 'Latest Galaxy Watch', '300', 3, 1);

INSERT INTO public.store_product (product_name, product_description, product_price, product_type, product_manufacturer)
VALUES('WATCH-GT-3', 'Latest GT Watch', '200', 3, 3);

INSERT INTO public.store_product (product_name, product_description, product_price, product_type, product_manufacturer)
VALUES('IPHONE-14-PRO', 'Latest Iphone', '1100', 1, 2);