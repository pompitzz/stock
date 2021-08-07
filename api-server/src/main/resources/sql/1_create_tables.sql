CREATE TABLE stock
(
    stock_id   bigint         NOT NULL AUTO_INCREMENT,
    exchange   varchar(255)   NOT NULL,
    industry   varchar(255),
    logo_url   varchar(255),
    market     varchar(255)   NOT NULL,
    name       varchar(255)   NOT NULL,
    sector     varchar(255),
    state      varchar(255),
    symbol     varchar(255)   NOT NULL UNIQUE,
    website    varchar(255),
    currency   varchar(255)   NOT NULL,
    time_zone  varchar(255)   NOT NULL,
    price_date date           NOT NULL,
    price      decimal(19, 2) NOT NULL,
    PRIMARY KEY (stock_id)
);

ALTER TABLE stock
    ADD (last_synced_at datetime DEFAULT NOW());


CREATE TABLE historical_stock_price
(
    historical_stock_price_id bigint         NOT NULL AUTO_INCREMENT,
    close                     decimal(19, 2) NOT NULL,
    date                      date           NOT NULL,
    high                      decimal(19, 2) NOT NULL,
    low                       decimal(19, 2) NOT NULL,
    open                      decimal(19, 2) NOT NULL,
    volume                    decimal(19, 2) NOT NULL,
    stock_id                  bigint         NOT NULL,
    PRIMARY KEY (historical_stock_price_id),
    FOREIGN KEY (stock_id) REFERENCES stock (stock_id)
);

CREATE TABLE user_interest_stock
(
    user_interest_stock_id bigint NOT NULL AUTO_INCREMENT,
    deleted                bit    NOT NULL,
    stock_id               bigint NOT NULL,
    user_id                bigint NOT NULL,
    PRIMARY KEY (user_interest_stock_id)
);

CREATE TABLE user
(
    user_id                   bigint       NOT NULL AUTO_INCREMENT,
    access_token              varchar(255) NOT NULL,
    access_token_expiry_time  datetime     NOT NULL,
    oauth_service_type        integer      NOT NULL,
    oauth_service_user_id     bigint       NOT NULL,
    refresh_token             varchar(255) NOT NULL,
    refresh_token_expiry_time datetime     NOT NULL,
    user_name                 varchar(255) NOT NULL,
    PRIMARY KEY (user_id)
) ENGINE = InnoDB;

ALTER TABLE user
    ADD UNIQUE INDEX oauth (oauth_service_user_id, oauth_service_type);

ALTER TABLE user
    ADD (role varchar(255) DEFAULT 'USER')
