-- liquibase formatted sql

CREATE TABLE "reservations"
(
	"order_id"    VARCHAR(26) NOT NULL,
	"product_ref" VARCHAR(26) NOT NULL,
	"quantity"    INT         NOT NULL,
	PRIMARY KEY ("order_id", "product_ref")
);

