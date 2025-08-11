-- liquibase formatted sql
-- changeset liquibaseuser:1

create extension vector;

create sequence customer_seq;
-- rollback drop sequence customer_seq;

CREATE TABLE customer_orders (
	id    integer PRIMARY KEY DEFAULT nextval('customer_seq'),
	customer_id varchar(255) NULL,
	order_id int8 NULL,
	product_id varchar(255) NULL,
	quantity int4 NOT NULL
);
-- rollback drop table customer_orders;

create table products(
id text PRIMARY KEY,
DATA JSONB);
-- rollback drop table products;


create table product_reviews(
id text PRIMARY KEY,
DATA JSONB);
-- rollback drop table product_reviews;

CREATE TABLE vector_store (
	id text NOT NULL,
	"content" text NULL,
	metadata json NULL,
	embedding vector(1024) NULL,
	CONSTRAINT vector_store_pkey PRIMARY KEY (id)
);

-- rollback DROP TABLE vector_store;

CREATE INDEX spring_ai_vector_index ON retail.vector_store USING hnsw (embedding vector_cosine_ops);

-- rollback DROP INDEX spring_ai_vector_index;