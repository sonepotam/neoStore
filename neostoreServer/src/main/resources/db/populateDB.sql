DELETE FROM users;
DELETE FROM pets;

ALTER SEQUENCE neostore_seq RESTART WITH 1;



insert into breeds( name) values
('кот'),
('собака'),
('рыбки'),
('крокодил'),
('лошадь'),
('корова'),
('попугай');


