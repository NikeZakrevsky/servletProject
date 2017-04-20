create table projects (
  id integer identity primary key,
  name varchar(25) not null,
  short_name varchar(25) not null,
  description varchar(50)
);

create table persons (
  id integer identity primary key,
  first_name varchar(25) not null,
  middle_name varchar(25) not null,
  last_name varchar(25) not null,
  position varchar(25) not null
);

create table tasks (
  id integer identity primary key,
  name varchar(50) not null,
  work_time integer not null,
  start_date date not null,
  end_date date not null,
  status varchar(50) not null,
  project_id integer,
  person_id integer,
  constraint project_fk foreign key(project_id) references projects(id)
  on delete set null
  on update cascade,
  constraint person_fk foreign key(person_id) references persons(id)
  on delete set null
  on update cascade
);