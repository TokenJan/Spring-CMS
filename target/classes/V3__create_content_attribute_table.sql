create table content_attribute {
  id bigint not null auto_increment,
  version_id bigint not null,
  version bigint not null,
  key varchar not null,
  value varchar not null,
  type varchar not null,
  created_time date not null,
  updated_time date not null,
  primary key(id)
};