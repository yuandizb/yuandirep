create table IF NOT EXISTS oauth_client_details (
  client_id VARCHAR(200) PRIMARY KEY,
  resource_ids VARCHAR(200),
  client_secret VARCHAR(200),
  scope VARCHAR(200),
  authorized_grant_types VARCHAR(200),
  web_server_redirect_uri VARCHAR(200),
  authorities VARCHAR(200),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additional_information VARCHAR(4096),
  autoapprove VARCHAR(200)
);
# INSERT INTO oauth_client_details VALUE ('ms',NULL ,'ms','all,write,read','code,password,custom,refresh_token',NULL ,NULL ,NULL ,NULL,NULL ,NULL);