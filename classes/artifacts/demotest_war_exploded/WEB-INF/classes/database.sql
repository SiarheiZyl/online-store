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
  addr_id INTEGER(18) NOT NULL,
  FOREIGN KEY (addr_id) REFERENCES addresses (address_id)
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

create table addresses (
  address_id INTEGER(18) NOT NULL AUTO_INCREMENT,
  country  VARCHAR(255) NOT NULL,
  town  VARCHAR(255) NOT NULL,
  zip_code VARCHAR(10) NOT NULL,
  street VARCHAR(255) NOT NULL,
  building VARCHAR(20) NOT NULL,
  apartment  VARCHAR(20) NOT NULL,
  CONSTRAINT client_address_pk PRIMARY KEY (address_id)
);

create table orders (
  order_id  INTEGER (18) NOT NULL AUTO_INCREMENT,
  ordering_user INTEGER(18)  NOT NULL,
  payment_method  VARCHAR(40)    NOT NULL,
  payment_state VARCHAR(40)    NOT NULL,
  delivery_method VARCHAR(40)    NOT NULL,
  order_status VARCHAR(40)    NOT NULL,
  CONSTRAINT order_id_pk PRIMARY KEY (order_id),
  FOREIGN KEY (ordering_user) REFERENCES users (id)
);

create table ordered_items (
  ordered_item_id INTEGER(18) NOT NULL AUTO_INCREMENT,
  orders INTEGER(18) NOT NULL,
  item  INTEGER(18) NOT NULL,
  quantity  INTEGER(18)  NOT NULL,
  CONSTRAINT order_items_id_pk PRIMARY KEY (ordered_item_id),
  FOREIGN KEY (orders) REFERENCES orders (order_id),
  FOREIGN KEY (item) REFERENCES items (item_id)
);