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