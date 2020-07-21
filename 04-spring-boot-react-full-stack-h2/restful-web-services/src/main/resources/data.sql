/*https://www.browserling.com/tools/bcrypt Use Rounds 10*/

/*vikas/dummy*/
INSERT INTO USER (ID, USERNAME, PASSWORD, ROLE) 
VALUES (1, 'vikas', '$2a$10$3zHzb.Npv1hfZbLEU5qsdOju/tk2je6W6PnNnY.c1ujWPcZh4PL6e','ROLE_USER');

/*vikas1/mypassword*/
INSERT INTO USER (ID, USERNAME, PASSWORD, ROLE) 
VALUES (2, 'vikas1', '$2a$10$i9AckmxMkb4yKtLCdxeQheCm2pXWB3qZ2G189/Ph/DUci1DvLO.Rq','ROLE_USER');


insert into todo(id, username,description,target_date,is_done)
values(10001, 'vikas', 'Learn JPA', sysdate(), false);

insert into todo(id, username,description,target_date,is_done)
values(10002, 'vikas', 'Learn Data JPA', sysdate(), false);

insert into todo(id, username,description,target_date,is_done)
values(10003, 'vikas', 'Learn Microservices', sysdate(), false);