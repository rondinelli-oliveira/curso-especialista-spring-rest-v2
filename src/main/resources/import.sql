insert into kitchen (id, name) values (1, 'Tailandesa')
insert into kitchen (id, name) values (2, 'Indiana')

insert into state (id, name) values (1, 'Minas Gerais');
insert into state (id, name) values (2, 'São Paulo');
insert into state (id, name) values (3, 'Ceará');

insert into city (id, name, state_id) values (1, 'Uberlândia', 1);
insert into city (id, name, state_id) values (2, 'Belo Horizonte', 1);
insert into city (id, name, state_id) values (3, 'São Paulo', 2);
insert into city (id, name, state_id) values (4, 'Campinas', 2);
insert into city (id, name, state_id) values (5, 'Fortaleza', 3);

insert into restaurant (name, freigth_rate, kitchen_id, registration_date, update_date, address_city_id, address_zip_code, address_street, address_number, address_district) values ('Thai Gourmet', 10, 1, utc_timestamp, utc_timestamp, 1, '38400-999', 'Rua João Pinheiro', 1000, 'Centro');
insert into restaurant (name, freigth_rate, kitchen_id, registration_date, update_date) values ('Thai Delivery', 9.50, 1, utc_timestamp, utc_timestamp);
insert into restaurant (name, freigth_rate, kitchen_id, registration_date, update_date) values ('Tuk Tuk Comida Indiana', 15, 2, utc_timestamp, utc_timestamp);

insert into payment_method (id, description) values (1, 'Credit card');
insert into payment_method (id, description) values (2, 'Debit card');
insert into payment_method (id, description) values (3, 'Cash');

insert into permission (id, name, description) values (1, 'LIST_KITCHEN', 'Allow consult kitchens');
insert into permission (id, name, description) values (2, 'UPDATE_KITCHEN', 'Allow update kitchen');

insert into restaurant_payment_methods (restaurant_id, payment_methods_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3);