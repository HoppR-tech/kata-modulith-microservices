-- liquibase formatted sql

CREATE TABLE "orders"
(
	"id" VARCHAR(26) PRIMARY KEY,
	"placed_at" INTEGER,
	"canceled_at" INTEGER
);

CREATE TABLE "order_items"
(
	"id"          VARCHAR(26) PRIMARY KEY,
	"order_id"    VARCHAR(26) NOT NULL,
	"product_ref" VARCHAR(26) NOT NULL,
	"quantity"    INT  NOT NULL,
	FOREIGN KEY ("order_id") REFERENCES "orders" ("id") ON DELETE CASCADE
);

