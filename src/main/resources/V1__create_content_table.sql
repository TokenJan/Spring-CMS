create table content {
  id bigint not null auto_increment,
  status varchar not null,
  type varchar not null,
  publish_id bigint not null,
  created_time date not null,
  updated_time date not null,
  primary key(id)
};