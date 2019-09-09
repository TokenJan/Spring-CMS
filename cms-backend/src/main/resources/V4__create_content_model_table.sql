create table content {
  id bigint not null auto_increment,
  name varchar not null,
  attribute blob not null,
  created_time date not null,
  updated_time date not null,
  primary key(id)
};