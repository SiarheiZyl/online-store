create table vebinar.users
(
  id         int auto_increment,
  first_name varchar(255) not null,
  last_name  varchar(255) not null,
  birthdate  date         not null,
  login      varchar(255) not null,
  password   varchar(255) not null,
  email      varchar(255) not null,
  role       int(2)       null,
  constraint users_id_uindex
  unique (id)
);

alter table vebinar.users
  add primary key (id);

create table items (
  item_id INTEGER(18) NOT NULL AUTO_INCREMENT,
  item_name VARCHAR(60) NOT NULL,
  item_category INTEGER(18) NOT NULL,
  price DOUBLE(30, 2) NOT NULL,
  weight DOUBLE(8, 3)  NOT NULL,
  available_count INTEGER(18) NOT NULL,
  picture BLOB,
  CONSTRAINT item_id_pk PRIMARY KEY (item_id)
);

create table item_params (
  item_param_id INTEGER(18) NOT NULL AUTO_INCREMENT,
  item  INTEGER(18) NOT NULL,
  param  INTEGER(18) NOT NULL,
  FOREIGN KEY (item) REFERENCES items (item_id),
  FOREIGN KEY (param) REFERENCES params (param_id),
  CONSTRAINT item_param_id_pk PRIMARY KEY (item_param_id)
)

create table categories (
  catergory_id INTEGER(18) NOT NULL AUTO_INCREMENT,
  category_name VARCHAR(60)  NOT NULL,

  CONSTRAINT catergory_id_pk PRIMARY KEY (catergory_id)
);

create table params (
  param_id INTEGER(18) NOT NULL AUTO_INCREMENT,
  param_name VARCHAR(60)  NOT NULL,
  CONSTRAINT param_id_pk PRIMARY KEY (param_id)
);