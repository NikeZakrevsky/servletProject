create table projects (
  project_id integer identity primary key,
  project_name varchar(25) not null,
  short_name varchar(25) not null,
  description varchar(50)
);

create table persons (
  person_id integer identity primary key,
  first_name varchar(25) not null,
  middle_name varchar(25) not null,
  last_name varchar(25) not null,
  position varchar(40) not null
);

create table tasks (
  task_id integer identity primary key,
  task_name varchar(50) not null,
  work_time integer not null,
  start_date date not null,
  end_date date not null,
  status varchar(50) not null,
  project_id integer,
  person_id integer,
  constraint project_fk foreign key(project_id) references projects(project_id)
  on delete set null
  on update cascade,
  constraint person_fk foreign key(person_id) references persons(person_id)
  on delete set null
  on update cascade
);