create sequence runs_seq start with 1 increment by 50;
create sequence runs_users_seq start with 1 increment by 50;
create table runs (
  distance numeric(16, 4),
  finish_latitude numeric(6, 4),
  finish_longitude numeric(7, 4),
  is_active boolean not null,
  start_latitude numeric(6, 4),
  start_longitude numeric(7, 4),
  finish_datetime timestamp(6),
  id bigint not null,
  start_datetime timestamp(6),
  user_id bigint not null,
  average_speed numeric(16, 4),
  primary key (id)
);
create table runs_users (
  birth_date date not null,
  id bigint not null,
  first_name varchar(255) not null,
  last_name varchar(255) not null,
  sex varchar(255) not null check (
    sex in ('MALE', 'FEMALE')
  ),
  primary key (id)
);
alter table
  if exists runs
add
  constraint fk_user_run foreign key (user_id) references runs_users;
