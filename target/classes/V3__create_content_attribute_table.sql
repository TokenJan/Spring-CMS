create table content_attribute {
  id bigint not null auto_increment,
  version_id bigint not null,
  version bigint not null,
  attibute_key varchar not null,
  attibute_value varchar not null,
  attibute_type varchar not null,
  created_time date not null,
  updated_time date not null,
  primary key(id)
};