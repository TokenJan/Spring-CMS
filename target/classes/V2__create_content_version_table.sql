create table content_version {
  id bigint not null auto_increment,
  content_id bigint not null,
  version bigint not null,
  status varchar not null,
  created_time date not null,
  updated_time date not null,
  primary key(id)
};