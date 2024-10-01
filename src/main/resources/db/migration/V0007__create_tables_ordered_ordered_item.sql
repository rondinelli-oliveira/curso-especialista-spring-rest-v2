create table ordered (address_number integer,
freight_rate decimal(38, 2),
order_status tinyint check (order_status between 0 and 3),
sub_total decimal(38, 2),
total_value decimal(38, 2),
address_city_id bigint,
cancellation_date datetime(6),
confirmation_date datetime(6),
delivery_date datetime(6),
id bigint not null auto_increment,
payment_method_id bigint not null,
registration_date datetime(6),
restaurant_id bigint not null,
user_id bigint not null,
address_complement varchar(255),
address_district varchar(255),
address_street varchar(255),
address_zip_code varchar(255),

primary key (id),

constraint FKt8bjyp3vhima34ivd33koqvxv foreign key (address_city_id) references city (id),
constraint FK1bsh1egyoxeqqhseeur3jnye1 foreign key (restaurant_id) references restaurant (id),
constraint FKjqbh5jbj1olkur6fpmle5r9ev foreign key (user_id) references user (id),
constraint FKt3o0ktuow7ehkv8mg6venb2xh foreign key (payment_method_id) references payment_method (id)
) engine = InnoDB;

create table ordered_item (quantity integer,
total_price decimal(38, 2),
unit_price decimal(38, 2),
id bigint not null auto_increment,
ordered_id bigint not null,
product_id bigint not null,
observation varchar(255),

primary key (id),
unique key uk_item_ordered_product (ordered_id, product_id),

constraint FKfyed8s2qkifmpo0ssjkawe322 foreign key (ordered_id) references ordered (id),
constraint FKaq0vwxoytp1o7acx7bljwr4u foreign key (product_id) references product (id)

) engine = InnoDB;