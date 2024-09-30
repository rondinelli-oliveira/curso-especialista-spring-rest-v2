create table payment_method (id bigint not null auto_increment,
description varchar(255) not null,
primary key (id)) engine = InnoDB;

create table permission (id bigint not null auto_increment,
description varchar(255) not null,
name varchar(255) not null,
primary key (id)) engine = InnoDB;

create table permission_group_permissions (permission_group_id bigint not null,
permissions_id bigint not null) engine = InnoDB;

create table permission_group (id bigint not null auto_increment,
name varchar(255) not null,
primary key (id)) engine = InnoDB;

create table product (active bit not null,
price decimal(38, 2) not null,
id bigint not null auto_increment,
restaurant_id bigint not null,
description varchar(255) not null,
name varchar(255) not null,
primary key (id)) engine = InnoDB;

create table restaurant (address_number integer,
freigth_rate decimal(38, 2) not null,
address_city_id bigint,
id bigint not null auto_increment,
kitchen_id bigint not null,
registration_date datetime not null,
update_date datetime not null,
address_complement varchar(255),
address_district varchar(255),
address_street varchar(255),
address_zip_code varchar(255),
name varchar(255) not null,
primary key (id)) engine = InnoDB;

create table restaurant_payment_methods (payment_methods_id bigint not null,
restaurant_id bigint not null) engine = InnoDB;

create table user (data_cadastro datetime not null,
id bigint not null auto_increment,
email varchar(255) not null,
nome varchar(255) not null,
senha varchar(255) not null,
primary key (id)) engine = InnoDB;

create table user_permission_groups (permission_groups_id bigint not null,
user_id bigint not null) engine = InnoDB;

alter table permission_group_permissions add constraint fk_permission_group_permissions foreign key (permissions_id) references permission (id);

alter table permission_group_permissions add constraint fk_group_permission_group foreign key (permission_group_id) references permission_group (id);

alter table product add constraint fk_product_restaurant foreign key (restaurant_id) references restaurant (id);

alter table restaurant add constraint fk_restaurant_city foreign key (address_city_id) references city (id);

alter table restaurant add constraint fk_restaurant_kitchen foreign key (kitchen_id) references kitchen (id);

alter table restaurant_payment_methods add constraint fk_restaurant_payment_method foreign key (payment_methods_id) references payment_method (id);

alter table restaurant_payment_methods add constraint fk_payment_method_restaurant foreign key (restaurant_id) references restaurant (id);

alter table user_permission_groups add constraint fk_user_group foreign key (permission_groups_id) references permission_group (id);

alter table user_permission_groups add constraint fk_group_user foreign key (user_id) references user (id);