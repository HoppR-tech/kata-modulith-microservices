-- liquibase formatted sql

CREATE TABLE "orders"
(
	"id" VARCHAR(26) PRIMARY KEY
);

CREATE TABLE "order_items"
(
	"order_id"    VARCHAR(26) NOT NULL,
	"product_ref" VARCHAR(26) NOT NULL,
	"quantity"    INT NOT NULL,
	PRIMARY KEY ("order_id", "product_ref"),
	FOREIGN KEY ("order_id") REFERENCES "orders" ("id") ON DELETE CASCADE
);

CREATE TABLE "reservations"
(
	"order_id" 	  VARCHAR(26) NOT NULL,
	"product_ref" VARCHAR(26) PRIMARY KEY,
	"quantity"    INT NOT NULL
);

