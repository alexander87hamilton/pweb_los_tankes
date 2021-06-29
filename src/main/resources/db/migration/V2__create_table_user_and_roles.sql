CREATE TABLE public.user
(
  id serial NOT NULL,
  username character varying,
  fullname character varying,
  email character varying,
  password character varying,
  CONSTRAINT user_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);

insert into public.user (username, fullname, email, password) values ('eltanke1', 'El Tanke Uno', 'eltanke@ceis.cujae.edu.cu', 'e4erfsdfsdae');


CREATE TABLE role
(
  id serial NOT NULL,
  role_name character varying,
  CONSTRAINT role_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);

CREATE TABLE user_role
(
  user_id integer NOT NULL,
  role_id integer NOT NULL,
  CONSTRAINT user_role_pkey PRIMARY KEY (user_id, role_id),
  CONSTRAINT user_role_role_id_fkey FOREIGN KEY (role_id)
      REFERENCES public.role (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT user_role_user_id_fkey FOREIGN KEY (user_id)
      REFERENCES public."user" (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);

insert into role (role_name ) values ('Admin');
insert into role (role_name ) values ('Guest');
insert into role (role_name ) values ('Planner');

insert into user_role (user_id, role_id) values ((select id from public.user  where username = 'eltanke1'), (select id from role where role_name = 'Admin'));
insert into user_role (user_id, role_id) values ((select id from public.user  where username = 'eltanke1'), (select id from role where role_name = 'Guest'));
insert into user_role (user_id, role_id) values ((select id from public.user  where username = 'eltanke1'), (select id from role where role_name = 'Planner'));


update public.user set password = '$2a$10$e.3rFWJaCQV0zeLKn9cnIO.Ck6MniRfRQe0dEvN4qHDeCdIm/kbMi';

update role set role_name = upper(role_name);
update role set role_name = concat('ROLE_', role_name);
