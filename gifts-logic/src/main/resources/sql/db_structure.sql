CREATE SCHEMA IF NOT EXISTS gifts;

CREATE TABLE tag
(
    id   bigint NOT NULL AUTO_INCREMENT,
    name varchar(50),
    PRIMARY KEY (id)
);

CREATE TABLE gift_certificate
(
    id               bigint         NOT NULL AUTO_INCREMENT,
    name             varchar(100)   NOT NULL UNIQUE,
    description      varchar(500)   NOT NULL,
    price            decimal(10, 2) NOT NULL,
    duration         smallint       NOT NULL,
    create_date      datetime       NOT NULL,
    last_update_date datetime       NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE gift_tags
(
    certificate_id bigint NOT NULL,
    tag_id         bigint NOT NULL,
    CONSTRAINT gift_tags_gift_certificate FOREIGN KEY (certificate_id) REFERENCES gift_certificate (id),
    CONSTRAINT gift_tags_tag FOREIGN KEY (tag_id) REFERENCES tag (id)
)