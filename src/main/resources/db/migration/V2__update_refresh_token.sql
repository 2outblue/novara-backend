ALTER TABLE refresh_token
ADD revoked_at datetime(6);

ALTER TABLE refresh_token
ADD replaced_by varchar(255);