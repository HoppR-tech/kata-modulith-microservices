-- liquibase formatted sql

CREATE TABLE "inventories"
(
	"product_ref" VARCHAR(26) PRIMARY KEY,
	"quantity"    INT  NOT NULL
);
