create table projects (
  id integer identity primary key,
  name varchar(25) not null,
  shortname varchar(25) not null,
  description varchar(50)
);

create table persons (
  id integer identity primary key,
  fname varchar(25) not null,
  sname varchar(25) not null,
  lname varchar(25) not null,
  position varchar(25) not null
);

create table tasks (
  id integer identity primary key,
  name varchar(50) not null,
  time integer not null,
  startDate date not null,
  endDate date not null,
  status varchar(50) not null,
  projectId integer,
  personId integer,
  constraint project_fk foreign key(projectId) references projects(id)
  on delete set null
  on update cascade,
  constraint person_fk foreign key(personId) references persons(id)
  on delete set null
  on update cascade
);