create table projects (
  id integer identity primary key,
  name varchar(25) NOT NULL,
  shortname varchar(25) NOT NULL,
  description varchar(50) NOT NULL
);

create table persons (
  id integer identity primary key,
  fname varchar(25),
  sname varchar(25),
  lname varchar(25),
  position varchar(25)
);

create table tasks (
  id integer identity primary key,
  name varchar(50),
  time int,
  start_date date,
  end_date date,
  status varchar(50),
  project_id INTEGER,
  person_id INTEGER,
  constraint project_fk foreign key(project_id) references projects(id)
  on delete set null
  on update cascade,
  constraint person_fk foreign key(person_id) references persons(id)
  on delete cascade
  on update cascade
);