-- liquibase formatted sql

CREATE TABLE "orders"
(
	"id"           VARCHAR(26) PRIMARY KEY,
	"customer_id"  VARCHAR(26) NOT NULL,
	"placed_at"    TIMESTAMP,
	"cancelled_at" TIMESTAMP
);

CREATE TABLE "order_items"
(
	"order_id"    VARCHAR(26) NOT NULL,
	"product_ref" VARCHAR(26) NOT NULL,
	"quantity"    INT         NOT NULL,
	PRIMARY KEY ("order_id", "product_ref"),
	FOREIGN KEY ("order_id") REFERENCES "orders" ("id") ON DELETE CASCADE
);

CREATE TABLE "reservations"
(
	"order_id"    VARCHAR(26) NOT NULL,
	"product_ref" VARCHAR(26) NOT NULL,
	"quantity"    INT         NOT NULL,
	PRIMARY KEY ("order_id", "product_ref")
);

CREATE TABLE "loyalties"
(
	"customer_id"      VARCHAR(26) NOT NULL,
	"order_id"         VARCHAR(26) NOT NULL,
	"number_of_points" INT         NOT NULL,
	PRIMARY KEY ("customer_id", "order_id")
);

