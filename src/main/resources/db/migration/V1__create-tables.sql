create table client (
    id serial primary key,
    name varchar(255) not null,
    cpf varchar(255) not null unique,
    email varchar(255) not null unique,
    money float not null
);
create table shopkeeper (
    id serial primary key,
    name varchar(255) not null,
    cnpj varchar(255) not null unique,
    email varchar(255) not null unique,
    money float not null
 );