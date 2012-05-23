drop table if exists USERS;
create table USERS (
ID identity,
USERNAME varchar(256),
CREATION_USER varchar(256),
CREATION_DATETIME timestamp,
constraint PK_USER_ID primary key (ID));

drop table if exists TAGS;
create table TAGS (
ID identity,
KEY varchar(256),
VALUE varchar(256),
constraint PK_TAG_ID primary key (ID));