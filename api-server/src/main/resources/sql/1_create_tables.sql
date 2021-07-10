create table stock
(
    stock_id   bigint         not null auto_increment,
    exchange   varchar(255)   not null,
    industry   varchar(255),
    logo_url   varchar(255),
    market     varchar(255)   not null,
    name       varchar(255)   not null,
    sector     varchar(255),
    state      varchar(255),
    symbol     varchar(255)   not null unique,
    website    varchar(255),
    currency   varchar(255)   not null,
    time_zone  varchar(255)   not null,
    price_date date           not null,
    price      decimal(19, 2) not null,
    primary key (stock_id)
);


create table historical_stock_price
(
    historical_stock_price_id bigint         not null auto_increment,
    close                     decimal(19, 2) not null,
    date                      date           not null,
    high                      decimal(19, 2) not null,
    low                       decimal(19, 2) not null,
    open                      decimal(19, 2) not null,
    volume                    decimal(19, 2) not null,
    stock_id                  bigint         not null,
    primary key (historical_stock_price_id),
    foreign key (stock_id) references stock (stock_id)
);

create table user_interest_stock
(
    user_interest_stock_id bigint not null auto_increment,
    deleted                bit    not null,
    stock_id               bigint not null,
    user_id                bigint not null,
    primary key (user_interest_stock_id)
)
